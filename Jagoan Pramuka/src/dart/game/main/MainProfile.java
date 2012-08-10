/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dart.game.main;

import com.chocoarts.network.Profile;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author Agung
 */
public class MainProfile extends Profile{

    private boolean sound = false;
    private int score = 0;

    public MainProfile(){
    }

    public void reset() {
        score = 0;
    }

    public void writeVariables(DataOutputStream stream) throws Exception {
        stream.writeBoolean(sound);
        stream.writeInt(score);
    }

    public void readVariables(DataInputStream stream) throws Exception {
        sound = stream.readBoolean();
        score = stream.readInt();
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }
    public boolean getSound(){
        return sound;
    }

}
