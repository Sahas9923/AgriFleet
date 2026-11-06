package com.agrifleet.network_service.controller;

import com.agrifleet.network_service.service.NetworkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/network-analysis")
public class NetworkController {

    private final NetworkService networkService;

    public NetworkController(NetworkService networkService) {
        this.networkService = networkService;
    }

    @GetMapping("/graph")
    public ResponseEntity<Map<String, Object>> getGraph() {
        return ResponseEntity.ok(networkService.getGraph());
    }

    @GetMapping("/analyze")
    public ResponseEntity<Map<String, Object>> analyzeRegion(@RequestParam Long regionId) {
        return ResponseEntity.ok(networkService.analyzeRegion(regionId));
    }

    @PostMapping("/weight-check")
    public ResponseEntity<Map<String, Object>> checkWeightLimit(@RequestBody Map<String, Object> payload) {
        Long uNode = Long.valueOf(payload.get("uNode").toString());
        Long vNode = Long.valueOf(payload.get("vNode").toString());
        Double weight = Double.valueOf(payload.get("vehicleWeightTonnes").toString());
        return ResponseEntity.ok(networkService.checkWeightLimit(uNode, vNode, weight));
    }
}
