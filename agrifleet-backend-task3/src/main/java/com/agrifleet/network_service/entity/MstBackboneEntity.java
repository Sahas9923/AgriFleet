package com.agrifleet.network_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "mst_logistics_backbone")
public class MstBackboneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "backbone_id")
    private Long backboneId;

    @Column(name = "region_id", nullable = false)
    private Long regionId;

    @Column(name = "mst_edge_list", nullable = false)
    private String mstEdgeList;

    @Column(name = "total_backbone_cost", nullable = false)
    private Double totalBackboneCost;

    @Column(name = "last_recalculated", insertable = false, updatable = false)
    private String lastRecalculated;

    public Long getBackboneId() { return backboneId; }
    public void setBackboneId(Long backboneId) { this.backboneId = backboneId; }
    public Long getRegionId() { return regionId; }
    public void setRegionId(Long regionId) { this.regionId = regionId; }
    public String getMstEdgeList() { return mstEdgeList; }
    public void setMstEdgeList(String mstEdgeList) { this.mstEdgeList = mstEdgeList; }
    public Double getTotalBackboneCost() { return totalBackboneCost; }
    public void setTotalBackboneCost(Double totalBackboneCost) { this.totalBackboneCost = totalBackboneCost; }
    public String getLastRecalculated() { return lastRecalculated; }
    public void setLastRecalculated(String lastRecalculated) { this.lastRecalculated = lastRecalculated; }
}
