/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.data;

/**
 *
 * @author Maviosso
 */
public class Accesory extends Item{
    public int effect;

    public Accesory(int effect, String imagePath, String name, String description, int price, boolean bought, boolean equipped) {
        super(imagePath, name, description, price, bought, equipped);
        this.effect = effect;
    }
    
    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    
}
