package com.agrifleet.route_optimization_service.service;

import com.agrifleet.route_optimization_service.algorithm.AStarRouter;
import com.agrifleet.route_optimization_service.entity.RoadEdgeEntity;
import com.agrifleet.route_optimization_service.entity.RoadNodeEntity;
import com.agrifleet.route_optimization_service.repository.RoadEdgeRepository;
import com.agrifleet.route_optimization_service.repository.RoadNodeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoutingService {

    private final RoadNodeRepository nodeRepository;
    private final RoadEdgeRepository edgeRepository;
    private final RouteExecutionCacheService cacheService;

    public RoutingService(RoadNodeRepository nodeRepository,
                          RoadEdgeRepository edgeRepository,
                          RouteExecutionCacheService cacheService) {
        this.nodeRepository = nodeRepository;
        this.edgeRepository = edgeRepository;
        this.cacheService = cacheService;
    }

    public Map<String, Object> calculateRoute(Long startId, Long targetId, boolean useHeuristic, Long vehicleId) {
        List<RoadNodeEntity> nodes = nodeRepository.findAll();
        List<RoadEdgeEntity> edges = edgeRepository.findAll();

        Map<Long, RoadNodeEntity> nodeMap = nodes.stream()
                .collect(Collectors.toMap(RoadNodeEntity::getNodeId, n -> n));

        // The rural road network is UNDIRECTED: a road can be driven in both directions.
        // Most rows are stored mirrored (u->v and v->u), but some only exist one way
        // (e.g. 3->10 with no 10->3, 8->9 with no 9->8). Grouping only by uNode would
        // make such nodes unreachable as an origin, so register every edge under BOTH
        // endpoints and let the algorithm resolve the neighbour (see AStarRouter).
        Map<Long, List<RoadEdgeEntity>> adjacencyList = new HashMap<>();
        for (RoadEdgeEntity edge : edges) {
            adjacencyList.computeIfAbsent(edge.getUNode(), k -> new ArrayList<>()).add(edge);
            adjacencyList.computeIfAbsent(edge.getVNode(), k -> new ArrayList<>()).add(edge);
        }

        AStarRouter.PathResult result = AStarRouter.computeShortestPath(startId, targetId, nodeMap, adjacencyList, useHeuristic);

        Map<String, Object> response = new HashMap<>();
        response.put("algorithm", useHeuristic ? "ASTAR" : "DIJKSTRA");
        response.put("startNode", startId);
        response.put("targetNode", targetId);
        response.put("pathNodeSequence", result.pathSequence);
        response.put("totalDistanceKm", result.totalDistance);
        response.put("totalTravelTimeMins", result.totalDistance * 2.5);
        response.put("nodesVisitedCount", result.nodesVisited);

        try {
            cacheService.saveCacheEntry(vehicleId, startId, targetId, response);
        } catch (Exception ignored) {
        }

        return response;
    }
}