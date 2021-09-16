import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

class ItuVindmoelle {
    static byte[] buffer = new byte[22];
    static DataInputStream inputStream = new DataInputStream(System.in);

    public static void main(String[] args) throws IOException {
        inputStream.read(buffer);

        int i = 0;
        while (buffer[++i] != 0) {}
        var text = Arrays.copyOfRange(buffer, 0, i - 1);

        var parts = new String(text).split("\n");
        int rotation = Integer.parseInt(parts[0]);
        int destination = Integer.parseInt(parts[1]);
        int rotate = (360 - rotation + destination) % 360;
        rotate -= 360 * (((((rotate - 181) & (1 << 31)) >> 31)) + 1);
        System.out.print(rotate);
    }
}
