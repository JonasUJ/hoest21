/*
 * Naïve solution that only checks mink above.
 * Gets 79/100 points.
 */

import java.util.*;

class ItuMink {
    static int readNum() throws java.io.IOException {
        int num = 0, b;
        while ((b = System.in.read()) != ' ' && b != '\n')
            num = num * 10 + b - '0';
        return num;
    }
    public static void main(String[] args) throws java.io.IOException {
        int h = readNum();
        int b = readNum();
        int d = readNum();
        ArrayList<Element> mink = new ArrayList<>();
        Element[][] ground = new Element[b][h];

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < b; x++) {
                switch (System.in.read()) {
                    case '#':
                        ground[x][y] = new Element(x, y, false);
                        break;
                    case '<':
                        var m = new Element(x, y, true);
                        ground[x][y] = m;
                        mink.add(m);
                        break;
                    case '=':
                    case '>':
                        ground[x][y] = ground[x-1][y];
                        break;
                }
            }
            System.in.read();
        }

        var iter = mink.listIterator(mink.size());
        while (iter.hasPrevious()) {
            var m = iter.previous();
            m.findCost(ground, d);
        }

        char[] output = new char[(b + 1) * h];
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < b; x++) {
                output[y * (b + 1) + x] = ground[x][y].text();
            }
            output[(y+1) * (b+1) - 1] = '\n';
        }

        System.out.print(output);
    }
}

class Element {
    public int x, y, cost;
    char text;
    int prints = 0;
    boolean excavated;
    final char[] chars = new char[] { '<', '=', '>' };
    ArrayList<Element> filler;
    HashSet<Element> dependencies;
    boolean mink;
    public Element(int x, int y, boolean mink) {
        this.x = x;
        this.y = y;
        this.mink = mink;
        if (mink) {
            filler = new ArrayList<>();
            dependencies = new HashSet<>();
            cost = Integer.MAX_VALUE;
        }
    }
    public char text() {
        if (excavated)
            return ' ';
        if (mink)
            return chars[prints++ % chars.length];
        return '#';
    }
    public int findCost(Element[][] ground, int d) {
        if (cost != Integer.MAX_VALUE) return cost;
        cost = 0;

        for (int o = 0; o < 3; o++) {
            for (int y = this.y - 1; y >= 0; y--) {
                Element here = ground[this.x + o][y];
                if (here.mink) {
                    if (!dependencies.contains(here)) {
                        dependencies.add(here);
                        cost += here.findCost(ground, d);
                    }
                    break;
                }
                filler.add(here);
                cost += 1;
            }
        }

        cost -= d;

        // System.out.printf("(%2d, %2d) cost: %d%n", x, y, cost);
        if (cost < 0) {
            excavate();
            cost = 0;
        }

        return cost;
    }
    public void excavate() {
        if (excavated) return;
        excavated = true;
        cost = 0;
        for (var f : filler) {
            f.excavated = true;
        }
        for (var d : dependencies) {
            d.excavate();
        }
    }
    public void clear() {
        filler.clear();
        dependencies.clear();
        cost = Integer.MAX_VALUE;
    }
}
