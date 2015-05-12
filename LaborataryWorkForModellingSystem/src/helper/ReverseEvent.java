package helper;

import drawElements.DrawPlateMesh;

import javax.swing.*;

/**
 * Created by neikila on 12.05.15.
 */
public class ReverseEvent {
    JButton button = null;
    JButton button2 = null;

    private boolean pulsing = false;

    public ReverseEvent() {
    }

    public void setObjects(JButton button, JButton button2) {
        this.button = button;
        this.button2 = button2;
    }

    public void buttonOnePressed(DrawPlateMesh meshComponent) {
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

    public void buttonTwoPressed(DrawPlateMesh meshComponent) {
        meshComponent.restart();
        button.setText("Start");
        pulsing = false;
    }

    public void finished() {
        if (button != null) {
            button.setText("Finished");
        }
    }
}
