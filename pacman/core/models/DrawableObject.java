package pacman.core.models;



import java.awt.Graphics;

public abstract class DrawableObject {
    public int x;
    public int y;
    public int width;
    public int height;


    public abstract void draw(Graphics g);

	//проверка колизий(объекты столкнулись)
    public boolean isIntersects(DrawableObject target) {
        return (x + width) > target.x && x < (target.x + target.width) &&
                (y + height) > target.y && y < (target.y + target.height);
    }
}
