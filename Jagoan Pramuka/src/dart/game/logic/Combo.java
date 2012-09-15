/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.logic;

import java.util.Timer;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author AyamChiken
 */
public class Combo {
    
    public static int posX,
                      posY;
    private int comboStack;
    private int highestCombo;
    
    private boolean showEffect;
    private Timer komboTimer;

    public Combo(int comboStack, int highestCombo) {
        this.comboStack = comboStack;
        this.highestCombo = highestCombo;
    }
    
    
    public Combo (){
        comboStack = 0;
        highestCombo = 0;
    }
    
    public void hit (int amount){
        comboStack += amount;
        showEffect = true;
        if (highestCombo<comboStack){
            highestCombo = comboStack;
        }
    }
    
    public void miss (){
        
        comboStack = 0;
    }
    
    public int getComboStack(){
        return comboStack;
    }
    
    public int getHighestCombo(){
        return highestCombo;
    }
    
    public void pseudoPaint(Graphics g) {
        
        
        g.setColor(192, 192, 192);
        //g.fillRect(getX(), getY(), BAR_WIDTH, BAR_HEIGHT);

        g.setColor(128, 255, 0);
        //g.fillRect(getX(), getY(), currentLPWidth, BAR_HEIGHT);
    }
    
}
