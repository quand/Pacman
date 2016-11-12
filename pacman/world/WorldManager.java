package pacman.world;

import pacman.core.models.BaseManager;
import pacman.world.models.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import static pacman.core.models.MoveableObject.DEFAULT_VELOCITY;


public class WorldManager extends BaseManager {
    // =============================================================================================
    // CONSTANTS
    // =============================================================================================
    private static final int COLUMNS_COUNT = 19;
    private static final int ROW_COUNT = 22;
    private static final int MISTAKE = DEFAULT_VELOCITY*2;
    /*
	private static final int RED_ENIMIES_COUNT = 6;
    private static final int PURPLE_ENIMIES_COUNT = 8;
    private static final int BLUE_ENIMIES_COUNT = 30;
	*/

    public static final int CELL_WIDTH = 30;
    public static final int CELL_HEIGHT = 30;

    // =============================================================================================
    // FIELDS
    // =============================================================================================
    private boolean[] keys;
    private Pacman player;
    private ArrayList<Wall> field;
    private ArrayList<Corn> corns;
    private ArrayList<Ghost> ghosts;
    Random random;
    private int enemiesVelocity;
    private boolean holdEnemies;
    // =============================================================================================
    // CONSTRUCTOR
    // =============================================================================================
    public WorldManager() {
        keys = new boolean[65536];
        player = new Pacman(9,12);
        field = new ArrayList<>();
        corns = new ArrayList<>();
        initField();
        ghosts = new ArrayList<>();
        initGhost();
        random = new Random();
        /*enemiesVelocity = RandomUtils.nextBoolean() ? ENEMIES_FORMATION_X_VELOCITY : -ENEMIES_FORMATION_X_VELOCITY;*/

    }

    // =============================================================================================
    // UPDATE
    // =============================================================================================
    @Override
    public void init() {
        initPlayer();
        initBricksPosition();
        initCornPosition();
        initGhostPosition();
    }
    @Override
    public void update() {
        processInput();
        updatePlayer();
        updateGhosts();
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
        for (Ghost ghost : ghosts)
            ghost.draw(g);
    }

    // =============================================================================================
    // GHOST
    // =============================================================================================
    private void initGhost(){
        for (int i=0;i<4;i++){
            ghosts.add(new Ghost(8,8+i,i+1));
        }
    }
    private void initGhostPosition() {
        for (Ghost ghost : ghosts) {
            ghost.x = ghost.column * CELL_WIDTH;
            ghost.y = ghost.row * CELL_HEIGHT;
        }
    }

    private void randomDirection(Ghost ghost,char[] canMove){
        Direction dir = ghost.direction;
        int r = random.nextInt(4);
        if (canMove[r]!='0')
        {
            switch (canMove[r]){
                case 't': ghost.direction=Direction.TOP;
                    break;
                case 'd': ghost.direction=Direction.DOWN;
                    break;
                case 'l': ghost.direction=Direction.LEFT;
                    break;
                case 'r': ghost.direction=Direction.RIGHT;
                    break;
            }
        }
        else randomDirection(ghost,canMove);
    }
    private void moveGhost(){
        for (Ghost ghost : ghosts)
            ghost.move();

    }
    private void collisionG(){
        for (Ghost ghost:ghosts)
        {
            switch (ghost.direction) {
                case DOWN:
                    for (Wall brick : field)
                        if (ghost.isIntersects(brick))
                            ghost.y = brick.y - ghost.height;
                    break;
                case TOP:
                    for (Wall brick : field)
                        if (ghost.isIntersects(brick)){
                            ghost.y=brick.y+ghost.height;
                        }
                    break;

                case LEFT:
                    for (Wall brick : field)
                        if (ghost.isIntersects(brick))
                            ghost.x=brick.x+ghost.width;
                    break;
                case RIGHT:
                    for (Wall brick : field)
                        if (ghost.isIntersects(brick))
                            ghost.x=brick.x-ghost.width;
                    break;
            }

        }
    }
    private int checkMove(char[] canMove){
        int sum=0;
        for (int i=0;i<4;i++)
            if (canMove[i]!='0')
                sum++;
        return sum;
    }

    private void changeDirection(){
        collisionG();
        checkBlock();
        //for (int i=0;i<6;i++)
    }


    private void checkBlock(){
        for (Ghost ghost : ghosts) {
            checkBrick(ghost);
        }

    }

    void checkBrick(Ghost ghost){
        char[] canMove = {'t','d','l','r'};
        for (Wall brick : field) {
            if ((ghost.row - 1 == brick.row && ghost.column == brick.column)||ghost.direction==Direction.DOWN)
                canMove[0] = '0';
            if ((ghost.row + 1 == brick.row && ghost.column == brick.column)||ghost.direction==Direction.TOP)
                canMove[1] = '0';
            if ((ghost.row == brick.row && ghost.column - 1 == brick.column)||ghost.direction==Direction.RIGHT)
                canMove[2] = '0';
            if ((ghost.row == brick.row && ghost.column + 1 == brick.column)||ghost.direction==Direction.LEFT)
                canMove[3] = '0';
        }
        if(checkMove(canMove)>1)
            randomDirection(ghost,canMove);
        else for (int i=0;i<4;i++)
            if (canMove[i]!='0')
            {
                switch (canMove[i]){
                    case 't': ghost.direction=Direction.TOP;
                        break;
                    case 'd': ghost.direction=Direction.DOWN;
                        break;
                    case 'l': ghost.direction=Direction.LEFT;
                        break;
                    case 'r': ghost.direction=Direction.RIGHT;
                        break;
                }
            }
    }

    void firstStep(){
        for (Ghost ghost : ghosts) {
            if (ghost.row==10&&ghost.column==9)
                for (int i=0;i<8;i++)
                    ghost.y-=DEFAULT_VELOCITY;
        }
    }

    private void updateGhosts(){
        //firstStep();
        moveGhost();
        increasePlace();
        changeDirection();
    }
    void increasePlace(){

    }
    // =============================================================================================
    // PLAYER
    // =============================================================================================
    private void initPlayer() {
        player.x = (int)player.column * CELL_WIDTH;
        player.y = (int)player.row * CELL_HEIGHT;
    }

    private void updatePlayer() {
        player.move();
        collision();
        teleport();
        eatCorn();
        player.setImage(player.direction);
    }

    private void collision(){
        switch (player.direction) {
            case DOWN:
                for (Wall brick : field)
                    if (brick.x+brick.width-player.x<=MISTAKE&&brick.x+brick.width-player.x>0)
                        player.x=brick.x+brick.width;
                    else if (player.x+player.width-brick.x<=MISTAKE&&player.x+player.width-brick.x>0)
                        player.x=brick.x-CELL_WIDTH;
                    else if (player.isIntersects(brick))
                        player.y=brick.y-player.height;
                break;
            case TOP:
                for (Wall brick : field)
                    if (brick.x+brick.width-player.x<=MISTAKE&&brick.x+brick.width-player.x>0)
                        player.x=brick.x+brick.width;
                    else if (player.x+player.width-brick.x<=MISTAKE&&player.x+player.width-brick.x>0)
                        player.x=brick.x-CELL_WIDTH;
                    else if (player.isIntersects(brick))
                        player.y=brick.y+player.height;
                break;

            case LEFT:
                for (Wall brick : field)
                    if (player.y+player.height-brick.y<=MISTAKE&&player.y+player.height-brick.y>0)
                        player.y=brick.y-CELL_HEIGHT;
                    else if ((brick.y+brick.height)-player.y<=MISTAKE&&(brick.y+brick.height)-player.y>0)
                        player.y=brick.y+brick.height;
                    else if (player.isIntersects(brick))
                        player.x=brick.x+player.width;
                break;
            case RIGHT:
                for (Wall brick : field)
                    if (player.y+player.height-brick.y<=MISTAKE&&player.y+player.height-brick.y>0)
                        player.y=brick.y-CELL_HEIGHT;
                    else if ((brick.y+brick.height)-player.y<=MISTAKE&&(brick.y+brick.height)-player.y>0)
                        player.y=brick.y+brick.height;
                    else if (player.isIntersects(brick))
                        player.x=brick.x-player.width;
                break;
        }
    }

    private void eatCorn(){
        for (Wall corn : corns)
        {
            if (player.isIntersects(corn))
            {
                player.score+=100;
                corn.state=ObjectState.NOTHING;
            }
        }
    }
    private void teleport(){
        if(player.x+player.width>19*CELL_WIDTH&&player.y==10*CELL_HEIGHT)
        {
            player.y=10*CELL_HEIGHT;
            player.x=0;
        }
        if(player.x<0&&player.y==10*CELL_HEIGHT)
        {
            player.y=10*CELL_HEIGHT;
            player.x=18*CELL_WIDTH;
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
		{'*','*','*','1','0','1','0','0','0','*','0','0','0','1','0','1','*','*','*'},
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

	private void initBricksPosition() {
            for (Wall brick : field) {
            brick.x = brick.column * CELL_WIDTH;
            brick.y = brick.row * CELL_HEIGHT;
        }
    }
    //============================================================================================
    //Corns
    //============================================================================================
    private void initCornPosition(){
        for (Wall corn : corns)
        {
            corn.x = corn.column*CELL_WIDTH+10;
            corn.y = corn.row*CELL_HEIGHT+10;
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
