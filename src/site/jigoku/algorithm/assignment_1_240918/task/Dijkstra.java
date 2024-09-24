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


//    public void addEdge(DNode from, DNode to) {
//        graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
//        edges.put(new Pair<>(from, to), to.getDirectDistanceFrom(from));
//    }
//
//    public List<DNode> getShortestPath(DNode start, DNode end) {
//        Map<DNode, DNode> previousNodes = new HashMap<>();
//        Map<DNode, Double> shortestDistances = new HashMap<>();
//        PriorityQueue<DNode> queue = new PriorityQueue<>(Comparator.comparingDouble(shortestDistances::get));
//
//        shortestDistances.put(start, 0.0);
//        queue.add(start);
//
//        while (!queue.isEmpty()) {
//            DNode currentNode = queue.poll();
//
//            if (currentNode.equals(end)) {
//                return constructPath(previousNodes, end);
//            }
//
//            for (DNode neighbor : graph.get(currentNode)) {
//                double newDistance = shortestDistances.get(currentNode) + edges.get(new Pair<>(currentNode, neighbor));
//
//                if (!shortestDistances.containsKey(neighbor) || newDistance < shortestDistances.get(neighbor)) {
//                    shortestDistances.put(neighbor, newDistance);
//                    previousNodes.put(neighbor, currentNode);
//                    queue.add(neighbor);
//                }
//            }
//        }
//
//        return null;  // 如果没有找到路径
//    }
//
//    private List<DNode> constructPath(Map<DNode, DNode> previousNodes, DNode end) {
//        List<DNode> path = new ArrayList<>();
//        DNode currentNode = end;
//
//        while (currentNode != null) {
//            path.add(currentNode);
//            currentNode = previousNodes.get(currentNode);
//        }
//
//        Collections.reverse(path);
//        return path;
//    }
}
