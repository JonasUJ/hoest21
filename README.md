# hoest21
hoest21 was a series of task over the span of 4 weeks. These are my solutions for them. Leaderboard is available on [kattis](https://itu.kattis.com/sessions/a5kum2).

# About solutions
I've tried my best to optimize the runtime of the solutions. I use a variant of the following method in almost all solutions.
```java
static int readNum() throws java.io.IOException {
    int num = 0, b;
    while ((b = System.in.read()) != '\n' && b != ' ' && b != -1)
        num = num * 10 + b - '0';
    return num;
}
```
What it does is read until the next newline, space, or EOF and return what was read as an `int`. Using `System.in` for IO is faster than wrapping it in the slow (but handy) `java.util.Scanner`.

# Samples

When applicable, I've included a single sample input in a `samp.in` file. Testing the solution can be done by running `java main.java < samp.in`, although one should prefer `javac main.java && java <ClassName> < samp.in`.
