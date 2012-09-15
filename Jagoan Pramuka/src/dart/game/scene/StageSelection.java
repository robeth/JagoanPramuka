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
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

/**
 *
 * @author Maviosso
 */
public class StageSelection extends Scene {

    private CustomFont berlin;
//    private DummyButton TITLE = new DummyButton(110,0 ,100,40),
//            LEVEL_DETAIL = new DummyButton(20, 40, 280,100),
//            LEVEL_STAR = new DummyButton(260,100,40,40),
//            LEVEL_SCORE = new DummyButton(210,100,40,40),
//            LEVEL_INFO = new DummyButton(180,50,100,50),
//            LEVEL_THUMBNAIL = new DummyButton(30,50,140,80),
//            HINT = new DummyButton(20,190,200,40),
//            BACK = new DummyButton(230,200, 50,30);
//   private DummyButton[] LEVELS = {
//            new DummyButton(15,160,50,50),
//            new DummyButton(75,160,50,50),
//            new DummyButton(135,160,50,50),
//            new DummyButton(195,160,50,50),
//            new DummyButton(255,160,50,50)};
    Image background,cursorImage, padlockImage;
    private int level;
    private boolean isBack;
    private MainProfile profile;
    private int[][] cursorPosition = {
        {30, 110},
        {80, 110},
        {130,110},
        {180,110},
        {230,110}
            };
    public StageSelection(Engine engine) {
        super(engine);
    }

    public void init() {
        try {
            berlin = new CustomFont("/font/berlinSansFB12White");
            background = Image.createImage("/levelBG.png");
            cursorImage = Image.createImage("/Cursor.png");
            padlockImage = Image.createImage("/Padlock.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        level = 0;
        isBack = false;
        profile = (MainProfile) engine.getProfile();
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
        g.drawImage(background,0,0,0);
        
        int lastLevel = profile.getLastLevel();
        for(int i = 0; i < 5; i++){
            berlin.paintString(g, "Lvl "+(i+1), cursorPosition[i][0] + 15, cursorPosition[i][1]-10, Graphics.TOP|Graphics.LEFT);
            if(i > lastLevel){
                g.drawImage(padlockImage,cursorPosition[i][0] + 25, cursorPosition[i][1]-30, 0);
            }
        }
        
        
        
        if(isBack){
            g.drawImage(cursorImage,215,205,0);
        }else {
            berlin.paintString(g, "Skor: "+profile.getHighscore(level), 50, 135, Graphics.TOP| Graphics.LEFT);
            berlin.paintString(g, "Kombo: "+profile.getBestCombo(level), 50, 150, Graphics.LEFT | Graphics.TOP);
            berlin.paintString(g, "Hewan: "+profile.getSavedAnimals(level), 50, 165, Graphics.LEFT | Graphics.TOP);
            berlin.paintString(g, "Geng cicak: level "+(level+1), 150, 135, Graphics.TOP | Graphics.LEFT);
            g.drawImage(cursorImage,cursorPosition[level][0], cursorPosition[level][1],0);
        }
    }

    public void keyPressed(int keyCode, int rawKeyCode) {
        if (keyCode == GameCanvas.FIRE) {
            if (isBack) {
                MainMenu mainMenu = new MainMenu(engine);
                changeScene(mainMenu);
            } else {
                if(level <= profile.getLastLevel()){
                    MainPlay mainPlay = new MainPlay(engine, level + 1);
                    changeScene(mainPlay);
                }
            }
        } else if (keyCode == GameCanvas.LEFT) {
            if (--level < 0) {
                level = 4;
            }
        } else if (keyCode == GameCanvas.RIGHT) {
            if (++level >= 5) {
                level = 0;
            }
        } else if (keyCode == GameCanvas.DOWN || keyCode == GameCanvas.UP) {
            isBack = !isBack;
        } else if (rawKeyCode == GameCanvas.KEY_STAR) {
            profile.setLastLevel(4);
        } 
    }

    public void pointerPressed(int x, int y) {
    }

    public void sleep() {
    }
}
