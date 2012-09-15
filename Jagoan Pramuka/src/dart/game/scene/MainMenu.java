/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.scene;

import com.chocoarts.Engine;
import com.chocoarts.debug.Debug;
import com.chocoarts.drawing.ChocoSprite;
import com.chocoarts.scene.Scene;
import com.chocoarts.text.CustomFont;
import dart.game.sound.SoundManager;
import dart.game.sprite.Arrow;
import dart.game.sprite.ImageSlider;
import dart.game.sprite.MenuButton;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

/**
 *
 * @author Maviosso
 */
public class MainMenu extends Scene {

    private static final int PLAY_BUTTON = 0,
            SHOP_BUTTON = 1,
            OPTION_BUTTON = 2,
            ACHIEVEMENT_BUTTON = 3,
            ABOUT_BUTTON = 4,
            QUIT_BUTTON = 5;
    private Image bgmenu, optionselect, shopselect, exitselect, playselect, optionunselect, shopunselect, exitunselect, playunselect, logo;
    private int pointer = PLAY_BUTTON;
    private ChocoSprite bgsprite;
    private ImageSlider imageSlider;
    private Arrow leftArrow, rightArrow;
    
    private SoundManager sm;
    public MainMenu(Engine engine) {
        super(engine);
    }

    public void init() throws Exception {
        bgmenu = Image.createImage("/BGMenuBaru.jpg");
        sm = SoundManager.getInstance();
        sm.playBG(SoundManager.BM_ALAM_LUAS);
        
        Image[] images = new Image[6];
        images[0] = Image.createImage("/Mulai.png");
        images[1] = Image.createImage("/Toko.png");
        images[2] = Image.createImage("/Atur.png");
        images[3] = Image.createImage("/Penghargaan.png");
        images[4] = Image.createImage("/Tentang.png");
        images[5] = Image.createImage("/Keluar.png");
        
        imageSlider = new ImageSlider(images, 90, 160, 160, 50, 150, 35, 500);
        leftArrow = new Arrow(Image.createImage("/leftArrow.png"), 60, 195, 250, -10, false);
        rightArrow = new Arrow(Image.createImage("/rightArrow.png"), 240, 195, 250, 10, false);
    }

    public void pause() {
        System.out.println("On Pause On Pause On Pause On Pause On Pause");
    }

    public void start() {
        System.out.println("On Start On Start On Start On Start On Start");
    }

    public void resume() {
        System.out.println("On Resume On Resume On Resume On Resume On Resume");
    }

    public void reset() {
        System.out.println("On reset On reset On reset On reset On reset On reset ");
    }

    public void update(long currentTime) {
        imageSlider.update(currentTime);
        leftArrow.update(currentTime);
        rightArrow.update(currentTime);
    }

    public void paint(Graphics g) {
        g.drawImage(bgmenu, 0,0, Graphics.TOP | Graphics.LEFT);
        imageSlider.paint(g);
        leftArrow.paint(g);
        rightArrow.paint(g);
    }

    public void keyPressed(int keyCode, int rawKeyCode) {
        int prev = pointer;
        boolean beep = false;
        if (keyCode == GameCanvas.LEFT) {
            if( imageSlider.slideLeft(System.currentTimeMillis())){
                pointer++;
                beep = true;
            }
        } else if (keyCode == GameCanvas.RIGHT) {
            if(imageSlider.slideRight(System.currentTimeMillis())){
                pointer--;
                beep = true;
            }
        } else if (keyCode == GameCanvas.FIRE) {
            goToScene(pointer);
            beep = true;
        }

        pointer = pointer < 0 ? 5 : (pointer > 5 ? 0 : pointer);
        
        if(beep){
            sm.playSFX(SoundManager.SFX_BUTTON);
        }
    }

    public void pointerPressed(int x, int y) {
    }

    public void sleep() {
        System.out.println("On Sleep On Sleep  On Sleep On Sleep On Sleep ");
    }

    private void goToScene(int pointer) {
        Debug.println("go to scene :" + pointer);
        Scene nextScene = null;
        boolean exit = false;
        switch (pointer) {
            case ABOUT_BUTTON:
                nextScene = new CreditScreen(engine);
                break;
            case ACHIEVEMENT_BUTTON:
                nextScene = new HelpScreen(engine);
                break;
            case OPTION_BUTTON:
                nextScene = new OptionScreen(engine);
                break;
            case PLAY_BUTTON:
                nextScene = new StageSelection(engine);
                break;
            case QUIT_BUTTON:
                exit = true;
                break;
            case SHOP_BUTTON:
                nextScene = new ShopScreen(engine);
                break;
        }

        if (exit) {
            closeApp();
        } else {
            changeScene(nextScene);
        }
    }
}
