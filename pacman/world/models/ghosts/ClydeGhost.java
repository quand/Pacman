package pacman.world.models.ghosts;

import pacman.core.models.MoveableObject;
import pacman.utils.ResourcesLoader;
import pacman.world.models.Direction;
import pacman.world.models.GhostState;

public class ClydeGhost extends MoveableObject {

    private int id=0;

    public ClydeGhost(int row, int column, int id){
        this.row = row;
        this.id = id;
        direction= Direction.LEFT;
        this.column = column;
        state = GhostState.CHASE;
        sprite = ResourcesLoader.loadDrawableIgnoreErrors("ghost1.png");
        width = sprite.getWidth(null);
        height = sprite.getHeight(null);
    }
    public boolean placeChanged(){
        return prevRow!=row||prevColumn!=column;
    }
}
