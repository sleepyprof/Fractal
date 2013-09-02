package de.gdietz.fun.fractal.formula.collections;

import de.gdietz.fun.fractal.formula.*;
import de.gdietz.fun.fractal.formula.formulas.*;
import de.gdietz.fun.fractal.util.Complex;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Coordinate3D;

import java.util.ArrayList;
import java.util.List;

public class ComplexFunctionCollection implements FunctionCollection<Complex> {

    public static final ParamCoordMapper<Complex, Complex, Coordinate> mapper = new ComplexCoordMapper();
    public static final ParamCoordMapper<Complex, Complex, Coordinate3D> mapper3D = new ComplexCoord3DMapper();
    
    public FractalIteratorFactory<Coordinate> getApfelIteratorFactory(ApfelFunction<Complex> function, double bound) {
        return new ApfelIteratorFactory<Complex, Coordinate>(mapper, function, new BoundaryTest<Complex>(bound));
    }

    public FractalIteratorFactory<Coordinate3D> getApfel3DIteratorFactory(ApfelFunction<Complex> function, double bound) {
        return new ApfelIteratorFactory<Complex, Coordinate3D>(mapper3D, function, new BoundaryTest<Complex>(bound));
    }

    public List<ApfelFunction<Complex>> getCollection() {
        List<ApfelFunction<Complex>> functions = new ArrayList<ApfelFunction<Complex>>();

        for (int n = 2; n <= 8; n++)
            functions.add(new ApfelFunctionPow<Complex>(n));

        for (int n = 2; n <= 6; n++)
            for (int m = 1; m < n; m++)
                functions.add(new ApfelFunctionPowSum<Complex>(n, m));

        for (int n = 1; n <= 5; n++)
            for (int m = 1; m <= 5; m++)
                if (n != m) {
                    functions.add(new ApfelFunctionMixedPow<Complex>(n, Complex.MINUS_ONE, m));
                    functions.add(new ApfelFunctionMixedPow<Complex>(n, Complex.I, m));
                }

        for (int m = 1; m <= 4; m++)
            for (int n = m + 1; n <= 5; n++)
                functions.add(new ApfelFunctionRational(n, m));
        for (int n = 1; n <= 5; n++)
            functions.add(new ApfelFunctionRational(n,  n));

        functions.add(new ApfelFunctionSin());
        functions.add(new ApfelFunctionCos());
        functions.add(new ApfelFunctionSinh());
        functions.add(new ApfelFunctionCosh());
        functions.add(new ApfelFunctionExp());

        for (int n = 2; n <= 5; n++)
            functions.add(new ApfelFunctionPow<Complex>(-n));

        for (int n = 2; n <= 5; n++) {
            functions.add(new ApfelFunctionSinPow(n));
            functions.add(new ApfelFunctionCosPow(n));
            functions.add(new ApfelFunctionTanPow(n));
            functions.add(new ApfelFunctionSinhPow(n));
            functions.add(new ApfelFunctionCoshPow(n));
            functions.add(new ApfelFunctionTanhPow(n));
        }

        for (int n = 3; n <= 9; n+=2)
            functions.add(new ApfelFunctionPowHalf(n));

        functions.add(new ApfelFunctionComplexPow(new Complex(1.0, 1.0)));
        functions.add(new ApfelFunctionComplexPow(new Complex(2.0, 1.0)));
        functions.add(new ApfelFunctionComplexPow(new Complex(2.0, 2.0)));
        functions.add(new ApfelFunctionComplexPow(new Complex(3.0, 1.0)));
        functions.add(new ApfelFunctionComplexPow(new Complex(3.0, 2.0)));
        functions.add(new ApfelFunctionComplexPow(new Complex(3.0, 3.0)));
        functions.add(new ApfelFunctionComplexPow(new Complex(4.0, 1.0)));

        for (int n = 2; n <= 5; n++)
            functions.add(new ApfelFunctionLogisticPow(n));

        functions.add(new ApfelFunctionGauss());
        functions.add(new ApfelFunctionGauss(0.0, 2.0));
        functions.add(new ApfelFunctionGauss(Complex.ZERO, Complex.I));
        functions.add(new ApfelFunctionGauss(1.0, 1.0));

        functions.add(new ApfelFunctionGamma());
        functions.add(new ApfelFunctionLogGamma());

        for (int n = 0; n <= 5; n++)
            for (FormulaHelper.TrigonHyperbolFunc func : FormulaHelper.TrigonHyperbolFunc.values())
                if (n != 0 ||
                        !(func == FormulaHelper.TrigonHyperbolFunc.SIN ||
                                func == FormulaHelper.TrigonHyperbolFunc.COS ||
                                func == FormulaHelper.TrigonHyperbolFunc.SINH ||
                                func == FormulaHelper.TrigonHyperbolFunc.COSH))
                    functions.add(new ApfelFunctionPowTrigonHyperbol(n, func));

        for (int n = 0; n <= 5; n++)
            for (FormulaHelper.TrigonHyperbolFunc func : FormulaHelper.TrigonHyperbolFunc.values())
                functions.add(new ApfelFunctionPowTrigonHyperbolOfInverse(n, func));

        for (int n = 1; n <= 5; n++)
            for (FormulaHelper.TrigonHyperbolFunc func : FormulaHelper.TrigonHyperbolFunc.values())
                functions.add(new ApfelFunctionLogTrigonHyperbolPow(n, func));

        for (int n = 2; n <= 5; n++) {
            functions.add(new ApfelFunctionBubblePow<Complex>(-n, Complex.ZERO));
            functions.add(new ApfelFunctionBubblePow<Complex>(-n, Complex.ONE));
            functions.add(new ApfelFunctionBubblePow<Complex>(-n, Complex.I));
        }

        return functions;
    }

    public String getName() {
        return "complex";
    }

}
