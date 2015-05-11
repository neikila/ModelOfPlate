package drawElements;

import elements.PlateWithRoundCorner;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

/**
 * Created by neikila on 10.04.15.
 */

public class DrawPlate extends JComponent {
    protected final int DEFAULT_WIDTH;
    protected final int DEFAULT_HEIGHT;
    protected final double scaleX;
    protected final double scaleY;

    protected PlateWithRoundCorner plate;

    private Line2D leftLine;
    private Line2D rightLine;
    private Line2D topLine;
    private Line2D bottomLine;
    private Arc2D arc;

    public DrawPlate(PlateWithRoundCorner plateWithRoundCorner, double sscaleX, double sscaleY) {
        scaleX = sscaleX;
        scaleY = -1 * sscaleY;
        plate = plateWithRoundCorner;
        DEFAULT_WIDTH = (int) (plate.getWidth() * scaleX);
        DEFAULT_HEIGHT = (int) (plate.getHeight() * scaleY);

        int num = plate.getCornerNum();
        double zeroX = plate.getX();
        double zeroY = plate.getY();

        //Переделать под любое расположение отверстия =) UPD я тут подумал, а ну его нафиг

        double centerX;
        if (num == 0 || num == 3) {
            centerX = zeroX + (plate.getWidth() - 2 * plate.getRadius()) * scaleX;
        }
        else {
            centerX = zeroX;
        }
        double centerY;
        if (num > 1) {
            centerY = zeroY + plate.getHeight() * scaleY;
        }
        else {
            centerY = zeroY + 2 * plate.getRadius() * scaleY;
        }
        double width = 2 * plate.getRadius() * scaleX;
        double height = 2 * plate.getRadius() * -1 * scaleY;

        topLine = new Line2D.Double(zeroX + (plate.getWidth() - plate.getRadius()) * scaleX, zeroY + plate.getHeight() * scaleY,
                zeroX, zeroY + plate.getHeight() * scaleY);
        leftLine = new Line2D.Double(zeroX, zeroY + plate.getHeight() * scaleY,
                zeroX, zeroY);
        bottomLine = new Line2D.Double(zeroX, zeroY,
                zeroX + plate.getWidth() * scaleX, zeroY);
        rightLine = new Line2D.Double(zeroX + plate.getWidth() * scaleX, zeroY,
                zeroX + plate.getWidth() * scaleX, zeroY + (plate.getHeight() - plate.getRadius()) * scaleY);
        arc = new Arc2D.Double(centerX, centerY, width, height, 90 * num, 90, Arc2D.OPEN);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        //Переделать под любое расположение отверстия =) UPD я тут подумал, а ну его нафиг

        g2.draw(leftLine);
        g2.draw(rightLine);
        g2.draw(topLine);
        g2.draw(bottomLine);
        g2.draw(arc);
    }

    public Path2D getPath() {
        Path2D path = new Path2D.Double();
        path.append(topLine, true);
        path.append(leftLine, true);
        path.append(bottomLine, true);
        path.append(rightLine, true);
        path.append(arc, true);
        path.closePath();
//        Point2D point2D = new Point2D.Double(plate.getX() + 50, plate.getY() - 50);
//        if(path.contains(point2D)) {
//            System.out.println("Success");
//        } else {
//            System.out.println("Not");
//        }
        return path;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension((int)plate.getX() + DEFAULT_WIDTH + 5, (int)plate.getY() + DEFAULT_HEIGHT + 5);
    }
}