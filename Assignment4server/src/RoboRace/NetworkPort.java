package RoboRace;

import java.io.IOException;
import java.net.Socket;
import java.io.*;
public class NetworkPort implements Port
{
    private BufferedReader in;
    private PrintWriter out;
    public NetworkPort(Socket client) throws IOException
    {
        try{
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public synchronized void send(String message) {
        if (out != null) {
		out.println(message);
                out.println("\0");
		out.flush();
	};
    }

    @Override
    public String recieve() {
        String returnstring="";
        try {
            while(true){
                String a=in.readLine();
                if(a.equals("\0")){
                    break;
                }
                returnstring=returnstring+a;
            }
        } catch (IOException ex) {
        }
        return returnstring;
    }

    public void close() throws IOException 
    {      
    }
    
}
