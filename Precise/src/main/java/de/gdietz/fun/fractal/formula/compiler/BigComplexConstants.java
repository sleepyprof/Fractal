package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.util.BigComplex;

public enum BigComplexConstants implements Constant<BigComplex> {

    ZERO {
        public BigComplex get() {
            return BigComplex.ZERO;
        }
        public String toString() {
            return "0";
        }
    },
    ONE {
        public BigComplex get() {
            return BigComplex.ONE;
        }
        public String toString() {
            return "1";
        }
    },
    I {
        public BigComplex get() {
            return BigComplex.I;
        }
        public String toString() {
            return "i";
        }
    }

}
