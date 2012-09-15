/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.sound;

import com.chocoarts.debug.Debug;
import java.io.IOException;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;

/**
 *
 * @author Maviosso
 */
public class SoundManager {

    public static final int BM_ALAM_LUAS = 0,
            BM_HIMNE = 1,
            SFX_BUTTON = 2,
            SFX_PUNCH = 3,
            SFX_HURT_ALIEN = 4,
            SFX_ULTI_EARTHQUAKE = 5,
            SFX_HURT_MAN = 6,
            SFX_PUNCH2 = 7,
            SFX_HURT_MAN2 = 8,
            SFX_POINT = 9,
            SFX_POINT2 = 10,
            SFX_THUNDER = 11;
    public static final String[][] SOUND_DATA = {
        {"/alamluas+drum.mid", "audio/midi"},
        {"/himne+drum.mid", "audio/midi"},
        {"/jump.wav", "audio/X-wav"},
        {"/clang.mp3", "audio/mpeg"},
        {"/alienhurt.mp3", "audio/mpeg"},
        {"/explosion.mp3", "audio/mpeg"},
        {"/hurt_man.mp3", "audio/mpeg"},
        {"/jesh.mp3", "audio/mpeg"},
        {"/moh.mp3", "audio/mpeg"},
        {"/poin.mp3", "audio/mpeg"},
        {"/poin2.mp3", "audio/mpeg"},
        {"/thunder.mp3", "audio/mpeg"}
    };
    private static final int STAND_BY = 111,
            PLAYING = 123,
            PAUSING = 1234;
    private int stateBM = STAND_BY,
            stateSFX = STAND_BY;
    private static SoundManager soundManager;
    private Player bmPlayer, sfxPlayer;
    private int activeBG, activeSFX;

    private SoundManager() {
        bmPlayer = sfxPlayer = null;
        activeBG = activeSFX = -1;
    }

    public static SoundManager getInstance() {
        if (soundManager == null) {
            soundManager = new SoundManager();
        }
        return soundManager;
    }

    public void playBG(int audioID) {
        if (activeBG == audioID) {
            //do nothing 
        } else {
            resetBGResources();
            try {
                bmPlayer = Manager.createPlayer(getClass().getResourceAsStream(SOUND_DATA[audioID][0]), SOUND_DATA[audioID][1]);
                bmPlayer.realize();
                bmPlayer.prefetch();
                bmPlayer.setLoopCount(-1);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (MediaException ex) {
                ex.printStackTrace();
            }
        }


        try {
            bmPlayer.start();
            stateBM = PLAYING;
        } catch (MediaException ex) {
            ex.printStackTrace();
        }
    }

    public void pauseBG() {
        if (bmPlayer != null && stateBM == PLAYING) {
            try {
                bmPlayer.stop();
                stateBM = PAUSING;
            } catch (MediaException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void resumeBG() {
        if (bmPlayer != null && stateBM == PAUSING) {
            try {
                bmPlayer.start();
                stateBM = PLAYING;
            } catch (MediaException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void stopBG() {
        resetBGResources();
    }

    private void resetBGResources() {
        if (bmPlayer != null && bmPlayer.getState() != Player.UNREALIZED) {
            bmPlayer.deallocate();
            bmPlayer.close();
            bmPlayer = null;
            stateBM = STAND_BY;
            System.gc();
        }
    }

    private void resetSFXResources() {
        if (sfxPlayer != null && sfxPlayer.getState() != Player.UNREALIZED) {
            System.out.println("on release SFX");
            sfxPlayer.deallocate();
            sfxPlayer.close();
            sfxPlayer = null;
            stateSFX = STAND_BY;
            System.gc();
        }
    }

    public void playSFX(int sfxID) {
        if (activeSFX == sfxID) {
            //do nothing 
        } else {
            resetSFXResources();

            try {
                sfxPlayer = Manager.createPlayer(getClass().getResourceAsStream(SOUND_DATA[sfxID][0]), SOUND_DATA[sfxID][1]);
                sfxPlayer.realize();
                sfxPlayer.prefetch();
                sfxPlayer.setLoopCount(1);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (MediaException ex) {
                ex.printStackTrace();
            }
        }


        try {
            sfxPlayer.start();
            stateBM = PLAYING;
        } catch (MediaException ex) {
            ex.printStackTrace();
        }
        Debug.printFree("Freeeeee:");
    }

    public void stopSFX() {
        resetSFXResources();
    }

    public void reset() {
        resetBGResources();
        resetSFXResources();
    }
}
