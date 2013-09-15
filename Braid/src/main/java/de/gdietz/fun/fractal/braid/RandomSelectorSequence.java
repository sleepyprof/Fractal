package de.gdietz.fun.fractal.braid;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class RandomSelectorSequence implements SelectorSequence {

	private final Random random = new Random();

	private final int number;
    private final boolean negative;

	private final List<Integer> selectors;

	public RandomSelectorSequence(int number, boolean negative) {
		this.number = number;
        this.negative = negative;
		selectors = new ArrayList<Integer>();
	}

    public RandomSelectorSequence(int number) {
        this(number, false);
    }

	private synchronized void createNewSelectors(int index) {
		while(selectors.size() <= index) {
			int selector = random.nextInt(number) + 1;
            if (negative && random.nextInt(2) == 1)
                selector = -selector;
			selectors.add(selector);
		}
	}

	public int get(int index) {
		createNewSelectors(index);
		return selectors.get(index);
	}

    public String toString() {
        return "random";
    }

}
