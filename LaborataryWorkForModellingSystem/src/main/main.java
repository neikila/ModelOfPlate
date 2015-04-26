package main;

import elements.PlateWithRoundCorner;
import frames.ElementFrame;
import helper.Settings;
import phisics.MeshForPlateWithRoundCorner;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by neikila on 10.04.15.
 */
public class main {
    public static void main(String[] args) {
        System.out.println("Start!");

        Map<String, String[]> test = new HashMap<String, String[]>();

        final PlateWithRoundCorner plate = new PlateWithRoundCorner(Settings.width, Settings.height, Settings.rad, Settings.zeroX, Settings.zeroY);
        final MeshForPlateWithRoundCorner mesh = new MeshForPlateWithRoundCorner(plate, Settings.dx, Settings.dy, Settings.defaultTemperature);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ElementFrame frame = new ElementFrame(plate, mesh);
//                TestFrame frame = new TestFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}