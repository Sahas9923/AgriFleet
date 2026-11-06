package com.agrifleet.network_service.repository;

import com.agrifleet.network_service.entity.RoadNodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoadNodeRepository extends JpaRepository<RoadNodeEntity, Long> {
}
