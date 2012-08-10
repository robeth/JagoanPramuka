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
 * @author Maviosso
 */
public class AttackArea extends ChocoSprite {

    public static final int CHARGING_STATE = 101;
    public static final int DAMAGE_STATE = 102;
    public static final int NONE_STATE = 103;
    private int state;
    private int attackDamage;
    private int maxIncrement;
    private int incrementDuration;
    private int incrementLength;
    private long startTime;
    private int initWidth;
    private int initHeight;
    private int currentWidth;
    private int currentHeight;

    public AttackArea(Image image, int width, int height, int initWidth, int initHeight) {
        super(image, width, height);
        this.initWidth = initWidth;
        this.initHeight = initHeight;
        currentWidth = initWidth;
        currentHeight = initHeight;
        state = NONE_STATE;
    }

    public void pseudoPaint(Graphics g) {
        if(state == CHARGING_STATE){
            g.setColor(255, 0, 0);
            g.drawRect(getX(), getY(), currentWidth+4, currentHeight+4);

            //g.setColor(0, 255, 0);
            //g.fillRect(getX()+2, getY()+2, currentWidth, currentHeight);
        } 
    }

    public void updateCharge(long currentTime) {

        int stack = (int) (System.currentTimeMillis() - startTime) / incrementDuration;
        Debug.println("Stack:"+stack);
        if (stack > maxIncrement) {
            stack = maxIncrement;
        }
        currentWidth = initWidth + stack * incrementLength;

    }

    public void startCharging(long startTime) {
        state = CHARGING_STATE;
        this.startTime = startTime;
    }

    public void endCharging() {
        state = NONE_STATE;
    }

    public void reset() {
        state = NONE_STATE;
        currentWidth = initWidth;
        currentHeight = initHeight;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getIncrementDuration() {
        return incrementDuration;
    }

    public void setIncrementDuration(int incrementDuration) {
        this.incrementDuration = incrementDuration;
    }

    public int getIncrementLength() {
        return incrementLength;
    }

    public void setIncrementLength(int incrementLength) {
        this.incrementLength = incrementLength;
    }

    public int getMaxIncrement() {
        return maxIncrement;
    }

    public void setMaxIncrement(int maxIncrement) {
        this.maxIncrement = maxIncrement;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getState() {
        return state;
    }

    public boolean isCharging() {
        return state == CHARGING_STATE;
    }

    public boolean canDamage(Enemy e) {
        return (
                ((getX() < e.getX()) && (getX()+currentWidth > e.getX())) ||
                ((getX() < e.getX()+e.getWidth()) && (getX()+currentWidth > e.getX() + e.getWidth()))
                ); 
    }
}
