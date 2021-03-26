package UMLUtils;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

class DrawPanel extends JLayeredPane implements MouseListener, MouseMotionListener{
    private int depth = 0;
    private MouseHandle handle;
    private ArrayList<ADTBaseItem> items = new ArrayList<ADTBaseItem>();
    private ArrayList<BaseLineItem> lines = new ArrayList<BaseLineItem>();

    DrawPanel() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void changeMode(MouseHandle handle) {
        unselectAll();
        repaint();
        this.handle = handle;
    }

    public void group(CompositeItem item) {
        ArrayList<ADTBaseItem> list = new ArrayList<ADTBaseItem>();
        for (ADTBaseItem adtBaseItem : items) {
            if (adtBaseItem.isSelected()) {
                System.out.println(adtBaseItem);
                list.add(adtBaseItem);
                //items.remove(adtBaseItem);
            }
        }

        for (ADTBaseItem adtBaseItem : list) {
                System.out.println(adtBaseItem);
           items.remove(adtBaseItem);
        }

        for (ADTBaseItem adtBaseItem : list) {
                System.out.println(adtBaseItem);
            item.attach(adtBaseItem);
        }
        items.add(item);
        item.setSelected();
        repaint();
    }

    public void ungroup() {
        ArrayList<ADTBaseItem> list = new ArrayList<ADTBaseItem>();
        ArrayList<ADTBaseItem> removeList = new ArrayList<ADTBaseItem>();
        for (ADTBaseItem adtBaseItem : items) {
            if (adtBaseItem.isSelected() && adtBaseItem.isGroup()) {
                list.addAll( ((CompositeItem)adtBaseItem).detach() );
                removeList.add(adtBaseItem);
            }
        }
        for (ADTBaseItem adtBaseItem : removeList) {
            items.remove(adtBaseItem);
        }
        items.addAll(list);
        unselectAll();
        repaint();
    }

    public void add(BaseItem item) {
        items.add(item);
        add(item, depth);
        setLayer(item, depth);
        depth++;
    }

    public void add(BaseLineItem line) {
        lines.add(line);
    }

    public void unselectAll() {
        for (ADTBaseItem adtbaseItem : items) {
            if (adtbaseItem.isSelected()) {
                adtbaseItem.setUnselected();
            }
        }
    }

    private boolean isItemContain(ADTBaseItem item, int x, int y) {
        int left = item.getX();
        int top = item.getY();
        int bottom = top + item.getHeight();
        int right = left + item.getWidth();
        return left < x && x < right && top < y && y < bottom;
    }

    public ADTBaseItem findItemAt(Point point) {
        return findItemAt((int)point.getX(), (int)point.getY());
    }

    public ADTBaseItem findItemAt(int x, int y) {
        int maxDepth = -30000;
        ADTBaseItem ret = null;
        for (ADTBaseItem adtbaseItem : items) {
            if (isItemContain(adtbaseItem, x, y)) {
                if (maxDepth < getLayer(adtbaseItem)) {
                    maxDepth = getLayer(adtbaseItem);
                    ret = adtbaseItem;
                }
            }
        }
        return ret;
    }

    private boolean isItemInRegion(ADTBaseItem item, int left, int right, int top, int bottom) {
        return left < item.getX() &&
               top < item.getY() && 
               item.getX() + item.getWidth() < right &&
               item.getY() + item.getHeight() < bottom;
    }

    public ArrayList<ADTBaseItem> findItemInRegion(int left, int right, int top, int bottom) {
        ArrayList<ADTBaseItem> list = new ArrayList<ADTBaseItem>();
        for (ADTBaseItem adtbaseItem : items) {
            if (isItemInRegion(adtbaseItem, left, right, top, bottom)) {
                list.add(adtbaseItem);
            }
        }
        return list;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (ADTBaseItem adtbaseItem : items) {
            adtbaseItem.draw(g);
        }
        for (BaseLineItem baseLineItem: lines) {
            baseLineItem.draw(g);
        }
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        handle.draggedHandler(this, arg0);
    }


    @Override
    public void mouseClicked(MouseEvent arg0) {
        handle.clickedHandler(this, arg0);
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        handle.pressedHandler(this, arg0);
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        handle.releasedHandler(this, arg0);
    }

    @Override
    public void mouseEntered(MouseEvent arg0) { }

    @Override
    public void mouseMoved(MouseEvent arg0) { }

    @Override
    public void mouseExited(MouseEvent arg0) { }

}