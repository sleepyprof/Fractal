package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.util.Quaternion;

public enum QuaternionVariables implements Variable<Quaternion> {

    Z {
        public Quaternion get(Quaternion z, Quaternion c, Quaternion p) {
            return z;
        }
        public String toString() {
            return "z";
        }
    },
    C {
        public Quaternion get(Quaternion z, Quaternion c, Quaternion p) {
            return c;
        }
        public String toString() {
            return "c";
        }
    },
    P {
        public Quaternion get(Quaternion z, Quaternion c, Quaternion p) {
            return p;
        }
        public String toString() {
            return "p";
        }
    }

}