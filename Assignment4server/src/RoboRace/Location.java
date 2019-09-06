package RoboRace;

import COSC3P40.xml.*;
import COSC3P40.graphics.*;
import java.util.List;
import java.awt.*;

public class Location implements XMLObject {
	
	private Tile tile;
	private Decoration decoration;
	private List<Direction> walls;
	private Animation animation;
	
	private static AnimationFactory animationFactory = null;
	
	public Location(Tile tile, Decoration decoration, List<Direction> walls) {
		this.tile = tile;
		this.decoration = decoration;
		this.walls = walls;
		if (animationFactory == null)
			animationFactory = new AnimationFactory();
		animation = animationFactory.createAnimation(tile,decoration,walls);
	}
	
	public boolean hasWall(Direction d) {
		return walls.contains(d);
	}
	
	public boolean isPit() {
		return (tile instanceof Pit);
	}
	
	public void effect(EventCounter counter, EventList events, int phase, Robot robot, Board board) {
		int size = events.size();
		if (decoration != null)
			decoration.effect(counter,events,phase,robot,board);
		if (size == events.size()) 
			tile.effect(counter,events,robot,board);
	}
	
	public String toXMLString() {
		String result = "<location>\n";
		result += tile.toXMLString() + "\n";
		if (decoration != null)
			result += decoration.toXMLString() + "\n";
		if (!walls.isEmpty()) {
			result += "<walls ";
			for (Direction d : Direction.values()) 
				result += d.toString().toLowerCase() + "=\"" + walls.contains(d) + "\" ";
			result += "/>\n";
		};
		return result + "</location>";
	}
	
	public void start() {
		animation.start();
	}
	
	public void update(long delta) {
		animation.update(delta);
	}
	
	public Image getImage() {
		return animation.getImage();
	}
	
}