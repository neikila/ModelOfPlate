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
    public ElementFrame(PlateWithRoundCorner plate, MeshForPlateWithRoundCorner mesh) {
        double scaleX = 90;
        double scaleY = 90;
        add(new DrawPlateMesh(mesh, scaleX, scaleY));
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
    }
}