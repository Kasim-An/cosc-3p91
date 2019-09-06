package RoboRace;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import COSC3P40.graphics.*;

public class CardPane extends JPanel implements MouseListener {
	
	private Image noCard;
	private Image[] selectImages;
	private CardList cards = null;
	private int[] selected;
	private int numberSelected;
	private boolean selecting;
	
	public CardPane() {
		setPreferredSize(new Dimension(644,120));
		ImageManager manager = ImageManager.getInstance();
		noCard = manager.loadImage("Cards/NoCard.png");
		selectImages = new Image[5];
		for(int i=0; i<5; i++)
			selectImages[i] = manager.loadImage("Cards/Select" + (i+1) + ".png");
		selected = new int[7];
		numberSelected = 0;
		selecting = false;
		addMouseListener(this);
	}
	
	public CardList selectCards(CardList list) {
		cards = list;
		selecting = true;
		repaint();
		synchronized(this) {
			while(selecting) {
				try {
					wait();
				} catch(Exception e) {}
			};
		};
		return cards;
	}
	
	public void paintComponent(Graphics g) {
		g.fillRect(0,0,getSize().width,120);	
		for(int i=0; i<7; i++) {
			if (cards == null || i>=cards.size()) 
				g.drawImage(noCard,i*92,0,null);
			else {
				g.drawImage(cards.get(i).getImage(),i*92,0,null);
				if (selected[i]!=0)
					g.drawImage(selectImages[selected[i]-1],i*92+30,50,null);
			};
		};		
	}
	
	public void mouseClicked(MouseEvent e) {
	}
	
	public void mouseExited(MouseEvent e) {
	}
	
	public void mouseEntered(MouseEvent e) {
	}
	
	public void mousePressed(MouseEvent e) {
		if (selecting) {
			switch (e.getButton()) {
				case MouseEvent.BUTTON1:
					int number = e.getX() / 92;
					if (0 <= number && number < 7 && selected[number] == 0) {
						numberSelected++;
						selected[number] = numberSelected;
						if (numberSelected == 5) {
							CardList result = new CardList();
							for(int i=1; i<=5; i++)
								for(int j=0; j<7; j++) 
									if (selected[j]==i)
										result.add(cards.get(j));
							cards = result;
							for(int i=0; i<7; i++)
								selected[i]=0;
							numberSelected = 0;
							selecting = false;
							synchronized(this) {
								notify();
							};
						};
					};
					break;
				case MouseEvent.BUTTON3:
					if (numberSelected != 0) {
						for (int i = 0; i < 7; i++) {
							if (selected[i] == numberSelected) selected[i] = 0;
						};
						numberSelected--;
					};
			};
			repaint();
		};
	}
	
	public void mouseReleased(MouseEvent e) {
	}

	
}