package COSC3P40.graphics;

import java.awt.Image;
import java.util.List;
import java.util.ArrayList;
import COSC3P40.util.Pair;

/**
    The Animation class manages a series of images (frames) and
    the amount of time to display each frame.
*/
public class Animation {

    private List<Pair<Image,Long>> frames;
    private int currFrameIndex;
    private long animTime;
    private long totalDuration;
    private boolean repeating;
    private boolean running;

    /**
        Creates a new, empty Animation.
    */
    public Animation(boolean repeating) {
    	super();
        frames = new ArrayList<Pair<Image,Long>>();
        totalDuration = 0;
        this.repeating = repeating;
        running = false;
    }


    /**
        Adds an image to the animation with the specified
        duration (time to display the image).
    */
    public synchronized void addFrame(Image image, long duration) {
        totalDuration += duration;
        frames.add(new Pair<Image,Long>(image, totalDuration));
    }


    /**
        Starts this animation over from the beginning.
    */
    public synchronized void start() {
        reset();
        running = true;
    }
    
    public synchronized void reset() {
    	animTime = 0;
        currFrameIndex = 0;
    }
    
    public synchronized boolean isRunning() {
    	return running;
    }

    /**
        Updates this animation's current image (frame), if
        necessary.
    */
    public synchronized void update(long elapsedTime) {
        if (running && frames.size() > 1) {
            animTime += elapsedTime;

            if (animTime >= totalDuration) {
            	if (repeating) {
            		currFrameIndex = 0;
                	animTime = animTime % totalDuration;
                } else {
                	animTime = totalDuration;
                	running = false;
                };
            }
            while (animTime > frames.get(currFrameIndex).getSecond()) {
                currFrameIndex++;
            }
        }
    }


    /**
        Gets this Animation's current image. Returns null if this
        animation has no images.
    */
    public synchronized Image getImage() {
        if (frames.size() == 0) {
            return null;
        }
        else {
            return frames.get(currFrameIndex).getFirst();
        }
    }
    
}
