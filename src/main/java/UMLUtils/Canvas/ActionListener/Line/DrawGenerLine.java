package UMLUtils.Canvas.ActionListener.Line;

import UMLUtils.DrawPanel;
import UMLUtils.Canvas.Line.GenerLine;

public class DrawGenerLine extends DrawLine {

    @Override
    public void pressedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        super.pressedHandler(panel, e);
        if (start != null) {
            line = new GenerLine(start, start.getDirect(pressed));
        }
    }
}
