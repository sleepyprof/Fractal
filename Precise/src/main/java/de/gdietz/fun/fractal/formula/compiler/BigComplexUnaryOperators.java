package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.util.BigComplex;

public enum BigComplexUnaryOperators implements UnaryOperator<BigComplex> {

    NEGATE {
        public BigComplex operate(BigComplex x) {
            return x.negate();
        }
        public String toString() {
            return "neg";
        }
    },
    SQR {
        public BigComplex operate(BigComplex x) {
            return x.sqr();
        }
        public String toString() {
            return "sqr";
        }
    },
    CUBE {
        public BigComplex operate(BigComplex x) {
            return x.cube();
        }
        public String toString() {
            return "cube";
        }
    },
    INVERSE {
        public BigComplex operate(BigComplex x) {
            return x.inverse();
        }
        public String toString() {
            return "inv";
        }
    },
    CONJUGATE {
        public BigComplex operate(BigComplex x) {
            return x.conjugate();
        }
        public String toString() {
            return "conj";
        }
    }

}
