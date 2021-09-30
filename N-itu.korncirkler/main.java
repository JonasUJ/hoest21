import java.util.HashMap;

class ItuKorncirkler {
    static int readNum() throws java.io.IOException {
        int num = 0, b;
        while ((b = System.in.read()) != '\n' && b != ' ' && b != -1)
            num = num * 10 + b - '0';
        return num;
    }
    public static void main(String[] args) throws java.io.IOException {
        int b = readNum();
        int h = readNum();
        StringBuilder sb = new StringBuilder();
        HashMap<Integer, Integer> found = new HashMap<Integer, Integer>();
        for (int y = 0; y <= h; y++) {
            int xs = -1;
            for (int x = 0; x <= b; x++) {
                if (System.in.read() == '#') {
                    if (xs == -1) xs = x;
                } else {
                    if (found.containsKey(x)) {
                        int ys = found.remove(x);
                        sb.append(x);
                        sb.append(" ");
                        sb.append((y - ys - 1) / 2 + ys);
                        sb.append("\n");
                    }
                    if (xs != -1) {
                        int c = xs + (x - xs) / 2;
                        found.putIfAbsent(c, y);
                        xs = -1;
                    }
                }
            }
        }

        System.out.print(sb);
    }
}
