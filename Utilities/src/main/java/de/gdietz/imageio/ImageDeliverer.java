package de.gdietz.imageio;

import java.awt.image.BufferedImage;

public interface ImageDeliverer {

    public int getFrames();
    public BufferedImage getImage(int frame);

}
