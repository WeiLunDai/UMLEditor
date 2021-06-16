package UMLUtils.Canvas;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * abstruct data type for both composite and baseItem
 * some precolating here with a predefined empty function
 */
public abstract class ADTBaseShape extends JPanel {
    protected boolean selected;

    public enum direct {
        NODIRECT,LEFT,RIGHT,TOP,BOTTOM;
    }

    public Boolean isSelected() {
        return this.selected;
    }

    public void setSelected() {
        selected = true;
        //System.out.println("set selected");
    }

    public void setUnselected() {
        selected = false;
        //System.out.println("set unselected");
    }

    public ADTBaseShape.direct getDirect(Point point) { return ADTBaseShape.direct.NODIRECT; }

    public Point getPort(ADTBaseShape.direct dir) { return null; }

    void attachAll(ADTBaseShape item) { }

    public ArrayList<ADTBaseShape> detachAll() { return null; }

    /**
     * empty method, this paint will be handler by draw method
     * and do repaint by drawPanel
     */
    public void paintComponent(Graphics g) { }

    public abstract void draw(Graphics g);
}
