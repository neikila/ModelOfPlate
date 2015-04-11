package elements;

/**
 * Created by neikila on 10.04.15.
 */
public class PlateWithRoundCorner extends Plate{
    private double radius;
    private int cornerNum;

    public PlateWithRoundCorner(double w, double h, double r, int cornerNum, double x, double y) {
        super(x, y, w, h);
        radius = r;
        this.cornerNum = cornerNum;
    }

    public double getRadius() {
        return radius;
    }

    public int getCornerNum() {
        return cornerNum;
    }
}
