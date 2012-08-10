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
    private String[] enemyArrayLv1 = {"1","","","1","","","1","","","1","",""
            ,"1","1","","","1","11","","1","1","","","11","1","1","","1","1"
            ,"1","1","1","","11","11","","1","","","","11","1","1","","1","1"
            ,"1","","1","","11","1","","1","1","","","1","1","11","","","11"
            ,"1","11","","1","11","1","","1","11","","","1","1","","1","","11"
            ,"1","1","1","1","","1","1","","11","1","","11","","11","","","11"
            ,"","11","11","","11","11","1","","11","","1","","11","1","","1","11"
            ,"11","1","11","","11","","1","","11","","11","11","","11","","1","1"
            ,"1","111","","","11","11","11","","11","1","1","11","111","","11","","111"};
    private Random r;
    private World world;
    
    public EnemyGenerator(World world, long duration,int[] enemiesPool, long firstDelay){
        this.duration = duration;
        this.enemiesPool = enemiesPool;
        this.lastUpdate = System.currentTimeMillis() + firstDelay;
        this.r = new Random();
        this.world = world;
        this.indexEnemyArray = 0;
        
    }
    
    public void update(long currentTime){
        if(currentTime - lastUpdate > duration){        
            lastUpdate = currentTime;
            if (this.indexEnemyArray < this.enemyArrayLv1.length){
                for (int i=0;i<enemyArrayLv1[indexEnemyArray].length();i++){
                    world.addEnemy(getSpecEnemy(enemyArrayLv1[indexEnemyArray].charAt(i)));  
                }
            } else {
                // udah abis musuhnya selesai.
                // masuk scene selamat anda berhasil,
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
        try {
            enemyImage = Image.createImage("/penjahat_sprite.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new Enemy(enemyImage, 34, 42, 400, enemiesPool[enemyPoolIndex]);
     
    }
    
}
