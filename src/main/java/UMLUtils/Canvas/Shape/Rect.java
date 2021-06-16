package UMLUtils.Canvas.Shape;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * draw rectangle
 */
public class Rect extends BaseShape {

    public Rect(int x, int y) {
        super(x, y);
        setSize(90, 90);
    }

    @Override
    public void drawName(Graphics g) {
        if (!name.isEmpty()) {
            g.setFont(new Font("Serif", Font.BOLD, 16));
            FontMetrics fontMetrics = g.getFontMetrics();
            int textX = getX() + (getWidth() - fontMetrics.stringWidth(name)) / 2;
            int textY = getY() + (getHeight() / 3 + fontMetrics.getAscent() - fontMetrics.getDescent()) / 2;
            g.drawString(name, textX, textY);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(getX(), getY(), getWidth(), getHeight() / 3);
        g.setColor(Color.BLACK);
        g.drawRect(getX(), getY() + getHeight() / 3, getWidth() - 1, getHeight() / 3);
        g.drawRect(getX(), getY(), getWidth() - 1, getHeight() - 1);
        if (isSelected()) {
            drawPorts(g);
        }
        drawName(g);
    }
}
