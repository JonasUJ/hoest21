import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

class ItuKornsilo {
    static byte[] buffer = new byte[22];
    static DataInputStream inputStream = new DataInputStream(System.in);
    static int index = 0;

    static char read() {
        return (char)buffer[index++];
    }

    public static void main(String[] args) throws IOException {
        inputStream.read(buffer);

        int i = 0;
        while (buffer[++i] != 0) {}
        var text = Arrays.copyOfRange(buffer, 0, i - 1);

        var parts = new String(text).split(" ", 3);
        var N = Integer.parseInt(parts[0]);
        var K = Integer.parseInt(parts[1]);
        var H = Integer.parseInt(parts[2]);
        System.out.println(Math.max(0, (int)Math.ceil((N - K * H) / (double)H)));
    }
}
