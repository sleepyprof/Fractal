package de.gdietz.fun.fractal.formula.compiler.parser;

import de.gdietz.fun.fractal.formula.compiler.CalculatorAction;
import de.gdietz.fun.fractal.util.Normed;

import java.util.List;

public interface CalculatorParser<X extends Normed> {

    public List<CalculatorAction<X>> parse(String str, boolean allowParam);

}
