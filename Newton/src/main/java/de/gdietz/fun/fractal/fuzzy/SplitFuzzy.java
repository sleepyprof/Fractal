package de.gdietz.fun.fractal.fuzzy;

public class SplitFuzzy implements Fuzzy {

    private final Fuzzy lambdaFuzzy;
    private final Fuzzy epsilonFuzzy;

    public SplitFuzzy(Fuzzy lambdaFuzzy, Fuzzy epsilonFuzzy) {
        this.lambdaFuzzy = lambdaFuzzy;
        this.epsilonFuzzy = epsilonFuzzy;
    }

    public void setLambda(double lambda) {
        lambdaFuzzy.setLambda(lambda);
    }

    public double getLambda() {
        return lambdaFuzzy.getLambda();
    }

    public void setEpsilon(double epsilon) {
        epsilonFuzzy.setEpsilon(epsilon);
    }

    public double getEpsilon() {
        return epsilonFuzzy.getEpsilon();
    }

}
