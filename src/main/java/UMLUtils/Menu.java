package UMLUtils;
import java.sql.Driver;

import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * pass drawPanel to menuItem
 * the actionPerform will directly take effect on draw panel
 */
class GroupActionItem extends JMenuItem implements ActionListener {
    private DrawPanel drawPanel;

    GroupActionItem(DrawPanel drawPanel) {
        super("group");
        this.drawPanel = drawPanel;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        //System.out.println("group");
        drawPanel.group(new CompositeItem());
    }
}

class UngroupActionItem extends JMenuItem implements ActionListener {
    private DrawPanel drawPanel;

    UngroupActionItem(DrawPanel drawPanel) {
        super("ungroup");
        this.drawPanel = drawPanel;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        //System.out.println("ungroup");
        drawPanel.ungroup();
    }
}

class ButtonExit extends JButton implements ActionListener {
    private JFrame frame;

    ButtonExit(String name, JFrame frame) {
        super(name);
        this.frame = frame;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        frame.dispose();
    }

}

class ChangeObjNameItem extends JMenuItem implements ActionListener {
    private DrawPanel drawPanel;

    ChangeObjNameItem(DrawPanel drawPanel) {
        super("change name");
        this.drawPanel = drawPanel;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        //System.out.println("change name");

        JFrame frame = new JFrame("new name");

        JPanel base = new JPanel();
        JButton ok = new ButtonExit("OK", frame);
        JButton cancel = new ButtonExit("Cancel", frame);
        JTextField text = new JTextField(20);

        base.add(text);
        base.add(ok);
        base.add(cancel);

        frame.add(base);

        frame.setSize(400, 200);
        frame.setVisible(true);
    }

}

/**
 * construct menu bar structure
 */
class Menu extends JMenuBar {

    Menu(DrawPanel drawPanel) {
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        edit.add(new GroupActionItem(drawPanel));
        edit.add(new UngroupActionItem(drawPanel));
        edit.add(new ChangeObjNameItem(drawPanel));
        add(file);
        add(edit);
    }
}
