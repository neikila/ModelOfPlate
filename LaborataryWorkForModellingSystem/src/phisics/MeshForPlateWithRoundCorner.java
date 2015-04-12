package phisics;

import elements.PlateWithRoundCorner;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by neikila on 12.04.15.
 */
public class MeshForPlateWithRoundCorner {
    private final double dx;
    private final double dy;
    private HashMap<String, Node> currentState;
    private HashMap<String, Node> nodes;
    private final double defaultTemperature;
    private PlateWithRoundCorner plate;

    public MeshForPlateWithRoundCorner(PlateWithRoundCorner plateWithRoundCorner, double dx, double dy, double defaultTemperature) {
        this.plate = plateWithRoundCorner;
        this.dx = dx;
        this.dy = dy;
        this.defaultTemperature = defaultTemperature;
        nodes = new HashMap<String, Node>();
        createMesh();
    }

    private void createMesh() {

        final double arcX = plate.getWidth() - plate.getRadius();
        final double arcY = plate.getHeight() - plate.getRadius();

        int limitXBeforeArc = (int) (arcX / dx ) + 1;
        int limitX = (int) (plate.getWidth() / dx) + 1;
        int limitYBeforeArc = (int) (arcY / dy ) + 1;
        int limitY = (int) (plate.getHeight() / dy) + 1;

        int i, j;
        double x, y;

        for (j = 0; j < limitYBeforeArc; ++j) {
            y = j * dy;
            for (i = 0; i < limitX; ++i) {
                x = (i * dx);
                nodes.put(i + ";" + j, new Node(new Point2D.Double(x, y), defaultTemperature));
            }
        }
        for (j = 0; j < limitY; ++j) {
            y = j * dy;
            for (i = 0; i < limitXBeforeArc; ++i) {
                x = (i * dx) ;
                nodes.put(i + ";" + j, new Node(new Point2D.Double(x, y), defaultTemperature));
            }
            for (; Math.pow((i * dx - arcX), 2) + Math.pow((j * dy - arcY), 2) <= Math.pow(plate.getRadius(), 2); ++i) {
                x = (i * dx);
                nodes.put(i + ";" + j, new Node(new Point2D.Double(x, y), defaultTemperature));
            }
        }

        double tempX;
        double tempY;
        boolean flag;
        for (i = limitXBeforeArc; i < limitX; ++i) {
            x = i * dx;
            y = arcY + Math.sqrt(Math.pow(plate.getRadius(), 2) - Math.pow(i * dx - arcX, 2));
            j = ((int)(y / dy) + 1);
            flag = (y % dy != 0);
            tempX = arcX + Math.sqrt(Math.pow(plate.getRadius(), 2) - Math.pow(j * dy - arcY, 2));
            if (((int)(tempX / dx) + 1) == i && (tempX - x + dx) * dy > (y - (j - 1) * dy) * dx ) {
                x = tempX;
                y = j * dy;
                flag = (x % dx != 0);
            }
            if (!nodes.containsKey(i + ";" + j) && flag)
                nodes.put(i + ";" + j, new Node(new Point2D.Double(x, y), defaultTemperature));
        }

        for (i = limitYBeforeArc; i < limitY; ++i) {
            y = i * dy;
            x = arcX + Math.sqrt(Math.pow(plate.getRadius(), 2) - Math.pow(i * dy - arcY, 2));
            j = ((int)(x / dx) + 1);
            flag = (x % dx != 0);
            tempY = arcY + Math.sqrt(Math.pow(plate.getRadius(), 2) - Math.pow(j * dx - arcX, 2));
            if (((int)(tempY / dy) + 1) == i && (tempY - y + dy) * dx > (x - (j - 1) * dx) * dy ) {
                y = tempY;
                x = j * dx;
                flag = (y % dy != 0);
            }
            if (!nodes.containsKey(j + ";" + i) && flag)
                nodes.put(j + ";" + i, new Node(new Point2D.Double(x, y), defaultTemperature));
        }
        updateCurrentState();
    }

    public Node getNode(int a, int b) {
        return nodes.get(a + ";" + b);
    }

    public boolean contains(int xIndex, int yIndex) {
        return nodes.containsKey(xIndex + ";" + yIndex);
    }

    public int getMaxYIndex() {
        return (int)(plate.getHeight() / dy);
    }

    public double getX() {
        return plate.getX();
    }

    public double getY() {
        return plate.getY();
    }

    public PlateWithRoundCorner getPlate() {
        return plate;
    }

    private void updateCurrentState() {
        if (currentState == null) {
            currentState = new HashMap<String, Node>();
            for(Map.Entry<String, Node> entry : nodes.entrySet()) {
                String key = entry.getKey();
                Node temp = entry.getValue();
                Node value = new Node(temp.getPoint(), temp.getTemperature());
                currentState.put(key, value);
            }
        } else {
            HashMap <String, Node> temp = currentState;
            currentState = nodes;
            nodes = temp;
        }
    }

    public void iteration(double deltaTime, double left, double right, double top, double bottom) {
        updateCurrentState();

        double tX = 0;
        double tY = 0;
        Node currentNode;
        Node temp;
        int i;
        int j;
        for (j = 0; j < getMaxYIndex(); ++j) {
            for (i = 0; currentState.containsKey(i + ";" + j); ++i) {
                tX = 0;
                tY = 0;
                if ((currentNode = currentState.get(i + ";" + j)) != null) {
                    tX -= 2 * currentNode.getTemperature();
                    tY -= 2 * currentNode.getTemperature();
                }
                boolean edgeX = false;
                boolean edgeY = false;
                if ((temp = currentState.get((i + 1) + ";" + j)) != null) {
                    tX += temp.getTemperature();
                    if ((temp = currentState.get((i - 1) + ";" + j)) != null) {
                        tX += temp.getTemperature();
                    } else {
                        edgeX = true;
                        tX = left;
                    }
                } else {
                    edgeX = true;
                    tX = right;
                }
                if ((temp = currentState.get(i + ";" + (j - 1))) != null && !edgeX) {
                    tY += temp.getTemperature();
                    if ((temp = currentState.get(i + ";" + (j + 1))) != null) {
                        tY += temp.getTemperature();
                    } else {
                        edgeY = true;
                        tY = top;
                    }
                } else {
                    edgeY = true;
                    tY = bottom;
                }
                if (!(edgeX || edgeY)) {
                    nodes.get(i + ";" + j).setTemperature(currentNode.getTemperature() + deltaTime * (tX / (dx * dx) + tY / (dy * dy)));
                } else {
                    nodes.get(i + ";" + j).setTemperature(edgeX == true ? tX : tY);
                }
            }
        }
    }
}