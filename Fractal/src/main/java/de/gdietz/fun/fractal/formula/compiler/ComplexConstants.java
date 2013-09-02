package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.util.Complex;

public enum ComplexConstants implements Constant<Complex> {

    ZERO {
        public Complex get() {
            return Complex.ZERO;
        }
        public String toString() {
            return "0";
        }
    },
    ONE {
        public Complex get() {
            return Complex.ONE;
        }
        public String toString() {
            return "1";
        }
    },
    I {
        public Complex get() {
            return Complex.I;
        }
        public String toString() {
            return "i";
        }
    }

}
