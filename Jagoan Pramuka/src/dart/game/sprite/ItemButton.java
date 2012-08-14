/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.sprite;

import com.chocoarts.text.CustomFont;
import dart.game.data.Item;

/**
 *
 * @author Maviosso
 */
public class ItemButton extends DummyButton2 {
    private Item item;

    public ItemButton(Item item, CustomFont font, int x, int y, int width, int height, boolean isStroked, boolean isFilled, Color normalStrokeColor, Color chosenStrokeColor, Color normalFillColor, Color chosenFillColor) {
        super(font, x, y, width, height, isStroked, isFilled, item.getName()+":"+item.getPrice(), normalStrokeColor, chosenStrokeColor, normalFillColor, chosenFillColor);
        this.item = item;
    }
    
    public ItemButton(CustomFont font, int x, int y, int width, int height, boolean isStroked, boolean isFilled, String label,Color normalStrokeColor, Color chosenStrokeColor, Color normalFillColor, Color chosenFillColor) {
        super(font, x, y, width, height, isStroked, isFilled, label, normalStrokeColor, chosenStrokeColor, normalFillColor, chosenFillColor);
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    
}
