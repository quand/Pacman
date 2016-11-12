package pacman.world.models;

import pacman.core.models.MoveableObject;
import pacman.utils.ResourcesLoader;

public class Ghost extends MoveableObject {

    private int id=0;

    public Ghost(int row, int column,int id){
        this.row = row;
        this.id = id;
        this.prevRow=row;
        this.prevColumn=column;
        direction=Direction.TOP;
        this.column = column;
        state = GhostState.STILL;
        sprite = ResourcesLoader.loadDrawableIgnoreErrors("ghost"+id+".png");
        width = sprite.getWidth(null);
        height = sprite.getHeight(null);
    }
    public boolean placeChanged(){
        return prevRow!=row||prevColumn!=column;
    }
}
