package de.gdietz.fun.fractal.util;

import java.text.NumberFormat;

public class ComplexNumberFormatter {

    private final NumberFormat nf;
    private final boolean parentheses;
    private final boolean negativeParentheses;
    private final boolean sign;

    private final StringBuffer buffer;
    private int terms;
    private boolean negative;

    public ComplexNumberFormatter(NumberFormat nf, boolean parentheses, boolean negativeParentheses, boolean sign) {
        this.nf = nf;
        this.parentheses = parentheses;
        this.negativeParentheses = negativeParentheses;
        this.sign = sign;
        buffer = new StringBuffer();
        terms = 0;
        negative = false;
    }

    public ComplexNumberFormatter(NumberFormat nf, boolean parentheses) {
        this(nf, parentheses, false, false);
    }

    public ComplexNumberFormatter(NumberFormat nf) {
        this(nf, false);
    }

    public ComplexNumberFormatter(boolean parentheses, boolean negativeParentheses, boolean sign) {
        this(NumberFormat.getInstance(), parentheses, negativeParentheses, sign);
    }

    public ComplexNumberFormatter(boolean parentheses) {
        this(parentheses, false, false);
    }

    public ComplexNumberFormatter() {
        this(false);
    }

    public void addNumberString(double r, String vec) {
        if (r == 0.0)
            return;
        if (r < 0.0 && terms == 0)
            negative = true;
        terms++;
        String add = nf.format(r);
        if (!vec.isEmpty() && add.contains("E") && add.contains("e"))
            add += "*";
        add += vec;
        if (r == 1.0 && !vec.isEmpty())
            add = vec;
        if (r == -1.0 && !vec.isEmpty())
            add = "-" + vec;
        String op = "";
        if (buffer.length() != 0 && !add.startsWith("-"))
            op = "+";
        buffer.append(op);
        buffer.append(add);
    }

    public void addNumberString(double r) {
        addNumberString(r, "");
    }

    public String toString() {
        if (sign) {
            if (negative) {
                if (parentheses && terms > 1 || negativeParentheses)
                    return "+ (" + buffer.toString() + ")";
                if (buffer.charAt(0) == '-') {
                    StringBuffer temp = new StringBuffer(buffer);
                    temp.insert(1, " ");
                    return temp.toString();
                }
                return "+ (" + buffer.toString() + ")";
            } else {
                if (terms == 0)
                    return "+ " + nf.format(0.0);
                if (parentheses && terms > 1)
                    return "+ (" + buffer.toString() + ")";
                return "+ " + buffer.toString();
            }
        } else {
            if (terms == 0)
                return nf.format(0.0);
            if (parentheses && terms > 1 || negativeParentheses && negative)
                return "(" + buffer.toString() + ")";
            return buffer.toString();
        }
    }

}
