package com.agrifleet.network_service.entity;

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
    private Double elevationMeters;

    @Column(name = "is_farm_gate")
    private Integer isFarmGate;

    @Column(name = "is_depot")
    private Integer isDepot;

    public Long getNodeId() { return nodeId; }
    public String getNodeName() { return nodeName; }
    public Double getLat() { return lat; }
    public Double getLng() { return lng; }
    public Double getElevationMeters() { return elevationMeters; }
    public Integer getIsFarmGate() { return isFarmGate; }
    public Integer getIsDepot() { return isDepot; }
}
