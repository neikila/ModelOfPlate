package main;

import frames.ElementFrame;
import javax.swing.*;
import java.awt.*;

/**
 * Created by neikila on 10.04.15.
 */
public class main {
    public static void main(String[] args) {
        System.out.println("Start!");
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ElementFrame frame = new ElementFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

