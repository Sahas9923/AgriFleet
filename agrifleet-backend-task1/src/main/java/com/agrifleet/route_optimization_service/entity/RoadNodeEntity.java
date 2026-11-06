package com.agrifleet.route_optimization_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "road_nodes")
public class RoadNodeEntity {

    @Id
    @Column(name = "node_id")
    private Long nodeId;

    @Column(name = "node_name")
    private String nodeName;

    @Column(name = "lat", nullable = false)
    private Double lat;

    @Column(name = "lng", nullable = false)
    private Double lng;

    @Column(name = "elevation_meters")
    private Double elevationMeters = 0.0;

    @Column(name = "is_farm_gate")
    private Integer isFarmGate = 0;

    @Column(name = "is_depot")
    private Integer isDepot = 0;

    // --- Getters and Setters ---
    public Long getNodeId() { return nodeId; }
    public void setNodeId(Long nodeId) { this.nodeId = nodeId; }

    public String getNodeName() { return nodeName; }
    public void setNodeName(String nodeName) { this.nodeName = nodeName; }

    public Double getLat() { return lat; }
    public void setLat(Double lat) { this.lat = lat; }

    public Double getLng() { return lng; }
    public void setLng(Double lng) { this.lng = lng; }

    public Double getElevationMeters() { return elevationMeters; }
    public void setElevationMeters(Double elevationMeters) { this.elevationMeters = elevationMeters; }

    public Integer getIsFarmGate() { return isFarmGate; }
    public void setIsFarmGate(Integer isFarmGate) { this.isFarmGate = isFarmGate; }

    public Integer getIsDepot() { return isDepot; }
    public void setIsDepot(Integer isDepot) { this.isDepot = isDepot; }
}