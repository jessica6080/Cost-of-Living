import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;

/* Interactive map that takes in input for data.txt file containing city/state names
and cost of living indices and plots the points onto US map. Each point is color-coded
such that the color represents the cost of living index relative to other indices.
With the points plotted, the user can hover their mouse over a point to reveal
information about the city.

TEST FOR FEATURE 2 can be found right above test client */

public class Map {
    // symbol table for city/state name and cost index
    private final int numberOfStates = 50; // number of states in US/dataset
    private String[] name = new String[numberOfStates]; // array stores city/state names
    private double[] cost = new double[numberOfStates]; // stores cost of living indices
    private double[] lat = new double[numberOfStates]; // stores x coordinates of cities
    private double[] lon = new double[numberOfStates]; // stores y coordinates of cities

    // parallel array for city/state name and latitude/longitude
    public Map() {
        String[] city = new String[numberOfStates]; // stores only city names
        String[] state = new String[numberOfStates]; // stores only state names
        StdIn.readLine(); // ignores headers in file
        double max = Double.NEGATIVE_INFINITY;
        double min = Double.POSITIVE_INFINITY;
        while (!StdIn.isEmpty()) {
            for (int i = 0; i < numberOfStates; i++) {
                // stores name, cost, lat/long into parallel arrays
                city[i] = StdIn.readString();
                state[i] = StdIn.readString();
                cost[i] = StdIn.readDouble();
                lat[i] = StdIn.readDouble();
                lon[i] = StdIn.readDouble();
                // combines city and state names into "name" array
                /* city and state arrays were initially separated to ensure we
                can use same .txt file for Calculator.java since Calculator.java
                only accepts state names and indices, not including city names */
                name[i] = city[i] + " " + state[i];
                max = Math.max(max, cost[i]);
                min = Math.min(min, cost[i]);
            }
            System.out.println("Test: max cost of living index is " + max);
            System.out.println("Test: min cost of living index is " + min);
        }
    }

    // This method plots each data point from the .txt file
    // and uses a color scale to represent the quartile of each city point
    public void drawPoint() {
        StdDraw.setPenRadius(0.025);
        // initializes costNew to prepare to be sorted
        double[] costNew = new double[cost.length];
        for (int i = 0; i < cost.length; i++) {
            costNew[i] = cost[i];
        }
        // for loop that sorts through each element of the array to order elements
        // from least to greatest
        for (int i = 0; i < cost.length; i++) {
            for (int j = i + 1; j < cost.length; j++) {
                if ((costNew[i] > costNew[j]) && (i != j)) {
                    double temp = costNew[j];
                    costNew[j] = costNew[i];
                    costNew[i] = temp;
                }
            }
        }
        // outputs ordered array for costNew
        StdOut.println("Test: outputs ordered array: " + Arrays.toString(costNew));

        /* for loop that runs through the cost array and finds the cost's index in
        the sorted array. Depending on what the index is of the cost in the sorted
        array,we can see which percentage group (groups go by 25%) it falls under.*/
        for (int i = 0; i < lat.length; i++) {
            double value = cost[i];
            int pos = 0;
            for (int j = 0; j < cost.length; j++) {
                if (value == costNew[j]) {
                    pos = j;
                    break;
                }
            }
            if (pos < costNew.length / 4) {
                // Indices below the 1st Quartile assigned blue
                StdDraw.setPenColor(Color.blue);
            }
            else if (pos >= costNew.length / 4 && pos < costNew.length / 2) {
                // RGB format for the color purple
                // Indices between the 1st and 2nd Quartile assigned purple
                StdDraw.setPenColor(new Color(148, 0, 211));
            }
            else if (pos >= costNew.length / 2 && pos < costNew.length * 3 / 4) {
                // Indices between the 2nd and 3rd Quartile assigned magenta
                StdDraw.setPenColor(Color.magenta);
            }
            else if (pos >= costNew.length * 3 / 4) {
                // Indices above the 3rd Quartile assigned red
                StdDraw.setPenColor(Color.red);
            }
            // output points with respective color-coded dots
            StdDraw.point(lat[i], lon[i]);
        }
    }

    // output the map image and scale it to the canvas window
    public void mapPoint() {
        // set dimensions of canvas to match pixel size of map image
        String usMap = "map.jpg"; // US map template
        Picture pic = new Picture(usMap);

        int width = pic.width();
        int height = pic.height();
        System.out.println("Test: Width = " + width + " Height = " + height);

        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(0, 610); // 610 is the pixel width of image
        StdDraw.setYscale(0, 381); // 381 is pixel height of image
        StdDraw.picture(305, 190, usMap); // pixel width/height halved to center image
        StdDraw.setPenColor(Color.black);
        StdDraw.show();
    }

    // Outputs color legend to let user know what each colored dot represents
    public void drawLegend() {
        // if working, legend should output onto top right corner of map
        StdDraw.setPenColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 10);
        StdDraw.setFont(font);
        // hard-coded values below represent coordinates where legend is outputted on map
        StdDraw.text(450, 370, "Color Legend:");
        StdDraw.setPenColor(Color.RED);
        StdDraw.text(450, 360, "Red: Costs above the 3rd Quartile");
        StdDraw.setPenColor(Color.MAGENTA);
        StdDraw.text(450, 350, "Magenta: Costs between the 2nd and 3rd Quartile");
        StdDraw.setPenColor(new Color(148, 0, 211)); // color purple
        StdDraw.text(450, 340, "Purple: Costs between the 1st and 2nd Quartile");
        StdDraw.setPenColor(Color.blue);
        StdDraw.text(450, 330, "Blue: Costs below the 1st Quartile");
    }

    // finds the current and updated mouse x- and y-coordinates. If the mouse
    // hovers over a city, then a pop-up in the bottom right corner will display
    // the city name and its cost of living index.
    public void mouseFollower() {
        // setting x and y as the mouse's x and y coordinate and have it constantly
        // being updated
        while (true) {
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            StdDraw.pause(10);

            // for loop for when mouse coordinates in range of city's coordinates
            // draw a rectangle with an outline and display information
            for (int i = 0; i < lat.length; i++) {
                if (x <= lat[i] + 3 && x >= lat[i] - 3 && y <= lon[i] + 3
                        && y >= lon[i] - 3) {
                    StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
                    // drawing a filled rectangle with red outline that will contain info
                    // on bottom right portion of map as a pop-up
                    StdDraw.filledRectangle(412, 5, 70, 45);
                    StdDraw.setPenRadius(0.005); // set size of colored points
                    StdDraw.setPenColor(StdDraw.RED);
                    // hard-coded values are specifically-placed
                    // border lines to outline the filled rectangle
                    StdDraw.line(342, 50, 482, 50);
                    StdDraw.line(342, 50, 342, 0);
                    StdDraw.line(342, 0, 482, 0);
                    StdDraw.line(482, 50, 482, 0);
                    StdDraw.enableDoubleBuffering();
                    StdDraw.setPenColor(StdDraw.BLACK);
                    // city information will be outputted inside
                    // the filled rectangle display, hence the coordinates
                    StdDraw.textLeft(346, 35, "Location: " + name[i]);
                    StdDraw.textLeft(352, 18, "Cost of living: " + cost[i]);
                    System.out.println("Test: Mouse successfully hovered over " + name[i]
                                               + " to reveal index of " + cost[i]);
                    StdDraw.show();
                    // shows text box containing city data for 1 second before disappearing
                    StdDraw.pause(1000);
                }
                else {
                    StdDraw.show();
                    StdDraw.setPenColor(StdDraw.WHITE);
                    // draws filled white rectangle to erase previous city's info
                    // from rectangle display
                    StdDraw.filledRectangle(412, 5, 72, 47);
                }
            }
        }
    }

    /* TEST FOR FEATURE 2: stores input data into parallel arrays and plots each
    city's points onto US map; each plotted point has a certain classified color
    to represent cost of living index relative to other cities, and when user hovers
    over a city point, a text box temporarily appears that reveals city's information
    Input: data.txt file
    Expected Outcome: Input stored in parallel arrays can be printed out to ensure
    correct corresponding values. US map should be outputted along with the
    color-coded points, and when user's cursor hovers over point, text box should
    appear to show city's information
    Result: passed (data was printed out onto terminal, Manually checked: US map
    was seen to be visible, and text box temporarily appeared when cursor hovered over */

    public static void main(String[] args) {
        Map map = new Map();
        // prints out names, cost indices, and x/y coordinate info onto terminal
        // from stored arrays
        for (int i = 0; i < map.numberOfStates; i++) {
            StdOut.printf("%s %.1f %.2f %.2f\n",
                          map.name[i], map.cost[i], map.lat[i], map.lon[i]);
        }
        map.mapPoint();
        map.drawPoint();
        map.drawLegend();
        map.mouseFollower();
    }
}
