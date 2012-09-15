/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.sprite;

import com.chocoarts.component.Timer;
import com.chocoarts.debug.Debug;
import com.chocoarts.drawing.AnimatedSprite;
import dart.game.logic.EnemyGenerator;
import java.io.IOException;
import java.util.Random;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Maviosso
 */
public class Enemy extends AnimatedSprite {

    public static int BRONZE_AMOUNT = 10;
    public static int SILVER_AMOUNT = 25;
    public static int GOLD_AMOUNT = 50;
    public static int TOTAL_CHANCE = 300;
    public static int WALKING = 8765;
    public static int SUFFERRING = 9765;
    public static int DEATH = 10765;
    public static int DYING = 10766;
    public static int DECAY = 11765;
    public static int BOSS = 99999;
    public static int CREEP = 11111;
    public static Image hitEffect[];
    protected int LP;
    protected int score;
    protected int speed;
    protected int type;
    protected HealthBar LPBar;
    protected int goldChance; // on percentage
    protected int bronzeChance;
    protected int silverChance;
    protected Random coinChance;
    protected int state;
    protected boolean showEffect;
    protected int sufferringTime = 500;
    protected int deathTime = 2000;
    protected int effectTime = 120;
    protected Timer sufferringTimer;
    protected Timer deathTimer;
    protected Timer effectTimer;
    protected static Random randomizer;
    public Image currentEffect;

    public static void initialize() {
        randomizer = new Random();
        hitEffect = new Image[3];
        try {
            hitEffect[0] = Image.createImage("/effect1.png");
            hitEffect[1] = Image.createImage("/effect2.png");
            hitEffect[2] = Image.createImage("/effect3.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static Image getRandomEffect(){
        return hitEffect[randomizer.nextInt(3)];
    }
    
    public Enemy(Image image, int frameWidth, int frameHeight, int frameDuration){
        super(image, frameWidth, frameHeight, frameDuration);
        Enemy.initialize();
    }

    public Enemy(Image image, int frameWidth, int frameHeight, int frameDuration, int enemyType) {

        super(image, frameWidth, frameHeight, frameDuration);
        Enemy.initialize();
        if (enemyType == EnemyGenerator.MALING) {
            Debug.println("enemy LP=50");
            LP = 50;
            score = 10;
            goldChance = 1;
            speed = 2;
            silverChance = 5;
            bronzeChance = 10;
            type = Enemy.CREEP;
        } else if (enemyType == EnemyGenerator.MAFIA) {
            Debug.println("enemy LP=100");
            LP = 100;
            score = 20;
            speed = 2;
            goldChance = 5;
            silverChance = 15;
            bronzeChance = 30;
            type = Enemy.CREEP;
        } else if (enemyType == EnemyGenerator.KUNTILANAK) {
            Debug.println("enemy LP=200");
            LP = 200;
            score = 30;
            speed = 3;
            goldChance = 10;
            silverChance = 20;
            bronzeChance = 40;
            type = Enemy.CREEP;
        } else if (enemyType == EnemyGenerator.BOS_CICAK) {
            Debug.println("enemy LP=150");
            LP = 150;
            score = 25;
            speed = 2;
            goldChance = 8;
            silverChance = 16;
            bronzeChance = 32;
            type = Enemy.CREEP;
        }
        LPBar = new HealthBar(image, frameWidth, frameHeight, LP);
        LPBar.setPosition(this.getX(), this.getY() - HealthBar.BAR_HEIGHT);
        coinChance = new Random();
        state = WALKING;
        sufferringTimer = new Timer(sufferringTime);
        deathTimer = new Timer(deathTime);
        effectTimer = new Timer(effectTime);
        showEffect = false;   
    }

    public void update(long currentTime) {
        if (state == WALKING) {
            super.update(currentTime);
            move((speed * -1), 0);
            LPBar.move((speed * -1), 0);
        } else if (state == SUFFERRING) {


            if (sufferringTimer.isTicked(currentTime)) {
                state = WALKING;
                sufferringTimer.reset();
                this.reset();
            } else {
                this.setFrame(4);
                LPBar.move(1, 0);
                move(1, 0);
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

    public void pseudoPaint(Graphics g) {
        paint(g);
        LPBar.pseudoPaint(g);
        if (showEffect){
            g.drawImage(currentEffect, this.getX(), this.getY(), 0);
        }
    }

    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        LPBar.setPosition(this.getX(), this.getY() - HealthBar.BAR_HEIGHT);
    }

    public boolean attacked(int damage) {

        if (getLP() <= 0 && (state == DEATH || state == DYING)) {
            return false;
        }
        
        showEffect = true;
        effectTimer.reset();
        currentEffect = Enemy.getRandomEffect();
        
        state = SUFFERRING;
        this.sufferringTimer.reset();

        setLP(getLP() - damage);

        if (LP <= 0) {
            state = DYING;
            this.deathTimer.reset();
            return true;
        }
        return false;
    }

    public void setLP(int lifePoint) {
        this.LP = lifePoint;
        LPBar.setCurrentLP(LP);
    }

    public int getLP() {
        return this.LP;
    }

    public void setScore(int Score) {
        this.score = Score;
    }

    public int getScore() {
        return this.score;
    }

    public int getCoin() {
        if (coinChance.nextInt(TOTAL_CHANCE) < this.goldChance) {
            return Enemy.GOLD_AMOUNT;
        } else if (coinChance.nextInt(TOTAL_CHANCE) < this.silverChance) {
            return Enemy.SILVER_AMOUNT;
        } else if (coinChance.nextInt(TOTAL_CHANCE) < this.bronzeChance) {
            return Enemy.BRONZE_AMOUNT;
        } else {
            return 0;
        }
    }

    public int getState() {
        return state;
    }
    public void setState(int state){
        this.state = state;
    }
    
    public int getType(){
        return type;
    }
    
}
