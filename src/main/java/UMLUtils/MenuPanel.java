package UMLUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
abstract class MenuActionItem extends JMenuItem implements ActionListener {
    protected DrawPanel drawPanel;

    MenuActionItem(DrawPanel drawPanel, String name) {
        super(name);
        this.drawPanel = drawPanel;
        addActionListener(this);
    }
}


class GroupActionItem extends MenuActionItem {
    GroupActionItem(DrawPanel drawPanel) {
        super(drawPanel, "group");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        drawPanel.group();
    }
}

class UngroupActionItem extends MenuActionItem {

    UngroupActionItem(DrawPanel drawPanel) {
        super(drawPanel, "ungroup");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        drawPanel.ungroup();
    }
}

class ChangeObjNameItem extends MenuActionItem {
    ChangeObjNameItem(DrawPanel drawPanel) {
        super(drawPanel, "change name");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        new AskFrame(drawPanel);
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

/**
 * construct menu bar structure
 */
public class MenuPanel extends JMenuBar {
    private JMenu file = new JMenu("File");
    private JMenu edit = new JMenu("Edit");

    MenuPanel(DrawPanel drawPanel) {
        edit.add(new GroupActionItem(drawPanel));
        edit.add(new UngroupActionItem(drawPanel));
        edit.add(new ChangeObjNameItem(drawPanel));
        add(file);
        add(edit);
    }
}
