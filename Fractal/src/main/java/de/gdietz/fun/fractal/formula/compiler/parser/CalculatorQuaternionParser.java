package de.gdietz.fun.fractal.formula.compiler.parser;

import de.gdietz.fun.fractal.formula.compiler.*;
import de.gdietz.fun.fractal.util.ComplexNumberParser;
import de.gdietz.fun.fractal.util.Quaternion;

import java.util.ArrayList;
import java.util.List;

public class CalculatorQuaternionParser implements CalculatorParser<Quaternion> {

    public CalculatorAction<Quaternion> parseSingle(String str, boolean allowParam) {
        if (str.equals("p") && !allowParam) 
            throw new CalculatorException("Parameter variable not allowed");

        for(BinaryOperator<Quaternion> op : QuaternionBinaryOperators.values())
            if (str.equals(op.toString()))
                return op;
        
        for(UnaryOperator<Quaternion> op : QuaternionUnaryOperators.values())
            if (str.equals(op.toString()))
                return op;
        
        for(Constant<Quaternion> c : QuaternionConstants.values())
            if (str.equals(c.toString()))
                return c;

        for(Variable<Quaternion> c : QuaternionVariables.values())
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
            return new PowOperator<Quaternion>(n);
        }

        if (str.isEmpty())
            throw new CalculatorException("Calculator string empty.");

        try {
            Quaternion c = ComplexNumberParser.parseQuaternion(str);
            return new ConstantImpl<Quaternion>(c);
        } catch (NumberFormatException e) {
            throw new CalculatorException("Cannot parse calculator string as number: " + str, e);
        }
    }

    public List<CalculatorAction<Quaternion>> parse(String str, boolean allowParam) {
        List<CalculatorAction<Quaternion>> result = new ArrayList<CalculatorAction<Quaternion>>();

        String[] partStrs = str.split(",");
        for(String partStr : partStrs)
            result.add(parseSingle(partStr.trim(), allowParam));

        return result;
    }

}
