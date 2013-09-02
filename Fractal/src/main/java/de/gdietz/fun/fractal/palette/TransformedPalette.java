package de.gdietz.fun.fractal.palette;

import java.awt.*;

public class TransformedPalette implements Palette {

    private static float swing(float x) {
        float y = 0.5f * (x + 1.0f);
        float frac = y - (float) Math.floor(y);
        return Math.abs(frac + frac - 1.0f);
    }

    public enum Form {

        LINEAR(1.0f) {
            public int get(int index, float factor) {
                return index > 0 ? (int)(factor * index) : index;
            }
        },
        LOGARITHMIC(100.0f) {
            public int get(int index, float factor) {
                return index > 0 ? (int)(factor * Math.log(index + 1)) : index;
            }
        },
        QUADRATIC(10.0f) {
            public int get(int index, float factor) {
                return index > 0 ? (int)(factor * Math.sqrt(index)) : index;
            }
        },
        SAW(100.0f) {
            public int get(int index, float factor) {
                return index > 0 ? (int)(factor * swing(index / factor) + 1) : index;
            }
        };

        private final float baseFactor;

        private Form(float baseFactor) {
            this.baseFactor = baseFactor;
        }

        public abstract int get(int index, float factor);

        public float getBaseFactor() {
            return baseFactor;
        }

    }

    private final Palette palette;
    private final Form form;
    private final float factor;

    public TransformedPalette(Palette palette, Form form, float factor) {
        this.palette = palette;
        this.form = form;
        this.factor = factor;
    }

    public TransformedPalette(Palette palette, Form form) {
        this(palette, form, 1.0f);
    }

    public TransformedPalette(Palette palette, float factor) {
        this(palette, Form.LINEAR, factor);
    }

    public Color get(int index) {
        return palette.get(form.get(index, form.getBaseFactor() * factor));
    }

    public String toString() {
        String str;
        switch(form) {
            case LINEAR:
                str = "Linear";
                break;
            case LOGARITHMIC:
                str = "Log";
                break;
            case QUADRATIC:
                str = "Quadratic";
                break;
            case SAW:
                str = "Saw";
                break;
            default:
                throw new AssertionError("Unknown TransformedPalette Form.");
        }
        str += "Palette(" + palette + ", " + factor + ")";
        return str;
    }

}
