package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelDerivableFunction;
import de.gdietz.fun.fractal.util.NormedSemiNumber;
import de.gdietz.fun.fractal.util.PowerUtils;

public class ApfelFunctionPowSum<X extends NormedSemiNumber<X>> implements ApfelDerivableFunction<X> {

    private final int[] exponents;

    public ApfelFunctionPowSum(int... exponents) {
        this.exponents = exponents;
    }

    public X func(X z, X c) {
        X sum = z.getZero();
        for(int exp : exponents)
            sum = sum.add(z.pow(exp));
        return sum.add(c);
    }

    public X derivation(X z, X c) {
        X sum = z.getZero();
        for(int exp : exponents)
            sum = sum.add(z.pow(exp - 1).multiply(exp));
        return sum;
    }

    public String toString() {
        if (exponents.length == 0)
            return "c";
        String str = PowerUtils.powerString("z", exponents[0]);
        for(int i = 1; i < exponents.length; i++)
            str += " + " + PowerUtils.powerString("z", exponents[i]);
        return str + " + c";
    }

}
