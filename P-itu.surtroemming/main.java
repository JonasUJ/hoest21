class ItuSurstromming {
    static int readNum() throws java.io.IOException {
        int num = 0, b;
        while ((b = System.in.read()) != '\n')
            num = num * 10 + b - '0';
        return num;
    }
    public static void main(String[] args) throws java.io.IOException {
        readNum(); // Ignore limit
        System.out.println("? 100 100");
        int r1 = readNum();
        System.out.println("? 0 100");
        int r2 = readNum();
        System.out.println("? 0 0");
        int r3 = readNum();

        int x = (r1 - r2 + 10000) / 200;
        int y = (r1 - r3 + 20000 - 200 * x) / 200;
        int z = (int)Math.sqrt(r1 - Math.pow(x, 2) - Math.pow(y, 2));

        var sb = new StringBuilder();
        sb.append("! ").append(100 - x).append(' ').append(100 - y).append(' ').append(z);
        System.out.println(sb);
    }
}
