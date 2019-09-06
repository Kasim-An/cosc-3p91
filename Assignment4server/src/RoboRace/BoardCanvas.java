package RoboRace;

import java.awt.*;
import java.awt.image.*;
import COSC3P40.graphics.*;
import java.util.List;

public class BoardCanvas extends Canvas implements Runnable {
	
	private Board board;
	private boolean running;
	private Thread thread;
	
	public BoardCanvas(Board board) {
		this.board = board;
		Dimension dim = board.getDisplaySize();
		setSize(dim.width,dim.height);
	}
	
	public void start() {
		thread = new Thread(this);
		running = true;
		board.start();
		thread.start();
	}
	
	public void stop() {
		running = false;
		try{
			thread.join();
		} catch(Exception e) {}
	}
	
	public void run() {
		createBufferStrategy(2);
		BufferStrategy strategy = getBufferStrategy(); 
		long lastLoopTime = System.currentTimeMillis();
		long currentTime;
		long delta;
		Graphics graphics;
		while (running) {
			currentTime = System.currentTimeMillis();
			delta = currentTime - lastLoopTime;
			lastLoopTime = currentTime;
			board.update(delta);
			graphics = strategy.getDrawGraphics();
			board.draw(graphics);
			graphics.dispose();
			strategy.show();
			if (delta < 10) try { 
				                 Thread.sleep(10-delta); 
			                } catch (Exception e) {};
		}
	}
	
}