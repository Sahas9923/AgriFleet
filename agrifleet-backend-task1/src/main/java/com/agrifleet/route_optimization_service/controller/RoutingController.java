package com.agrifleet.route_optimization_service.controller;

import com.agrifleet.route_optimization_service.entity.RoadEdgeEntity;
import com.agrifleet.route_optimization_service.entity.RoadNodeEntity;
import com.agrifleet.route_optimization_service.entity.RouteExecutionCacheEntity;
import com.agrifleet.route_optimization_service.repository.RoadEdgeRepository;
import com.agrifleet.route_optimization_service.repository.RoadNodeRepository;
import com.agrifleet.route_optimization_service.repository.RouteExecutionCacheRepository;
import com.agrifleet.route_optimization_service.service.RoutingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/routing")
public class RoutingController {

    private final RoadNodeRepository nodeRepository;
    private final RoadEdgeRepository edgeRepository;
    private final RouteExecutionCacheRepository cacheRepository;
    private final RoutingService routingService;

    public RoutingController(RoadNodeRepository nodeRepository,
                             RoadEdgeRepository edgeRepository,
                             RouteExecutionCacheRepository cacheRepository,
                             RoutingService routingService) {
        this.nodeRepository = nodeRepository;
        this.edgeRepository = edgeRepository;
        this.cacheRepository = cacheRepository;
        this.routingService = routingService;
    }

    @GetMapping("/nodes")
    public ResponseEntity<List<RoadNodeEntity>> getRoadNodes() {
        return ResponseEntity.ok(nodeRepository.findAll());
    }

    @GetMapping("/edges")
    public ResponseEntity<List<RoadEdgeEntity>> getRoadEdges() {
        return ResponseEntity.ok(edgeRepository.findAll());
    }

    @GetMapping("/optimize/astar")
    public ResponseEntity<Map<String, Object>> calculateRouteAStar(
            @RequestParam Long start, @RequestParam Long target) {
        return ResponseEntity.ok(routingService.calculateRoute(start, target, true, 1L));
    }

    @GetMapping("/optimize/dijkstra")
    public ResponseEntity<Map<String, Object>> calculateRouteDijkstra(
            @RequestParam Long start, @RequestParam Long target) {
        return ResponseEntity.ok(routingService.calculateRoute(start, target, false, 1L));
    }

    @GetMapping("/cache")
    public ResponseEntity<List<RouteExecutionCacheEntity>> getRouteCache() {
        return ResponseEntity.ok(cacheRepository.findAll());
    }
}