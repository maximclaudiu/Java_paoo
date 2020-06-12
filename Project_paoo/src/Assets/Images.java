package Assets;

import java.awt.image.BufferedImage;

public class Images {

    public static BufferedImage tile_T1;
    public static BufferedImage tile_T2;
    public static BufferedImage tile_T3;
    public static BufferedImage player;
    public static BufferedImage Ball;
    // Dimensiunile sub-imaginilor din assets.png
    public static final int    tileWidth   = 100;
    public static final int    tileHeight  = 50;
    public static final int    playerWidth   = 150;
    public static final int    playerHeight  = 22;
    public static final int    BallWidth  = 16;
    public static final int    BallHeight  = 16;
    public static void Init() {
        // Imparte asset-urile din assets.png in poze ce vor reprezenta texturile actorilor (Player, Tile, Ball)
        SpriteSheet sprite = new SpriteSheet(ImageLoader.LoadImage("/textures/assets.png"));
        tile_T1 = sprite.crop(0, 0,tileWidth,tileHeight);
        tile_T2 = sprite.crop(1, 0,tileWidth,tileHeight);
        tile_T3 = sprite.crop(2, 0,tileWidth,tileHeight);
        player =sprite.crop(0,2.3,playerWidth,playerHeight);
        Ball =sprite.crop(9.4,3.15,BallWidth,BallHeight);
    }
}