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
    public static final int MALING = 10009;
    public static final int MAFIA = 10010;
    public static final int KUNTILANAK = 10011;
    public static final int BOS_CICAK = 10012;
    public static final int[] SEQUENCE_KUNTI = {0,1,2};
    public static final int[] SEQUENCE_MALING = {0,1,2,3};
    int indexEnemyArray;
    private static final String[] enemyArrayLv1 = {"1","","","","","","1","","","","",""
            ,"1","","","","1","1","","1","","","","","1","1","","","1"
            ,"1","1","","","","1","","1","","1","","","1","1","","1","1"
            ,"1","","1","","11","1","","","","","","1","1","11","","","11"
            ,"1","1","","1","1","1","","","1","","","1","11","","1","","11"
            };
    private static final String[] enemyArrayLv2 = {"1","","","","1","","","","","1","",""
            ,"2","","1","","","2","","1","","","","1","","1","","","2"
            ,"1","","1","","","2","","2","","","2","1","","1","","","2"
            ,"2","","2","","","2","1","2","","","2","1","","1","1","","2"
            ,"2","2","","2","","","2","2","","2","21","11","","12","","1","2"
            };
    private static final String[] enemyArrayLv3 = {"1","","","1","","","","","1","1","",""
            ,"2","","1","","","2","","1","","","","1","2","1","","","2"
            ,"2","","2","","2","2","","2","","1","2","1","","1","","1","2"
            ,"2","","4","","1","4","1","","2","","2","2","","4","2","","2"
            ,"1","2","","2","","","4","2","","2","24","12","","12","","1","4"
            };
    private static final String[] enemyArrayLv4 = {"1","","","1","","","","2","","","","2"
            ,"1","","3","","","2","","","3","","","2","2","","","","2"
            ,"2","","2","","2","2","","2","","","2","1","","4","","2","2"
            ,"2","","3","","2","4","","","3","","2","4","","","2","","3"
            ,"3","2","","2","","","2","","","23","2","42","","32","","43","3"
            };
    private static final String[] enemyArrayLv5 = {"1","","","2","","","","3","","","","2"
            ,"2","","3","","","2","1","","3","","","2","3","","","","3"
            ,"2","","2","","3","3","","2","","","3","4","","4","","3","2"
            ,"3","","3","","2","3","","","3","","2","3","","","2","","3"
            ,"3","3","","3","","","2","","","23","3","43","","32","","23","3"
            };
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
                    break;
                case 2 : enemyArrayUsed = enemyArrayLv2;
                    break;
                case 3 : enemyArrayUsed = enemyArrayLv3;
                    break;
                case 4 : enemyArrayUsed = enemyArrayLv4;
                    break;
                case 5 : enemyArrayUsed = enemyArrayLv5;
                    break;
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
        } else if (enemyCode == '4'){
            return getEnemy(3);
        } else {
            return getEnemy(0);
        }
    }
    
    public Enemy getEnemy(int enemyPoolIndex){
        Image enemyImage = null;
        int[] sequence = null;
        // tambah untuk gambar yang berbeda
        try {
            if (enemyPoolIndex == 0){
                enemyImage = Image.createImage("/Penjahat_mini.png");
                sequence = EnemyGenerator.SEQUENCE_MALING;
            } else if (enemyPoolIndex == 1){
                enemyImage = Image.createImage("/Mafia_mini.png");
                sequence = EnemyGenerator.SEQUENCE_MALING;
            } else if (enemyPoolIndex == 2){
                enemyImage = Image.createImage("/Kuntilanak_mini.png");
                sequence = EnemyGenerator.SEQUENCE_KUNTI;
            } else if (enemyPoolIndex == 3){
                enemyImage = Image.createImage("/Boss_mini.png");
                sequence = EnemyGenerator.SEQUENCE_KUNTI;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Enemy e = new Enemy(enemyImage, 42, 42, 400, enemiesPool[enemyPoolIndex]); 
        e.setFrameSequence(sequence);
        return e;
     
    }
    
}
