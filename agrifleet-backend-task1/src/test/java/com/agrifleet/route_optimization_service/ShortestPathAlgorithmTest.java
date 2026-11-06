package com.agrifleet.route_optimization_service;

import com.agrifleet.route_optimization_service.service.algorithm.AStarAlgorithm;
import com.agrifleet.route_optimization_service.service.algorithm.DijkstraAlgorithm;
import com.agrifleet.route_optimization_service.service.algorithm.Haversine;
import com.agrifleet.route_optimization_service.service.algorithm.NodeCoord;
import com.agrifleet.route_optimization_service.service.algorithm.RoutingGraph;
import com.agrifleet.route_optimization_service.service.algorithm.SearchResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShortestPathAlgorithmTest {

    private final AStarAlgorithm aStar = new AStarAlgorithm();
    private final DijkstraAlgorithm dijkstra = new DijkstraAlgorithm();

    /**
     * Nodes 0..3 form a small graph; node 4 is isolated.
     * Optimal 0 -> 3 is 0->2->1->3 with cost 8.
     */
    private RoutingGraph sampleGraph() {
        RoutingGraph g = new RoutingGraph();
        for (long i = 0; i <= 4; i++) {
            g.addNode(i, 0, 0); // identical coords -> admissible h(n) = 0
        }
        g.addEdge(0, 2, 2, 2, 1);
        g.addEdge(2, 1, 1, 1, 2);
        g.addEdge(0, 1, 4, 4, 3);
        g.addEdge(1, 3, 5, 5, 4);
        g.addEdge(2, 3, 8, 8, 5);
        g.setMaxSpeedKmh(60);
        return g;
    }

    @Test
    void dijkstraFindsOptimalPath() {
        SearchResult r = dijkstra.findShortestPath(sampleGraph(), 0, 3);
        assertTrue(r.found());
        assertEquals(8.0, r.totalCostMinutes(), 1e-9);
        assertEquals(8.0, r.totalDistanceKm(), 1e-9);
        assertEquals(List.of(0L, 2L, 1L, 3L), r.path());
    }

    @Test
    void aStarFindsSameOptimalPath() {
        SearchResult r = aStar.findShortestPath(sampleGraph(), 0, 3);
        assertTrue(r.found());
        assertEquals(8.0, r.totalCostMinutes(), 1e-9);
        assertEquals(List.of(0L, 2L, 1L, 3L), r.path());
    }

    @Test
    void notFoundWhenTargetUnreachable() {
        SearchResult r = aStar.findShortestPath(sampleGraph(), 0, 4);
        assertFalse(r.found());
        assertTrue(r.path().isEmpty());
        assertEquals(0.0, r.totalDistanceKm(), 1e-9);
        assertTrue(r.nodeExpansions() >= 0);
    }

    @Test
    void aStarExpandsNoMoreThanDijkstra() {
        RoutingGraph grid = gridGraph(49); // 7 x 7 grid
        SearchResult a = aStar.findShortestPath(grid, 0, 48);
        SearchResult d = dijkstra.findShortestPath(grid, 0, 48);

        assertTrue(a.found());
        // both algorithms are optimal -> identical cost
        assertEquals(d.totalCostMinutes(), a.totalCostMinutes(), 1e-9);
        // A* heuristic-directed search must not expand more vertices than Dijkstra
        assertTrue(a.nodeExpansions() <= d.nodeExpansions(),
                "A* expanded " + a.nodeExpansions() + " but Dijkstra expanded " + d.nodeExpansions());
    }

    /** Uniform 7x7 grid on a small plane; edge cost = distance at 60 km/h. */
    private RoutingGraph gridGraph(int n) {
        RoutingGraph g = new RoutingGraph();
        int side = (int) Math.sqrt(n);
        for (int i = 0; i < n; i++) {
            double lat = (i / side) * 0.01;
            double lon = (i % side) * 0.01;
            g.addNode(i, lat, lon);
        }
        for (int i = 0; i < n; i++) {
            int row = i / side;
            int col = i % side;
            if (col + 1 < side) {
                addGridEdge(g, i, i + 1);
            }
            if (row + 1 < side) {
                addGridEdge(g, i, i + side);
            }
        }
        g.setMaxSpeedKmh(60);
        return g;
    }

    private void addGridEdge(RoutingGraph g, int u, int v) {
        NodeCoord a = g.coords().get((long) u);
        NodeCoord b = g.coords().get((long) v);
        double km = Haversine.distanceKm(a.latitude(), a.longitude(), b.latitude(), b.longitude());
        double minutes = km; // speed 60 km/h -> 1 minute per km
        long edgeId = (long) u * 1000L + v;
        g.addEdge(u, v, minutes, km, edgeId);
        g.addEdge(v, u, minutes, km, edgeId);
    }
}
