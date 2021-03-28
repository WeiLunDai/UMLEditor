package UMLUtils;

import java.awt.*;
import javax.swing.JFrame;

/**
 * Main windows entry point 
 */
class AppUI extends JFrame {

    public AppUI () {

        /**
         * add three major object to frame
         */
        DrawPanel drawPanel = new DrawPanel();
        add(drawPanel);
        setJMenuBar(new Menu(drawPanel));
        add(new ToolsPanel(drawPanel), BorderLayout.WEST);

        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main( String[] args )
    {
        new AppUI();
        //System.out.println( "Hello World!" );
    }
}