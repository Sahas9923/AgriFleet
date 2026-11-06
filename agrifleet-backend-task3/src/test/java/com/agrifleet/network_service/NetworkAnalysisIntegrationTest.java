package com.agrifleet.network_service;

import com.agrifleet.network_service.dto.NetworkAnalysisResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NetworkAnalysisIntegrationTest {

    @Autowired
    private NetworkAnalysisService service;

    @Test
    void springContextShouldLoad() {
        assertNotNull(service);
    }

    @Test
    void completeTask3AnalysisShouldWork() {

        NetworkAnalysisResponse result =
                service.analyzeNetwork(101);

        assertNotNull(result);

        assertEquals(
                10,
                result.nodeCount()
        );

        assertEquals(
                10,
                result.edgeCount()
        );

        assertEquals(
                7,
                result.bridges().size()
        );

        assertEquals(
                9,
                result.mst().edges().size()
        );

        assertTrue(
                result.mst().spanningTree()
        );

        assertEquals(
                31.84,
                result.mst().totalCost(),
                0.0001
        );
    }
}
