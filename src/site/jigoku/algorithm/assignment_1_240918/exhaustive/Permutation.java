package site.jigoku.algorithm.assignment_1_240918.exhaustive;

import site.jigoku.algorithm.utils.SystemUtils;

import java.io.IOException;
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
        int max = 0;
        for (int i = numOfConditions - 1; i >= 0; i--) {
            if (!picked[i]) {
                max = i;
                break;
            }
        }
        for (int i = n - 1; i >= 0; i--) { // 从后向前寻找第一个不是当前最大值的位置
            int cur = array[i];
            if (cur < max || cur < numOfConditions - i - 1) { // 从此处开始向后排除着最小限度地增加
                for (int j = i + 1; j < n; j++) { // 只保留前面的占用状态，后面的清空
                    picked[array[j]] = false;
                }
                // 当前位置设置成下一个可选数
                for (int k = cur; k < numOfConditions; k++) {
                    if (!picked[k]) {
                        picked[cur] = false;
                        array[i] = k;
                        picked[k] = true;
                        break;
                    }
                }
                for (int j = i + 1; j < n; j++) { // 遍历后面的元素
                    for (int k = 0; k < numOfConditions; k++) { // 找未被使用的最小值
                        if (!picked[k]) {
                            picked[array[j]] = false;
                            array[j] = k;
                            picked[k] = true;
                            break;
                        }
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

    public static void main(String[] args) throws IOException {
        Permutation permutation = new Permutation(5, 3);
        int count = 0;
        while (permutation.next()) {
            count++;
            int[] current = permutation.getCurrent();
            System.out.println(Arrays.toString(current));
//            SystemUtils.pause();
        }
        System.out.println("Count: " + count);
    }

}
