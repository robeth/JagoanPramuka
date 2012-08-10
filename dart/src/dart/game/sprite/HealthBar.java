/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.sprite;

import com.chocoarts.debug.Debug;
import com.chocoarts.drawing.ChocoSprite;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author AyamChiken
 */
public class HealthBar extends ChocoSprite {
    
    public static final int BAR_WIDTH = 34;
    public static final int BAR_HEIGHT = 5;
    private int lifePoint;
    private int currentLP;
    private int currentLPWidth;
    
    public HealthBar(Image image, int width, int height, int lifePoint) {
        super(image, width, height);
        this.lifePoint = lifePoint;
        currentLP = lifePoint;
        currentLPWidth = BAR_WIDTH;
    }
    
    public void pseudoPaint(Graphics g) {
        g.setColor(192, 192, 192);
        g.fillRect(getX(), getY(), BAR_WIDTH, BAR_HEIGHT);

        g.setColor(128, 255, 0);
        g.fillRect(getX(), getY(), currentLPWidth, BAR_HEIGHT);
    }
    
    public void calculateWidth(){
        currentLPWidth = (currentLP * BAR_WIDTH) / lifePoint;
    }
    
    public void setCurrentLP(int LP){
        this.currentLP = LP;
        calculateWidth();
    }
    
}
