package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.util.BigComplex;

public enum BigComplexVariables implements Variable<BigComplex> {

    Z {
        public BigComplex get(BigComplex z, BigComplex c, BigComplex p) {
            return z;
        }
        public String toString() {
            return "z";
        }
    },
    C {
        public BigComplex get(BigComplex z, BigComplex c, BigComplex p) {
            return c;
        }
        public String toString() {
            return "c";
        }
    },
    P {
        public BigComplex get(BigComplex z, BigComplex c, BigComplex p) {
            return p;
        }
        public String toString() {
            return "p";
        }
    }

}