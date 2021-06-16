package UMLUtils.Canvas.ActionListener.Shape;

import UMLUtils.DrawPanel;
import UMLUtils.Canvas.ActionListener.DrawAdapter;
import UMLUtils.Canvas.Shape.Rect;

/**
 * add class to drawPanel
 */
public class DrawClass extends DrawAdapter {

    @Override
    public void pressedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e) {
        drawPanel.add(new Rect(e.getX(), e.getY()));
        drawPanel.repaint();
    }
}
