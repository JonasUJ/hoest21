import java.math.BigInteger;

class ItuReproduktionsrater {
    private static int readNum() throws java.io.IOException {
        int num = 0, b;
        while ((b = System.in.read()) != '\n' && b != ' ' && b != -1)
            num = num * 10 + b - '0';
        return num;
    }
    public static void main(String[] args) throws java.io.IOException {
        int n = readNum();
        int l = readNum();
        BigInteger[] rates = new BigInteger[l];
        BigInteger[] prev = new BigInteger[l];
        for (int i = 0; i < l; i++) {
            rates[i] = BigInteger.valueOf(readNum());
            prev[i] = BigInteger.ZERO;
        }
        var sb = new StringBuilder();

        for (int gen = 0; gen < n; gen++) {
            var cur = BigInteger.ZERO;
            if (gen > l) {
                for (int p = 0; p < l; p++) {
                    cur = cur.add(prev[(gen - p - 1) % l].multiply(rates[p]));
                }
            } else {
                for (int p = 0; p < l && p < gen; p++) {
                    cur = cur.add(prev[(gen - p - 1) % l].multiply(rates[p]));
                }
                cur = cur.add(BigInteger.ONE);
            }

            prev[gen % l] = cur;

            sb.append(cur);
            sb.append('\n');
        }

        System.out.print(sb);
    }
}
