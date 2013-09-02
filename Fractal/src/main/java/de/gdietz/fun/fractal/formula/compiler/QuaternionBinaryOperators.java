package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.util.Quaternion;

public enum QuaternionBinaryOperators implements BinaryOperator<Quaternion> {

    ADD {
        public Quaternion compose(Quaternion x, Quaternion y) {
            return x.add(y);
        }
        public String toString() {
            return "+";
        }
    },
    SUBTRACT {
        public Quaternion compose(Quaternion x, Quaternion y) {
            return x.subtract(y);
        }
        public String toString() {
            return "-";
        }
    },
    MULTIPLY {
        public Quaternion compose(Quaternion x, Quaternion y) {
            return x.multiply(y);
        }
        public String toString() {
            return "*";
        }
    },
    DIVIDE {
        public Quaternion compose(Quaternion x, Quaternion y) {
            return x.divide(y);
        }
        public String toString() {
            return "/";
        }
    }

}
