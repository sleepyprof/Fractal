package de.gdietz.imageio;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataFormatImpl;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
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

        if (this.metadata.size() > 0) {
            IIOMetadataNode tEXt_parent = new IIOMetadataNode("tEXt");
            for (String key : this.metadata.keySet()) {
                IIOMetadataNode tEXt_node = new IIOMetadataNode("tEXtEntry");
                tEXt_node.setAttribute("keyword", key);
                tEXt_node.setAttribute("value", this.metadata.get(key));
                tEXt_parent.appendChild(tEXt_node);
            }
            IIOMetadataNode root = new IIOMetadataNode(metadata.getNativeMetadataFormatName());
            root.appendChild(tEXt_parent);
            metadata.mergeTree(IIOMetadataFormatImpl.standardMetadataFormatName, root);
        }

        IIOImage iioi = new IIOImage(image, null, metadata);

        ImageOutputStream ios = ImageIO.createImageOutputStream(file);
        writer.setOutput(ios);

        writer.write(iioi);
    }

}
