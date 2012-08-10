/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.logic;

/**
 *
 * @author AyamChiken
 */
public class FinalAttack {
    
    public static final int FIRST = 20;
    public static final int SECOND = 50;
    public static final int THIRD = 100;
    
    private int currentBar;
    
    public FinalAttack(){
        currentBar = 0;
        
    }
    
    public void incBar(int combo){
        currentBar += (combo/5)+1;
    }
    
    public int getBar(){
        return currentBar;
    }
    
    public void resetBar(){
        currentBar = 0;
    }
    
    public int getMultiplier(){
        if (currentBar >= THIRD){
            return 6;
        } else if (currentBar >= SECOND){
            return 3;
        } else if (currentBar >= FIRST){
            return 1;
        }
        
        return 0;
    }
    
    public boolean canDo(){
        if (currentBar >= FIRST){
            return true;
        }
        
        return false;
    }
    
    
}
