import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.graphics.GRoundRect;
import acm.program.GraphicsProgram;


public class GameBoard extends GraphicsProgram{

	public static final int APPLICATION_WIDTH = 800;
	public static final int APPLICATION_HEIGHT = 800;

	private int boxSize = 200;

	private int width = 4;
	private int height = 4;

	private int offset = 10;

	private Tile[][] tileList = new Tile[width][height];

	private boolean hasMoved = true;
	private boolean allFull = false;

	private static final Color BACKGROUND_COLOR = new Color(187, 173, 160);
	private static final Color SQUARE_COLOR = new Color(205,193,180);

	private static final Color CASE_2 = new Color(0xeee4da);
	private static final Color CASE_4 = new Color(0xede0c8);
	private static final Color CASE_2048 = new Color(0xedc22e);

	public static void main(String args[]) {
		new GameBoard().start(args);

	}

	public void run() {
		setBackground(BACKGROUND_COLOR);
		drawGrid();
		addKeyListeners();
		spawnTile();
		spawnTile();

		while(true) {
			GRect cover = new GRect(0, 0, APPLICATION_WIDTH, APPLICATION_HEIGHT);
			GLabel win = new GLabel("You've reached the last tile! Congrats!", 100, 350);
			GLabel lose = new GLabel("No more moves. You've lost.", 100, 350);
			Font f = new Font("Arial", 1, 40);

			for(int j = 0; j < height; j++) {
				for(int i = 0; i < width; i++) {
					if(tileList[i][j] != null && tileList[i][j].getValue() == 2048) {

						cover.setFilled(true);
						cover.setColor(CASE_2048);
						add(cover);


						win.setColor(Color.WHITE);
						add(win);

						win.setFont(f);
					}

					if(tileList[i][j] == null) {
						allFull = false;
					} else if(i == 3 && j == 3 && tileList[i][j] != null) {
						allFull = true;
					}

					if(allFull && hasMoved == false) {
						cover.setFilled(true);
						cover.setColor(Color.BLACK);
						add(cover);

						lose.setColor(Color.WHITE);
						add(lose);
						win.setFont(f);
					}
				} 
			}
			pause(20);
		}

	}


	public void spawnTile() {
		int xPos = (int) (4 * Math.random());
		int yPos = (int) (4 * Math.random());

		if(tileList[xPos][yPos] == null) {
			Tile tile = new Tile(xPos, yPos);
			tileList[xPos][yPos] = tile;
			tile.setFilled(true);
			if(tile.getValue() == 2) {
				tile.setColor(CASE_2);
			}
			else {
				tile.setColor(CASE_4);
			}
			add(tile);
		} else {
			spawnTile();
		}
	}

	public void drawGrid() {
		for(int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				GRoundRect myRect = new GRoundRect((boxSize * i) + offset, (boxSize * j) + offset, boxSize - (2 * offset), boxSize - (2 * offset));
				myRect.setFilled(true);
				myRect.setColor(SQUARE_COLOR);
				add(myRect);
			}

		}

	}

	public boolean isColliding(GObject object1, GObject object2) {
		if(object1.getBounds().intersects(object2.getBounds())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isCollidable(int x, int y) {
		if(x >= 0 && x < width && y >= 0 && y < height) {
			if(tileList[x][y] != null) {
				return false;
			} else {
				return true;
			} 
		} else {
			return false;
		}
	}

	public void keyPressed(KeyEvent ke) {

		if(ke.getKeyCode() == KeyEvent.VK_RIGHT) {
			hasMoved = false;

			for(int j = 0; j < height; j++) {
				for(int i = width - 2; i >= 0; i--) {
					if(tileList[i][j] != null) {
						if(tileList[i][j].moveTile("right", tileList)) {
							hasMoved = true;
						}
					}
				}
			}

			if(hasMoved) {
				spawnTile();
			}

		} else if(ke.getKeyCode() == KeyEvent.VK_LEFT) {
			hasMoved = false;

			for(int j = 0; j < height; j++) {
				for(int i = 1; i < width; i++) {
					if(tileList[i][j] != null) {
						if(tileList[i][j].moveTile("left", tileList)) {
							hasMoved = true;
						}
					}
				}
			}

			if(hasMoved) {
				spawnTile();
			}

		} else if(ke.getKeyCode() == KeyEvent.VK_DOWN) {
			hasMoved = false;

			for(int j = height - 2; j >= 0; j--) {
				for(int i = 0; i < width; i++) {
					if(tileList[i][j] != null) {
						if(tileList[i][j].moveTile("down", tileList)) {
							hasMoved = true;
						}
					}
				}
			}

			if(hasMoved) {
				spawnTile();
			}

		} else if(ke.getKeyCode() == KeyEvent.VK_UP) {
			hasMoved = false;

			for(int j = 1; j < height; j++) {
				for(int i = 0; i < width; i++) {
					if(tileList[i][j] != null) {
						if(tileList[i][j].moveTile("up", tileList)) {
							hasMoved = true;
						}
					}
				}
			}

			if(hasMoved) {
				spawnTile();
			}

		}


	}

}
