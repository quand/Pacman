package pacman.world.models;

import pacman.core.models.MoveableObject;
import pacman.utils.ResourcesLoader;

public class Ghost extends MoveableObject {

    public int id=0;

    public Ghost(int row, int column,int id){
        this.row = row;
        this.id = id;
        direction=Direction.TOP;
        this.column = column;
        state = GhostState.STILL;
        sprite = ResourcesLoader.loadDrawableIgnoreErrors("ghost"+id+".png");
        width = sprite.getWidth(null);
        height = sprite.getHeight(null);
    }

}
