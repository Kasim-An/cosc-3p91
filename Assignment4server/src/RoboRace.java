import RoboRace.*;
import javax.swing.*;
import COSC3P40.graphics.*;
import COSC3P40.sound.SoundManager;
import COSC3P40.xml.*;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class RoboRace {
    
    public static void main(String[] args) throws InvalidMidiDataException, IOException, MidiUnavailableException {
    	JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
	ImageManager.getInstance().setImagePath("./Images/");
	XMLReader.setXMLPath("./");
	XMLReader.setXSDPath("./XSD/");	
    	int nHuman = 0;
    	while (nHuman==0 || nHuman>4) {
	    	try {
	    		nHuman = Integer.parseInt(GameDialogs.showInputDialog("Number of human players","Please, input the number of human players (1-4):"));
	    	} catch(Exception e) {};
	};
        ServerSocket server;
        Socket sock;
        String[] names=new String[nHuman];
        NetworkPort[] ports=new NetworkPort[nHuman];
        try{
            server=new ServerSocket(998);
            for(int i=0;i<nHuman;i++){
                sock=server.accept();
                ports[i]=new NetworkPort(sock);
                names[i]=ports[i].recieve();
            }
        }
        catch(IOException e){}
    	(new GameMaster(nHuman,names,ports)).run();
        
        
    }	   
}
