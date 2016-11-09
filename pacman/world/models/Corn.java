package pacman.world.models;

import pacman.utils.ResourcesLoader;

import java.awt.*;

public class Corn extends Wall {
    public Corn(int row, int column){
        super(row,column);
        state = ObjectState.CORN;
        sprite = ResourcesLoader.loadDrawableIgnoreErrors("Corn_small.png");
        width = sprite.getWidth(null);
        height = sprite.getHeight(null);
    }

    @Override
    public void draw(Graphics g) {
        switch (state) {
            case CORN:
                g.drawImage(sprite, x, y, null);
        }
    }
}
