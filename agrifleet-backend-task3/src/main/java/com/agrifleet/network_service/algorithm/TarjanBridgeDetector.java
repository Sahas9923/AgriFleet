package com.agrifleet.network_service.algorithm;

import java.util.*;

public class TarjanBridgeDetector {

    public static class Edge {
        public int u, v;
        public double weight;
        public Edge(int u, int v, double weight) {
            this.u = u; this.v = v; this.weight = weight;
        }
    }

    private int timer = 0;
    private List<Edge> bridges;
    private boolean[] visited;
    private int[] tin, low;
    private Map<Integer, List<Integer>> adj;

    public List<Edge> findBridges(int numNodes, List<Edge> edges) {
        timer = 0;
        bridges = new ArrayList<>();
        visited = new boolean[numNodes + 1];
        tin = new int[numNodes + 1];
        low = new int[numNodes + 1];
        adj = new HashMap<>();

        for (int i = 1; i <= numNodes; i++) {
            adj.put(i, new ArrayList<>());
        }

        for (Edge e : edges) {
            adj.get(e.u).add(e.v);
            adj.get(e.v).add(e.u);
        }

        for (int i = 1; i <= numNodes; i++) {
            if (!visited[i]) {
                dfs(i, -1, edges);
            }
        }
        return bridges;
    }

    private void dfs(int u, int p, List<Edge> allEdges) {
        visited[u] = true;
        tin[u] = low[u] = ++timer;

        for (int v : adj.get(u)) {
            if (v == p) continue;
            if (visited[v]) {
                low[u] = Math.min(low[u], tin[v]);
            } else {
                dfs(v, u, allEdges);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] > tin[u]) {
                    double w = 25.0;
                    for (Edge e : allEdges) {
                        if ((e.u == u && e.v == v) || (e.u == v && e.v == u)) {
                            w = e.weight;
                            break;
                        }
                    }
                    bridges.add(new Edge(u, v, w));
                }
            }
        }
    }
}
