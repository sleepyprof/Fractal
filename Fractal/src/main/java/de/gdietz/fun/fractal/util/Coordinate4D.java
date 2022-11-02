package de.gdietz.fun.fractal.util;

public class Coordinate4D implements Tuple<Coordinate4D> {

    public static final Coordinate4D ORIGIN = new Coordinate4D();

    public static final Coordinate4D NaN = new Coordinate4D(Double.NaN, Double.NaN, Double.NaN, Double.NaN);

    protected final double x;
	protected final double y;
    protected final double z;
    protected final double w;

    public Coordinate4D(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Coordinate4D() {
        this(0.0, 0.0, 0.0, 0.0);
	}

    public Coordinate4D(Coordinate4D c) {
        x = c.x;
        y = c.y;
        z = c.z;
        w = c.w;
    }

	public double getX() {
		return x;
	}

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getW() {
        return w;
    }

    public double normSqr() {
		return x * x + y * y + z * z + w * w;
	}

	public double norm() {
		return Math.sqrt(normSqr());
	}

    public Coordinate4D negate() {
        return new Coordinate4D(-x, -y, -z, -w);
    }

    public Coordinate4D add(Coordinate4D c) {
        return new Coordinate4D(x + c.x, y + c.y, z + c.z, w + c.w);
    }

    public Coordinate4D subtract(Coordinate4D c) {
        return new Coordinate4D(x - c.x, y - c.y, z - c.z, w - c.w);
    }

    public Coordinate4D multiply(double r) {
        return new Coordinate4D(r * x, r * y, r * z, r * w);
    }

    public boolean isZero() {
        return x == 0.0 && y == 0.0 && z == 0.0 && w == 0.0;
    }

    public String toString() {
        return "(" + x + ", " + y + ", " + z + ", " + w + ")";
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate4D)) return false;

        Coordinate4D that = (Coordinate4D) o;

        return x == that.x && y == that.y && z == that.z && w == that.w;
    }

    public int hashCode() {
        int result;
        long temp;
        temp = x != 0.0d ? Double.doubleToLongBits(x) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = y != 0.0d ? Double.doubleToLongBits(y) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = z != 0.0d ? Double.doubleToLongBits(z) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = w != 0.0d ? Double.doubleToLongBits(w) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

}

