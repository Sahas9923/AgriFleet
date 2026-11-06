package com.agrifleet.route_optimization_service.repository;

import com.agrifleet.route_optimization_service.entity.RoadEdgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoadEdgeRepository extends JpaRepository<RoadEdgeEntity, Long> {
    List<RoadEdgeEntity> findByUNode(Long uNode);
}