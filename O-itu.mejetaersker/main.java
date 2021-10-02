class ItuMejetaersker {
    public static void main(String[] args) throws java.io.IOException {
        boolean[][] field = new boolean[201][201];
        int x = 100,
            y = 100,
            xd = 0,
            yd = -1,
            xmin = 100,
            xmax = 101,
            ymin = 100,
            ymax = 101;
        field[x][y] = true;
        for (byte b : System.in.readAllBytes()) {
            switch (b) {
                case '>':
                    xd = xd + yd;
                    yd = xd - yd;
                    xd = (xd - yd) * -1;
                    break;
                case '<':
                    yd = yd + xd;
                    xd = yd - xd;
                    yd = (yd - xd) * -1;
                    break;
                case '^':
                    x += xd;
                    y += yd;
                    xmin = Math.min(xmin, x);
                    xmax = Math.max(xmax, x);
                    ymin = Math.min(ymin, y);
                    ymax = Math.max(ymax, y);
                    field[x][y] = true;
                    break;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int j = ymin; j <= ymax; j++) {
            for (int i = xmin; i <= xmax; i++) {
                if (field[i][j])
                    sb.append('#');
                else
                    sb.append(' ');
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}
