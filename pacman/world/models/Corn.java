package pacman.world.models;

import pacman.utils.ResourcesLoader;

public class Corn extends Wall {
    public Corn(int row, int column){
        super(row,column);
        this.state = EnemyState.CORN;
        sprite = ResourcesLoader.loadDrawableIgnoreErrors("Corn.png");
        width = sprite.getWidth(null);
        height = sprite.getHeight(null);
    }

}
