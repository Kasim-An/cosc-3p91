package RoboRace;

import java.io.Closeable;

public class Channel {
	
	private Queue<String> port1ToPort2;
	private Queue<String> port2ToPort1;
	
	public Channel() {
		port1ToPort2 = new Queue<String>();
		port2ToPort1 = new Queue<String>();
	}
	
	public void pushPort1(String message) { 
		port1ToPort2.push(message);
	}
	
	public void pushPort2(String message) { 
		port2ToPort1.push(message);
	}
	
	public String pullPort1() {
		return port2ToPort1.pull();
	}
	
	public String pullPort2() {
		return port1ToPort2.pull();
	}
	
	public Port asPort1() {
		return new Port() {
			public void send(String message) {
				pushPort1(message);
			}
	
			public String recieve() {
				return pullPort1();
			}
			
			public void close() {}
		};
	}
	
	public Port asPort2() {
		return new Port() {
			public void send(String message) {
				pushPort2(message);
			}
	
			public String recieve() {
				return pullPort2();
			}
			
			public void close() {}
		};
	}

}