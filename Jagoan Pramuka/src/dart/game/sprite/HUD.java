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
    private int money;
    private Image animal1, animal2, animal3, comboBar;
    
    
    public Image getAnimal1() {
        return animal1;
    }

    public Image getAnimal2() {
        return animal2;
    }

    public Image getAnimal3() {
        return animal3;
    }
    
    public void update (Combo comboObj, FinalAttack faObj){
        this.combo = comboObj.getComboStack();
        this.bar = faObj.getBar();
       
    }
    
    public HUD(CustomFont scoreFont, CustomFont comboFont, int money) {
        this.scoreFont = scoreFont;
        this.comboFont = comboFont;
        this.money = money;
        this.life = 3;
         try {
            animal1 = Image.createImage("/1_ayam.png");
            animal2 = Image.createImage("/2_kambing.png");
            animal3 = Image.createImage("/3_sapi.png");
            comboBar = Image.createImage("/combo.png");
        } catch (IOException ex) {
                
            ex.printStackTrace();
        }
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
        scoreFont.paintString(g, "Coin: "+money, 220, 20, Graphics.LEFT|Graphics.TOP);
        
        int barLength = (int) Math.ceil(bar * (0.8));
        g.setColor(255,0,0);
        g.fillRect(28, 230,barLength, 10);
        
        g.drawImage(comboBar,0, 240, Graphics.LEFT | Graphics.BOTTOM);
        comboFont.paintString(g, Integer.toString(combo), 15, 220, Graphics.LEFT|Graphics.TOP);
        
        //comboFont.paintString(g, "BarAttack: "+bar, 220, 40, Graphics.LEFT|Graphics.TOP);
        if (life >= 1){
            g.drawImage(animal1, 10, 0, 0);
        }
        if (life >= 2){
            g.drawImage(animal2, 30, 1, 0);
        }
        if (life >= 3) {
            g.drawImage(animal3, 50, 2, 0);
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
    
    public int getMoney(){
        return money;
    }
    
    public void incMoney(int money){
        this.money += money;
    }
}
