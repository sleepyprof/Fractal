package de.gdietz.fun.fractal.newton;

public class IterateInfo<I> {

    private final I info;
    private final boolean valid;

    public IterateInfo(I info, boolean valid) {
        this.info = info;
        this.valid = valid;
    }

    public IterateInfo() {
        this(null, false);
    }

    public I getInfo() {
        return info;
    }

    public boolean isValid() {
        return valid;
    }

}
