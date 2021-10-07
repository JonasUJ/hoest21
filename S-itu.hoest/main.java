import java.util.Arrays;

class ItuHoest {
    static int readNum() throws java.io.IOException {
        int num = 0, b;
        while ((b = System.in.read()) != ' ' && b != '\n' && b != -1)
            num = num * 10 + b - '0';
        return num;
    }
    public static void main(String[] args) throws java.io.IOException {
        int b = readNum() - 1;
        int h = readNum();
        int xs = readNum();
        int ys = readNum();
        int i = 0;
        // byte[] path = new byte[b*h + 3*h + b + (h + 2) * (h & 1) + 1 + xs + ys];
        byte[] path = new byte[100000];

        for (int y = ys; y > 0; y--) path[i++] = '^';
        path[i++] = '<';
        for (int x = xs; x > 0; x--) path[i++] = '^';
        path[i++] = '>';
        path[i++] = '>';

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < b; x++) {
                path[i++] = '^';
            }
            if (y != h-1)
            if ((y & 1) == 0) {
                path[i++] = '>';
                path[i++] = '^';
                path[i++] = '>';
            } else {
                path[i++] = '<';
                path[i++] = '^';
                path[i++] = '<';
            }
        }

        if ((h & 1) == 1) {
            path[i++] = '>';
            path[i++] = '>';
            for (int x = b; x > 0; x--) path[i++] = '^';
        }
        path[i++] = '>';
        for (int y = h - 1; y > 0; y--) path[i++] = '^';

        path = Arrays.copyOf(path, i);

        System.out.write(path);
        System.out.flush();
    }
}
