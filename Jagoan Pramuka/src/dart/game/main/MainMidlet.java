/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.main;

import com.chocoarts.ChocoMIDlet;
import com.chocoarts.Engine;
import dart.game.data.AchievementData;
import dart.game.scene.MainMenu;
import dart.game.scene.SplashScreen;
import dart.game.sound.SoundManager;
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
        profile.printStatus();
        
        //Buat scene level 1
        SplashScreen game = new SplashScreen(engine);
        engine.setFirstScene(game);
        AchievementData.profile = (MainProfile) engine.getProfile();
        SoundManager.profile = (MainProfile) engine.getProfile();
        //engine.re
        Display.getDisplay(this).setCurrent(engine);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        try {
            engine.getProfile().writeProfile();
            super.notifyDestroyed();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
