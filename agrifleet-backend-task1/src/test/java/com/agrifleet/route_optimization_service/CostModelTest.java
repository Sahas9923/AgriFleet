package com.agrifleet.route_optimization_service;

import com.agrifleet.route_optimization_service.model.RoadEdge.SurfaceType;
import com.agrifleet.route_optimization_service.model.WeatherCondition.Condition;
import com.agrifleet.route_optimization_service.service.CostModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CostModelTest {

    private final CostModel model = new CostModel();

    @Test
    void surfaceFactors() {
        assertEquals(1.00, model.surfaceFactor(SurfaceType.PAVED), 1e-9);
        assertEquals(1.15, model.surfaceFactor(SurfaceType.GRAVEL), 1e-9);
        assertEquals(1.35, model.surfaceFactor(SurfaceType.MUD), 1e-9);
        assertEquals(1.50, model.surfaceFactor(SurfaceType.DIRT_TRACK), 1e-9);
        assertEquals(1.00, model.surfaceFactor(SurfaceType.BRIDGE), 1e-9);
    }

    @Test
    void weatherMultipliers() {
        assertEquals(1.0, model.weatherMultiplier(Condition.CLEAR, 0.9), 1e-9);
        assertEquals(1.25, model.weatherMultiplier(Condition.LIGHT_RAIN, 0.5), 1e-9);
        assertEquals(1.8, model.weatherMultiplier(Condition.HEAVY_RAIN, 0.8), 1e-9);
        assertEquals(2.62, model.weatherMultiplier(Condition.FLOOD, 0.9), 1e-9);
        // null condition defaults to CLEAR
        assertEquals(1.0, model.weatherMultiplier(null, 0.7), 1e-9);
    }

    @Test
    void travelTimeMinutes() {
        assertEquals(60.0, model.travelTimeMinutes(60, 60), 1e-9);
        assertEquals(30.0, model.travelTimeMinutes(30, 60), 1e-9);
        assertEquals(8.0, model.travelTimeMinutes(2, 15), 1e-9);
    }

    @Test
    void unpavedDetection() {
        assertTrue(model.isUnpaved(SurfaceType.GRAVEL));
        assertTrue(model.isUnpaved(SurfaceType.MUD));
        assertTrue(model.isUnpaved(SurfaceType.DIRT_TRACK));
        assertFalse(model.isUnpaved(SurfaceType.PAVED));
        assertFalse(model.isUnpaved(SurfaceType.BRIDGE));
    }
}
