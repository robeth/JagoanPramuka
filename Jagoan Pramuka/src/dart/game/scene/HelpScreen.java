/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.scene;

import com.chocoarts.Engine;
import com.chocoarts.debug.Debug;
import com.chocoarts.scene.Scene;
import com.chocoarts.text.CustomFont;
import dart.game.sound.SoundManager;
import dart.game.sprite.DummyButton;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

/**
 *
 * @author Maviosso
 */
public class HelpScreen extends Scene{
    private CustomFont berlin;
    private int page;
    private static final int PAGE_NUMBER = 3;
    private String[] contentSamples = {
        "Page 1.Jangan lupa shalat", 
        "Page 2.Jangan injak daku", 
        "Page 3.Buang sampah pada tempatnya"};
    private DummyButton TITLE = new DummyButton(110,0 ,100,40),
            INFO = new DummyButton(60, 200, 200,40),
            FRAME = new DummyButton(35,45,250,150);
    private SoundManager sm;
    
    public HelpScreen(Engine engine) {
        super(engine);
    }

   public void init() throws Exception {
        berlin = new CustomFont("/font/berlinSansFB12White");
        page = 0;
        int[] sfxIDs = new int[1];
        sfxIDs[0] = SoundManager.SFX_BUTTON;
        sm = SoundManager.getInstance();
        sm.playBG(SoundManager.BM_ALAM_LUAS);
        sm.initSFXs(sfxIDs);
    }

    public void pause() {
        sm.stopBG();
        sm.stopSFX();
    }

    public void start() {
        
    }

    public void resume() {
        sm.playBG(SoundManager.BM_ALAM_LUAS);
    }

    public void reset() {
        
    }

    public void update(long currentTime) {
        
    }

    public void paint(Graphics g) {
        TITLE.drawString(g, berlin, "Tutorial Screen");
        
        g.setColor(0,0,255);
        FRAME.fill(g);
        FRAME.drawString(g, berlin, contentSamples[page]);
        
        g.setColor(255,255,255);
        INFO.drawStroke(g);
        INFO.drawString(g, berlin, "< | > to navigate. Press FIRE to Main Menu");
        
    }

    public void keyPressed(int keyCode, int rawKeyCode) {
        if (keyCode == GameCanvas.FIRE){
            MainMenu mainMenu = new MainMenu(engine);
            changeScene(mainMenu);
        } else if(keyCode == GameCanvas.LEFT){
            if(--page < 0) page = PAGE_NUMBER -1;
        } else if(keyCode == GameCanvas.RIGHT){
            if(++page >= PAGE_NUMBER) page = 0;
        }
    }

    public void pointerPressed(int x, int y) {
        
    }

    public void sleep() {
        sm.stopBG();
        sm.stopSFX();
    }
}
