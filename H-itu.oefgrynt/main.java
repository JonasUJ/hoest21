class ItuOefgrynt {
    private static double readNum() throws java.io.IOException {
        double num = 0, b;
        while ((b = System.in.read()) != '\n' && b != ' ' && b != -1)
            num = num * 10 + b - '0';
        return num;
    }
    public static void main(String[] args) throws java.io.IOException {
        double num = readNum();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= num; i++) {
            if (i % 15 == 0)
                sb.append("øfgrynt");
            else if (i % 3 == 0)
                sb.append("øf");
            else if (i % 5 == 0)
                sb.append("grynt");
            else
                sb.append(i % 100);
            sb.append('\n');
        }
        System.out.print(sb.toString());
    }
}
