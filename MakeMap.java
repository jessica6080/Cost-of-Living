import java.awt.Color;
import java.awt.Font;

// file stores standard input related to cost of living and outputs a US map containing
// colored dots representing cities and their corresponding cost of living index
// relative to other cities
public class MakeMap {
    // parallel arrays containing city/state name, cost index, latitude, and longitude
    private final int numberOfStates = 50;
    private String[] name = new String[numberOfStates];
    private double[] cost = new double[numberOfStates];
    private double[] lat = new double[numberOfStates];
    private double[] lon = new double[numberOfStates];
    private double max;
    private double min;
    private Font font = new Font("Arial", Font.BOLD, 10);


    public MakeMap() {
        StdIn.readLine();
        max = Double.NEGATIVE_INFINITY;
        min = Double.POSITIVE_INFINITY;
        String[] city = new String[numberOfStates];
        String[] state = new String[numberOfStates];
        while (!StdIn.isEmpty()) {
            for (int i = 0; i < numberOfStates; i++) {
                // stores name, cost, lat/long into parallel arrays
                city[i] = StdIn.readString();
                state[i] = StdIn.readString();
                cost[i] = StdIn.readDouble();
                lat[i] = StdIn.readDouble();
                lon[i] = StdIn.readDouble();
                max = Math.max(max, cost[i]);
                min = Math.min(min, cost[i]);
                name[i] = city[i] + " " + state[i];
            }
            System.out.println("max: " + max);
            System.out.println("min: " + min);
        }
    }

    public void drawPoint() {
        StdDraw.setPenRadius(0.025);
        double[] costNew = cost;
        for (int i = 0; i < cost.length; i++) {
            for (int j = 1; j < cost.length; j++) {
                if ((costNew[i] > costNew[j]) && (i != j)) {
                    double temp = costNew[j];
                    costNew[j] = costNew[i];
                    costNew[i] = temp;
                }
            }
        }
        for (int i = 0; i < lat.length; i++) {
            double value = cost[i];
            int pos = 0;
            for (int j = 0; j < cost.length; j++) {
                if (value == costNew[j]) {
                    pos = j;
                    break;
                }
            }
            if (pos <= costNew.length / 4) {
                StdDraw.setPenColor(Color.yellow);
            }
            else if (pos > costNew.length / 4 && pos <= costNew.length / 2) {
                StdDraw.setPenColor(Color.orange);
            }
            else if (pos > costNew.length / 2 && pos <= costNew.length * 3 / 4) {
                StdDraw.setPenColor(Color.pink);
            }
            else {
                StdDraw.setPenColor(Color.red);
            }
            StdDraw.point(lat[i], lon[i]);
        }
    }

    public void setMap() {
        String usMap = "map.jpg";
        Picture pic = new Picture(usMap);

        int height = pic.height();
        int width = pic.width();

        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(0, 610); // 610 is the pixel width of image
        StdDraw.setYscale(0, 381); // 381 is pixel height of image
        StdDraw.picture(305, 190, usMap); // pixel width/height halved to center image
        // drawPoint();
        StdDraw.setPenColor(Color.black);
        StdDraw.show();
    }

    public void drawLegend() { // outputs text about color legend onto canvas
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setFont(font);
        StdDraw.text(475, 370, "Color Legend:");
        StdDraw.setPenColor(Color.RED);
        StdDraw.text(475, 360, "Red: Costs above the 3rd Quartile");
        StdDraw.setPenColor(Color.MAGENTA);
        StdDraw.text(475, 350, "Magenta: Costs between the 2nd and 3rd Quartile");
        StdDraw.setPenColor(Color.PINK);
        StdDraw.text(475, 340, "Pink: Costs between the 1st and 2nd Quartile");
        StdDraw.setPenColor(Color.ORANGE);
        StdDraw.text(475, 330, "Orange: Costs below the 1st Quartile");
    }

    public static void main(String[] args) {
        MakeMap map = new MakeMap();
        for (int i = 0; i < map.numberOfStates; i++) {
            // tests to see if all parallel arrays contain correct input data
            StdOut.printf("%s %4.1f %7.4f %7.4f\n",
                          map.name[i],
                          map.cost[i], map.lat[i], map.lon[i]);
        }
        System.out.println(map.max + " " + map.min); // prints out max and min
        map.setMap();
        map.drawPoint();
        map.drawLegend();
    }
}
