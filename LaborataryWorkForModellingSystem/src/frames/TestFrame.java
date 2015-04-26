package frames;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by neikila on 12.04.15.
 */
public class TestFrame extends JFrame{
/*
    GeneralPath gp = new GeneralPath();
    gp.
*/

    public TestFrame() {

        add(new JComponent() {

            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;

                Path2D path = new Path2D.Double();

                double x0 = 600;
                double y0 = 400;

                double width = 100;
                double height = 100;

                double radius = 100;

//                path.moveTo(x0 + 20, y0);
//                path.lineTo(x0 - radius, y0);

                double x1 = x0 + radius * Math.cos(Math.PI * 5/6);
                double y1 = y0 - radius * Math.sin(Math.PI * 5/6);
                double x2 = x0 + radius * Math.cos(Math.PI * 4/6);
                double y2 = y0 - radius * Math.sin(Math.PI * 4/6);
                double x3 = x0 + radius * Math.cos(Math.PI * 3/6);
                double y3 = y0 - radius * Math.sin(Math.PI * 3/6);
//                g2.draw(new Ellipse2D.Double(x1, y1, 5, 5));
//                g2.draw(new Ellipse2D.Double(x2, y2, 5, 5));
//                g2.draw(new Ellipse2D.Double(x3, y3, 5, 5));

//                path.curveTo(x1, y1, x2, y2, x3, y3);
//                path.quadTo(x1,y1, x2, y2);

//                path.lineTo(x0 - radius, y0 + 40);
//                path.lineTo(x0 + 20, y0 + 40);


//                path.lineTo(x0 + 10, y0);

                Point2D first = new Point2D.Double(x0 - 30, y0);
                Point2D second = new Point2D.Double(x0 - width - 30, y0 - width);

                Rectangle2D rect = new Rectangle2D.Double();
                rect.setFrameFromDiagonal(second, first);

                path.append(rect, true);

                path.closePath();
//                x0 -= width;

//                Arc2D arc = new Arc2D.Double(x0 - radius, y0 - radius, radius * 2, radius * 2, 90 * 1, 90, Arc2D.PIE);
                Arc2D arc = new Arc2D.Double();
                arc.setArcByTangent(second, new Point2D.Double(x0 - 2 * radius, y0 - radius), new Point2D.Double(x0 - 2 * radius, y0), radius);
                arc.setArcType(Arc2D.PIE);
//                arc.setAngleStart(new Point2D.Double(x0 - width, y0));
//                arc.setAngles(second, new Point2D.Double(x0 - 2 * radius, y0));
                g2.draw(arc);
                g2.setPaint(new Color(0, 255, 255, 100));
                g2.fill(arc);

                g2.setPaint(new Color(255, 255, 0, 255));
//                path.append(arc, true);

//                g2.fill(path);
//                g2.draw(path);
            }
        });
        setLocationRelativeTo(null);
        setExtendedState(Frame.MAXIMIZED_BOTH);
    }
}
