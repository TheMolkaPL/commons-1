package org.diorite.commons.array;

final class ArraySum {
    private ArraySum() {}

    static byte sum(byte[] array) {
        return (byte) sumToLong(array);
    }

    static int sumToInt(byte[] array) {
        return (int) sumToLong(array);
    }

    static long sumToLong(byte[] array) {
        long sum = 0;
        for (byte x: array) {
            sum += x;
        }
        return sum;
    }

    static char sum(char[] array) {
        return (char) sumToLong(array);
    }

    static int sumToInt(char[] array) {
        return (int) sumToLong(array);
    }

    static long sumToLong(char[] array) {
        long sum = 0;
        for (char x: array) {
            sum += x;
        }
        return sum;
    }

    static int sum(int[] array) {
        return (int) sumToLong(array);
    }

    static long sumToLong(int[] array) {
        long sum = 0;
        for (int x: array) {
            sum += x;
        }
        return sum;
    }

    static long sum(long[] array) {
        long sum = 0;
        for (long x: array) {
            sum += x;
        }
        return sum;
    }

    static float sum(float[] array) {
        float sum = 0;
        for (float x: array) {
            sum += x;
        }
        return sum;
    }

    static double sumToDouble(float[] array) {
        double sum = 0;
        for (float x: array) {
            sum += x;
        }
        return sum;
    }

    static double sum(double[] array) {
        double sum = 0;
        for (double x: array) {
            sum += x;
        }
        return sum;
    }
}
