INSERT INTO road_nodes
(node_id, node_name, lat, lng, elevation_meters, is_farm_gate, is_depot)
VALUES
(1, 'Main Machinery Depot Alpha', 8.311400, 80.403700, 92.0, 0, 1),
(2, 'Junction Medawachchiya Cross', 8.324500, 80.412000, 95.5, 0, 0),
(3, 'Canal Bridge Crossing Alpha', 8.338000, 80.428000, 89.0, 0, 0),
(4, 'Farm Gate Plot 1 (Booking 1)', 8.335000, 80.445000, 87.5, 1, 0),
(5, 'Farm Gate Plot 2 (Booking 2)', 8.362000, 80.412000, 98.0, 1, 0),
(6, 'South Agricultural Bypass', 8.295000, 80.395000, 94.0, 0, 0),
(7, 'Farm Gate Plot 3 (Booking 3)', 8.298000, 80.362000, 91.0, 1, 0),
(8, 'Gravel Road Intersect South', 8.275000, 80.390000, 90.0, 0, 0),
(9, 'Farm Gate Plot 4 (Booking 4)', 8.275000, 80.418000, 88.0, 1, 0),
(10, 'Sub-Depot Beta (East Sector)', 8.365000, 80.450000, 96.0, 0, 1);

INSERT INTO road_edges
(edge_id, u_node, v_node, base_distance_km, surface_type,
 max_weight_tonnes, weather_penalty_multiplier, computed_weight)
VALUES
(1, 1, 2, 1.75, 'PAVED_HIGHWAY', 45.0, 1.00, 1.750),
(2, 2, 1, 1.75, 'PAVED_HIGHWAY', 45.0, 1.00, 1.750),

(3, 2, 3, 2.20, 'GRAVEL', 28.0, 1.25, 2.750),
(4, 3, 2, 2.20, 'GRAVEL', 28.0, 1.25, 2.750),

(5, 3, 4, 1.90, 'DIRT_TRACK', 16.0, 1.60, 3.040),
(6, 4, 3, 1.90, 'DIRT_TRACK', 16.0, 1.60, 3.040),

(7, 2, 5, 4.20, 'PAVED_HIGHWAY', 40.0, 1.00, 4.200),
(8, 5, 2, 4.20, 'PAVED_HIGHWAY', 40.0, 1.00, 4.200),

(9, 1, 6, 2.10, 'PAVED_HIGHWAY', 45.0, 1.00, 2.100),
(10, 6, 1, 2.10, 'PAVED_HIGHWAY', 45.0, 1.00, 2.100),

(11, 6, 7, 3.70, 'GRAVEL', 25.0, 1.20, 4.440),
(12, 7, 6, 3.70, 'GRAVEL', 25.0, 1.20, 4.440),

(13, 6, 8, 2.30, 'DIRT_TRACK', 18.0, 1.50, 3.450),

(14, 8, 9, 3.10, 'MUDDY_FIELD', 12.0, 2.10, 6.510),

(15, 3, 10, 3.60, 'PAVED_HIGHWAY', 40.0, 1.00, 3.600),

(16, 4, 10, 3.40, 'GRAVEL', 22.0, 1.30, 4.420);