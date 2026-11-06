package com.agrifleet_core_service;

import com.agrifleet_core_service.entity.DepotEntity;
import com.agrifleet_core_service.repository.DepotRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DepotEndpointsTest {

    @Autowired
    private DepotRepository depotRepository;

    @Test
    void shouldPersistAndFetchDepots() {
        DepotEntity depot = new DepotEntity();
        depot.setDepotName("North Depot");
        depot.setLatitude(8.3114);
        depot.setLongitude(80.4037);
        depot.setAddress("North field hub");

        DepotEntity saved = depotRepository.save(depot);

        assertThat(saved.getDepotId()).isNotNull();
        assertThat(depotRepository.findAll()).isNotEmpty();
    }
}
