package UMLUtils;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

/**
 * All UML object will be created on this panel including 
 * composite, class, use case, and line with different arrow head
 */
class DrawPanel extends JLayeredPane implements MouseListener, MouseMotionListener{
    private int depth = 0;
    private MouseActionHandler mouseActionHandler;
    private ArrayList<ADTBaseItem> adtBaseItems = new ArrayList<ADTBaseItem>();
    private ArrayList<BaseLineItem> baseLineItems = new ArrayList<BaseLineItem>();

    DrawPanel() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * Mainly to be used by button action listener
     * @param handle
     */
    public void changeMode(MouseActionHandler mouseActionHandler) {
        unselectAll();
        repaint();
        this.mouseActionHandler = mouseActionHandler;
    }

    public void changeName(String name) {
        for (ADTBaseItem adtBaseItem : adtBaseItems) {
            if (adtBaseItem.isSelected()) {
                adtBaseItem.setName(name);
            }
        }
    }

    /**
     * Group all selected object
     * This function will move all selected object into composite object
     * @param compositeItem 
     */
    public void group(CompositeItem compositeItem) {
        ArrayList<ADTBaseItem> list = new ArrayList<ADTBaseItem>();
        for (ADTBaseItem adtBaseItem : adtBaseItems) {
            if (adtBaseItem.isSelected()) {
                list.add(adtBaseItem);
            }
        }
        if (list.isEmpty()) {
            return;
        }

        adtBaseItems.removeAll(list);
        compositeItem.attachAll(list);

        adtBaseItems.add(compositeItem);
        compositeItem.setSelected();
        repaint();
    }

    /**
     * add all BaseItem in selected object which is composite object to adtBaseItems list
     * and
     * remove composite from drawPanel
     */
    public void ungroup() {
        ArrayList<ADTBaseItem> list = new ArrayList<ADTBaseItem>();
        ArrayList<ADTBaseItem> removeList = new ArrayList<ADTBaseItem>();
        for (ADTBaseItem adtBaseItem : adtBaseItems) {
            if (adtBaseItem.isSelected()) {
                ArrayList<ADTBaseItem> tmp = adtBaseItem.detachAll();
                if (tmp != null) {
                    list.addAll(tmp);
                    removeList.add(adtBaseItem);
                }

            }
        }

        adtBaseItems.removeAll(removeList);
        adtBaseItems.addAll(list);
        unselectAll();
        repaint();
    }

    /**
     * composite object will not be add by this method
     * So the highest layer is BaseItem 
     * @param baseItem
     */
    public void add(BaseItem baseItem) {
        adtBaseItems.add(baseItem);
        add(baseItem, depth);
        setLayer(baseItem, depth);
        depth++;
    }

    public void add(BaseLineItem baseLineItem) {
        baseLineItems.add(baseLineItem);
    }

    public void unselectAll() {
        for (ADTBaseItem adtbaseItem : adtBaseItems) {
            if (adtbaseItem.isSelected()) {
                adtbaseItem.setUnselected();
            }
        }
    }

    private boolean isItemContain(ADTBaseItem adtBaseItem, int x, int y) {
        int left = adtBaseItem.getX();
        int top = adtBaseItem.getY();
        int bottom = top + adtBaseItem.getHeight();
        int right = left + adtBaseItem.getWidth();
        return left < x && x < right && top < y && y < bottom;
    }

    public ADTBaseItem findItemAt(Point point) {
        return findItemAt((int)point.getX(), (int)point.getY());
    }

    /**
     * find the highest layer adtBaseitem at (x, y)
     * @param x
     * @param y
     * @return
     */
    public ADTBaseItem findItemAt(int x, int y) {
        int maxDepth = -30000;
        ADTBaseItem ret = null;
        for (ADTBaseItem adtbaseItem : adtBaseItems) {
            if (isItemContain(adtbaseItem, x, y)) {
                if (maxDepth < getLayer(adtbaseItem)) {
                    maxDepth = getLayer(adtbaseItem);
                    ret = adtbaseItem;
                }
            }
        }
        return ret;
    }

    private boolean isItemInRegion(ADTBaseItem adtBaseItem, int left, int right, int top, int bottom) {
        return left < adtBaseItem.getX() &&
               top < adtBaseItem.getY() && 
               adtBaseItem.getX() + adtBaseItem.getWidth() < right &&
               adtBaseItem.getY() + adtBaseItem.getHeight() < bottom;
    }

    public ArrayList<ADTBaseItem> findItemInRegion(int left, int right, int top, int bottom) {
        ArrayList<ADTBaseItem> list = new ArrayList<ADTBaseItem>();
        for (ADTBaseItem adtbaseItem : adtBaseItems) {
            if (isItemInRegion(adtbaseItem, left, right, top, bottom)) {
                list.add(adtbaseItem);
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
        for (ADTBaseItem adtbaseItem : adtBaseItems) {
            adtbaseItem.draw(g);
        }
        for (BaseLineItem baseLineItem: baseLineItems) {
            baseLineItem.draw(g);
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