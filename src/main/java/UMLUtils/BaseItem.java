package UMLUtils;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JPanel;

abstract class ADTBaseItem extends JPanel {
    protected boolean selected;
    protected boolean group;

    Boolean isGroup() {
        return this.group;
    }

    Boolean isSelected() {
        return this.selected;
    }

    void setSelected() {
        selected = true;
        System.out.println("set selected");
    }

    void setUnselected() {
        selected = false;
        System.out.println("set unselected");
    }

    public void paintComponent(Graphics g) { }

    abstract void draw(Graphics g);
}

class CompositeItem extends ADTBaseItem {
    ArrayList<ADTBaseItem> member = new ArrayList<ADTBaseItem>();
    private int left = 999;
    private int right = 0;
    private int top = 999;
    private int bottom = 0;

    CompositeItem() {
        this.group = true;
    }

    void setSelected() {
        selected = true;
        for (ADTBaseItem adtBaseItem : member) {
            adtBaseItem.setUnselected();
        }
        System.out.println("set selected");
    }

    void attach(ADTBaseItem item) {
        member.add(item);
        left = Math.min(left, item.getX());
        right = Math.max(right, item.getX() + item.getWidth());
        top = Math.min(top, item.getY());
        bottom = Math.max(bottom, item.getY() + item.getHeight());
        super.setLocation(left, top);
        setSize(right - left, bottom - top);
    }

    ArrayList<ADTBaseItem> detach() {
        return member;
    }

    @Override
    public void setLocation(int x, int y) {
        int offsetX = x - getX();
        int offsetY = y - getY();
        left += offsetX;
        right += offsetX;
        top += offsetY;
        bottom += offsetY;
        super.setLocation(x, y);
        for (ADTBaseItem adtBaseItem : member) {
            adtBaseItem.setLocation(adtBaseItem.getX() + offsetX, adtBaseItem.getY() + offsetY);
        }
    }

    @Override
    void draw(Graphics g) {
        for (ADTBaseItem adtBaseItem : member) {
            adtBaseItem.draw(g);
        }
        if (isSelected()) {
            g.drawRect(left, top, right - left, bottom - top);
        }
    }
}

abstract class BaseItem extends ADTBaseItem {
    private int portSize = 8;

    public enum direct {
        LEFT,RIGHT,TOP,BOTTOM;
    }

    protected BaseItem(int x,int y) {
        group = false;
        selected = false;
        setLocation(x, y);
        setSize(90, 60);
    }

    Point getPort(Point point) {
        return getPort(getDirect((int)point.getX(), (int)point.getY()));
    }

    Point getPort(int x, int y) {
        return getPort(getDirect(x, y));
    }

    Point getPort(direct dir) {
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

    BaseItem.direct getDirect(Point point) {
        return getDirect((int)point.getX(), (int)point.getY());
    }

    BaseItem.direct getDirect(int x, int y) {
        double locX = (double)(x - getX());
        double locY = (double)(y - getY());
        double axisX = (double)getWidth();
        double axisY = (double)getHeight();
        boolean upRight = (locY < ((axisY / axisX) * locX));
        axisY *= (-1);
        boolean upLeft = (locY < ((axisY / axisX) * locX - axisY));
        if (upRight && upLeft) {
            return BaseItem.direct.TOP;
        }
        if (upRight && !upLeft) {
            return BaseItem.direct.RIGHT;
        }
        if (!upRight && upLeft) {
            return BaseItem.direct.LEFT;
        }
        if (!upRight && !upLeft) {
            return BaseItem.direct.BOTTOM;
        }
        return null;
    }

    public void drawPorts(Graphics g) {
        Point ports[] = new Point[4];
        ports[0] = getPort(BaseItem.direct.LEFT);
        ports[1] = getPort(BaseItem.direct.RIGHT);
        ports[2] = getPort(BaseItem.direct.TOP);
        ports[3] = getPort(BaseItem.direct.BOTTOM);
        g.drawRect(ports[0].x - portSize, ports[0].y - portSize / 2, portSize, portSize);
        g.drawRect(ports[1].x , ports[1].y - portSize / 2, portSize, portSize);
        g.drawRect(ports[2].x - portSize / 2, ports[2].y - portSize, portSize, portSize);
        g.drawRect(ports[3].x - portSize / 2, ports[3].y, portSize, portSize);
    }

}

class RectItem extends BaseItem {

    RectItem(int x, int y) {
        super(x, y);
    }

    @Override
    void draw(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(getX(), getY(), getWidth() - 1, getHeight() - 1);
        if (isSelected()) {
            drawPorts(g);
        }
    }
}

class OvalItem extends BaseItem {

    OvalItem(int x, int y) {
        super(x, y);
    }

    @Override
    void draw(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(getX(), getY(), getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawOval(getX(), getY(), getWidth() - 1, getHeight() - 1);
        if (isSelected()) {
            drawPorts(g);
        }
    }
}

abstract class BaseLineItem {
    protected BaseItem start;
    protected BaseItem.direct startDirect;
    protected BaseItem end;
    protected BaseItem.direct endDirect;
    protected int arrowSize = 20;
    protected double arrowAngle = Math.PI / 6;

    BaseLineItem(BaseItem start, BaseItem.direct startDirect) {
        this.start = start;
        this.startDirect = startDirect;
        setEnd(null, null);
    }

    abstract void drawArrow(Graphics g, int endX, int endY);

    void setEnd(BaseItem item, BaseItem.direct direct) {
        this.end = item;
        this.endDirect = direct;
    }

    void draw(Graphics g, int endX, int endY) {
        Point point = start.getPort(startDirect);
        g.drawLine(point.x, point.y, endX, endY);
        drawArrow(g, endX, endY);
    }

    void draw(Graphics g) {
        if (end != null) {
            Point endPoint = end.getPort(endDirect);
            draw(g, endPoint.x, endPoint.y);
            drawArrow(g, endPoint.x, endPoint.y);
        }
    }
}

class AssocLineItem extends BaseLineItem {

    public AssocLineItem(BaseItem start, BaseItem.direct startDirect) {
        super(start, startDirect);
    }

    @Override
    void drawArrow(Graphics g, int endX, int endY) {
        Point startPoint = start.getPort(startDirect);

        double vecX = startPoint.x - endX;
        double vecY = startPoint.y - endY;
        vecX = vecX * Math.cos(arrowAngle) - vecY * Math.sin(arrowAngle);
        vecY = vecX * Math.sin(arrowAngle) + vecY * Math.cos(arrowAngle);
        double multip = arrowSize / Math.pow(Math.pow(vecX, 2) + Math.pow(vecY, 2), 0.5);
        vecX *= multip;
        vecY *= multip;
        g.drawLine(endX, endY, endX + (int)vecX, endY + (int)vecY);

        vecX = startPoint.x - endX;
        vecY = startPoint.y - endY;
        vecX = vecX * Math.cos(arrowAngle) + vecY * Math.sin(arrowAngle);
        vecY = vecY * Math.cos(arrowAngle) - vecX * Math.sin(arrowAngle);
        multip = arrowSize / Math.pow(Math.pow(vecX, 2) + Math.pow(vecY, 2), 0.5);
        vecX *= multip;
        vecY *= multip;
        g.drawLine(endX, endY, endX + (int)vecX, endY + (int)vecY);

    }
}

class GenerLineItem extends BaseLineItem {

    GenerLineItem(BaseItem start, BaseItem.direct startDirect) {
        super(start, startDirect);
    }

    @Override
    void drawArrow(Graphics g, int endX, int endY) {
        Point startPoint = start.getPort(startDirect);

        double lvecX = startPoint.x - endX;
        double lvecY = startPoint.y - endY;
        lvecX = lvecX * Math.cos(arrowAngle) - lvecY * Math.sin(arrowAngle);
        lvecY = lvecX * Math.sin(arrowAngle) + lvecY * Math.cos(arrowAngle);
        double multip = arrowSize / Math.pow(Math.pow(lvecX, 2) + Math.pow(lvecY, 2), 0.5);
        lvecX *= multip;
        lvecY *= multip;
        g.drawLine(endX, endY, endX + (int)lvecX, endY + (int)lvecY);

        double rvecX = startPoint.x - endX;
        double rvecY = startPoint.y - endY;
        rvecX = rvecX * Math.cos(arrowAngle) + rvecY * Math.sin(arrowAngle);
        rvecY = rvecY * Math.cos(arrowAngle) - rvecX * Math.sin(arrowAngle);
        multip = arrowSize / Math.pow(Math.pow(rvecX, 2) + Math.pow(rvecY, 2), 0.5);
        rvecX *= multip;
        rvecY *= multip;
        g.drawLine(endX, endY, endX + (int)rvecX, endY + (int)rvecY);

        g.drawLine(endX + (int)lvecX, endY + (int)lvecY, endX + (int)rvecX, endY + (int)rvecY);
    }

}

class ComposLineItem extends BaseLineItem {

    ComposLineItem(BaseItem start, BaseItem.direct startDirect) {
        super(start, startDirect);
    }

    @Override
    void drawArrow(Graphics g, int endX, int endY) {
        Point startPoint = start.getPort(startDirect);

        double lvecX = startPoint.x - endX;
        double lvecY = startPoint.y - endY;
        lvecX = lvecX * Math.cos(arrowAngle) - lvecY * Math.sin(arrowAngle);
        lvecY = lvecX * Math.sin(arrowAngle) + lvecY * Math.cos(arrowAngle);
        double multip = arrowSize / Math.pow(Math.pow(lvecX, 2) + Math.pow(lvecY, 2), 0.5);
        lvecX *= multip;
        lvecY *= multip;
        g.drawLine(endX, endY, endX + (int)lvecX, endY + (int)lvecY);

        double rvecX = startPoint.x - endX;
        double rvecY = startPoint.y - endY;
        rvecX = rvecX * Math.cos(arrowAngle) + rvecY * Math.sin(arrowAngle);
        rvecY = rvecY * Math.cos(arrowAngle) - rvecX * Math.sin(arrowAngle);
        multip = arrowSize / Math.pow(Math.pow(rvecX, 2) + Math.pow(rvecY, 2), 0.5);
        rvecX *= multip;
        rvecY *= multip;
        g.drawLine(endX, endY, endX + (int)rvecX, endY + (int)rvecY);

        int tmpX = endX + (int)lvecX;
        int tmpY = endY + (int)lvecY;
        g.drawLine(tmpX, tmpY, tmpX + (int)rvecX, tmpY + (int)rvecY);
        tmpX = endX + (int)rvecX;
        tmpY = endY + (int)rvecY;

        g.drawLine(tmpX, tmpY, tmpX + (int)lvecX, tmpY + (int)lvecY);
    }
}