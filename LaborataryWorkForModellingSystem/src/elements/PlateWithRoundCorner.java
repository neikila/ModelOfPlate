package elements;

/**
 * Created by neikila on 10.04.15.
 */
public class PlateWithRoundCorner extends Plate{
    private final double radius;
    private final int cornerNum;

    public PlateWithRoundCorner(double w, double h, double r, double x, double y) {
        super(x, y, w, h);
        radius = r;
        this.cornerNum = 0;
    }

    public double getRadius() {
        return radius;
    }

    public int getCornerNum() {
        return cornerNum;
    }
}
