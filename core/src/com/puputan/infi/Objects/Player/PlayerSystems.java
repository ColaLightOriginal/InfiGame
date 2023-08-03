package com.puputan.infi.Objects.Player;

import Screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.puputan.infi.Objects.Enemy.EnemyObject;
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

    public PlayerSystems(PlayerObject playerObject){
        this.playerObject = playerObject;
        this.shootingPointObjectsList = new HashMap<>();
        this.actualPowerUps = new LinkedList<>();
        this.possiblePowerUps = new LinkedList<>();

        initializeLevels();
        initializeShootingPoints();
        initializePowerUps();
    }

    public void initializeLevels(){
        this.experienceLevels.put(0, 0L);
        this.experienceLevels.put(1, 100L);
        this.experienceLevels.put(2, 200L);
        this.experienceLevels.put(3, 300L);
        this.experienceLevels.put(4, 400L);
        this.experienceLevels.put(5, 500L);
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
        if(actualPowerUps.stream().anyMatch(powerUp -> powerUp == PowerUpsEnum.Bullets)){
            GameScreen.bulletsList.addAll(shootBullet());
        } else shootRayCast();
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
            this.playerObject.setAddedToDispose(true);
            this.playerObject.addToDispose(this.playerObject.getBody());
            Gdx.app.exit();
        }
    }

    public void gainExp(){
        if(this.level >= this.experienceLevels.get(this.experienceLevels.size()-1)) return;
        this.experience += 100;
        this.checkLevel();
    }

    private void checkLevel(){
        long experienceToLevel = this.experienceLevels.get(this.level+1);
        if(this.experience >= experienceToLevel) this.levelUp();
    }

    private void levelUp(){
        this.level++;
        rollUpgrade();
    }

    private void rollUpgrade(){
        LinkedList<PowerUpsEnum> tmpPowerUpsList = new LinkedList<>(possiblePowerUps);
        PowerUpsEnum powerUpsEnum;

        for(int i =0; i < 3; i++) {
            powerUpsEnum = getRandomPowerUp(tmpPowerUpsList);
            tmpPowerUpsList = validatePowerUp(tmpPowerUpsList, powerUpsEnum);
        }

        System.out.println(tmpPowerUpsList);
        this.activatePowerUp(tmpPowerUpsList.get(0));
    }

    private LinkedList<PowerUpsEnum> validatePowerUp(LinkedList<PowerUpsEnum> powerUpsList, PowerUpsEnum powerUp){
        switch (powerUp){
            case RayCast:
                possiblePowerUps.remove(powerUp);
                break;
            case SpeedUp:
                if(this.playerObject.getVELOCITY() >= 1000) powerUpsList.remove(PowerUpsEnum.SpeedUp);
                break;
            case Bullets:
                powerUpsList.remove(PowerUpsEnum.Bullets);
                powerUpsList.add(PowerUpsEnum.RayCast);
                break;
            case ShootingPointsUpgrade:
                powerUpsList.remove(PowerUpsEnum.ShootingPointsUpgrade);
        }
        return powerUpsList;
    }

    private void activatePowerUp(PowerUpsEnum powerUp){
        switch (powerUp){
            case RayCast:
                actualPowerUps.add(powerUp);
                possiblePowerUps.remove(powerUp);
                break;
            case SpeedUp:
                this.playerObject.setVELOCITY(this.playerObject.getVELOCITY()+100);
                if(this.playerObject.getVELOCITY() >= 1000) this.possiblePowerUps.remove(PowerUpsEnum.SpeedUp);
                break;
            case Bullets:
                actualPowerUps.add(PowerUpsEnum.Bullets);
                actualPowerUps.remove(PowerUpsEnum.RayCast);
                possiblePowerUps.remove(PowerUpsEnum.Bullets);
                possiblePowerUps.add(PowerUpsEnum.RayCast);
                break;
            case ShootingPointsUpgrade:
                this.upgradeShootingPoints();
                this.actualPowerUps.add(PowerUpsEnum.ShootingPointsUpgrade);
                this.possiblePowerUps.remove(PowerUpsEnum.ShootingPointsUpgrade);
        }
    }

    private PowerUpsEnum getRandomPowerUp(LinkedList<PowerUpsEnum> powerUpsList){
        int index = PRNG.nextInt(powerUpsList.size());
        return powerUpsList.get(index);
    }
}
