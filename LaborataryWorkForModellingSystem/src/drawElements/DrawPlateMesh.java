package drawElements;

import helper.Settings;
import phisics.MeshForPlateWithRoundCorner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

/**
 * Created by neikila on 11.04.15.
 */
public class DrawPlateMesh extends JComponent implements ActionListener {

    private int count = 0;
    final private Timer timer;
    final private MeshForPlateWithRoundCorner mesh;
    final protected double scaleX;
    final protected double scaleY;
    final private DrawPlate drawPlate;

    public DrawPlateMesh (MeshForPlateWithRoundCorner mesh, double scaleX, double scaleY) {
        this.scaleX = scaleX;
        this.scaleY = -1 * scaleY;
        this.mesh = mesh;
        drawPlate = new DrawPlate(mesh.getPlate(), scaleX, scaleY);
        timer = new Timer((int) (Settings.deltaTime * 1000), this);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;


        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawPlate.paintComponent(g);

        int limitY = mesh.getMaxYIndex();

        for (int j = 0; j < limitY; ++j) {
            for (int i = 0; mesh.contains((i + 1), (j + 1)); ++i) {
                Rectangle2D rect = new Rectangle2D.Double();
                rect.setFrameFromDiagonal(
                        mesh.getX() + mesh.getNode(i, j).getPoint().getX() * scaleX,
                        mesh.getY() + mesh.getNode(i, j).getPoint().getY() * scaleY,
                        mesh.getX() + mesh.getNode((i + 1), (j + 1)).getPoint().getX() * scaleX,
                        mesh.getY() + mesh.getNode((i + 1), (j + 1)).getPoint().getY() * scaleY
                        );
                double avgTemp = (mesh.getNode(i, j).getTemperature() +
                        mesh.getNode(i, (j + 1)).getTemperature() +
                        mesh.getNode((i + 1), j).getTemperature() +
                        mesh.getNode((i + 1), (j + 1)).getTemperature()) / 4.0;
                int red = (int)(avgTemp / 200 * 255);
                if (red > 255) {
                    red = 255;
                    System.out.println("Red more than 255 i = " + i + " j = " + j);
                }
                if (red < 0) {
                    System.out.println("Red less than 0 i = " + i + " j = " + j);
                    red = 0;
                }
                int blue = 255 - red;
                try {
                    g2.setPaint(new Color(red, 0, blue, 150));
                } catch (Exception e) {
                    System.out.println("I have tried");
                    System.exit(1);
                }
                g2.fill(rect);
                g2.draw(rect);
                g2.setPaint(new Color(0, 0, 0));
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mesh.iteration(Settings.deltaTime, Settings.leftTemperature, Settings.rightTemperature, Settings.topTemperature, Settings.bottomTemperature);
        repaint();
        ++count;
        if (count % 100 == 0) {
            System.out.println("Iteration number: " + count);
        }
        if (count  > Settings.count)
            stop();
    }

    public void start() {
        timer.start();
    }
    public void stop() {
        System.out.println("Stopped");
        timer.stop();
    }

    @Override
    public Dimension getPreferredSize() {
        return drawPlate.getPreferredSize();
    }

}