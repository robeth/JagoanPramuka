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
    private Image animal1, animal2, animal3, animal1fail, animal2fail, animal3fail, comboBar;

    public Image getAnimal1() {
        return animal1;
    }

    public Image getAnimal2() {
        return animal2;
    }

    public Image getAnimal3() {
        return animal3;
    }

    public Image getAnimal1Fail() {
        return animal1fail;
    }

    public Image getAnimal2Fail() {
        return animal2fail;
    }

    public Image getAnimal3Fail() {
        return animal3fail;
    }

    public void update(Combo comboObj, FinalAttack faObj) {
        this.combo = comboObj.getComboStack();
        this.bar = faObj.getBar();

    }

    public HUD(CustomFont scoreFont, CustomFont comboFont, int money, String animalS1,String animalS2,String animalS3,
            String animal_no1, String animal_no2, String animal_no3) {
        this.scoreFont = scoreFont;
        this.comboFont = comboFont;
        this.money = money;
        this.life = 3;
        try {
            animal1 = Image.createImage(animalS1);
            animal2 = Image.createImage(animalS2);
            animal3 = Image.createImage(animalS3);
            animal1fail = Image.createImage(animal_no1);
            animal2fail = Image.createImage(animal_no2);
            animal3fail = Image.createImage(animal_no3);
            comboBar = Image.createImage("/combo.png");
        } catch (IOException ex) {

            ex.printStackTrace();
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void incScore(int score) {
        this.score += score;
    }

    public void setCombo(int combo) {
        this.combo = combo;
    }

    public void setBar(int amount) {
        this.bar = amount;
    }

    public int getScore() {
        return score;
    }

    public int getLife() {
        return life;
    }

    public void paint(Graphics g) throws IOException {
        scoreFont.paintString(g, "Score: " + score, 220, 0, Graphics.LEFT | Graphics.TOP);
        scoreFont.paintString(g, "Coin: " + money, 220, 20, Graphics.LEFT | Graphics.TOP);

        int barLength = (int) Math.ceil(bar * (0.8));
        g.setColor(255, 0, 0);
        g.fillRect(28, 230, barLength, 10);

        g.drawImage(comboBar, 0, 240, Graphics.LEFT | Graphics.BOTTOM);
        comboFont.paintString(g, Integer.toString(combo), 15, 220, Graphics.LEFT | Graphics.TOP);

        //comboFont.paintString(g, "BarAttack: "+bar, 220, 40, Graphics.LEFT|Graphics.TOP);

        g.drawImage(life >= 1 ? animal1 : animal1fail, 10, 0, 0);
        g.drawImage(life >= 2 ? animal2 : animal2fail, 42, 0, 0);
        g.drawImage(life >= 3 ? animal3 : animal3fail, 74, 0, 0);

    }

    public boolean stealed() {
        if (life > 0) {
            life -= 1;
        }
        if (life == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getMoney() {
        return money;
    }

    public void incMoney(int money) {
        this.money += money;
    }
}
