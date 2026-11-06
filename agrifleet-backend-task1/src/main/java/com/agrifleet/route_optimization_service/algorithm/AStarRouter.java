package com.agrifleet.route_optimization_service.algorithm;

import com.agrifleet.route_optimization_service.entity.RoadEdgeEntity;
import com.agrifleet.route_optimization_service.entity.RoadNodeEntity;

import java.util.*;

public class AStarRouter {

    public static class PathResult {
        public List<Long> pathSequence;
        public double totalDistance;
        public int nodesVisited;

        public PathResult(List<Long> pathSequence, double totalDistance, int nodesVisited) {
            this.pathSequence = pathSequence;
            this.totalDistance = totalDistance;
            this.nodesVisited = nodesVisited;
        }
    }

    private static class NodeRecord {
        Long nodeId;
        double fScore;

        public NodeRecord(Long nodeId, double fScore) {
            this.nodeId = nodeId;
            this.fScore = fScore;
        }
    }

    public static PathResult computeShortestPath(
            Long startId,
            Long targetId,
            Map<Long, RoadNodeEntity> nodeMap,
            Map<Long, List<RoadEdgeEntity>> adjacencyList,
            boolean useHeuristic) {

        RoadNodeEntity targetNode = nodeMap.get(targetId);
        if (targetNode == null || !nodeMap.containsKey(startId)) {
            throw new IllegalArgumentException("Invalid start or target node ID for routing.");
        }

        PriorityQueue<NodeRecord> openSet = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fScore));
        Set<Long> closedSet = new HashSet<>();

        Map<Long, Double> gScore = new HashMap<>();
        Map<Long, Double> fScore = new HashMap<>();
        Map<Long, Long> cameFrom = new HashMap<>();

        for (Long nodeId : nodeMap.keySet()) {
            gScore.put(nodeId, Double.MAX_VALUE);
            fScore.put(nodeId, Double.MAX_VALUE);
        }

        gScore.put(startId, 0.0);
        double initialH = useHeuristic ? calculateHaversineHeuristic(nodeMap.get(startId), targetNode) : 0.0;
        fScore.put(startId, initialH);
        openSet.add(new NodeRecord(startId, initialH));

        int nodesVisited = 0;

        while (!openSet.isEmpty()) {
            NodeRecord currentRecord = openSet.poll();
            Long currentId = currentRecord.nodeId;
            nodesVisited++;

            if (currentId.equals(targetId)) {
                List<Long> path = reconstructPath(cameFrom, currentId);
                return new PathResult(path, gScore.get(targetId), nodesVisited);
            }

            closedSet.add(currentId);

            List<RoadEdgeEntity> edges = adjacencyList.getOrDefault(currentId, Collections.emptyList());
            for (RoadEdgeEntity edge : edges) {
                // Edges are registered under both endpoints (undirected), so the neighbour is
                // whichever end is NOT the node currently being expanded.
                Long neighborId = edge.getUNode().equals(currentId) ? edge.getVNode() : edge.getUNode();
                if (closedSet.contains(neighborId)) {
                    continue;
                }

                double tentativeG = gScore.get(currentId) + edge.getComputedWeight();

                if (tentativeG < gScore.get(neighborId)) {
                    cameFrom.put(neighborId, currentId);
                    gScore.put(neighborId, tentativeG);
                    double h = useHeuristic ? calculateHaversineHeuristic(nodeMap.get(neighborId), targetNode) : 0.0;
                    double f = tentativeG + h;
                    fScore.put(neighborId, f);

                    openSet.removeIf(nr -> nr.nodeId.equals(neighborId));
                    openSet.add(new NodeRecord(neighborId, f));
                }
            }
        }

        throw new RuntimeException("Path not found between node " + startId + " and node " + targetId);
    }

    private static double calculateHaversineHeuristic(RoadNodeEntity a, RoadNodeEntity b) {
        double latDiff = a.getLat() - b.getLat();
        double lngDiff = a.getLng() - b.getLng();
        return Math.sqrt(latDiff * latDiff + lngDiff * lngDiff) * 111.0;
    }

    private static List<Long> reconstructPath(Map<Long, Long> cameFrom, Long current) {
        List<Long> path = new ArrayList<>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(0, current);
        }
        return path;
    }
}