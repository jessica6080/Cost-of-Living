import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/* Interactive calculator created using Java Swing Framework that takes in
user input (2 state initials and current income) from JTextFields and calculates
percent change in income and estimated new income when moving from state 1 to
state 2 after clicking "Calculate" button

Test for Feature 3 can be found right above MouseClicked() */

public class Calculator extends JFrame
        implements MouseListener { // MouseInputListener, KeyListener,
    // ST with state abbrev. as key and cost index as value
    private static final ST<String, Double> ST = new ST<String, Double>();
    private JTextField field1; // text field for state 1 abbrev. input
    private JTextField field2; // text field for state 2 abbrev. input
    private JTextField inc; // text field for current income input
    // label to output percent change in cost of living
    private JLabel answer = new JLabel("Percent change in cost of living:", SwingConstants.CENTER);
    // label to output change in income needed to maintain current standard living
    // in state 1 after moving to state 2
    private JLabel stateLabel = new JLabel(
            "Percent change to maintain current cost of living moving "
                    + "from a major city in state 1 to a major city in state 2:",
            SwingConstants.CENTER);
    // string "Income" to avoid duplication of the string in code
    private final String incInput = "Income";

    public Calculator() {
        StdIn.readLine(); // ignores headers in input file
        while (!StdIn.isEmpty()) {
            StdIn.readString(); // ignores city name for simpler utility of calculator
            String state = StdIn.readString(); // key is state abbrev.
            double index = StdIn.readDouble(); // value is cost of living index
            StdIn.readInt(); // ignores x-coordinate/latitude of city
            StdIn.readInt(); // ignores y-coordinate/longitude of city
            if (!ST.contains(state)) ST.put(state, index); // inputting data into ST
        }
        int fieldWidth = 4; // width of text field for state 1 and 2
        int incWidth = 5; // width of text field for income input
        field1 = new JTextField("State 1", fieldWidth); // initialized text field for state 1
        field2 = new JTextField("State 2", fieldWidth); // initialized text field for state 2
        inc = new JTextField(incInput, incWidth); // initialized text field to input income
    }

    // operations to calculate estimated income needed to maintain standard of
    // living in the new state: (((state2 - state2)/100) + 1) * currentIncome
    public static double calculate(String s1, String s2, int income) {
        double state1 = ST.get(s1); // gets cost index of state 1 from ST
        double state2 = ST.get(s2); // gets cost index of state 2 from ST
        // test should print out the states and their correct corresponding indices
        System.out.println("Test: Checking if s1/s2 match up to index in ST: "
                                   + "State 1 = " + s1 + " State 2 = " + s2
                                   + "\nIndex 1 = " + state1 + "Index 2 = " + state2);

        double difference = state2 - state1;
        double percent = difference / 100;
        double add1 = percent + 1;
        double result = add1 * income;
        // tests should output correct calculations & can be checked by own computation
        System.out.println("Test: Difference calculated was " + difference);
        System.out.println("Test: Percent calculated was " + percent);
        System.out.println("Test: Adding 1 to percent was " + add1);
        System.out.println("Test: Result calculated was " + result);
        return result;
    }

    // checks if cost of living index in state 2 is higher or lower than state 1
    // returns string that will output
    public String checkLowHigh(double state1, double state2) {
        String loHi;
        double difference = state2 - state1;
        // tests to see program properly determines if state 2 has higher or lower
        // cost of living index than state 1
        System.out.printf("Test: State 2 minus State 1 is %.2f", difference);
        if (difference < 0) loHi = "lower"; // lower cost of index in state 2
        else if (difference > 0) loHi = "higher"; // higher cost of index in state 2
        else loHi = " ... it's the same state"; // cost of index same because same state
        // test should output correct classification of if state 2 is higher/lower than 1
        System.out.println("\nTest: State 2's cost of living is " + loHi + " than state 1");
        return loHi;
    }

    public void createCalc() {
        JFrame f = new JFrame("Calculator"); // frame to place all GUI components onto
        JLabel labelState = new JLabel("Input State 1 & 2's Initials",
                                       SwingConstants.CENTER);
        JLabel income = new JLabel("Input Income (Whole number): $", SwingConstants.TRAILING);

        field1.setEditable(true); // sets state 1 and 2 text fields to be editable
        field2.setEditable(true); // and allows user to input state abbrev.

        // calculate button for income calculation
        JButton calc = new JButton("Calculate");
        calc.addMouseListener(this);

        // panel containing GUI components
        // test check should show all components present on window when program is ran
        JPanel panel = new JPanel();
        panel.add(labelState); // added GUI labels, fields, and buttons to panel
        panel.add(field1);
        panel.add(field2);
        panel.add(income);
        panel.add(inc);
        panel.add(calc);

        panel.add(stateLabel);
        panel.add(answer);

        f.add(panel); // added panel to frame and made visible
        f.setSize(700, 150); // values represent the frame size for calculator
        f.setVisible(true);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
        Calculator cal = new Calculator();
        for (String keys : ST) {
            // should print out state names (name) and cost of living indices (values)
            System.out.println(keys + " " + ST.get(keys));
        }
        cal.createCalc();
    }

    /* TEST FOR FEATURE 3: calculating income and percent change in income by
         providing user input and clicking "Calculate" button to generate results
         Input: User inputs two state initials (ex. NY and NJ) and current income;
         calculate button is clicked to begin computations
         Expected Outcome: in clicking the button, the calculator outputs calculations
         onto the interactive calculator and on terminal; throws error if input was
         invalid
         Result: passed (Manually tested and inputted info to produce calculations
         that are printed out to the calculator and onto the terminal) */

    public void mouseClicked(MouseEvent e) {
        // tests to show program is properly running when mouse clicks button
        System.out.println("Mouse clicked");

        // retrieves text from state 1 and state 2 text fields
        String state1Text = field1.getText().toUpperCase();
        String state2Text = field2.getText().toUpperCase();
        // retrieves income and parses into string
        int incomeInput = Integer.parseInt(inc.getText().replaceAll("\\s", "")
                                              .replaceAll(",", ""));

        // test should output same input in state 1 and state 2 text fields in all uppercase
        System.out.println(
                "Test state1text is "
                        + state1Text);
        System.out.println("Test: state2text is " + state2Text);
        // test should output same input in income text field
        System.out.println("Test: income input is " + incomeInput);

        // throws error if income was not inputted
        if (inc.getText().equals(incInput))
            throw new IllegalArgumentException("PLEASE PROVIDE INCOME");

        // throws error if income input is negative (invalid)
        if (incomeInput < 0)
            throw new IllegalArgumentException("INVALID INCOME; RE-INPUT.");

        // throws error if abbreviation not recognized
        if (!ST.contains(state1Text) || !ST.contains(state2Text))
            throw new IllegalArgumentException("NOT A VALID STATE ABBREV. TRY AGAIN.");

        double state1 = ST.get(state1Text); // gets cost index of state 1 from ST
        double state2 = ST.get(state2Text); // gets cost index of state 2 from ST
        // calculates estimated income to maintain current standard of living
        // execute two custom methods (calculate and checkLowHigh)
        double result = calculate(state1Text, state2Text, incomeInput);
        String loHi = checkLowHigh(state1, state2);

        // output results of percent change and min. income needed to move onto labels
        // test checks should show correct classification of state 2's index being higher
        // or lower than state 1
        stateLabel.setText(
                "The income to maintain current cost of living in " + state1Text + " "
                        + "when moving to " + state2Text + " is about "
                        + (int) Math.abs(state2 - state1) + "% " + loHi);
        answer.setText("Estimated change in income given current income of $"
                               + incomeInput + " to move is $" + (int) result);
        System.out.printf("Test: Estimated income to maintain your standard of living "
                                  + "in state 2 is $%.2f\n", result);
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
