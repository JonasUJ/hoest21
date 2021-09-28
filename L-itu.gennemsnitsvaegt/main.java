class ItuGennemsnitsvaegt {
    private static int readNum() throws java.io.IOException {
        int num = 0, b;
        while ((b = System.in.read()) != '\n' && b != -1)
            num = num * 10 + b - '0';
        return num;
    }
    private static void advance(int c) throws java.io.IOException {
        int b;
        while (c == 1 && (b = System.in.read()) != '\n' && b != -1) {}
    }
    public static void main(String[] args) throws java.io.IOException {
        int n = readNum();
        double acc = 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            int k = readNum();
            int c = (k - 10) >>> 31 | (2000 - k) >>> 31;
            int ic = ~c & 1;
            i += c;
            count += ic;
            acc += k * ic;
            advance(c);
        }
        System.out.println(acc / count);
    }
}
