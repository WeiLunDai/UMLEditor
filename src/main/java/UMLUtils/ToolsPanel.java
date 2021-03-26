package UMLUtils;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

class ToolsButton extends JButton implements ActionListener {
    private int status;
    private ToolsPanel panel;
    public ToolsButton (int status, ToolsPanel ref) {
        this.status = status;
        this.panel = ref;
        addActionListener(this);
    }
    public int getStatus() {
        return status;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        panel.setStatus( getStatus() );
    }
}

class ToolsPanel extends JPanel {
    public enum Tools{
        SELECT("src/images/select.png", new DrawSelectAction(), 0),
        ASSOC("src/images/association.png", new DrawAssocLineAction(), 1),
        GENER("src/images/generalization.png", new DrawGenerLineAction(), 2),
        COMPOS("src/images/composition.png", new DrawComposLineAction(), 3),
        CLASS("src/images/class.png", new DrawClassAction(), 4),
        UCASE("src/images/use case.png", new DrawUcaseAction(), 5);

        Tools(String path, MouseHandle handler, int index) {
            this.path = path;
            this.handle = handler;
            this.index = index;
        }
        private final String path;
        private final MouseHandle handle;
        private final int index;

        public String getPath() {
            return this.path;
        }

        public int getIndex() {
            return this.index;
        }

        public MouseHandle getHandler() {
            return this.handle;
        }
    }

    private int status;
    private DrawPanel panel;
    private ArrayList<JButton> buttons = new ArrayList<JButton>(); 

    public void setStatus(int status) {
        this.status = status;
        for (Tools tools : Tools.values()) {
            if (tools.getIndex() == status) {
                panel.changeMode(tools.getHandler());
            }
        }
    }

    public int getStatus() {
        return status;
    }

    ToolsPanel(DrawPanel panel) {
        this.panel = panel;
        this.status = Tools.CLASS.getIndex();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (Tools tools: Tools.values()) {
            System.out.println(tools.getIndex());
            System.out.println(tools.getPath());

            ToolsButton tmp = new ToolsButton(tools.getIndex(), this);
            tmp.setIcon(new ImageIcon(tools.getPath()));
            tmp.addActionListener(tmp);
            tmp.setBorder(BorderFactory.createEmptyBorder());
            buttons.add(tools.getIndex(), tmp);
            add(tmp);
        }
        panel.changeMode(Tools.CLASS.getHandler());
    }
}
