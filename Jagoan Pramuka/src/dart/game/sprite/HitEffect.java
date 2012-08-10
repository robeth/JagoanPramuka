/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.sprite;

import com.chocoarts.drawing.ChocoSprite;
import javax.microedition.lcdui.Image;

/**
 *
 * @author AyamChiken
 */
public class HitEffect extends ChocoSprite{
    public static int POW = 1001;
    public static int BAM = 1002;
    public static int WHAM = 1003;
     
    
    public HitEffect(Image image, int width, int height) {
        super(image, width, height);
    }
    
}
