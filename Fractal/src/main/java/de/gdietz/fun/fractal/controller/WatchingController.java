package de.gdietz.fun.fractal.controller;

public class WatchingController {

    private final static int DEFAULT_WAIT_MILLIS = 5000;

    private final Notifiable client;
    private final int waitMillis;

	private Thread updateThread;
    private boolean active = true;

    private class UpdateThread extends Thread {

        public UpdateThread() {
            super("WatchingThread");
        }

		public void run() {
			while (active) {
				client.checkAndNotify();
				try {
					Thread.sleep(waitMillis);
				} catch (InterruptedException e) {
					// no action required
				}
			}
		}

	}

	public WatchingController(Notifiable client, int waitMillis) {
		this.client = client;
        this.waitMillis = waitMillis;

		updateThread = new UpdateThread();
		updateThread.start();
	}

    public WatchingController(Notifiable client) {
        this(client, DEFAULT_WAIT_MILLIS);
    }

    protected void finalize() throws Throwable {
        active = false;
        try {
            updateThread.join();
        } catch (InterruptedException e) {
            // no action required
        }
        updateThread = null;
        super.finalize();
    }

}
