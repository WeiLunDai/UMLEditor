package UMLUtils;

import java.awt.*;
import java.util.ArrayList;

interface MouseActionHandler {
    abstract void clickedHandler(DrawPanel drawPanel,  java.awt.event.MouseEvent e);
    abstract void pressedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e);
    abstract void draggedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e);
    abstract void releasedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e);
}

/**
 * Adapter for MouseActionHandler interface
 */
abstract class BaseDraw implements MouseActionHandler {

    @Override
    public void clickedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e) { }

    @Override
    public void pressedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e) { }

    @Override
    public void draggedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e) { }

    @Override
    public void releasedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e) { }
}

/**
 * add class to drawPanel
 */
class DrawClassAction extends BaseDraw {

    @Override
    public void pressedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e) {
        drawPanel.add(new RectItem(e.getX(), e.getY()));
        drawPanel.repaint();
    }
}

/**
 * add use case to drawPanel
 */
class DrawUcaseAction extends BaseDraw {

    @Override
    public void pressedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e) {
        drawPanel.add(new OvalItem(e.getX(), e.getY()));
        drawPanel.repaint();
    }
}

/**
 * handler select action
 */
class DrawSelectAction extends BaseDraw {

    private Point pressed;
    private ADTBaseItem moveItem;

    /**
     * moveItem must be set at pressedHandler
     */
    @Override
    public void pressedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e) {
        pressed = e.getPoint();
        drawPanel.unselectAll();
        moveItem = drawPanel.findItemAt(pressed.x, pressed.y);
        //System.out.println(moveItem);
        if (moveItem != null) {
            moveItem.setSelected();
        }
        drawPanel.repaint();
    }

    /**
     * If moveItem be set as null, then draw a rectangle from pressed point to mouse location
     * otherwise, a object will be moved to mouse location
     */
    @Override
    public void draggedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e) {
        if (moveItem != null) {
            int newX = moveItem.getX() + e.getX() - pressed.x;
            int newY = moveItem.getY() + e.getY() - pressed.y;
            pressed = e.getPoint();
            moveItem.setLocation(newX, newY);
            drawPanel.repaint();
        } else {
            int x = Math.min(pressed.x, e.getX());
            int y = Math.min(pressed.y, e.getY());
            int width = Math.max(pressed.x, e.getX()) - x;
            int height = Math.max(pressed.y, e.getY()) - y;
            drawPanel.getGraphics().clearRect(0, 0, drawPanel.getWidth(), drawPanel.getHeight());
            drawPanel.paint(drawPanel.getGraphics());
            drawPanel.getGraphics().drawRect(x, y, width, height);
        }
    }

    /**
     * drag item do no action
     * only handle selected region action
     */
    @Override
    public void releasedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e) {
        if (moveItem == null) {
            int left = Math.min(pressed.x, e.getX());
            int right = Math.max(pressed.x, e.getX());
            int top = Math.min(pressed.y, e.getY());
            int bottom = Math.max(pressed.y, e.getY());
            ArrayList<ADTBaseItem> list = drawPanel.findItemInRegion(left, right, top, bottom);
            for (ADTBaseItem adtbaseItem : list) {
                adtbaseItem.setSelected();
                //System.out.println(adtbaseItem);
            }
        }
        drawPanel.repaint();
    }
}

/**
 * draw only a line
 * 
 * begin from pressed point, if there is a adtbaseItem
 * end at released point
 * If begin and end both have a adtbaseItem, then create a line object
 */
abstract class DrawLineAction extends BaseDraw {
    protected Point pressed;
    protected ADTBaseItem start;
    protected ADTBaseItem end;
    protected BaseLineItem line;

    /**
     * start may miss at sometime
     */
    @Override
    public void pressedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e) {
        pressed = e.getPoint();
        start = drawPanel.findItemAt(pressed);
    }

    @Override
    public void draggedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e) {
        if (start != null) {
            drawPanel.getGraphics().clearRect(0, 0, drawPanel.getWidth(), drawPanel.getHeight());
            drawPanel.paint(drawPanel.getGraphics());
            line.draw(drawPanel.getGraphics(), e.getX(), e.getY());
        }
        //System.out.println("dragged");
    }

    @Override
    public void releasedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        //System.out.println("release");
        end = panel.findItemAt(e.getX(), e.getY());
        if (start != null && end != null && start != end) {
            line.setEnd(end, end.getDirect(e.getPoint()));
            panel.add(line);
        }
        panel.repaint();
    }
}

/**
 * override pressedHandler and allocate different line style
 */
class DrawAssocLineAction extends DrawLineAction {

    @Override
    public void pressedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        super.pressedHandler(panel, e);
        if (start != null) {
            line = new AssocLineItem(start, start.getDirect(pressed));
        }
    }
}

class DrawGenerLineAction extends DrawLineAction {

    @Override
    public void pressedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        super.pressedHandler(panel, e);
        if (start != null) {
            line = new GenerLineItem(start, start.getDirect(pressed));
        }
    }
}

class DrawComposLineAction extends DrawLineAction {

    @Override
    public void pressedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        super.pressedHandler(panel, e);
        if (start != null) {
            line = new ComposLineItem(start, start.getDirect(pressed));
        }
    }
}