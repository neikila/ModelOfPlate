package frames;

import drawElements.DrawPlateMesh;
import elements.PlateWithRoundCorner;
import helper.Settings;
import phisics.MeshForPlateWithRoundCorner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by neikila on 10.04.15.
 */
public class ElementFrame extends JFrame {
    public ElementFrame(PlateWithRoundCorner plate, final MeshForPlateWithRoundCorner mesh) {
        double scaleX = Settings.scaleX;
        double scaleY = Settings.scaleY;

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.black)));
        setContentPane(panel);

        final DrawPlateMesh meshComponent = new DrawPlateMesh(mesh, scaleX, scaleY);
        meshComponent.setLocation(100, 100);
        panel.add(meshComponent);

        final JButton button = new JButton("Start");
        button.addActionListener(new ActionListener() {
            private boolean pulsing = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pulsing) {
                    pulsing = false;
                    meshComponent.stop();
                    button.setText("Start");
                } else {
                    pulsing = true;
                    meshComponent.start();
                    button.setText("Stop");
                }
            }
        });
        panel.add(button, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setExtendedState(Frame.MAXIMIZED_BOTH);
    }
}