package pacman.core.models;

import pacman.world.models.Direction;
import pacman.world.models.GhostState;

import java.awt.*;


public class MoveableObject extends DrawableObject {
    public Direction direction=Direction.NONE;
    public static final int DEFAULT_VELOCITY = 5;
    protected static final int UPDATE_TO_CHANGE_SPRITE=4;
    protected GhostState state;
    protected Image sprite;

    public void move(){
        switch(this.direction){
            case LEFT:
                x-=DEFAULT_VELOCITY;
                break;
            case TOP:
                y-=DEFAULT_VELOCITY;
                break;
            case RIGHT:
                x+=DEFAULT_VELOCITY;
                break;
            case DOWN:
                y+=DEFAULT_VELOCITY;
                break;
        }
    }

    @Override
    public void draw(Graphics g) {
        switch (state) {
            case STILL:
                g.drawImage(sprite, x, y, null);
                break;
            case KILL:
                //dyingAnimator.drawNextFrame(g, x, y);
                break;
        }
    }
}
