package UMLUtils.Canvas.Shape;

import java.awt.Point;

import UMLUtils.Canvas.ADTBaseShape;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * base object with ports property
 */
public abstract class BaseShape extends ADTBaseShape {
    private int portSize = 8;
    protected String name = "";

    protected BaseShape(int x,int y) {
        selected = false;
        setLocation(x, y);
        setSize(90, 60);
    }

    public void setName(String name) {
        this.name = name;
    }

    Point getPort(Point point) {
        return getPort(getDirect((int)point.getX(), (int)point.getY()));
    }

    Point getPort(int x, int y) {
        return getPort(getDirect(x, y));
    }

    public Point getPort(ADTBaseShape.direct dir ) {
        if (dir == direct.LEFT) {
            return new Point(getX(), getY() + getHeight() / 2);
        }
        if (dir == direct.RIGHT) {
            return new Point(getX() + getWidth(), getY() + getHeight() / 2);
        }
        if (dir == direct.TOP) {
            return new Point(getX() + getWidth() / 2, getY());
        }
        if (dir == direct.BOTTOM) {
            return new Point(getX() + getWidth() / 2, getY() + getHeight());
        }
        return null;
    }

    public ADTBaseShape.direct getDirect(Point point) {
        return getDirect((int)point.getX(), (int)point.getY());
    }

    ADTBaseShape.direct getDirect(int x, int y) {
        double locX = (double)(x - getX());
        double locY = (double)(y - getY());
        double axisX = (double)getWidth();
        double axisY = (double)getHeight();
        boolean upRight = (locY < ((axisY / axisX) * locX));
        axisY *= (-1);
        boolean upLeft = (locY < ((axisY / axisX) * locX - axisY));
        if (upRight && upLeft) {
            return ADTBaseShape.direct.TOP;
        }
        if (upRight && !upLeft) {
            return ADTBaseShape.direct.RIGHT;
        }
        if (!upRight && upLeft) {
            return ADTBaseShape.direct.LEFT;
        }
        if (!upRight && !upLeft) {
            return ADTBaseShape.direct.BOTTOM;
        }
        return ADTBaseShape.direct.NODIRECT;
    }

    public void drawPorts(Graphics g) {
        Point ports[] = new Point[4];
        ports[0] = getPort(ADTBaseShape.direct.LEFT);
        ports[1] = getPort(ADTBaseShape.direct.RIGHT);
        ports[2] = getPort(ADTBaseShape.direct.TOP);
        ports[3] = getPort(ADTBaseShape.direct.BOTTOM);
        g.drawRect(ports[0].x - portSize, ports[0].y - portSize / 2, portSize, portSize);
        g.drawRect(ports[1].x , ports[1].y - portSize / 2, portSize, portSize);
        g.drawRect(ports[2].x - portSize / 2, ports[2].y - portSize, portSize, portSize);
        g.drawRect(ports[3].x - portSize / 2, ports[3].y, portSize, portSize);
    }

    public void drawName(Graphics g) {
        if (!name.isEmpty()) {
            g.setFont(new Font("Serif", Font.BOLD, 16));
            FontMetrics fontMetrics = g.getFontMetrics();
            int textX = getX() + (getWidth() - fontMetrics.stringWidth(name)) / 2;
            int textY = getY() + (getHeight() + fontMetrics.getAscent() - fontMetrics.getDescent()) / 2;
            g.drawString(name, textX, textY);
        }
    }

}