package UMLUtils;

import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

class GroupActionItem extends JMenuItem implements ActionListener {
    private DrawPanel panel;

    GroupActionItem(DrawPanel panel) {
        super("group");
        this.panel = panel;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        System.out.println("group");
        panel.group(new CompositeItem());
    }
}

class UngroupActionItem extends JMenuItem implements ActionListener {
    private DrawPanel panel;

    UngroupActionItem(DrawPanel panel) {
        super("ungroup");
        this.panel = panel;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        System.out.println("ungroup");
        panel.ungroup();
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
    private DrawPanel panel;

    ChangeObjNameItem(DrawPanel panel) {
        super("change name");
        this.panel = panel;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        System.out.println("change name");

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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}

class Menu extends JMenuBar {

    Menu(DrawPanel panel) {
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        edit.add(new GroupActionItem(panel));
        edit.add(new UngroupActionItem(panel));
        edit.add(new ChangeObjNameItem(panel));
        add(file);
        add(edit);
    }
}
