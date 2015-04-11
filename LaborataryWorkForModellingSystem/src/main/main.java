package main;

import elements.PlateWithRoundCorner;
import frames.ElementFrame;
import phisics.MeshForPlateWithRoundCorner;

import javax.swing.*;
import java.awt.*;

/**
 * Created by neikila on 10.04.15.
 */
public class main {
    public static void main(String[] args) {
        System.out.println("Start!");
        double defaultTemperature = 0;
        double width = 8;
        double height = 6;
        double rad = 3;
        double zeroX = 100;
        double zeroY = 600;

        double dx = 0.2;
        double dy = 0.2;

        final PlateWithRoundCorner plate = new PlateWithRoundCorner(width, height, rad, zeroX, zeroY);
        final MeshForPlateWithRoundCorner mesh = new MeshForPlateWithRoundCorner(plate, dx, dy, defaultTemperature);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ElementFrame frame = new ElementFrame(plate, mesh);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

