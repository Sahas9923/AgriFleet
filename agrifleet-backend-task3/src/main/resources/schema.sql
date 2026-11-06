PRAGMA foreign_keys = ON;

DROP TABLE IF EXISTS mst_logistics_backbone;
DROP TABLE IF EXISTS network_bridges_and_cuts;
DROP TABLE IF EXISTS road_edges;
DROP TABLE IF EXISTS road_nodes;

CREATE TABLE road_nodes (
    node_id INTEGER PRIMARY KEY,
    node_name TEXT,
    lat REAL NOT NULL,
    lng REAL NOT NULL,
    elevation_meters REAL DEFAULT 0.0,
    is_farm_gate INTEGER DEFAULT 0,
    is_depot INTEGER DEFAULT 0
);

CREATE TABLE road_edges (
    edge_id INTEGER PRIMARY KEY AUTOINCREMENT,
    u_node INTEGER NOT NULL,
    v_node INTEGER NOT NULL,
    base_distance_km REAL NOT NULL,
    surface_type TEXT DEFAULT 'PAVED_HIGHWAY',
    max_weight_tonnes REAL DEFAULT 40.0,
    weather_penalty_multiplier REAL DEFAULT 1.0,
    computed_weight REAL NOT NULL,
    FOREIGN KEY (u_node)
        REFERENCES road_nodes(node_id)
        ON DELETE CASCADE,
    FOREIGN KEY (v_node)
        REFERENCES road_nodes(node_id)
        ON DELETE CASCADE
);

CREATE TABLE network_bridges_and_cuts (
    cut_id INTEGER PRIMARY KEY AUTOINCREMENT,
    region_id INTEGER NOT NULL,
    u_node INTEGER NOT NULL,
    v_node INTEGER NOT NULL,
    is_bridge INTEGER DEFAULT 1,
    is_articulation_point INTEGER DEFAULT 0,
    max_tonnage_limit REAL NOT NULL,
    is_severed INTEGER DEFAULT 0,
    isolated_subgraph_nodes TEXT,
    discovered_at TEXT DEFAULT (DATETIME('now')),
    FOREIGN KEY (u_node)
        REFERENCES road_nodes(node_id),
    FOREIGN KEY (v_node)
        REFERENCES road_nodes(node_id)
);

CREATE TABLE mst_logistics_backbone (
    backbone_id INTEGER PRIMARY KEY AUTOINCREMENT,
    region_id INTEGER NOT NULL,
    mst_edge_list TEXT NOT NULL,
    total_backbone_cost REAL NOT NULL,
    last_recalculated TEXT DEFAULT (DATETIME('now'))
);

CREATE INDEX idx_road_edges_uv
ON road_edges(u_node, v_node);

CREATE INDEX idx_network_bridges_region
ON network_bridges_and_cuts(region_id);

CREATE INDEX idx_mst_backbone_region
ON mst_logistics_backbone(region_id);