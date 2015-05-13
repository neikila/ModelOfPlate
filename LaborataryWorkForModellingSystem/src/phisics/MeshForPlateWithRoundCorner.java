package phisics;

import elements.PlateWithRoundCorner;
import helper.Settings;

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by neikila on 12.04.15.
 */
public class MeshForPlateWithRoundCorner {
    private static long count = 0;
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

    public double getDx() { return dx; }

    public double getDy() { return dy; }

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
                nodes.put(i + ";" + j, new Node(new Point2D.Double(x, y), defaultTemperature, false));
            }
        }

        for (j = limitYBeforeArc; j < limitY; ++j) {
            y = j * dy;
            for (i = 0; i < limitXBeforeArc; ++i) {
                x = (i * dx) ;
                nodes.put(i + ";" + j, new Node(new Point2D.Double(x, y), defaultTemperature, false));
            }
            for (; Math.pow((i * dx - arcX), 2) + Math.pow((j * dy - arcY), 2) < Math.pow(plate.getRadius(), 2); ++i) {
                x = (i * dx);
                nodes.put(i + ";" + j, new Node(new Point2D.Double(x, y), defaultTemperature, false));
            }
            if (Math.pow((i * dx - arcX), 2) + Math.pow((j * dy - arcY), 2) == Math.pow(plate.getRadius(), 2)) {
                x = (i * dx);
                nodes.put(i + ";" + j, new Node(new Point2D.Double(x, y), defaultTemperature, true));
            }
        }

        for (i = limitY - 1; i > limitYBeforeArc - 1; --i) {
            y = i * dy;
            x = arcX + Math.sqrt(Math.pow(plate.getRadius(), 2) - Math.pow(y - arcY, 2));
            j = (int) (x / dx);
            for (; j < limitX; ++j) {
                x = j * dx;
                if (Math.pow(x - dx - arcX, 2) + Math.pow(y - dy - arcY, 2) < Math.pow(plate.getRadius(), 2)) {
                    if (!nodes.containsKey(j + ";" + i))
                        nodes.put(j + ";" + i, new Node(new Point2D.Double(x, y), defaultTemperature, true));
                } else {
                    break;
                }
            }
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

    public int getMaxXIndex() {
        return (int)(plate.getWidth() / dx);
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
                Node value = new Node(temp.getPoint(), temp.getTemperature(), temp.isEdge());
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

        final double arcX = plate.getWidth() - plate.getRadius();
        final double arcY = plate.getHeight() - plate.getRadius();

        double tX;
        double tY;
        Node currentNode;
        Node leftNode;
        Node rightNode;
        Node topNode;
        Node bottomNode;
        int i;
        int j;
        boolean edge = false;

        for (j = 0; j < getMaxYIndex() + 1; ++j) {
            for (i = 0; (currentNode = currentState.get(i + ";" + j)) != null; ++i) {
                edge = false;
                tX = 0;
                tY = 0;


                leftNode = currentState.get((i - 1) + ";" + j);
                rightNode = currentState.get((i + 1) + ";" + j);
                topNode = currentState.get(i + ";" + (j + 1));
                bottomNode = currentState.get(i + ";" + (j - 1));

                if(i == 0 && (j == getMaxYIndex() / 2)) {
                    currentNode.setTemperature(rightNode.getTemperature() / (1 + dx));
                    edge = true;
                }

                if (currentNode.isEdge() && !edge) {
                    currentNode.setTemperature(right);
                    edge = true;
                }
                if (rightNode == null && !edge) {
                    currentNode.setTemperature(right);
                    edge = true;
                }
                if (leftNode == null && !edge) {
                    currentNode.setTemperature(left);
                    edge = true;
                }
                if (topNode == null && !edge) {
                    currentNode.setTemperature(top);
                    edge = true;
                }
                if (bottomNode == null && !edge) {
                    currentNode.setTemperature(bottom);
                    edge = true;
                }
                if (!edge) {
                    if (rightNode.isEdge()) {
                        double mu = (arcX +
                                Math.sqrt(Math.pow(plate.getRadius(), 2)
                                        - Math.pow(currentNode.getPoint().getY() - arcY, 2))
                                - currentNode.getPoint().getX()) / dx;
                        mu = Math.abs(mu);
                        tX -= (mu + 1) * currentNode.getTemperature();
                        tX += right;
                        tX += mu * leftNode.getTemperature();
                        tX = 2 * tX / (dx * dx * mu * (mu + 1));
                    } else {
                        tX -= 2 * currentNode.getTemperature();
                        tX += rightNode.getTemperature();
                        tX += leftNode.getTemperature();
                        tX /= (dx * dx);
                    }

                    if (topNode.isEdge()) {
                        double mu = (arcY +
                                Math.sqrt(Math.pow(plate.getRadius(), 2)
                                        - Math.pow(currentNode.getPoint().getX() - arcX, 2))
                                - currentNode.getPoint().getY()) / dx;
                        mu = Math.abs(mu);
                        tY -= (mu + 1) * currentNode.getTemperature();
                        tY += right;
                        tY += mu * bottomNode.getTemperature();
                        tY = 2 * tY / (dy * dy * mu * (mu + 1));
                    } else {
                        tY -= 2 * currentNode.getTemperature();
                        tY += topNode.getTemperature();
                        tY += bottomNode.getTemperature();
                        tY /= (dy * dy);
                    }
                    currentNode.setTemperature(currentNode.getTemperature() + deltaTime * (tX + tY));
                }
            }
        }
        if (count %  Settings.step == 0) {
            printToFile("out/step" + count);
        }
        ++count;
        if (count % 100 == 0) {
            System.out.println("Iteration Number = " + count);
        }
    }

    public long getCount() {
        return count;
    }

    private void printToFile(String fileName) {
        PrintWriter file = null;
        try {
            file = new PrintWriter(fileName);
            int limitY = getMaxYIndex() + 1;
            Node temp;
            if (Settings.printToFile) {
                for (int j = 0; j < limitY; ++j) {
                    for (int i = 0; (temp = getNode(i, j)) != null; ++i) {
                        String result = "" + temp.getPoint().getX() + ' ' + temp.getPoint().getY() + ' ' + temp.getTemperature();
                        file.println(result);
                    }
                    file.println();
                }
            } else {
                for (int j = 0; j < limitY; ++j) {
                    for (int i = 0; (temp = getNode(i, j)) != null; ++i) {
                        String result = "" + i + ' ' + j + ' ' + temp.getTemperature();
                        file.println(result);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ups. No such file.");
        } finally {
            if (file != null) {
                file.close();
            }
        }
    }
}