package UMLUtils.Canvas.ActionListener.Shape;

import UMLUtils.DrawPanel;
import UMLUtils.Canvas.ActionListener.DrawAdapter;
import UMLUtils.Canvas.Shape.Oval;

/**
 * add use case to drawPanel
 */
public class DrawUcase extends DrawAdapter {

    @Override
    public void pressedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e) {
        drawPanel.add(new Oval(e.getX(), e.getY()));
        drawPanel.repaint();
    }
}
