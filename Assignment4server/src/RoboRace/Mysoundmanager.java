package RoboRace;

import COSC3P40.midi.MidiManager;
import COSC3P40.sound.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Mysoundmanager extends SoundManager{
    File BGM=new File("Sounds&Midi\\3.mid");
    MidiManager mm=new MidiManager();
    public Mysoundmanager() {
        super(new AudioFormat(8000,8,1,false,false));
    }
    public void playbump(){
        super.setSoundPath("./Sounds&Midi");
        super.play(super.getSound("bump.wav"));
    }
    public void playvictory(){
        super.setSoundPath("./Sounds&Midi");
        super.play(super.getSound("fanfare.wav"));
    }
    public void playexplosion(){
        super.setSoundPath("./Sounds&Midi");
        super.play(super.getSound("explosion.wav"));
    }
    public void playBGM() throws InvalidMidiDataException, IOException{
        Sequence sequence=MidiSystem.getSequence(BGM);
            
            mm.play(sequence, true);
    }
}
