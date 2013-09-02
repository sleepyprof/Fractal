package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.util.Complex;

public enum ComplexVariables implements Variable<Complex> {

    Z {
        public Complex get(Complex z, Complex c, Complex p) {
            return z;
        }
        public String toString() {
            return "z";
        }
    },
    C {
        public Complex get(Complex z, Complex c, Complex p) {
            return c;
        }
        public String toString() {
            return "c";
        }
    },
    P {
        public Complex get(Complex z, Complex c, Complex p) {
            return p;
        }
        public String toString() {
            return "p";
        }
    }

}