/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.sprite;

import com.chocoarts.component.Timer;
import com.chocoarts.drawing.ChocoSprite;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Maviosso
 */
public class Arrow extends ChocoSprite{
    private int animationDistanceX;
    private int animationDistanceY;
    private long animationTime;
    private boolean goingBack;
    private long startTime;
    private int initX, initY;
    
    public Arrow(Image image, int initX, int initY,long animationTime, int animationDistanceX, int animationDistanceY, boolean goingBack){
        super(image);
        this.animationDistanceX = animationDistanceX;
        this.animationDistanceY = animationDistanceY;
        this.animationTime = animationTime;
        this.goingBack = goingBack;
        this.initX = initX;
        this.initY = initY;
    }
    private int destX, sourceX, destY, sourceY;
    
    public void update(long currentTime){
        if(currentTime - startTime > animationTime){
            goingBack = !goingBack;
            startTime = currentTime;
            destX = (goingBack) ? initX : initX + animationDistanceX;
            sourceX = (goingBack) ? initX + animationDistanceX : initX;
            destY = (goingBack) ? initY : initY + animationDistanceY;
            sourceY = (goingBack) ? initY + animationDistanceY : initY;
        } else {
            float progress = ((float)(currentTime - startTime)) / ((float)animationTime); 
            int displacementX = interpolate(sourceX, destX, progress);
            int displacementY = interpolate(sourceY, destY, progress);
            setPosition(displacementX, displacementY);
        }
    }
    
    public void initiate(long currentTime){
        startTime = currentTime;
    }
    
    private static int interpolate(int source, int destination, float progress) {
        return source + ((int) ((destination - source) * progress));
    }
}
