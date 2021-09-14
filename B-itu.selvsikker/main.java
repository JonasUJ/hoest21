import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

class ItuSelvsikker {
    static byte[] buffer = new byte[81];
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
        System.out.print(Character.toUpperCase(parts[1].charAt(0)));
        System.out.print(parts[1].substring(1));
        System.out.print(' ');
        System.out.print(Character.toLowerCase(parts[0].charAt(0)));
        System.out.print(parts[0].substring(1));
        System.out.print(' ');
        System.out.print(parts[2].substring(0, parts[2].length() - 1));
        System.out.println('!');
    }
}
