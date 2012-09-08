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
import dart.game.main.MainProfile;
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
            LOSE_STATE = 1019,
            PRE_STATE = 9090;
    public static final String AYAM = "/1_ayam.png",
            KAMBING = "/2_kambing.png",
            SAPI = "/3_sapi.png",
            ANOA = "4_anoa.png",
            BABIRUSA = "5_babirusa.png",
            BEKANTAN = "6_bekantan.png",
            BERUANG_MADU = "7_beruangmadu.png",
            ENGGANG = "8_enggang.png",
            BINTURUNG = "9_binturung.png",
            BADAK = "10_badak.png",
            MALEO = "11_maleo.png",
            HARIMAU = "12_harimau.png",
            CENDRAWASIH = "13_cendrawasih.png",
            KOMODO = "14_komodo.png",
            GAJAH = "15_gajah.png",
            AYAM_NO = "/1_ayam_no.png",
            KAMBING_NO = "/2_kambing_no.png",
            SAPI_NO = "/3_sapi_no.png",
            ANOA_NO = "4_anoa_no.png",
            BABIRUSA_NO = "5_babirusa_no.png",
            BEKANTAN_NO = "6_bekantan_no.png",
            BERUANG_MADU_NO = "7_beruangmadu_no.png",
            ENGGANG_NO = "8_enggang_no.png",
            BINTURUNG_NO = "9_binturung_no.png",
            BADAK_NO = "10_badak_no.png",
            MALEO_NO = "11_maleo_no.png",
            HARIMAU_NO = "12_harimau_no.png",
            CENDRAWASIH_NO = "13_cendrawasih_no.png",
            KOMODO_NO = "14_komodo_no.png",
            GAJAH_NO = "15_gajah_no.png";
    private Image aryaImage,
            bimaImage,
            cintaImage,
            enemyImage,
            backgroundImage,
            winImage,
            loseImage,
            oneImage,
            twoImage,
            threeImage,
            startImage;
    private Hero heroes[];
    private ChocoSprite background;
    private World world;
    private HUD hud;
    private boolean[] keyDownStates;
    private int gameState;
    private CustomFont berlin, gothic;
    private MainProfile profile;
    private int level;
    private int curScore;
    // Atrributes of Pre Game
    private Timer timer;
    private int preState;
    private static final int ZERO = 0,
            ONE = 1,
            TWO = 2,
            THREE = 3,
            START = 4;
    // Attributes of Post Game
    private int postState, animCounter;
    private static final int SCORE = 1,
            COMBO = 2,
            ANIMAL = 3,
            CONFIRM = 4,
            ANIM1 = 0,
            ANIM2 = 1,
            ANIM3 = 2;
    private Image currentTutorialImage;
    private int currentTutorialIndex;
    private boolean isTutorial;

    MainPlay(Engine engine, int level) {
        super(engine);
        this.level = level;
    }

    public void init() throws Exception {
        try {

            aryaImage = Image.createImage("/aryas.png");
            bimaImage = Image.createImage("/bimas.png");
            cintaImage = Image.createImage("/cintas.png");
            winImage = Image.createImage("/Sukses.png");
            loseImage = Image.createImage("/Gagal.png");
            oneImage = Image.createImage("/1.png");
            twoImage = Image.createImage("/2.png");
            threeImage = Image.createImage("/3.png");
            startImage = Image.createImage("/semangat.png");


            heroes = new Hero[3];
            heroes[0] = new Hero(aryaImage, 50, 43, 200, Hero.ARYA);
            heroes[1] = new Hero(bimaImage, 42, 44, 200, Hero.BIMA);
            heroes[2] = new Hero(cintaImage, 46, 44, 100, Hero.CINTA);

            keyDownStates = new boolean[4];

            //profile
            profile = (MainProfile) engine.getProfile();

            //hud
            berlin = new CustomFont("/font/berlinSansFB12White");
            gothic = new CustomFont("/font/gothic10White");

            gameState = PRE_STATE;
            postState = SCORE;
            curScore = 0;
            timer = new Timer(1000);

            if (level == 1){
                hud = new HUD(berlin, gothic, profile.getMoney(),AYAM,KAMBING,SAPI,AYAM_NO,KAMBING_NO,SAPI_NO);
                backgroundImage = Image.createImage("/map1.jpg");
            } else if (level == 2){
                hud = new HUD(berlin, gothic, profile.getMoney(),ANOA,BABIRUSA,BEKANTAN,ANOA_NO,BABIRUSA_NO,BEKANTAN_NO);
                backgroundImage = Image.createImage("/map2.jpg");
            } else if (level == 3){
                hud = new HUD(berlin, gothic, profile.getMoney(),BERUANG_MADU,ENGGANG,BINTURUNG,BERUANG_MADU_NO,ENGGANG_NO,BINTURUNG_NO);
                backgroundImage = Image.createImage("/map3.jpg");
            } else if (level == 4){
                hud = new HUD(berlin, gothic, profile.getMoney(),BADAK,MALEO,HARIMAU,BADAK_NO,MALEO_NO,HARIMAU_NO);
                backgroundImage = Image.createImage("/map4.jpg");
            } else if (level == 5){
                hud = new HUD(berlin, gothic, profile.getMoney(),CENDRAWASIH,KOMODO,GAJAH,CENDRAWASIH_NO,KOMODO_NO,GAJAH_NO);
                backgroundImage = Image.createImage("/map5.jpg");
            }
            
            background = new ChocoSprite(backgroundImage);
            world = new World(this, heroes, hud,level);
            background.setPosition(0, 0);
            
            isTutorial = level == 1;
            currentTutorialIndex = 0;
            if (isTutorial) {
                currentTutorialImage = getTutorialImage(0);
            }
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
        if (isTutorial) {
        } else {
            if (gameState == PLAY_STATE) {
                updateKeyStates();
                world.update(currentTime, keyDownStates);
                hud.update(world.getComboObj(), world.getFaObj());
            } else if (gameState == PRE_STATE) {
                if (preState == ZERO) {
                    timer.start();
                    preState = ONE;
                } else if (timer.isTicked(currentTime)) {
                    timer.reset();
                    if (++preState > START) {
                        gameState = PLAY_STATE;
                    }
                }
            } else if (isGameEnd()) {
                if (postState == SCORE) {
                    if (curScore < hud.getScore()) {
                        curScore++;
                    } else {
                        postState = COMBO;
                        timer.reset();
                    }
                } else if (postState == COMBO) {
                    if (timer.isTicked(currentTime)) {
                        postState = ANIMAL;
                        animCounter = ANIM1;
                        timer.reset();

                    }
                } else if (postState == ANIMAL) {
                    if (timer.isTicked(currentTime)) {
                        timer.reset();
                        animCounter++;
                        if (animCounter > ANIM3) {
                            postState = CONFIRM;
                        }
                    }
                }
            }
        }
    }

    public void paint(Graphics g) {
        try {
            if (isTutorial) {
                g.drawImage(currentTutorialImage, 0, 0, Graphics.LEFT | Graphics.TOP);
            } else {
                background.setPosition(world.getDeltaX(), world.getDeltaY());
                background.paint(g);
                world.paint(g);
                hud.paint(g);

                if (gameState == PRE_STATE) {
                    Image preImage = null;
                    switch (preState) {
                        case ZERO:
                            preImage = oneImage;
                            break;
                        case ONE:
                            preImage = oneImage;
                            break;
                        case TWO:
                            preImage = twoImage;
                            break;
                        case THREE:
                            preImage = threeImage;
                            break;
                        case START:
                            preImage = startImage;
                            break;
                    }
                    g.drawImage(preImage, 160, 120, Graphics.HCENTER | Graphics.VCENTER);
                }

                if (isGameEnd()) {
                    Image notificationImage = (gameState == WIN_STATE) ? winImage : loseImage;
                    g.drawImage(notificationImage, g.getClipWidth() / 2, g.getClipHeight() / 2, Graphics.HCENTER | Graphics.VCENTER);

                    berlin.paintString(g, Integer.toString(curScore), 150, 110, Graphics.LEFT | Graphics.TOP);
                    if (postState >= COMBO) {
                        berlin.paintString(g, Integer.toString(world.getComboObj().getHighestCombo()), 150, 130, Graphics.LEFT | Graphics.TOP);
                    }

                    if (postState >= ANIMAL) {
                        int life = hud.getLife();

                        if (animCounter > ANIM1) {
                            if (life >= 1) {
                                g.drawImage(hud.getAnimal1(), 150, 150, 0);
                            } else {
                                g.drawImage(hud.getAnimal1Fail(), 150, 150, 0);
                            }
                        }
                        if (animCounter > ANIM2) {
                            if (life >= 2) {
                                g.drawImage(hud.getAnimal2(), 170, 150, 0);
                            } else {
                                g.drawImage(hud.getAnimal2Fail(), 170, 150, 0);
                            }
                        }
                        if (animCounter > ANIM3) {
                            if (life >= 3) {
                                g.drawImage(hud.getAnimal3(), 190, 150, 0);
                            } else {
                                g.drawImage(hud.getAnimal3Fail(), 190, 150, 0);
                            }
                        }
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
        } else if (keyCode == GameCanvas.FIRE) {
            if (isGameEnd()) {
                switch (postState) {
                    case SCORE:
                        curScore = hud.getScore();
                        break;
                    case COMBO:
                        break;
                    case ANIMAL:
                        animCounter = ANIM3 + 1;
                        break;
                    case CONFIRM:
                        if (curScore == hud.getScore()) {
                            profile.setMoney(world.getHUDMoney());
                            profile.setBestCombo(level - 1, Math.max(profile.getBestCombo(level - 1), world.getComboObj().getHighestCombo()));
                            profile.setHighscore(level - 1, Math.max(profile.getHighscore(level - 1), hud.getScore()));
                            profile.setSavedAnimals(level - 1, Math.max(profile.getSavedAnimals(level - 1), hud.getLife()));

                            if (gameState == WIN_STATE) {
                                profile.setLastLevel(Math.max(profile.getLastLevel(), level));
                            }

                            MainMenu mainMenu = new MainMenu(engine);
                            changeScene(mainMenu);
                        } else {
                            curScore = hud.getScore();
                        }
                        break;
                }
            } else {

                if (isTutorial) {
                    currentTutorialIndex++;
                    if (currentTutorialIndex > 3) {
                        isTutorial = false;
                    }
                    currentTutorialImage = getTutorialImage(currentTutorialIndex);
                }



            }

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

    public boolean isGameEnd() {
        return gameState == WIN_STATE || gameState == LOSE_STATE;
    }
    private static final String[] tutorials = {
        "/help_1.png", "/help_2.png", "help_3.png", "help_4.png"};

    private Image getTutorialImage(int index) {
        Image image = null;
        try {
            image = Image.createImage(tutorials[index]);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return image;
    }
}
