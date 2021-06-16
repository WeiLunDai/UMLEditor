package UMLUtils.Canvas.ActionListener.Line;

import UMLUtils.DrawPanel;
import UMLUtils.Canvas.Line.AssocLine;

/**
 * override pressedHandler and allocate different line style
 */
public class DrawAssocLine extends DrawLine {

    @Override
    public void pressedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        super.pressedHandler(panel, e);
        if (start != null) {
            line = new AssocLine(start, start.getDirect(pressed));
        }
    }
}
