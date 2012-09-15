/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.scene;

import com.chocoarts.Engine;
import com.chocoarts.scene.Scene;
import com.chocoarts.text.CustomFont;
import dart.game.main.MainProfile;
import dart.game.sound.SoundManager;
import dart.game.sprite.DummyButton;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

/**
 *
 * @author Maviosso
 */
public class OptionScreen extends Scene {

    CustomFont berlin;
    private MainProfile profile;
    private static final int SOUND_ROW = 0;
    private static final int RESET_ROW = 1;
    private static final int CONFIRM_ROW = 2;
    private int currentRow;
    private boolean currentSound;
    private boolean isReset;
    private boolean isConfirmReset;
    private Image background, boxImage, resetImage, backImage, yesImage, cancelImage, cursorImage;
    private SoundManager sm;

    public OptionScreen(Engine engine) {
        super(engine);
    }

    private void applyChange() {
        profile.setSound(currentSound);

    }

    private void applyReset() {
        profile.reset();
    }

    public void init() {
        try {
            berlin = new CustomFont("/font/berlinSansFB12White");
            background = Image.createImage("/Pengaturan.png");
            backImage = Image.createImage("/KembaliShop.png");
            boxImage = Image.createImage("/boxNotif.png");
            yesImage = Image.createImage("/Iya.png");
            cancelImage = Image.createImage("/Batal.png");
            cursorImage = Image.createImage("/Cursor.png");
            resetImage = Image.createImage("/ResetShop.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        profile = (MainProfile) engine.getProfile();
        currentSound = profile.getSound();
        currentRow = SOUND_ROW;
        isReset = false;
        isConfirmReset = false;
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
        sm.playBG(SoundManager.BM_ALAM_LUAS);
    }

    public void reset() {
    }

    public void update(long currentTime) {
    }
    
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, 0);
        berlin.paintString(g,"Suara", 35, 80, Graphics.TOP | Graphics.LEFT);
        berlin.paintString(g, "Nyala", 120, 80, Graphics.TOP | Graphics.LEFT);
        berlin.paintString(g,"Mati", 200, 80, Graphics.TOP | Graphics.LEFT);
        g.drawImage(resetImage, 30, 130, 0 );
        g.drawImage(backImage, 30, 170,0);
        
        int pointerX, pointerY;
        pointerX = pointerY = 0;
        if (currentRow == SOUND_ROW) {
            pointerY = 85;
            pointerX = (currentSound) ? 110 : 190;
                
        } else if (currentRow == RESET_ROW) {
            pointerY = 147;
            pointerX = 32;
        } else if (currentRow == CONFIRM_ROW) {
            pointerY = 187;
            pointerX = 32;
        }
        g.drawImage(cursorImage, pointerX, pointerY, 0);
        if(isReset){
            g.drawImage(boxImage, 35,20, 0);
            berlin.paintString(g,  "Semua data akan terhapus", 160, 40, Graphics.TOP | Graphics.HCENTER);
            berlin.paintString(g,  "Apakah anda yakin?", 160, 70, Graphics.TOP | Graphics.HCENTER);
            g.drawImage(yesImage, 65,120,0 );
            g.drawImage(cancelImage, 175, 120,0);
            g.drawImage(cursorImage, (isConfirmReset)? 65 : 175 , 130, 0);
        }
        

    }

    public void keyPressed(int keyCode, int rawKeyCode) {
        boolean beep = false;
        if (keyCode == GameCanvas.FIRE) {
            beep = true;
            switch (currentRow) {
                case CONFIRM_ROW:
                    applyChange();
                    changeScene(new MainMenu(engine));
                    break;
                case RESET_ROW:
                    if (isReset) {
                        if (isConfirmReset) {
                            applyReset();
                        }
                        isReset = false;
                    } else {
                        isReset = true;
                        isConfirmReset = false;
                    }
                    break;
            }
        } else if (keyCode == GameCanvas.DOWN && !isReset) {
            beep = true;
            if (++currentRow > CONFIRM_ROW) {
                currentRow = SOUND_ROW;
            }
        } else if (keyCode == GameCanvas.UP && !isReset) {
            beep = true;
            if (--currentRow < SOUND_ROW) {
                currentRow = CONFIRM_ROW;
            }
        } else if (keyCode == GameCanvas.RIGHT || keyCode == GameCanvas.LEFT) {
            switch (currentRow) {
                case SOUND_ROW:
                    beep = true;
                    currentSound = !currentSound;
                    if(currentSound){
                        sm.stopBG();
                        sm.stopSFX();
                    } else {
                        sm.playSFX(SoundManager.SFX_BUTTON);
                        sm.playBG(SoundManager.BM_ALAM_LUAS);
                    }
                    break;
                case RESET_ROW:
                    beep = true;
                    if (isReset) {
                        isConfirmReset = !isConfirmReset;
                    }
                    break;
            }
        }
        
        if(beep) sm.playSFX(SoundManager.SFX_BUTTON);
    }

    public void pointerPressed(int x, int y) {
    }

    public void sleep() {
        sm.stopBG();
        sm.stopSFX();
    }
}
