package com.agrifleet.route_optimization_service;

import com.agrifleet.route_optimization_service.service.algorithm.Haversine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HaversineTest {

    @Test
    void distanceToSelfIsZero() {
        assertEquals(0.0, Haversine.distanceKm(7.29, 80.63, 7.29, 80.63), 1e-9);
    }

    @Test
    void kandyToGampolaIsReasonable() {
        // Depot Kandy (7.2906, 80.6337) -> Farm Gampola (7.1643, 80.5696)
        double km = Haversine.distanceKm(7.2906, 80.6337, 7.1643, 80.5696);
        assertTrue(km > 10 && km < 20, "expected ~15 km, got " + km);
    }

    @Test
    void isSymmetric() {
        double d1 = Haversine.distanceKm(7.29, 80.63, 7.16, 80.57);
        double d2 = Haversine.distanceKm(7.16, 80.57, 7.29, 80.63);
        assertEquals(d1, d2, 1e-9);
    }
}
