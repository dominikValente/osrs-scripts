package osrs_scripts;

import org.powerbot.script.Area;
import org.powerbot.script.Tile;

public class Obstacle {
	
	String action;
	int ID;
	Area area;
	
	public Obstacle(String action, int ID, int x1, int y1, int x2, int y2, int z) {
		this.action = action;
		this.ID = ID;
		area = new Area(new Tile(x1, y1, z), new Tile(x2, y2, z));
	}
}
