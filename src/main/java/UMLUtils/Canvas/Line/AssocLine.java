package UMLUtils.Canvas.Line;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import UMLUtils.Canvas.ADTBaseShape;

/**
 * draw associative line head
 */
public class AssocLine extends BaseLine {

    public AssocLine(ADTBaseShape start, ADTBaseShape.direct startDirect) {
        super(start, startDirect);
    }

    @Override
    protected void drawArrow(Graphics g, int endX, int endY) {
        Point startPoint = start.getPort(startDirect);
        Graphics2D g2 = (Graphics2D)g;
        AffineTransform tx1 = g2.getTransform();
        AffineTransform tx2 = (AffineTransform)tx1.clone();
        double theta = Math.atan2(endY - startPoint.y, endX - startPoint.x);

        tx2.translate(endX, endY);
        tx2.rotate(theta - Math.PI / 2);

        g2.setTransform(tx2);
        g2.drawLine(0, 0, -7, -14);
        g2.drawLine(0, 0, 7, -14);
        g2.setTransform(tx1);

    }
}