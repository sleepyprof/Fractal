package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.util.Complex;

public enum ComplexBinaryOperators implements BinaryOperator<Complex> {

    ADD {
        public Complex compose(Complex x, Complex y) {
            return x.add(y);
        }
        public String toString() {
            return "+";
        }
    },
    SUBTRACT {
        public Complex compose(Complex x, Complex y) {
            return x.subtract(y);
        }
        public String toString() {
            return "-";
        }
    },
    MULTIPLY {
        public Complex compose(Complex x, Complex y) {
            return x.multiply(y);
        }
        public String toString() {
            return "*";
        }
    },
    DIVIDE {
        public Complex compose(Complex x, Complex y) {
            return x.divide(y);
        }
        public String toString() {
            return "/";
        }
    },
    POW {
        public Complex compose(Complex x, Complex y) {
            return x.pow(y);
        }
        public String toString() {
            return "^";
        }
    }

}
