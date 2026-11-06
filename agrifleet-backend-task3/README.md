# 🌾 AgriFleet: Task 3 — Network Analysis & Resilience Service

[![Java Version](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.1-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![SQLite](https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white)](https://www.sqlite.org/)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen?style=for-the-badge)]()
[![Module](https://img.shields.io/badge/NIBM_PDSA-Task_3-blue?style=for-the-badge)]()

---

## 📖 1. Project Overview & System Context

**AgriFleet** is an Intelligent Decision Support System (IDSS) engineered for agricultural harvester and tractor fleet logistics (an "Uber for Agricultural Machinery"). 

Within the 5-module AgriFleet ecosystem, **Task 3 (Network Analysis Module)** provides the proactive network resilience intelligence layer. Heavy agricultural machinery (combine harvesters weighing 12–18 tonnes) operates across fragile rural road networks with dirt tracks and weight-restricted bridges.

```text
+-----------------------------------------------------------------------------------+
|                         AGRIFLEET CENTRAL GUI / API GATEWAY                       |
+---------+------------------+------------------+------------------+----------------+
          |                  |                  |                  |                  |
          v                  v                  v                  v                  v
+------------------++------------------++------------------++------------------++------------------+
|     TASK 1       ||     TASK 2       ||     TASK 3       ||     TASK 4       ||     TASK 5       |
| Route            || Resource         || Network          || Intelligent      || Multi-Job        |
| Optimization     || Allocation       || Analysis         || Decision (MCDM)  || Optimization     |
| (A* / Dijkstra)  || (Hungarian)      || (Tarjan / MST)   || (TOPSIS)         || (Genetic Algo)   |
+------------------++------------------++------------------++------------------++------------------+
                                                │
                                                ▼ [TASK 3 MODULE]
                                    ┌───────────────────────┐
                                    │ 🚨 Tarjan Bridges     │
                                    │ 🌳 Kruskal's MST      │
                                    │ ⚖️ Weight Verifier    │
                                    └───────────────────────┘
```

### Core Responsibilities of Task 3:
1. **Network Resilience (Tarjan's Algorithm):** Proactively detects critical single-point-of-failure roads/bridges whose flooding or collapse disconnects farming communities.
2. **Infrastructure Backbone (Kruskal's MST):** Calculates the cost-minimal connection backbone across all regional machinery depots and farm clusters without cycles.
3. **Structural Weight Tolerance Check:** Validates whether heavy vehicle tonnage exceeds bridge tolerances before dispatch.

---

## 🎯 2. Learning Outcome Mapping (NIBM PDSA)

| Learning Outcome | Syllabus Requirement | Task 3 Implementation Evidence |
| :--- | :--- | :--- |
| **LO1** | Algorithmic reasoning, selection, and asymptotic complexity analysis | Derivation of Upper Bound ($\mathcal{O}$), Tight Bound ($\Theta$), and Lower Bound ($\Omega$) for Tarjan's DFS and Kruskal's MST. |
| **LO2** | Design and implementation of custom data structures for novel problems | Adjacency List graph models, Disjoint Set Union (DSU with Path Compression & Union by Rank), and discovery timestamps (`tin`/`low`). |

---

## 🧠 3. Algorithmic Architecture & Mathematical Foundations

```text
                           TASK 3: NETWORK ANALYSIS
                                      │
                 ┌────────────────────┴────────────────────┐
                 ▼                                         ▼
     🚨 SUB-PROBLEM 1: RESILIENCE             💰 SUB-PROBLEM 2: INFRASTRUCTURE COST
       Tarjan's Bridge Detection                  Kruskal's Algorithm (MST)
                 │                                         │
        DFS with tin[] & low[]                      Edge Sorting + DSU
         Time: Θ(V + E)                              Time: O(E log E)
         Space: O(V)                                 Space: O(V)
```

### A. Tarjan's Bridge Detection Algorithm
A road edge $e = (u, v)$ is a **Bridge (Cut-Edge)** if its removal strictly increases the number of connected components in the graph:

$$c(G \setminus \{e\}) > c(G)$$

* **Discovery Time $\text{tin}[u]$:** Timestamp when node $u$ is entered in DFS traversal.
* **Low-Link Value $\text{low}[u]$:** Earliest discovery timestamp reachable via subtree and back-edges:
  $$\text{low}[u] = \min \begin{cases} \text{tin}[u] \\ \text{tin}[v] & \text{for all back-edges } (u, v) \\ \text{low}[v] & \text{for all tree-edges } (u, v) \end{cases}$$
* **Fundamental Bridge Condition:**
  $$\mathbf{low}[v] > \mathbf{tin}[u] \implies \text{Edge } (u, v) \text{ is a Critical Bridge!}$$

### B. Kruskal's Minimum Spanning Tree (MST)
Computes a tree spanning all $|V|$ vertices using exactly $|V| - 1$ edges with minimal total weight:

$$\min \sum_{e \in T} w(e) \quad \text{subject to } T \text{ is acyclic and connects } V$$

* Employs **Disjoint Set Union (DSU)** with **Path Compression** and **Union by Rank** ($\mathcal{O}(\alpha(V))$ amortized per operation) to detect and prevent cycles.

---

## ⏱️ 4. Theoretical Complexity Summary

| Algorithm | Paradigm | Time (Best) | Time (Avg / Worst) | Space Complexity |
| :--- | :--- | :---: | :---: | :---: |
| **Tarjan's Bridge Detection** | Single DFS Traversal | $\Omega(V + E)$ | $\Theta(V + E)$ | $\mathcal{O}(V)$ |
| **Kruskal's MST Backbone** | Greedy + DSU | $\Omega(E \log E)$ | $\mathcal{O}(E \log E)$ | $\mathcal{O}(V)$ |
| **Bridge Weight Verifier** | Database Lookup | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ |

---

## 📂 5. Project Directory Structure

```text
agrifleet-backend-task3/
├── pom.xml                                      # Maven dependencies & build plugins
├── mvnw / mvnw.cmd                              # Maven wrapper scripts
├── README.md                                    # Project documentation
│
├── src/
│   ├── main/
│   │   ├── java/com/agrifleet/network_service/
│   │   │   ├── NetworkServiceApplication.java   # Spring Boot entrypoint
│   │   │   │
│   │   │   ├── algorithm/                       # Pure algorithmic engine
│   │   │   │   ├── TarjanBridgeDetector.java    # Tarjan's DFS bridge detector
│   │   │   │   └── KruskalMST.java              # Kruskal's MST with DSU
│   │   │   │
│   │   │   ├── controller/                      # REST API Layer
│   │   │   │   └── NetworkController.java       # HTTP endpoint handlers
│   │   │   │
│   │   │   ├── entity/                          # SQLite JPA Entities
│   │   │   │   ├── RoadEdgeEntity.java          # road_edges table entity
│   │   │   │   ├── NetworkBridgeEntity.java     # network_bridges table entity
│   │   │   │   └── MstBackboneEntity.java       # mst_backbone table entity
│   │   │   │
│   │   │   ├── repository/                      # Spring Data JPA interfaces
│   │   │   │   ├── RoadEdgeRepository.java
│   │   │   │   ├── NetworkBridgeRepository.java
│   │   │   │   └── MstBackboneRepository.java
│   │   │   │
│   │   │   └── service/                         # Business orchestration service
│   │   │       └── NetworkService.java          # Orchestrates DB, Tarjan & Kruskal
│   │   │
│   │   └── resources/
│   │       ├── application.properties           # SQLite datasource & port configuration
│   │       ├── schema.sql                       # Database DDL table definitions
│   │       └── data.sql                         # Regional benchmark seed data
│   │
│   └── test/java/com/agrifleet/network_service/ # Test Suites
│       ├── NetworkServiceApplicationTests.java  # Spring Boot context loader test
│       ├── NetworkAnalysisIntegrationTest.java  # End-to-end integration test
│       └── algorithm/                           # Algorithm unit test suites
│           └── TarjanBridgeDetectorTest.java    # Tarjan unit tests (lines, loops, clusters)
```

---

## 🔌 6. REST API Specification

### 1. Run Full Regional Network Analysis
* **Endpoint:** `GET /api/network/analyze`
* **Query Parameter:** `regionId` (e.g. `101`)
* **Sample Request:**
  ```http
  GET http://localhost:8083/api/network/analyze?regionId=101
  ```
* **Sample JSON Response:**
  ```json
  {
    "regionId": 101,
    "totalBackboneCost": 31.84,
    "criticalBridges": [
      { "u": 2, "v": 3, "weight": 2.75 },
      { "u": 2, "v": 5, "weight": 4.20 },
      { "u": 1, "v": 2, "weight": 1.75 },
      { "u": 6, "v": 7, "weight": 4.44 },
      { "u": 8, "v": 9, "weight": 6.51 },
      { "u": 6, "v": 8, "weight": 3.45 },
      { "u": 1, "v": 6, "weight": 2.10 }
    ],
    "mstBackboneEdges": [
      { "u": 1, "v": 2, "weight": 1.75 },
      { "u": 1, "v": 6, "weight": 2.10 },
      { "u": 2, "v": 3, "weight": 2.75 },
      { "u": 3, "v": 4, "weight": 3.04 },
      { "u": 6, "v": 8, "weight": 3.45 },
      { "u": 3, "v": 10, "weight": 3.60 },
      { "u": 2, "v": 5, "weight": 4.20 },
      { "u": 6, "v": 7, "weight": 4.44 },
      { "u": 8, "v": 9, "weight": 6.51 }
    ]
  }
  ```

---

### 2. Verify Bridge Weight Tolerance
* **Endpoint:** `POST /api/network/weight-check`
* **Sample Request Body:**
  ```json
  {
    "uNode": 2,
    "vNode": 3,
    "vehicleWeightTonnes": 25.0
  }
  ```
* **Sample JSON Response (Clearance Granted):**
  ```json
  {
    "uNode": 2,
    "vNode": 3,
    "vehicleWeightTonnes": 25.0,
    "bridgeLimitTonnes": 28.0,
    "isAllowed": true,
    "warning": "Weight limit acceptable."
  }
  ```

---

## 🚀 7. How to Build & Run

### Prerequisites
* **Java Development Kit (JDK):** Version 17 or higher
* **Node.js & npm:** (For running the frontend dashboard)
* **SQLite:** Included automatically via Xerial SQLite JDBC

### Running the Backend Service:
```bash
# 1. Clone the repository
git clone https://github.com/AgriFleet/agrifleet-backend-task3.git
cd agrifleet-backend-task3

# 2. Start the Spring Boot microservice on port 8083
./mvnw spring-boot:run
```


