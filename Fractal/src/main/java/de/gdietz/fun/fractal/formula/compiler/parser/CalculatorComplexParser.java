package de.gdietz.fun.fractal.formula.compiler.parser;

import de.gdietz.fun.fractal.formula.compiler.*;
import de.gdietz.fun.fractal.util.Complex;
import de.gdietz.fun.fractal.util.ComplexNumberParser;

import java.util.ArrayList;
import java.util.List;

public class CalculatorComplexParser implements CalculatorParser<Complex> {

    public CalculatorAction<Complex> parseSingle(String str, boolean allowParam) {
        if (str.equals("p") && !allowParam)
            throw new CalculatorException("Parameter variable not allowed");

        for(BinaryOperator<Complex> op : ComplexBinaryOperators.values())
            if (str.equals(op.toString()))
                return op;

        for(UnaryOperator<Complex> op : ComplexUnaryOperators.values())
            if (str.equals(op.toString()))
                return op;

        for(Constant<Complex> c : ComplexConstants.values())
            if (str.equals(c.toString()))
                return c;

        for(Variable<Complex> c : ComplexVariables.values())
            if (str.equals(c.toString()))
                return c;

        if (str.startsWith("^")) {
            String exp = str.substring(1);
            int n;
            try {
                n = Integer.parseInt(exp);
            } catch (NumberFormatException e) {
                throw new CalculatorException("Cannot parse calculator power string: " + str, e);
            }
            return new PowOperator<>(n);
        }

        if (str.isEmpty())
            throw new CalculatorException("Calculator string empty.");

        try {
            Complex c = ComplexNumberParser.parseComplex(str);
            return new ConstantImpl<>(c);
        } catch (NumberFormatException e) {
            throw new CalculatorException("Cannot parse calculator string as number: " + str, e);
        }
    }

    public List<CalculatorAction<Complex>> parse(String str, boolean allowParam) {
        List<CalculatorAction<Complex>> result = new ArrayList<>();

        String[] partStrs = str.split(",");
        for(String partStr : partStrs)
            result.add(parseSingle(partStr.trim(), allowParam));

        return result;
    }

}
