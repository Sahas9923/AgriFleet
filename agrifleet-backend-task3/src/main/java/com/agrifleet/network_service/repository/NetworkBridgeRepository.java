package com.agrifleet.network_service.repository;

import com.agrifleet.network_service.entity.NetworkBridgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NetworkBridgeRepository extends JpaRepository<NetworkBridgeEntity, Long> {
    List<NetworkBridgeEntity> findByRegionId(Long regionId);
}
