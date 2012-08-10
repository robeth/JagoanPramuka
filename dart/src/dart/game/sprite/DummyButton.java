/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.sprite;

import com.chocoarts.shape.Rectangle;
import com.chocoarts.text.CustomFont;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Maviosso
 */
public class DummyButton extends Rectangle{
    
    public DummyButton(int x, int y, int width, int height){
        super(x, y, width, height);
    }
    public void drawStroke(Graphics g){
        g.drawRect(x, y, width, height);
    }
    
    public void fill(Graphics g){
        g.fillRect(x, y, width, height);
    }
    
    public void drawString(Graphics g, CustomFont cf, String data){
        int centerX = x + width/2;
        int centerY = y + height/2;
        cf.paintString(g, data,centerX, centerY, Graphics.HCENTER | Graphics.VCENTER);
    }
    
}
