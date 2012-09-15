/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.scene;

import com.chocoarts.Engine;
import com.chocoarts.scene.Scene;
import com.chocoarts.text.CustomFont;
import dart.game.data.Achievement;
import dart.game.data.AchievementData;
import dart.game.sound.SoundManager;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

/**
 *
 * @author Maviosso
 */
public class AchievementScreen extends Scene{
    private Image[] badgeImages;
    private Image lockedBadge, cursor, bg;
    private final Achievement[] achievements = AchievementData.BADGES;
    private int pointer;
    private boolean exit;
    private CustomFont berlin;
    private SoundManager sm;
    
    
    
    public AchievementScreen(Engine engine){
        super(engine); 
    }
    
    public void init() throws Exception {
        berlin = new CustomFont("/font/berlinSansFB12White");
        badgeImages = new Image[achievements.length];
        for(int i = 0; i < achievements.length; i++){
            badgeImages[i] = Image.createImage(achievements[i].getImagePath());
        }
        
        lockedBadge = Image.createImage("/BadgeLocked.png");
        cursor = Image.createImage("/Cursor.png");
        bg = Image.createImage("/PenghargaanBG.png");
        sm = SoundManager.getInstance();
        sm.playBG(SoundManager.BM_ALAM_LUAS);
        exit =false;
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

    public void update(long l) {
    }

    public void paint(Graphics g) {
        g.drawImage(bg, 0,0,Graphics.LEFT | Graphics.TOP);
        for(int i = 0; i < badgeImages.length; i++){
            if(achievements[i].isUnlocked()){
                g.drawImage(badgeImages[i], (30 + ((i%5)*50)), (i <5)? 70 : 125, Graphics.TOP | Graphics.LEFT);
            } else {
                g.drawImage(lockedBadge, (30 + ((i%5)*50)), (i <5)? 70 : 125, Graphics.TOP | Graphics.LEFT);
            }
        }
        
        if(exit){
            g.drawImage(cursor, 210, 200, Graphics.TOP | Graphics.LEFT);
        } else if(pointer < 5){
            g.drawImage(cursor, 30 + (50*pointer), 100, Graphics.TOP | Graphics.LEFT);
        } else {
            g.drawImage(cursor, 30 + (50*(pointer-5)), 160, Graphics.TOP | Graphics.LEFT);
        }
        if(exit){
            berlin.paintString(g, "Kembali ke menu utama", 30, 190, Graphics.TOP | Graphics.LEFT);
        } else {
            berlin.paintString(g,achievements[pointer].getDescription(), 30, 190, Graphics.TOP | Graphics.LEFT);
        }
    }

    public void keyPressed(int keyCode, int rawKeyCode) {
        boolean beep = false;
        
        if (keyCode == GameCanvas.FIRE) {
            
            if (exit) {
                beep = true;
                MainMenu mainMenu = new MainMenu(engine);
                changeScene(mainMenu);
            }
        } else if (keyCode == GameCanvas.LEFT) {
            if (--pointer %5 == 4) {
                pointer += 5;
                
            }
            if(pointer < 0) pointer =4;
            beep = true;
        } else if (keyCode == GameCanvas.RIGHT) {
            if (++pointer % 5 == 0) {
                pointer -= 5;
            }
            beep = true;
        } else if (keyCode == GameCanvas.DOWN) {
            if(pointer < 5){
                pointer += 5;
            } else if (exit){
                pointer %= 5;
                exit = !exit;
            } else {
                exit = !exit;
            }
            beep = true;
        } else if (keyCode == GameCanvas.UP) {
            if(pointer < 5){
                exit = !exit;
            } else if (exit){
                pointer = (pointer%5);
                pointer += 5;
                exit = !exit;
            } else {
                pointer -= 5;
            }
            beep = true;
        } 
        if(beep) sm.playSFX(SoundManager.SFX_BUTTON);
    }

    public void pointerPressed(int i, int i1) {
    }

    public void sleep() {
        sm.stopBG();
        sm.stopSFX();
    }
    
}
