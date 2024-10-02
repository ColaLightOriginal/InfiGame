package com.puputan.infi.Systems;

import Screens.GameScreen;
import Screens.GameStatesEnum;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.puputan.infi.Objects.Enemy.EnemyObject;
import com.puputan.infi.Objects.Player.PlayerObject;
import com.puputan.infi.Objects.Player.PowerUpsEnum;
import com.puputan.infi.Tools.RaycastCallbackImpl;
import com.puputan.infi.Objects.Bullet.BulletObject;
import com.puputan.infi.Objects.Bullet.ShootingPointType;
import com.puputan.infi.Objects.ShootingPointObject;

import java.util.*;

public class PlayerSystems {

    private PlayerObject playerObject;

    private int hp = 3;
    private int experience = 0;
    private int level = 0;
    private final HashMap<Integer, Long> experienceLevels = new HashMap<>();
    private final Map<ShootingPointType,ShootingPointObject> shootingPointObjectsList;
    private ArrayList<Vector2> shootingPointsList;
    private LinkedList<PowerUpsEnum> possiblePowerUps;
    private LinkedList<PowerUpsEnum> actualPowerUps;
    private Random PRNG = new Random();

    private float lastShootTime;
    private float shootingTimeDelta;
    private float minimumShootingTime = 0.2f;

    public PlayerSystems(PlayerObject playerObject){
        this.playerObject = playerObject;
        this.shootingPointObjectsList = new HashMap<>();
        this.actualPowerUps = new LinkedList<>();
        this.possiblePowerUps = new LinkedList<>();
        this.lastShootTime = 0f;
        this.shootingTimeDelta = 1f;

        initializeLevels();
        initializeShootingPoints();
        initializePowerUps();
    }

    public void initializeLevels(){
        this.experienceLevels.put(0, 0L);
        this.experienceLevels.put(1, 1000L);
        this.experienceLevels.put(2, 2000L);
        this.experienceLevels.put(3, 3000L);
        this.experienceLevels.put(4, 4000L);
        this.experienceLevels.put(5, 5000L);
        this.experienceLevels.put(6, 10000L);
        this.experienceLevels.put(7, 20000L);
        this.experienceLevels.put(8, 25000L);
        this.experienceLevels.put(9, 100000L);
    }

    public void initializePowerUps(){
        actualPowerUps.add(PowerUpsEnum.Bullets);
        possiblePowerUps.addAll(Arrays.asList(PowerUpsEnum.values()));
        possiblePowerUps.remove(PowerUpsEnum.Bullets);
    }

    public void initializeShootingPoints(){
        this.shootingPointsList = new ArrayList<>();
        this.shootingPointsList.add(new Vector2(this.playerObject.getWidth()/2, this.playerObject.getHeight() + 1));
        this.shootingPointObjectsList.put(ShootingPointType.MIDDLE, new ShootingPointObject(this.shootingPointsList.get(0),this.playerObject));
    }

    public void upgradeShootingPoints(){
        this.shootingPointsList.add(new Vector2(0.1f*this.playerObject.getWidth(),  this.playerObject.getHeight() + 1));
        this.shootingPointsList.add(new Vector2(0.9f*this.playerObject.getWidth(), this.playerObject.getHeight() + 1));
        this.shootingPointObjectsList.put(ShootingPointType.LEFT, new ShootingPointObject(this.shootingPointsList.get(1),this.playerObject));
        this.shootingPointObjectsList.put(ShootingPointType.RIGHT, new ShootingPointObject(this.shootingPointsList.get(2),this.playerObject));
    }

    public void shootRayCast(){
        RaycastCallbackImpl rayCastCallback = new RaycastCallbackImpl();
        ShootingPointObject shootingPoint =  this.shootingPointObjectsList.get(ShootingPointType.MIDDLE);
        shootingPoint.updatePosition(this.playerObject);
        Vector2 shootingPointPosition = shootingPoint.getPosition();

        GameScreen.world.rayCast(rayCastCallback,shootingPointPosition.x, shootingPointPosition.y,
                shootingPointPosition.x, GameScreen.HEIGHT);
        if(rayCastCallback.getReturnFixture() != null) {
            EnemyObject enemyObject = (EnemyObject) rayCastCallback.getReturnFixture().getBody().getUserData();
            enemyObject.hit();
        }
    }

    public void shoot(){
        if(GameScreen.gameTime - this.lastShootTime < this.shootingTimeDelta) return;
        if(actualPowerUps.stream().anyMatch(powerUp -> powerUp == PowerUpsEnum.Bullets)){
            GameScreen.bulletsList.addAll(shootBullet());
        } else shootRayCast();
        this.lastShootTime = GameScreen.gameTime;
    }

    public ArrayList<BulletObject> shootBullet(){
        ArrayList<BulletObject> bulletsList = new ArrayList<>();
        for (Map.Entry<ShootingPointType, ShootingPointObject> shootingPoint : this.shootingPointObjectsList.entrySet()){
            shootingPoint.getValue().updatePosition(this.playerObject);
            bulletsList.add(new BulletObject(shootingPoint.getValue(), shootingPoint.getKey()));
        }
        return bulletsList;
    }

    public void hit(){
        this.hp--;
        if(this.hp<=0 && !this.playerObject.isAddedToDispose()) {
            this.playerObject.destroy();
            Gdx.app.exit();
        }
    }

    public void gainExp(){
        if(this.level >= this.experienceLevels.size()-1) return;

        this.experience += 100;
        long experienceToLevel = this.experienceLevels.get(this.level+1);

        if(this.experience >= experienceToLevel) this.levelUp();
    }

    private void levelUp(){
        LinkedList<PowerUpsEnum> rolledUpgrades = rollUpgrade();
        this.level++;
        GameScreen.gameStates = GameStatesEnum.PowerUpChoose;
        GameScreen.pauseTime = TimeUtils.nanoTime() * MathUtils.nanoToSec;

        GameScreen.powerUpChooseUIStage.addButtons(rolledUpgrades);
    }

    private LinkedList<PowerUpsEnum> rollUpgrade(){
        LinkedList<PowerUpsEnum> tmpPowerUpsList = new LinkedList<>(possiblePowerUps);
        LinkedList<PowerUpsEnum> resultPowerUpsList = new LinkedList<>();

        for(int i=0; i<3; i++){
            if(tmpPowerUpsList.isEmpty()) return resultPowerUpsList;
            if(!validatePowerUp(tmpPowerUpsList, resultPowerUpsList, getRandomPowerUp(tmpPowerUpsList))) i++;
        }
        return resultPowerUpsList;
    }

    private boolean validatePowerUp(LinkedList<PowerUpsEnum> powerUpsList,
                                 LinkedList<PowerUpsEnum> resultsList, PowerUpsEnum powerUp){
        switch (powerUp){
            case RayCast:
            case ShootingPointsUpgrade:
                powerUpsList.remove(powerUp);
                resultsList.add(powerUp);
                return true;
            case FasterReload:
                if(this.shootingTimeDelta >= this.minimumShootingTime) {
                    powerUpsList.remove(powerUp);
                    resultsList.add(powerUp);
                    return true;
                }
                return false;
            case SpeedUp:
                if(this.playerObject.getVELOCITY() <= this.playerObject.getMAX_VELOCITY()){
                    powerUpsList.remove(powerUp);
                    resultsList.add(powerUp);
                    return true;
                }
                return false;
            case Bullets:
                powerUpsList.remove(powerUp);
                powerUpsList.add(PowerUpsEnum.RayCast);
                resultsList.add(powerUp);
                return true;
        }
        return false;
    }

    public void activatePowerUp(PowerUpsEnum powerUp){
        switch (powerUp){
            case RayCast:
                actualPowerUps.add(powerUp);
                actualPowerUps.remove(PowerUpsEnum.Bullets);
                actualPowerUps.remove(PowerUpsEnum.ShootingPointsUpgrade);
                possiblePowerUps.remove(powerUp);
                possiblePowerUps.add(PowerUpsEnum.Bullets);
                break;
            case SpeedUp:
                this.playerObject.setVELOCITY(this.playerObject.getVELOCITY()+100);
                if(this.playerObject.getVELOCITY() >= this.playerObject.getMAX_VELOCITY())
                    this.possiblePowerUps.remove(powerUp);
                break;
            case FasterReload:
                this.shootingTimeDelta -= 0.2f;
                if(this.shootingTimeDelta <= minimumShootingTime) this.possiblePowerUps.remove(powerUp);
                break;
            case Bullets:
                actualPowerUps.add(powerUp);
                actualPowerUps.remove(PowerUpsEnum.RayCast);
                possiblePowerUps.remove(powerUp);
                possiblePowerUps.add(PowerUpsEnum.RayCast);
                break;
            case ShootingPointsUpgrade:
                this.upgradeShootingPoints();
                this.actualPowerUps.add(powerUp);
                this.possiblePowerUps.remove(powerUp);
                break;
        }
    }

    private PowerUpsEnum getRandomPowerUp(LinkedList<PowerUpsEnum> powerUpsList){
        int index = PRNG.nextInt(powerUpsList.size());
        return powerUpsList.get(index);
    }
}
