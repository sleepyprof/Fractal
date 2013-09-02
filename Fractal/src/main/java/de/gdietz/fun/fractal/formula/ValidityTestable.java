package de.gdietz.fun.fractal.formula;

public interface ValidityTestable<X> {

    public ValidityTest<X> getTest();
    public void setTest(ValidityTest<X> test);

}
