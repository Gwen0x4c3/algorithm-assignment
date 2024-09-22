package site.jigoku.algorithm.assignment_1_240918.exhaustive;

import java.util.Arrays;

public class Digital {

    /** 候选数组 */
    private final int[] candidates;
    /** 当前数组 */
    private final int[] array;
    boolean first = true;

    public Digital(int... candidates) {
        this.candidates = candidates;
        this.array = new int[candidates.length];
        Arrays.fill(this.array, 0);
    }
    public boolean next() {
        if (first) {
            first = false;
            return true;
        }
        int n = candidates.length;
        for (int i = n - 1; i >= 0; i--) {
            int cur = array[i];
            if (cur < candidates[i] - 1) {
                array[i]++;
                return true;
            } else {
                if (i == 0) {
                    return false;
                }
                array[i] = 0;
            }
        }
        return false;
    }

    public int[] getCurrent() {
        return Arrays.copyOf(array, array.length);
    }

    public static void main(String[] args) {
        Digital digital = new Digital(4, 5, 3); // 4 * 5 * 3 = 60
        int count = 0;
        while (digital.next()) {
            count++;
            System.out.println(Arrays.toString(digital.getCurrent()));
        }
        System.out.println("Count: " + count);
    }
}
