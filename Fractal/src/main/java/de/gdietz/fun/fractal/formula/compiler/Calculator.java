package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.util.Normed;

import java.util.List;
import java.util.Stack;

public class Calculator<X extends Normed> {

    private final Stack<X> stack;

    public Calculator() {
        stack = new Stack<>();
    }

    public void reset() {
        stack.clear();
    }

    private X getResult() {
        if (stack.size() != 1)
            throw new CalculatorException("Calculation not started or not finished");
        return stack.get(0);
    }

    private void process(BinaryOperator<X> op) {
        if (stack.size() < 2)
            throw new CalculatorException("Not enough arguments for binary operator");
        X y = stack.pop();
        X x = stack.pop();
        X result = op.compose(x, y);
        stack.push(result);
    }

    private void process(UnaryOperator<X> op) {
        if (stack.size() < 1)
            throw new CalculatorException("No argument for unary operator");
        X x = stack.pop();
        X result = op.operate(x);
        stack.push(result);
    }

    private void process(Constant<X> c) {
        X result = c.get();
        stack.push(result);
    }

    private void process(Variable<X> var, X z, X c, X p) {
        X result = var.get(z, c, p);
        if (result == null)
            throw new CalculatorException("Variable not supported");
        stack.push(result);
    }

    private void process(CalculatorAction<X> action, X z, X c, X p) {
        if (action instanceof BinaryOperator)
            process((BinaryOperator<X>) action);
        else if (action instanceof UnaryOperator)
            process((UnaryOperator<X>) action);
        else if (action instanceof Constant)
            process((Constant<X>) action);
        else if (action instanceof Variable)
            process((Variable<X>) action, z, c, p);
        else
            throw new CalculatorException("Unknown calculation action");
    }

    public X calculate(List<CalculatorAction<X>> algorithm, X z, X c, X p) {
        reset();
        for(CalculatorAction<X> action : algorithm)
            process(action, z, c, p);
        return getResult();
    }
    
    public X calculate(List<CalculatorAction<X>> algorithm, X z, X c) {
        return calculate(algorithm, z, c, null);
    }

    public static <X extends Normed> boolean checkValidity(List<CalculatorAction<X>> algorithm) {
        int level = 0;
        for(CalculatorAction<X> action : algorithm) {
            if (action instanceof BinaryOperator) {
                if (level < 2)
                    return false;
                level--;
            } else if (action instanceof UnaryOperator) {
                if (level < 1)
                    return false;
            } else if (action instanceof Constant)
                level++;
            else if (action instanceof Variable)
                level++;
            else
                throw new CalculatorException("Unknown calculation action");
        }
        return level == 1;
    }

}
