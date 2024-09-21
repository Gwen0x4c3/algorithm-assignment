package assignment_1_240918.exhaustive;

import java.util.Arrays;

public class Permutation {

    private final int numOfConditions;
    private final int n;
    private final int[] array;
    private boolean first = true;

    public Permutation(int numOfConditions, int n) {
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
        boolean[] picked = new boolean[numOfConditions];
        Arrays.fill(picked, false);
        for (int item : array) {
            picked[item] = true;
        }
        for (int i = n - 1; i >= 0; i--) {
            // 找到最小的未选中数 和 下一个要选的数
            int min = 0, next = numOfConditions - 1;
            int cur = array[i];
            for (int j = 0; j < cur; j++) {
                if (!picked[j]) {
                    min = j;
                    break;
                }
            }
            for (int j = cur; j < numOfConditions; j++) {
                if (!picked[j]) {
                    next = j;
                    break;
                }
            }
            picked[cur] = false;
            if (!picked[next] && cur < next) {
                array[i] = next;
                picked[next] = true;
                return true;
            } else { // 否则这个位置变成最小未被选中的数
                array[i] = min;
                picked[min] = true;
            }
        }
        return false;
    }

    public int[] getCurrent() {
        return Arrays.copyOf(array, n);
    }

    public static void main(String[] args) {
        Permutation permutation = new Permutation(5, 3);
        int count = 0;
        while (permutation.next()) {
            count++;
            int[] current = permutation.getCurrent();
            System.out.println(Arrays.toString(current));
        }
        System.out.println("Count: " + count);
    }

}
