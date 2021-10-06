import java.util.HashMap;

class ItuBingo {
    static int readNum() throws java.io.IOException {
        int num = 0, b;
        while ((b = System.in.read()) != '\n' && b != -1)
            num = num * 10 + b - '0';
        return num;
    }
    static int[] readLine() throws java.io.IOException {
        int b, i = 0, num = 0;
        int[] nums = new int[5];
        while (i != 5) {
            b = System.in.read();
            if (b == ' ' || b == '\n') {
                if (num != 0) {
                    nums[i++] = num;
                    num = 0;
                }
            } else {
                num = num * 10 + b - '0';
            }
        }
        return nums;
    }
    public static void main(String[] args) throws java.io.IOException {
        System.in.readNBytes(14);
        var sb = new StringBuilder();
        boolean[][] board = new boolean[5][5];
        board[2][2] = true;
        var coords = new HashMap<Integer, Integer>();
        for (int y = 0; y < 5; y++) {
            var nums = readLine();
            for (int x = 0; x < 5; x++) {
                if (x == 2 && y == 2) continue;
                coords.put(nums[x], (x << 4) + y);
            }
        }

        while (true) {
            int num = readNum();
            Integer p = coords.remove(num);
            if (p == null) continue;
            int x = p >> 4, y = p & 0xf;
            board[x][y] = true;
            boolean bingox = true;
            for (int r = 0; r < 5; r++) {
                bingox &= board[r][y];
            }
            boolean bingoy = true;
            for (int c = 0; c < 5; c++) {
                bingoy &= board[x][c];
            }
            boolean bingod1 = true;
            bingod1 &= board[0][0];
            bingod1 &= board[1][1];
            bingod1 &= board[3][3];
            bingod1 &= board[4][4];
            boolean bingod2 = true;
            bingod2 &= board[4][0];
            bingod2 &= board[3][1];
            bingod2 &= board[1][3];
            bingod2 &= board[0][4];

            if (bingox || bingoy || bingod1 || bingod2) {
                sb.append(num);
                sb.append(" bingo!\n");
                break;
            }
        }

        while (true) {
            int num = readNum();
            coords.remove(num);
            if (coords.size() == 0) {
                sb.append(num);
                sb.append(" bingo!\n");
                break;
            }
        }

        System.out.print(sb);
    }
}
