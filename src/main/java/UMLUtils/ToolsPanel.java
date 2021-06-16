package UMLUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import UMLUtils.Canvas.ActionListener.DrawSelect;
import UMLUtils.Canvas.ActionListener.MouseActionHandler;
import UMLUtils.Canvas.ActionListener.Line.DrawAssocLine;
import UMLUtils.Canvas.ActionListener.Line.DrawComposLine;
import UMLUtils.Canvas.ActionListener.Line.DrawGenerLine;
import UMLUtils.Canvas.ActionListener.Shape.DrawClass;
import UMLUtils.Canvas.ActionListener.Shape.DrawUcase;

class ToolsButton extends JButton implements ActionListener {
    private ToolsPanel.Modes mode;
    private ToolsPanel toolsPanel;
    public ToolsButton (ToolsPanel.Modes mode, ToolsPanel ref) {
        this.mode = mode;
        this.toolsPanel = ref;
        setIcon(new ImageIcon(mode.getPath()));
        setBorder(BorderFactory.createEmptyBorder());
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        toolsPanel.setMode( mode );
    }
}

/**
 * tools panel predefine with enum class
 * bind image path with button 
 * bind button changeMode to some MouseActionHandler
 */
public class ToolsPanel extends JPanel {
    public enum Modes{
        SELECT("src/images/newSelect.png", "src/images/newSelectCHS.png", new DrawSelect(), 0),
        ASSOC("src/images/newAssoc.png", "src/images/newAssocCHS.png", new DrawAssocLine(), 1),
        GENER("src/images/newGener.png", "src/images/newGenerCHS.png", new DrawGenerLine(), 2),
        COMPOS("src/images/newCompos.png", "src/images/newComposCHS.png", new DrawComposLine(), 3),
        CLASS("src/images/newClass.png", "src/images/newClassCHS.png", new DrawClass(), 4),
        UCASE("src/images/newUcase.png", "src/images/newUcaseCHS.png", new DrawUcase(), 5);

        Modes(String path, String chsPath, MouseActionHandler handler, int index) {
            this.path = path;
            this.chsPath = chsPath;
            this.handle = handler;
            this.index = index;
        }
        private final String path;
        private final String chsPath;
        private final MouseActionHandler handle;
        private final int index;

        public String getPath() {
            return this.path;
        }

        public String getChsPath() {
            return this.chsPath;
        }

        public int getIndex() {
            return this.index;
        }

        public MouseActionHandler getHandler() {
            return this.handle;
        }
    }

    //private int status;
    private Modes mode;
    private DrawPanel drawPanel;
    private ArrayList<JButton> buttons = new ArrayList<JButton>(); 

    public void setMode(Modes newMode) {
        if (newMode == mode) {
            return;
        }

        JButton button;
        for (Modes modes : Modes.values()) {
            if (modes == mode) {
                button = buttons.get(modes.getIndex());
                button.setIcon(new ImageIcon(modes.getPath()));
            }
            if (modes == newMode) {
                button = buttons.get(modes.getIndex());
                button.setIcon(new ImageIcon(modes.getChsPath()));
                drawPanel.changeMode(modes.getHandler());
            }
        }
        mode = newMode;
    }

    /**
     * initialize tools panel and bind relation
     * we set class button action as default
     * @param drawPanel
     */
    ToolsPanel(DrawPanel drawPanel) {
        this.drawPanel = drawPanel;
        this.mode = Modes.CLASS;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (Modes modes: Modes.values()) {
            ToolsButton toolsButton = new ToolsButton(modes, this);
            buttons.add(modes.getIndex(), toolsButton);
            add(toolsButton);
        }
        setMode(Modes.SELECT);
    }
}
