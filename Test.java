public class Test {
    public static void main(String[] args) {
        MakeMap make = new MakeMap();
        Map2 map = new Map2();
        Calculator cal = new Calculator();

        make.setMap();
        make.drawPoint();
        make.drawLegend();

        map.createMap();

        cal.createCalc();


    }
}
