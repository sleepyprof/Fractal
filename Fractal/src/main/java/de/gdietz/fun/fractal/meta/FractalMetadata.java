package de.gdietz.fun.fractal.meta;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.formula.ValidityTest;
import de.gdietz.fun.fractal.formula.ValidityTestable;
import de.gdietz.fun.fractal.fuzzy.Fuzzy;
import de.gdietz.fun.fractal.palette.Palette;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Coordinate3D;
import de.gdietz.fun.fractal.view.ColorStrategy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class FractalMetadata extends HashMap<String, String> {

    private static final String TITLE         = "Title";           // Short (one line) title or caption for image
    private static final String AUTHOR        = "Author";          // Name of image's creator
    private static final String DESCRIPTION   = "Description";     // Description of image (possibly long)
    private static final String COPYRIGHT     = "Copyright";       // Copyright notice
    private static final String CREATION_TIME = "Creation Time";   // Time of original image creation
    private static final String SOFTWARE      = "Software";        // Software used to create the image
    private static final String DISCLAIMER    = "Disclaimer";      // Legal disclaimer
    private static final String WARNING       = "Warning";         // Warning of nature of content
    private static final String SOURCE        = "Source";          // Device used to create the image
    private static final String COMMENT       = "Comment";         // Miscellaneous comment; conversion from GIF comment

    public static final String FRACTAL_TYPE = "FractalType";

    public static final String FRACTAL_WIDTH  = "FractalWidth";
    public static final String FRACTAL_HEIGHT = "FractalHeight";
    public static final String FRACTAL_SIZE   = "FractalSize";

    public static final String FRACTAL_CORNER_FROM_X = "FractalCornerFromX";
    public static final String FRACTAL_CORNER_FROM_Y = "FractalCornerFromY";
    public static final String FRACTAL_CORNER_FROM_Z = "FractalCornerFromZ";
    public static final String FRACTAL_CORNER_TO_X   = "FractalCornerToX";
    public static final String FRACTAL_CORNER_TO_Y   = "FractalCornerToY";
    public static final String FRACTAL_CORNER_TO_Z   = "FractalCornerToZ";

    public static final String FRACTAL_PARAMETER_X = "FractalParameterX";
    public static final String FRACTAL_PARAMETER_Y = "FractalParameterY";
    public static final String FRACTAL_PARAMETER_Z = "FractalParameterZ";

    public static final String FRACTAL_ITERATOR_FACTORY = "FractalIteratorFactory";
    public static final String FRACTAL_MAXITER = "FractalMaxiter";

    public static final String FRACTAL_ITERATION_TYPE = "FractalIterationType";

    public static final String FRACTAL_FUZZY_LAMBDA  = "FractalFuzzyParameterLambda";
    public static final String FRACTAL_FUZZY_EPSILON = "FractalFuzzyParameterEpsilon";

    public static final String FRACTAL_PALETTE = "FractalPalette";


    private static final DateFormat RFC822DATEFORMAT = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);


    public FractalMetadata() {
        setSoftware("Fractal(3D) Java Program by Dr. Gunnar Dietz");
        setCreationTime(new Date());
        setSource("Java " + System.getProperty("java.version") +
                " (" + System.getProperty("java.vendor") + ") on " +
                System.getProperty("os.name") + " " + System.getProperty("os.version") +
                " (" + System.getProperty("os.arch") + ")");
        setAuthor(System.getProperty("user.name"));
    }


    public void setTitle(String title) {
        put(TITLE, title);
    }

    public void setAuthor(String author) {
        put(AUTHOR, author);
    }

    public void setDescription(String description) {
        put(DESCRIPTION, description);
    }

    public void setCopyright(String copyright) {
        put(COPYRIGHT, copyright);
    }

    public void setCreationTime(Date date) {
        put(CREATION_TIME, RFC822DATEFORMAT.format(date));
    }

    public void setSoftware(String software) {
        put(SOFTWARE, software);
    }

    public void setDisclaimer(String disclaimer) {
        put(DISCLAIMER, disclaimer);
    }

    public void setWarning(String warning) {
        put(WARNING, warning);
    }

    public void setSource(String source) {
        put(SOURCE, source);
    }

    public void setComment(String comment) {
        put(COMMENT, comment);
    }


    public void setType(String type) {
        put(FRACTAL_TYPE, type);
    }

    public void setSize(int width, int height) {
        put(FRACTAL_WIDTH, String.valueOf(width));
        put(FRACTAL_HEIGHT, String.valueOf(height));
    }

    public void setSize(int size) {
        put(FRACTAL_SIZE, String.valueOf(size));
    }

    public void setCorners(Coordinate from, Coordinate to) {
        put(FRACTAL_CORNER_FROM_X, String.valueOf(from.getX()));
        put(FRACTAL_CORNER_FROM_Y, String.valueOf(from.getY()));
        put(FRACTAL_CORNER_TO_X, String.valueOf(to.getX()));
        put(FRACTAL_CORNER_TO_Y, String.valueOf(to.getY()));
    }

    public void setCorners(Coordinate3D from, Coordinate3D to) {
        put(FRACTAL_CORNER_FROM_X, String.valueOf(from.getX()));
        put(FRACTAL_CORNER_FROM_Y, String.valueOf(from.getY()));
        put(FRACTAL_CORNER_FROM_Z, String.valueOf(from.getZ()));
        put(FRACTAL_CORNER_TO_X, String.valueOf(to.getX()));
        put(FRACTAL_CORNER_TO_Y, String.valueOf(to.getY()));
        put(FRACTAL_CORNER_TO_Z, String.valueOf(to.getZ()));
    }

    public void setParameter(Coordinate parameter) {
        put(FRACTAL_PARAMETER_X, String.valueOf(parameter.getX()));
        put(FRACTAL_PARAMETER_Y, String.valueOf(parameter.getY()));
    }

    public void setParameter(Coordinate3D parameter) {
        put(FRACTAL_PARAMETER_X, String.valueOf(parameter.getX()));
        put(FRACTAL_PARAMETER_Y, String.valueOf(parameter.getY()));
        put(FRACTAL_PARAMETER_Z, String.valueOf(parameter.getZ()));
    }

    public void setIteratorFactory(FractalIteratorFactory<?> iteratorFactory) {
        put(FRACTAL_ITERATOR_FACTORY, iteratorFactory.toString());
        if (iteratorFactory instanceof ValidityTestable) {
            ValidityTestable<?> testable = (ValidityTestable<?>) iteratorFactory;
            ValidityTest<?> test = testable.getTest();
            if (test instanceof Fuzzy) {
                Fuzzy fuzzy = (Fuzzy) test;
                setLambda(fuzzy.getLambda());
            }
        }
    }

    public void setMaxiter(int maxiter) {
        put(FRACTAL_MAXITER, String.valueOf(maxiter));
    }

    public void setIterationType(boolean endless) {
        put(FRACTAL_ITERATION_TYPE, endless ? "Endless" : "Simple");
    }

    public void setLambda(double lambda) {
        put(FRACTAL_FUZZY_LAMBDA, String.valueOf(lambda));
    }

    public void setEpsilon(double epsilon) {
        put(FRACTAL_FUZZY_EPSILON, String.valueOf(epsilon));
    }

    public void setPalette(Palette palette) {
        put(FRACTAL_PALETTE, palette.toString());
    }

    public void setColorStrategy(ColorStrategy<?> coloring) {
        setPalette(coloring.getPalette());
    }

}
