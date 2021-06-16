package UMLUtils.Canvas.Line;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

import UMLUtils.Canvas.ADTBaseShape;

/**
 * draw general line head
 */
public class GenerLine extends BaseLine {

    public GenerLine(ADTBaseShape start, ADTBaseShape.direct startDirect) {
        super(start, startDirect);
    }

    @Override
    protected void drawArrow(Graphics g, int endX, int endY) {
        Point startPoint = start.getPort(startDirect);
        Graphics2D g2 = (Graphics2D)g;
        Polygon polygon = new Polygon();
        AffineTransform tx1 = g2.getTransform();
        AffineTransform tx2 = (AffineTransform)tx1.clone();
        double theta = Math.atan2(endY - startPoint.y, endX - startPoint.x);

        polygon.addPoint(0, 0);
        polygon.addPoint(-7, -14);
        polygon.addPoint(7, -14);

        tx2.translate(endX, endY);
        tx2.rotate(theta - Math.PI / 2);

        g2.setTransform(tx2);
        g2.setColor(Color.WHITE);
        g2.fill(polygon);
        g2.setColor(Color.BLACK);
        g2.draw(polygon);
        g2.setTransform(tx1);
    }

}