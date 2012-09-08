/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.logic;

import com.chocoarts.debug.Debug;
import dart.game.main.MainProfile;
import dart.game.scene.MainPlay;
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
    private static final int CHARGED_DELAY = 200;
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

    public World(MainPlay context, Hero[] heroes,HUD hud,int level){
        this.context = context;
        profile = (MainProfile)context.engine.getProfile();
        
        this.hud = hud;
        
        
        //Lanes component
        lanes = new Lanes(LANE_NUMBER, 40, 2, 70);
        comboObj = new Combo();
        faObj = new FinalAttack();
        
        
        //Heroes component
        this.heroes =  heroes;
        
        enemiesLanes = new Vector[LANE_NUMBER];
        coins = new Vector();
        for (int i = 0; i < LANE_NUMBER; i++){
            enemiesLanes[i] = new Vector();
        }
        
        fixHeroesPlacement();
        r = new Random();
        
        int[] en = new int[3];
        en[0] = EnemyGenerator.MALING;
        en[1] = EnemyGenerator.MAFIA;
        en[2] = EnemyGenerator.KUNTILANAK;
        eg = new EnemyGenerator(this, 1000, en, 1000,level);
        
        isWaveEnd = false;
        commenceCA = false;
        lastCharged = 0;
        chargePos = 0;
        chargeMiss = true;
    }

    public void update(long currentTime, boolean[] attackStatus){
        if(isLevelEnd()){
            context.setGameState(MainPlay.WIN_STATE);
            return;
        }
        updateEnemies(currentTime);
        updateCoins (currentTime);

        for(int i = 0; i< 3; i++){
            heroes[i].update(currentTime, attackStatus[i]);
            if(heroes[i].isAttacking()){
                if (!heroes[i].isCharged()){
                    applyAttack(heroes[i].getAttack(),i,0,true);
                    heroes[i].finishAttack(true);
                } else {
                    commenceCA = true;
                    lastCharged = currentTime;
                    CAheroes = i;
                }
            }
        }     
        eg.update(currentTime);
        
        for(int i =0; i < heroes.length; i++){
            heroes[i].update(currentTime);
        }
        
        // disini lakukan 
        if (commenceCA){
            
            if (currentTime - lastCharged >= CHARGED_DELAY * 3 && chargePos == 3){
                chargeMiss = applyAttack(heroes[CAheroes].getAttack(),CAheroes,4,chargeMiss);
                heroes[CAheroes].setCharge(false);
                heroes[CAheroes].finishAttack(false);
                chargePos = 0;
                commenceCA = false;
                heroes[CAheroes].resetAttack();
            } else if (currentTime - lastCharged >= CHARGED_DELAY * 2 && chargePos == 2){
                chargeMiss = applyAttack(heroes[CAheroes].getAttack(),CAheroes,3,chargeMiss);
                heroes[CAheroes].finishAttack(false);
                chargePos ++;
                if (heroes[CAheroes].getAttack().getStack() == 3){
                    chargePos = 0;
                    commenceCA = false;
                    heroes[CAheroes].resetAttack();
                    heroes[CAheroes].setCharge(false);
                    heroes[CAheroes].finishAttack(false);
                }
            } else if (currentTime - lastCharged >= CHARGED_DELAY * 1 && chargePos == 1){
                chargeMiss = applyAttack(heroes[CAheroes].getAttack(),CAheroes,2,chargeMiss);
                heroes[CAheroes].finishAttack(false);
                chargePos ++;
                if (heroes[CAheroes].getAttack().getStack() == 2){
                    chargePos = 0;
                    commenceCA = false;
                    heroes[CAheroes].resetAttack();
                    heroes[CAheroes].setCharge(false);
                    heroes[CAheroes].finishAttack(false);
                }
            } else if (currentTime - lastCharged >= 0 && chargePos == 0){
                chargeMiss = applyAttack(heroes[CAheroes].getAttack(),CAheroes,1,true);
                heroes[CAheroes].finishAttack(true);
                chargePos ++;
                if (heroes[CAheroes].getAttack().getStack() == 1){
                    chargePos = 0;
                    commenceCA = false;
                    heroes[CAheroes].resetAttack();
                    heroes[CAheroes].setCharge(false);
                    heroes[CAheroes].finishAttack(false);
                }
            }
        }
    }

    public void paint(Graphics g){
        for(int i =0; i < heroes.length; i++){
            heroes[i].pseudoPaint(g);
        }
        
        for(int j = 0; j < enemiesLanes.length; j++){
            for(int i = 0; i < enemiesLanes[j].size(); i++){
                Enemy p = (Enemy)enemiesLanes[j].elementAt(i);
                if(p != null){
                    p.pseudoPaint(g);
                }
            }
        }
        
        for(int i = 0; i < coins.size(); i++){
                CoinEffect c = (CoinEffect)coins.elementAt(i);
                if(c != null){
                    c.paint(g);
                }
            }
        
        
    }
    
    private void updateEnemies(long currentTime) {
        for(int i = 0; i < enemiesLanes.length; i++){
            Vector enemies = enemiesLanes[i];
            for (int counter = 0; counter < enemies.size();) {
                Enemy locEnemy = (Enemy) enemies.elementAt(counter);
                if (locEnemy != null) {
                    locEnemy.update(currentTime);
                    int x = locEnemy.getX();
                    if (x <= 20) {
                        enemies.setElementAt(null, counter);
                        enemies.removeElementAt(counter);
                        this.comboObj.miss();
                        if (hud.stealed()){
                            context.setGameState(MainPlay.LOSE_STATE);
                        }
                    } else if (locEnemy.getState()== Enemy.DECAY){
                         enemies.removeElementAt(counter);
                    } else {
                        counter++;
                    }
                    
                }
            }
        }
    }
    
    private void updateCoins (long currentTime){
        for (int i=0; i< coins.size();){
            CoinEffect c = (CoinEffect) coins.elementAt(i);
            if (c!=null){
                c.update(currentTime);
                int x = c.getX();
                int y = c.getY();
                
                if (x >= 220 || y<=20){
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
        for(int i = 0; i < heroes.length; i++)
            heroes[i].setPosition(0, lanes.getY(i));
    }

    //Called by enemy generator
    public void addEnemy(Enemy specEnemy) {
        int laneIndex = r.nextInt(LANE_NUMBER);
        setEnemyPosition(specEnemy, laneIndex);
        
        enemiesLanes[laneIndex].addElement(specEnemy);
    }
    
    public void addCoin (int coinAmount, int posX, int posY){
        Image coinImage = null;
        
        try {
            if (coinAmount == 100){
                coinImage = Image.createImage("/Gold.png");
            } else if (coinAmount == 50){
                coinImage = Image.createImage("/Silver.png");
            } else if (coinAmount == 10){
                coinImage = Image.createImage("/Bronze.png");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        CoinEffect coinEffect = new CoinEffect(coinImage, 15,15,coinAmount);
        coinEffect.setPosition(posX, posY);
        
        coins.addElement(coinEffect);
        
    }
    
    void setEnemyPosition(Enemy e, int laneIndex){
        e.setPosition(320, lanes.getY(laneIndex));
    }

    private boolean applyAttack(AttackArea attack, int laneIndex,int chargeCount,boolean miss) {
        boolean missed = miss;
        for(int i = 0; i < enemiesLanes[laneIndex].size(); i++ ){
            Enemy e = (Enemy) enemiesLanes[laneIndex].elementAt(i);
            if(e!=null && attack.canDamage(e,chargeCount)){
                if (e.attacked(attack.getAttackDamage())){
                    //i--;
                    hud.incScore(e.getScore());
                    int c = e.getCoin();
                    if (c > 0){
                        this.addCoin(c, e.getX(), e.getY());
                    }
                }
                Debug.println("kombo ++");
                comboObj.hit(1);
                faObj.incBar(comboObj.getComboStack());
                missed = false;
            }
        }
        if (missed){
            comboObj.miss();
            Debug.println("miss" + comboObj.getComboStack() );
        }
        
        return missed;
    }
    
    public void applyFinalAttack() {
        int damage = heroes[0].getAttack().getAttackDamage()*faObj.getMultiplier();
        
        if (faObj.canDo()){
            attackLane(damage,0);
            attackLane(damage,1);
            attackLane(damage,2);
            faObj.resetBar();
        }
    }
    
    private void attackLane (int damage,int laneIndex) {
        for(int i =0; i < enemiesLanes[laneIndex].size(); i++ ){
            Enemy e = (Enemy) enemiesLanes[laneIndex].elementAt(i);
            if(e!=null){
                if (e.attacked(damage)){
                    enemiesLanes[laneIndex].removeElementAt(i);
                    i--;
                    hud.incScore(e.getScore());
                }
                Debug.println("damaged" + damage);
            }
        }
    }

    
    public Combo getComboObj(){
        return comboObj;
    }
    
    public FinalAttack getFaObj(){
        return faObj;
    }

    public void notifyWaveEnd() {
        isWaveEnd = true;
    }

    private boolean isLevelEnd() {
        return isWaveEnd && enemiesLanes[0].isEmpty() && enemiesLanes[1].isEmpty()
                && enemiesLanes[2].isEmpty();
    }
    
    public int getHUDMoney(){
        return hud.getMoney();
    }
    
}
