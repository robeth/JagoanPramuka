/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.scene;

import com.chocoarts.Engine;
import com.chocoarts.component.Timer;
import com.chocoarts.scene.Scene;
import com.chocoarts.text.CustomFont;
import dart.game.sprite.DummyButton;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

/**
 *
 * @author Maviosso
 */
public class SplashScreen extends Scene {
    private Image splashImage;
    private Timer splashTimer;
    
    public SplashScreen(Engine engine) {
        super(engine);
    }

   public void init()  {
        try {
            splashImage = Image.createImage("/SplashScreen1.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        splashTimer = new Timer(2000);
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
        if(splashTimer.isTicked(currentTime)){
            changeScene(new MainMenu(engine));
        }
    }

    public void paint(Graphics g) {
        g.drawImage(splashImage, 0, 0, Graphics.TOP | Graphics.LEFT);
    }

    public void keyPressed(int keyCode, int rawKeyCode) {
    }

    public void pointerPressed(int x, int y) {
        
    }

    public void sleep() {
        
    }
}
