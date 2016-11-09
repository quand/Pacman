package pacman.world.models;

import pacman.core.models.MoveableObject;
import pacman.utils.ResourcesLoader;

import java.awt.*;

public class Pacman extends MoveableObject {


    public int score;

    public Pacman() {
        sprite = ResourcesLoader.loadDrawableIgnoreErrors("Pacman state1 right.png");
        width = sprite.getWidth(null);
        height = sprite.getHeight(null);
    }
    public void setImage(Direction direction){
        switch (direction){
            case DOWN:
                sprite = ResourcesLoader.loadDrawableIgnoreErrors("Pacman state1 down.png");
                break;
            case TOP:
                sprite = ResourcesLoader.loadDrawableIgnoreErrors("Pacman state1 up.png");
                break;
            case RIGHT:
                sprite = ResourcesLoader.loadDrawableIgnoreErrors("Pacman state1 right.png");
                break;
            case LEFT:
                sprite = ResourcesLoader.loadDrawableIgnoreErrors("Pacman state1 left.png");
                break;
        }
    }

    public int getScore(){
        return score;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(sprite, x, y, null);
    }

}
