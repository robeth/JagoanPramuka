/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.sprite;

import com.chocoarts.debug.Debug;
import com.chocoarts.text.CustomFont;
import javax.microedition.lcdui.Graphics;
import dart.game.logic.*;
import java.io.IOException;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Maviosso
 */
public class HUD {
    private CustomFont scoreFont;
    private CustomFont comboFont;
    private int score;
    private int combo;
    private int bar;
    private int life;
    
    public void update (Combo comboObj, FinalAttack faObj){
        this.combo = comboObj.getComboStack();
        this.bar = faObj.getBar();
        //Debug.println("on HUD" + comboObj.getComboStack() );
    }
    
    public HUD(CustomFont scoreFont, CustomFont comboFont) {
        this.scoreFont = scoreFont;
        this.comboFont = comboFont;
        this.life = 3;
    }
    
    public void setScore(int score){
        this.score = score;
    }
    
    public void incScore(int score){
        this.score += score;
    }
    
    public void setCombo(int combo){
        this.combo = combo;
    }
    
    public void setBar(int amount){
        this.bar = amount;
    }
    
    public int getScore(){
        return score;
    }
    
    public int getLife(){
        return life;
    }
    
    public void paint(Graphics g) throws IOException{
        scoreFont.paintString(g, "Score: "+score, 220, 0, Graphics.LEFT|Graphics.TOP);
        comboFont.paintString(g, "Combo: "+combo, 220, 20, Graphics.LEFT|Graphics.TOP);
        comboFont.paintString(g, "BarAttack: "+bar, 220, 40, Graphics.LEFT|Graphics.TOP);
        if (life >= 1){
            g.drawImage(Image.createImage("/1_ayam.png"), 10, 0, 0);
        }
        if (life >= 2){
            g.drawImage(Image.createImage("/2_kambing.png"), 30, 1, 0);
        }
        if (life >= 3) {
            g.drawImage(Image.createImage("/3_sapi.png"), 50, 2, 0);
        }
    } 
    
    public boolean stealed(){
        if (life > 0){
            life -= 1;
        }
        if (life == 0){
            return true;
        } else {
            return false;
        }
    }
}
