package UMLUtils;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

/**
 * Main windows entry point 
 */
class AppUI extends JFrame {
    private DrawPanel drawPanel = new DrawPanel();
    private JMenuBar menuBar = new Menu(drawPanel);
    private ToolsPanel toolsPanel = new ToolsPanel(drawPanel);

    public AppUI () {

        /**
         * add three major object to frame
         */
        add(drawPanel);
        setJMenuBar(menuBar);
        add(toolsPanel, BorderLayout.WEST);

        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main( String[] args )
    {
        new AppUI();
    }
}