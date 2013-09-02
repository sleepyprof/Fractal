package de.gdietz.fun.fractal.model.util;

import de.gdietz.fun.fractal.view.CalculationListener;

public class MultithreadFractalThread extends Thread {

    private final int width;
    private final int height;
    
    private final MultithreadFractalWork work;
    private final boolean endless;

    private final CalculationListener listener;
    
    private final int parallelThreads;

    private PartThread[] partThreads;

    private boolean stopNow = false;

    private boolean invalid;
    private final Object mutexInvalid = new Object();

    private boolean calculating = false;
    private final Object mutexCalculating = new Object();

    private PartListener[] partListeners;
    private final Object mutexListener = new Object();
    
    public MultithreadFractalThread(int width, int height, MultithreadFractalWork work, boolean endless, CalculationListener listener, int parallelThreads) {
        super("FractalWorkerThread");

        this.width = width;
        this.height = height;
        this.work = work;
        this.endless = endless;
        this.listener = listener;
        this.parallelThreads = parallelThreads;

        partThreads = new PartThread[parallelThreads];
        
        partListeners = new PartListener[parallelThreads];
        for (int i = 0; i < parallelThreads; i++)
            partListeners[i] = new PartListener();
    }

    public MultithreadFractalThread(int width, int height, MultithreadFractalWork work, boolean endless, CalculationListener listener) {
        this(width, height, work, endless, listener, 2 * Runtime.getRuntime().availableProcessors());
    }

    public void stopNow() {
        stopNow = true;
    }

    private class PartListener implements CalculationListener {

        private int progress;
        private int total;

        private PartListener() {
            setCalculating(false);
        }

        public void setCalculating(boolean calculating) {
            progress = 0;
            total = 0;
        }

        public void setProgress(int progress, int total) {
            this.progress = progress;
            this.total = total;

            if (!endless) {
                int progressSum = 0;
                int totalSum = 0;
                synchronized (mutexListener) {
                    for (PartListener partListener : partListeners) {
                        progressSum += partListener.progress;
                        totalSum += partListener.total;
                    }
                }

                listener.setProgress(progressSum, totalSum != 0 ? totalSum : 1);
            }
        }

    }

    private class PartThread extends Thread {

        private int fromX;
        private int fromY;
        private int toX;
        private int toY;

        private PartListener partListener;

        private PartThread(int fromX, int fromY, int toX, int toY, PartListener partListener) {
            super("FractalWorkerPartThread");
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
            this.partListener = partListener;
        }

        public void run() {
            boolean localInvalid = true;
            for (int y = fromY; y <= toY; y++) {
                partListener.setProgress(y - fromY, toY - fromY + 1);
                for (int x = fromX; x <= toX; x++) {
                    if (stopNow)
                        return;
                    boolean valid = work.calculate(x, y);
                    localInvalid = localInvalid && !valid;
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    // no action required
                }
            }
            synchronized (mutexInvalid) {
                invalid = invalid && localInvalid;
            }
        }

    }

    private void startParts() {
        for (int t = 0; t < parallelThreads; t++) {
            partThreads[t] = new PartThread(0,
                    height * t / parallelThreads, width - 1, height * (t + 1) / parallelThreads - 1,
                    partListeners[t]);
            partThreads[t].start();
        }
    }

    private void joinParts() {
        for (int t = 0; t < parallelThreads; t++) {
            try {
                partThreads[t].join();
                partThreads[t] = null;
            } catch (InterruptedException e) {
                // no action required
            }
        }
    }

    public void run() {
        synchronized (mutexCalculating) {
            calculating = true;
        }
        listener.setCalculating(true);

        boolean finished = false;
        while(!finished) {
            invalid = true;
            startParts();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // no action required
            }
            joinParts();
            finished = invalid || stopNow;

            if (!finished)
                work.notifyViews();
        }

        synchronized (mutexCalculating) {
            calculating = false;
        }
        listener.setCalculating(false);

        work.notifyViews();
    }

    public boolean isCalculating() {
        synchronized (mutexCalculating) {
            return calculating;
        }
    }

}
