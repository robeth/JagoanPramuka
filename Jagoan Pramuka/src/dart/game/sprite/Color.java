/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.sprite;

/**
 *
 * @author Maviosso
 */
public class Color {
    private int r,g,b;
    public static final Color RED = new Color(255,0,0),
            GREEN = new Color(0,255,0),
            BLUE = new Color(0,0,255),
            WHITE = new Color(255,255,255),
            BLACK = new Color(0,0,0);
        
    
    public Color(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    public int getRGB(){
        return (r * 256*256) + (g * 256) + (b);
    }
}
