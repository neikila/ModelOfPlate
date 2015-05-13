package drawElements;

import helper.ReverseEvent;
import helper.Settings;
import phisics.MeshForPlateWithRoundCorner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.nio.file.Paths;
import java.util.Scanner;

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
    private Path2D path;
    private ReverseEvent reverseEvent;

    public DrawPlateMesh (MeshForPlateWithRoundCorner mesh, double scaleX, double scaleY, ReverseEvent reverseEvent) {
        this.scaleX = scaleX;
        this.scaleY = -1 * scaleY;
        this.mesh = mesh;
        drawPlate = new DrawPlate(mesh.getPlate(), scaleX, scaleY);
        timer = new Timer((int) (Settings.deltaTime * 1000 * Settings.step), this);
        path = drawPlate.getPath();
        this.reverseEvent = reverseEvent;
    }

    public void drawTempPanel(Graphics2D g2) {
        Rectangle2D panel = new Rectangle2D.Double();
        int delta = 100;
        Point2D zero = new Point2D.Double(delta + drawPlate.plate.getWidth() * scaleX + drawPlate.plate.getX(), drawPlate.plate.getY());
        Point2D diag = new Point2D.Double(delta + drawPlate.plate.getWidth() * scaleX + drawPlate.plate.getX() + 40, drawPlate.plate.getY() - 255 * 2);
        panel.setFrameFromDiagonal(zero, diag);
        g2.setPaint(new Color(0, 0, 0));
        g2.fill(panel);
        g2.draw(panel);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        drawTempPanel(g2);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int limitY = mesh.getMaxYIndex();
        int limitX = mesh.getMaxXIndex();
        double dx = mesh.getDx();
        double dy = mesh.getDy();

        double zeroX = mesh.getX();
        double zeroY = mesh.getY();

        for (int i = 0; i < limitX + 1; ++i) {
            g2.draw(new Line2D.Double(zeroX + i * dx * scaleX, zeroY, zeroX + i * dx * scaleX, zeroY + limitY * dy * scaleY));
        }

        for (int i = 0; i < limitY + 1; ++i) {
            g2.draw(new Line2D.Double(zeroX, zeroY + i * dy * scaleY, zeroX + limitX * dx * scaleX, zeroY + i * dy * scaleY));
        }

        g2.draw(path);
        g2.clip(path);

        for (int j = 0; j < limitY; ++j) {
            for (int i = 0; mesh.contains((i + 1), (j + 1)); ++i) {

                Rectangle2D rect = new Rectangle2D.Double();
                rect.setFrameFromDiagonal(
                        mesh.getX() + mesh.getNode(i, j).getPoint().getX() * scaleX,
                        mesh.getY() + mesh.getNode(i, j).getPoint().getY() * scaleY,
                        mesh.getX() + mesh.getNode((i + 1), (j + 1)).getPoint().getX() * scaleX,
                        mesh.getY() + mesh.getNode((i + 1), (j + 1)).getPoint().getY() * scaleY
                );
                double avgTemp = 0;
                avgTemp = (mesh.getNode(i, j).getTemperature() +
                        mesh.getNode(i, (j + 1)).getTemperature() +
                        mesh.getNode((i + 1), j + 1).getTemperature() +
                        mesh.getNode((i + 1), j).getTemperature()) / 4.0;
                int red = (int)(avgTemp / Settings.maxTemp * 255);
                int roundSeed = Settings.colorStep;
                red = (red / roundSeed) * roundSeed;
                if (red > 255) {
                    red = 255;
                }
                if (red < 0) {
                    red = 0;
                }
                int blue = 255 - red;

                g2.setPaint(new Color(red, 0, blue, 150));
                g2.fill(rect);
                g2.draw(rect);
                g2.setPaint(new Color(0, 0, 0));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (count < Settings.iterationNum) {
            getMeshFromFile("step" + count);
            repaint();
            count += Settings.step;
            if (count % 100 == 0) {
                System.out.println("Iteration number: " + count);
            }
        } else {
            reverseEvent.finished();
            stop();
        }
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        System.out.println("Iteration number: " + count);
        System.out.println("Stopped");
        timer.stop();
    }

    public void restart() {
        stop();
        count = 0;
    }

    private void getMeshFromFile(String fileName) {
        try {
            Scanner step = new Scanner(Paths.get("out/" + fileName));
            int i, j;
            while(step.hasNext()) {
                i = step.nextInt();
                j = step.nextInt();
                mesh.getNode(i, j).setTemperature(step.nextDouble());
            }
        } catch (Exception e) {
            System.out.println("Ups no such " + fileName);
            System.exit(-1);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return drawPlate.getPreferredSize();
    }
}