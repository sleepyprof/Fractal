package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.formula.FractalIterator;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.fuzzy.Fuzzy;
import de.gdietz.fun.fractal.meta.FractalMetadata;
import de.gdietz.fun.fractal.util.Coordinate;

public class FractalPeriodModel extends FractalSimpleIterateModelAdapter<Integer> implements Fuzzy {

    private double epsilon;
    private double epsilonSqr;

    public FractalPeriodModel(int width, int height, Coordinate from, Coordinate to, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, double epsilon, boolean julia) {
        super(new FractalSimpleBuffer(width, height, from, to), iteratorFactory, maxiter, julia);
        this.epsilon = epsilon;
        this.epsilonSqr = epsilon * epsilon;
    }

    public FractalPeriodModel(int width, int height, Coordinate from, Coordinate to, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, double epsilon) {
        this(width, height, from, to, iteratorFactory, maxiter, epsilon, false);
    }

    public void setLambda(double lambda) {
    }

    public double getLambda() {
        return 0.0;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
        this.epsilonSqr = epsilon * epsilon;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public Integer calculate(Coordinate c, Coordinate p) {
        FractalIterator<Coordinate> iterator = getIteratorFactory().get(c, p);
        if (iterator.isSurvivor())
            return -1;
        int iter = 0;
        while(iterator.isValid() && iter < getMaxiter()) {
            iterator.iterate();
            iter++;
        }

        Coordinate coord = iterator.getCoordinate();

        int period = 0;
        while(iterator.isValid() && period < getMaxiter()) {
            iterator.iterate();
            period++;
            Coordinate coord2 = iterator.getCoordinate();
            if (coord2.subtract(coord).normSqr() < epsilonSqr)
                return period;
        }

        if (iterator.isValid())
            return 0;
        return -1;
    }

    public FractalMetadata getMetadata() {
        FractalMetadata metadata = new FractalMetadata();
        metadata.setType(isJulia() ? "Julia Set Period Analysis" : "Mandelbrot Set Period Analysis");
        metadata.setIterationType(false);
        metadata.setIteratorFactory(getIteratorFactory());
        metadata.setMaxiter(getMaxiter());
        metadata.setSize(getWidth(), getHeight());
        metadata.setCorners(getCornerFrom(), getCornerTo());
        metadata.setParameter(getParameter());
        metadata.setEpsilon(getEpsilon());
        return metadata;
    }

}
