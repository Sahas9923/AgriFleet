package com.agrifleet_core_service.repository;

import com.agrifleet_core_service.entity.DepotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepotRepository extends JpaRepository<DepotEntity, Long> {
}
