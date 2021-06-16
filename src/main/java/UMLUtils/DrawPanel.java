package UMLUtils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import UMLUtils.Canvas.ADTBaseShape;
import UMLUtils.Canvas.CompositeItem;
import UMLUtils.Canvas.ActionListener.MouseActionHandler;
import UMLUtils.Canvas.Line.BaseLine;
import UMLUtils.Canvas.Shape.BaseShape;

/**
 * All UML object will be created on this panel including 
 * composite, class, use case, and line with different arrow head
 */
public class DrawPanel extends JLayeredPane implements MouseListener, MouseMotionListener{
    private int depth = 0;
    private MouseActionHandler mouseActionHandler;
    private ArrayList<ADTBaseShape> adtBaseShapes = new ArrayList<ADTBaseShape>();
    private ArrayList<BaseLine> baseLines = new ArrayList<BaseLine>();

    public DrawPanel() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * Mainly to be used by button action listener
     * @param mouseActionHandler
     */
    public void changeMode(MouseActionHandler mouseActionHandler) {
        unselectAll();
        repaint();
        this.mouseActionHandler = mouseActionHandler;
    }

    public void changeName(String name) {
        for (ADTBaseShape adtBaseShape: adtBaseShapes) {
            if (adtBaseShape.isSelected()) {
                adtBaseShape.setName(name);
            }
        }
    }

    /**
     * Group all selected object
     * This function will move all selected object into composite object
     * 
     */
    public void group() {
        ArrayList<ADTBaseShape> list = new ArrayList<ADTBaseShape>();
        for (ADTBaseShape adtBaseShape : adtBaseShapes) {
            if (adtBaseShape.isSelected()) {
                list.add(adtBaseShape);
            }
        }
        if (list.isEmpty()) {
            return;
        }

        CompositeItem compositeItem = new CompositeItem();
        adtBaseShapes.removeAll(list);
        compositeItem.attachAll(list);

        adtBaseShapes.add(compositeItem);
        compositeItem.setSelected();
        repaint();
    }

    /**
     * add all BaseItem in selected object which is composite object to adtBaseShape list
     * and
     * remove composite from drawPanel
     */
    public void ungroup() {
        ArrayList<ADTBaseShape> list = new ArrayList<ADTBaseShape>();
        ArrayList<ADTBaseShape> removeList = new ArrayList<ADTBaseShape>();
        for (ADTBaseShape adtBaseShape : adtBaseShapes) {
            if (adtBaseShape.isSelected()) {
                ArrayList<ADTBaseShape> tmp = adtBaseShape.detachAll();
                if (tmp != null) {
                    list.addAll(tmp);
                    removeList.add(adtBaseShape);
                }

            }
        }

        adtBaseShapes.removeAll(removeList);
        adtBaseShapes.addAll(list);
        unselectAll();
        repaint();
    }

    /**
     * composite object will not be add by this method
     * So the highest layer is BaseItem 
     * @param baseItem
     */
    public void add(BaseShape baseItem) {
        adtBaseShapes.add(baseItem);
        add(baseItem, depth);
        setLayer(baseItem, depth);
        depth++;
    }

    public void add(BaseLine baseLine) {
        baseLines.add(baseLine);
    }

    public void unselectAll() {
        for (ADTBaseShape adtBaseShape : adtBaseShapes) {
            if (adtBaseShape.isSelected()) {
                adtBaseShape.setUnselected();
            }
        }
    }

    private boolean isItemContain(ADTBaseShape adtBaseShape, int x, int y) {
        int left = adtBaseShape.getX();
        int top = adtBaseShape.getY();
        int bottom = top + adtBaseShape.getHeight();
        int right = left + adtBaseShape.getWidth();
        return left < x && x < right && top < y && y < bottom;
    }

    /**
     * find the highest layer adtBaseitem at (x, y)
     * @param x
     * @param y
     * @return
     */
    public ADTBaseShape findItemAt(int x, int y) {
        int maxDepth = -30000;
        ADTBaseShape ret = null;
        for (ADTBaseShape adtBaseShape: adtBaseShapes) {
            if (isItemContain(adtBaseShape, x, y)) {
                if (maxDepth < getLayer(adtBaseShape)) {
                    maxDepth = getLayer(adtBaseShape);
                    ret = adtBaseShape;
                }
            }
        }
        return ret;
    }


    public ADTBaseShape findItemAt(Point point) {
        return findItemAt((int)point.getX(), (int)point.getY());
    }

    private boolean isItemInRegion(ADTBaseShape adtBaseShape, int left, int right, int top, int bottom) {
        return left < adtBaseShape.getX() &&
               top < adtBaseShape.getY() && 
               adtBaseShape.getX() + adtBaseShape.getWidth() < right &&
               adtBaseShape.getY() + adtBaseShape.getHeight() < bottom;
    }

    public ArrayList<ADTBaseShape> findItemInRegion(int left, int right, int top, int bottom) {
        ArrayList<ADTBaseShape> list = new ArrayList<ADTBaseShape>();
        for (ADTBaseShape adtBaseShape : adtBaseShapes) {
            if (isItemInRegion(adtBaseShape, left, right, top, bottom)) {
                list.add(adtBaseShape);
            }
        }
        return list;
    }

    /**
     * manual draw all adtbaseItem with its own draw function
     * also for line object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        for (ADTBaseShape adtBaseShape : adtBaseShapes) {
            adtBaseShape.draw(g);
        }
        for (BaseLine baseLine: baseLines) {
            baseLine.draw(g);
        }
    }

    /**
     * The following are mouse action listener
     * They will be handler by MouseActionHandler interface
     */
    @Override
    public void mouseDragged(MouseEvent arg0) {
        mouseActionHandler.draggedHandler(this, arg0);
    }


    @Override
    public void mouseClicked(MouseEvent arg0) {
        mouseActionHandler.clickedHandler(this, arg0);
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        mouseActionHandler.pressedHandler(this, arg0);
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        mouseActionHandler.releasedHandler(this, arg0);
    }

    @Override
    public void mouseEntered(MouseEvent arg0) { }

    @Override
    public void mouseMoved(MouseEvent arg0) { }

    @Override
    public void mouseExited(MouseEvent arg0) { }

}