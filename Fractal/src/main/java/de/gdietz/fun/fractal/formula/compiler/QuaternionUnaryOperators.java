package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.util.Quaternion;

public enum QuaternionUnaryOperators implements UnaryOperator<Quaternion> {

    NEGATE {
        public Quaternion operate(Quaternion x) {
            return x.negate();
        }
        public String toString() {
            return "neg";
        }
    },
    SQR {
        public Quaternion operate(Quaternion x) {
            return x.sqr();
        }
        public String toString() {
            return "sqr";
        }
    },
    CUBE {
        public Quaternion operate(Quaternion x) {
            return x.cube();
        }
        public String toString() {
            return "cube";
        }
    },
    INVERSE {
        public Quaternion operate(Quaternion x) {
            return x.inverse();
        }
        public String toString() {
            return "inv";
        }
    },
    CONJUGATE {
        public Quaternion operate(Quaternion x) {
            return x.conjugate();
        }
        public String toString() {
            return "conj";
        }
    }

}
