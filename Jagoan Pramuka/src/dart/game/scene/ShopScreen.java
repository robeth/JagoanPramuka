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
            CONFIRM_EQUIP_STATE = 2;
    private int cursorState;
    private int prevCursorState;
    private static final int KITCHEN_SET = 0,
            TONGKAT = 1,
            TALI = 2,
            HASDUK = 3,
            RING = 4,
            EMBLEM = 5,
            MENU_UTAMA = 6;
    private boolean isOK;
    private ItemButton itemButtons[];
    private DummyButton2 okButton, cancelButton, previewFrame, confirmFrame,
            descriptionFrame, moneyFrame;
    private Image padlockImage, cursorImage, background, coinImage, backImage, boxImage, yesImage, cancelImage;
    private int[][] cursorPosition = {
        {40, 110}, {100, 110}, {160, 110},
        {40, 170}, {100, 170}, {160, 170},
        {40, 200}
    };

    public ShopScreen(Engine engine) {
        super(engine);

    }

    public void init() {
        try {
            berlin = new CustomFont("/font/berlinSansFB12White");
            coinImage = Image.createImage("/CoinShop.png");
            background = Image.createImage("/ShopBG.png");
            padlockImage = Image.createImage("/Padlock.png");
            cursorImage = Image.createImage("/Cursor.png");
            backImage = Image.createImage("/KembaliShop.png");
            boxImage = Image.createImage("/boxNotif.png");
            yesImage = Image.createImage("/Iya.png");
            cancelImage = Image.createImage("/Batal.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        profile = (MainProfile) engine.getProfile();

        itemButtons = new ItemButton[7];

        itemButtons[KITCHEN_SET] = new ItemButton(ItemDatabase.ALAT_MASAK, berlin, 10, 10, 50, 50, true, true, Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);
        itemButtons[TONGKAT] = new ItemButton(ItemDatabase.TONGKAT, berlin, 70, 10, 50, 50, true, true, Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);
        itemButtons[TALI] = new ItemButton(ItemDatabase.TALI, berlin, 130, 10, 50, 50, true, true, Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);
        itemButtons[HASDUK] = new ItemButton(ItemDatabase.HASDUK, berlin, 10, 70, 50, 50, true, true, Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);
        itemButtons[RING] = new ItemButton(ItemDatabase.RING, berlin, 70, 70, 50, 50, true, true, Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);
        itemButtons[EMBLEM] = new ItemButton(ItemDatabase.BADGE, berlin, 130, 70, 50, 50, true, true, Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);
        itemButtons[MENU_UTAMA] = new ItemButton(berlin, 10, 150, 50, 50, true, true, "Back", Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);

        okButton = new DummyButton2(berlin, 130, 70, 50, 50, true, true, "OK", Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);
        cancelButton = new DummyButton2(berlin, 190, 70, 50, 50, true, true, "Cancel", Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);
        previewFrame = new DummyButton2(berlin, 200, 10, 100, 200, true, true, "PreviewFrame", Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);
        descriptionFrame = new DummyButton2(berlin, 210, 100, 80, 90, true, true, "Description Frame", Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);
        confirmFrame = new DummyButton2(berlin, 120, 60, 130, 80, true, true, "", Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);
        moneyFrame = new DummyButton2(berlin, 260, 10, 50, 30, true, true, "Money: ", Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);
        cursorState = KITCHEN_SET;
        windowState = BROWSE_STATE;
        itemButtons[cursorState].setIsChosen(true);



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
        g.drawImage(backImage, 40, 190, 0);
        berlin.paintString(g, Integer.toString(profile.getMoney()), 228, 37, Graphics.TOP | Graphics.LEFT);
        if (cursorState != MENU_UTAMA) {
            berlin.paintString(g, itemButtons[cursorState].getItem().getName(), 235, 100, Graphics.TOP | Graphics.LEFT);
            berlin.paintString(g, itemButtons[cursorState].getItem().getDescription(), 130, 190, Graphics.TOP | Graphics.LEFT);
        }

        for (int i = 0; i < MENU_UTAMA; i++) {
            if (!itemButtons[i].getItem().isBought()) {
                g.drawImage(coinImage, cursorPosition[i][0] + 10, cursorPosition[i][1], 0);
                berlin.paintString(g, Integer.toString(itemButtons[i].getItem().getPrice()), cursorPosition[i][0] + 20, cursorPosition[i][1] - 5, Graphics.TOP | Graphics.LEFT);
                g.drawImage(padlockImage, cursorPosition[i][0] + 30, cursorPosition[i][1] - 25, 0);
            }

        }
        g.drawImage(cursorImage, cursorPosition[cursorState][0], cursorPosition[cursorState][1], 0);
        
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
                        if (itemButtons[cursorState].getItem().isBought()) {
                            windowState = CONFIRM_EQUIP_STATE;
                            isOK = itemButtons[cursorState].getItem().isEquipped();

                        } //jika uang cukup buat beli
                        else if (profile.getMoney() >= itemButtons[cursorState].getItem().getPrice()) {
                            windowState = CONFIRM_BUY_STATE;
                            isOK = false;
                        } //Jika uang tidak cukup
                        else {
                            //bunyi eng ing eng. fulus ente nggak cukup
                        }
                    }
                } else if (windowState == CONFIRM_BUY_STATE) {
                    if (isOK) {
                        profile.setMoney(profile.getMoney() - itemButtons[cursorState].getItem().getPrice());
                        itemButtons[cursorState].getItem().setBought(true);

                        windowState = CONFIRM_EQUIP_STATE;
                        isOK = false;
                    } else {
                        windowState = BROWSE_STATE;
                        isOK = false;
                    }
                } else if (windowState == CONFIRM_EQUIP_STATE) {
                    windowState = BROWSE_STATE;
                    if (isOK) {
                        if (itemButtons[cursorState].getItem() instanceof Weapon) {
                            ItemDatabase.unequipAllWeapons();
                        }
                        itemButtons[cursorState].getItem().setEquipped(true);
                    }
                    windowState = BROWSE_STATE;
                }
                updateButtonsState();
                break;
            case GameCanvas.DOWN:
                if (windowState == BROWSE_STATE) {
                    cursorState += 3;
                    if (cursorState > 8) {
                        cursorState = KITCHEN_SET;
                    } else if (cursorState > EMBLEM) {
                        cursorState = MENU_UTAMA;
                    }
                }
                updateButtonsState();
                break;
            case GameCanvas.UP:
                if (windowState == BROWSE_STATE) {
                    cursorState -= 3;
                    if (cursorState < KITCHEN_SET) {
                        cursorState = MENU_UTAMA;
                    }
                }
                updateButtonsState();
                break;
            case GameCanvas.RIGHT:
                if (windowState == BROWSE_STATE) {
                    cursorState++;
                    if (cursorState == HASDUK) {
                        cursorState = KITCHEN_SET;
                    } else if (cursorState == MENU_UTAMA) {
                        cursorState = HASDUK;
                    } else if (cursorState > MENU_UTAMA) {
                        cursorState = MENU_UTAMA;
                    }
                } else if (windowState == CONFIRM_BUY_STATE || windowState == CONFIRM_EQUIP_STATE) {
                    isOK = !isOK;
                }
                updateButtonsState();
                break;
            case GameCanvas.LEFT:
                if (windowState == BROWSE_STATE) {
                    cursorState--;
                    if (cursorState < KITCHEN_SET) {
                        cursorState = TALI;
                    } else if (cursorState == TALI) {
                        cursorState = EMBLEM;
                    } else if (cursorState == EMBLEM) {
                        cursorState = MENU_UTAMA;
                    }
                } else if (windowState == CONFIRM_BUY_STATE || windowState == CONFIRM_EQUIP_STATE) {
                    isOK = !isOK;
                }
                updateButtonsState();
                break;

        }


        if (rawKeyCode == GameCanvas.KEY_STAR) {
            profile.setMoney(5000);
        }
        moneyFrame.setLabel("" + profile.getMoney());
    }

    public void pointerPressed(int x, int y) {
    }

    public void sleep() {
    }

    private void updateButtonsState() {
        itemButtons[prevCursorState].setIsChosen(false);
        itemButtons[cursorState].setIsChosen(true);
        okButton.setIsChosen(isOK);
        cancelButton.setIsChosen(!isOK);
    }
}
