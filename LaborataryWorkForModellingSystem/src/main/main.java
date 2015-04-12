package main;

import elements.PlateWithRoundCorner;
import frames.ElementFrame;
import helper.Settings;
import phisics.MeshForPlateWithRoundCorner;

import javax.swing.*;
import java.awt.*;

/**
 * Created by neikila on 10.04.15.
 */
public class main {
    public static void main(String[] args) {
        System.out.println("Start!");
        final PlateWithRoundCorner plate = new PlateWithRoundCorner(Settings.width, Settings.height, Settings.rad, Settings.zeroX, Settings.zeroY);
        final MeshForPlateWithRoundCorner mesh = new MeshForPlateWithRoundCorner(plate, Settings.dx, Settings.dy, Settings.defaultTemperature);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
//                mesh.iteration(Settings.deltaTime, Settings.leftTemperature, Settings.rightTemperature, Settings.topTemperature, Settings.bottomTemperature);
                ElementFrame frame = new ElementFrame(plate, mesh);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}