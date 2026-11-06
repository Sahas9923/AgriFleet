package com.agrifleet.network_service.repository;

import com.agrifleet.network_service.entity.MstBackboneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MstBackboneRepository extends JpaRepository<MstBackboneEntity, Long> {
    Optional<MstBackboneEntity> findByRegionId(Long regionId);
}
