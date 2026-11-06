package com.agrifleet.route_optimization_service.repository;

import com.agrifleet.route_optimization_service.entity.RouteExecutionCacheEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteExecutionCacheRepository extends JpaRepository<RouteExecutionCacheEntity, Long> {
}