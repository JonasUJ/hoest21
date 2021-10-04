import java.util.Arrays;

class ItuAdgangskode {
    private static int readNum() throws java.io.IOException {
        int num = 0, b;
        while ((b = System.in.read()) != '\n' && b != ' ' && b != -1)
            num = num * 10 + b - '0';
        return num;
    }
    private static String readWord() throws java.io.IOException {
        byte[] buffer = new byte[80*2];
        int b, i = 0;
        while ((b = System.in.read()) != '\n' && b != ' ' && b != -1)
            buffer[i++] = (byte)b;
        return new String(buffer, 0, i);
    }
    public static void main(String[] args) throws java.io.IOException {
        int wantedLength = readNum();
        int namesAmount = readNum();
        var names = new String[80];
        for (int i = 0; i < namesAmount; i++) {
            var name = readWord();
            var length = name.length();
            if (length >= wantedLength) continue;
            var comp = names[wantedLength - length];
            if (comp != null) {
                System.out.print(name.toLowerCase());
                System.out.println(comp.toLowerCase());
                return;
            }
            names[length] = name;
        }

        System.out.println("*umuligt*");
    }
}
