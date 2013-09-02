package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.util.Quaternion;

public enum QuaternionConstants implements Constant<Quaternion> {

    ZERO {
        public Quaternion get() {
            return Quaternion.ZERO;
        }
        public String toString() {
            return "0";
        }
    },
    ONE {
        public Quaternion get() {
            return Quaternion.ONE;
        }
        public String toString() {
            return "1";
        }
    },
    I {
        public Quaternion get() {
            return Quaternion.I;
        }
        public String toString() {
            return "i";
        }
    },
    J {
        public Quaternion get() {
            return Quaternion.J;
        }
        public String toString() {
            return "j";
        }
    },
    K {
        public Quaternion get() {
            return Quaternion.K;
        }
        public String toString() {
            return "k";
        }
    }

}
