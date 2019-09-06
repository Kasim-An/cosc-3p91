package COSC3P40.sound;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
import COSC3P40.util.ThreadPool;
import COSC3P40.io.LoopingByteInputStream;


/**
    The SoundManager class manages sound playback. The
    SoundManager is a ThreadPool, with each thread playing back
    one sound at a time. This allows the SoundManager to
    easily limit the number of simultaneous sounds being played.
    <p>Possible ideas to extend this class:<ul>
    <li>add a setMasterVolume() method, which uses Controls to
        set the volume for each line.
    <li>don't play a sound if more than, say, 500ms has passed
        since the request to play
    </ul>
*/
public class SoundManager extends ThreadPool {

    private AudioFormat playbackFormat;
    private ThreadLocal<SourceDataLine> localLine;		// Java 1.5
    private ThreadLocal<byte[]> localBuffer;			// Java 1.5
    private Object pausedLock;
    private boolean paused;
    private String soundPath;							// the sound directory 
    													// used

    
    /**
        Gets the maximum number of simultaneous sounds with the
        specified AudioFormat that the default mixer can play.
    */
    public static int getMaxSimultaneousSounds(AudioFormat playbackFormat) {
        DataLine.Info lineInfo = new DataLine.Info(
            SourceDataLine.class, playbackFormat);
        Mixer mixer = AudioSystem.getMixer(null);
        int maxLines = mixer.getMaxLines(lineInfo);   // Java 1.5
	    if (maxLines == AudioSystem.NOT_SPECIFIED) {
	        maxLines = 32;
	    }
	    return maxLines;
    }
    
    
    /**
        Creates a new SoundManager using the maximum number of
        simultaneous sounds.
    */
    public SoundManager(AudioFormat playbackFormat) {
        this(playbackFormat,getMaxSimultaneousSounds(playbackFormat),"");
    }


    /**
        Creates a new SoundManager with the specified maximum
        number of simultaneous sounds.
    */
    public SoundManager(AudioFormat playbackFormat, int maxSimultaneousSounds) {
        this(playbackFormat,maxSimultaneousSounds,"");
    }
    
    public SoundManager(AudioFormat playbackFormat, String path) {
        this(playbackFormat,getMaxSimultaneousSounds(playbackFormat),path);
    }
    
    public SoundManager(AudioFormat playbackFormat, int maxSimultaneousSounds, String path) {
        super(Math.min(maxSimultaneousSounds,getMaxSimultaneousSounds(playbackFormat)));
        setSoundPath(path);
        this.playbackFormat = playbackFormat;
        localLine = new ThreadLocal<SourceDataLine>();	// Java 1.5
        localBuffer = new ThreadLocal<byte[]>();		// Java 1.5
        pausedLock = new Object();
        // notify threads in pool it's ok to start
        synchronized (this) {
            notifyAll();
        }
    }
    
    /** This method sets the SoundPath to the given string. It automatically
	 ** adds "/" if necessary.
	 **
	 ** @param path		the new SoundPath
	*/
	public void setSoundPath(String path) {
		soundPath = path;
		if (!soundPath.endsWith("/")) soundPath += "/";
	} //setSoundPath
	
	/** This method returns the Sound-Path used by this manager.
	 **	
	*/
	public String getSoundPath() {
		return soundPath;
	} //getSoundPath


    public void close() {
        cleanUp();
        super.close();
    }


    public void join() {
        cleanUp();
        super.join();
    }


    /**
        Sets the paused state. Sounds may not pause immediately.
    */
    public void setPaused(boolean paused) {
        if (this.paused != paused) {
            synchronized (pausedLock) {
                this.paused = paused;
                if (!paused) {
                    // restart sounds
                    pausedLock.notifyAll();
                }
            }
        }
    }


    /**
        Returns the paused state.
    */
    public boolean isPaused() {
        return paused;
    }


    /**
        Loads a Sound from the file system. Returns null if an
        error occurs.
    */
    public Sound getSound(String filename) {
        return getSound(getAudioInputStream(filename));
    }


    /**
        Loads a Sound from an input stream. Returns null if an
        error occurs.
    */
    public Sound getSound(InputStream is) {
        return getSound(getAudioInputStream(is));
    }


    /**
        Loads a Sound from an AudioInputStream.
    */
    public Sound getSound(AudioInputStream audioStream) {
        if (audioStream == null) {
            return null;
        }

        // get the number of bytes to read
        int length = (int)(audioStream.getFrameLength() * audioStream.getFormat().getFrameSize());

        // read the entire stream
        byte[] samples = new byte[length];
        DataInputStream is = new DataInputStream(audioStream);
        try {
            is.readFully(samples);
            is.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        // return the samples
        return new Sound(samples);
    }

    /**
        Plays a sound. This method returns immediately.
    */
    public InputStream play(Sound sound) {
        return play(sound, null, false);
    }


    /**
        Plays a sound with an optional SoundFilter, and optionally
        looping. This method returns immediately.
    */
    public InputStream play(Sound sound, SoundFilter filter, boolean loop) {
        InputStream is;
        if (sound != null) {
            if (loop) {
                is = new LoopingByteInputStream(
                    sound.getSamples());
            }
            else {
                is = new ByteArrayInputStream(sound.getSamples());
            }

            return play(is, filter);
        }
        return null;
    }


    /**
        Plays a sound from an InputStream. This method
        returns immediately.
    */
    private InputStream play(InputStream is) {
        return play(is, null);
    }


    /**
        Does any clean up before closing.
    */
    private void cleanUp() {
        // signal to unpause
        setPaused(false);

        // close the mixer (stops any running sounds)
        Mixer mixer = AudioSystem.getMixer(null);
        if (mixer.isOpen()) {
            mixer.close();
        }
    }
    
    /**
        Creates an AudioInputStream from a sound from the file
        system.
    */
    private AudioInputStream getAudioInputStream(String filename) {
        try {
            return getAudioInputStream(
                new FileInputStream(soundPath + filename));
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    /**
        Creates an AudioInputStream from a sound from an input
        stream
    */
    private AudioInputStream getAudioInputStream(InputStream is) {

        try {
            if (!is.markSupported()) {
                is = new BufferedInputStream(is);
            }
            // open the source stream
            AudioInputStream source =
                AudioSystem.getAudioInputStream(is);

            // convert to playback format
            return AudioSystem.getAudioInputStream(
                playbackFormat, source);
        }
        catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }

        return null;
    }

	/**
        Plays a sound from an InputStream with an optional
        sound filter. This method returns immediately.
    */
    private InputStream play(InputStream is, SoundFilter filter) {
        if (is != null) {
            if (filter != null) {
                is = new FilteredSoundStream(is, filter);
            }
            runTask(new SoundPlayer(is));
        }
        return is;
    }


    /**
        Signals that a PooledThread has started. Creates the
        Thread's line and buffer.
    */
    protected void threadStarted() {
        // wait for the SoundManager constructor to finish
        synchronized (this) {
            try {
                wait();
            }
            catch (InterruptedException ex) { }
        }

        // use a short, 100ms (1/10th sec) buffer for filters that
        // change in real-time
        int bufferSize = playbackFormat.getFrameSize() *
            Math.round(playbackFormat.getSampleRate() / 10);

        // create, open, and start the line
        SourceDataLine line;
        DataLine.Info lineInfo = new DataLine.Info(
            SourceDataLine.class, playbackFormat);
        try {
            line = (SourceDataLine)AudioSystem.getLine(lineInfo);
            line.open(playbackFormat, bufferSize);
        }
        catch (LineUnavailableException ex) {
            // the line is unavailable - signal to end this thread
            Thread.currentThread().interrupt();
            return;
        }

        line.start();

        // create the buffer
        byte[] buffer = new byte[bufferSize];

        // set this thread's locals
        localLine.set(line);
        localBuffer.set(buffer);
    }


    /**
        Signals that a PooledThread has stopped. Drains and
        closes the Thread's Line.
    */
    protected void threadStopped() {
        SourceDataLine line = localLine.get();			// Java 1.5
        if (line != null) {
            line.drain();
            line.close();
        }
    }


    /**
        The SoundPlayer class is a task for the PooledThreads to
        run. It receives the threads's Line and byte buffer from
        the ThreadLocal variables and plays a sound from an
        InputStream.
        <p>This class only works when called from a PooledThread.
    */
    private class SoundPlayer implements Runnable {

        private InputStream source;

        public SoundPlayer(InputStream source) {
            this.source = source;
        }

        public void run() {
            // get line and buffer from ThreadLocals
            SourceDataLine line = localLine.get();		// Java 1.5
            byte[] buffer = localBuffer.get();			// Java 1.5
            if (line == null || buffer == null) {
                // the line is unavailable
                return;
            }

            // copy data to the line
            try {
                int numBytesRead = 0;
                while (numBytesRead != -1) {
                    // if paused, wait until unpaused
                    synchronized (pausedLock) {
                        if (paused) {
                            try {
                                pausedLock.wait();
                            }
                            catch (InterruptedException ex) {
                                return;
                            }
                        }
                    }
                    // copy data
                    numBytesRead =
                        source.read(buffer, 0, buffer.length);
                    if (numBytesRead != -1) {
                        line.write(buffer, 0, numBytesRead);
                    }
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

}
