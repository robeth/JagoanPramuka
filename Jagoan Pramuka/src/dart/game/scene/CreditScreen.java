/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.scene;

import com.chocoarts.Engine;
import com.chocoarts.scene.Scene;
import com.chocoarts.text.CustomFont;
import dart.game.sound.SoundManager;
import dart.game.sprite.Arrow;
import dart.game.sprite.DummyButton;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

/**
 *
 * @author Maviosso
 */
public class CreditScreen extends Scene {

    private int page;
    private static final int PAGE_NUMBER = 3;
    private Image bg, i1, i2,i3, mgdw,r,l;
    private Arrow leftArrow, rightArrow;
    private SoundManager sm;
    
    public CreditScreen(Engine engine) {
        super(engine);
    }

   public void init() throws Exception {
        page = 0;
        bg = Image.createImage("/KreditBG.jpg");
        i1 = Image.createImage("/Konten1.png");
        i2 = Image.createImage("/Konten2.png");
        i3 = Image.createImage("/Konten3.png");
        mgdw = Image.createImage("/mgdwLogo.png");
        r = Image.createImage("/rightArrow.png");
        l = Image.createImage("/leftArrow.png");
        leftArrow = new Arrow(l, 10, 105, 250, 10, false);
        rightArrow = new Arrow(r, 275,105,250,-10,false);
        sm = SoundManager.getInstance();
        sm.playBG(SoundManager.BM_ALAM_LUAS);
    }

    public void pause() {
        sm.stopBG();
        sm.stopSFX();
    }

    public void start() {
        
    }

    public void resume() {
        sm.playSFX(SoundManager.BM_ALAM_LUAS);
    }

    public void reset() {
        
    }

    public void update(long currentTime) {
        leftArrow.update(currentTime);
        rightArrow.update(currentTime);
    }

    public void paint(Graphics g) {
        g.drawImage(bg, 0,0,Graphics.LEFT| Graphics.TOP);
        if(page == 0){
            g.drawImage(i1, 60, 75,Graphics.LEFT | Graphics.TOP);
        } else if (page == 1){
            g.drawImage(i2, 60, 75,Graphics.LEFT | Graphics.TOP);
        } else {
            g.drawImage(i3, 60, 75,Graphics.LEFT | Graphics.TOP);
        }
        g.drawImage(mgdw, 110, 170, Graphics.LEFT | Graphics.TOP);
        leftArrow.paint(g);
        rightArrow.paint(g);
    }

    public void keyPressed(int keyCode, int rawKeyCode) {
        boolean beep = false;
        if (keyCode == GameCanvas.FIRE){
            beep = true;
            MainMenu mainMenu = new MainMenu(engine);
            changeScene(mainMenu);
        } else if (keyCode == GameCanvas.RIGHT){
            beep = true;
            page--;
            if(page < 0) page = 2;
        } else if( keyCode == GameCanvas.LEFT){
            beep = true;
            page++;
            if(page > 2) page = 0;
        }
        
        if(beep){
            sm.playSFX(SoundManager.SFX_BUTTON);
        }
    }

    public void pointerPressed(int x, int y) {
        
    }

    public void sleep() {
        sm.stopBG();
        sm.stopSFX();
    }
}
