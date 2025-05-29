-- Insertar tipos de vehículos
INSERT INTO tipo_vehiculo (marca, modelo, precio, tipo_vehiculo, imagen) VALUES 
('Audi', 'A3', 65.00, 'COCHE', ''),
('BMW', 'Serie 3', 60.00, 'COCHE', ''),
('Citroën', 'Berlingo', 45.00, 'FURGONETA', ''),
('Ducati', 'Monster 821', 18.00, 'MOTO', ''),
('Fiat', 'Ducato', 52.00, 'FURGONETA', ''),
('Ford', 'Focus', 30.00, 'COCHE', ''),
('Harley-Davidson', 'Iron 883', 20.00, 'MOTO', ''),
('Honda', 'CBR600RR', 15.00, 'MOTO', ''),
('Hyundai', 'i20N', 35.00, 'COCHE', ''),
('Kawasaki', 'Ninja 400', 14.00, 'MOTO', ''),
('Mercedes-Benz', 'Sprinter', 55.00, 'FURGONETA', ''),
('Nissan', 'Leaf', 40.00, 'COCHE', ''),
('Opel', 'Combo Life', 48.00, 'FURGONETA', ''),
('Peugeot', '208', 28.00, 'COCHE', ''),
('Renault', 'Clio', 27.00, 'COCHE', ''),
('Seat', 'Ibiza', 29.00, 'COCHE', ''),
('Suzuki', 'GSX-R750', 16.00, 'MOTO', ''),
('Tesla', 'Model 3', 70.00, 'COCHE', ''),
('Toyota', 'Corolla', 30.00, 'COCHE', ''),
('Volkswagen', 'Transporter', 50.00, 'FURGONETA', ''),
('Yamaha', 'MT-07', 12.00, 'MOTO', '');

-- Insertar vehículos
INSERT INTO vehiculo (matricula, id_tipo_vehiculo, color, kilometraje, disponibilidad, ubicacion, combustible, etiqueta, autonomia, puertas, aire_acondicionado, plazas, transmision) VALUES
('0000JJJ', 17, 'Gris', 40000, FALSE, 'PONTEVEDRA', 'DIESEL', 'C', NULL, 3, TRUE, 3, 'MANUAL'),
('0120NBB', 7, 'Negro', 25000, FALSE, 'MADRID', 'GASOLINA', 'C', NULL, 5, TRUE, 5, 'MANUAL'),
('1111AAA', 8, 'Rojo', 15000, TRUE, 'ZARAGOZA', 'GASOLINA', 'C', NULL, 5, TRUE, 5, 'MANUAL'),
('1212KKK', 18, 'Azul', 13000, TRUE, 'CORDOBA', 'ELECTRICO', 'CERO', 300, 5, TRUE, 5, 'AUTOMATICO'),
('1234ABC', 1, 'Blanco', 20000, TRUE, 'MADRID', 'ELECTRICO', 'CERO', 350, 5, TRUE, 5, 'AUTOMATICO'),
('2222BBB', 9, 'Verde', 6000, TRUE, 'MALAGA', 'GASOLINA', 'B', NULL, NULL, NULL, 2, 'MANUAL'),
('2345PQR', 6, 'Gris', 18000, TRUE, 'ALICANTE', 'GASOLINA', 'ECO', NULL, 5, TRUE, 5, 'AUTOMATICO'),
('3434LLL', 19, 'Rojo', 4500, TRUE, 'BURGOS', 'GASOLINA', 'B', NULL, NULL, NULL, 2, 'MANUAL'),
('3456JKL', 4, 'Azul', 22000, FALSE, 'SEVILLA', 'DIESEL', 'C', NULL, 5, TRUE, 5, 'AUTOMATICO'),
('4444DDD', 11, 'Gris', 12000, TRUE, 'GRANADA', 'GASOLINA', 'ECO', NULL, 5, TRUE, 5, 'MANUAL'),
('4556EEE', 12, 'Negro', 7000, TRUE, 'CANTABRIA', 'GASOLINA', 'B', NULL, NULL, NULL, 2, 'MANUAL'),
('5656MMM', 20, 'Verde', 32000, TRUE, 'ASTURIAS', 'DIESEL', 'C', NULL, 5, TRUE, 5, 'MANUAL'),
('5678DEF', 2, 'Negro', 8000, TRUE, 'BARCELONA', 'GASOLINA', 'B', NULL, NULL, NULL, 2, 'MANUAL'),
('6666FFF', 13, 'Blanco', 25000, TRUE, 'SALAMANCA', 'DIESEL', 'C', NULL, 5, TRUE, 5, 'MANUAL'),
('6778GGG', 14, 'Azul', 10000, TRUE, 'MADRID', 'ELECTRICO', 'CERO', 400, 4, TRUE, 5, 'AUTOMATICO'),
('7777GGG', 14, 'Azul', 10000, TRUE, 'MADRID', 'ELECTRICO', 'CERO', 400, 4, TRUE, 5, 'AUTOMATICO'),
('7878NNN', 21, 'Negro', 17000, TRUE, 'LEON', 'ELECTRICO', 'CERO', 300, 5, TRUE, 5, 'AUTOMATICO'),
('7890MNO', 5, 'Amarillo', 3000, TRUE, 'BALEARES', 'GASOLINA', 'B', NULL, NULL, NULL, 2, 'MANUAL'),
('8888HHH', 15, 'Blanco', 22000, TRUE, 'VALENCIA', 'GASOLINA', 'C', NULL, 5, TRUE, 5, 'MANUAL'),
('8999III', 16, 'Negro', 9000, TRUE, 'TOLEDO', 'GASOLINA', 'B', NULL, NULL, NULL, 2, 'MANUAL'),
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