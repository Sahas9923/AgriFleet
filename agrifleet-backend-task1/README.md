# 🚜 AgriFleet — Task 1: Route Optimization Microservice

**Module:** Programming, Data Structures and Algorithms (PDSA) — Coursework 1
**NIBM · BSc (Hons) Computing 26.1**

A Spring Boot microservice that exposes the **Intelligent Route Optimization Module**
(Task 1) as a RESTful API. It computes optimal rural transit paths between machinery
depots and farm parcels, applies weather-aware road resistance, and tracks live GPS
vehicle paths.

## ✨ Features

| Feature | Description |
|---|---|
| **Dynamic Rural Pathfinding** | A\* (selected) & Dijkstra (baseline) over the rural road network |
| **Weather-Aware Road Resistance** | Rain penalties applied to unpaved tracks (gravel / mud / dirt) |
| **Live Vehicle Path Tracing** | Exact coordinate-level GPS path vectors for real-time tracking |
| **Bridge Weight Restrictions** | Overloaded bridges pruned when vehicle tonnage exceeds tolerance |
| **Algorithm Benchmarking** | `POST /benchmark` generates the execution-time-vs-N curve (report Ch. 8) |

## 🧱 Tech Stack

- Java 17 · Spring Boot 4.x (`webmvc`, `data-jpa`, `validation`)
- **SQLite** (`sqlite-jdbc` + Hibernate community dialect)
- Lombok · Maven wrapper

## 🚀 Running

```bash
cd route-optimization-service
./mvnw spring-boot:run        # Windows: mvnw.cmd spring-boot:run
```

- Service: **`http://localhost:8081`**
- SQLite DB: `route_service.db` (auto-created; sample network seeded on first boot)
- Smoke test:
  ```bash
  curl -X POST http://localhost:8081/api/v1/routes/optimize \
    -H "Content-Type: application/json" \
    -d '{"startNodeId":1,"endNodeId":5,"algorithm":"ASTAR","weatherAware":true}'
  ```

## 📡 API Overview (base: `/api/v1`)

| Area | Endpoints |
|---|---|
| Info | `GET /info` |
| Nodes | `POST/GET /nodes` · `GET/PUT/DELETE /nodes/{id}` |
| Edges | `POST/GET /edges` · `GET/PUT/DELETE /edges/{id}` |
| Graph | `POST /graph/import` · `GET /graph/stats` |
| Weather | `PUT /weather/{nodeId}` · `GET /weather` · `GET /weather/{nodeId}` |
| Vehicles | `POST/GET /vehicles` · `GET/PUT /vehicles/{id}` · `PUT /vehicles/{id}/location` |
| **Routes** | `POST /routes/optimize` ⭐ · `GET /routes` · `GET /routes/{id}` · `GET /routes/{id}/path` · `POST /routes/compare` |
| Benchmark | `POST /benchmark` |

> 📖 Full request/response documentation, SQLite schema, and frontend integration guide:
> [`docs/TASK1_Route_Optimization_Microservice_API.md`](docs/TASK1_Route_Optimization_Microservice_API.md)

## 🧪 Tests

```bash
cd route-optimization-service
./mvnw test
```

Covers: A\* / Dijkstra optimality, not-found handling, A\* vs Dijkstra expansion,
Haversine distance, and the surface/weather cost model.

## 📁 Repository Layout

```
route-optimization-service/
├── src/main/java/com/agrifleet/route_optimization_service/
│   ├── config/        # CORS, data seeder
│   ├── controller/    # REST endpoints
│   ├── dto/           # request/response records
│   ├── exception/     # global error handling
│   ├── model/         # JPA entities (SQLite schema)
│   ├── repository/    # Spring Data repositories
│   └── service/
│       ├── algorithm/ # A*, Dijkstra, Haversine, graph structures
│       └── ...        # graph / route / weather / vehicle / benchmark services
docs/                  # API design + per-commit summaries
```

## 📦 Commits

| Commit | Scope |
|---|---|
| 1 | Data layer (entities, repositories, seeder) |
| 2 | Graph & fleet management API |
| 3 | Core routing engine (A\* / Dijkstra) |
| 4 | Benchmarking & polish (info, validation, tests) |
