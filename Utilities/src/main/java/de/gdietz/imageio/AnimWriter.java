package de.gdietz.imageio;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface AnimWriter {

    public final static boolean USE_GIF = false;   // TODO not working yet...


    public final static String DEFAULT_EXTENSION = USE_GIF ?
            GifWriter.EXTENSION : MovWriter.DEFAULT_EXTENSION;

    public static AnimWriter create(ImageDeliverer deliverer, File file, Component parent) {
        return USE_GIF ?
                new GifWriter(deliverer, file, parent) :
                new MovWriter(deliverer, file, parent);
    }


    public void addMetadata(String keyword, String text);

    public void addMetadata(Map<String, String> metadata);

    public void write() throws IOException;

}
