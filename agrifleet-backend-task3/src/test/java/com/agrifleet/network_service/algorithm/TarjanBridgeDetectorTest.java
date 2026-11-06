package com.agrifleet.network_service.algorithm;

import com.agrifleet.network_service.graph.Graph;
import com.agrifleet.network_service.graph.GraphEdge;
import com.agrifleet.network_service.graph.GraphNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TarjanBridgeDetectorTest {

    private TarjanBridgeDetector detector;

    @BeforeEach
    void setUp() {
        detector = new TarjanBridgeDetector();
    }

    @Test
    @DisplayName("Test 1: Linear path A - B - C has all roads as critical bridges")
    void testLinearPathAllBridges() {
        Graph graph = new Graph();
        graph.addNode(new GraphNode(1, "Depot", 6.9, 79.8));
        graph.addNode(new GraphNode(2, "Junction", 6.91, 79.81));
        graph.addNode(new GraphNode(3, "Farm", 6.92, 79.82));

        GraphEdge e1 = new GraphEdge(1, 1, 2, 5.0, 20.0);
        GraphEdge e2 = new GraphEdge(2, 2, 3, 4.0, 20.0);

        graph.addEdge(e1);
        graph.addEdge(e2);

        List<GraphEdge> bridges = detector.findBridges(graph);

        assertEquals(2, bridges.size());
        assertTrue(bridges.contains(e1));
        assertTrue(bridges.contains(e2));
    }

    @Test
    @DisplayName("Test 2: Square cycle A - B - C - D - A has ZERO bridges due to alternate loop")
    void testSquareCycleZeroBridges() {
        Graph graph = new Graph();
        graph.addNode(new GraphNode(1, "Node 1", 6.9, 79.8));
        graph.addNode(new GraphNode(2, "Node 2", 6.91, 79.81));
        graph.addNode(new GraphNode(3, "Node 3", 6.92, 79.82));
        graph.addNode(new GraphNode(4, "Node 4", 6.93, 79.83));

        graph.addEdge(new GraphEdge(1, 1, 2, 2.0, 25.0));
        graph.addEdge(new GraphEdge(2, 2, 3, 3.0, 25.0));
        graph.addEdge(new GraphEdge(3, 3, 4, 2.5, 25.0));
        graph.addEdge(new GraphEdge(4, 4, 1, 3.5, 25.0));

        List<GraphEdge> bridges = detector.findBridges(graph);

        assertEquals(0, bridges.size());
    }

    @Test
    @DisplayName("Test 3: Triangle cycle with a single stick edge to Farm (Lollipop shape)")
    void testLollipopGraphSingleBridge() {
        Graph graph = new Graph();
        graph.addNode(new GraphNode(1, "Hub 1", 6.9, 79.8));
        graph.addNode(new GraphNode(2, "Hub 2", 6.91, 79.81));
        graph.addNode(new GraphNode(3, "Hub 3", 6.92, 79.82));
        graph.addNode(new GraphNode(4, "Isolated Farm", 6.93, 79.83));

        graph.addEdge(new GraphEdge(1, 1, 2, 2.0, 30.0));
        graph.addEdge(new GraphEdge(2, 2, 3, 2.0, 30.0));
        graph.addEdge(new GraphEdge(3, 3, 1, 2.0, 30.0));

        GraphEdge stickEdge = new GraphEdge(4, 3, 4, 5.0, 15.0);
        graph.addEdge(stickEdge);

        List<GraphEdge> bridges = detector.findBridges(graph);

        assertEquals(1, bridges.size());
        assertEquals(stickEdge.id(), bridges.get(0).id());
    }

    @Test
    @DisplayName("Test 4: Disconnected farm clusters find bridges in all components")
    void testDisconnectedComponents() {
        Graph graph = new Graph();

        graph.addNode(new GraphNode(1, "Cluster1 Node1", 6.9, 79.8));
        graph.addNode(new GraphNode(2, "Cluster1 Node2", 6.91, 79.81));
        GraphEdge c1Edge = new GraphEdge(101, 1, 2, 2.0, 20.0);
        graph.addEdge(c1Edge);

        graph.addNode(new GraphNode(3, "Cluster2 Node3", 7.0, 80.0));
        graph.addNode(new GraphNode(4, "Cluster2 Node4", 7.01, 80.01));
        GraphEdge c2Edge = new GraphEdge(102, 3, 4, 3.0, 20.0);
        graph.addEdge(c2Edge);

        List<GraphEdge> bridges = detector.findBridges(graph);

        assertEquals(2, bridges.size());
        assertTrue(bridges.contains(c1Edge));
        assertTrue(bridges.contains(c2Edge));
    }

    @Test
    @DisplayName("Test 5: AgriFleet Specification 10-Node Rural Network Benchmark")
    void testAgriFleetSpecificationNetwork() {
        Graph graph = new Graph();

        for (int i = 1; i <= 10; i++) {
            graph.addNode(new GraphNode(i, "Node " + i, 6.9 + (i * 0.01), 79.8 + (i * 0.01)));
        }

        graph.addEdge(new GraphEdge(1, 1, 2, 1.75, 30.0));
        graph.addEdge(new GraphEdge(2, 2, 5, 4.20, 30.0));
        graph.addEdge(new GraphEdge(3, 5, 6, 4.50, 30.0));
        graph.addEdge(new GraphEdge(4, 6, 1, 2.10, 30.0));

        GraphEdge bridge23 = new GraphEdge(5, 2, 3, 2.20, 28.0);
        graph.addEdge(bridge23);

        graph.addEdge(new GraphEdge(6, 3, 4, 1.90, 25.0));
        graph.addEdge(new GraphEdge(7, 4, 10, 5.10, 25.0));
        graph.addEdge(new GraphEdge(8, 10, 3, 3.60, 25.0));

        GraphEdge bridge67 = new GraphEdge(9, 6, 7, 4.44, 18.0);
        graph.addEdge(bridge67);

        graph.addEdge(new GraphEdge(10, 6, 8, 2.30, 22.0));
        GraphEdge bridge89 = new GraphEdge(11, 8, 9, 3.10, 12.0);
        graph.addEdge(bridge89);

        List<GraphEdge> bridges = detector.findBridges(graph);
        Set<Integer> bridgeEdgeIds = bridges.stream().map(GraphEdge::id).collect(Collectors.toSet());

        assertTrue(bridgeEdgeIds.contains(5));
        assertTrue(bridgeEdgeIds.contains(9));
        assertTrue(bridgeEdgeIds.contains(10));
        assertTrue(bridgeEdgeIds.contains(11));
    }
}
