package de.gdietz.fun.fractal.view;

import de.gdietz.fun.fractal.controller.Fractal2DMouseController;
import de.gdietz.fun.fractal.controller.FractalAnimPairedController;
import de.gdietz.fun.fractal.palette.Palette;
import de.gdietz.fun.fractal.util.Tuple;
import de.gdietz.gui.swing.MouseDraggingJLabel;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public abstract class FractalAnimPictureViewBase<T extends Tuple<T>> extends MouseDraggingJLabel implements FractalView, PaletteView, AnimationView, AnimationListener, SaveableView, PictureCoordMapper<T> {

	protected final FractalAnimPicture<T> picture;
	protected final FractalAnimPairedController<T> controller;

    private boolean animation;
    private Thread updateThread;

    private int frame = 0;
    private int waitCounter = 0;
    private int direction = 1;

    private List<AnimationListener> listeners;


    private class UpdateThread extends Thread {

        public UpdateThread() {
            super("FractalAnimPictureViewThread");
        }

		public void run() {
            animation = true;
			while (animation) {
				setNextFrame();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// no action required
				}
			}
		}

	}


    public FractalAnimPictureViewBase(FractalAnimPicture<T> picture, FractalAnimPairedController<T> controller) {
        listeners = new ArrayList<AnimationListener>();

        this.picture = picture;
        this.controller = controller;
        picture.addObserver(this);
        update();

        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.TOP);

        Fractal2DMouseController<T> mouseController = new Fractal2DMouseController<T>(controller, this, this);
		addMouseListener(mouseController);
		addMouseMotionListener(mouseController);
    }

    public void addAnimationListener(AnimationListener listener) {
        listeners.add(listener);
    }

    public abstract void update(int currentFrame, int frames);

    private void updateAllListeners() {
        int frames = picture.getFrames();
        if (frame >= frames)
            frame = frames - 1;
        if (frame < 0)
            frame = 0;

        update(frame, frames);
        for(AnimationListener listener : listeners)
            listener.update(frame, frames);
    }

    public synchronized void update() {
        updateAllListeners();
    }

    public void update(Observable o, Object arg) {
        update();
    }

    public synchronized void setCurrentFrame(int frame) {
        this.frame = frame;
        updateAllListeners();
    }

    protected synchronized void setNextFrame() {
        if (waitCounter > 0)
            waitCounter--;
        else {
            frame += direction;
            if (frame <= 0 || frame >= picture.getFrames() - 1) {
                direction = -direction;
                waitCounter = 20;
            }
        }
        updateAllListeners();
    }

    public void writeTo(File file) throws IOException {
        picture.writeTo(file);
    }

    public String getExtension() {
        return picture.getExtension();
    }

    public void setPalette(Palette palette) {
        picture.setPalette(palette);
        update();
    }

    public Palette getPalette() {
        return picture.getPalette();
    }

    public abstract T getCoordinate(int x, int y);

    public void startAnimation() {
        if (animation)
            stopAnimation();
        updateThread = new UpdateThread();
        updateThread.start();
    }

    public void stopAnimation() {
        animation = false;
        if (updateThread != null) {
            try {
                updateThread.join();
            } catch (InterruptedException e) {
                // no action required
            }
        }
        updateThread = null;
    }

    protected void finalize() throws Throwable {
        stopAnimation();
        super.finalize();
    }

}




