package com.agrifleet.route_optimization_service.service;

import com.agrifleet.route_optimization_service.entity.RouteExecutionCacheEntity;
import com.agrifleet.route_optimization_service.repository.RouteExecutionCacheRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RouteExecutionCacheService {

    private final RouteExecutionCacheRepository cacheRepository;

    public RouteExecutionCacheService(RouteExecutionCacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    public void saveCacheEntry(Long vehicleId, Long origin, Long destination, Map<String, Object> routeResult) {
        RouteExecutionCacheEntity cache = new RouteExecutionCacheEntity();
        cache.setVehicleId(vehicleId != null ? vehicleId : 1L);
        cache.setOriginNode(origin);
        cache.setDestinationNode(destination);
        cache.setPathNodeSequence(routeResult.get("pathNodeSequence").toString());
        cache.setTotalDistanceKm((Double) routeResult.get("totalDistanceKm"));
        cache.setTotalTravelTimeMins((Double) routeResult.get("totalTravelTimeMins"));
        cache.setNodesVisitedCount((Integer) routeResult.get("nodesVisitedCount"));
        cache.setAlgorithmUsed((String) routeResult.get("algorithm"));

        cacheRepository.save(cache);
    }

    public List<RouteExecutionCacheEntity> getAllCachedRoutes() {
        return cacheRepository.findAll();
    }
}