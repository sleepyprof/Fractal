package de.gdietz.imageio;

import org.monte.media.*;
import org.monte.media.avi.AVIWriter;
import org.monte.media.math.Rational;
import org.monte.media.quicktime.QuickTimeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

public class MovWriter {

    private final ImageDeliverer deliverer;
    private final File file;
    private final Component parent;

    public final static String DEFAULT_EXTENSION = "mov";
    public final static String DEFAULT_ENCODING = VideoFormatKeys.ENCODING_QUICKTIME_PNG;
    public final static String DEFAULT_COMPRESSOR = null;

    private final static Logger log = LoggerFactory.getLogger(MovWriter.class);

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

            MovieWriter out = null;
            try {
                out = Registry.getInstance().getWriter(file);
                Format format = new Format(
                        FormatKeys.MediaTypeKey, FormatKeys.MediaType.VIDEO,
                        FormatKeys.EncodingKey, DEFAULT_ENCODING,
                        FormatKeys.FrameRateKey, new Rational(10, 1),
                        VideoFormatKeys.WidthKey, width,
                        VideoFormatKeys.HeightKey, height,
                        VideoFormatKeys.DepthKey, 24);
                if (DEFAULT_COMPRESSOR != null)
                    format = format.append(VideoFormatKeys.CompressorNameKey, DEFAULT_COMPRESSOR);
                out.addTrack(format);
                setVideoColorTableIfPossible(out, 0, image.getColorModel());

                for (int frame = 0; frame < frames - 1; frame++) {
                    setFramesProgress(frame);
                    if (isCancelled()) {
                        out.close();
                        return;
                    }
                    image = deliverer.getImage(frame);
                    writeImageWithDuration(out, 0, image, 1);
                }

                setFramesProgress(frames - 1);
                if (isCancelled()) {
                    out.close();
                    return;
                }
                image = deliverer.getImage(frames - 1);
                writeImageWithDuration(out, 0, image, 20);

                for (int frame = frames - 2; frame > 0; frame--) {
                    setFramesProgress(2 * frames - frame - 2);
                    if (isCancelled()) {
                        out.close();
                        return;
                    }
                    image = deliverer.getImage(frame);
                    writeImageWithDuration(out, 0, image, 1);
                }

                setFramesProgress(2 * frames - 2);
                if (isCancelled()) {
                    out.close();
                    return;
                }
                image = deliverer.getImage(0);
                writeImageWithDuration(out, 0, image, 20);

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

        private final File file;

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


    private static void setVideoColorTableIfPossible(MovieWriter out, int track, ColorModel colorModel) {
        if (out instanceof QuickTimeWriter)
            ((QuickTimeWriter) out).setVideoColorTable(track, colorModel);
    }

    private static void writeImageWithDuration(MovieWriter out, int track, BufferedImage image, long duration) throws IOException {
        if (out instanceof QuickTimeWriter)
            ((QuickTimeWriter) out).write(track, image, duration);
        else if (out instanceof AVIWriter)
            ((AVIWriter) out).write(track, image, duration);
        else {
            Buffer buf = new Buffer();
            buf.format = new Format(VideoFormatKeys.DataClassKey, BufferedImage.class);
            buf.sampleDuration = out.getFormat(0).get(FormatKeys.FrameRateKey).inverse().multiply(duration);
            buf.data = image;
            out.write(track, buf);
        }
    }

}
