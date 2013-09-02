package de.gdietz.imageio;

//import ch.randelshofer.media.quicktime.QuickTimeOutputStream;

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

    private interface SaveTaskInterface {
        public boolean cancel(boolean mayInterruptIfRunning);
    }

    private class SaveTask extends SwingWorker<Void, Void> implements SaveTaskInterface {

        private void writeTo(File file) throws IOException {
/*
            int frames = deliverer.getFrames();
            if (frames == 0)
                return;

            QuickTimeOutputStream.VideoFormat format = QuickTimeOutputStream.VideoFormat.PNG;
            float quality = 1.0f;
            int timeScale = 10;
            QuickTimeOutputStream out = new QuickTimeOutputStream(file, format);
            out.setVideoCompressionQuality(quality);
            out.setTimeScale(timeScale);

            BufferedImage image;

            for (int frame = 0; frame < frames - 1; frame++) {
                setFramesProgress(frame);
                if (isCancelled()) {
                    out.close();
                    return;
                }
                image = deliverer.getImage(frame);
                out.writeFrame(image, 1);
            }

            setFramesProgress(frames - 1);
            if (isCancelled()) {
                out.close();
                return;
            }
            image = deliverer.getImage(frames - 1);
            out.writeFrame(image, 20);

            for (int frame = frames - 2; frame > 0; frame--) {
                setFramesProgress(2 * frames - frame - 2);
                if (isCancelled()) {
                    out.close();
                    return;
                }
                image = deliverer.getImage(frame);
                out.writeFrame(image, 1);
            }

            setFramesProgress(2 * frames - 2);
            if (isCancelled()) {
                out.close();
                return;
            }
            image = deliverer.getImage(0);
            out.writeFrame(image, 20);

            setFramesProgress(2 * frames - 1);
            out.close();
*/
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
                e.printStackTrace();
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
