/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.data;

/**
 *
 * @author Maviosso
 */
public class Weapon extends Item{
    
    private int attack;
    private int range;
    private int attackSpeed;

    public Weapon(int attack, int range, int attackSpeed, String imagePath, String name, String description, int price, boolean bought, boolean equipped) {
        super(imagePath, name, description, price, bought, equipped);
        this.attack = attack;
        this.range = range;
        this.attackSpeed = attackSpeed;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    
}
