package main;

import elements.PlateWithRoundCorner;
import frames.ElementFrame;
import helper.Settings;
import phisics.MeshForPlateWithRoundCorner;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by neikila on 10.04.15.
 */
public class main {
    public static void main(String[] args) {
        Settings.getSet();

        final PlateWithRoundCorner plate = new PlateWithRoundCorner(Settings.width, Settings.height, Settings.rad, Settings.zeroX, Settings.zeroY);
        final MeshForPlateWithRoundCorner mesh = new MeshForPlateWithRoundCorner(plate, Settings.dx, Settings.dy, Settings.defaultTemperature);

        int iterationNum = Settings.iterationNum;

        System.out.println("IterationNum = " +  iterationNum);
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();

        if(name.equals("ok")) {
            System.out.println("Start");
            for (int i = 0; i < iterationNum; ++i) {
                mesh.iteration(Settings.deltaTime, Settings.leftTemperature, Settings.rightTemperature, Settings.topTemperature, Settings.bottomTemperature);
            }
            System.out.println("Finished");
            if (Settings.printToFile) {
                printToFile(mesh);
            }
        } else {
            System.exit(0);
        }

        if(!Settings.printToFile) {
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

    private static void printToFile(MeshForPlateWithRoundCorner mesh) {
        PrintWriter file = null;
        try {
            file = new PrintWriter("result");
            file.println("set cbrange [0:200]");
            file.println("set view map\n");
            String pause = "pause "+ Settings.deltaTime +"\n";
            long count = mesh.getCount();
            for (int i = 0; i < count; ++i) {
                file.println("splot 'out/step" + i + "' with pm3d\n" + pause);
            }
            file.println("pause -1\n");
        } catch (FileNotFoundException e) {
            System.out.println("Ups. No such file.");
        } finally {
            if (file != null) {
                file.close();
            }
        }
    }
}
