/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.sprite;

import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Maviosso
 */
public class StarEffect {
    private static Image starImage;
    private int startX;
    private int startY;
    private long fadeTime;
    private int fadeDistance;
    private boolean active;
    private long activationTime;
    private long deltaTime;
    
    public StarEffect(int startX, int startY,int fadeTime, int fadeDistance){
        this.startX = startX;
        this.startY = startY;
        this.fadeTime = fadeTime;
        this.fadeDistance = fadeDistance;
    }
    
    public void update(long currentTime){
        if(active){
            deltaTime = Math.min(fadeTime, currentTime-activationTime);
            if(deltaTime == fadeTime) {
                reset();
            }
        }
    }
    
    public void activate(long currentTime){
        active = true;
        activationTime = currentTime;
    }
    public void paint(Graphics g){
        //bintang ke kanan
        if(active){
            float progress = (float) deltaTime / (float)fadeTime* (float)fadeDistance ;
            g.drawImage(getStarImage(), startX+(int)progress, startY, Graphics.LEFT | Graphics.TOP);
            g.drawImage(getStarImage(), startX+(int)(progress * 0.8), startY+((int)(progress*0.25)), Graphics.LEFT | Graphics.TOP);
            g.drawImage(getStarImage(), startX+(int)(progress*0.8), startY-((int)(progress*0.25)), Graphics.LEFT | Graphics.TOP);
        }
    }
    
    public void reset(){
        active = false;
    }
    
    private static Image getStarImage(){
        if(starImage == null){
            try {
                starImage = Image.createImage("/bintang.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return starImage;
    }

    public int getFadeDistance() {
        return fadeDistance;
    }

    public void setFadeDistance(int fadeDistance) {
        this.fadeDistance = fadeDistance;
    }

    public long getFadeTime() {
        return fadeTime;
    }

    public void setFadeTime(long fadeTime) {
        this.fadeTime = fadeTime;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }
}
