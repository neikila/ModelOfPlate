package helper;

import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by neikila on 12.04.15.
 */
public class Settings {
    public static final double width = 8;
    public static final double height = 6;
    public static final double rad = 3;
    public static final double zeroX = 100;
    public static final double zeroY = 600;

    public static final double scaleX = 90;
    public static final double scaleY = 90;

    public static double dx = 1;
    public static double dy = 1;

    public static double deltaTime = 0.1;

    public static double defaultTemperature = 0;

    public static double leftTemperature = 200;
    public static double rightTemperature = 100;
    public static double topTemperature = 100;
    public static double bottomTemperature = 50;

    public static boolean printToFile = true;

    public static int count = 25;

    public static int iterationNum;

    public static void getSet() {
        try {
            Scanner settings = new Scanner(Paths.get("settings"));

            settings.next();
            dx = settings.nextDouble();
            settings.next();
            dy = settings.nextDouble();
            settings.next();
            deltaTime = settings.nextDouble();
            settings.next();
            defaultTemperature = settings.nextDouble();
            settings.next();
            leftTemperature = settings.nextDouble();
            settings.next();
            rightTemperature = settings.nextDouble();
            settings.next();
            topTemperature = settings.nextDouble();
            settings.next();
            bottomTemperature = settings.nextDouble();
            settings.next();
            printToFile = settings.nextBoolean();

            iterationNum = (int) (count / deltaTime);

            System.out.println("dx = " + Settings.dx);
            System.out.println("dy = " + Settings.dy);
            System.out.println("deltaTime = " + Settings.deltaTime);
            System.out.println("defaultTemperature  = " + Settings.defaultTemperature);
            System.out.println("leftTemperature = " + Settings.leftTemperature);
            System.out.println("rightTemperature = " + Settings.rightTemperature);
            System.out.println("topTemperature = " + Settings.topTemperature);
            System.out.println("bottomTemperature = " + Settings.bottomTemperature);
            System.out.println("printToFile = " + Settings.printToFile);
        } catch (Exception e) {
            System.out.println("Ups. No Settings");
            System.exit(-1);
        }
    }
}