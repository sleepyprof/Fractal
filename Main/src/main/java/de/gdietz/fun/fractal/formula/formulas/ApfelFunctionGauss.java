package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.util.Complex;

public class ApfelFunctionGauss implements ApfelFunction<Complex> {

    private final Complex mu;
    private final Complex sigma;

    private final Complex expFactor;
    private final Complex factor;

    public ApfelFunctionGauss(Complex mu, Complex sigma) {
        this.mu = mu;
        this.sigma = sigma;
        Complex twoSigmaSqr = sigma.sqr().multiply(2.0);
        expFactor = twoSigmaSqr.inverse().negate();
        factor = twoSigmaSqr.multiply(Math.PI).sqrt().inverse();
    }

    public ApfelFunctionGauss(double mu, double sigma) {
        this(new Complex(mu), new Complex(sigma));
    }

    public ApfelFunctionGauss() {
        this(0.0, 1.0);
    }

    public Complex func(Complex z, Complex c) {
        return z.subtract(mu).sqr().multiply(expFactor).exp().multiply(factor).add(c);
    }

    public String toString() {
        return "gauss(z, " + mu + ", " + sigma + ") + c";
    }

}
