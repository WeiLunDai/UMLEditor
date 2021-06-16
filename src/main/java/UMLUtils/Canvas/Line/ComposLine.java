package UMLUtils.Canvas.Line;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

import UMLUtils.Canvas.ADTBaseShape;

/**
 * draw composite line head
 */
public class ComposLine extends BaseLine {

    public ComposLine(ADTBaseShape start, ADTBaseShape.direct startDirect) {
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
        polygon.addPoint(-6, -12);
        polygon.addPoint(0, -24);
        polygon.addPoint(6, -12);

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