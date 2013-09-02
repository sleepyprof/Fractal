package de.gdietz.fun.fractal.test;

import de.gdietz.fun.fractal.braid.ConstantSelectorSequence;
import de.gdietz.fun.fractal.braid.RealIntersecData;
import de.gdietz.fun.fractal.braid.SelectorSequence;
import de.gdietz.fun.fractal.util.*;

import java.awt.*;

public class Test {

    public void test() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        for (GraphicsDevice gd : gs) {
            DisplayMode dm = gd.getDisplayMode();
            int screenWidth = dm.getWidth();
            int screenHeight = dm.getHeight();
            System.out.println(screenWidth + "x" + screenHeight);
        }

        quaternionTest(new Quaternion(.43, 1.32, -2.32, -4.3));
        selectorTest(1, 1, 2);
        braidTest(-1.3, -1.5, -1.7, 0.111, 0.223, 0.337);
        powerUtilsTest();
        parseTest("1.5 + 6i - 32 j");
        parseTest("i - k");
        parseTest("-i+j");
        transformTest();
        matrixTest(new Matrix(.123,.345,.526,.264));
        matrixTest(new Matrix3D(.123,.534,.345,1.23,.653,.2342,.123,.534,.23));
    }

    public void quaternionTest(Quaternion q) {
        Quaternion q2 = q.cube();
        Quaternion q3 = q.multiply(q.sqr());
        Quaternion q4 = q.multiply(q.multiply(q));

        System.out.println(q);
        System.out.println(q2);
        System.out.println(q3);
        System.out.println(q4);
    }

    public void selectorTest(int... i) {
        SelectorSequence seq = new ConstantSelectorSequence(i);
        for(int index=0; index<40; index++)
            System.out.print(seq.get(index));
        System.out.println();
    }

    public void braidTest(double d1, double d2, double d3, double y, double z, double u) {
        RealIntersecData isd = new RealIntersecData(d1, d2, d3, y, z, u);

        System.out.println(isd);
        System.out.println(isd.sigma1());
        System.out.println(isd.sigma2());
        System.out.println(isd.sigma1().sigma2());
        System.out.println(isd.sigma2().sigma1());
        System.out.println(isd.sigma1().sigmaInv1());
        System.out.println(isd.sigma2().sigmaInv2());
    }

    public void powerUtilsTest() {
        Quaternion[] factors = {Quaternion.ZERO, Quaternion.ONE, new Quaternion(-1.0), Quaternion.I, new Quaternion(0.0, -1.0), new Quaternion(1.0, 1.0), new Quaternion(-1.0, 1.0)};
        for(Quaternion factor : factors) {
            System.out.println(PowerUtils.powerString(factor, "z", 2));
            System.out.println(PowerUtils.powerString(factor, "z", 2, true));
            System.out.println(PowerUtils.powerString("z", 2, factor));
            System.out.println(PowerUtils.powerString("z", 2, factor, true));
            System.out.println("---");
        }
    }

    public void parseTest(String source) {
        Quaternion result = ComplexNumberParser.parseQuaternion(source);
        System.out.println(result);
    }

    public void transformTest(Coordinate oldFrom, Coordinate oldTo, Coordinate newFrom, Coordinate newTo, Coordinate c) {
        AffineTransformFactory<Coordinate, Matrix> tf = new CoordinateAffineTransformFactory();
        AffineOperator<Coordinate, Matrix> op = tf.get(oldFrom, oldTo, newFrom, newTo);
        Coordinate d = op.operate(c);
        System.out.printf("{%s %s} -> {%s %s}: %s -> %s\n", oldFrom, oldTo, newFrom, newTo, c, d);
    }

    public void transformTest() {
        transformTest(new Coordinate(-1.0, -1.0), new Coordinate(1.0, 1.0), new Coordinate(1.0, 1.0), new Coordinate(1.3, 1.3), new Coordinate(0.5, 0.5));
    }

    public <T extends Tuple<T>, O extends LinearOperator<T, O>> void matrixTest(O m) {
        O inv = m.inverse();
        O e1 = m.compose(inv);
        O e2 = inv.compose(m);

        System.out.println("m:\n" + m);
        System.out.println("det:\n" + m.det());
        System.out.println("inv:\n" + inv);
        System.out.println("m * inv:\n" + e1);
        System.out.println("inv * m:\n" + e2);
    }


    public static void main(String... args) {
        new Test().test();
    }

}
