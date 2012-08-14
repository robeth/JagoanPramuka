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
import dart.game.main.MainProfile;
import dart.game.sprite.Color;
import dart.game.sprite.DummyButton2;
import dart.game.sprite.ItemButton;
import javax.microedition.lcdui.Graphics;
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
            
    public ShopScreen(Engine engine) {
        super(engine);
        
    }
    
    public void init() throws Exception {
        berlin = new CustomFont("/font/berlinSansFB12White");
        profile = (MainProfile) engine.getProfile();
        
        itemButtons = new ItemButton[7];
        
        itemButtons[KITCHEN_SET] = new ItemButton(ItemDatabase.ALAT_MASAK,berlin, 10, 10, 50, 50, true, true, Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);
        itemButtons[TONGKAT] = new ItemButton(ItemDatabase.TONGKAT,berlin, 70, 10, 50, 50, true, true, Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);
        itemButtons[TALI] = new ItemButton(ItemDatabase.TALI, berlin, 130, 10, 50, 50, true, true, Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);
        itemButtons[HASDUK] = new ItemButton(ItemDatabase.HASDUK,berlin, 10, 70, 50, 50, true, true, Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);
        itemButtons[RING] = new ItemButton(ItemDatabase.RING,berlin, 70, 70, 50, 50, true, true, Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);
        itemButtons[EMBLEM] = new ItemButton(ItemDatabase.BADGE,berlin, 130, 70, 50, 50, true, true,Color.BLACK, Color.WHITE, Color.RED, Color.BLUE);
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
        for(int i = 0; i < itemButtons.length; i ++)
            itemButtons[i].paint(g);
        
        previewFrame.paint(g);
        descriptionFrame.paint(g);
        moneyFrame.paint(g);
        if(windowState != BROWSE_STATE){
            confirmFrame.paint(g);
            okButton.paint(g);
            cancelButton.paint(g);
        }
    }

    public void keyPressed(int keyCode, int rawKeyCode) {
        prevCursorState = cursorState;
        switch(keyCode){
            case GameCanvas.FIRE:
                if(windowState == BROWSE_STATE){
                    if(cursorState == MENU_UTAMA){
                        MainMenu mainMenu = new MainMenu(engine);
                        changeScene(mainMenu);
                    } else {
                        windowState = CONFIRM_BUY_STATE;
                        isOK = false;
                    }
                } else if (windowState == CONFIRM_BUY_STATE ){
                    if(isOK){
                        windowState = CONFIRM_EQUIP_STATE;
                        isOK = false;
                    } else {
                        
                    }
                } else if (windowState == CONFIRM_EQUIP_STATE){
                    windowState = BROWSE_STATE;
                    if(isOK){
                        
                    } else {
                        
                    }
                    windowState = BROWSE_STATE;
                }
                break;
            case GameCanvas.DOWN: 
                if(windowState == BROWSE_STATE){
                    cursorState+= 3;
                    if(cursorState > 8) cursorState = KITCHEN_SET;
                    else if (cursorState > EMBLEM) cursorState = MENU_UTAMA;
                }
                updateButtonsState();
                break;
            case GameCanvas.UP: 
                if(windowState == BROWSE_STATE){
                    cursorState -= 3;
                    if(cursorState < KITCHEN_SET) cursorState = MENU_UTAMA;
                }
                updateButtonsState();
                break;
            case GameCanvas.RIGHT: 
                if(windowState == BROWSE_STATE){
                    cursorState++;
                    if(cursorState == HASDUK) cursorState = KITCHEN_SET;
                    else if(cursorState == MENU_UTAMA) cursorState = HASDUK;
                    else if(cursorState > MENU_UTAMA) cursorState = MENU_UTAMA;
                } else if(windowState == CONFIRM_BUY_STATE || windowState  == CONFIRM_EQUIP_STATE){
                    isOK = !isOK;
                }
                updateButtonsState();
                break;
            case GameCanvas.LEFT: 
                if(windowState == BROWSE_STATE){
                    cursorState--;
                    if(cursorState < KITCHEN_SET) cursorState = TALI;
                    else if(cursorState == TALI) cursorState = EMBLEM;
                    else if(cursorState == EMBLEM) cursorState = MENU_UTAMA;
                } else if(windowState == CONFIRM_BUY_STATE || windowState  == CONFIRM_EQUIP_STATE){
                    isOK = !isOK;
                }
                updateButtonsState();
                break;
        }
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
