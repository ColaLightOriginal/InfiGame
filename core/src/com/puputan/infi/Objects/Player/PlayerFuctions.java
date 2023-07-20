package com.puputan.infi.Objects.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.puputan.infi.Objects.Bullet.BulletObject;
import com.puputan.infi.Objects.Bullet.ShootingPointType;
import com.puputan.infi.Objects.ShootingPointObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerFuctions {

    private PlayerObject playerObject;

    private int hp = 10;
    private int experience = 0;
    private int level = 0;
    private final HashMap<Integer, Long> experienceLevels = new HashMap<>();
    private final Map<ShootingPointObject, ShootingPointType> shootingPointObjectsList;
    private ArrayList<Vector2> shootingPointsList;


    public PlayerFuctions(PlayerObject playerObject){
        this.playerObject = playerObject;
        this.shootingPointObjectsList = new HashMap<>();
        initializeLevels();
        initializeShootingPoints();
    }

    public void initializeLevels(){
        this.experienceLevels.put(0, 0L);
        this.experienceLevels.put(1, 100L);
        this.experienceLevels.put(2, 1000L);
        this.experienceLevels.put(3, 2000L);
        this.experienceLevels.put(4, 3500L);
        this.experienceLevels.put(5, 7000L);
    }

    public void initializeShootingPoints(){
        this.shootingPointsList = new ArrayList<>();
        this.shootingPointsList.add(new Vector2(this.playerObject.getWidth()/2, this.playerObject.getHeight() + 1));
        this.shootingPointObjectsList.put(new ShootingPointObject(this.shootingPointsList.get(0),this.playerObject), ShootingPointType.MIDDLE);
    }

    public void upgradeShootingPoints(){
        this.shootingPointsList.add(new Vector2(0.1f*this.playerObject.getWidth(),  this.playerObject.getHeight() + 1));
        this.shootingPointsList.add(new Vector2(0.9f*this.playerObject.getWidth(), this.playerObject.getHeight() + 1));
        this.shootingPointObjectsList.put(new ShootingPointObject(this.shootingPointsList.get(1),this.playerObject), ShootingPointType.LEFT );
        this.shootingPointObjectsList.put(new ShootingPointObject(this.shootingPointsList.get(2),this.playerObject), ShootingPointType.RIGHT);
    }

    public ArrayList<BulletObject> shoot(){
        ArrayList<BulletObject> bulletsList = new ArrayList<>();
        for (Map.Entry<ShootingPointObject, ShootingPointType> shootingPoint : this.shootingPointObjectsList.entrySet()){
            shootingPoint.getKey().updatePosition(shootingPoint.getKey().getOffset(), this.playerObject);
            bulletsList.add(new BulletObject(shootingPoint.getKey(), shootingPoint.getValue()));
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
        this.experience += 100;
        this.checkLevel();
    }

    private void checkLevel(){
        long experienceToLevel = this.experienceLevels.get(this.level+1);
        if(this.experience >= experienceToLevel) this.levelUp();
    }

    private void levelUp(){
        this.level++;
        this.upgradeShootingPoints();
    }
}
