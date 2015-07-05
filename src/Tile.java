import java.awt.Color;

import acm.graphics.GRoundRect;


public class Tile extends GRoundRect{
	public static final int TILE_SIZE = 180;
	public static final int OFFSET = 10;
	public static final int BOX_SIZE = TILE_SIZE + 2 * OFFSET;

	private int x;
	private int y;
	private int value;
	
	private static final Color CASE_4 = new Color(0xede0c8);
	private static final Color CASE_8 = new Color(0xf2b179);
	private static final Color CASE_16 = new Color(0xf59563);
	private static final Color CASE_32 = new Color(0xf67c5f);
	private static final Color CASE_64 = new Color(0xf65e3b);
	private static final Color CASE_128 = new Color(0xedcf72);
	private static final Color CASE_256 = new Color(0xedcc61);
	private static final Color CASE_512 = new Color(0xedc850);
	private static final Color CASE_1024 = new Color(0xedc53f);
	private static final Color CASE_2048 = new Color(0xedc22e);

	public Tile(int x, int y) {
		super(x * BOX_SIZE + OFFSET, y * BOX_SIZE + OFFSET, TILE_SIZE, TILE_SIZE);
		this.x = x;
		this.y = y;
		int ref = (int) (4 * Math.random());
		if(ref < 3) {
			value = 2;
		} else {
			value = 4;
		}
	}

	public int getTileX() {
		return x;
	}

	public int getTileY() {
		return y;
	}

	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}

	//error
	public boolean moveTile(String direction, Tile[][] grid) {
		boolean hasMoved = false;
		int dx = 0;
		int dy = 0;

		switch(direction) {
		case "up":
			dy = -1;
			break;
		case "down":
			dy = 1;
			break;
		case "right":
			dx = 1;
			break;
		case "left":
			dx = -1;
			break;
		default:
			System.err.println("INVALID MOVEMENT");
			break;
		}

		for(int i = 0; i < 3; i++) {
			try{
				if(grid[x + dx][y + dy] == null) {
					move(dx * BOX_SIZE, dy * BOX_SIZE);
					grid[x + dx][y + dy] = this;
					grid[x][y] = null;
					x = x + dx;
					y = y + dy;
					hasMoved = true;
				} else if(grid[x + dx][y + dy].getValue() == value) {
					mergeTile(grid[x + dx][y + dy]);
					setVisible(false);
					grid[x][y] = null;
					hasMoved = true;
				} 
			} catch(Exception e) {
				break;
			}
		}
		return hasMoved;
	}

	public void mergeTile(Tile other) {
		other.setValue(2 * other.getValue());
		Color c = Color.WHITE;
		switch(other.getValue()) {
		case 4:
			c = CASE_4;
			break;
		case 8:
			c = CASE_8;
			break;
		case 16:
			c = CASE_16;
			break;
		case 32:
			c = CASE_32;
			break;
		case 64:
			c = CASE_64;
			break;
		case 128:
			c = CASE_128;
			break;
		case 256:
			c = CASE_256;
			break;
		case 512:
			c = CASE_512;
			break;
		case 1024:
			c = CASE_1024;
			break;
		case 2048:
			c = CASE_2048;
			break;
		default:
			break;
		}
		other.setColor(c);
		
	
	}

}
