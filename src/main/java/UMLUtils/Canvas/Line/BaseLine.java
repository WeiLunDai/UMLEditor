package UMLUtils.Canvas.Line;

import java.awt.Graphics;
import java.awt.Point;

import UMLUtils.Canvas.ADTBaseShape;

/**
 * abstract line base item
 * must be initialized with a start point
 */
public abstract class BaseLine {
    protected ADTBaseShape start;
    protected ADTBaseShape.direct startDirect;
    protected ADTBaseShape end;
    protected ADTBaseShape.direct endDirect;

    protected BaseLine(ADTBaseShape start, ADTBaseShape.direct startDirect) {
        this.start = start;
        this.startDirect = startDirect;
        setEnd(null, null);
    }

    protected abstract void drawArrow(Graphics g, int endX, int endY);

    public void setEnd(ADTBaseShape end, ADTBaseShape.direct endDirect) {
        this.end = end;
        this.endDirect = endDirect;
    }

    /**
     * draw with a end point
     * @param g
     * @param endX
     * @param endY
     */
    public void draw(Graphics g, int endX, int endY) {
        if (start != null && startDirect != ADTBaseShape.direct.NODIRECT) {
            Point point = start.getPort(startDirect);
            g.drawLine(point.x, point.y, endX, endY);
            drawArrow(g, endX, endY);
        }
    }

    /**
     * draw with end object
     * If no end object exist, then do nothing
     * @param g
     */
    public void draw(Graphics g) {
        if (start != null && startDirect != ADTBaseShape.direct.NODIRECT &&
            end != null && endDirect != ADTBaseShape.direct.NODIRECT) {
            Point endPoint = end.getPort(endDirect);
            draw(g, endPoint.x, endPoint.y);
            drawArrow(g, endPoint.x, endPoint.y);
        }
    }
}


