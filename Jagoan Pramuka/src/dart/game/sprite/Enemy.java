/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.sprite;

import com.chocoarts.debug.Debug;
import com.chocoarts.drawing.AnimatedSprite;
import dart.game.logic.EnemyGenerator;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Maviosso
 */
public class Enemy extends AnimatedSprite {
    
    private int LP;
    private int score;
    private int speed;
    private int Type;
    private HealthBar LPBar;

    public Enemy(Image image, int frameWidth, int frameHeight, int frameDuration,int enemyType) { 
        
        super(image, frameWidth, frameHeight, frameDuration);
        this.Type = enemyType;
        if (enemyType == EnemyGenerator.ALIEN){
            Debug.println("enemy LP=100");
            LP = 100;
            score = 10;
        } else if (enemyType == EnemyGenerator.ALIEN2){
            Debug.println("enemy LP=200");
            LP = 200;
            score = 20;
        } else if (enemyType == EnemyGenerator.ALIEN3){
            Debug.println("enemy LP=300");
            LP = 300;
            score = 30;
        }
        LPBar = new HealthBar(image,frameWidth,frameHeight,LP);
        LPBar.setPosition(this.getX(), this.getY()-HealthBar.BAR_HEIGHT);
    }

    public void update(long currentTime) {
        super.update(currentTime);
        move(-1, 0);
        LPBar.move(-1, 0);
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
}
