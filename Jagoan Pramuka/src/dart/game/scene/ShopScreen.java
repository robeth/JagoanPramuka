/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.scene;

import com.chocoarts.Engine;
import com.chocoarts.scene.Scene;
import com.chocoarts.text.CustomFont;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

/**
 *
 * @author Maviosso
 */
public class ShopScreen extends Scene {
    CustomFont berlin;
    
    public ShopScreen(Engine engine) {
        super(engine);
    }
    //List of item
    //Buy
    //Back

    public void init() throws Exception {
        berlin = new CustomFont("/font/berlinSansFB12White");
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
        berlin.paintString(g, "Shop Screen. Press Fire key to finish.", 50,100, Graphics.LEFT|Graphics.TOP);
    }

    public void keyPressed(int keyCode, int rawKeyCode) {
        if (keyCode == GameCanvas.FIRE){
            MainMenu mainMenu = new MainMenu(engine);
            engine.setNextScene(mainMenu);
            changeScene(mainMenu);
        }
    }

    public void pointerPressed(int x, int y) {
        
    }

    public void sleep() {
        
    }
}
