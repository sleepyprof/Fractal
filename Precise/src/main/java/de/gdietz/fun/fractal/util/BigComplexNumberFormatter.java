package de.gdietz.fun.fractal.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class BigComplexNumberFormatter {

    private final boolean parentheses;
    private final boolean negativeParentheses;
    private final boolean sign;

    private final StringBuffer buffer;
    private int terms;
    private boolean negative;

    public BigComplexNumberFormatter(boolean parentheses, boolean negativeParentheses, boolean sign) {
        this.parentheses = parentheses;
        this.negativeParentheses = negativeParentheses;
        this.sign = sign;
        buffer = new StringBuffer();
        terms = 0;
        negative = false;
    }

    public BigComplexNumberFormatter(boolean parentheses) {
        this(parentheses, false, false);
    }

    public BigComplexNumberFormatter() {
        this(false);
    }

    public void addNumberString(BigDecimal r, String vec) {
        int rsign = r.compareTo(BigDecimal.ZERO);
        if (rsign == 0)
            return;
        if (rsign < 0 && terms == 0)
            negative = true;
        terms++;
        String add = r.toString();
        if (!vec.isEmpty() && add.contains("E") && add.contains("e"))
            add += "*";
        add += vec;
        if (r.compareTo(BigDecimal.ONE) == 0 && !vec.isEmpty())
            add = vec;
        if (r.negate().compareTo(BigDecimal.ONE) == 0 && !vec.isEmpty())
            add = "-" + vec;
        String op = "";
        if (buffer.length() != 0 && !add.startsWith("-"))
            op = "+";
        buffer.append(op);
        buffer.append(add);
    }

    public void addNumberString(BigDecimal r) {
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
                    return "+ 0";
                if (parentheses && terms > 1)
                    return "+ (" + buffer.toString() + ")";
                return "+ " + buffer.toString();
            }
        } else {
            if (terms == 0)
                return "0";
            if (parentheses && terms > 1 || negativeParentheses && negative)
                return "(" + buffer.toString() + ")";
            return buffer.toString();
        }
    }

}
