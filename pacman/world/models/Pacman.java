package pacman.world.models;

import pacman.core.models.MoveableObject;
import pacman.utils.ResourcesLoader;

import java.awt.*;

public class Pacman extends MoveableObject {

    public int currentRow;
    public int currentColumn;
    public int nextRow;
    public int nextColumn;

    private Image sprite;

    public int getCenterX() {
        return x + width / 2;
    }

    public Pacman() {
        sprite = ResourcesLoader.loadDrawableIgnoreErrors("Pacman state1 right.png");
        width = sprite.getWidth(null);
        height = sprite.getHeight(null);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(sprite, x, y, null);
    }

}
