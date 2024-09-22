package site.jigoku.algorithm.assignment_1_240918.exhaustive;

import java.util.Arrays;

public class Combination {

    private final int numOfConditions;
    private final int n;
    private final int[] array;
    private boolean first = true;

    public Combination(int numOfConditions, int n) {
        this.numOfConditions = numOfConditions;
        this.n = n;
        this.array = new int[n];
        for (int i = 0; i < n; i++) {
            this.array[i] = i;
        }
    }


    public boolean next() {
        if (first) {
            first = false;
            return true;
        }
        for (int i = n - 1; i >= 0; i--) {
            int cur = array[i];
            if (cur < numOfConditions - n + i) {
                array[i]++;
                return true;
            } else {
                if (i == 0) {
                    return false;
                }
                // 找到第一个需要加的数
                int start = i - 1;
                for (start = i - 1; start >= 0; start--) {
                    if (array[start] < numOfConditions - n + start) {
                        break;
                    }
                }
                if (start == -1) {
                    return false;
                }
                for (int j = start; j < n; j++) {
                    if (j == start) {
                        array[j]++;
                    } else {
                        array[j] = array[j - 1] + 1;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public int[] getCurrent() {
        return Arrays.copyOf(array, n);
    }

    public static void main(String[] args) {
        Combination combination = new Combination(7, 3);
        int count = 0;
        while (combination.next()) {
            count++;
            System.out.println(Arrays.toString(combination.getCurrent()));
        }
        System.out.println("Count: " + count);
    }
}
