package phisics;

import java.awt.geom.Point2D;

/**
 * Created by neikila on 11.04.15.
 */
public class Node implements  Cloneable{
    private Point2D point;
    private double temperature;
    private boolean isEdge;

    public Node(Point2D point, double temperature, boolean isEdge) {
        this.point = point;
        this.temperature = temperature;
        this.isEdge = isEdge;
    }

    public boolean isEdge() { return isEdge; }

    public Point2D getPoint() {
        return point;
    }

    public double getTemperature() {
        return temperature;
    }

    public void increaseTemperature(double deltaT) {
        temperature += deltaT;
    }

    public void setTemperature(double deltaT) {
        temperature = deltaT;
    }
}
