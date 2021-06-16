package UMLUtils.Canvas.ActionListener.Line;

import UMLUtils.DrawPanel;
import UMLUtils.Canvas.Line.ComposLine;

public class DrawComposLine extends DrawLine {

    @Override
    public void pressedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        super.pressedHandler(panel, e);
        if (start != null) {
            line = new ComposLine(start, start.getDirect(pressed));
        }
    }
}