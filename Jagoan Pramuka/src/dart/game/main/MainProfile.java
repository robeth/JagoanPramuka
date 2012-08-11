/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dart.game.main;

import com.chocoarts.network.Profile;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author Robeth Rahmatullah
 */
public class MainProfile extends Profile{

    /**
     * Setting variables (Sound)
     */
    private boolean sound;
    
    /**
     * Level informations
     */
    private int lastLevel;
    private int[] bestCombo, highscore, savedAnimals;
    
    /**
     * Shop Variables
     */
    private int money;
    private boolean hasTongkat, hasTali, hasKitchenKit;
    private boolean hasAcc1, hasAcc2, hasAcc3;
    

    public MainProfile(){
        reset();
    }

    public void reset() {
        sound = true;
        lastLevel = 1;
        
        bestCombo = new int[5];
        savedAnimals = new int[5];
        highscore = new int[5];
        
        money = 0;
        hasKitchenKit = false;
        hasAcc1 = hasAcc2 = hasAcc3 = hasTongkat = hasTali = false;
    }

    public void writeVariables(DataOutputStream stream) throws Exception {
        stream.writeBoolean(sound);
        stream.writeInt(lastLevel);
        for(int i = 0; i < 5; i++){
            stream.writeInt(bestCombo[i]);
            stream.writeInt(savedAnimals[i]);
            stream.writeInt(highscore[i]);
        }
        stream.writeInt(money);
        stream.writeBoolean(hasTongkat);
        stream.writeBoolean(hasTali);
        stream.writeBoolean(hasKitchenKit);
        stream.writeBoolean(hasAcc1);
        stream.writeBoolean(hasAcc2);
        stream.writeBoolean(hasAcc3);
        
    }

    public void readVariables(DataInputStream stream) throws Exception {
        sound = stream.readBoolean();
        lastLevel = stream.readInt();
        
        for(int i = 0; i < 5; i++){
            bestCombo[i] = stream.readInt();
            savedAnimals[i] = stream.readInt();
            highscore[i] = stream.readInt();
        }
        
        money = stream.readInt();
        hasTongkat = stream.readBoolean();
        hasTali = stream.readBoolean();
        hasKitchenKit = stream.readBoolean();
        hasAcc1 = stream.readBoolean();
        hasAcc2 = stream.readBoolean();
        hasAcc3 = stream.readBoolean();
    }
    

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }
    public boolean getSound(){
        return sound;
    }

    public boolean isHasAcc1() {
        return hasAcc1;
    }

    public void setHasAcc1(boolean hasAcc1) {
        this.hasAcc1 = hasAcc1;
    }

    public boolean isHasAcc2() {
        return hasAcc2;
    }

    public void setHasAcc2(boolean hasAcc2) {
        this.hasAcc2 = hasAcc2;
    }

    public boolean isHasAcc3() {
        return hasAcc3;
    }

    public void setHasAcc3(boolean hasAcc3) {
        this.hasAcc3 = hasAcc3;
    }

    public boolean isHasKitchenKit() {
        return hasKitchenKit;
    }

    public void setHasKitchenKit(boolean hasKitchenKit) {
        this.hasKitchenKit = hasKitchenKit;
    }

    public boolean isHasTali() {
        return hasTali;
    }

    public void setHasTali(boolean hasTali) {
        this.hasTali = hasTali;
    }

    public boolean isHasTongkat() {
        return hasTongkat;
    }

    public void setHasTongkat(boolean hasTongkat) {
        this.hasTongkat = hasTongkat;
    }

    public int getLastLevel() {
        return lastLevel;
    }

    public void setLastLevel(int lastLevel) {
        this.lastLevel = lastLevel;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
    
    public int[] getHighscore(){
        return highscore;
    }
    
    public int[] getBestCombo(){
        return bestCombo;
    }
    
    public int[] getSavedAnimals(){
        return savedAnimals;
    }
    
    public int getHighscore(int level){
        return highscore[level];
    }
    
    public int getBestCombo(int level){
        return bestCombo[level];
    }
    
    public int getSavedAnimals(int level){
        return savedAnimals[level];
    }
    
    public void setHighscore(int level, int newHighscore){
        highscore[level] = newHighscore;
    }
    
    public void setBestCombo(int level, int newBestCombo){
        bestCombo[level] = newBestCombo;
    }
    
    public void setSavedAnimals(int level, int animalsRecord){
        savedAnimals[level] = animalsRecord;
    }
}
