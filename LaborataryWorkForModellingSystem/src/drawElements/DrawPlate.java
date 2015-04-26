package drawElements;

import elements.PlateWithRoundCorner;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;

/**
 * Created by neikila on 10.04.15.
 */

public class DrawPlate extends JComponent {
    protected final int DEFAULT_WIDTH;
    protected final int DEFAULT_HEIGHT;
    protected final double scaleX;
    protected final double scaleY;

    protected PlateWithRoundCorner plate;

    public DrawPlate(PlateWithRoundCorner plateWithRoundCorner, double scaleX, double scaleY) {
        this.scaleX = scaleX;
        this.scaleY = -1 * scaleY;
        plate = plateWithRoundCorner;
        DEFAULT_WIDTH = (int) (plate.getWidth() * scaleX);
        DEFAULT_HEIGHT = (int) (plate.getHeight() * scaleY);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        int num = plate.getCornerNum();
        double zeroX = plate.getX();
        double zeroY = plate.getY();

        //Переделать под любое расположение отверстия =) UPD я тут подумал, а ну его нафиг

        g2.draw(new Line2D.Double(zeroX, zeroY,
                zeroX + plate.getWidth() * scaleX, zeroY));
        g2.draw(new Line2D.Double(zeroX, zeroY,
                zeroX, zeroY + plate.getHeight() * scaleY));
        g2.draw(new Line2D.Double(zeroX, zeroY + plate.getHeight() * scaleY,
                zeroX + (plate.getWidth() - plate.getRadius()) * scaleX, zeroY + plate.getHeight() * scaleY));
        g2.draw(new Line2D.Double(zeroX + plate.getWidth() * scaleX, zeroY,
                zeroX + plate.getWidth() * scaleX, zeroY + (plate.getHeight() - plate.getRadius()) * scaleY));


        double centerX;
        if (num == 0 || num == 3)
            centerX = zeroX + (plate.getWidth() - 2 * plate.getRadius()) * scaleX;
        else
            centerX = zeroX;
        double centerY;
        if (num > 1)
            centerY = zeroY + plate.getHeight() * scaleY;
        else
            centerY = zeroY + 2 * plate.getRadius() * scaleY;
        double width = 2 * plate.getRadius() * scaleX;
        double height = 2 * plate.getRadius() * -1 * scaleY;

        Arc2D arc = new Arc2D.Double(centerX, centerY, width, height, 90 * num, 90, 0);
        g2.draw(arc);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension((int)plate.getX() + DEFAULT_WIDTH + 5, (int)plate.getY() + DEFAULT_HEIGHT + 5);
    }
}