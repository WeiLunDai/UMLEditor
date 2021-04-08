package UMLUtils;

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
        drawPanel.ungroup();
    }
}

class AskFrame extends JFrame implements ActionListener {
    private DrawPanel drawPanel;
    private JTextField textField = new JTextField(20);
    private JPanel panel = new JPanel();
    private JButton ok = new JButton("OK");
    private JButton cancel = new JButton("cancel");

    AskFrame (DrawPanel drawPanel) {
        this.drawPanel = drawPanel;
        ok.setActionCommand("ok");
        ok.addActionListener(this);
        cancel.setActionCommand("cancel");
        cancel.addActionListener(this);

        panel.add(textField);
        panel.add(ok);
        panel.add(cancel);

        add(panel);

        setSize(400, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String action = arg0.getActionCommand();
        if (action.equals("ok")) {
            drawPanel.changeName(textField.getText());
            drawPanel.repaint();
        }
        dispose();
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
        new AskFrame(drawPanel);
    }
}

/**
 * construct menu bar structure
 */
class Menu extends JMenuBar {
    JMenu file = new JMenu("File");
    JMenu edit = new JMenu("Edit");

    Menu(DrawPanel drawPanel) {
        edit.add(new GroupActionItem(drawPanel));
        edit.add(new UngroupActionItem(drawPanel));
        edit.add(new ChangeObjNameItem(drawPanel));
        add(file);
        add(edit);
    }
}
