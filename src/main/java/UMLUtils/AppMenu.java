package UMLUtils;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

class AppMenu extends JMenuBar {

    private Map<String, String[]> menuList = new HashMap<String, String[]>() {
    {
        put("File", new String[]{"open", "save"});
        put("Edit", new String[]{"group", "ungroup"});;
    }};

    //menuBar = new JMenuBar();
    AppMenu() {
        for (Map.Entry<String, String[]> entry : menuList.entrySet()) {
            JMenu menu = new JMenu(entry.getKey());
            System.out.println(entry.getKey());
            for (String item : entry.getValue()) {
                menu.add(new JMenuItem(item));
                System.out.println(item);
            }
            add(menu);
        }
    }
}
