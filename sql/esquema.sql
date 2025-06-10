-- Tabla de administración de clientes
CREATE TABLE cliente (
    id_cliente SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    cedula VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    CONSTRAINT uc_cliente UNIQUE (cedula, email)
);

-- Extensión de la tabla cliente para taller mecánico
ALTER TABLE cliente
    ADD COLUMN telefono VARCHAR(20),
    ADD COLUMN direccion VARCHAR(200),
    ADD COLUMN tipo_cliente VARCHAR(20) NOT NULL;

-- Tabla de administración de vehículos
CREATE TABLE vehiculo (
    id_vehiculo SERIAL PRIMARY KEY,
    marca VARCHAR(100) NOT NULL,
    numero_chapa VARCHAR(50) NOT NULL UNIQUE,
    modelo VARCHAR(100) NOT NULL,
    anio INTEGER NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    id_cliente INTEGER NOT NULL,
    CONSTRAINT fk_vehiculo_cliente FOREIGN KEY (id_cliente)
        REFERENCES cliente (id_cliente)
);

-- Tabla de administración de repuestos
CREATE TABLE repuesto (
    id_repuesto SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    codigo VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla de administración de mecánicos
CREATE TABLE mecanico (
    id_mecanico SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    fecha_ingreso DATE NOT NULL,
    especialidad VARCHAR(100) NOT NULL
);

-- Tabla de cabecera de servicios
CREATE TABLE servicio (
    id_servicio SERIAL PRIMARY KEY,
    fecha DATE NOT NULL,
    descripcion_general VARCHAR(500) NOT NULL,
    kilometraje_actual INTEGER NOT NULL,
    costo_total DECIMAL(10,2) NOT NULL,
    id_vehiculo INTEGER NOT NULL,
    CONSTRAINT fk_servicio_vehiculo FOREIGN KEY (id_vehiculo)
        REFERENCES vehiculo (id_vehiculo)
);

-- Tabla de detalle de servicio
CREATE TABLE detalle_servicio (
    id_detalle_servicio SERIAL PRIMARY KEY,
    id_servicio INTEGER NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    costo DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_detalleservicio_servicio FOREIGN KEY (id_servicio)
        REFERENCES servicio (id_servicio)
);

-- Tabla intermedia detalle_servicio_repuesto
CREATE TABLE detalle_servicio_repuesto (
    id_detalle_servicio INTEGER NOT NULL,
    id_repuesto INTEGER NOT NULL,
    PRIMARY KEY (id_detalle_servicio, id_repuesto),
    CONSTRAINT fk_detallerepuesto_detalle_servicio FOREIGN KEY (id_detalle_servicio)
        REFERENCES detalle_servicio (id_detalle_servicio),
    CONSTRAINT fk_detallerepuesto_repuesto FOREIGN KEY (id_repuesto)
        REFERENCES repuesto (id_repuesto)
);

-- Tabla intermedia detalle_servicio_mecanico
CREATE TABLE detalle_servicio_mecanico (
    id_detalle_servicio INTEGER NOT NULL,
    id_mecanico INTEGER NOT NULL,
    PRIMARY KEY (id_detalle_servicio, id_mecanico),
    CONSTRAINT fk_detallemecanico_detalle_servicio FOREIGN KEY (id_detalle_servicio)
        REFERENCES detalle_servicio (id_detalle_servicio),
    CONSTRAINT fk_detallemecanico_mecanico FOREIGN KEY (id_mecanico)
        REFERENCES mecanico (id_mecanico)
);