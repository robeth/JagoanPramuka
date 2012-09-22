/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.logic;

import com.chocoarts.component.Timer;
import com.chocoarts.debug.Debug;
import dart.game.main.MainProfile;
import dart.game.scene.MainPlay;
import dart.game.sound.SoundManager;
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Layer;
import dart.game.sprite.*;
import java.io.IOException;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Maviosso
 */
public class World {

    private static final int LANE_NUMBER = 3;
    private static final int CHARGED_DELAY = 150;
    private Hero[] heroes;
    private Vector[] enemiesLanes;
    private Vector coins;
    private Lanes lanes;
    private Random r;
    private EnemyGenerator eg;
    private Combo comboObj;
    private FinalAttack faObj;
    private HUD hud;
    private MainPlay context;
    private boolean isWaveEnd;
    private boolean commenceCA;
    private int CAheroes;
    private long lastCharged;
    private int chargePos;
    private MainProfile profile;
    private boolean chargeMiss;
    private int state;
    private static int NORMAL = 0,
            FINAL_ATTACK = 1;
    private int finalAttackState;
    private int chargedAttacState;
    private static int ONE = 1,
            TWO = 2,
            THREE = 3,
            END = 5,
            EARTHQUAKE = 4,
            FOUR = 4;
    private Timer finalAttackTimer;
    private Timer earthquakeTimer;
    private Timer chargeAttackTimer;
    private Image arya, bima, cinta;
    private int deltaX;
    private int deltaY;
    private Random randomizer;
    private boolean firstCharge;
    private int kuntilanakCount;
    private int finalAttackCount;
    private boolean noFAlv5;
    private SoundManager sm;

    public World(MainPlay context, Hero[] heroes, HUD hud, int level) {
        this.context = context;
        profile = (MainProfile) context.engine.getProfile();

        this.hud = hud;


        //Lanes component
        lanes = new Lanes(LANE_NUMBER, 40, 2, 70);
        comboObj = new Combo();
        faObj = new FinalAttack();


        //Heroes component
        this.heroes = heroes;

        enemiesLanes = new Vector[LANE_NUMBER];
        coins = new Vector();
        for (int i = 0; i < LANE_NUMBER; i++) {
            enemiesLanes[i] = new Vector();
        }

        fixHeroesPlacement();
        r = new Random();

        int[] en = new int[5];
        en[0] = EnemyGenerator.MALING;
        en[1] = EnemyGenerator.MAFIA;
        en[2] = EnemyGenerator.KUNTILANAK;
        en[3] = EnemyGenerator.ALIEN;
        en[4] = EnemyGenerator.BOS_CICAK;
        eg = new EnemyGenerator(this, 1000, en, 1000, level, enemiesLanes, lanes);

        isWaveEnd = false;
        commenceCA = false;
        lastCharged = 0;
        chargePos = 0;
        chargeMiss = true;
        state = NORMAL;
        chargeAttackTimer = new Timer(World.CHARGED_DELAY);
        finalAttackTimer = new Timer(1000);
        earthquakeTimer = new Timer(2000);
        //chargeAttackTimer = new Timer(1000);
        deltaX = deltaY = 0;
        randomizer = new Random();
        
        firstCharge = true;
        
        if (level == 5){
            noFAlv5 = true;
        } else {
            noFAlv5 = false;
        }
        
        finalAttackCount = 0;
        kuntilanakCount = profile.getKuntilanakKill();
        
        int[] sfxIDs = new int[4];
        sfxIDs[0] = SoundManager.SFX_THUNDER;
        sfxIDs[1] = SoundManager.SFX_ULTI_EARTHQUAKE;
        sfxIDs[2] = SoundManager.SFX_PUNCH;
        sfxIDs[3] = SoundManager.SFX_HURT_MAN2;
        
        
        sm = SoundManager.getInstance();
        sm.playBG(SoundManager.BM_HIMNE);
        sm.initSFXs(sfxIDs);
        
        
        try {
            arya = Image.createImage("/aryabox.png");
            bima = Image.createImage("/bimabox.png");
            cinta = Image.createImage("/cintabox.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void resetFinalAttackAnimation() {
        finalAttackTimer.reset();
        earthquakeTimer.reset();
        finalAttackState = ONE;
    }

    public void update(long currentTime, boolean[] attackStatus) {
        if (state == NORMAL) {
            if (isLevelEnd()) {
                context.setGameState(MainPlay.WIN_STATE);
                return;
            }
            updateEnemies(currentTime);
            updateCoins(currentTime);
            comboObj.update(currentTime);

            for (int i = 0; i < 3; i++) {
                heroes[i].update(currentTime, attackStatus[i]);
                if (heroes[i].isAttacking()) {
                    if (!heroes[i].isCharged()) {
                        applyAttack(heroes[i].getAttack(), i, 0, true);
                        heroes[i].finishAttack(true);
                    } else {
                        commenceCA = true;
                        //lastCharged = currentTime;
                        //chargeAttackTimer.reset();
                        heroes[i].finishAttack(false);
                        CAheroes = i;
                    }
                }
            }
            eg.update(currentTime);

            for (int i = 0; i < heroes.length; i++) {
                heroes[i].update(currentTime);
            }

            // disini lakukan 
            if (commenceCA) {
                if (firstCharge){
                        if (!applyAttack(heroes[CAheroes].getAttack(), CAheroes, 0, false)){
                            chargeMiss = false;
                        }
                        if (!applyAttack(heroes[CAheroes].getAttack(), CAheroes, 1, false)){
                            chargeMiss = false; 
                        }
                        heroes[CAheroes].finishAttack(true);
                        chargePos++;
                        firstCharge = false;
                        if (heroes[CAheroes].getAttack().getStack() == 1) {
                            chargePos = 0;
                            commenceCA = false;
                            heroes[CAheroes].resetAttack();
                            heroes[CAheroes].setCharge(false);
                            heroes[CAheroes].finishAttack(false);
                            firstCharge = true;
                            if (chargeMiss){
                                comboObj.miss();
                            }
                            chargeMiss = true;
                        }
                }
                if (chargeAttackTimer.isTicked(currentTime)) {
                    if (chargePos == 3) {
                        if (!applyAttack(heroes[CAheroes].getAttack(), CAheroes, 4, false)){
                            chargeMiss = false; 
                        }
                        heroes[CAheroes].setCharge(false);
                        heroes[CAheroes].finishAttack(false);
                        chargePos = 0;
                        commenceCA = false;
                        heroes[CAheroes].resetAttack();
                        firstCharge = true;
                        if (chargeMiss){    
                            comboObj.miss();
                        }
                        chargeMiss = true;
                    } else if (chargePos == 2) {
                        if (!applyAttack(heroes[CAheroes].getAttack(), CAheroes, 3, false)){
                            chargeMiss = false; 
                        }
                        heroes[CAheroes].finishAttack(false);
                        chargePos++;
                        if (heroes[CAheroes].getAttack().getStack() == 3) {
                            chargePos = 0;
                            commenceCA = false;
                            heroes[CAheroes].resetAttack();
                            heroes[CAheroes].setCharge(false);
                            heroes[CAheroes].finishAttack(false);
                            firstCharge = true;
                            if (chargeMiss){
                                comboObj.miss();
                            }
                            chargeMiss = true;
                        }
                    } else if (chargePos == 1) {
                        if (!applyAttack(heroes[CAheroes].getAttack(), CAheroes, 2, false)){
                            chargeMiss = false; 
                        }
                        heroes[CAheroes].finishAttack(false);
                        chargePos++;
                        if (heroes[CAheroes].getAttack().getStack() == 2) {
                            chargePos = 0;
                            commenceCA = false;
                            heroes[CAheroes].resetAttack();
                            heroes[CAheroes].setCharge(false);
                            heroes[CAheroes].finishAttack(false);
                            firstCharge = true;
                            if (chargeMiss){
                                comboObj.miss();
                            }
                           chargeMiss = true;
                        }
                    }
                    chargeAttackTimer.reset();
                }
            }
        } else if (state == FINAL_ATTACK) {
            if (finalAttackState != EARTHQUAKE) {
                if (finalAttackTimer.isTicked(currentTime)) {
                    if (finalAttackState == ONE) {
                        finalAttackState = TWO;
                        sm.playSFX(SoundManager.SFX_THUNDER);
                    } else if (finalAttackState == TWO) {
                        finalAttackState = THREE;
                        sm.playSFX(SoundManager.SFX_THUNDER);
                    } else if (finalAttackState == THREE) {
                        finalAttackState = EARTHQUAKE;
                        earthquakeTimer.reset();
                        attackLane(0);
                        attackLane(1);
                        attackLane(2);
                        faObj.resetBar();
                        sm.playSFX(SoundManager.SFX_THUNDER);
                    } else if (finalAttackState == EARTHQUAKE) {
                        sm.playSFX(SoundManager.SFX_ULTI_EARTHQUAKE);
                    }
                    finalAttackTimer.reset();
                }
            } else if (earthquakeTimer.isTicked(currentTime)) {
                state = NORMAL;
                deltaY = deltaX = 0;
                this.startEnemies();
            } else {
                deltaX = randomizer.nextInt(5);
                deltaY = randomizer.nextInt(5);
            }


        }
    }

    public void paint(Graphics g) {
        comboObj.pseudoPaint(g);
        
        for (int i = 0; i < heroes.length; i++) {
            heroes[i].pseudoPaint(g);
        }

        for (int j = 0; j < enemiesLanes.length; j++) {
            for (int i = 0; i < enemiesLanes[j].size(); i++) {
                Enemy p = (Enemy) enemiesLanes[j].elementAt(i);
                if (p != null) {
                    p.pseudoPaint(g);
                }
            }
        }

        for (int i = 0; i < coins.size(); i++) {
            CoinEffect c = (CoinEffect) coins.elementAt(i);
            if (c != null) {
                c.paint(g);
            }
        }

        if (state == FINAL_ATTACK && finalAttackState < EARTHQUAKE) {
            if (finalAttackState > 0) {
                g.drawImage(arya, 0, 240, Graphics.LEFT | Graphics.BOTTOM);
            }
            if (finalAttackState > ONE) {
                g.drawImage(bima, 0, 0, Graphics.LEFT | Graphics.TOP);
            }
            if (finalAttackState > TWO) {
                g.drawImage(cinta, 320, 0, Graphics.RIGHT | Graphics.TOP);
            }
        }

    }
    
    public void pause(){
        sm.pauseBG();
    }

    private void updateEnemies(long currentTime) {
        for (int i = 0; i < enemiesLanes.length; i++) {
            Vector enemies = enemiesLanes[i];
            for (int counter = 0; counter < enemies.size();) {
                Enemy locEnemy = (Enemy) enemies.elementAt(counter);
                if (locEnemy != null) {
                    locEnemy.update(currentTime);
                    int x = locEnemy.getX();
                    if (x <= -50) {
                        enemies.setElementAt(null, counter);
                        enemies.removeElementAt(counter);
                        this.comboObj.miss();
                        if (locEnemy.getType() == Enemy.BOSS){
                            hud.stealed();
                            hud.stealed();
                            hud.stealed();
                        }
                        if (hud.stealed()) {
                            context.setGameState(MainPlay.LOSE_STATE);
                        }
                    } else if (locEnemy.getState() == Enemy.DECAY) {
                        if (locEnemy.getJenis() == EnemyGenerator.KUNTILANAK){
                            kuntilanakCount ++;
                        }
                        enemies.removeElementAt(counter);
                    } else {
                        counter++;
                    }

                }
            }
        }
    }

    public void startEnemies() {
        for (int i = 0; i < enemiesLanes.length; i++) {
            Vector enemies = enemiesLanes[i];
            for (int counter = 0; counter < enemies.size(); counter++) {
                Enemy locEnemy = (Enemy) enemies.elementAt(counter);
                if (locEnemy != null) {
                    locEnemy.reset();
                }
            }
        }
    }

    private void updateCoins(long currentTime) {
        for (int i = 0; i < coins.size();) {
            CoinEffect c = (CoinEffect) coins.elementAt(i);
            if (c != null) {
                c.update(currentTime);
                int x = c.getX();
                int y = c.getY();

                if (x >= 220 || y <= 20) {
                    hud.incMoney(c.getAmount());
                    coins.setElementAt(null, i);
                    coins.removeElementAt(i);
                } else {
                    i++;
                }
            }
        }
    }

    private void fixHeroesPlacement() {
        for (int i = 0; i < heroes.length; i++) {
            heroes[i].setPosition(0, lanes.getY(i));
        }
    }

    //Called by enemy generator
    public void addEnemy(Enemy specEnemy) {
        int laneIndex = r.nextInt(LANE_NUMBER);

        setEnemyPosition(specEnemy, laneIndex);

        enemiesLanes[laneIndex].addElement(specEnemy);
    }

    public void addCoin(int coinAmount, int posX, int posY) {
        Image coinImage = null;

        try {
            if (coinAmount == Enemy.GOLD_AMOUNT) {
                coinImage = Image.createImage("/Gold.png");
            } else if (coinAmount == Enemy.SILVER_AMOUNT) {
                coinImage = Image.createImage("/Silver.png");
            } else if (coinAmount == Enemy.BRONZE_AMOUNT) {
                coinImage = Image.createImage("/Bronze.png");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        CoinEffect coinEffect = new CoinEffect(coinImage, 15, 15, coinAmount);
        coinEffect.setPosition(posX, posY);

        coins.addElement(coinEffect);

    }

    void setEnemyPosition(Enemy e, int laneIndex) {
        if (e.getType() == Enemy.BOSS){
            e.setPosition(320, lanes.getY(laneIndex)-18);
        } else {
            e.setPosition(320, lanes.getY(laneIndex));
        }
    }

    private boolean applyAttack(AttackArea attack, int laneIndex, int chargeCount, boolean miss) {
        boolean missed = true;
        sm.playSFX(SoundManager.SFX_PUNCH);
        for (int i = 0; i < enemiesLanes[laneIndex].size(); i++) {
            Enemy e = (Enemy) enemiesLanes[laneIndex].elementAt(i);
            if (e != null && attack.canDamage(e, chargeCount)) {
                sm.playSFX(SoundManager.SFX_HURT_MAN2);
                if (e.getType() == Enemy.BOSS) {
                    int newLane = ((Boss) e).changeLane();
                    System.out.println("new Lane =" + newLane);
                    enemiesLanes[laneIndex].removeElementAt(i);
                    i--;
                    enemiesLanes[newLane].addElement(e);
                }
                if (e.attacked(attack.getAttackDamage())) {
                    //i--;
                    hud.incScore(e.getScore());
                    int c = e.getCoin();
                    if (c > 0) {
                        this.addCoin(c, e.getX(), e.getY());
                    }
                    comboObj.hit(1);
                    faObj.incBar(comboObj.getComboStack());
                } 
                missed = false;
            }
        }
        if (missed && miss) {
            comboObj.miss();
            Debug.println("miss" + comboObj.getComboStack());
        }

        return missed;
    }

    public void applyFinalAttack() {

        if (faObj.canDo()) {
            resetFinalAttackAnimation();
            state = FINAL_ATTACK;
            finalAttackCount++;
            if (noFAlv5){
                noFAlv5 = false;
            }
        }
    }

    private void attackLane(int laneIndex) {
        int damage = heroes[0].getAttack().getAttackDamage() * faObj.getMultiplier();
        for (int i = 0; i < enemiesLanes[laneIndex].size(); i++) {
            Enemy e = (Enemy) enemiesLanes[laneIndex].elementAt(i);
            if (e != null) {
                if (e.attacked(damage)) {
                    e.setState(Enemy.DYING);
                    //i--;
                    hud.incScore(e.getScore());
                }
                Debug.println("damaged" + damage);
            }
        }
    }

    public Combo getComboObj() {
        return comboObj;
    }

    public FinalAttack getFaObj() {
        return faObj;
    }

    public void notifyWaveEnd() {
        isWaveEnd = true;
    }

    private boolean isLevelEnd() {
        return isWaveEnd && enemiesLanes[0].isEmpty() && enemiesLanes[1].isEmpty()
                && enemiesLanes[2].isEmpty();
    }

    public int getHUDMoney() {
        return hud.getMoney();
    }

    public int getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(int deltaX) {
        this.deltaX = deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(int deltaY) {
        this.deltaY = deltaY;
    }
    
    public int getKuntilanakCount(){
        return kuntilanakCount;
    }
    
    public int getFACount(){
        return finalAttackCount;
    }
    
    public boolean getNoFAlv5(){
        return noFAlv5;
    }
}
