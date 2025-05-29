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
('9999III', 16, 'Negro', 9000, TRUE, 'TOLEDO', 'GASOLINA', 'B', NULL, NULL, NULL, 2, 'MANUAL'),
('A123BCD', 1, 'Gris', 18000, TRUE, 'MADRID', 'GASOLINA', 'C', NULL, 5, TRUE, 5, 'MANUAL'),
('B456CDE', 1, 'Blanco', 21000, FALSE, 'BARCELONA', 'GASOLINA', 'ECO', NULL, 5, TRUE, 5, 'AUTOMATICO'),
('C789DEF', 5, 'Negro', 5000, TRUE, 'VALENCIA', 'GASOLINA', 'B', NULL, NULL, NULL, 2, 'MANUAL'),
('D012EFG', 5, 'Azul', 3000, TRUE, 'SEVILLA', 'GASOLINA', 'B', NULL, NULL, NULL, 2, 'MANUAL'),
('E345FGH', 14, 'Rojo', 15000, TRUE, 'MURCIA', 'ELECTRICO', 'CERO', 420, 4, TRUE, 5, 'AUTOMATICO'),
('F678GHI', 14, 'Negro', 8000, TRUE, 'MADRID', 'ELECTRICO', 'CERO', 390, 4, TRUE, 5, 'AUTOMATICO'),
('G901HIJ', 20, 'Gris', 27000, TRUE, 'ZARAGOZA', 'DIESEL', 'C', NULL, 5, TRUE, 5, 'MANUAL'),
('H234IJK', 20, 'Blanco', 19000, FALSE, 'ALICANTE', 'DIESEL', 'C', NULL, 5, TRUE, 5, 'MANUAL');

-- Insertar usuarios
INSERT INTO usuario (dni, nombre, apellidos, correo, contrasena, telefono, rol) VALUES
('$2a$10$gvxmcMy2LV5hyIVdXkODN.XYnkihxIgRR3ilc8yr0ccMqtOwSVmSe', 'Admin', 'Admin', 'admin@admin.com', '$2a$10$/G8wzOlaczFdFppYhi9WFOcQ71BEE/bD98RGRT4TWjCG8u/RwUj0i', '000000000', 'ADMIN'),
('$2a$10$Mrfc7RH/hO2DDlFbnnUTNehtOL2udC8JIk8BNhamX/wyhslE1ij9W', 'Juan', 'Perez', 'juan@example.com', '$2a$10$vWft.n4G.wS6zIJawIT4jeNSLO9uszF6O7LoGOhGLhRbeDXX0W6xO', '620212324', 'CLIENTE'),
('$2a$10$OSsA2ZGjJIac11pNmrws2.Vq02yLMGVEmgjnaj.zVVqr8jyvoiJ.i', 'Alicia', 'Gomez', 'alicia@example.com', '$2a$10$lagHtOUymSe/Qan0T.UWAuB5E.VOLHs8/Evyx41cxX6NYnJbIFvtG', '629282726', 'CLIENTE');
-- Insertar reservas
INSERT INTO reserva (matricula, id_usuario, fecha_reserva, dias_reserva, precio) VALUES
('2345PQR', 1, '2025-05-29', 1, 30.00),
('2345PQR', 2, '2025-05-30', 2, 60.00),
('2345PQR', 3, '2025-06-01', 4, 120.00);

-- Insertar resenas
INSERT INTO resena (comentario, puntuacion, fecha, id_usuario, matricula, id_reserva) VALUES
('El BMW Serie 3 ofrece una conducción muy deportiva y suave a la vez. La respuesta del motor y el cambio automático fueron impecables, ideal para viajes por carretera', 5, '2025-05-30', 1, '2345PQR', 1),
('Disfruté mucho del confort interior y los asientos de piel. El sistema de navegación me guió sin fallos y el consumo se mantuvo dentro de lo esperado', 4, '2025-06-01', 2, '2345PQR', 2),
('La experiencia de lujo se nota en cada detalle: desde el sonido envolvente hasta el diseño minimalista del salpicadero. Aparcar con la cámara trasera y los sensores es pan comido', 5, '2025-06-05', 3, '2345PQR', 3);
