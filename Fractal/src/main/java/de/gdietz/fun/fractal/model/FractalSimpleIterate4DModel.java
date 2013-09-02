package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.meta.FractalMetadata;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.formula.FractalIterator;

public class FractalSimpleIterate4DModel extends FractalIterateModelAdapter<Integer> {

    private final FractalSimple4DBuffer buffer;

	private final boolean julia;

	private boolean calculating;
	private FractalThread thread;

	private class FractalThread extends Thread {

		public boolean stopNow = false;

        public FractalThread() {
            super("Fractal4DThread");
        }

        private void doCalculate(int x, int yi, int yj, int yk) {
            Coordinate c = buffer.getCoordinate(x, yi, yj, yk);
            Coordinate p = buffer.getParameter(x, yi, yj, yk);
            int index = julia ? calculate(p, c) : calculate(c, p);
            buffer.setData(x, yi, yj, yk, index);
        }

        public void run() {
			calculating = true;
            setCalculating(true);
            for (int y = 0; y < getHeight(); y++) {
				for (int x = 0; x < getWidth(); x++) {
                    buffer.setData(x, y, -1);
				}
			}
            int lx = buffer.getLengthX();
            int lyi = buffer.getLengthYi();
            int lyj = buffer.getLengthYj();
            int lyk = buffer.getLengthYk();
            for (int yi = 0; yi < lyi; yi++) {
				for (int x = 0; x < lx; x++) {
					if (stopNow) {
						calculating = false;
                        setCalculating(false);
						return;
					}
                    doCalculate(x, yi, 0, 0);
				}
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    // no action required
                }
			}
            for (int yj = 0; yj < lyj; yj++) {
				for (int x = 0; x < lx; x++) {
					if (stopNow) {
						calculating = false;
                        setCalculating(false);
						return;
					}
                    doCalculate(x, lyi - 1, yj, 0);
				}
			}
            for (int yk = 0; yk < lyk; yk++) {
				for (int x = 0; x < lx; x++) {
					if (stopNow) {
						calculating = false;
                        setCalculating(false);
						return;
					}
                    doCalculate(x, lyi - 1, lyj - 1, yk);
				}
			}
            for (int yj = 0; yj < lyj; yj++) {
				for (int yi = 0; yi < lyi; yi++) {
					if (stopNow) {
						calculating = false;
                        setCalculating(false);
						return;
					}
                    doCalculate(lx - 1, yi, yj, 0);
				}
			}
            for (int yk = 0; yk < lyk; yk++) {
				for (int yi = 0; yi < lyi; yi++) {
					if (stopNow) {
						calculating = false;
                        setCalculating(false);
						return;
					}
                    doCalculate(0, yi, 0, yk);
				}
			}
            for (int yk = 0; yk < lyk; yk++) {
				for (int yj = 0; yj < lyj; yj++) {
					if (stopNow) {
						calculating = false;
                        setCalculating(false);
						return;
					}
                    doCalculate(0, lyi - 1, yj, yk);
				}
			}
            for (int yk = 0; yk < lyk - 1; yk++) {
				for (int yj = 0; yj < lyj - 1; yj++) {
					if (stopNow) {
						calculating = false;
                        setCalculating(false);
						return;
					}
                    int x = 0;
                    int yi = lyi - 1;
                    Coordinate c1 = buffer.getCoordinate(x, yi, yj, yk);
                    Coordinate p1 = buffer.getParameter(x, yi, yj, yk);
                    Coordinate c2 = buffer.getCoordinate(x, yi, yj + 1, yk + 1);
                    Coordinate p2 = buffer.getParameter(x, yi, yj + 1, yk + 1);
                    Coordinate c = c1.add(c2).multiply(0.5);
                    Coordinate p = p1.add(p2).multiply(0.5);
                    int index = julia ? calculate(p, c) : calculate(c, p);
                    buffer.setData(buffer.getX(x, yi, yj, yk), buffer.getY(x, yi, yj, yk) - 1, index);
				}
			}
            for(int x = 0; x < lx; x++) {
                buffer.setData(x, lyi - 1, 0, 0, -1);
                buffer.setData(x, lyi - 1, lyj - 1, 0, -1);
            }
            for(int yi = 0; yi < lyi; yi++) {
                buffer.setData(0, yi, 0, 0, -1);
                buffer.setData(lx - 1, yi, 0, 0, -1);
            }
            for(int yj = 0; yj < lyj; yj++) {
                buffer.setData(0, lyi - 1, yj, 0, -1);
                buffer.setData(lx - 1, lyi - 1, yj, 0, -1);
            }
            for(int yk = 0; yk < lyk; yk++) {
                buffer.setData(0, lyi - 1, 0, yk, -1);
                buffer.setData(0, lyi - 1, lyj - 1, yk, -1);
            }
            calculating = false;
            setCalculating(false);
			setChanged();
			notifyObservers();
		}

	}

	private FractalSimpleIterate4DModel(FractalSimple4DBuffer buffer, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, boolean julia) {
		super(buffer, iteratorFactory, maxiter);
		this.buffer = buffer;
		this.julia = julia;
	}

	public FractalSimpleIterate4DModel(int width, int height, Coordinate from, Coordinate to, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, boolean julia) {
		this(new FractalSimple4DBuffer(width, height, from, to), iteratorFactory, maxiter, julia);
	}

	public FractalSimpleIterate4DModel(int width, int height, Coordinate from, Coordinate to, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
		this(width, height, from, to, iteratorFactory, maxiter, false);
	}

    public boolean isJulia() {
        return julia;
    }

    public int calculate(Coordinate c, Coordinate p) {
		FractalIterator<Coordinate> iterator = getIteratorFactory().get(c, p);
		if (iterator.isSurvivor())
			return -1;
		int iter = 0;
		while(iterator.isValid() && iter < getMaxiter()) {
			iterator.iterate();
			iter++;
		}
		if (iterator.isValid())
			return -1;
		return iter;
	}

	protected void stopCalculationSilent() {
		if (thread != null) {
			thread.stopNow = true;
			try {
				thread.join();
			} catch (InterruptedException e) {
				// no action required
			}
		}
		thread = null;
	}

	protected void startCalculationSilent() {
        calculating = true;
        thread = new FractalThread();
		thread.start();
	}

	public boolean isCalculating() {
		return calculating;
	}

    protected void finalize() throws Throwable {
        stopCalculationSilent();
        super.finalize();
    }

    public FractalMetadata getMetadata() {
        FractalMetadata metadata = new FractalMetadata();
        metadata.setType(isJulia() ? "Julia Set 4D" : "Mandelbrot Set 4D");
        metadata.setIterationType(false);
        metadata.setIteratorFactory(getIteratorFactory());
        metadata.setMaxiter(getMaxiter());
        metadata.setSize(getWidth(), getHeight());
        metadata.setCorners(getCornerFrom(), getCornerTo());
        metadata.setParameter(getParameter());
        return metadata;
    }

}