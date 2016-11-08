package pacman.world.models;

import pacman.utils.ResourcesLoader;

public class Corn extends Wall {
    public Corn(int row, int column){
        super(row,column);
        this.state = ObjectState.CORN;
        sprite = ResourcesLoader.loadDrawableIgnoreErrors("Corn_small.png");
        width = sprite.getWidth(null);
        height = sprite.getHeight(null);
    }

}
