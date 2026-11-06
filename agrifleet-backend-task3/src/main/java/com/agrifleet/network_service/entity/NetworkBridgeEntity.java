package com.agrifleet.network_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "network_bridges_and_cuts")
public class NetworkBridgeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cut_id")
    private Long cutId;

    @Column(name = "region_id", nullable = false)
    private Long regionId;

    @Column(name = "u_node", nullable = false)
    private Long uNode;

    @Column(name = "v_node", nullable = false)
    private Long vNode;

    @Column(name = "is_bridge")
    private Integer isBridge = 1;

    @Column(name = "is_articulation_point")
    private Integer isArticulationPoint = 0;

    @Column(name = "max_tonnage_limit")
    private Double maxTonnageLimit;

    @Column(name = "is_severed")
    private Integer isSevered = 0;

    @Column(name = "isolated_subgraph_nodes")
    private String isolatedSubgraphNodes;

    @Column(name = "discovered_at", insertable = false, updatable = false)
    private String discoveredAt;

    public Long getCutId() { return cutId; }
    public void setCutId(Long cutId) { this.cutId = cutId; }
    public Long getRegionId() { return regionId; }
    public void setRegionId(Long regionId) { this.regionId = regionId; }
    public Long getUNode() { return uNode; }
    public void setUNode(Long uNode) { this.uNode = uNode; }
    public Long getVNode() { return vNode; }
    public void setVNode(Long vNode) { this.vNode = vNode; }
    public Integer getIsBridge() { return isBridge; }
    public void setIsBridge(Integer isBridge) { this.isBridge = isBridge; }
    public Integer getIsArticulationPoint() { return isArticulationPoint; }
    public void setIsArticulationPoint(Integer isArticulationPoint) { this.isArticulationPoint = isArticulationPoint; }
    public Double getMaxTonnageLimit() { return maxTonnageLimit; }
    public void setMaxTonnageLimit(Double maxTonnageLimit) { this.maxTonnageLimit = maxTonnageLimit; }
    public Integer getIsSevered() { return isSevered; }
    public void setIsSevered(Integer isSevered) { this.isSevered = isSevered; }
    public String getIsolatedSubgraphNodes() { return isolatedSubgraphNodes; }
    public void setIsIsolatedSubgraphNodes(String isolatedSubgraphNodes) { this.isolatedSubgraphNodes = isolatedSubgraphNodes; }
    public String getDiscoveredAt() { return discoveredAt; }
    public void setDiscoveredAt(String discoveredAt) { this.discoveredAt = discoveredAt; }
}
