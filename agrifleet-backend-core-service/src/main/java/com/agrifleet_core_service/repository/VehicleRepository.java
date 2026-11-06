package com.agrifleet_core_service.repository;

import com.agrifleet_core_service.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
}
