package UMLUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * abstruct data type for both composite and baseItem
 * some precolating here with a predefined empty function
 */
abstract class ADTBaseItem extends JPanel {
    protected boolean selected;

    public enum direct {
        NODIRECT,LEFT,RIGHT,TOP,BOTTOM;
    }

    Boolean isSelected() {
        return this.selected;
    }

    void setSelected() {
        selected = true;
        //System.out.println("set selected");
    }

    void setUnselected() {
        selected = false;
        //System.out.println("set unselected");
    }

    ADTBaseItem.direct getDirect(Point point) {return ADTBaseItem.direct.NODIRECT;}

    Point getPort(ADTBaseItem.direct dir) {return null;}

    void attachAll(ADTBaseItem item) { }

    ArrayList<ADTBaseItem> detachAll() { return null; }

    /**
     * empty method, this paint will be handler by draw method
     * and do repaint by drawPanel
     */
    public void paintComponent(Graphics g) { }

    abstract void draw(Graphics g);
}

/**
 * a object contains more adtbaseItem
 * constructe once by attachAll
 * destructe once by detachAll
 * 
 * the region of composite will not be reset
 */
class CompositeItem extends ADTBaseItem {
    ArrayList<ADTBaseItem> member = new ArrayList<ADTBaseItem>();
    private int left = Integer.MAX_VALUE;
    private int right = 0;
    private int top = Integer.MAX_VALUE;
    private int bottom = 0;

    void setSelected() {
        selected = true;
        for (ADTBaseItem adtBaseItem : member) {
            adtBaseItem.setUnselected();
        }
        //System.out.println("set selected");
    }

    void attachAll(ArrayList<ADTBaseItem> adtBaseItems) {
        member.addAll(adtBaseItems);
        for (ADTBaseItem adtBaseItem : member) {
            left = Math.min(left, adtBaseItem.getX());
            right = Math.max(right, adtBaseItem.getX() + adtBaseItem.getWidth());
            top = Math.min(top, adtBaseItem.getY());
            bottom = Math.max(bottom, adtBaseItem.getY() + adtBaseItem.getHeight());
        }
        /* !remember we already override setLocation */
        super.setLocation(left, top);
        setSize(right - left, bottom - top);
    }

    ArrayList<ADTBaseItem> detachAll() {
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

/**
 * base object with ports property
 */
abstract class BaseItem extends ADTBaseItem {
    private int portSize = 8;
    protected String name = "";

    protected BaseItem(int x,int y) {
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

    Point getPort(ADTBaseItem.direct dir ) {
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

    ADTBaseItem.direct getDirect(Point point) {
        return getDirect((int)point.getX(), (int)point.getY());
    }

    ADTBaseItem.direct getDirect(int x, int y) {
        double locX = (double)(x - getX());
        double locY = (double)(y - getY());
        double axisX = (double)getWidth();
        double axisY = (double)getHeight();
        boolean upRight = (locY < ((axisY / axisX) * locX));
        axisY *= (-1);
        boolean upLeft = (locY < ((axisY / axisX) * locX - axisY));
        if (upRight && upLeft) {
            return ADTBaseItem.direct.TOP;
        }
        if (upRight && !upLeft) {
            return ADTBaseItem.direct.RIGHT;
        }
        if (!upRight && upLeft) {
            return ADTBaseItem.direct.LEFT;
        }
        if (!upRight && !upLeft) {
            return ADTBaseItem.direct.BOTTOM;
        }
        return ADTBaseItem.direct.NODIRECT;
    }

    public void drawPorts(Graphics g) {
        Point ports[] = new Point[4];
        ports[0] = getPort(ADTBaseItem.direct.LEFT);
        ports[1] = getPort(ADTBaseItem.direct.RIGHT);
        ports[2] = getPort(ADTBaseItem.direct.TOP);
        ports[3] = getPort(ADTBaseItem.direct.BOTTOM);
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

/**
 * draw rectangle
 */
class RectItem extends BaseItem {

    RectItem(int x, int y) {
        super(x, y);
        setSize(90, 90);
    }

    @Override
    public void drawName(Graphics g) {
        if (!name.isEmpty()) {
            g.setFont(new Font("Serif", Font.BOLD, 16));
            FontMetrics fontMetrics = g.getFontMetrics();
            int textX = getX() + (getWidth() - fontMetrics.stringWidth(name)) / 2;
            int textY = getY() + (getHeight() / 3 + fontMetrics.getAscent() - fontMetrics.getDescent()) / 2;
            g.drawString(name, textX, textY);
        }
    }

    @Override
    void draw(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(getX(), getY(), getWidth(), getHeight() / 3);
        g.setColor(Color.BLACK);
        g.drawRect(getX(), getY() + getHeight() / 3, getWidth() - 1, getHeight() / 3);
        g.drawRect(getX(), getY(), getWidth() - 1, getHeight() - 1);
        if (isSelected()) {
            drawPorts(g);
        }
        drawName(g);
    }
}

/**
 * draw oval
 */
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
        drawName(g);
    }
}

/**
 * abstract line base item
 * must be initialized with a start point
 */
abstract class BaseLineItem {
    protected ADTBaseItem start;
    protected ADTBaseItem.direct startDirect;
    protected ADTBaseItem end;
    protected ADTBaseItem.direct endDirect;

    BaseLineItem(ADTBaseItem start, ADTBaseItem.direct startDirect) {
        this.start = start;
        this.startDirect = startDirect;
        setEnd(null, null);
    }

    abstract void drawArrow(Graphics g, int endX, int endY);

    void setEnd(ADTBaseItem end, ADTBaseItem.direct endDirect) {
        this.end = end;
        this.endDirect = endDirect;
    }

    /**
     * draw with a end point
     * @param g
     * @param endX
     * @param endY
     */
    void draw(Graphics g, int endX, int endY) {
        if (start != null && startDirect != ADTBaseItem.direct.NODIRECT) {
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
    void draw(Graphics g) {
        if (start != null && startDirect != ADTBaseItem.direct.NODIRECT &&
            end != null && endDirect != ADTBaseItem.direct.NODIRECT) {
            Point endPoint = end.getPort(endDirect);
            draw(g, endPoint.x, endPoint.y);
            drawArrow(g, endPoint.x, endPoint.y);
        }
    }
}

/**
 * draw associative line head
 */
class AssocLineItem extends BaseLineItem {

    public AssocLineItem(ADTBaseItem start, ADTBaseItem.direct startDirect) {
        super(start, startDirect);
    }

    @Override
    void drawArrow(Graphics g, int endX, int endY) {
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

/**
 * draw general line head
 */
class GenerLineItem extends BaseLineItem {

    GenerLineItem(ADTBaseItem start, ADTBaseItem.direct startDirect) {
        super(start, startDirect);
    }

    @Override
    void drawArrow(Graphics g, int endX, int endY) {
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

/**
 * draw composite line head
 */
class ComposLineItem extends BaseLineItem {

    ComposLineItem(ADTBaseItem start, ADTBaseItem.direct startDirect) {
        super(start, startDirect);
    }

    @Override
    void drawArrow(Graphics g, int endX, int endY) {
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