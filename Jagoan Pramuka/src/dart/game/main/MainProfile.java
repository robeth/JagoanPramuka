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

    
    
    private int score = 0;
    

    public MainProfile(){
    }

    public void reset() {
        score = 0;
    }

    public void writeVariables(DataOutputStream stream) throws Exception {
        stream.writeBoolean(sound);
        stream.writeInt(score);
    }

    public void readVariables(DataInputStream stream) throws Exception {
        sound = stream.readBoolean();
        score = stream.readInt();
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
