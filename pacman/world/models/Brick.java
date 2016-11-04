package pacman.world.models;


import pacman.utils.ResourcesLoader;

public class Brick extends Wall {
	// =============================================================================================
    // CONSTANTS
    // =============================================================================================
    private final static int WIDTH = 50;
    private final static int HEIGHT = 50;
	//private final static SpriteSheet LIFE_SPRITE_SHEET = new SpriteSheet("", WIDTH, HEIGHT);

	// =============================================================================================
    // CONSTRUCTOR
    // =============================================================================================
    public Brick(int row, int column){
		super(row,column);
		this.state = EnemyState.WALL;
		sprite = ResourcesLoader.loadDrawableIgnoreErrors("walltexture.png");
		width = sprite.getWidth(null);
		height = sprite.getHeight(null);
	}
}
