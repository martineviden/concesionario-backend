-- Insertar tipos de vehículos
INSERT INTO tipo_vehiculo (marca, modelo, precio, tipo_vehiculo, imagen) VALUES 
('Toyota', 'Corolla', 22000.00, 'COCHE', 'toyota_corolla.jpg'),
('Honda', 'CBR600RR', 10500.00, 'MOTO', 'honda_cbr600rr.jpg'),
('Volkswagen', 'Transporter', 32000.00, 'FURGONETA', 'vw_transporter.jpg'),
('BMW', 'Serie 3', 38000.00, 'COCHE', 'bmw_serie3.jpg'),
('Yamaha', 'MT-07', 7500.00, 'MOTO', 'yamaha_mt07.jpg'),
('Ford', 'Focus', 19500.00, 'COCHE', 'ford_focus.jpg');

-- Insertar vehículos
INSERT INTO vehiculo (matricula, id_tipo_vehiculo, color, kilometraje, disponibilidad, ubicacion, combustible, etiqueta, autonomia, puertas, aire_acondicionado, plazas, transmision) VALUES
('1234ABC', 1, 'Rojo', 15000, TRUE, 'MADRID', 'GASOLINA', 'C', NULL, 5, TRUE, 5, 'MANUAL'),
('5678DEF', 2, 'Negro', 8000, TRUE, 'BARCELONA', 'GASOLINA', 'B', NULL, NULL, NULL, 2, 'MANUAL'),
('9012GHI', 3, 'Blanco', 45000, TRUE, 'VALENCIA', 'DIESEL', 'C', NULL, 3, TRUE, 3, 'MANUAL'),
('3456JKL', 4, 'Azul', 22000, FALSE, 'SEVILLA', 'DIESEL', 'C', NULL, 5, TRUE, 5, 'AUTOMATICO'),
('7890MNO', 5, 'Amarillo', 3000, TRUE, 'BALEARES', 'GASOLINA', 'B', NULL, NULL, NULL, 2, 'MANUAL'),
('2345PQR', 6, 'Gris', 18000, TRUE, 'ALICANTE', 'GASOLINA', 'ECO', NULL, 5, TRUE, 5, 'AUTOMATICO');