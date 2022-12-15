import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Map2 extends JFrame implements MouseListener, ActionListener {
    // number of cities/states analyzed from data
    private final int numberOfStates = 50;
    // array stores city/state names
    private String[] name = new String[numberOfStates];
    // array that stores cities' cost of living indices
    private double[] cost = new double[numberOfStates];

    private double max; // finds maximum cost of living index
    private double min; // finds minimum cost of living index
    // frame to place all GUI components from frame onto
    private JFrame f = new JFrame("Cost of Living Index in US Map");
    private JPanel panel = new JPanel(); // panel to add GUI components to
    private JLabel information; // label that outputs information on city
    // symbol table for city/state name (String) and cost index (Double)
    private ST<String, Double> st = new ST<String, Double>();
    // ComboBox aka dropdown list containing city/state names
    private JComboBox<String> stateList = new JComboBox<>();


    public Map2() {
        StdIn.readLine(); // ignores headers in .txt file
        max = Double.NEGATIVE_INFINITY;
        min = Double.POSITIVE_INFINITY;
        // parallel array for city and state names
        String[] city = new String[numberOfStates];
        String[] state = new String[numberOfStates];

        while (!StdIn.isEmpty()) {
            for (int i = 0; i < numberOfStates; i++) {
                // stores city/state names and cost of living into parallel arrays
                city[i] = StdIn.readString();
                state[i] = StdIn.readString();
                cost[i] = StdIn.readDouble();
                // combines city and state names into "name" array
                // this is so that we can use the same .txt file for Calculator.java
                name[i] = city[i] + " " + state[i];
                st.put(name[i], cost[i]); // inputs names and indices into symbol table
                StdIn.readDouble(); // ignores x and y coordinates
                StdIn.readDouble();
                // finds max and min cost of living indices
                max = Math.max(max, cost[i]);
                min = Math.min(min, cost[i]);
            }
            // tests should return max (158.0) and min (83.9) cost of living
            System.out.println("Test: Max is " + max + " and Min is " + min);
        }
    }

    // updates the text in the information label when a different city is clicked
    // in the dropdown list
    public void updateCity(String cityName) {
        // label text can be tested by clicking a state from the window's dropdown menu
        // and seeing that cities' information show up and updated when changed
        information.setText("City, State name: " + cityName + " \n Cost of Index: "
                                    + st.get(cityName));
    }

    // adds city/state names into the dropdown list and shows
    public void fillCombo() {
        // can be tested/checked by running program and checking dropdown menu
        // contains city/state names
        for (int i = 0; i < numberOfStates; i++) {
            stateList.addItem(name[i]);
        }
        stateList.setSelectedIndex(0);
        stateList.addActionListener(this);
    }

    public void createMap() {
        ImageIcon icon = new ImageIcon("map2.png");
        JLabel map = new JLabel("", icon, SwingConstants.CENTER);
        JButton show = new JButton("Show All States' Info");
        show.addMouseListener(this);
        information = new JLabel();
        fillCombo();
        // createComp(); // inputs cities into combo/dropdown box

        panel.add(show);
        panel.add(map);
        panel.add(stateList);
        panel.add(information);

        f.add(panel); // added panel to frame and made visible
        f.setSize(650, 500);
        f.setVisible(true);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Map2 map = new Map2();
        for (int i = 0; i < map.numberOfStates; i++) {
            StdOut.printf("%s %4.1f\n",
                          map.name[i], map.cost[i]);
        }
        map.createMap();
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse Clicked");
        String inf = "Name Cost\n";
        for (int i = 0; i < numberOfStates; i++) {
            inf = inf + name[i] + " " + cost[i] + "\n";
        }
        System.out.print(inf);
        System.out.println("Max Cost of Living Index: " + max);
        System.out.println("Min Cost of Living Index: " + min);
    }

    public void actionPerformed(ActionEvent e) {
        // tests/prints to make sure code is executing when dropdown menu is accessed
        System.out.println("Action performed: Dropdown menu accessed");
        // retrieves city/state name that was selected from dropdown list
        String cityName = (String) stateList.getSelectedItem();
        // tests to make sure selected city from dropdown menu is the
        // same one being outputted
        System.out.println("Test: New city picked is " + cityName);
        updateCity(cityName);
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }


}
