class ItuBondegaard {
    static int readNum() throws java.io.IOException {
        int num = 0, b;
        while ((b = System.in.read()) != '\n' && b != ' ' && b != -1)
            num = num * 10 + b - '0';
        return num;
    }
    public static void main(String[] args) throws java.io.IOException {
        System.out.print("Rued og Valborgs bondegård\n".repeat(readNum()));
    }
}
