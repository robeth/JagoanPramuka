/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dart.game.main;

import com.chocoarts.network.Profile;
import dart.game.data.ItemDatabase;
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
    

    public MainProfile(){
        reset();
    }

    public void reset() {
        sound = true;
        lastLevel = 0;
        
        bestCombo = new int[5];
        savedAnimals = new int[5];
        highscore = new int[5];
        
        money = 0;
        ItemDatabase.reset();
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
        
        stream.writeBoolean(ItemDatabase.ALAT_MASAK.isBought());
        stream.writeBoolean(ItemDatabase.TONGKAT.isBought());
        stream.writeBoolean(ItemDatabase.TALI.isBought());
        stream.writeBoolean(ItemDatabase.HASDUK.isBought());
        stream.writeBoolean(ItemDatabase.RING.isBought());
        stream.writeBoolean(ItemDatabase.BADGE.isBought());
        
        stream.writeBoolean(ItemDatabase.ALAT_MASAK.isEquipped());
        stream.writeBoolean(ItemDatabase.TONGKAT.isEquipped());
        stream.writeBoolean(ItemDatabase.TALI.isEquipped());
        stream.writeBoolean(ItemDatabase.HASDUK.isEquipped());
        stream.writeBoolean(ItemDatabase.RING.isEquipped());
        stream.writeBoolean(ItemDatabase.BADGE.isEquipped());
        
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
        ItemDatabase.ALAT_MASAK.setBought(stream.readBoolean());
        ItemDatabase.TONGKAT.setBought(stream.readBoolean());
        ItemDatabase.TALI.setBought(stream.readBoolean());
        ItemDatabase.HASDUK.setBought(stream.readBoolean());
        ItemDatabase.RING.setBought(stream.readBoolean());
        ItemDatabase.BADGE.setBought(stream.readBoolean());
        
        ItemDatabase.ALAT_MASAK.setEquipped(stream.readBoolean());
        ItemDatabase.TONGKAT.setEquipped(stream.readBoolean());
        ItemDatabase.TALI.setEquipped(stream.readBoolean());
        ItemDatabase.HASDUK.setEquipped(stream.readBoolean());
        ItemDatabase.RING.setEquipped(stream.readBoolean());
        ItemDatabase.BADGE.setEquipped(stream.readBoolean());
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

    void printStatus() {
        System.out.println("Money: "+money);
        System.out.println("Last Level:"+lastLevel);
        for(int i = 1; i < 6; i++){
            System.out.println("--Level "+i+"--");
            System.out.println("Highscore:"+highscore[i-1]);
            System.out.println("Best Combo:"+bestCombo[i-1]);
            System.out.println("Saved Animals:"+savedAnimals[i-1]);
        }
    }
}
