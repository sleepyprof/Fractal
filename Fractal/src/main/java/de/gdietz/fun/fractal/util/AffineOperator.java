package de.gdietz.fun.fractal.util;

public class AffineOperator<T extends Tuple<T>, O extends LinearOperator<T, O>> implements Operator<T, AffineOperator<T, O>> {

    private final O op;
    private final T trans;

    public AffineOperator(O op, T trans) {
        this.op = op;
        this.trans = trans;
    }

    public T operate(T x) {
        return op.operate(x).add(trans);
    }

    public AffineOperator<T, O> compose(AffineOperator<T, O> aff) {
        return new AffineOperator<T, O>(op.compose(aff.op), op.operate(aff.trans).add(trans));
    }

    public AffineOperator<T, O> add(AffineOperator<T, O> aff) {
        return new AffineOperator<T, O>(op.add(aff.op), trans.add(aff.trans));
    }

    public AffineOperator<T, O> multiply(double r) {
        return new AffineOperator<T, O>(op.multiply(r), trans.multiply(r));
    }

    public AffineOperator<T, O> inverse() throws NonInvertibleOperatorException {
        O inv = op.inverse();
        return new AffineOperator<T, O>(inv, inv.operate(trans).negate());
    }

}
