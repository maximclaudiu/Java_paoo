package Assets;

import java.awt.image.BufferedImage;

public class SpriteSheet
{
    private BufferedImage spriteSheet;

    public SpriteSheet(BufferedImage buffImg)
    {
        spriteSheet = buffImg;
    }

    public BufferedImage crop(double x, double y, int width, int height)
    {
         return spriteSheet.getSubimage((int)(x * width), (int)(y * height), width, height);
    }



}

