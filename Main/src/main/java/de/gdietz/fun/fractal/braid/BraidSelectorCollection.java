package de.gdietz.fun.fractal.braid;

import java.util.ArrayList;
import java.util.List;

public class BraidSelectorCollection {

    public List<SelectorSequence> getSelectorSequenceCollection() {
        List<SelectorSequence> seqs = new ArrayList<>();

        seqs.add(new RandomSelectorSequence(2));

        for(int i=1; i<=2; i++)
            seqs.add(new ConstantSelectorSequence(i));

        seqs.add(new ConstantSelectorSequence(1, 2));
        seqs.add(new ConstantSelectorSequence(2, 1));

        for(int i=1; i<=2; i++)
            for(int j=1; j<=2; j++)
                for(int k=1; k<=2; k++)
                    if (i!=j || i!=k || j!=k)
                        seqs.add(new ConstantSelectorSequence(i, j, k));

        return seqs;
    }

}
