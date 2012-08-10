/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.scene;

import com.chocoarts.Engine;
import com.chocoarts.component.Timer;
import com.chocoarts.debug.Debug;
import com.chocoarts.drawing.ChocoSprite;
import com.chocoarts.scene.Scene;
import com.chocoarts.text.CustomFont;
import dart.game.logic.World;
import dart.game.sprite.HUD;
import dart.game.sprite.Hero;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

/**
 *
 * @author Agung
 */
public class MainPlay extends Scene {

    public static final int PLAY_STATE = 1092,
            WIN_STATE = 20998,
            LOSE_STATE = 1019;
    private Image aryaImage,
            bimaImage,
            cintaImage,
            enemyImage,
            backgroundImage,
            winImage,
            loseImage;
    private Hero heroes[];
    private ChocoSprite background;
    private World world;
    private HUD hud;
    private boolean[] keyDownStates;
    private int gameState;
    private CustomFont berlin, gothic;

    MainPlay(Engine engine) {
        super(engine);
    }

    public void init() throws Exception {
        try {

            aryaImage = Image.createImage("/arya_mini.png");
            bimaImage = Image.createImage("/bima_mini.png");
            cintaImage = Image.createImage("/cinta_mini.png");
            backgroundImage = Image.createImage("/map.jpg");
            winImage = Image.createImage("/Sukses.png");
            loseImage = Image.createImage("/Gagal.png");

            heroes = new Hero[3];
            heroes[0] = new Hero(aryaImage, 37, 45, 0, Hero.ARYA);
            heroes[1] = new Hero(bimaImage, 37, 45, 0, Hero.BIMA);
            heroes[2] = new Hero(cintaImage, 41, 45, 0, Hero.CINTA);
            background = new ChocoSprite(backgroundImage);

            background.setPosition(0, 0);
            keyDownStates = new boolean[4];


            //hud
            berlin = new CustomFont("/font/berlinSansFB12White");
            gothic = new CustomFont("/font/gothic10White");
            hud = new HUD(berlin, gothic);

            world = new World(this, heroes, hud);
            gameState = PLAY_STATE;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
        if (gameState == PLAY_STATE) {
            updateKeyStates();
            world.update(currentTime, keyDownStates);
            hud.update(world.getComboObj(), world.getFaObj());
        }
    }

    public void paint(Graphics g) {
        try {
            background.paint(g);
            world.paint(g);
            hud.paint(g);

            if (gameState != PLAY_STATE) {
                Image notificationImage = (gameState == WIN_STATE)? winImage : loseImage;
                g.drawImage(notificationImage, g.getClipWidth() / 2, g.getClipHeight() / 2, Graphics.HCENTER | Graphics.VCENTER);
                berlin.paintString(g, Integer.toString(hud.getScore()), 150, 110, Graphics.LEFT | Graphics.TOP);
                berlin.paintString(g, Integer.toString(world.getComboObj().getHighestCombo()), 150, 130, Graphics.LEFT | Graphics.TOP);
                if (gameState == WIN_STATE) {
                   int life = hud.getLife();
                    if (life >= 1) {
                        g.drawImage(hud.getAnimal1(), 150, 150, 0);
                    }
                    if (life >= 2) {
                        g.drawImage(hud.getAnimal2(), 170, 150, 0);
                    }
                    if (life >= 3) {
                        g.drawImage(hud.getAnimal3(), 190, 150, 0);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void keyPressed(int keyCode, int rawKeyCode) {
        if (rawKeyCode == GameCanvas.KEY_NUM5) {
            if (gameState == PLAY_STATE) {
                world.applyFinalAttack();
            } else {
                MainMenu mainMenu = new MainMenu(engine);
                changeScene(mainMenu);
            }
        } else if (keyCode == GameCanvas.FIRE && gameState != PLAY_STATE) {
            MainMenu mainMenu = new MainMenu(engine);
            changeScene(mainMenu);
        }

    }

    public void updateKeyStates() {
        int keyState = engine.getKeyStates();
        keyDownStates[0] = keyState == GameCanvas.UP_PRESSED;
        keyDownStates[1] = keyState == GameCanvas.LEFT_PRESSED;
        keyDownStates[2] = keyState == GameCanvas.DOWN_PRESSED;
    }

    public void pointerPressed(int i, int i1) {
    }

    public void sleep() {
    }

    public void setGameState(int newState) {
        this.gameState = newState;
    }
}
