package UMLUtils;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.naming.SizeLimitExceededException;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

import org.ietf.jgss.GSSContext;
import org.w3c.dom.events.MouseEvent;

interface MouseHandle {
    abstract void clickedHandler(DrawPanel panel,  java.awt.event.MouseEvent e);
    abstract void pressedHandler(DrawPanel panel, java.awt.event.MouseEvent e);
    abstract void draggedHandler(DrawPanel panel, java.awt.event.MouseEvent e);
    abstract void releasedHandler(DrawPanel panel, java.awt.event.MouseEvent e);
}

abstract class BaseDraw implements MouseHandle {

    @Override
    public void clickedHandler(DrawPanel panel, java.awt.event.MouseEvent e) { }

    @Override
    public void pressedHandler(DrawPanel panel, java.awt.event.MouseEvent e) { }

    @Override
    public void draggedHandler(DrawPanel panel, java.awt.event.MouseEvent e) { }

    @Override
    public void releasedHandler(DrawPanel panel, java.awt.event.MouseEvent e) { }
}

class DrawClassAction extends BaseDraw {

    @Override
    public void pressedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        panel.add(new RectItem(e.getX(), e.getY()));
        panel.repaint();
    }
}

class DrawUcaseAction extends BaseDraw {

    @Override
    public void pressedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        panel.add(new OvalItem(e.getX(), e.getY()));
        panel.repaint();
    }
}

class DrawSelectAction extends BaseDraw {

    private Point pressed;
    private ADTBaseItem moveItem;

    @Override
    public void pressedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        pressed = e.getPoint();
        panel.unselectAll();
        moveItem = panel.findItemAt(pressed.x, pressed.y);
        System.out.println(moveItem);
        if (moveItem != null) {
            moveItem.setSelected();
        }
        panel.repaint();
        //System.out.println("SelectPressed");
    }

    @Override
    public void draggedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        if (moveItem != null) {
            int newX = moveItem.getX() + e.getX() - pressed.x;
            int newY = moveItem.getY() + e.getY() - pressed.y;
            pressed = e.getPoint();
            moveItem.setLocation(newX, newY);
            panel.repaint();
        } else {
            int x = Math.min(pressed.x, e.getX());
            int y = Math.min(pressed.y, e.getY());
            int width = Math.max(pressed.x, e.getX()) - x;
            int height = Math.max(pressed.y, e.getY()) - y;
            panel.getGraphics().clearRect(0, 0, panel.getWidth(), panel.getHeight());
            panel.paint(panel.getGraphics());
            panel.getGraphics().drawRect(x, y, width, height);
        }
    }

    @Override
    public void releasedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        int left = Math.min(pressed.x, e.getX());
        int right = Math.max(pressed.x, e.getX());
        int top = Math.min(pressed.y, e.getY());
        int bottom = Math.max(pressed.y, e.getY());
        ArrayList<ADTBaseItem> list = panel.findItemInRegion(left, right, top, bottom);
        for (ADTBaseItem adtbaseItem : list) {
            adtbaseItem.setSelected();
            System.out.println(adtbaseItem);
        }
        panel.repaint();
    }
}

abstract class DrawLineAction extends BaseDraw {
    protected Point pressed;
    protected BaseItem start;
    protected BaseItem end;
    protected BaseLineItem line;

    @Override
    public void pressedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        pressed = e.getPoint();
        start = (BaseItem)panel.findItemAt(pressed);
    }

    @Override
    public void draggedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        //panel.repaint();
        if (start != null) {
            panel.getGraphics().clearRect(0, 0, panel.getWidth(), panel.getHeight());
            panel.paint(panel.getGraphics());
            line.draw(panel.getGraphics(), e.getX(), e.getY());
        }
    }

    @Override
    public void releasedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        end = (BaseItem)panel.findItemAt(e.getX(), e.getY());
        if (start != null && end != null && start != end) {
            line.setEnd(end, end.getDirect(e.getPoint()));
            panel.add(line);
        }
        panel.repaint();
    }
}

class DrawAssocLineAction extends DrawLineAction {

    @Override
    public void pressedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        super.pressedHandler(panel, e);
        line = new AssocLineItem(start, start.getDirect(pressed));
    }
}

class DrawGenerLineAction extends DrawLineAction {

    @Override
    public void pressedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        super.pressedHandler(panel, e);
        line = new GenerLineItem(start, start.getDirect(pressed));
    }
}

class DrawComposLineAction extends DrawLineAction {

    @Override
    public void pressedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        super.pressedHandler(panel, e);
        line = new ComposLineItem(start, start.getDirect(pressed));
    }
}