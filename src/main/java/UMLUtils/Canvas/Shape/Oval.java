package UMLUtils.Canvas.Shape;

import java.awt.Color;
import java.awt.Graphics;

/**
 * draw oval
 */
public class Oval extends BaseShape {

    public Oval(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(getX(), getY(), getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawOval(getX(), getY(), getWidth() - 1, getHeight() - 1);
        if (isSelected()) {
            drawPorts(g);
        }
        drawName(g);
    }
}