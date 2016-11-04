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
    public EnemyState state = EnemyState.NOTHING;
    protected Image sprite;
    //protected SpriteSheetAnimator livingAnimator;
	// =============================================================================================
    // CONSTRUCTOR
    // =============================================================================================
    public Wall(int row, int column){
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
