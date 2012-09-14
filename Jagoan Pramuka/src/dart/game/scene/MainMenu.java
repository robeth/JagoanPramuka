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
import dart.game.sprite.MenuButton;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

/**
 *
 * @author Maviosso
 */
public class MainMenu extends Scene {
    //Play
    //Shop
    //Option
    //Credit
    //Help

    private static final int PLAY_BUTTON = 0,
            SHOP_BUTTON = 1,
            OPTION_BUTTON = 2,
            //CREDIT_BUTTON = 3,
            //HELP_BUTTON = 4,
            QUIT_BUTTON = 3;
    private Image bgmenu, optionselect, shopselect, exitselect, playselect, optionunselect, shopunselect, exitunselect, playunselect, logo;
    private int pointer = PLAY_BUTTON;
    //private String[] buttonsName;
    //private CustomFont berlin;
    private MenuButton buttons[] = new MenuButton[4];
    private ChocoSprite bgsprite;
//        new DummyButton(250, 180, 60, 40), // help
//        new DummyButton(185, 180, 60, 40)};  // quit

    private SoundManager sm;
    public MainMenu(Engine engine) {
        super(engine);
    }

    public void init() throws Exception {
        //berlin = new CustomFont("/font/berlinSansFB12White");

        bgmenu = Image.createImage("/bgmenu.jpg");
        optionselect = Image.createImage("/option_select.png");
        optionunselect = Image.createImage("/option_unselect.png");
        playselect = Image.createImage("/play_select.png");
        playunselect = Image.createImage("/play_unselect.png");
        shopselect = Image.createImage("/shop_select.png");
        shopunselect = Image.createImage("/shop_unselect.png");
        exitselect = Image.createImage("/exit_select.png");
        exitunselect = Image.createImage("/exit_unselect.png");
        logo = Image.createImage("/logo.png");

        bgsprite = new ChocoSprite(bgmenu);
        buttons[0] = new MenuButton(playselect,150,35);
        buttons[1] = new MenuButton(shopunselect,150,35);
        buttons[2] = new MenuButton(optionunselect,150,35);
        buttons[3] = new MenuButton(exitunselect,150,35);
        bgsprite.setPosition(0, 0);

        for(int i = 0; i < buttons.length; i++){
            buttons[i].setPosition(170,100 + i*35);
        }

        /*buttonsName = new String[6];
        buttonsName[0] = "Play";
        buttonsName[1] = "Shop";
        buttonsName[2] = "Option";
        buttonsName[3] = "Credit";
        buttonsName[4] = "Tutorial";
        buttonsName[5] = "Quit";*/
        sm = SoundManager.getInstance();
        sm.playBG(SoundManager.BM_HIMNE);
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
    }

    public void paint(Graphics g) {
        bgsprite.paint(g);
        g.drawImage(logo, g.getClipWidth()/2, 0, Graphics.TOP | Graphics.HCENTER);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].paint(g);
        }
        /*for (int i = 0; i < buttonsName.length; i++) {
        if (pointer == i) {
        g.setColor(255, 0, 0);
        } else {
        g.setColor(0, 0, 255);
        }
        buttons[i].fill(g);
        buttons[i].drawString(g, berlin, buttonsName[i]);
        }*/
    }

    int gogogo = SoundManager.SFX_BUTTON;
    public void keyPressed(int keyCode, int rawKeyCode) {
        int prev = pointer;
        boolean beep = false;
        if (keyCode == GameCanvas.UP) {
            pointer--;
            beep = true;
        } else if (keyCode == GameCanvas.DOWN) {
            pointer++;
            beep = true;
        } else if (keyCode == GameCanvas.FIRE) {
            goToScene(pointer);
            beep = true;
        }

        pointer = pointer < 0 ? 3 : (pointer > 3 ? 0 : pointer);
        int next = pointer;
        
        if(beep){
            
            sm.playSFX(gogogo);
            gogogo = (++gogogo > SoundManager.SFX_THUNDER)? SoundManager.SFX_BUTTON : gogogo;
        }
        setButtonImage(prev,next);
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
           /* case CREDIT_BUTTON:
                nextScene = new CreditScreen(engine);
                break;
            case HELP_BUTTON:
                nextScene = new HelpScreen(engine);
                break;*/
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

    private void setButtonImage(int prev, int next) {
        switch (prev) {
            case OPTION_BUTTON:
                buttons[prev].setImage(optionunselect, 150, 35);
                break;
            case PLAY_BUTTON:
                buttons[prev].setImage(playunselect, 150, 35);
                break;
            case QUIT_BUTTON:
                buttons[prev].setImage(exitunselect, 150, 35);
                break;
            case SHOP_BUTTON:
                buttons[prev].setImage(shopunselect, 150, 35);
                break;
        }
        switch (next) {
            case OPTION_BUTTON:
                buttons[next].setImage(optionselect, 150, 35);
                break;
            case PLAY_BUTTON:
                buttons[next].setImage(playselect, 150, 35);
                break;
            case QUIT_BUTTON:
                buttons[next].setImage(exitselect, 150, 35);
                break;
            case SHOP_BUTTON:
                buttons[next].setImage(shopselect, 150, 35);
                break;
        }

    }
}
