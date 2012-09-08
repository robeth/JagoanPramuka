/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.sprite;

import com.chocoarts.component.Timer;
import com.chocoarts.debug.Debug;
import com.chocoarts.drawing.ChocoSprite;
import java.io.IOException;
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
    private int stack;
    private Timer timer;
    private Image tile1;
    private Image tile2;
    private int tileState;
    private static final int ONE = 1, TWO = 2;
    private boolean charging;

    

    public AttackArea(Image image, int width, int height, int initWidth, int initHeight) {
        super(image, width, height);
        this.initWidth = initWidth;
        this.initHeight = initHeight;
        currentWidth = initWidth;
        currentHeight = initHeight;
        state = NONE_STATE;
        timer = new Timer(250);
        tileState = ONE;
        charging = false;
        try {
            tile1 = Image.createImage("/til1.png");
            tile2 = Image.createImage("/til2.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void pseudoPaint(Graphics g) {
        if (state == CHARGING_STATE && charging) {
            for (int i = 0; i < stack + 1; i++) {
                g.drawImage((tileState == ONE) ? tile1 : tile2, i * incrementLength + getX(), getY(), Graphics.TOP | Graphics.LEFT);
            }
        }
    }

    public void updateCharge(long currentTime) {
        float floatStack =(System.currentTimeMillis() - startTime) / incrementDuration; 
        stack = (int) floatStack;
        if(!charging) charging = floatStack > 0.5f;
        
        Debug.println("Stack:" + stack);
        if (stack > maxIncrement) {
            stack = maxIncrement;
        }
        currentWidth = initWidth + stack * incrementLength;

        if (timer.isTicked(currentTime)) {
            timer.reset();
            tileState = (tileState == ONE) ? TWO : ONE;
        }
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
        stack = 0 ;
        charging = false;
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

    public int getStack() {
        return stack;
    }

    public boolean canDamage(Enemy e, int chargedCount) {
        return (((getX() + chargedCount * 50 < e.getX()) && (getX() + (chargedCount + 1) * 50 > e.getX()))
                || ((getX() + chargedCount * 50 < e.getX() + 20) && (getX() + (chargedCount + 1) * 50 > e.getX() + 20)));
    }
    
    public boolean isChargingAttack() {
        return charging;
    }

    public void setIsChargingAttack(boolean startCharge) {
        this.charging = startCharge;
    }
}
