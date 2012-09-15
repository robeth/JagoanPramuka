/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.scene;

import com.chocoarts.Engine;
import com.chocoarts.debug.Debug;
import com.chocoarts.scene.Scene;
import com.chocoarts.text.CustomFont;
import dart.game.data.ItemDatabase;
import dart.game.data.Weapon;
import dart.game.main.MainProfile;
import dart.game.sprite.Color;
import dart.game.sprite.DummyButton2;
import dart.game.sprite.ItemButton;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

/**
 *
 * @author Maviosso
 */
public class ShopScreen extends Scene {

    private CustomFont berlin;
    private MainProfile profile;
    private int windowState;
    private static final int BROWSE_STATE = 0,
            CONFIRM_BUY_STATE = 1,
            CONFIRM_EQUIP_STATE = 2,
            MENU_UTAMA = 3;
    private int cursorState;
    private int prevCursorState;
    private static final int KITCHEN_SET = 0,
            TONGKAT = 1,
            PEDANG = 2;
    private Weapon weapons[];
    private boolean isOK;
    private Image padlockImage, cursorImage, background, boxImage, yesImage, cancelImage;
    

    public ShopScreen(Engine engine) {
        super(engine);

    }

    public void init() {
        try {
            berlin = new CustomFont("/font/berlinSansFB12White");
            background = Image.createImage("/TokoBG.jpg");
            padlockImage = Image.createImage("/Gembok.png");
            cursorImage = Image.createImage("/Cursor.png");
            boxImage = Image.createImage("/boxNotif.png");
            yesImage = Image.createImage("/Iya.png");
            cancelImage = Image.createImage("/Batal.png");
            weapons = new Weapon[3];
            weapons[PEDANG] = (Weapon) ItemDatabase.PEDANG;
            weapons[KITCHEN_SET] = (Weapon) ItemDatabase.ALAT_MASAK;
            weapons[TONGKAT] = (Weapon) ItemDatabase.TONGKAT;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        profile = (MainProfile) engine.getProfile();
        cursorState = KITCHEN_SET;
        windowState = BROWSE_STATE;
    }

    public void pause() {
    }

    public void start() {
    }

    public void resume() {
    }

    public void reset() {
    }

    public void update(long currentTime) {
    }

    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, 0);
        berlin.paintString(g, Integer.toString(profile.getMoney()), 235, 40, Graphics.TOP | Graphics.LEFT);
        if (cursorState != MENU_UTAMA && windowState == BROWSE_STATE) {
            berlin.paintString(g, weapons[cursorState].getName(), 35, 165, Graphics.TOP | Graphics.LEFT);
            berlin.paintString(g, "Kekuatan:"+weapons[cursorState].getAttack()+" Jangkauan:"+weapons[cursorState].getMaxStack(), 35, 185, Graphics.TOP | Graphics.LEFT);
        }

        for (int i = 0; i < MENU_UTAMA; i++) {
            if (!weapons[i].isBought()) {
                berlin.paintString(g, Integer.toString(weapons[i].getPrice()), 60 + 90*i , 140, Graphics.TOP | Graphics.LEFT);
                g.drawImage(padlockImage, 80 + i*90, 80, 0);
            }

        }
        
        if(cursorState == MENU_UTAMA){
            g.drawImage(cursorImage, 213, 205, 0);
        } else {
            g.drawImage(cursorImage, 35 + 90 * cursorState, 120, 0);
            
        }
        if(windowState != BROWSE_STATE){
            String message = (windowState == CONFIRM_BUY_STATE)? "Beli barang ini?" : "Pakai barang ini?";
            g.drawImage(boxImage, 35,20, 0);
            berlin.paintString(g, message, 160, 40, Graphics.TOP | Graphics.HCENTER);
            g.drawImage(yesImage, 65,120,0 );
            g.drawImage(cancelImage, 175, 120,0);
            g.drawImage(cursorImage, (isOK)? 65 : 175 , 125, 0);
        }
    }

    public void keyPressed(int keyCode, int rawKeyCode) {
        prevCursorState = cursorState;
        switch (keyCode) {
            case GameCanvas.FIRE:
                if (windowState == BROWSE_STATE) {
                    if (cursorState == MENU_UTAMA) {
                        MainMenu mainMenu = new MainMenu(engine);
                        changeScene(mainMenu);
                    } else {
                        //Jika udah beli, langsung ke equip state
                        if (weapons[cursorState].isBought()) {
                            windowState = CONFIRM_EQUIP_STATE;
                            isOK = weapons[cursorState].isEquipped();

                        } //jika uang cukup buat beli
                        else if (profile.getMoney() >= weapons[cursorState].getPrice()) {
                            windowState = CONFIRM_BUY_STATE;
                            isOK = false;
                        } //Jika uang tidak cukup
                        else {
                            //bunyi eng ing eng. fulus ente nggak cukup
                        }
                    }
                } else if (windowState == CONFIRM_BUY_STATE) {
                    if (isOK) {
                        profile.setMoney(profile.getMoney() - weapons[cursorState].getPrice());
                        weapons[cursorState].setBought(true);

                        windowState = CONFIRM_EQUIP_STATE;
                        isOK = false;
                    } else {
                        windowState = BROWSE_STATE;
                        isOK = false;
                    }
                } else if (windowState == CONFIRM_EQUIP_STATE) {
                    windowState = BROWSE_STATE;
                    if (isOK) {
                        if (weapons[cursorState] instanceof Weapon) {
                            ItemDatabase.unequipAllWeapons();
                        }
                        weapons[cursorState].setEquipped(true);
                    }
                    windowState = BROWSE_STATE;
                }
                break;
            case GameCanvas.DOWN:
                if (windowState == BROWSE_STATE) {
                    if (cursorState != MENU_UTAMA) {
                        cursorState = MENU_UTAMA;
                    } else {
                        cursorState = KITCHEN_SET;
                    }
                }
                break;
            case GameCanvas.UP:
                if (windowState == BROWSE_STATE) {
                    if (cursorState != MENU_UTAMA) {
                        cursorState = MENU_UTAMA;
                    } else {
                        cursorState = KITCHEN_SET;
                    }
                }
                break;
            case GameCanvas.RIGHT:
                if (windowState == BROWSE_STATE) {
                    cursorState++;
                    if(cursorState > PEDANG) cursorState = KITCHEN_SET;
                } else if (windowState == CONFIRM_BUY_STATE || windowState == CONFIRM_EQUIP_STATE) {
                    isOK = !isOK;
                }
                break;
            case GameCanvas.LEFT:
                if (windowState == BROWSE_STATE) {
                    cursorState--;
                    if (cursorState < KITCHEN_SET) {
                        cursorState = PEDANG;
                    }
                } else if (windowState == CONFIRM_BUY_STATE || windowState == CONFIRM_EQUIP_STATE) {
                    isOK = !isOK;
                }
                break;

        }


        if (rawKeyCode == GameCanvas.KEY_STAR) {
            profile.setMoney(5000);
        }
    }

    public void pointerPressed(int x, int y) {
    }

    public void sleep() {
    }

}
