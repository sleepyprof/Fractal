package de.gdietz.imageio;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PNGWriter {

    public static final String EXTENSION = "png";

    private final RenderedImage image;
    private final File file;

    private final Map<String, String> metadata;

    public PNGWriter(RenderedImage image, File file) {
        this.image = image;
        this.file = file;

        metadata = new HashMap<>();
    }

    public void addMetadata(String keyword, String text) {
        metadata.put(keyword, text);
    }

    public void addMetadata(Map<String, String> metadata) {
        for(String key : metadata.keySet())
            this.metadata.put(key, metadata.get(key));
    }

    public void write() throws IOException {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(EXTENSION);
        ImageWriter writer = writers.next();

        ImageWriteParam param = writer.getDefaultWriteParam();

        IIOMetadata metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), param);

        if (metadata instanceof com.sun.imageio.plugins.png.PNGMetadata) {
            com.sun.imageio.plugins.png.PNGMetadata pngMetadata = (com.sun.imageio.plugins.png.PNGMetadata) metadata;

            ArrayList<String> keyword = pngMetadata.tEXt_keyword;
            ArrayList<String> text = pngMetadata.tEXt_text;

            for(String key : this.metadata.keySet()) {
                if (keyword == null)
                    keyword = new ArrayList<>();
                if (text == null)
                    text = new ArrayList<>();

                keyword.add(key);
                text.add(this.metadata.get(key));
            }

            pngMetadata.tEXt_keyword = keyword;
            pngMetadata.tEXt_text = text;
        }

        IIOImage iioi = new IIOImage(image, null, metadata);

        ImageOutputStream ios = ImageIO.createImageOutputStream(file);
        writer.setOutput(ios);

        writer.write(iioi);
    }

}
