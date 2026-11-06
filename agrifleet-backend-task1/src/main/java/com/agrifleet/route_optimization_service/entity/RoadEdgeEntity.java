package com.agrifleet.route_optimization_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "road_edges")
public class RoadEdgeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edge_id")
    private Long edgeId;

    @Column(name = "u_node", nullable = false)
    private Long uNode;

    @Column(name = "v_node", nullable = false)
    private Long vNode;

    @Column(name = "base_distance_km", nullable = false)
    private Double baseDistanceKm;

    @Column(name = "surface_type")
    private String surfaceType = "PAVED_HIGHWAY";

    @Column(name = "max_weight_tonnes")
    private Double maxWeightTonnes = 40.0;

    @Column(name = "weather_penalty_multiplier")
    private Double weatherPenaltyMultiplier = 1.0;

    @Column(name = "computed_weight", nullable = false)
    private Double computedWeight;

    // --- Getters and Setters ---
    public Long getEdgeId() { return edgeId; }
    public void setEdgeId(Long edgeId) { this.edgeId = edgeId; }

    public Long getUNode() { return uNode; }
    public void setUNode(Long uNode) { this.uNode = uNode; }

    public Long getVNode() { return vNode; }
    public void setVNode(Long vNode) { this.vNode = vNode; }

    public Double getBaseDistanceKm() { return baseDistanceKm; }
    public void setBaseDistanceKm(Double baseDistanceKm) { this.baseDistanceKm = baseDistanceKm; }

    public String getSurfaceType() { return surfaceType; }
    public void setSurfaceType(String surfaceType) { this.surfaceType = surfaceType; }

    public Double getMaxWeightTonnes() { return maxWeightTonnes; }
    public void setMaxWeightTonnes(Double maxWeightTonnes) { this.maxWeightTonnes = maxWeightTonnes; }

    public Double getWeatherPenaltyMultiplier() { return weatherPenaltyMultiplier; }
    public void setWeatherPenaltyMultiplier(Double weatherPenaltyMultiplier) { this.weatherPenaltyMultiplier = weatherPenaltyMultiplier; }

    public Double getComputedWeight() { return computedWeight; }
    public void setComputedWeight(Double computedWeight) { this.computedWeight = computedWeight; }
}