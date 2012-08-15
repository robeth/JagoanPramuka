/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.sprite;

import com.chocoarts.drawing.ChocoSprite;
import javax.microedition.lcdui.Image;

/**
 *
 * @author AyamChiken
 */
public class CoinEffect extends ChocoSprite{
    public static int BRONZE = 2001;
    public static int SILVER = 2002;
    public static int GOLD = 2003;
    int amount;
    int ySpeed;
    int xSpeed;
    //220 dan 20 adalah posisi coin
    
    public CoinEffect(Image image, int width, int height,int amount) {
        super(image, width, height);
        ySpeed = -6;
        this.getXSpeed();
        this.amount = amount;
    }
    
     public void update(long currentTime) {
        if (this.getX() < 220) {
            move(getXSpeed(), 0);
        }
        if (this.getY() > 20) {
            move(0,ySpeed);
        }
    }
     
     private int getXSpeed (){
         int xDistance = 220 - this.getX();
         int yDistance = this.getY() - 20;
         
         xSpeed = (xDistance/yDistance) * ySpeed * -1;
         
         return xSpeed;
     }
     
     public int getAmount(){
         return this.amount;
     }
}
