package UMLUtils;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.RepaintManager;

class MovedListeren extends MouseAdapter {

    private Point pressed;

    MovedListeren() {
        pressed = new Point();
    }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent arg0) {
        // TODO Auto-generated method stub
        Component comp = arg0.getComponent();
        comp.setLocation( comp.getX() + arg0.getX() - pressed.x,
                          comp.getY() + arg0.getY() - pressed.y);

    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent arg0) {
        // TODO Auto-generated method stub
        pressed.x = arg0.getX();
        pressed.y = arg0.getY();
        
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent arg0) {
        // TODO Auto-generated method stub
        //Component comp = arg0.getComponent();
        //comp.removeMouseListener(this);
        //comp.removeMouseMotionListener(this);
        //System.out.println("release");
    }
}

class BaseItem extends JPanel {
    private boolean selected;

    public BaseItem(int x,int y) {

        selected = false;

        MovedListeren mLis = new MovedListeren();
        addMouseListener(mLis);
        addMouseMotionListener(mLis);

        //setBackground(Color.lightGray);
        setLocation(x, y);
        setSize(90, 60);
        //setBounds(10, 10, 90, 60);
        repaint();
    }

    public void setSelected() {
        selected = true;
    }

    public void setUnseleted() {
        selected = false;
    }

}

class RectItem extends BaseItem {

    RectItem(int x, int y) {
        super(x, y);
    }

    @Override
    public void paintComponent(Graphics g) {
        //System.out.println(getX() + ", " + getY());
        //System.out.println(getWidth() + ", " + getHeight());
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}

class OvalItem extends BaseItem {

    OvalItem(int x, int y) {
        super(x, y);
    }

    @Override
    public void paintComponent(Graphics g) {
        //System.out.println(getX() + ", " + getY());
        //System.out.println(getWidth() + ", " + getHeight());
        g.fillOval(0, 0, getWidth(), getHeight());
    }
}

class DrawClassListener extends MouseAdapter {

    @Override
    public void mouseClicked(java.awt.event.MouseEvent arg0) {
        // TODO Auto-generated method stub
        Object obj = arg0.getSource();
        if (obj instanceof JPanel) {
            ((JPanel)obj).add(new RectItem(arg0.getX(), arg0.getY()));
            ((JPanel)obj).repaint();
        }

        System.out.println("click");

    }
}

class DrawUcaseListener extends MouseAdapter {

    @Override
    public void mouseClicked(java.awt.event.MouseEvent arg0) {
        // TODO Auto-generated method stub
        Object obj = arg0.getSource();
        if (obj instanceof JPanel) {
            ((JPanel)obj).add(new OvalItem(arg0.getX(), arg0.getY()));
            ((JPanel)obj).repaint();
        }

        System.out.println("click");
    }
}

class AppDrawPanel extends JPanel implements ActionListener {

    private Color[] itemColors = { Color.black, Color.magenta, Color.cyan, Color.red, Color.green };
    //MouseListener listen;
    //MouseMotionListener motionListen;

    public AppDrawPanel() {
        //setSize(600, 400);

        //basePanel = new JPanel();
        setBackground(Color.white);
        setLayout(null);

        //for (Color color : itemColors) {
        //JPanel jp = new BaseItem();
            //jp.setBackground(color);
        //add(new RectItem());
        //add(new OvalItem());
        //}

        //Component[] comp = getComponents();
        //for (Component component : comp) {
            //System.out.println(component.getX() + "," + component.getY());
        //}

        // change part
        DrawClassListener dlis = new DrawClassListener();
        //listen = dlis;
        //motionListen = dlis;
        addMouseListener(dlis);
        addMouseMotionListener(dlis);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        Object obj = arg0.getSource();
        if (obj instanceof BaseButton) {
            ((BaseButton) obj).changeMode(this);
        }
    }
}
