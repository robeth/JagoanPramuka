package dart.game.sprite;

import com.chocoarts.debug.Debug;
import com.chocoarts.drawing.AnimatedSprite;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Maviosso
 */
public class Hero extends AnimatedSprite {
    public static int ARYA = 111;
    public static int BIMA = 112;
    public static int CINTA = 113;
    public static int EFFECT_TIME = 120;
    private AttackArea attackArea;
    private boolean attack;
    private boolean showEffect;
    private long effectTimeLast;
    private HitEffect hitEffect;
    
    public Hero(Image image, int frameWidth, int frameHeight, int frameDuration,int type) throws IOException {
        super(image, frameWidth, frameHeight, frameDuration);
       
        attackArea = new AttackArea(image, frameWidth, frameHeight, 50, 50);
        attackArea.setAttackDamage(50);
        attackArea.setIncrementDuration(1000);
        attackArea.setIncrementLength(50);
        attackArea.setMaxIncrement(3);
        attackArea.setPosition(this.getX()+this.getWidth(), this.getY());
       
        hitEffect = getEffect(type);
        hitEffect.setPosition(this.getX()+this.getWidth(), this.getY() + 5);
        showEffect = false;
    }

    public void update(long time, boolean isAttack){        
        if(attackArea.isCharging()){
            if(isAttack){
                Debug.println("update!!");
                attackArea.updateCharge(time);
            } else {
                //give attack flag
                attack = true;
            }
        } else {
            if(isAttack){
                Debug.println("Start charging");
                attackArea.startCharging(time);
            }
        }
        
        if (showEffect && (time - effectTimeLast)>EFFECT_TIME){
            showEffect = false;
        }
        
    }
    
    public void setPosition(int x, int y){
        super.setPosition(x, y);
        attackArea.setPosition(x + this.getWidth(), this.getY());
        hitEffect.setPosition(this.getX()+this.getWidth(), this.getY() + 5);
    }
    
    public void pseudoPaint(Graphics g){
        paint(g);
        attackArea.pseudoPaint(g);
        if (showEffect){
            hitEffect.paint(g);
        }
    }
    
    public boolean isAttacking(){
        return attack;
    }
    
    public AttackArea getAttack(){
        return attackArea;
    }
    
    public void finishAttack () {
        attack = false;
        showEffect = true;
        this.effectTimeLast = System.currentTimeMillis();
        attackArea.reset();
    }
    
    public HitEffect getEffect(int type) throws IOException {
        if (type == Hero.ARYA){
            return new HitEffect(Image.createImage("/effect1.png"),48,26);
        }else if (type == Hero.BIMA){
            return new HitEffect(Image.createImage("/effect2.png"),43,26);
        } else {
            return new HitEffect(Image.createImage("/effect3.png"),49,27);
        }
    }
}