/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.logic;

/**
 *
 * @author AyamChiken
 */
public class Combo {
    
    private int comboStack;
    private int highestCombo;
    
    
    public Combo (){
        comboStack = 0;
        highestCombo = 0;
    }
    
    public void hit (int amount){
        comboStack += amount;
        if (highestCombo<comboStack){
            highestCombo = comboStack;
        }
    }
    
    public void miss (){
        
        comboStack = 0;
    }
    
    public int getComboStack(){
        return comboStack;
    }
    
    public int getHighestCombo(){
        return highestCombo;
    }
    
}
