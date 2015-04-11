package frames;

import drawElements.DrawPlateMesh;
import elements.PlateWithRoundCorner;
import phisics.MeshForPlateWithRoundCorner;

import javax.swing.*;
import java.awt.*;

/**
 * Created by neikila on 10.04.15.
 */
public class ElementFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 300;

    public ElementFrame() {
        double scaleX = 90;
        double scaleY = 90;
        PlateWithRoundCorner plate = new PlateWithRoundCorner(8, 6, 3, 0, 100, 600);
        double defaultTemperature = 0;
        MeshForPlateWithRoundCorner mesh = new MeshForPlateWithRoundCorner(plate, 0.2, 0.2, defaultTemperature);
        add(new DrawPlateMesh(mesh, scaleX, scaleY));
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
    }
}