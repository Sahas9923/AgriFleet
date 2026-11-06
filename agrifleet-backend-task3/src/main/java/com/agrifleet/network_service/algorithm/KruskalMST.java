package com.agrifleet.network_service.algorithm;

import java.util.*;

public class KruskalMST {

    public static class DSU {
        int[] parent, rank;
        public DSU(int n) {
            parent = new int[n + 1];
            rank = new int[n + 1];
            for (int i = 0; i <= n; i++) parent[i] = i;
        }
        public int find(int i) {
            if (parent[i] == i) return i;
            return parent[i] = find(parent[i]);
        }
        public boolean unite(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);
            if (rootI != rootJ) {
                if (rank[rootI] < rank[rootJ]) parent[rootI] = rootJ;
                else if (rank[rootI] > rank[rootJ]) parent[rootJ] = rootI;
                else { parent[rootJ] = rootI; rank[rootI]++; }
                return true;
            }
            return false;
        }
    }

    public static class MSTResult {
        public List<TarjanBridgeDetector.Edge> mstEdges;
        public double totalCost;
        public MSTResult(List<TarjanBridgeDetector.Edge> mstEdges, double totalCost) {
            this.mstEdges = mstEdges; this.totalCost = totalCost;
        }
    }

    public static MSTResult calculateMST(int numNodes, List<TarjanBridgeDetector.Edge> edges) {
        List<TarjanBridgeDetector.Edge> mutableEdges = new ArrayList<>(edges);
        mutableEdges.sort(Comparator.comparingDouble(e -> e.weight));
        DSU dsu = new DSU(numNodes);
        List<TarjanBridgeDetector.Edge> mst = new ArrayList<>();
        double totalCost = 0.0;

        for (TarjanBridgeDetector.Edge e : mutableEdges) {
            if (dsu.unite(e.u, e.v)) {
                mst.add(e);
                totalCost += e.weight;
            }
        }
        return new MSTResult(mst, totalCost);
    }
}
