package RoboRace;

import COSC3P40.graphics.*;
import java.util.List;
import java.awt.*;
import java.awt.image.*;

public class AnimationFactory {
	
	private static Image[] beltNorth = null;
	private static Image[] beltEast = null;
	private static Image[] beltSouth = null;
	private static Image[] beltWest = null;
	private static Image[] floor = null;
	private static Image[] gearClockwise = null;
	private static Image[] gearCounterClockwise = null;
	private static Image[] robots = null;
	private static Image goal = null;
	private static Image pit = null;
	private static Image wallNorth = null;
	private static Image wallEast = null;
	private static Image wallSouth = null;
	private static Image wallWest = null;
	private static Image crusher = null;
	private static Image pusherNorth = null;
	private static Image pusherEast = null;
	private static Image pusherSouth = null;
	private static Image pusherWest = null;
		
	private static int beltImages = 38;
	private static int gearImages = 18;
	private static int robotImages = 72;
	
	public AnimationFactory() {
		ImageManager manager = ImageManager.getInstance();
		if (beltNorth == null) 
			beltNorth = loadBelt(Direction.North,manager);
		if (beltEast == null) 
			beltEast = loadBelt(Direction.East,manager);
		if (beltSouth == null) 
			beltSouth = loadBelt(Direction.South,manager);
		if (beltWest == null) 
			beltWest = loadBelt(Direction.West,manager);
		if (floor == null) {
			floor = new Image[6];
			for(int i=0; i<6; i++)
				floor[i] = manager.loadImage("Floor/Floor" + i + ".png");
		};
		if (gearClockwise == null)
			gearClockwise = loadGear(true,manager);
		if (gearCounterClockwise == null)
			gearCounterClockwise = loadGear(false,manager);
		if (robots == null)
			robots = loadRobots(manager);
		if (goal == null) 
			goal = manager.loadImage("Goal/Goal.png");
		if (pit == null) 
			pit = manager.loadImage("Pit/Pit.png");		
		if (wallNorth == null) 
			wallNorth = manager.loadImage("Walls/WallNorth.png");
		if (wallEast == null) 
			wallEast = manager.loadImage("Walls/WallEast.png");
		if (wallSouth == null) 
			wallSouth = manager.loadImage("Walls/WallSouth.png");
		if (wallWest == null) 
			wallWest = manager.loadImage("Walls/WallWest.png");
		if (crusher == null) 
			crusher = manager.loadImage("Crusher/Crusher.png");
		if (pusherNorth == null) 
			pusherNorth = manager.loadImage("Pusher/PusherNorth.png");
		if (pusherEast == null) 
			pusherEast = manager.loadImage("Pusher/PusherEast.png");
		if (pusherSouth == null) 
			pusherSouth = manager.loadImage("Pusher/PusherSouth.png");
		if (pusherWest == null) 
			pusherWest = manager.loadImage("Pusher/PusherWest.png");
	}
	
	public Animation createAnimation(Tile tile, Decoration decoration, List<Direction> walls) {
		Animation animation = new Animation(true);
		if (tile instanceof Belt) {
			Image[] images = null;
			switch(((Belt) tile).getDirection()) {
				case North: images = beltNorth;
							break;
				case East:  images = beltEast;
							break;
				case South:	images = beltSouth;
							break;
				case West:  images = beltWest;
			};
			int rand = (int) Math.floor(beltImages*Math.random());
			for(int i=0; i<beltImages; i++) {
				Image image = addAddOns(images[rand],decoration,walls);
				if (rand==19)
					animation.addFrame(image,200);
				else 
				    animation.addFrame(image,20);
				rand++;
				if (rand >= beltImages) rand = 0;
			};
		};
		if (tile instanceof Floor) {
			int rand = (int) Math.floor(6*Math.random());
			Image image = addAddOns(floor[rand],decoration,walls);
			animation.addFrame(image,1000);
		};
		if (tile instanceof Gear) {
			Image[] images = null;
			if (((Gear) tile).isClockwise()) 
				images = gearClockwise;
			else
				images = gearCounterClockwise;
			int rand = (int) Math.floor(gearImages*Math.random());
			for(int i=0; i<gearImages; i++) {
				Image image = addAddOns(images[rand],decoration,walls);
				animation.addFrame(image,20);
				rand++;
				if (rand >= gearImages) rand = 0;
			};
		};
		if (tile instanceof Goal) {
			Image image = addAddOns(goal,decoration,walls);
			animation.addFrame(image,1000);
		};
		if (tile instanceof Pit) {
			Image image = addAddOns(pit,decoration,walls);
			animation.addFrame(image,1000);
		};	
		return animation;
	}
	
	public Animation[] createRobotAnimations(String name) {
		Image[] images = new Image[robotImages];
		for(int i=0; i<robotImages; i++)
			images[i] = addName(robots[i],name);
		Animation[] result = new Animation[12];
		for(int i=0; i<12; i++)
			result[i] = new Animation(false);
		for(int i=0; i<=18; i++) {
			result[0].addFrame(images[i],20);
			result[1].addFrame(images[i+18],20);
			result[2].addFrame(images[i+36],20);
			if (i==18) 
				result[3].addFrame(images[0],20);
			else result[3].addFrame(images[i+54],20);
			if (i==0) 
				result[4].addFrame(images[0],20);
			else result[4].addFrame(images[72-i],20);
			result[5].addFrame(images[54-i],20);	
			result[6].addFrame(images[36-i],20);
			result[7].addFrame(images[18-i],20);
		};
		for(int i=0; i<=36; i++) {
			result[8].addFrame(images[i],20);
			result[9].addFrame(images[i+18],20);
		    if (i==36) 
		    	result[10].addFrame(images[0],20);
			else result[10].addFrame(images[i+36],20);
			if (i<18)
				result[11].addFrame(images[i+54],20);
			else result[11].addFrame(images[i-18],20);
		};
		return result;
	}
	
	private Image[] loadBelt(Direction direction, ImageManager manager) {
		Image[] result = new Image[beltImages];
		for(int i=0; i<beltImages; i++) {
		    String str = "Belt" + direction + "/Belt";
		    if (i<10) str += "0" + i; else str += i;
			result[i] = manager.loadImage(str + ".png");
		};
		return result;
	}
	
	private Image[] loadGear(boolean clockwise, ImageManager manager) {
		Image[] result = new Image[gearImages];
		for(int i=0; i<gearImages; i++) {
		    String str = "Gear";
		    if (clockwise) str += "Clockwise/Gear"; else str += "CounterClockwise/Gear";
		    if (i<10) str += "0" + i; else str += i;
			result[i] = manager.loadImage(str + ".png");
		};
		return result;
	}
	
	private Image[] loadRobots(ImageManager manager) {
		Image[] result = new Image[robotImages];
		for(int i=0; i<robotImages; i++)
			result[i] = manager.loadImage("Robot/robot" + i + ".png");
		return result;
	}
	
	private Image addAddOns(Image image, Decoration decoration, List<Direction> walls) {
		return addDecoration(addWalls(image,walls),decoration);
	}
	
	private Image addWalls(Image image, List<Direction> walls) {
		if (!walls.isEmpty()) {
			Image result = cloneImage(image);
			Graphics graphics = result.getGraphics();
			graphics.drawImage(image,0,0,null);
			if (walls.contains(Direction.North))
				graphics.drawImage(wallNorth,0,0,null);
			if (walls.contains(Direction.East))
				graphics.drawImage(wallEast,0,0,null);
			if (walls.contains(Direction.South))
				graphics.drawImage(wallSouth,0,0,null);
			if (walls.contains(Direction.West))
				graphics.drawImage(wallWest,0,0,null);
			graphics.dispose();
			return result;
		};
		return image;
	}
	
	private Image addDecoration(Image image, Decoration decoration) {
		if (decoration != null) {
			Image result = cloneImage(image);
			Graphics graphics = result.getGraphics();
			if (decoration instanceof Pusher) {
				Direction direction = ((Pusher) decoration).getDirection();
				switch (direction) {
					case North: graphics.drawImage(pusherNorth,0,0,null);
								break;
					case East:  graphics.drawImage(pusherEast,0,0,null);
								break;
					case South: graphics.drawImage(pusherSouth,0,0,null);
								break;
					case West:  graphics.drawImage(pusherWest,0,0,null);
				};
			};
			if (decoration instanceof Crusher) {
				graphics.drawImage(crusher,0,0,null);
			};
			graphics.dispose();
			return result;
		};
		return image;	
	}
	
	private Image addName(Image image, String name) {
		Image result = cloneImage(image);
		Graphics graphics = result.getGraphics();
		graphics.setColor(Color.red);
		graphics.drawString(name,10,64);
		graphics.dispose();
		return result;
	}
	
	private Image cloneImage(Image image) {
		BufferedImage result = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = result.createGraphics();
		graphics.drawImage(image,0,0,null);
		graphics.dispose();
		return result;
	}
}