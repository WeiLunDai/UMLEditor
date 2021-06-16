package UMLUtils.Canvas.ActionListener;

import UMLUtils.DrawPanel;

/**
 * Adapter for MouseActionHandler interface
 */
public abstract class DrawAdapter implements MouseActionHandler {

    @Override
    public void clickedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e) { }

    @Override
    public void pressedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e) { }

    @Override
    public void draggedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e) { }

    @Override
    public void releasedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e) { }
}
