package UMLUtils.Canvas.ActionListener.Line;

import java.awt.Point;

import UMLUtils.DrawPanel;
import UMLUtils.Canvas.ADTBaseShape;
import UMLUtils.Canvas.ActionListener.DrawAdapter;
import UMLUtils.Canvas.Line.BaseLine;

/**
 * draw only a line
 * 
 * begin from pressed point, if there is a adtbaseItem
 * end at released point
 * If begin and end both have a adtbaseItem, then create a line object
 */
public abstract class DrawLine extends DrawAdapter {
    protected Point pressed;
    protected ADTBaseShape start;
    protected ADTBaseShape end;
    protected BaseLine line;

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
        if (start != null && line != null) {
            drawPanel.getGraphics().clearRect(0, 0, drawPanel.getWidth(), drawPanel.getHeight());
            drawPanel.paint(drawPanel.getGraphics());
            line.draw(drawPanel.getGraphics(), e.getX(), e.getY());
        }
    }

    @Override
    public void releasedHandler(DrawPanel panel, java.awt.event.MouseEvent e) {
        end = panel.findItemAt(e.getX(), e.getY());
        if (start != null && end != null && start != end) {
            line.setEnd(end, end.getDirect(e.getPoint()));
            panel.add(line);
        }
        panel.repaint();
    }
}