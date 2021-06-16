package UMLUtils.Canvas.ActionListener;

import UMLUtils.DrawPanel;

public interface MouseActionHandler {
    abstract void clickedHandler(DrawPanel drawPanel,  java.awt.event.MouseEvent e);
    abstract void pressedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e);
    abstract void draggedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e);
    abstract void releasedHandler(DrawPanel drawPanel, java.awt.event.MouseEvent e);
}
