package de.gdietz.imageio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.*;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GifWriter implements AnimWriter {

    private final ImageDeliverer deliverer;
    private final File file;
    private final Component parent;

    private final Map<String, String> metadata;

    public static final String EXTENSION = "gif";

    private final static Logger log = LoggerFactory.getLogger(GifWriter.class);


    // see https://memorynotfound.com/generate-gif-image-java-delay-infinite-loop-example/
    public static class GifSequenceWriter implements Closeable {

        protected ImageWriter writer;
        protected ImageWriteParam params;
        protected IIOMetadata metadata;

        public GifSequenceWriter(ImageOutputStream out, int imageType, int delay, boolean loop, String comment) throws IOException {
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(EXTENSION);
            writer = writers.next();
            params = writer.getDefaultWriteParam();

            ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(imageType);
            metadata = writer.getDefaultImageMetadata(imageTypeSpecifier, params);

            configureRootMetadata(delay, loop, comment);

            writer.setOutput(out);
            writer.prepareWriteSequence(null);
        }

        private void configureRootMetadata(int delay, boolean loop, String comment) throws IIOInvalidTreeException {
            String metaFormatName = metadata.getNativeMetadataFormatName();
            IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metaFormatName);

            IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
            graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
            graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
            graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
            graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(delay / 10));
            graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");

            if (comment != null) {
                IIOMetadataNode commentsNode = getNode(root, "CommentExtensions");
                commentsNode.setAttribute("CommentExtension", comment);
            }

            IIOMetadataNode appExtensionsNode = getNode(root, "ApplicationExtensions");
            IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
            child.setAttribute("applicationID", "NETSCAPE");
            child.setAttribute("authenticationCode", "2.0");

            int loopContinuously = loop ? 0 : 1;
            child.setUserObject(new byte[]{0x01, (byte) (loopContinuously & 0xFF), (byte) ((loopContinuously >> 8) & 0xFF)});
            appExtensionsNode.appendChild(child);

            metadata.setFromTree(metaFormatName, root);
        }

        private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
            int nNodes = rootNode.getLength();
            for (int i = 0; i < nNodes; i++) {
                if (rootNode.item(i).getNodeName().equalsIgnoreCase(nodeName))
                    return (IIOMetadataNode) rootNode.item(i);
            }
            IIOMetadataNode node = new IIOMetadataNode(nodeName);
            rootNode.appendChild(node);
            return node;
        }

        public void writeToSequence(RenderedImage img) throws IOException {
            writer.writeToSequence(new IIOImage(img, null, metadata), params);
        }

        public void close() throws IOException {
            writer.endWriteSequence();
        }

    }


    private interface SaveTaskInterface {
        public boolean cancel(boolean mayInterruptIfRunning);
    }

    private class SaveTask extends SwingWorker<Void, Void> implements SaveTaskInterface {

        private void writeTo(File file) throws IOException {
            int frames = deliverer.getFrames();
            if (frames == 0)
                return;

            BufferedImage image = deliverer.getImage(0);
            int imageType = image.getType();

            String comment = metadata.get("Description");

            try (ImageOutputStream output = new FileImageOutputStream(file);
                 GifSequenceWriter out =
                         new GifSequenceWriter(output, imageType, 250, true, comment)) {

                for (int frame = 0; frame < frames - 1; frame++) {
                    setFramesProgress(frame);
                    if (isCancelled()) {
                        out.close();
                        output.close();
                        return;
                    }
                    image = deliverer.getImage(frame);
                    writeImageWithDuration(out, image, 1);
                }

                setFramesProgress(frames - 1);
                if (isCancelled()) {
                    out.close();
                    output.close();
                    return;
                }
                image = deliverer.getImage(frames - 1);
                writeImageWithDuration(out, image, 20);

                for (int frame = frames - 2; frame > 0; frame--) {
                    setFramesProgress(2 * frames - frame - 2);
                    if (isCancelled()) {
                        out.close();
                        output.close();
                        return;
                    }
                    image = deliverer.getImage(frame);
                    writeImageWithDuration(out, image, 1);
                }

                setFramesProgress(2 * frames - 2);
                if (isCancelled()) {
                    out.close();
                    output.close();
                    return;
                }
                image = deliverer.getImage(0);
                writeImageWithDuration(out, image, 20);

                setFramesProgress(2 * frames - 1);
            }
        }

        public int getMaximum() {
            return 2 * deliverer.getFrames() - 1;
        }

        private void setFramesProgress(int frame) {
            int max = getMaximum();
            int progress = (max == 0) ? 100 : 100 * frame / max;
            setProgress(progress);
        }

        private final File file;

        private SaveTask(File file) {
            this.file = file;
        }

        protected Void doInBackground() throws Exception {
            try {
                writeTo(file);
            } catch (Exception e) {
                log.error("Could not write to file", e);
            }
            return null;
        }

    }

    private static class SaveProgressMonitor extends ProgressMonitor implements PropertyChangeListener {

        private final SaveTaskInterface task;

        private SaveProgressMonitor(Component parentComponent, SaveTaskInterface task) {
            super(parentComponent, "Saving...", null, 0, 100);
            this.task = task;
        }

        public void propertyChange(PropertyChangeEvent evt) {
            if ("progress".equals(evt.getPropertyName())) {
                int progress = (Integer) evt.getNewValue();
                setProgress(progress);

                if (isCanceled())
                    task.cancel(true);
            }

        }

    }


    public GifWriter(ImageDeliverer deliverer, File file, Component parent) {
        this.deliverer = deliverer;
        this.file = file;
        this.parent = parent;

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
        SaveTask task = new SaveTask(file);
        SaveProgressMonitor progressMonitor = new SaveProgressMonitor(parent, task);
        task.addPropertyChangeListener(progressMonitor);
        task.execute();
    }


    private static void writeImageWithDuration(GifSequenceWriter out, BufferedImage image, long duration) throws IOException {
        // TODO duration?
        out.writeToSequence(image);
    }

}
