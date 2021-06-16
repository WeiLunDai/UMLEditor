package UMLUtils.Canvas.ActionListener;

import java.awt.Point;
import java.util.ArrayList;

import UMLUtils.DrawPanel;
import UMLUtils.Canvas.ADTBaseShape;

/**
 * handler select action
 */
public class DrawSelect extends DrawAdapter {

    private Point pressed;
    private ADTBaseShape moveItem;

    /**
     * moveItem must be set at pressedHandler
     */
    @Override
    public void pressedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e) {
        pressed = e.getPoint();
        drawPanel.unselectAll();
        moveItem = drawPanel.findItemAt(pressed.x, pressed.y);
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
            ArrayList<ADTBaseShape> list = drawPanel.findItemInRegion(left, right, top, bottom);
            for (ADTBaseShape adtbaseItem : list) {
                adtbaseItem.setSelected();
                //System.out.println(adtbaseItem);
            }
        }
        drawPanel.repaint();
    }
}
