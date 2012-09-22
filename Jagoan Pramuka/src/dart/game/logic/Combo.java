/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.logic;

import com.chocoarts.component.Timer;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author AyamChiken
 */
public class Combo {

    public static final int POS_X = 320,
            POS_Y = 240,
            SHOW_TIME = 500;
    private int comboStack;
    private int highestCombo;
    private int x1, y1, x2, y2, x3, y3;
    private boolean showEffect;
    private Timer komboTimer;
    private Image komboString,
            angka;

    public Combo(int comboStack, int highestCombo) {
        this.comboStack = comboStack;
        this.highestCombo = highestCombo;

    }

    public Combo() {
        comboStack = 0;
        highestCombo = 0;

        komboTimer = new Timer(Combo.SHOW_TIME);
        try {
            komboString = Image.createImage("/Kombo2.png");
            angka = Image.createImage("/Kombo.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        x1 = y1 = x2 = y2 = x3 = y3 = 0;

    }
    
    public void update(long currentTime){
        if (komboTimer.isTicked(currentTime)){
            showEffect = false;
        }
    }

    public void hit(int amount) {
        comboStack += amount;
        showEffect = true;
        defineRegion(comboStack);
        komboTimer.reset();
        if (highestCombo < comboStack) {
            highestCombo = comboStack;
        }
    }

    public void miss() {
        if (highestCombo < comboStack) {
            highestCombo = comboStack;
        }
        x1 = y1 = x2 = y2 = x3 = y3 = 0;
        comboStack = 0;
    }

    public int getComboStack() {
        return comboStack;
    }

    public int getHighestCombo() {
        return highestCombo;
    }

    public void pseudoPaint(Graphics g) {
        if (showEffect) {
            g.drawImage(komboString, POS_X, POS_Y - 25, Graphics.RIGHT | Graphics.BOTTOM);
            g.drawRegion(angka, x1, y1, 25, 25, Sprite.TRANS_NONE, POS_X, POS_Y, Graphics.RIGHT | Graphics.BOTTOM);
            if (!(x2 == 0 && y2 == 0 && x3 == 0 && y3 == 0)){
                g.drawRegion(angka, x2, y2, 25, 25, Sprite.TRANS_NONE, POS_X - 25, POS_Y, Graphics.RIGHT | Graphics.BOTTOM);
            }
            if (!(x3==0 && y3==0)){
                g.drawRegion(angka, x3, y3, 25, 25, Sprite.TRANS_NONE, POS_X - 50, POS_Y, Graphics.RIGHT | Graphics.BOTTOM);
            }
        }

        //g.setColor(192, 192, 192);
        //g.fillRect(getX(), getY(), BAR_WIDTH, BAR_HEIGHT);

        //g.setColor(128, 255, 0);
        //g.fillRect(getX(), getY(), currentLPWidth, BAR_HEIGHT);
    }

    public void defineRegion(int combo) {
        String rep = combo + "";
        if (rep.length() >= 3) {
            x3 = getXreg(rep.charAt(0) + "");
            y3 = getYreg(rep.charAt(0) + "");
        }
        if (rep.length() >= 2) {
            x2 = getXreg(rep.charAt(rep.length() - 2) + "");
            y2 = getYreg(rep.charAt(rep.length() - 2) + "");
        }
        if (rep.length() >= 1) {
            x1 = getXreg(rep.charAt(rep.length() - 1) + "");
            y1 = getYreg(rep.charAt(rep.length() - 1) + "");
        }
    }

    public int getXreg(String value) {
        if (value.equals("0") || value.equals("5")) {
            return 0;
        } else if (value.equals("1") || value.equals("6")) {
            return 25;
        } else if (value.equals("2") || value.equals("7")) {
            return 50;
        } else if (value.equals("3") || value.equals("8")) {
            return 75;
        } else if (value.equals("4") || value.equals("9")) {
            return 100;
        } else {
            return 0;
        }
    }

    public int getYreg(String value) {
        if (value.equals("0") || value.equals("1") || value.equals("2") || value.equals("3") || value.equals("4")) {
            return 0;
        } else {
            return 25;
        }
    }
}
