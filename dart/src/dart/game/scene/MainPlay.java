/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.scene;

import com.chocoarts.Engine;
import com.chocoarts.component.Timer;
import com.chocoarts.debug.Debug;
import com.chocoarts.drawing.AnimatedSprite;
import com.chocoarts.drawing.ChocoSprite;
import com.chocoarts.scene.Scene;
import com.chocoarts.text.CustomFont;
import dart.game.logic.World;
import dart.game.sprite.Enemy;
import dart.game.sprite.HUD;
import dart.game.sprite.Hero;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

/**
 *
 * @author Agung
 */
public class MainPlay extends Scene {

        
    private Image aryaImage, bimaImage, cintaImage, enemyImage, backgroundImage;
    private Hero heroes[];
    private ChocoSprite background; 
    
    private World world;
    private HUD hud;
    private boolean[] keyDownStates;

    MainPlay(Engine engine) {
        super(engine);
    }

    public void init() throws Exception {
        try {
            
            aryaImage = Image.createImage("/arya_mini.png");
            bimaImage = Image.createImage("/bima_mini.png");
            cintaImage = Image. createImage("/cinta_mini.png");
            backgroundImage = Image.createImage("/map.jpg");
            
            heroes = new Hero[3];
            heroes[0] = new Hero(aryaImage, 37, 45, 0,Hero.ARYA);
            heroes[1] = new Hero(bimaImage, 37, 45, 0,Hero.BIMA);
            heroes[2] = new Hero(cintaImage, 41,45,0,Hero.CINTA);
            background = new ChocoSprite(backgroundImage);
           
            background.setPosition(0,0);
            keyDownStates = new boolean[4];
   
            
            //hud
            CustomFont berlin = new CustomFont("/font/berlinSansFB12White");
            CustomFont gothic = new CustomFont("/font/gothic10White");
            hud = new HUD(berlin, gothic);
            
            world = new World(heroes,hud);
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
        updateKeyStates();
        world.update(currentTime, keyDownStates);
        hud.update(world.getComboObj(),world.getFaObj());
    }

    public void paint(Graphics g) {
        try {
            background.paint(g);
            world.paint(g);
            hud.paint(g);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void keyPressed(int keyCode, int rawKeyCode) {
        if (rawKeyCode == GameCanvas.KEY_NUM5){
            Debug.println("Masuk suu");
            world.applyFinalAttack();
        }
        
    }
    
    public void updateKeyStates(){
        int keyState = engine.getKeyStates();
        keyDownStates[0] = keyState == GameCanvas.UP_PRESSED;
        keyDownStates[1] = keyState == GameCanvas.LEFT_PRESSED;
        keyDownStates[2] = keyState == GameCanvas.DOWN_PRESSED;
    }

    public void pointerPressed(int i, int i1) {
    }

    public void sleep() {
    }

}
