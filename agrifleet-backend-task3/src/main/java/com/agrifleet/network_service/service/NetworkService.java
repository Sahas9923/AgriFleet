package com.agrifleet.network_service.service;

import com.agrifleet.network_service.algorithm.KruskalMST;
import com.agrifleet.network_service.algorithm.TarjanBridgeDetector;
import com.agrifleet.network_service.entity.RoadEdgeEntity;
import com.agrifleet.network_service.entity.RoadNodeEntity;
import com.agrifleet.network_service.repository.MstBackboneRepository;
import com.agrifleet.network_service.repository.NetworkBridgeRepository;
import com.agrifleet.network_service.repository.RoadEdgeRepository;
import com.agrifleet.network_service.repository.RoadNodeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NetworkService {

    private final NetworkBridgeRepository bridgeRepository;
    private final MstBackboneRepository mstRepository;
    private final RoadEdgeRepository roadEdgeRepository;
    private final RoadNodeRepository roadNodeRepository;

    public NetworkService(NetworkBridgeRepository bridgeRepository,
                          MstBackboneRepository mstRepository,
                          RoadEdgeRepository roadEdgeRepository,
                          RoadNodeRepository roadNodeRepository) {
        this.bridgeRepository = bridgeRepository;
        this.mstRepository = mstRepository;
        this.roadEdgeRepository = roadEdgeRepository;
        this.roadNodeRepository = roadNodeRepository;
    }

    public Map<String, Object> getGraph() {
        List<RoadNodeEntity> nodes = roadNodeRepository.findAll();
        List<RoadEdgeEntity> edges = roadEdgeRepository.findAll();

        List<Map<String, Object>> nodeList = new ArrayList<>();
        for (RoadNodeEntity n : nodes) {
            Map<String, Object> nodeMap = new HashMap<>();
            nodeMap.put("nodeId", n.getNodeId());
            nodeMap.put("nodeName", n.getNodeName());
            nodeMap.put("lat", n.getLat());
            nodeMap.put("lng", n.getLng());
            nodeMap.put("elevationMeters", n.getElevationMeters());
            nodeMap.put("isFarmGate", n.getIsFarmGate());
            nodeMap.put("isDepot", n.getIsDepot());
            nodeList.add(nodeMap);
        }

        List<Map<String, Object>> edgeList = new ArrayList<>();
        for (RoadEdgeEntity e : edges) {
            Map<String, Object> edgeMap = new HashMap<>();
            edgeMap.put("edgeId", e.getEdgeId());
            edgeMap.put("uNode", e.getUNode());
            edgeMap.put("vNode", e.getVNode());
            edgeMap.put("computedWeight", e.getComputedWeight());
            edgeMap.put("maxWeightTonnes", e.getMaxWeightTonnes());
            edgeList.add(edgeMap);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("nodes", nodeList);
        response.put("edges", edgeList);
        return response;
    }

    public Map<String, Object> analyzeRegion(Long regionId) {
        List<RoadEdgeEntity> dbEdges = roadEdgeRepository.findAll();
        List<TarjanBridgeDetector.Edge> edges = new ArrayList<>();

        int maxNodeId = 10;
        for (RoadEdgeEntity dbEdge : dbEdges) {
            edges.add(new TarjanBridgeDetector.Edge(
                    dbEdge.getUNode().intValue(),
                    dbEdge.getVNode().intValue(),
                    dbEdge.getComputedWeight()
            ));
            maxNodeId = Math.max(maxNodeId, Math.max(dbEdge.getUNode().intValue(), dbEdge.getVNode().intValue()));
        }

        TarjanBridgeDetector detector = new TarjanBridgeDetector();
        List<TarjanBridgeDetector.Edge> bridges = detector.findBridges(maxNodeId, edges);

        KruskalMST.MSTResult mstResult = KruskalMST.calculateMST(maxNodeId, edges);

        Map<String, Object> response = new HashMap<>();
        response.put("regionId", regionId);
        response.put("criticalBridges", bridges);
        response.put("mstBackboneEdges", mstResult.mstEdges);
        response.put("totalBackboneCost", mstResult.totalCost);

        return response;
    }

    public Map<String, Object> checkWeightLimit(Long uNode, Long vNode, Double vehicleWeightTonnes) {
        List<RoadEdgeEntity> allEdges = roadEdgeRepository.findAll();
        double bridgeLimit = 40.0;

        Optional<RoadEdgeEntity> matchedEdge = allEdges.stream()
                .filter(e -> (e.getUNode().equals(uNode) && e.getVNode().equals(vNode)) ||
                        (e.getUNode().equals(vNode) && e.getVNode().equals(uNode)))
                .findFirst();

        if (matchedEdge.isPresent() && matchedEdge.get().getMaxWeightTonnes() != null) {
            bridgeLimit = matchedEdge.get().getMaxWeightTonnes();
        }

        boolean isSafe = vehicleWeightTonnes <= bridgeLimit;

        Map<String, Object> result = new HashMap<>();
        result.put("uNode", uNode);
        result.put("vNode", vNode);
        result.put("vehicleWeightTonnes", vehicleWeightTonnes);
        result.put("bridgeLimitTonnes", bridgeLimit);
        result.put("isAllowed", isSafe);
        result.put("warning", isSafe ? "Weight limit acceptable." : "WARNING: Vehicle tonnage exceeds structural bridge tolerance! Rerouting recommended.");

        return result;
    }
}
