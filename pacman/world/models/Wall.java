package pacman.world.models;

import pacman.core.models.DrawableObject;

import java.awt.*;
//import pacman.world.models.;

public abstract class Wall extends DrawableObject {
	// =============================================================================================
    // FIELDS
    // =============================================================================================
    public final int row;
    public final int column;
    public ObjectState state;
    Image sprite;
    //protected SpriteSheetAnimator livingAnimator;
	// =============================================================================================
    // CONSTRUCTOR
    // =============================================================================================
    Wall(int row, int column){
		this.row = row;
		this.column = column;
	}
	// =============================================================================================
    // METHODS
    // =============================================================================================
    @Override
    public void draw(Graphics g) {
            g.drawImage(sprite, x, y, null);
    }
}
