class ItuStald {
    public static void main(String[] args) throws java.io.IOException {
        int min = 0, max = 0, count = 0, b;
        while ((b = System.in.read()) != -1) {
            if (b == 'i') max = Math.max(++count, max);
            if (b == 'u') min = Math.min(--count, min);
        }
        System.out.println(max - min);
    }
}
