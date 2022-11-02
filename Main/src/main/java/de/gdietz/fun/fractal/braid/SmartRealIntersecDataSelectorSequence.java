package de.gdietz.fun.fractal.braid;

public class SmartRealIntersecDataSelectorSequence implements SmartSelectorSequence<IntersecData<Double>> {

    public static enum Type {
        SMART, XLY1, YLX1, XLU1, ULX1, YLU1, ULY1
    }

    private final Type type;

    public SmartRealIntersecDataSelectorSequence(Type type) {
        this.type = type;
    }

    public SmartRealIntersecDataSelectorSequence() {
        this(Type.SMART);
    }

    public int get(RealIntersecData hint) {
        switch (type) {
            case XLY1:
                if (Math.abs(hint.getS12()) < Math.abs(hint.getS13()))
                    return 1;
                return 2;
            case YLX1:
                if (Math.abs(hint.getS13()) < Math.abs(hint.getS12()))
                    return 1;
                return 2;
            case XLU1:
                if (Math.abs(hint.getS12()) < Math.abs(hint.getS23()))
                    return 1;
                return 2;
            case SMART:
            case ULX1:
                if (Math.abs(hint.getS23()) < Math.abs(hint.getS12()))
                    return 1;
                return 2;
            case YLU1:
                if (Math.abs(hint.getS13()) < Math.abs(hint.getS23()))
                    return 1;
                return 2;
            case ULY1:
                if (Math.abs(hint.getS23()) < Math.abs(hint.getS13()))
                    return 1;
                return 2;
            default:
                throw new AssertionError("Unknown type.");
        }
    }

    public int get(int index, IntersecData<Double> hint) {
        return get((RealIntersecData) hint);
    }

    public String toString() {
        return type.name().toLowerCase();
    }

}