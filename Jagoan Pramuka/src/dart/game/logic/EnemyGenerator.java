/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.logic;

import dart.game.sprite.Enemy;
import java.io.IOException;
import java.util.Random;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Maviosso
 */
public class EnemyGenerator {
    private long duration;
    private int[] enemiesPool;
    private long lastUpdate;
    public static final int ALIEN = 10009;
    public static final int ALIEN2 = 10010;
    public static final int ALIEN3 = 10011;
    int indexEnemyArray;
    private static final String[] enemyArrayLv1 = {"1","","","","","","1","","","","",""
            ,"1","","","","1","1","","1","","","","","1","1","","","1"
            ,"1","1","","","","1","","1","","1","","","1","1","","1","1"
            ,"1","","1","","11","1","","","","","","1","1","11","","","11"
            ,"1","1","","1","1","1","","","1","","","1","11","","1","","11"
            /*,"1","1","1","1","","1","1","","11","1","","11","","11","","","11"
            ,"","11","11","","11","11","1","","11","","1","","11","1","","1","11"
            ,"11","1","11","","11","","1","","11","","11","11","","11","","1","1"
            ,"1","111","","","11","11","11","","11","1","1","11","111","","11","","111"*/};
    private static final String[] enemyArrayLv2 = {"1","","","","1","","","","","1","",""
            ,"2","","1","","","2","","1","","","","1","","1","","","2"
            };
    private static final String[] enemyArrayLv3 = {};
    private static final String[] enemyArrayLv4 = {};
    private static final String[] enemyArrayLv5 = {};
    private String[] enemyArrayUsed;
    private Random r;
    private World world;
    
    public EnemyGenerator(World world, long duration,int[] enemiesPool, long firstDelay,int lv){
        this.duration = duration;
        this.enemiesPool = enemiesPool;
        this.lastUpdate = System.currentTimeMillis() + firstDelay;
        this.r = new Random();
        this.world = world;
        this.indexEnemyArray = 0;
        
        switch (lv) {
                case 1 : enemyArrayUsed = enemyArrayLv1;
                case 2 : enemyArrayUsed = enemyArrayLv2;
                case 3 : enemyArrayUsed = enemyArrayLv3;
                case 4 : enemyArrayUsed = enemyArrayLv4;
                case 5 : enemyArrayUsed = enemyArrayLv5;
        }
                
        
    }
    
    public void update(long currentTime){
        if(currentTime - lastUpdate > duration){        
            lastUpdate = currentTime;
            if (this.indexEnemyArray < this.enemyArrayUsed.length){
                for (int i=0;i<enemyArrayUsed[indexEnemyArray].length();i++){
                    world.addEnemy(getSpecEnemy(enemyArrayUsed[indexEnemyArray].charAt(i)));  
                }
            } else {
                world.notifyWaveEnd();
            }
            indexEnemyArray+=1;
        }
    }
    
    
    public Enemy getRandomEnemy(){
        return getEnemy(Math.abs(r.nextInt()%enemiesPool.length));
    }
    
    public Enemy getSpecEnemy(char enemyCode){
        if (enemyCode == '1'){
            return getEnemy(0);
        } else if (enemyCode == '2'){
            return getEnemy(1);
        } else if (enemyCode == '3'){
            return getEnemy(2);
        } else {
            return getEnemy(0);
        }
    }
    
    public Enemy getEnemy(int enemyPoolIndex){
        Image enemyImage = null;
        // tambah untuk gambar yang berbeda
        try {
            enemyImage = Image.createImage("/Mafia_mini.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Enemy e = new Enemy(enemyImage, 42, 42, 400, enemiesPool[enemyPoolIndex]); 
        int[] sequence = {0,1,2,3};
        e.setFrameSequence(sequence);
        return e;
     
    }
    
}
