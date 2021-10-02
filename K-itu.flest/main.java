import java.util.Arrays;
import java.util.HashMap;

class ItuFlest {
    private static int readNum() throws java.io.IOException {
        int num = 0, b;
        while ((b = System.in.read()) != '\n' && b != ' ' && b != -1)
            num = num * 10 + b - '0';
        return num;
    }
    private static String readLine() throws java.io.IOException {
        byte[] buffer = new byte[100*10*2];
        int b, i = 0;
        while ((b = System.in.read()) != '\n' && b != -1)
            buffer[i++] = (byte)b;
        return new String(buffer, 0, i);
    }
    private static String[] readLineSplit() throws java.io.IOException {
        byte[] buffer = new byte[100*10*2];
        String[] words = new String[100];
        int b, i = 0, s = 0;
        while ((b = System.in.read()) != '\n' && b != -1) {
            if (b == ' ') {
                words[s++] = (new String(buffer, 0, i)).toLowerCase();
                i = 0;
            }
            else if (b != ',' && b != '.' && b != '!')
                buffer[i++] = (byte)b;
        }
        words[s++] = (new String(buffer, 0, i)).toLowerCase();
        return Arrays.copyOfRange(words, 0, s);
    }

    public static void main(String[] args) throws java.io.IOException {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int max = 0;
        for (var word : readLine().split(" ")) {
            map.put(word, 0);
        }
        int n = readNum();

        HashMap<String, Integer> lineMap = new HashMap<String, Integer>();
        for (int i = 0; i < n; i++) {
            lineMap.clear();
            int lineMax = 0;
            for (String word : readLineSplit()) {
                if (map.containsKey(word)) {
                    if (lineMap.containsKey(word)) {
                        int c = lineMap.get(word) + 1;
                        lineMax = Math.max(lineMax, c);
                        lineMap.put(word, c);
                    } else {
                        lineMax = Math.max(lineMax, 1);
                        lineMap.put(word, 1);
                    }
                }
            }
            for (var name : map.keySet()) {
                var key = lineMap.get(name);
                if (key != null && key == lineMax) {
                    int c = map.get(name) + 1;
                    max = Math.max(max, c);
                    map.put(name, c);
                    break;
                }
            }
        }

        var arr = map.keySet().toArray();
        Arrays.sort(arr);
        var sb = new StringBuilder();
        for (var name : arr) {
            if (map.get(name) == max) {
                sb.append(name);
                sb.append('\n');
            }
        }
        System.out.print(sb);
    }
}
