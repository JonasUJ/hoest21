import java.util.Map;
import static java.util.Map.entry;

class ItuRegnskab {
    static String readWord() throws java.io.IOException {
        byte[] buffer = new byte[16];
        int b, i = 0;
        while ((b = System.in.read()) != '\n' && b != ' ' && b != -1)
            buffer[i++] = (byte)b;
        return new String(buffer, 0, i);
    }
    public static void main(String[] args) throws java.io.IOException {
        var map = Map.ofEntries(
            entry("et", 1),
            entry("en", 1),
            entry("to", 2),
            entry("tre", 3),
            entry("fire", 4),
            entry("fem", 5),
            entry("seks", 6),
            entry("syv", 7),
            entry("otte", 8),
            entry("ni", 9),
            entry("ti", 10),
            entry("elleve", 11),
            entry("tolv", 12),
            entry("tretten", 13),
            entry("fjorten", 14),
            entry("femten", 15),
            entry("seksten", 16),
            entry("sytten", 17),
            entry("atten", 18),
            entry("nitten", 19),
            entry("tyve", 20),
            entry("tredive", 30),
            entry("fyrre", 40),
            entry("halvtreds", 50),
            entry("tres", 60),
            entry("halvfjerds", 70),
            entry("firs", 80),
            entry("halvfems", 90),
            entry("hundrede", 100),
            entry("tusinde", 1000),
            entry("million", 1000000),
            entry("millioner", 1000000)
        );

        int acc = 0;
        int innerAcc = 1;
        boolean is100 = false;
        String next;
        while ((next = readWord()).length() != 0) {
            Integer val = map.get(next);
            if (val == null) {
                var split = next.split("og");
                val = map.get(split[0]) + map.get(split[1]);
            }

            if (is100 && 0 < val && val < 100) {
                innerAcc += val;
            } else if (val < innerAcc) {
                acc += innerAcc;
                innerAcc = val;
            } else {
                innerAcc *= val;
            }

            is100 = val == 100;
        }

        acc += innerAcc;
        System.out.println(acc);
    }
}
