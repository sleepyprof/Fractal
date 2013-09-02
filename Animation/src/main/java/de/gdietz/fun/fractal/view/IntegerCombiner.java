package de.gdietz.fun.fractal.view;

import java.util.List;

public class IntegerCombiner implements Combiner<Integer> {

    public static int maxIndex(int index1, int index2) {
        if (index1 >= 0 && index2 >= 0)
            return Math.max(index1, index2);
        return Math.min(index1, index2);
    }

    public Integer combine(List<Integer> data) {
        int result = 0;
        for(int index : data)
            result = maxIndex(result, index);
        return result;
    }

}
