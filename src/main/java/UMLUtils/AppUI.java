package UMLUtils;

import java.awt.*;
import javax.swing.JFrame;

class AppUI extends JFrame {

    public AppUI () {

        // construct menu
        DrawPanel adPanel = new DrawPanel();
        add(adPanel);
        setJMenuBar(new Menu(adPanel));
        add(new ToolsPanel(adPanel), BorderLayout.WEST);

        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main( String[] args )
    {
        new AppUI();
        System.out.println( "Hello World!" );
    }
}