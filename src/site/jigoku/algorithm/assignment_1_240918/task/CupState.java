package site.jigoku.algorithm.assignment_1_240918.task;

import site.jigoku.algorithm.assignment_1_240918.exhaustive.Digital;
import site.jigoku.algorithm.utils.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CupState implements DNode {

    /** 记录所有水杯当前盛了多少水 */
    private int[] cups;
    /** 记录杯子的容积 */
    private int[] capacities;

    public CupState(int... cups) {
        this.cups = cups.clone();
    }

    public void setCapacities(int... capacities) {
        this.capacities = capacities.clone();
    }

    public int[] getCups() {
        return cups.clone();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        CupState cupState = (CupState) object;
        return Arrays.equals(cups, cupState.cups) && Arrays.equals(capacities, cupState.capacities);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(cups);
        result = 31 * result + Arrays.hashCode(capacities);
        return result;
    }

    @Override
    public String toString() {
        return "Cup: " + Arrays.toString(cups);
    }

    private boolean contains(int target) {
        for (int cup : cups) {
            if (cup == target) {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果两个杯子状态之间只有两个位置数值不一样，就返回这两个位置的坐标，否则返回null
     * @param state1
     * @param state2
     * @return
     */
    private Tuple<Integer, Integer> findOnlyTowDifferentIndex(CupState state1, CupState state2) {
        int[] cups1 = state1.getCups();
        int[] cups2 = state2.getCups();
        int first = -1, second = -1;
        for (int i = 0; i < cups1.length; i++) {
            if (cups1[i] != cups2[i]) {
                if (first == -1) {
                    first = i;
                } else if (second == -1) {
                    second = i;
                } else { // 有多个不相同的，返回两个-1
                    return new Tuple<Integer, Integer>(-1, -1);
                }
            }
        }
        return new Tuple<>(first, second);
    }

    /**
     * 判断两个杯子状态之间能否通过一次操作获得
     * @param state1
     * @param state2
     * @param capacities
     * @return
     */
    private boolean isDirect(CupState state1, CupState state2, int[] capacities) {
        int[] cups1 = state1.getCups();
        int[] cups2 = state2.getCups();
        // 看两种状态之间是否有两个位置对应不相同
        Tuple<Integer, Integer> tuple = findOnlyTowDifferentIndex(state1, state2);
        int i = tuple.getFirst(), j = tuple.getSecond();
        if (i != -1 && j != -1) {
            int before1 = cups1[i], before2 = cups1[j];
            int after1 = cups2[i], after2 = cups2[j];
            // 再判断是否一增一减，并且符合三种情况
            // 1. 失去水的杯子倒光，得到水的杯子满了或得到的水和失去的一样
            // 2. 失去水的杯子没倒光，并且得到水的杯子满了，并且失去的水比得到的水多
            // 3. 失去水的杯子没倒光，并且得到水的杯子没满，并且得到的水和失去的一样
            int diff1 = after1 - before1, diff2 = after2 - before2;
            if (diff1 * diff2 > 0) { // 同增同减的排除
                return false;
            }

            if (diff1 > 0 && diff2 < 0) { // 第一个位置得到水，第二个位置失去水
                if (after2 == 0) { // 1.
                    return after1 == capacities[i] || diff1 == -1 * diff2;
                } else {
                    if (after1 == capacities[i] && -1 * diff2 > diff1) { // 2.
                        return true;
                    }
                }
            } else if (diff1 < 0 && diff2 > 0) { // 第一个位置失去水，第二个位置得到水
                if (after1 == 0) { // 1.
                    return after2 == capacities[j] || diff2 == -1 * diff1;
                } else {
                    if (after2 == capacities[j] && -1 * diff1 > diff2) { // 2.
                        return true;
                    }
                }
            }
            return false;
        } else if (i != -1) { // 前后只有一个位置不一样，并且是把水倒空了或接满了
            if (cups1[i] == 0 && cups2[i] == capacities[i]
                    || cups2[i] == 0) { // 如果是从0开始接水，必须接的水和水杯容积一样，如果是倒水，只要倒光就ok
                return true;
            }
        }
        return false;
    }

    private void printPath(List<CupState> cupStates, int[] predecessor, int start, int end) {
        if (end == -1 || end == start) {
            return;
        }
        printPath(cupStates, predecessor, start, predecessor[end]);
        System.out.print(Arrays.toString(cupStates.get(end).getCups())+ " ");

    }

    @Override
    public double getDirectDistanceFrom(DNode from) {
        /*
            总体流程：
            1. 初始化水杯的容积，建立最开始的水杯状态[0,0,0]
            2. 使用Digital类，穷举出所有可能的组合（包括不可能实现的）
         */
        int[] digitalArray = new int[cups.length];
        for (int i = 0; i < capacities.length; i++) {
            digitalArray[i] = capacities[i] + 1;
        }

        // 生成所有组合
        Digital digital = new Digital(digitalArray);
        List<CupState> cupStates = new ArrayList<>();
        while (digital.next()) {
            int[] current = digital.getCurrent();
            CupState cupState = new CupState(current);
            cupState.setCapacities(capacities);
            cupStates.add(cupState);
        }

        // 初始化邻接矩阵
        int[][] edge = new int[cupStates.size()][cupStates.size()];
        // 补充邻接矩阵
        for (int i = 0; i < cupStates.size(); i++) {
            for (int j = 0; j < cupStates.size(); j++) {
                if (i == j) {
                    edge[i][j] = 1;
                    continue;
                }
                if (isDirect(cupStates.get(i), cupStates.get(j), capacities)) {
                    edge[i][j] = 1;
                } else {
                    edge[i][j] = 0;
                }
                if (isDirect(cupStates.get(j), cupStates.get(i), capacities)) {
                    edge[j][i] = 1;
                } else {
                    edge[j][i] = 0;
                }
            }
        }

        System.out.println(cupStates.size() + "种组合");

        // 计算距离
        int startIdx = cupStates.indexOf((CupState) from);
        int endIdx = cupStates.indexOf(this);
        Tuple<int[], int[]> tuple = Dijkstra.getShortestDistance(edge, startIdx);
        int[] predecessor = tuple.getSecond();

        printPath(cupStates, predecessor, startIdx, endIdx);
        System.out.println();

        return tuple.getFirst()[endIdx];
    }

    public static void main(String[] args) {
        int[] capacities = new int[] {3, 5, 7};
        CupState start = new CupState(0, 0, 0);
        start.setCapacities(capacities);
        CupState end = new CupState(3, 5, 5);
        end.setCapacities(capacities);
        System.out.println(end.getDirectDistanceFrom(start));
    }
}
