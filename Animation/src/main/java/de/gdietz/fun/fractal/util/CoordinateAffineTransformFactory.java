package de.gdietz.fun.fractal.util;

public class CoordinateAffineTransformFactory implements AffineTransformFactory<Coordinate, Matrix> {

    public AffineOperator<Coordinate, Matrix> get(Coordinate from, Coordinate to) {
        Coordinate diff = to.subtract(from);
        Matrix m = new Matrix(diff.x, 0.0, 0.0, diff.y);
        return new AffineOperator<Coordinate, Matrix>(m, from);
    }

    public AffineOperator<Coordinate, Matrix> get(Coordinate oldFrom, Coordinate oldTo, Coordinate newFrom, Coordinate newTo) {
        try {
            return get(newFrom, newTo).compose(get(oldFrom, oldTo).inverse());
        } catch (NonInvertibleOperatorException e) {
            Coordinate oldToMod = oldTo.add(oldTo.x == oldFrom.x ? new Coordinate(1.0, 0.0) : Coordinate.ORIGIN)
                    .add(oldTo.y == oldFrom.y ? new Coordinate(0.0, 1.0) : Coordinate.ORIGIN);
            return get(newFrom, newTo).compose(get(oldFrom, oldToMod));
        }
    }

}
