package de.gdietz.fun.fractal.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;

public class ComplexNumberParser {

    private static class ParsedInfo {

        public double number;
        public String vec;

        public ParsedInfo(double number, String vec) {
            this.number = number;
            this.vec = vec;
        }

        public String toString() {
            return "{" + number + "*" + vec + "}";
        }

    }

    private static ParsedInfo parseSingle(String source) {
        String numberStr = source;
        boolean minus = false;
        if (source.startsWith("+") || source.startsWith("-")) {
            String sign = source.substring(0, 1);
            if (sign.equals("-"))
                minus = true;
            numberStr = source.substring(1);
        }
        numberStr = numberStr.trim();

        NumberFormat format = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        Number number = format.parse(numberStr, pos);
        int splitPos = pos.getErrorIndex() == -1 ? pos.getIndex() : pos.getErrorIndex();

        String vec = numberStr.substring(splitPos);
        numberStr = numberStr.substring(0, splitPos);
        vec = vec.trim();
        if (number != null)
            return new ParsedInfo(minus ? -number.doubleValue() : number.doubleValue(), vec);

        numberStr = numberStr.trim();
        if (numberStr.isEmpty())
            return new ParsedInfo(minus ? -1.0 : 1.0, vec);

        try {
            number = format.parse(numberStr);
        } catch (ParseException e) {
            throw new AssertionError("Could not parse the previously parsed part: " + numberStr);
        }
        return new ParsedInfo(minus ? -number.doubleValue() : number.doubleValue(), vec);
    }

    private static List<ParsedInfo> parse(String source) {
        List<ParsedInfo> result = new ArrayList<ParsedInfo>();
        String str = source.trim();
        while(!str.isEmpty()) {
            ParsedInfo info = parseSingle(str);
            double x = info.number;
            String remaining = info.vec;
            int plusPos = remaining.indexOf('+');
            int minusPos = remaining.indexOf('-');
            int signPos = (plusPos == -1 ? minusPos : (minusPos == -1 ? plusPos : Math.min(plusPos, minusPos)));
            String vec;
            if (signPos == -1) {
                vec = remaining;
                remaining = "";
            } else {
                vec = remaining.substring(0, signPos);
                remaining = remaining.substring(signPos);
            }
            vec = vec.trim();
            ParsedInfo part = new ParsedInfo(info.number, vec);
            result.add(part);
            str = remaining.trim();
        }
        return result;
    }

    private static Complex parseComplex(ParsedInfo info) throws NumberFormatException {
        if (info.vec.isEmpty())
            return new Complex(info.number);
        if (info.vec.equals("i"))
            return new Complex(0.0, info.number);
        throw new NumberFormatException("Not a valid complex vector string: " + info.vec);
    }

    private static Quaternion parseQuaternion(ParsedInfo info) throws NumberFormatException {
        if (info.vec.isEmpty())
            return new Quaternion(info.number);
        if (info.vec.equals("i"))
            return new Quaternion(0.0, info.number);
        if (info.vec.equals("j"))
            return new Quaternion(0.0, 0.0, info.number, 0.0);
        if (info.vec.equals("k"))
            return new Quaternion(0.0, 0.0, 0.0, info.number);
        throw new NumberFormatException("Not a valid quaternion vector string: " + info.vec);
    }

    public static Complex parseComplex(String source) throws NumberFormatException {
        List<ParsedInfo> infos = parse(source);
        Complex result = Complex.ZERO;
        for(ParsedInfo info : infos)
            result = result.add(parseComplex(info));
        return result;
    }

    public static Quaternion parseQuaternion(String source) throws NumberFormatException {
        List<ParsedInfo> infos = parse(source);
        Quaternion result = Quaternion.ZERO;
        for(ParsedInfo info : infos)
            result = result.add(parseQuaternion(info));
        return result;
    }
    
}
