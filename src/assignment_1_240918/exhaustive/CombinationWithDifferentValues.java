package assignment_1_240918.exhaustive;

import java.util.Arrays;

public class CombinationWithDifferentValues {

    int[] nArray;

    int[] data;

    CombinationWithDifferentValues(int... nArray) {
        if (nArray == null || nArray.length == 0) {
            throw new RuntimeException("Invalid input array");
        }
        this.nArray = nArray;
        this.data = new int[nArray.length];
        Arrays.fill(data, 0);
        data[nArray.length - 1] = -1;
    }

    public boolean next() {
        for (int i = nArray.length - 1; i >= 0; i--) {
            if (data[i] < nArray[i] - 1) { // 可以继续增加
                data[i]++;
                return true;
            } else { // 无法增加了，如果已经到了最前面，则返回false
                data[i] = 0;
                if (i == 0) {
                    return false;
                }
            }
        }
        return false;
    }

    public int[] getCurrent() {
        return Arrays.copyOf(data, nArray.length);
    }

    public static void main(String[] args) {
        CombinationWithDifferentValues Combination = new CombinationWithDifferentValues(2, 3, 5);
        int i = 0;
        while (Combination.next()) {
            i++;
            System.out.println(Arrays.toString(Combination.getCurrent()));
        }
        System.out.println("total: " + i);
    }
}
