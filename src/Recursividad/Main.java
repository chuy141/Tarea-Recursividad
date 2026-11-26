package recursividad;

import java.util.Arrays;

public class Main {

    // P1: Recursion lineal: suma los primeros n elementos.
    public static int linearSum(int[] data, int n) {
        if (n == 0) {
            return 0;
        }
        return linearSum(data, n - 1) + data[n - 1];
    }

    // P2: Invierte el subarreglo desde low hasta high.
    public static void reverseArray(int[] data, int low, int high) {
        if (low < high) {
            int temp = data[low];
            data[low] = data[high];
            data[high] = temp;
            reverseArray(data, low + 1, high - 1);
        }
    }

    // P3 BAD: potencia con una llamada recursiva por cada exponente.
    public static double powerBad(double x, int n) {
        if (n == 0) {
            return 1.0;
        }
        return x * powerBad(x, n - 1);
    }

    // P3 GOOD: exponenciacion por cuadrados, O(log n).
    public static double powerGood(double x, int n) {
        if (n == 0) {
            return 1.0;
        }
        double partial = powerGood(x, n / 2);
        double result = partial * partial;
        if (n % 2 != 0) {
            result *= x;
        }
        return result;
    }

    // P4: suma de un arreglo por recursividad binaria.
    public static int binarySum(int[] data, int low, int high) {
        if (low > high) {
            return 0;
        }
        if (low == high) {
            return data[low];
        }
        int mid = (low + high) / 2;
        return binarySum(data, low, mid)
                + binarySum(data, mid + 1, high);
    }

    // P5: Fibonacci recursivo directo (ineficiente).
    public static long fibonacciBad(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacciBad(n - 1) + fibonacciBad(n - 2);
    }

    // P5: Fibonacci eficiente; devuelve [F(n), F(n+1)].
    public static long[] fibonacciGood(int n) {
        if (n == 0) {
            return new long[]{0, 1};
        }
        long[] pair = fibonacciGood(n / 2);
        long a = pair[0]; // F(k)
        long b = pair[1]; // F(k+1)
        long c = a * (2 * b - a); // F(2k)
        long d = a * a + b * b;   // F(2k+1)
        if (n % 2 == 0) {
            return new long[]{c, d};
        }
        return new long[]{d, c + d};
    }

    // P7: recursividad multiple, cuenta maneras de subir 1 o 2 escalones.
    // Tiene dos llamadas recursivas; n=0 tiene una forma (no moverse).
    public static long waysUpStairs(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 0 || n == 1) {
            return 1;
        }
        return waysUpStairs(n - 1) + waysUpStairs(n - 2);
    }

    // P8: Torres de Hanoi. Imprime cada movimiento.
    public static void towerOfHanoi(int n, char from, char to, char auxiliary) {
        if (n == 0) {
            return;
        }
        towerOfHanoi(n - 1, from, auxiliary, to);
        System.out.println("Mover disco " + n + " de " + from + " a " + to);
        towerOfHanoi(n - 1, auxiliary, to, from);
    }

    // Medicion de tiempo en nanosegundos convertida a milisegundos.
    public static double measureMillis(Runnable action) {
        long start = System.nanoTime();
        action.run();
        long end = System.nanoTime();
        return (end - start) / 1_000_000.0;
    }

    public static void main(String[] args) {
        System.out.println("P1: SUMA LINEAL");
        int[][] sumTests = {{2, 4, 6, 8}, {5}, {-3, 7, 2}};
        for (int[] a : sumTests) {
            System.out.println(Arrays.toString(a) + " => " + linearSum(a, a.length));
        }

        System.out.println("P2: INVERTIR ARREGLo");
        int[][] reverseTests = {{1, 2, 3, 4, 5}, {10, 20, 30, 40}, {7}};
        for (int[] a : reverseTests) {
            System.out.println("Antes: " + Arrays.toString(a));
            reverseArray(a, 0, a.length - 1);
            System.out.println("Despues: " + Arrays.toString(a));
        }

        System.out.println("P3: POTENCIAS Y TIEMPO");
        int[] exponents = {10, 50, 100, 1000, 10000};
        double x = 1.0001;
        System.out.println("x = " + x);
        System.out.printf("%-8s %-18s %-18s%n", "n", "BAD (ms)", "GOOD (ms)");
        for (int n : exponents) {
            String badTime;
            try {
                double ms = measureMillis(() -> powerBad(x, n));
                badTime = String.format("%.6f", ms);
            } catch (StackOverflowError e) {
                badTime = "StackOverflow";
            }
            double good = measureMillis(() -> powerGood(x, n));
            System.out.printf("%-8d %-18s %-18.6f%n", n, badTime, good);
        }
        System.out.println("Nota: el tiempo cambia segun computadora/JVM; BAD puede agotar la pila.");

        System.out.println("P4: SUMA BINARIA");
        int[][] binaryTests = {{1, 2, 3, 4}, {10, -2, 7}, {5, 5, 5, 5, 5}};
        for (int[] a : binaryTests) {
            int result = binarySum(a, 0, a.length - 1);
            System.out.println(Arrays.toString(a) + " => " + result);
        }

        System.out.println("P5: FIBONACCI");
        int[] fibTests = {5, 8, 10};
        for (int n : fibTests) {
            System.out.println("n=" + n + " BAD=" + fibonacciBad(n)
                    + " GOOD=" + fibonacciGood(n)[0]);
        }

        System.out.println("P7: RECURSION MULTIPLE (ESCALONES)");
        int[] stairTests = {3, 4, 5};
        for (int n : stairTests) {
            System.out.println("Escalones " + n + " => " + waysUpStairs(n) + " maneras");
        }

        System.out.println("P8: TORRES DE HANOI");
        towerOfHanoi(3, 'A', 'C', 'B');
    }
}
