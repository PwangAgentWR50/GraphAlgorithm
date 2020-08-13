package com.graph.shortestpath;

/*
 * Dijkstra algorithm
 * 1. create sptSet (track the vertices that finalized distance from source)
 * 2. Initialize distance to all vertices as INFINITE, except dist to source is 0
 * 3. While sptSet doesn't include all veritices, do
 *      Pick a vertex u, which is not in sptSet and has min distance value
 *      Include u to sptSet
 *      Update distance value of all adjacent vertices of u
 *
 * reference:
 * https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/
 * https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-in-java-using-priorityqueue/
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class DijkstraAdjMatrix {
    private static int[][] graph = { { 0, 4, 0, 0, 0, 0, 0, 8, 0 },
        { 4, 0, 8, 0, 0, 0, 0, 11, 0 },
        { 0, 8, 0, 7, 0, 4, 0, 0, 2 },
        { 0, 0, 7, 0, 9, 14, 0, 0, 0 },
        { 0, 0, 0, 9, 0, 10, 0, 0, 0 },
        { 0, 0, 4, 14, 10, 0, 2, 0, 0 },
        { 0, 0, 0, 0, 0, 2, 0, 1, 6 },
        { 8, 11, 0, 0, 0, 0, 1, 0, 7 },
        { 0, 0, 2, 0, 0, 0, 6, 7, 0 } };

    private static void dijkstra(int src) {
        int n = graph.length;
        boolean[] sptSet = new boolean[n];
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        for (int i = 0; i < n - 1; i++) {
            int u = -1, minDist = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (!sptSet[j] && dist[j] <= minDist) {
                    u = j;
                    minDist = dist[j];
                }
            }
            sptSet[u] = true;
            for (int v = 0; v < n; v++) {
                if (dist[u] != Integer.MAX_VALUE && graph[u][v] != 0 && !sptSet[v] && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }

        System.out.println(Arrays.toString(dist));
    }

    static class Node implements Comparable<Node> {
        int idx;
        int dist;
        public Node(int idx, int dist) {
            this.idx = idx;
            this.dist = dist;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "idx=" + idx +
                    ", dist=" + dist +
                    '}';
        }

        @Override
        public int compareTo(Node o) {
            return this.dist - o.dist;
        }
    }

    private static void dijkstraHeap(int src) {
        int V = graph.length;
        Set<Integer> sptSet = new HashSet<>();
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        PriorityQueue<Node> q = new PriorityQueue<>();
        for (int v = 0; v < V; v++) {
            if (v == src) {
                q.add(new Node(v, 0));
            } else {
                q.add(new Node(v, Integer.MAX_VALUE));
            }
        }

        while (sptSet.size() != V) {
            Node minNode = q.poll();
            int u = minNode.idx;
            if (sptSet.contains(u)) continue;
            dist[u] = minNode.dist;
            sptSet.add(u);
            for (int v = 0; v < V; v++) {
                if (!sptSet.contains(v) && dist[u] != Integer.MAX_VALUE && graph[u][v] != 0 && dist[u] + graph[u][v] < dist[v]) {
                    Node newNode = new Node(v, dist[u] + graph[u][v]);
                    q.add(newNode);
                    dist[v] = newNode.dist;
                }
            }
        }
        System.out.println(Arrays.toString(dist));
    }

    public static void main(String[] args) {
        dijkstraHeap(0);
    }
}
