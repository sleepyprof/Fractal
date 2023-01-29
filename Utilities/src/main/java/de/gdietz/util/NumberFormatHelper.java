package de.gdietz.util;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatHelper {

    public static NumberFormat getDefaultNumberFormat() {
        return NumberFormat.getInstance(Locale.ROOT);
    }

}
