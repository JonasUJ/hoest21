import java.io.IOException;

class ItuMark {
    private static double readNum() throws IOException {
        double num = 0, b;
        while ((b = System.in.read()) != '\n' && b != ' ' && b != -1)
            num = num * 10 + b - '0';
        return num;
    }

    private static double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    /**
     * Heron's area formula.
     * */
    private static double area(double x1, double y1, double x2, double y2, double x3, double y3) {
        double ab = dist(x1, y1, x2, y2);
        double bc = dist(x2, y2, x3, y3);
        double ca = dist(x3, y3, x1, y1);
        double s = (ab + bc + ca) / 2;
        return Math.sqrt(s * (s - ab) * (s - bc) * (s - ca));
    }

    public static void main(String[] args) throws IOException {
        int numPoints = (int)readNum();

        double[] points = new double[numPoints * 2];
        for (int i = 0; i < numPoints * 2; i += 2) {
            points[i] = readNum();
            points[i + 1] = readNum();
        }

        double area = 0;
        int offset = 0;
        while (numPoints * 2 - offset > 4) {
            area += area(points[0], points[1], points[2 + offset], points[3 + offset], points[4 + offset], points[5 + offset]);
            offset += 2;
        }

        System.out.println(Math.round(area * 100) / 100.0);
    }
}
