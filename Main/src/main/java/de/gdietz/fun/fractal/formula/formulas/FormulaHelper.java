package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.util.Complex;

public class FormulaHelper {

    private static final Complex PI = new Complex(Math.PI);

    private static final Complex[] PARAMS = {
            new Complex(0.99999999999980993),
            new Complex(676.5203681218851),
            new Complex(-1259.1392167224028),
            new Complex(771.32342877765313),
            new Complex(-176.61502916214059),
            new Complex(12.507343278686905),
            new Complex(-0.13857109526572012),
            new Complex(9.9843695780195716e-6),
            new Complex(1.5056327351493116e-7)
    };

    private static final int G = PARAMS.length - 2;

    public static Complex gamma(Complex z) {
        if (z.getReal() < 0.5)
            return PI.divide(PI.multiply(z).sin().multiply(gamma(Complex.ONE.subtract(z))));
        z = z.subtract(1.0);
        Complex x = PARAMS[0];
        for (int i = 1; i < PARAMS.length; i++) {
            x = x.add(PARAMS[i].divide(z.add(new Complex(i))));
        }
        Complex t = z.add(new Complex(G + 0.5));
        return PI.add(PI).sqrt().multiply(t.pow(z.add(new Complex(0.5)))).multiply(t.negate().exp()).multiply(x);
    }

    public static Complex logGamma(Complex z) {
        return gamma(z).log();
    }


    public static enum TrigonHyperbolFunc {

        SIN {
            public String toString() {
                return "sin";
            }
            public Complex calc(Complex z) {
                return z.sin();
            }
        },
        COS {
            public String toString() {
                return "cos";
            }
            public Complex calc(Complex z) {
                return z.cos();
            }
        },
        TAN {
            public String toString() {
                return "tan";
            }
            public Complex calc(Complex z) {
                return z.tan();
            }
        },
        SEC {
            public String toString() {
                return "sec";
            }
            public Complex calc(Complex z) {
                return z.cos().inverse();
            }
        },
        CSC {
            public String toString() {
                return "csc";
            }
            public Complex calc(Complex z) {
                return z.sin().inverse();
            }
        },
        COT {
            public String toString() {
                return "cot";
            }
            public Complex calc(Complex z) {
                return z.tan().inverse();
            }
        },
        SINH {
            public String toString() {
                return "sinh";
            }
            public Complex calc(Complex z) {
                return z.sinh();
            }
        },
        COSH {
            public String toString() {
                return "cosh";
            }
            public Complex calc(Complex z) {
                return z.cosh();
            }
        },
        TANH {
            public String toString() {
                return "tanh";
            }
            public Complex calc(Complex z) {
                return z.tanh();
            }
        },
        SECH {
            public String toString() {
                return "sech";
            }
            public Complex calc(Complex z) {
                return z.cosh().inverse();
            }
        },
        CSCH {
            public String toString() {
                return "csch";
            }
            public Complex calc(Complex z) {
                return z.sinh().inverse();
            }
        },
        COTH {
            public String toString() {
                return "coth";
            }
            public Complex calc(Complex z) {
                return z.tanh().inverse();
            }
        };

        public abstract Complex calc(Complex z);

    }

}
