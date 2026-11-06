package com.agrifleet.network_service.algorithm.kruskal;

import com.agrifleet.network_service.graph.Graph;
import com.agrifleet.network_service.graph.GraphEdge;
import com.agrifleet.network_service.graph.GraphNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KruskalMstTest {

    @Test
    void shouldCreateMinimumSpanningTree() {

        Graph graph = new Graph();

        graph.addNode(
                new GraphNode(1, "A", 0, 0)
        );

        graph.addNode(
                new GraphNode(2, "B", 0, 0)
        );

        graph.addNode(
                new GraphNode(3, "C", 0, 0)
        );

        graph.addEdge(
                new GraphEdge(1, 1, 2, 1.0, 40.0)
        );

        graph.addEdge(
                new GraphEdge(2, 2, 3, 2.0, 40.0)
        );

        graph.addEdge(
                new GraphEdge(3, 1, 3, 10.0, 40.0)
        );

        KruskalMst kruskal =
                new KruskalMst();

        KruskalMst.MstResult result =
                kruskal.findMst(graph);

        assertTrue(result.spanningTree());

        assertEquals(
                2,
                result.selectedEdges().size()
        );

        assertEquals(
                3.0,
                result.totalCost(),
                0.0001
        );
    }
}
