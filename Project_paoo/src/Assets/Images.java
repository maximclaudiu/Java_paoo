package Assets;

import java.awt.image.BufferedImage;

public class Images {

    public static BufferedImage tile_T1;
    public static BufferedImage tile_T2;
    public static BufferedImage tile_T3;
    public static BufferedImage player;
    public static BufferedImage Ball;
    public static final int    tileWidth   = 100;
    public static final int    tileHeight  = 50;
    public static final int    playerWidth   = 150;
    public static final int    playerHeight  = 22;
    public static final int    BallWidth  = 16;
    public static final int    BallHeight  = 16;
    public static void Init() {
        SpriteSheet sprite = new SpriteSheet(ImageLoader.LoadImage("/textures/tiles.png"));
        tile_T1 = sprite.crop(0, 0,tileWidth,tileHeight);
        tile_T2 = sprite.crop(1, 0,tileWidth,tileHeight);
        tile_T3 = sprite.crop(2, 0,tileWidth,tileHeight);
        player =sprite.crop(0,2.3,playerWidth,playerHeight);
        Ball =sprite.crop(9.4,3.15,BallWidth,BallHeight);
    }
}