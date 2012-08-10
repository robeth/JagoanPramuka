/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.logic;

import java.util.Vector;

/**
 *
 * @author Maviosso
 */
public class Lanes {
    private int laneWidth;
    private int laneSeparatorWidth;
    private int y;

    public Lanes(int laneNumber, int laneWidth, int laneSeparatorWidth, int y) {
        this.laneWidth = laneWidth;
        this.laneSeparatorWidth = laneSeparatorWidth;
        this.y = y;
    }
    
    public int getY(int laneIndex){
        return laneIndex*(laneWidth+laneSeparatorWidth)+ y;
    }

    public int getLaneSeparatorWidth() {
        return laneSeparatorWidth;
    }

    public void setLaneSeparatorWidth(int laneSeparatorWidth) {
        this.laneSeparatorWidth = laneSeparatorWidth;
    }

    public int getLaneWidth() {
        return laneWidth;
    }

    public void setLaneWidth(int laneWidth) {
        this.laneWidth = laneWidth;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
}
