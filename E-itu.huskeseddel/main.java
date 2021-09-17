class ItuHuskeseddel {
    public static void main(String[] args) throws java.io.IOException {
        var input = System.in.readNBytes(4);
        int carry = 1;
        for (int i = input.length - 1; i >= 0; i--) {
            int num = input[i] - 48 + carry;
            carry = num / 10;
            System.out.print(num % 10);
        }
    }
}
