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
public class DummyButton2 extends Rectangle{
    private boolean isStroked;
    private boolean isFilled;
    private String label;
    private Color normalStrokeColor;
    private Color chosenStrokeColor;
    private Color normalFillColor;
    private Color chosenFillColor;
    private CustomFont font;
    private boolean isChosen;
    
    public DummyButton2(CustomFont font, int x, int y, int width, int height, boolean isStroked,boolean isFilled, String label, Color normalStrokeColor, Color chosenStrokeColor, Color normalFillColor, Color chosenFillColor){
        super(x, y, width, height);    
        this.font = font;
        this.isStroked = isStroked;
        this.isFilled = isFilled;
        this.label = label;
        this.normalFillColor = normalFillColor;
        this.normalStrokeColor = normalStrokeColor;
        this.chosenFillColor = chosenFillColor;
        this.chosenStrokeColor = chosenStrokeColor;
    }

    public Color getChosenFillColor() {
        return chosenFillColor;
    }

    public void setChosenFillColor(Color chosenFillColor) {
        this.chosenFillColor = chosenFillColor;
    }

    public Color getChosenStrokeColor() {
        return chosenStrokeColor;
    }

    public void setChosenStrokeColor(Color chosenStrokeColor) {
        this.chosenStrokeColor = chosenStrokeColor;
    }

    public boolean isIsFilled() {
        return isFilled;
    }

    public void setIsFilled(boolean isFilled) {
        this.isFilled = isFilled;
    }

    public boolean isIsStroked() {
        return isStroked;
    }

    public void setIsStroked(boolean isStroked) {
        this.isStroked = isStroked;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Color getNormalFillColor() {
        return normalFillColor;
    }

    public void setNormalFillColor(Color normalFillColor) {
        this.normalFillColor = normalFillColor;
    }

    public Color getNormalStrokeColor() {
        return normalStrokeColor;
    }

    public void setNormalStrokeColor(Color normalStrokeColor) {
        this.normalStrokeColor = normalStrokeColor;
    }

    public boolean isIsChosen() {
        return isChosen;
    }

    public void setIsChosen(boolean isChosen) {
        this.isChosen = isChosen;
    }
    
    
    
    public void paint(Graphics g){
        if(isFilled){
            if(isChosen)
                g.setColor(chosenFillColor.getRGB());
            else
                g.setColor(normalFillColor.getRGB());
            g.fillRect(x, y, width, height);
        }
        
        if(isStroked){
            if(isChosen)
                g.setColor(chosenStrokeColor.getRGB());
            else
                g.setColor(normalStrokeColor.getRGB());
            g.drawRect(x, y, width, height);
        }
        
        
        //Tulis label
        if(label != null && label.length() > 0){
            int centerX = x + width/2;
            int centerY = y + height/2;
            font.paintString(g, label,centerX, centerY, Graphics.HCENTER | Graphics.VCENTER);
        }
    }
}
