package UMLUtils;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

abstract class BaseButton extends JButton {
    void reset(JPanel panel) {
        MouseListener[] list = panel.getMouseListeners();
        for (MouseListener mouseListener : list) {
            panel.removeMouseListener(mouseListener);
        }
        MouseMotionListener[] motionList = panel.getMouseMotionListeners();
        for (MouseMotionListener mouseMotionListener : motionList) {
            panel.removeMouseMotionListener(mouseMotionListener);
        }
    }
    abstract void changeMode(JPanel panel);
}

class SelectButton extends BaseButton {

    @Override
    void changeMode(JPanel panel) {
        // TODO Auto-generated method stub
        System.out.println("Select");
    }

}

class AssocButton extends BaseButton {

    @Override
    void changeMode(JPanel panel) {
        // TODO Auto-generated method stub
        System.out.println("Assoc");
        
    }

}

class GenerButton extends BaseButton {

    @Override
    void changeMode(JPanel panel) {
        // TODO Auto-generated method stub
        System.out.println("Gener");
        
    }

}

class ComposButton extends BaseButton {

    @Override
    void changeMode(JPanel panel) {
        // TODO Auto-generated method stub
        System.out.println("Compos");
        
    }

}

class ClassButton extends BaseButton {

    @Override
    void changeMode(JPanel panel) {
        // TODO Auto-generated method stub
        System.out.println("Class");
        reset(panel);
        DrawClassListener listen = new DrawClassListener();
        panel.addMouseListener(listen);
        panel.addMouseMotionListener(listen);
    }
}

class UcaseButton extends BaseButton {

    @Override
    void changeMode(JPanel panel) {
        // TODO Auto-generated method stub
        System.out.println("Ucase");
        reset(panel);
        DrawUcaseListener listen = new DrawUcaseListener();
        panel.addMouseListener(listen);
        panel.addMouseMotionListener(listen);
        
    }

}

class AppToolsPanel extends JPanel {
    public enum Tools{
        SELECT("src/images/select.png", new SelectButton()),
        ASSOC("src/images/association.png", new AssocButton()),
        GENER("src/images/generalization.png", new GenerButton()),
        COMPOS("src/images/composition.png", new ComposButton()),
        CLASS("src/images/class.png", new ClassButton()),
        UCASE("src/images/use case.png", new UcaseButton());

        Tools(String path, JButton button) {
            this.path = path;
            this.button = button;
        }
        private final String path;
        private final JButton button;

        public String getPath() {
            return this.path;
        }

        public JButton getButton() {
            return this.button;
        }
    }

    AppToolsPanel(AppDrawPanel panel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (Tools tools: Tools.values()) {
            //System.out.println(tools.getPath());
            if (tools.getButton() instanceof BaseButton) {
                BaseButton baseButton = (BaseButton)tools.getButton();
                baseButton.setIcon(new ImageIcon(tools.getPath()));
                baseButton.addActionListener(panel);
                baseButton.setBorder(BorderFactory.createEmptyBorder());;
                baseButton.changeMode(panel);
                add(baseButton);
            }
        }
    }
}
