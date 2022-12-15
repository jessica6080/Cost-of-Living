import java.awt.Color;
import java.awt.Font;

public class Map {
    // symbol table for city/state name and cost index
    private final int numberOfStates = 50;
    private String[] name = new String[numberOfStates];
    private double[] cost = new double[numberOfStates];
    private double[] lat = new double[numberOfStates];
    private double[] lon = new double[numberOfStates];
    private double max;
    private double min;
    // private ST<String, Double> st = new ST<String, Double>();

    // parallel array for city/state name and latitude/longitude
    public Map() {
        StdIn.readLine();
        max = Double.NEGATIVE_INFINITY;
        min = Double.POSITIVE_INFINITY;
        while (!StdIn.isEmpty()) {
            for (int i = 0; i < numberOfStates; i++) {
                // stores name, cost, lat/long into parallel arrays
                name[i] = StdIn.readString();
                cost[i] = StdIn.readDouble();
                lat[i] = StdIn.readDouble();
                lon[i] = StdIn.readDouble();
                max = Math.max(max, cost[i]);
                min = Math.min(min, cost[i]);
            }
            System.out.println(max);
            System.out.println(min);
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

    public void inRange() {
        StdDraw.setPenColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 10);
        for (int i = 0; i < lat.length; i++) {
            if (StdDraw.mouseX() == lat[i] && StdDraw.mouseY() == lon[i]) {
                StdDraw.setFont(font);
                StdDraw.text(lat[i], lon[i], "City: " + name[i] + " " + cost[i]);
                StdDraw.show();
                StdDraw.pause(10);
                StdDraw.clear();
            }
        }
    }

    public void showMore() {
        StdDraw.setPenColor(Color.black);
    }

    public void mapPoint() {
        String usMap = "map.jpg";
        Picture pic = new Picture(usMap);

        int height = pic.height();
        int width = pic.width();

        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(0, 610); // 1200 is the pixel width of image
        StdDraw.setYscale(0, 381); // 750 is pixel height of image
        StdDraw.picture(305, 190, usMap); // pixel width/height halved to center image
        // drawPoint();
        StdDraw.setPenColor(Color.black);
        StdDraw.show();
    }

    public void drawLegend() {
        StdDraw.setPenColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 10);
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
        Map map = new Map();
        for (int i = 0; i < map.numberOfStates; i++) {
            StdOut.printf("%s %4.1f %7.4f %7.4f\n",
                          map.name[i],
                          map.cost[i], map.lat[i], map.lon[i]); //  , ;
        }
        map.mapPoint();
        map.drawPoint();
        map.drawLegend();
    }
}
