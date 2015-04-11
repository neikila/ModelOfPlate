package elements;

/**
 * Created by neikila on 10.04.15.
 */
public class Plate {
    protected final double width;
    protected final double height;
    protected final double x;
    protected final double y;

    Plate() {
        width = 0;
        height = 0;
        x = 0;
        y = 0;
    }

    Plate(double x, double y, double w, double h) {
        width = w;
        height = h;
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }

    public double getY() { return y; }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
