/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.scene;

import com.chocoarts.Engine;
import com.chocoarts.scene.Scene;
import com.chocoarts.text.CustomFont;
import dart.game.main.MainProfile;
import dart.game.sprite.DummyButton;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

/**
 *
 * @author Maviosso
 */
public class OptionScreen extends Scene{
    CustomFont berlin;
    
    
    private MainProfile profile;
    
    private static final int SOUND_ROW = 0;
    private static final int RESET_ROW = 1;
    private static final int CONFIRM_ROW = 2;
    
    private int currentRow;
    private boolean currentSound;
    private boolean isReset;
    private boolean isConfirmReset;
    
    private DummyButton 
            TITLE = new DummyButton(110,0 ,100,40),
            SOUND = new DummyButton(20 ,50, 80,40),
            SOUND_ON = new DummyButton (120, 50, 50, 40),
            SOUND_OFF = new DummyButton(200, 50, 50, 40),
            RESET = new DummyButton(20, 120, 80, 40),
            MAIN_MENU = new DummyButton(220, 180, 80,40),
            OVERLAY_FRAME = new DummyButton(30,30,260, 150),
            OVERLAY_TITLE = new DummyButton(130, 40, 80,60),
            OVERLAY_CONTENT = new DummyButton(60, 110, 200,40 ),
            OVERLAY_YES = new DummyButton(90, 170, 60,40),
            OVERLAY_NO = new DummyButton(170, 170, 60,40);
            
    
    public OptionScreen(Engine engine) {
        super(engine);
    }
    
    private void applyChange(){
        profile.setSound(currentSound);
        
    }
    
    private void applyReset(){
        profile.reset();
    }

    public void init() throws Exception {
        berlin = new CustomFont("/font/berlinSansFB12White");
        profile = (MainProfile)engine.getProfile();
        currentSound = profile.getSound();
        currentRow = SOUND_ROW;
        isReset = false;
        isConfirmReset = false;
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
        TITLE.drawString(g, berlin,"Option Screen");
        
        //Draw Row Header
        g.setColor(255,0,0);
        SOUND.fill(g);
        RESET.fill(g);
        MAIN_MENU.fill(g);
        SOUND.drawString(g, berlin, "Sound");
        RESET.drawString(g, berlin, "Reset");
        MAIN_MENU.drawString(g, berlin, "Main Menu");
        
        //Draw stroke for selected row
        g.setColor(0, 0, 255);
        if(currentRow == SOUND_ROW){
            SOUND.drawStroke(g);
        } else if(currentRow == RESET_ROW){
            RESET.drawStroke(g);
        } else if(currentRow == CONFIRM_ROW){
            MAIN_MENU.drawStroke(g);
        }
        
        
        //Sound String
        if(currentSound){
            SOUND_ON.fill(g);
        } else {
            SOUND_OFF.fill(g);
        }
        SOUND_ON.drawString(g, berlin, "On");
        SOUND_OFF.drawString(g, berlin, "Off");
        
        
        //Reset Confirmation Overlay
        if(isReset){
            g.setColor(0, 255, 0);
            OVERLAY_FRAME.fill(g);
            
            g.setColor(0, 255, 255);
            OVERLAY_TITLE.fill(g);
            OVERLAY_TITLE.drawString(g, berlin, "Are you sure?");
            
            OVERLAY_CONTENT.fill(g);
            OVERLAY_CONTENT.drawString(g,berlin, "All your data will be erased");
            
            if(isConfirmReset)
                OVERLAY_YES.fill(g);
            else
                OVERLAY_NO.fill(g);
            
            OVERLAY_YES.drawString(g, berlin, "Yes");
            OVERLAY_NO.drawString(g, berlin, "No");
            
        }
        
    }

    public void keyPressed(int keyCode, int rawKeyCode) {
        if (keyCode == GameCanvas.FIRE ){
            switch(currentRow){
                case CONFIRM_ROW : 
                    applyChange();
                    changeScene(new MainMenu(engine)); 
                    break;
                case RESET_ROW : 
                    if(isReset){
                        if(isConfirmReset){
                            applyReset();
                        }
                        isReset = false;
                    } else {
                        isReset = true;
                        isConfirmReset = false;
                    } break;
            }
        } else if(keyCode == GameCanvas.DOWN && !isReset ){
            if(++currentRow > CONFIRM_ROW )
                currentRow = SOUND_ROW;
        } else if (keyCode == GameCanvas.UP && !isReset){
            if(--currentRow < SOUND_ROW)
                currentRow = CONFIRM_ROW;
        } else if(keyCode == GameCanvas.RIGHT || keyCode == GameCanvas.LEFT){
            switch(currentRow){
                case SOUND_ROW : currentSound = !currentSound; break;
                case RESET_ROW : if(isReset) isConfirmReset = !isConfirmReset; break;
            }
        }
    }

    public void pointerPressed(int x, int y) {
        
    }

    public void sleep() {
        
    }
}
