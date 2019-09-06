package RoboRace;

import java.util.List;
import java.util.LinkedList;

public class Queue<E> {

	private List<E> data;
	
	public Queue() {
		data = new LinkedList<E>();
	}
		
	public synchronized void push(E element) {
		data.add(element);
		notify();
	}
	
	public synchronized E pull()  {
		while (data.size() == 0) {
			try { 
				wait(); 
			} catch (Exception e) {};
		};
		return data.remove(0);
	}
	
	public void close() {};
	
}