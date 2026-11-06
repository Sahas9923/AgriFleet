package com.agrifleet.route_optimization_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "route_execution_cache")
public class RouteExecutionCacheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Long routeId;

    @Column(name = "vehicle_id", nullable = false)
    private Long vehicleId;

    @Column(name = "origin_node", nullable = false)
    private Long originNode;

    @Column(name = "destination_node", nullable = false)
    private Long destinationNode;

    @Column(name = "path_node_sequence", nullable = false)
    private String pathNodeSequence;

    @Column(name = "total_distance_km", nullable = false)
    private Double totalDistanceKm;

    @Column(name = "total_travel_time_mins", nullable = false)
    private Double totalTravelTimeMins;

    @Column(name = "nodes_visited_count", nullable = false)
    private Integer nodesVisitedCount;

    @Column(name = "algorithm_used", nullable = false)
    private String algorithmUsed;

    @Column(name = "computed_at", insertable = false, updatable = false)
    private String computedAt;

    // --- Getters and Setters ---
    public Long getRouteId() { return routeId; }
    public void setRouteId(Long routeId) { this.routeId = routeId; }

    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public Long getOriginNode() { return originNode; }
    public void setOriginNode(Long originNode) { this.originNode = originNode; }

    public Long getDestinationNode() { return destinationNode; }
    public void setDestinationNode(Long destinationNode) { this.destinationNode = destinationNode; }

    public String getPathNodeSequence() { return pathNodeSequence; }
    public void setPathNodeSequence(String pathNodeSequence) { this.pathNodeSequence = pathNodeSequence; }

    public Double getTotalDistanceKm() { return totalDistanceKm; }
    public void setTotalDistanceKm(Double totalDistanceKm) { this.totalDistanceKm = totalDistanceKm; }

    public Double getTotalTravelTimeMins() { return totalTravelTimeMins; }
    public void setTotalTravelTimeMins(Double totalTravelTimeMins) { this.totalTravelTimeMins = totalTravelTimeMins; }

    public Integer getNodesVisitedCount() { return nodesVisitedCount; }
    public void setNodesVisitedCount(Integer nodesVisitedCount) { this.nodesVisitedCount = nodesVisitedCount; }

    public String getAlgorithmUsed() { return algorithmUsed; }
    public void setAlgorithmUsed(String algorithmUsed) { this.algorithmUsed = algorithmUsed; }

    public String getComputedAt() { return computedAt; }
    public void setComputedAt(String computedAt) { this.computedAt = computedAt; }
}