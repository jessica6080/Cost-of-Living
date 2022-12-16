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

/* Interactive window that utilizes Java Swing library that outputs a window
containing dropdown menu containing all given city names, a button that prints out
all cities' names and associated data with it onto terminal, and a map showing all
cities plotted in colors to represent the city's cost of living index relative
to other cities

TEST FOR FEATURE 1 can be found right above MouseClicked() */

public class DropDown extends JFrame implements MouseListener, ActionListener {
    // number of cities/states analyzed from data
    private final int numberOfStates = 50;
    // array stores city/state names
    private String[] name = new String[numberOfStates];
    // array that stores cities' cost of living indices
    private double[] cost = new double[numberOfStates];
    private double max; // finds maximum cost of living index
    private double min; // finds minimum cost of living index
    private double avg; // finds average cost of living index
    // frame to place all GUI components from frame onto
    private JFrame f = new JFrame("Cost of Living Index in US Map");
    private JPanel panel = new JPanel(); // panel to add GUI components to
    private JLabel information; // label that outputs information on city
    // symbol table for city/state name (String) and cost index (Double)
    private ST<String, Double> st = new ST<String, Double>();
    // ComboBox aka dropdown list containing city/state names
    private JComboBox<String> stateList = new JComboBox<>();

    // constructor stores data into parallel arrays and calculates max, min, and average
    public DropDown() {
        StdIn.readLine(); // ignores initial headers in .txt file
        max = Double.NEGATIVE_INFINITY;
        min = Double.POSITIVE_INFINITY;
        // parallel arrays for city and state names
        String[] city = new String[numberOfStates];
        String[] state = new String[numberOfStates];
        // reads in data.txt file
        while (!StdIn.isEmpty()) {
            avg = 0;
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
                avg += cost[i]; // sums up all indices to calculate average
            }
            avg = avg / numberOfStates; // divides by total
            // tests should return correct average (100.94)
            System.out.printf("Test: Average index is %.2f", avg);
            // tests should return max (158.0) and min (83.9) cost of living
            System.out.println("\nTest: Max is " + max + " and Min is " + min);
        }
    }

    // updates the text in the information label when a different city is clicked
    // in the dropdown list; has String input for the city's name
    public void updateCity(String cityName) {
        // label text can be tested by clicking a state from the window's dropdown menu
        // and seeing that cities' information show up and updated when changed
        information.setText("City, State name: " + cityName + " \n Cost of Living Index: "
                                    + st.get(cityName));
    }

    // adds city/state names into the dropdown list and sets first inputted state
    // and info as the one initially visible from the list
    public void fillCombo() {
        // can be tested/checked by running program and checking dropdown menu
        // contains city/state names
        for (int i = 0; i < numberOfStates; i++) {
            stateList.addItem(name[i]);
        }
        // initial city that is visible on dropdown list should be Montgomery, AL
        stateList.setSelectedIndex(0);
        stateList.addActionListener(this);
    }

    // method creates GUI map/framework, including button, dropdown menu (combo box)
    // sets all GUI components to be visible
    public void createMap() {
        ImageIcon icon = new ImageIcon("map.png"); // should display map
        JLabel map = new JLabel("", icon, SwingConstants.CENTER);
        // button that when clicked, should print out each state's info in terminal
        JButton show = new JButton("Show All States' Info");
        show.addMouseListener(this);
        information = new JLabel();
        fillCombo(); // method above that fills dropdown list with states' info

        panel.add(show); // adds all created GUI components onto panel
        panel.add(map);
        panel.add(stateList);
        panel.add(information);

        f.add(panel); // added panel to frame and made components visible
        f.setSize(650, 500);
        f.setVisible(true);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        DropDown map = new DropDown();
        // prints out names and cost of indices info onto terminal
        for (int i = 0; i < map.numberOfStates; i++) {
            StdOut.printf("%s %4.1f\n",
                          map.name[i], map.cost[i]);
        }
        // can be tested to see if all GUI components work when window appears
        map.createMap();
    }

    /* TEST FOR FEATURE 1: Outputs an interactive window that allows the user to
    choose a city from the dropdown menu and output the cities' information
    using JComboBox and JButton through Java Swing Library
    Input: User clicks on a city from dropdown list or clicks on button to show
    all cities' information all at once
    Expected Outcome: in clicking a city from dropdown list, the text label to
    the right of the dropdown menu should change and output city information, and
    terminal should also output messages that recognize dropdown menu was accessed
    and what city has been clicked
    Result: passed (Manually tested and clicked on dropdown list and button to
    monitor output onto window and onto terminal was accurate) */

    // if button is clicked, all cities' information will be outputted to terminal
    public void mouseClicked(MouseEvent e) {
        // tests by printing mouse clicked to show code is running when clicked
        System.out.println("Mouse Clicked");
        String inf = "Name Cost\n";
        for (int i = 0; i < numberOfStates; i++) {
            inf = inf + name[i] + " " + cost[i] + "\n";
        }
        // can be tested/checked by seeing if terminal prints output in correct format
        System.out.print(inf);
        System.out.println("Max Cost of Living Index: " + max);
        System.out.println("Min Cost of Living Index: " + min);
        System.out.printf("Average Cost of Living Index: %.2f \n", avg);
    }

    // text label to the right of dropdown list should change
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
