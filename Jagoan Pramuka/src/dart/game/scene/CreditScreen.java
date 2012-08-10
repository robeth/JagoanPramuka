/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.scene;

import com.chocoarts.Engine;
import com.chocoarts.scene.Scene;
import com.chocoarts.text.CustomFont;
import dart.game.sprite.DummyButton;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

/**
 *
 * @author Maviosso
 */
public class CreditScreen extends Scene {

    private CustomFont berlin;
    private int page;
    private static final int PAGE_NUMBER = 3;
    private String[] contentSamples = {
        "Developer. Info Gamestudio DART", 
        "Credit sound effect & background music dari internet", 
        "Credit gambar2 dari internet"};
    private DummyButton TITLE = new DummyButton(110,0 ,100,40),
            INFO = new DummyButton(60, 200, 200,40),
            FRAME = new DummyButton(35,45,250,150);
    
    public CreditScreen(Engine engine) {
        super(engine);
    }

   public void init() throws Exception {
        berlin = new CustomFont("/font/berlinSansFB12White");
        page = 0;
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
        TITLE.drawString(g, berlin, "Credit Screen");
        
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
        
    }
}
