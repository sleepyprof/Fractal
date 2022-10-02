package de.gdietz.fun.fractal.formula.compiler.parser;

import de.gdietz.fun.fractal.formula.compiler.*;
import de.gdietz.fun.fractal.util.BigComplex;
import de.gdietz.fun.fractal.util.Complex;
import de.gdietz.fun.fractal.util.ComplexNumberParser;

import java.util.ArrayList;
import java.util.List;

public class CalculatorBigComplexParser implements CalculatorParser<BigComplex> {

    public CalculatorAction<BigComplex> parseSingle(String str, boolean allowParam) {
        if (str.equals("p") && !allowParam) 
            throw new CalculatorException("Parameter variable not allowed");

        for(BinaryOperator<BigComplex> op : BigComplexBinaryOperators.values())
            if (str.equals(op.toString()))
                return op;
        
        for(UnaryOperator<BigComplex> op : BigComplexUnaryOperators.values())
            if (str.equals(op.toString()))
                return op;
        
        for(Constant<BigComplex> c : BigComplexConstants.values())
            if (str.equals(c.toString()))
                return c;

        for(Variable<BigComplex> c : BigComplexVariables.values())
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
            return new ConstantImpl<>(new BigComplex(c));
        } catch (NumberFormatException e) {
            throw new CalculatorException("Cannot parse calculator string as number: " + str, e);
        }
    }

    public List<CalculatorAction<BigComplex>> parse(String str, boolean allowParam) {
        List<CalculatorAction<BigComplex>> result = new ArrayList<>();

        String[] partStrs = str.split(",");
        for(String partStr : partStrs)
            result.add(parseSingle(partStr.trim(), allowParam));

        return result;
    }

}
