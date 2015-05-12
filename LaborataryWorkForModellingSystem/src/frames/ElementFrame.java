package frames;

import drawElements.DrawPlateMesh;
import elements.PlateWithRoundCorner;
import helper.ReverseEvent;
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

        final ReverseEvent reverseEvent = new ReverseEvent();

        final DrawPlateMesh meshComponent = new DrawPlateMesh(mesh, scaleX, scaleY, reverseEvent);
        meshComponent.setLocation(100, 100);
        panel.add(meshComponent);

        final JButton button = new JButton("Start");
        final JButton button2 = new JButton("Restart");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                reverseEvent.buttonOnePressed(meshComponent);
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reverseEvent.buttonTwoPressed(meshComponent);
            }
        });

        reverseEvent.setObjects(button, button2);

        panel.add(button, BorderLayout.SOUTH);
        panel.add(button2, BorderLayout.NORTH);

        setLocationRelativeTo(null);
        setExtendedState(Frame.MAXIMIZED_BOTH);
    }
}