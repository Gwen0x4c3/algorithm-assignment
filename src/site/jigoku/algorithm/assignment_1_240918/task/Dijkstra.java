package site.jigoku.algorithm.assignment_1_240918.task;

import site.jigoku.algorithm.utils.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dijkstra {

    public static Tuple<int[], int[]> getShortestDistance(int[][] edge, int start) {
        int n = edge.length;
        boolean[] visited = new boolean[n];
        Arrays.fill(visited, false);

        // 前驱数组
        int[] predecessor = new int[n];
        Arrays.fill(predecessor, -1);

        // 距离数组
        int[] distance = new int[n];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[start] = 0;

        for (int i = 0; i < n; i++) {
            if (edge[start][i] != 0) {
                distance[i] = 1; // 初始化邻接节点的距离
                predecessor[i] = start;
            }
        }

        for (int i = 0; i < n - 1; i++) {
            int min = Integer.MAX_VALUE, minIdx = -1;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && distance[j] < min) {
                    minIdx = j;
                    min = distance[j];
                }
            }

            if (minIdx == -1) break; // 如果没有可访问的节点，退出循环

            visited[minIdx] = true;

            for (int j = 0; j < n; j++) {
                if (edge[minIdx][j] != 0 && !visited[j] && min + 1 < distance[j]) {
                    distance[j] = min + 1; // 更新距离
                    predecessor[j] = minIdx;
                }
            }
        }

        return new Tuple<>(distance, predecessor);
    }
}
