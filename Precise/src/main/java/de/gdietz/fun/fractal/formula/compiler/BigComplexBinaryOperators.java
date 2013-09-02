package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.util.BigComplex;

public enum BigComplexBinaryOperators implements BinaryOperator<BigComplex> {

    ADD {
        public BigComplex compose(BigComplex x, BigComplex y) {
            return x.add(y);
        }
        public String toString() {
            return "+";
        }
    },
    SUBTRACT {
        public BigComplex compose(BigComplex x, BigComplex y) {
            return x.subtract(y);
        }
        public String toString() {
            return "-";
        }
    },
    MULTIPLY {
        public BigComplex compose(BigComplex x, BigComplex y) {
            return x.multiply(y);
        }
        public String toString() {
            return "*";
        }
    }

}
