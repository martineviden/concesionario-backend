-- Insertar tipos de vehículos
INSERT INTO tipo_vehiculo (marca, modelo, precio, tipo_vehiculo, imagen) VALUES 
('Toyota', 'Corolla', 30.00, 'COCHE', ''),
('Honda', 'CBR600RR', 15.00, 'MOTO', ''),
('Volkswagen', 'Transporter', 50.00, 'FURGONETA', ''),
('BMW', 'Serie 3', 60.00, 'COCHE', ''),
('Yamaha', 'MT-07', 12.00, 'MOTO', ''),
('Ford', 'Focus', 30.00, 'COCHE', ''),
('Hyundai', 'i20N', 35.00, 'COCHE', '');

-- Insertar vehículos
INSERT INTO vehiculo (matricula, id_tipo_vehiculo, color, kilometraje, disponibilidad, ubicacion, combustible, etiqueta, autonomia, puertas, aire_acondicionado, plazas, transmision) VALUES
('1234ABC', 1, 'Blanco', 20000, TRUE, 'MADRID', 'ELECTRICO', 'CERO', NULL, 5, TRUE, 5, 'AUTOMATICO'),
('5678DEF', 2, 'Negro', 8000, TRUE, 'BARCELONA', 'GASOLINA', 'B', NULL, NULL, NULL, 2, 'MANUAL'),
('9012GHI', 3, 'Blanco', 45000, TRUE, 'VALENCIA', 'DIESEL', 'C', NULL, 3, TRUE, 3, 'MANUAL'),
('3456JKL', 4, 'Azul', 22000, FALSE, 'SEVILLA', 'DIESEL', 'C', NULL, 5, TRUE, 5, 'AUTOMATICO'),
('7890MNO', 5, 'Amarillo', 3000, TRUE, 'BALEARES', 'GASOLINA', 'B', NULL, NULL, NULL, 2, 'MANUAL'),
('2345PQR', 6, 'Gris', 18000, TRUE, 'ALICANTE', 'GASOLINA', 'ECO', NULL, 5, TRUE, 5, 'AUTOMATICO'),
('0120NBB', 7, 'Negro', 25000, FALSE, 'MADRID', 'GASOLINA', 'C', NULL, 5, TRUE, 5, 'MANUAL');

-- Insertar usuarios
INSERT INTO usuario (dni, nombre, apellidos, correo, contrasena, telefono, rol) VALUES
('$2a$10$gvxmcMy2LV5hyIVdXkODN.XYnkihxIgRR3ilc8yr0ccMqtOwSVmSe', 'Admin', 'Admin', 'admin@admin.com', '$2a$10$/G8wzOlaczFdFppYhi9WFOcQ71BEE/bD98RGRT4TWjCG8u/RwUj0i', '000000000', 'ADMIN'),
('$2a$10$Mrfc7RH/hO2DDlFbnnUTNehtOL2udC8JIk8BNhamX/wyhslE1ij9W', 'Juan', 'Perez', 'juan@example.com', '$2a$10$vWft.n4G.wS6zIJawIT4jeNSLO9uszF6O7LoGOhGLhRbeDXX0W6xO', '620212324', 'CLIENTE'),
('$2a$10$OSsA2ZGjJIac11pNmrws2.Vq02yLMGVEmgjnaj.zVVqr8jyvoiJ.i', 'Alicia', 'Gomez', 'alicia@example.com', '$2a$10$lagHtOUymSe/Qan0T.UWAuB5E.VOLHs8/Evyx41cxX6NYnJbIFvtG', '629282726', 'CLIENTE');



