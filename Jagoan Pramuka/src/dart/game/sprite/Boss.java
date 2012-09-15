/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.sprite;

import com.chocoarts.component.Timer;
import com.chocoarts.debug.Debug;
import dart.game.logic.EnemyGenerator;
import dart.game.logic.Lanes;
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Image;

/**
 *
 * @author AyamChiken
 */
public class Boss extends Enemy {
    
    private Vector[] enemyLanes;
    private int newPosY;
    private int newLane;
    private Random randomizer;
    private Lanes lanes;
    private int xSpeed;
    private int ySpeed;
    
    public Boss(Image image, int frameWidth, int frameHeight, int frameDuration, int enemyType,Vector[] enemyLanes, Lanes lanes) {

        super(image, frameWidth, frameHeight, frameDuration);
        Enemy.initialize();
        
        this.enemyLanes = enemyLanes;
        this.lanes = lanes;
        
        LP = 1000;
        score = 25;
        speed = 2;
        goldChance = 8;
        silverChance = 16;
        bronzeChance = 32;
        type = Enemy.BOSS;
        
        LPBar = new HealthBar(image, frameWidth, frameHeight, LP);
        LPBar.setPosition(this.getX(), this.getY() - HealthBar.BAR_HEIGHT);
        coinChance = new Random();
        state = WALKING;
        deathTimer = new Timer(deathTime);
        effectTimer = new Timer(effectTime);
        sufferringTimer = new Timer(sufferringTime);
        showEffect = false;   
        
        randomizer = new Random();
        
        ySpeed = -1;
    }
    
    public boolean attacked(int damage) {

        if (getLP() <= 0 && (state == DEATH || state == DYING)) {
            return false;
        }
        
        showEffect = true;
        effectTimer.reset();
        currentEffect = Enemy.getRandomEffect();
        
        state = SUFFERRING;
        this.newPosY = lanes.getY(newLane) - 18;
        System.out.println("newlane accepted = " + newLane );
        this.getYSpeed();

        setLP(getLP() - damage);
        
        if (LP <= 0) {
            state = DYING;
            this.deathTimer.reset();
            return true;
        }
        
        
        return false;
    }
    
    public void update(long currentTime) {
        if (state == WALKING) {
            super.update(currentTime);
        } else if (state == SUFFERRING) {
            if (this.getX() >= 260 && this.getY() == this.newPosY) {
                this.reset();
                state = WALKING;
            } else {
                this.setFrame(4);
                //LPBar.move(1, 0);
                //move(1, 0);
                if (this.getX() < 260) {
                    LPBar.move(getXSpeed(), 0);
                    move(getXSpeed(), 0);
                }
                if (this.getY() != this.newPosY) {
                    LPBar.move(0,ySpeed);
                    move(0,ySpeed);
                }
            }
        } else if (state == DEATH) {
            if (deathTimer.isTicked(currentTime)) {
                state = DECAY;
                deathTimer.reset();
            } else {
                this.setFrame(5);
                LPBar.setVisible(false);
            }
        } else if (state == DYING) {
            if (sufferringTimer.isTicked(currentTime)) {
                state = DEATH;
                sufferringTimer.reset();
            } else {
                this.setFrame(4);
                LPBar.move(1, 0);
                move(1, 0);
            }
        }
        if (showEffect && effectTimer.isTicked(currentTime)){
            showEffect = false;
        }
    }
    
    public int changeLane(){
        newLane = randomizer.nextInt(3);
        
        return newLane;
    }
    
    
    private int getCurrentLane(){
        if (this.getY() == lanes.getY(0)){
            return 0;
        } else if (this.getY() == lanes.getY(1)){
            return 1;
        } else if (this.getY() == lanes.getY(2)){
            return 2;
        } else {
            return 0;
        }
    }

    private int getXSpeed() {
         int xDistance = 260 - this.getX();
         int yDistance = Math.abs(this.getY() - this.newPosY);
         if (yDistance == 0){
             return 6;
         }
         
         xSpeed = Math.abs((xDistance/yDistance) * ySpeed);
         
         return xSpeed;
    }
    
    public void getYSpeed() {
        if (this.getY() == this.newPosY){
            this.ySpeed = 0;
        } else if (this.getY() - this.newPosY > 0){
            this.ySpeed = -1;
        } else {
            this.ySpeed = 1;
        }
    }
    
}
