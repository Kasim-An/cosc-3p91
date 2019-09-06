package COSC3P40.midi;

import java.io.*;
import javax.sound.midi.*;

public class MidiManager implements MetaEventListener {
	
    // Midi meta event
    public static final int END_OF_TRACK_MESSAGE = 47;
    
    private static MidiManager instance = null;
    
    public static MidiManager getInstance() {
    	if (instance == null) instance = new MidiManager();
    	return instance;
    }

    private Sequencer sequencer;
    private boolean loop;
    private boolean paused;
    private String midiPath;							// the midi directory 
    													// used

    
    public MidiManager() {
    	try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addMetaEventListener(this);
        }
        catch ( MidiUnavailableException ex) {
            sequencer = null;
        }
    }
    
    public void setMidiPath(String path) {
    	midiPath = path;
    	if (!path.endsWith("/")) path += "/";
    }
    
    public String getMidiPath() {
    	return midiPath;
    }


    /**
        Loads a sequence from the file system. Returns null if
        an error occurs.
    */
    public Sequence getSequence(String filename) {
        try {
            return getSequence(new FileInputStream(midiPath + filename));
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    /**
        Loads a sequence from an input stream. Returns null if
        an error occurs.
    */
    public Sequence getSequence(InputStream is) {
        try {
            if (!is.markSupported()) {
                is = new BufferedInputStream(is);
            }
            Sequence s = MidiSystem.getSequence(is);
            is.close();
            return s;
        }
        catch (InvalidMidiDataException ex) {
            ex.printStackTrace();
            return null;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

	
	public void play(Sequence sequence) {
		play(sequence,false);
	}
	
    /**
        Plays a sequence, optionally looping. This method returns
        immediately. The sequence is not played if it is invalid.
    */
    public void play(Sequence sequence, boolean loop) {
        if (sequencer != null && sequence != null && sequencer.isOpen()) {
            try {
                sequencer.setSequence(sequence);
                sequencer.start();
                this.loop = loop;
            }
            catch (InvalidMidiDataException ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
        This method is called by the sound system when a meta
        event occurs. In this case, when the end-of-track meta
        event is received, the sequence is restarted if
        looping is on.
    */
    public void meta(MetaMessage event) {
        if (event.getType() == END_OF_TRACK_MESSAGE) {
            if (sequencer != null && sequencer.isOpen() && loop) {
            	sequencer.setTickPosition(0);			// Java 1.5
                sequencer.start();
            }
        }
    }


    /**
        Stops the sequencer and resets its position to 0.
    */
    public void stop() {
         if (sequencer != null && sequencer.isOpen()) {
             sequencer.stop();
             sequencer.setMicrosecondPosition(0);
         }
    }


    /**
        Closes the sequencer.
    */
    public void close() {
         if (sequencer != null && sequencer.isOpen()) {
             sequencer.close();
         }
    }


    /**
        Gets the sequencer.
    */
    public Sequencer getSequencer() {
        return sequencer;
    }


    /**
        Sets the paused state. Music may not immediately pause.
    */
    public void setPaused(boolean paused) {
        if (this.paused != paused && sequencer != null && sequencer.isOpen()) {
            this.paused = paused;
            if (paused) {
                sequencer.stop();
            }
            else {
                sequencer.start();
            }
        }
    }


    /**
        Returns the paused state.
    */
    public boolean isPaused() {
        return paused;
    }

}
