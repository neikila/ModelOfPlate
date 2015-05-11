package main;

import elements.PlateWithRoundCorner;
import helper.Settings;
import phisics.MeshForPlateWithRoundCorner;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by neikila on 10.04.15.
 */
public class main {
    public static void main(String[] args) {
        System.out.println("Start!");

        final PlateWithRoundCorner plate = new PlateWithRoundCorner(Settings.width, Settings.height, Settings.rad, Settings.zeroX, Settings.zeroY);
        final MeshForPlateWithRoundCorner mesh = new MeshForPlateWithRoundCorner(plate, Settings.dx, Settings.dy, Settings.defaultTemperature);
        int iterationNum = (int)(((double)Settings.count / 1000 ) / Settings.deltaTime);
        System.out.println("IterationNum = " +  iterationNum);
        for (int i = 0; i < iterationNum; ++i) {
            mesh.iteration(Settings.deltaTime, Settings.leftTemperature, Settings.rightTemperature, Settings.topTemperature, Settings.bottomTemperature);
        }
        printToFile(mesh);

//        EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                ElementFrame frame = new ElementFrame(plate, mesh);
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.setVisible(true);
//            }
//        });
    }

    private static void printToFile(MeshForPlateWithRoundCorner mesh) {
        PrintWriter file = null;
        try {
            file = new PrintWriter("LaborataryWorkForModellingSystem/result");
            file.println("set view map\n");
            String pause = "pause "+ Settings.deltaTime +"\n";
            long count = 2500;// mesh.getCount();
            for (int i = 0; i < count; ++i) {
                file.println("splot 'out/step" + i + "' with pm3d\n" + pause);
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
