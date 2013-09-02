package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.util.Complex;

public enum ComplexUnaryOperators implements UnaryOperator<Complex> {

    NEGATE {
        public Complex operate(Complex x) {
            return x.negate();
        }
        public String toString() {
            return "neg";
        }
    },
    SQR {
        public Complex operate(Complex x) {
            return x.sqr();
        }
        public String toString() {
            return "sqr";
        }
    },
    CUBE {
        public Complex operate(Complex x) {
            return x.cube();
        }
        public String toString() {
            return "cube";
        }
    },
    INVERSE {
        public Complex operate(Complex x) {
            return x.inverse();
        }
        public String toString() {
            return "inv";
        }
    },
    CONJUGATE {
        public Complex operate(Complex x) {
            return x.conjugate();
        }
        public String toString() {
            return "conj";
        }
    },
    EXP {
        public Complex operate(Complex x) {
            return x.exp();
        }
        public String toString() {
            return "exp";
        }
    },
    LOG {
        public Complex operate(Complex x) {
            return x.log();
        }
        public String toString() {
            return "log";
        }
    },
    SQRT {
        public Complex operate(Complex x) {
            return x.sqrt();
        }
        public String toString() {
            return "sqrt";
        }
    },
    SIN {
        public Complex operate(Complex x) {
            return x.sin();
        }
        public String toString() {
            return "sin";
        }
    },
    COS {
        public Complex operate(Complex x) {
            return x.cos();
        }
        public String toString() {
            return "cos";
        }
    },
    SINH {
        public Complex operate(Complex x) {
            return x.sinh();
        }
        public String toString() {
            return "sinh";
        }
    },
    COSH {
        public Complex operate(Complex x) {
            return x.cosh();
        }
        public String toString() {
            return "cosh";
        }
    },
    TAN {
        public Complex operate(Complex x) {
            return x.tan();
        }
        public String toString() {
            return "tan";
        }
    },
    TANH {
        public Complex operate(Complex x) {
            return x.tanh();
        }
        public String toString() {
            return "tanh";
        }
    }

}
