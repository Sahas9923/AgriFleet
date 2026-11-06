package com.agrifleet.network_service.repository;

import com.agrifleet.network_service.entity.RoadEdgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoadEdgeRepository extends JpaRepository<RoadEdgeEntity, Long> {
}
