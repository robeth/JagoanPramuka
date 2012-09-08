package dart.game.sprite;

import com.chocoarts.component.Timer;
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
    public static int ATTACK_DELAY = 300;
    private AttackArea attackArea;
    private boolean attack;
    private boolean showEffect;
    private boolean canAttack;
    private boolean charged;
    private long effectTimeLast;
    private long lastAttack=0;
    private HitEffect hitEffect;
    private boolean hitEffectDone;
    private Timer chargeTimer;
    private boolean isCharge1;
    private Image chargeEffect1, chargeEffect2;
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
        canAttack = true;
        this.stop();
        this.hitEffectDone = false;
        this.charged = false;
        chargeTimer = new Timer(200);
        isCharge1= true;
        
        chargeEffect1= Image.createImage("/c_effect1.png");
        chargeEffect2= Image.createImage("/c_effect2.png");
    }

    public void update(long time, boolean isAttack){ 
        if (isCanAttack()){
            if(attackArea.isCharging()){
                if(isAttack){
                    Debug.println("update!!");
                    attackArea.updateCharge(time);
                } else {
                    //give attack flag
                    attack = true;
                    if (attackArea.getStack() >= 1){
                        charged = true;
                    }
                }
            } else {
                if(isAttack){
                    Debug.println("Start charging");
                    attackArea.startCharging(time);
                }
        }
        }
        
        if (showEffect && (time - effectTimeLast)>EFFECT_TIME){
            showEffect = false;
        }
        
        if (this.getFrame() == 1){
            this.hitEffectDone = true;
            showEffect = true;
            this.effectTimeLast = System.currentTimeMillis();
        }
        
        if (this.getFrame() == 0 && this.hitEffectDone == true){
            this.stop();
            this.hitEffectDone = false;
        }
        
        if (System.currentTimeMillis() - this.lastAttack > Hero.ATTACK_DELAY){
            this.canAttack = true;
        }
        
        if(attackArea.isChargingAttack() && chargeTimer.isTicked(time)){
            isCharge1= !isCharge1;
            chargeTimer.reset();
        }
        
    }
    
    public void setPosition(int x, int y){
        super.setPosition(x, y);
        attackArea.setPosition(x + this.getWidth(), this.getY());
        hitEffect.setPosition(this.getX()+this.getWidth(), this.getY() + 5);
    }
    
    public void pseudoPaint(Graphics g){
        paint(g);
        if(attackArea.isChargingAttack())
            g.drawImage((isCharge1) ? chargeEffect1: chargeEffect2, this.getX(), this.getY(), Graphics.TOP| Graphics.LEFT);
        
        
        
        
        
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
        //showEffect = true;
        int[] attackSequence = {0,1};
        this.setFrameSequence(attackSequence);
        this.setTimePerFrame(Hero.ATTACK_DELAY/this.getFrameSequenceLength());
        this.play();
        this.canAttack = false;
        this.lastAttack = System.currentTimeMillis();
        this.charged = false;
        //this.effectTimeLast = System.currentTimeMillis();
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
    
    public boolean isCanAttack(){
        return canAttack;
    }
    
    public boolean isCharged(){
        return charged;
    }
}