package de.gdietz.imageio;

import org.apache.log4j.Logger;
import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.VideoFormatKeys;
import org.monte.media.math.Rational;
import org.monte.media.quicktime.QuickTimeWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

public class MovWriter {

    private final ImageDeliverer deliverer;
    private final File file;
    private final Component parent;

    private static Logger log = Logger.getLogger(MovWriter.class);

    private interface SaveTaskInterface {
        public boolean cancel(boolean mayInterruptIfRunning);
    }

    private class SaveTask extends SwingWorker<Void, Void> implements SaveTaskInterface {

        private void writeTo(File file) throws IOException {
            int frames = deliverer.getFrames();
            if (frames == 0)
                return;

            BufferedImage image = deliverer.getImage(0);
            int width = image.getWidth();
            int height = image.getHeight();

            QuickTimeWriter out = null;
            try {
                out = new QuickTimeWriter(file);
                Format format = new Format(VideoFormatKeys.EncodingKey, VideoFormatKeys.ENCODING_QUICKTIME_PNG, VideoFormatKeys.DepthKey, 24);
                format = format.prepend(VideoFormatKeys.MediaTypeKey, FormatKeys.MediaType.VIDEO, VideoFormatKeys.FrameRateKey, new Rational(10, 1),
                        VideoFormatKeys.WidthKey, width, VideoFormatKeys.HeightKey, height);
                out.addTrack(format);
                out.setVideoColorTable(0, image.getColorModel());

                for (int frame = 0; frame < frames - 1; frame++) {
                    setFramesProgress(frame);
                    if (isCancelled()) {
                        out.close();
                        return;
                    }
                    image = deliverer.getImage(frame);
                    out.write(0, image, 1);
                }

                setFramesProgress(frames - 1);
                if (isCancelled()) {
                    out.close();
                    return;
                }
                image = deliverer.getImage(frames - 1);
                out.write(0, image, 20);

                for (int frame = frames - 2; frame > 0; frame--) {
                    setFramesProgress(2 * frames - frame - 2);
                    if (isCancelled()) {
                        out.close();
                        return;
                    }
                    image = deliverer.getImage(frame);
                    out.write(0, image, 1);
                }

                setFramesProgress(2 * frames - 2);
                if (isCancelled()) {
                    out.close();
                    return;
                }
                image = deliverer.getImage(0);
                out.write(0, image, 20);

                setFramesProgress(2 * frames - 1);
            } finally {
                if (out != null)
                    out.close();
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

        private File file;

        private SaveTask(File file) {
            this.file = file;
        }

        protected Void doInBackground() throws Exception {
            try{
                writeTo(file);
            } catch (Exception e) {
                log.error("Could not write to file", e);
            }
            return null;
        }

    }

    private static class SaveProgressMonitor extends ProgressMonitor implements PropertyChangeListener {

        private SaveTaskInterface task;

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

    public MovWriter(ImageDeliverer deliverer, File file, Component parent) {
        this.deliverer = deliverer;
        this.file = file;
        this.parent = parent;
    }

    public void write() throws IOException {
        SaveTask task = new SaveTask(file);
        SaveProgressMonitor progressMonitor = new SaveProgressMonitor(parent, task);
        task.addPropertyChangeListener(progressMonitor);
        task.execute();
    }

}
