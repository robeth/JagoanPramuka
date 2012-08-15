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
public class StageSelection extends Scene{
   private CustomFont berlin;
    private DummyButton TITLE = new DummyButton(110,0 ,100,40),
            LEVEL_DETAIL = new DummyButton(20, 40, 280,100),
            LEVEL_STAR = new DummyButton(260,100,40,40),
            LEVEL_SCORE = new DummyButton(210,100,40,40),
            LEVEL_INFO = new DummyButton(180,50,100,50),
            LEVEL_THUMBNAIL = new DummyButton(30,50,140,80),
            HINT = new DummyButton(20,190,200,40),
            BACK = new DummyButton(230,200, 50,30);
   private DummyButton[] LEVELS = {
            new DummyButton(15,160,50,50),
            new DummyButton(75,160,50,50),
            new DummyButton(135,160,50,50),
            new DummyButton(195,160,50,50),
            new DummyButton(255,160,50,50)};
    private int level;
    private boolean isBack;
            
    
    public StageSelection(Engine engine) {
        super(engine);
    }

   public void init() throws Exception {
        berlin = new CustomFont("/font/berlinSansFB12White");
        level = 0;
        isBack = false;
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
        TITLE.drawString(g, berlin, "Tutorial Screen");
        
        g.setColor(0,0,255);
        LEVEL_DETAIL.fill(g);
        
        g.setColor(0,255,0);
        LEVEL_THUMBNAIL.fill(g);
        LEVEL_INFO.fill(g);
        g.setColor(255,0,0);
        LEVEL_STAR.fill(g);
        LEVEL_SCORE.fill(g);
        
        for(int i = 0; i < LEVELS.length; i++){
            if(level == i)
                g.setColor(50,50,50);
            else 
                g.setColor(255,0,0);
            LEVELS[i].fill(g);
            LEVELS[i].drawString(g, berlin, "Level "+(i+1));
        }
        
        HINT.drawString(g, berlin, "Arrow to navigate.");
        if(isBack){
            g.setColor(100,100,100);
        }else {
            g.setColor(50,50,50);
        }
        BACK.fill(g);
        BACK.drawString(g, berlin, "Back");
    }

    public void keyPressed(int keyCode, int rawKeyCode) {
        if (keyCode == GameCanvas.FIRE){
            if(isBack){
                MainMenu mainMenu = new MainMenu(engine);
                changeScene(mainMenu);
            } else {
                MainPlay mainPlay = new MainPlay(engine, level + 1);
                changeScene(mainPlay);
            }
        } else if(keyCode == GameCanvas.LEFT){
            if(--level < 0) level = LEVELS.length -1;
        } else if(keyCode == GameCanvas.RIGHT){
            if(++level >= LEVELS.length) level = 0;
        } else if (keyCode == GameCanvas.DOWN || keyCode == GameCanvas.UP){
            isBack = !isBack;
        }
    }

    public void pointerPressed(int x, int y) {
        
    }

    public void sleep() {
        
    }
}
