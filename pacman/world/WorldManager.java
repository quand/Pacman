package pacman.world;

import pacman.core.models.BaseManager;
import pacman.world.models.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;



public class WorldManager extends BaseManager {
	// =============================================================================================
    // CONSTANTS
    // =============================================================================================
    private static final int COLUMNS_COUNT = 19;
    private static final int ROW_COUNT = 22;
    /*
	private static final int RED_ENIMIES_COUNT = 6;
    private static final int PURPLE_ENIMIES_COUNT = 8;
    private static final int BLUE_ENIMIES_COUNT = 30;
	*/

    public static final int CELL_WIDTH = 40;
    public static final int CELL_HEIGHT = 40;

    // =============================================================================================
    // FIELDS
    // =============================================================================================
    private boolean[] keys;
    private Pacman player;
    private ArrayList<Wall> field;
    private ArrayList<Corn> corns;
    private int enemiesVelocity;
    private boolean holdEnemies;
    // =============================================================================================
    // CONSTRUCTOR
    // =============================================================================================
    public WorldManager() {
        keys = new boolean[65536];
        player = new Pacman();
        field = new ArrayList<>();
        corns = new ArrayList<>();
		initField();
        /*enemiesVelocity = RandomUtils.nextBoolean() ? ENEMIES_FORMATION_X_VELOCITY : -ENEMIES_FORMATION_X_VELOCITY;*/

    }

    // =============================================================================================
    // UPDATE
    // =============================================================================================
    @Override
    public void init() {
        initPlayer();
        initBricksPosition();
    }

    @Override
    public void update() {
        processInput();
        updatePlayer();
        //updateEnemies();
    }

    @Override
    public void render(Graphics g) 
	{
        player.draw(g);
        for (Wall brick : field) {
            brick.draw(g);
        }
        for (Wall corn : corns) {
            corn.draw(g);
        }
    }
    // =============================================================================================
    // PLAYER
    // =============================================================================================
    private void initPlayer() {
        player.x = 360;
        player.y = 480;
    }

    private void updatePlayer() {
        player.move();
        collision();
        teleport();
        if (player.x < 0) {
            player.x = 0;
        } else if (player.x + player.width > width) {
            player.x = width - player.width;
        }
        if (player.y < 0) {
            player.y = 0;
        } else if (player.y + player.height > height) {
            player.y = height - player.height;
        }
    }
    private void collision(){
        switch (player.direction) {
            case DOWN:
                for (Wall brick : field)
                    if (player.isIntersects(brick)&& brick.state == EnemyState.WALL)
                        player.y=brick.y-player.height;
                break;
            case TOP:
                for (Wall brick : field)
                    if (player.isIntersects(brick)&& brick.state == EnemyState.WALL)
                        player.y=brick.y+player.height;
                break;
            case LEFT:
                for (Wall brick : field)
                    if (player.isIntersects(brick)&& brick.state == EnemyState.WALL)
                        player.x=brick.x+player.width;
                break;
            case RIGHT:
                for (Wall brick : field)
                    if (player.isIntersects(brick)&& brick.state == EnemyState.WALL)
                        player.x=brick.x-player.width;
                break;
        }
    }
    private void teleport(){
        if(player.x+player.width>760&&player.y==400)
        {
            player.y=400;
            player.x=0;
        }
        if(player.x<0&&player.y==400)
        {
            player.y=400;
            player.x=720;
        }
    }
    //добавить: если стена пересекается с игроком, то делать скорость в этом направлении равной 0.

	// =============================================================================================
    //FIELD
    // =============================================================================================

	private void initField() {
	char[][] matrix={
		{'1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'},
		{'1','0','0','0','0','0','0','0','0','1','0','0','0','0','0','0','0','0','1'},
		{'1','0','1','1','0','1','1','1','0','1','0','1','1','1','0','1','1','0','1'},
		{'1','0','1','1','0','1','1','1','0','1','0','1','1','1','0','1','1','0','1'},
		{'1','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','1'},
		{'1','0','1','1','0','1','0','1','1','1','1','1','0','1','0','1','1','0','1'},
		{'1','0','0','0','0','1','0','0','0','1','0','0','0','1','0','0','0','0','1'},
		{'1','1','1','1','0','1','1','1','0','1','0','1','1','1','0','1','1','1','1'},
		{'*','*','*','1','0','1','0','0','0','0','0','0','0','1','0','1','*','*','*'},
		{'1','1','1','1','0','1','0','1','1','*','1','1','0','1','0','1','1','1','1'},
		{'*','*','*','*','0','0','0','1','*','*','*','1','0','0','0','*','*','*','*'},
		{'1','1','1','1','0','1','0','1','1','1','1','1','0','1','0','1','1','1','1'},
		{'*','*','*','1','0','1','0','0','0','0','0','0','0','1','0','1','*','*','*'},
		{'1','1','1','1','0','1','0','1','1','1','1','1','0','1','0','1','1','1','1'},
		{'1','0','0','0','0','0','0','0','0','1','0','0','0','0','0','0','0','0','1'},
		{'1','0','1','1','0','1','1','1','0','1','0','1','1','1','0','1','1','0','1'},
		{'1','0','0','1','0','0','0','0','0','0','0','0','0','0','0','1','0','0','1'},
		{'1','1','0','1','0','1','0','1','1','1','1','1','0','1','0','1','0','1','1'},
		{'1','0','0','0','0','1','0','0','0','1','0','0','0','1','0','0','0','0','1'},
		{'1','0','1','1','1','1','1','1','0','1','0','1','1','1','1','1','1','0','1'},
		{'1','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','1'},
        {'1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'}
	};
        for (int row = 0; row <ROW_COUNT;row++)
        {
            for (int column  = 0; column  <COLUMNS_COUNT;column++)
            {
                if(matrix[row][column]=='1')
                    field.add(new Brick(row, column));
                if (matrix[row][column]=='0')
                    corns.add(new Corn(row,column));
            }
        }
    }

	private void initBricksPosition(){
		int gridWidth = COLUMNS_COUNT * CELL_WIDTH + (COLUMNS_COUNT - 1);
        int leftMargin = (width - gridWidth) / 2;
		
		for (Wall brick : field) {
            brick.x = brick.column * CELL_WIDTH ;
            brick.y =  brick.row * CELL_HEIGHT ;
        }
        for (Wall corn : corns)
        {
            corn.x = corn.column*CELL_WIDTH+5;
            corn.y = corn.row*CELL_HEIGHT+5;
        }
	}

    // =============================================================================================
    // KEYBOARD EVENT HANDLERS
    // =============================================================================================
    private void processInput() {
        player.direction=Direction.NONE;
        if (keys[KeyEvent.VK_LEFT] && !keys[KeyEvent.VK_RIGHT]) {
            player.direction = Direction.LEFT;

        }
        if (!keys[KeyEvent.VK_LEFT] && keys[KeyEvent.VK_RIGHT]) {
            player.direction = Direction.RIGHT;
        }
        if (keys[KeyEvent.VK_UP] && !keys[KeyEvent.VK_DOWN]) {
            player.direction = Direction.TOP;
        }
        if (!keys[KeyEvent.VK_UP] && keys[KeyEvent.VK_DOWN]) {
            player.direction = Direction.DOWN;

        }
    }

    public void keyPressed(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();
        if (code >= 0 && code < keys.length) {
            keys[code] = true;
        }
    }

    public void keyReleased(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();
        if (code >= 0 && code < keys.length) {
            keys[code] = false;
        }
    }
}
