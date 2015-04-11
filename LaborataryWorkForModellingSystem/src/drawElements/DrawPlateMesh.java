package drawElements;

import phisics.MeshForPlateWithRoundCorner;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by neikila on 11.04.15.
 */
public class DrawPlateMesh extends JComponent{

    MeshForPlateWithRoundCorner mesh;
    final protected double scaleX;
    final protected double scaleY;
    final private DrawPlate drawPlate;
    public DrawPlateMesh (MeshForPlateWithRoundCorner mesh, double scaleX, double scaleY) {
        this.scaleX = scaleX;
        this.scaleY = -1 * scaleY;
        this.mesh = mesh;
        drawPlate = new DrawPlate(mesh.getPlate(), scaleX, scaleY);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        drawPlate.paintComponent(g);

        int limitY = mesh.getMaxYIndex();

        for (int j = 0; j < limitY; ++j) {
            for (int i = 0; ; ++i) {
                Rectangle2D rect = new Rectangle2D.Double();
                if (!mesh.contains((i + 1), (j + 1)))
                    break;
                rect.setFrameFromDiagonal(
                        mesh.getX() + mesh.getNode(i, j).getPoint().getX() * scaleX,
                        mesh.getY() + mesh.getNode(i, j).getPoint().getY() * scaleY,
                        mesh.getX() + mesh.getNode((i + 1), (j + 1)).getPoint().getX() * scaleX,
                        mesh.getY() + mesh.getNode((i + 1), (j + 1)).getPoint().getY() * scaleY
                        );
                double avgTemp = (mesh.getNode(i, (j + 1)).getTemperature() +
                        mesh.getNode(i, (j + 1)).getTemperature() +
                        mesh.getNode((i + 1), j).getTemperature() +
                        mesh.getNode((i + 1), (j + 1)).getTemperature()) / 4;
                g2.setPaint(new Color((int)(avgTemp / 200 * 255), 0, (int)(255 - avgTemp / 200 * 255)));
                g2.fill(rect);
                g2.draw(rect);
                g2.setPaint(new Color(0, 0, 0));
            }
        }

    }
}