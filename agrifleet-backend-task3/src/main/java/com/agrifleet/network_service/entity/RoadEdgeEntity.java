package com.agrifleet.network_service.entity;

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

    @Column(name = "computed_weight", nullable = false)
    private Double computedWeight;

    @Column(name = "max_weight_tonnes")
    private Double maxWeightTonnes;

    public Long getEdgeId() { return edgeId; }
    public Long getUNode() { return uNode; }
    public Long getVNode() { return vNode; }
    public Double getComputedWeight() { return computedWeight; }
    public Double getMaxWeightTonnes() { return maxWeightTonnes; }
}
