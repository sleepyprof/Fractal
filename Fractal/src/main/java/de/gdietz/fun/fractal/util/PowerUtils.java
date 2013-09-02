package de.gdietz.fun.fractal.util;

public class PowerUtils {

    public static String powerString(String var, int exponent) {
        if (exponent == 0)
            return "1";
        if (exponent == 1)
            return var;
        if (exponent < 0)
            return var + "^(" + exponent + ")";
        return var + "^" + exponent;
    }

    public static String powerString(String var, double exponent) {
        if (exponent == 0)
            return "1";
        if (exponent == 1)
            return var;
        if (exponent < 0)
            return var + "^(" + exponent + ")";
        return var + "^" + exponent;
    }

    public static String powerString(String var, NormedNumber<?> exponent) {
        if (exponent.isZero())
            return "1";
        if (exponent.isUnit())
            return var;
        return var + "^" + exponent.toString(true, true, false);
    }

    public static String powerString(String func, String var, int exponent) {
        if (exponent == 0)
            return "1";
        if (exponent == 1)
            return func + " " + var;
        if (exponent < 0)
            return "1 / (" + func + "^" + exponent + " " + var + ")";
        return func + "^" + exponent + " " + var;
    }

    public static String powerString(String var, int exponent, String multiplicand) {
        if (exponent == 0)
            return multiplicand;
        return powerString(var, exponent) + " " + multiplicand;
    }

    public static String powerString(NormedNumber<?> factor, String var, int exponent, boolean sign) {
        if (exponent == 0 || factor.isZero())
            return factor.toString(true, !sign, sign);
        if (factor.isUnit())
            return (sign ? "+ " : "") + powerString(var, exponent);
        if (factor.negate().isUnit())
            return (sign ? "- " : "-") + powerString(var, exponent);
        return factor.toString(true, !sign, sign) + " " + powerString(var, exponent);
    }

    public static String powerString(NormedNumber<?> factor, String var, int exponent) {
        return powerString(factor, var, exponent, false);
    }

    public static String powerString(double factor, String var, int exponent, boolean sign) {
        return powerString(new DoubleNumber(factor), var, exponent, sign);
    }

    public static String powerString(double factor, String var, int exponent) {
        return powerString(new DoubleNumber(factor), var, exponent);
    }

    public static String powerString(String var, int exponent, NormedNumber<?> factor, boolean sign) {
        if (exponent == 0 || factor.isZero())
            return factor.toString(true, !sign, sign);
        if (factor.isUnit())
            return (sign ? "+ " : "") + powerString(var, exponent);
        if (factor.negate().isUnit())
            return (sign ? "- " : "-") + powerString(var, exponent);
        String factorStr = factor.toString(true, !sign, sign);
        String signStr = "";
        if (factorStr.startsWith("+ ") || factorStr.startsWith("- ")) {
            signStr = factorStr.substring(0, 2);
            factorStr = factorStr.substring(2);
        }
        return signStr + powerString(var, exponent) + " " + factorStr;
    }
    
    public static String powerString(String var, int exponent, NormedNumber<?> factor) {
        return powerString(var, exponent, factor, false);
    }



}
