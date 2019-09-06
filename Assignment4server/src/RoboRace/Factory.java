package RoboRace;

import COSC3P40.xml.*;
import java.awt.*;
import java.io.*;

public class Factory implements XMLObject {
	
	public static Factory load(String fileName) {
		XMLReader<Factory> reader = new XMLReader<Factory>();
        reader.setXMLSchema("factory.xsd");
        reader.setXMLNodeConverter(new FactoryReader());
        return reader.readXML(fileName);
	}	
	
	private int xSize;
	private int ySize;
	private Location[][] grid;
	
	public Factory(int xSize, int ySize, Location[][] grid) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.grid = grid;
	}
	
	public int getXSize() {
		return xSize;
	}
	
	public int getYSize() {
		return ySize;
	}
	
	public Location getLocation(int x, int y) {
		return grid[x][y];
	}
	
	public Location getLocation(Point p) {
		return getLocation(p.x,p.y);
	}
	
	public boolean hasWall(Point location, Direction direction) {
		return grid[location.x][location.y].hasWall(direction);
	}
	
	public String toXMLString() {
		String result = "<factory xSize=\"" + xSize + "\" ySize=\"" + ySize +"\">\n";
		for(int j=0; j<ySize; j++)
			for(int i=0; i<xSize; i++)
				result += grid[i][j].toXMLString() + "\n";
		return result + "</factory>";
	}
	
	public Dimension getDisplaySize() {
		return new Dimension(xSize*97,ySize*97);
	}
	
	public void start() {
		for(Location[] row : grid)
			for(Location loc : row)
				loc.start();
	}
	
	public void update(long delta) {
		for(Location[] row : grid)
			for(Location loc : row)
				loc.update(delta);
	}
	
	public void draw(Graphics graphics) {
		for(int i=0; i<xSize; i++)
			for(int j=0; j<ySize; j++) {
				Location loc = grid[i][j];
				graphics.drawImage(loc.getImage(),i*97,j*97,null);
			}
	}
	
}