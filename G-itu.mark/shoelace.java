import java.io.IOException;

class ItuMarkShoelace {
    private static double readNum() throws IOException {
        double num = 0, b;
        while ((b = System.in.read()) != '\n' && b != ' ' && b != -1)
            num = num * 10 + b - '0';
        return num;
    }

    public static void main(String[] args) throws IOException {
        int numPoints = (int)readNum();

        double[] points = new double[numPoints * 2 + 4];
        for (int i = 0; i < numPoints * 2; i += 2) {
            points[i] = readNum();
            points[i + 1] = readNum();
        }
        points[numPoints * 2 + 0] = points[0];
        points[numPoints * 2 + 1] = points[1];
        points[numPoints * 2 + 2] = points[2];
        points[numPoints * 2 + 3] = points[3];

        double area = 0;
        for (int i = 0; i < numPoints * 2; i += 2) {
            area += points[i] * points[i + 3] - points [i + 2] * points[i + 1];
        }

        area = Math.round(Math.abs(area) / 2 * 100) / 100.0;
        System.out.println(area);
    }
}
