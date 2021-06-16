package UMLUtils.Canvas;

import java.util.ArrayList;
import java.awt.Graphics;

/**
 * a object contains more adtbaseItem
 * constructe once by attachAll
 * destructe once by detachAll
 * 
 * the region of composite will not be reset
 */
public class CompositeItem extends ADTBaseShape {
    ArrayList<ADTBaseShape> member = new ArrayList<ADTBaseShape>();
    private int left = Integer.MAX_VALUE;
    private int right = 0;
    private int top = Integer.MAX_VALUE;
    private int bottom = 0;

    public void setSelected() {
        selected = true;
        for (ADTBaseShape adtBaseItem : member) {
            adtBaseItem.setUnselected();
        }
        //System.out.println("set selected");
    }

    public void attachAll(ArrayList<ADTBaseShape> adtBaseItems) {
        member.addAll(adtBaseItems);
        for (ADTBaseShape adtBaseItem : member) {
            left = Math.min(left, adtBaseItem.getX());
            right = Math.max(right, adtBaseItem.getX() + adtBaseItem.getWidth());
            top = Math.min(top, adtBaseItem.getY());
            bottom = Math.max(bottom, adtBaseItem.getY() + adtBaseItem.getHeight());
        }
        /* !remember we already override setLocation */
        super.setLocation(left, top);
        setSize(right - left, bottom - top);
    }

    public ArrayList<ADTBaseShape> detachAll() {
        return member;
    }

    @Override
    public void setLocation(int x, int y) {
        int offsetX = x - getX();
        int offsetY = y - getY();
        left += offsetX;
        right += offsetX;
        top += offsetY;
        bottom += offsetY;
        super.setLocation(x, y);
        for (ADTBaseShape adtBaseItem : member) {
            adtBaseItem.setLocation(adtBaseItem.getX() + offsetX, adtBaseItem.getY() + offsetY);
        }
    }

    @Override
    public void draw(Graphics g) {
        for (ADTBaseShape adtBaseItem : member) {
            adtBaseItem.draw(g);
        }
        if (isSelected()) {
            g.drawRect(left, top, right - left, bottom - top);
        }
    }
}