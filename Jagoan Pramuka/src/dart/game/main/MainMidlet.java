/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.main;

import com.chocoarts.ChocoMIDlet;
import com.chocoarts.Engine;
import dart.game.scene.MainMenu;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.game.GameCanvas;

/**
 * @author Agung
 */
public class MainMidlet extends ChocoMIDlet {

    Engine engine;
    private MainProfile profile;

    public void startApp() {
        engine = new Engine(this, true, false);

        //Ambil save data permainan sebelumnya. increment
        profile = new MainProfile();
        engine.initProfile(profile, "JagoanPramuka0.3");

        //Buat scene level 1
        MainMenu game = new MainMenu(engine);
        engine.setFirstScene(game);

        //Register music
        engine.regisMusic("effect", "/sound/button select.mid");
        engine.regisMusic("crowd", "/sound/crowd.mid");
        
        
        //engine.re
        Display.getDisplay(this).setCurrent(engine);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        try {
            engine.getProfile().writeProfile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
