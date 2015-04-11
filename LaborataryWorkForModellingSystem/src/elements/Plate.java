package elements;

/**
 * Created by neikila on 10.04.15.
 */
public class Plate {
    protected double width;
    protected double height;
    protected double x;
    protected double y;

    Plate() {
        width = 0;
        height = 0;
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
