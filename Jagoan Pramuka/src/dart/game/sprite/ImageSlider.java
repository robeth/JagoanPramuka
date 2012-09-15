/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dart.game.sprite;

import com.chocoarts.component.Timer;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author Maviosso
 */
public class ImageSlider {

    private Image[] images;
    private int x, y, width, height, imageWidth, imageHeight, xBaseline, yBaseline;
    private long transitionTime;
    private long transitionStartTime;
    private int currentIndex;
    private Image image1, image2;
    private int x1Source, x2Source, w1Source, w2Source, x1Dest, x2Dest, x1Pos, x2Pos;
    private int state;
    private static final int STANDBY = 0,
            SLIDING_RIGHT = 1,
            SLIDING_LEFT = 2;
    private Timer slidingTimer;

    public ImageSlider(Image[] images, int x, int y, int width, int height, int imageWidth, int imageHeight, long transitionTime) {
        this.images = images;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.transitionTime = transitionTime;
        this.xBaseline = this.x + (int) ((width - imageWidth) / 2);
        this.yBaseline = this.y + (int) ((height - imageHeight / 2));
        this.currentIndex = 0;
        this.image1 = images[currentIndex];
        this.slidingTimer = new Timer(transitionTime);
        this.state = STANDBY;

        x1Pos = xBaseline;
        x1Source = 0;
        w1Source = imageWidth;
        x1Dest = xBaseline;
    }

    public void update(long currentTime) {
        if (state == SLIDING_LEFT) {
            if (slidingTimer.isTicked(currentTime)) {
                image1 = image2;
                image2 = null;
                x1Pos = xBaseline;
                x1Source = 0;
                w1Source = imageWidth;
                x1Dest = xBaseline;
                state = STANDBY;
                currentIndex++;
                if(currentIndex >= images.length) currentIndex = 0;
            } else {
                float progress = ((float)(currentTime - transitionStartTime)) / ((float)transitionTime);
                progress = Math.min(1.0f, progress);
                x1Pos = interpolate(xBaseline, x - imageWidth, progress);
                x2Pos = interpolate(x + width, xBaseline, progress);
                x1Source = Math.max(0, x - x1Pos);
                w1Source = Math.max(0, x1Pos + imageWidth - x);
                w1Source = Math.min(w1Source, imageWidth);
                x1Dest = Math.max(x, x1Pos);

                x2Source = 0;
                w2Source = Math.max(0, x + width - x2Pos);
                w2Source = Math.min(imageWidth, w2Source);
                x2Dest = Math.min(x + width, x2Pos);

//                System.out.println("progress: x1source-w1source-x1dest: " +progress+"--" + x1Source + "--" + w1Source + "--" + x1Dest);
//                System.out.println("progress: x2source-w2source-x2dest: " +progress+"--" + x2Source + "--" + w2Source + "--" + x2Dest);
            }
        } else if (state == SLIDING_RIGHT) {
            if (slidingTimer.isTicked(currentTime)) {
                image1 = image2;
                image2 = null;
                x1Pos = xBaseline;
                x1Source = 0;
                w1Source = imageWidth;
                x1Dest = xBaseline;
                state = STANDBY;
                currentIndex--;
                if(currentIndex < 0) currentIndex = images.length-1;
            } else {
                float progress = ((float)(currentTime - transitionStartTime)) / ((float)transitionTime);
                progress = Math.min(1.0f, progress);
                x1Pos = interpolate(xBaseline, x + width, progress);
                x2Pos = interpolate(x - imageWidth, xBaseline, progress);
                x1Source = 0;
                w1Source = Math.max(0, x + width - x1Pos);
                w1Source = Math.min(imageWidth, w1Source);
                x1Dest = Math.min(x + width, x1Pos);

                x2Source = Math.max(0, x - x2Pos);
                w2Source = Math.max(0, x2Pos + imageWidth - x);
                w2Source = Math.min(w2Source, imageWidth);
                x2Dest = Math.max(x, x2Pos);
            }
        }
    }

    public boolean slideLeft(long currentTime) {
        if (state == STANDBY) {
            state = SLIDING_LEFT;
            

            int nextIndex = currentIndex + 1;
            nextIndex = (nextIndex >= images.length) ? 0 : nextIndex;
            
            image2 = images[nextIndex];
            x2Pos = x+width;
            x2Source = 0;
            w2Source = 0;
            x2Dest = x+width;
            
            slidingTimer.reset();
            slidingTimer.start();
            transitionStartTime = currentTime;
            return true;
        }
        return false;
    }

    public boolean slideRight(long currentTime) {
        if (state == STANDBY) {
            state = SLIDING_RIGHT;
            

            int nextIndex = currentIndex - 1;
            nextIndex = (nextIndex < 0) ? images.length - 1 : nextIndex;

            image2 = images[nextIndex];

            x2Pos = x-imageWidth;
            x2Source = 0;
            w2Source = 0;
            x2Dest = x;
            slidingTimer.reset();
            slidingTimer.start();
            transitionStartTime = currentTime;
            return true;
        }
        return false;
    }

    public void paint(Graphics g) {
        try{
            g.drawRegion(image1, x1Source, 0, w1Source, imageHeight, Sprite.TRANS_NONE, x1Dest, yBaseline, Graphics.LEFT | Graphics.TOP);
            if (image2 != null) {
                g.drawRegion(image2, x2Source, 0, w2Source, imageHeight, Sprite.TRANS_NONE, x2Dest, yBaseline, Graphics.LEFT | Graphics.TOP);
            }
        } catch(IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    private int interpolate(int source, int destination, float progress) {
        return source + ((int) ((destination - source) * progress));
    }
}
