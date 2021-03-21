package UMLUtils;

import java.awt.*;
import javax.swing.JFrame;

//class DrawPanel extends JPanel {

    //private void doDrawing(Graphics grap) {
        //Graphics grap = getGraphics();
        //grap.setColor(Color.BLUE);
        //grap.fillRect(10, 15, 90, 60);
        //grap.fillRect(110, 15, 90, 60);

        //grap.setColor(Color.DARK_GRAY);
        //grap.fillRect(10, 75, 90, 60);
        //grap.fillOval(40, 150, 30, 30);
        //grap.fillOval(40, 200, 30, 40);
        //grap.fillOval(40, 250, 40, 30);

        //gra.fillRect(60, 300, 20, 20);
        //drawOther();
    //}

    //@Override
    //public void paintComponent(Graphics g) {
        //super.paintComponent(g);
        //doDrawing(g);
    //}
//}

//class userInfo extends JPanel implements ActionListener, MouseMotionListener {

    //private JLayeredPane layer;

    //public userInfo() {
        //setLayout(null);

        //JPanel pnl = new JPanel();
        //pnl.setLayout(null);
        //pnl.setBounds(10, 10, 200, 200);
        //pnl.setBackground(Color.CYAN);
        //add(pnl);

        //JPanel png = new JPanel();
        //png.setLayout(null);
        //png.setBounds(20, 20, 100, 100);
        //png.setBackground(Color.GRAY);
        //pnl.add(png);

        //png.setLocation(50, 50);

        //DrawPanel dpa = new DrawPanel();
        //dpa.setBounds(30, 30, 50, 50);
        //png.add(dpa);

        //png.setVisible(true);

        //utlayer layer = new utlayer();
        //add(layer);
        //layer.setLayout(null);
        //layer.setBounds(20, 250, 200, 200);
        //layer.setBounds(0, 0, 300, 300);
        //layer.setBorder(BorderFactory.createTitledBorder("Move the mouse"));
        //layer.setBackground(Color.GREEN);
        //Point origin = new Point(10, 20);
        //Color[] layerCols = { Color.yellow, Color.magenta, Color.cyan, Color.red, Color.green };
        //for (int i = 0; i < 5; i++) {
            //DrawPanel ndpa = new DrawPanel();
            //ndpa.setBounds(origin.x, origin.y, 40, 40);
            //ndpa.setBackground(layerCols[i]);
            //layer.add(ndpa, i);
            //JLabel label = new JLabel(String.valueOf(i));
            //origin.x += 10;
            //origin.y += 10;
        //}
        //layer.addMouseMotionListener(this);
    //}
//}

//class utlayer extends JLayeredPane implements MouseMotionListener {
    //public JLayeredPane layer;

    //public utlayer() {
        //this.addMouseMotionListener(this);
    //}

    //public void mouseMoved(MouseEvent e) {
    //}
    //public void mouseDragged(MouseEvent e) {
        //this.setLocation(e.getX(), e.getY());
        //System.out.println("mouse" + e.getX() + " " + e.getY());
    //}
//}

class AppUI extends JFrame {

    public AppUI () {

        // construct menu
        setJMenuBar(new AppMenu());
        AppDrawPanel adPanel = new AppDrawPanel();
        add(adPanel);
        add(new AppToolsPanel(adPanel), BorderLayout.WEST);

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