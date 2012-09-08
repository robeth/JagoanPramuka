/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.sprite;

import com.chocoarts.component.Timer;
import com.chocoarts.debug.Debug;
import com.chocoarts.drawing.AnimatedSprite;
import dart.game.logic.EnemyGenerator;
import java.util.Random;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Maviosso
 */
public class Enemy extends AnimatedSprite {
    
    public static int BRONZE_AMOUNT = 10;
    public static int SILVER_AMOUNT = 50;
    public static int GOLD_AMOUNT = 100;
    public static int WALKING = 8765;
    public static int SUFFERRING = 9765;
    
    private int LP;
    private int score;
    private int speed;
    private int Type;
    private HealthBar LPBar;
    private int goldChance; // on percentage
    private int bronzeChance;
    private int silverChance;
    private Random coinChance;
    private int state;
    private int sufferringTime = 500;
    private Timer sufferringTimer;
    

    public Enemy(Image image, int frameWidth, int frameHeight, int frameDuration,int enemyType) { 
        
        super(image, frameWidth, frameHeight, frameDuration);
        this.Type = enemyType;
        if (enemyType == EnemyGenerator.MALING){
            Debug.println("enemy LP=50");
            LP = 50;
            score = 10;
            goldChance = 1;
            silverChance = 5;
            bronzeChance = 10;
        } else if (enemyType == EnemyGenerator.KOLORIJO){
            Debug.println("enemy LP=100");
            LP = 100;
            score = 20;
            goldChance = 5;
            silverChance = 15;
            bronzeChance = 30;
        } else if (enemyType == EnemyGenerator.MAFIA){
            Debug.println("enemy LP=150");
            LP = 200;
            score = 30;
            goldChance = 10;
            silverChance = 20;
            bronzeChance = 40;
        }
        LPBar = new HealthBar(image,frameWidth,frameHeight,LP);
        LPBar.setPosition(this.getX(), this.getY()-HealthBar.BAR_HEIGHT);
        coinChance = new Random();
        state= WALKING;
        sufferringTimer = new Timer(sufferringTime);
        
    }
    

    public void update(long currentTime) {
        if(state == WALKING){
            super.update(currentTime);
            move(-1, 0);
            LPBar.move(-1, 0);
        } else if(state == SUFFERRING){
            
            
            if(sufferringTimer.isTicked(currentTime)){
                state = WALKING;
                sufferringTimer.reset();
            } else {
                this.setFrame(4);
                LPBar.move(1, 0);
                move(1,0);
            }
        }
    }
    
    public void pseudoPaint(Graphics g){
        paint(g);
        LPBar.pseudoPaint(g);
    }
    
    public void setPosition(int x, int y){
        super.setPosition(x, y);
        LPBar.setPosition(this.getX(), this.getY()-HealthBar.BAR_HEIGHT);
    }
    
    public boolean attacked (int damage) {
        state =SUFFERRING;
        
        setLP(getLP()-damage);
        if (LP <= 0){
            return true;
        }     
        return false;
    }
    
    public void setLP (int lifePoint) {
        this.LP = lifePoint;
        LPBar.setCurrentLP(LP);
    }
    
    public int getLP (){
        return this.LP;
    }
    
    public void setScore (int Score) {
        this.score = Score;
    }
    
    public int getScore (){
        return this.score;
    }
    
    public int getCoin(){
        if (coinChance.nextInt(100) < this.goldChance){
            return Enemy.GOLD_AMOUNT;
        } else if (coinChance.nextInt(100) < this.silverChance){
            return Enemy.SILVER_AMOUNT;
        } else if (coinChance.nextInt(100) < this.bronzeChance){
            return Enemy.BRONZE_AMOUNT;
        } else {
            return 0;
        }
    }
}
