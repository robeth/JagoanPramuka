/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.scene;

import com.chocoarts.Engine;
import com.chocoarts.debug.Debug;
import com.chocoarts.scene.Scene;
import com.chocoarts.shape.Rectangle;
import com.chocoarts.text.CustomFont;
import dart.game.sprite.DummyButton;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

/**
 *
 * @author Maviosso
 */
public class MainMenu extends Scene {
    //Play
    //Shop
    //Option
    //Credit
    //Help
    private static final int PLAY_BUTTON = 0,
        SHOP_BUTTON = 1,
        OPTION_BUTTON = 2,
        CREDIT_BUTTON = 3,
        HELP_BUTTON = 4,
        QUIT_BUTTON =5;
    
    private int pointer = PLAY_BUTTON;
    private String[] buttonsName;
    private CustomFont berlin;
    private DummyButton buttons [] = {
             new DummyButton(250, 0, 60,40),    // play
             new DummyButton(250, 45, 60,40),   // shop
             new DummyButton(250, 90, 60, 40),  // option
             new DummyButton(250, 135, 60,40),  // credit
             new DummyButton(250, 180, 60, 40), // help
             new DummyButton(185, 180, 60, 40)};  // quit
    
    
    public MainMenu (Engine engine){
        super(engine);
    }
    
    public void init() throws Exception {
        berlin = new CustomFont("/font/berlinSansFB12White");
        
        buttonsName = new String[6];
        buttonsName[0] = "Play";
        buttonsName[1] = "Shop";
        buttonsName[2] = "Option";
        buttonsName[3] = "Credit";
        buttonsName[4] = "Tutorial";
        buttonsName[5] = "Quit";
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
        for(int i = 0; i < buttonsName.length; i++){
            if(pointer == i){
                g.setColor(255, 0,0);
            } else {
                g.setColor(0,0,255);
            }
            buttons[i].fill(g);
            buttons[i].drawString(g, berlin, buttonsName[i]);
        }
    }

    public void keyPressed(int keyCode, int rawKeyCode) {
        if(keyCode == GameCanvas.UP)
            pointer--;
        else if (keyCode == GameCanvas.DOWN)
            pointer++;
        else if (keyCode == GameCanvas.FIRE)
            goToScene(pointer);
        
        pointer = pointer < 0 ? 5 : (pointer > 5 ? 0 : pointer);
    }

    public void pointerPressed(int x, int y) {
    }

    public void sleep() {
    }

    private void goToScene(int pointer) {
        Debug.println("go to scene :"+pointer);
        Scene nextScene = null;
        boolean exit = false;
        switch(pointer){
            case CREDIT_BUTTON : nextScene = new CreditScreen(engine); break;
            case HELP_BUTTON : nextScene = new HelpScreen(engine); break;
            case OPTION_BUTTON : nextScene = new OptionScreen(engine); break;
            case PLAY_BUTTON: nextScene = new StageSelection(engine); break;
            case QUIT_BUTTON: exit = true;break;
            case SHOP_BUTTON: nextScene = new ShopScreen(engine); break;
        }
        
        if(exit)
            closeApp(); 
        else
            changeScene(nextScene);
    }
    
}
