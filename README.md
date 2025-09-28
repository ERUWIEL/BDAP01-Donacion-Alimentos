![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
# 🍽️ Sistema de Donaciones de Alimentos
### Contexto de la aplicacion
Plataforma de Donación de Alimentos
En la ciudad se desperdician diariamente toneladas de comida en supermercados, restaurantes y otros establecimientos, mientras que miles de familias viven en situación de inseguridad alimentaria. Ante esta problemática, un grupo de jóvenes propone el desarrollo de una plataforma digital de donación de alimentos que conecte a donadores con organizaciones comunitarias que los distribuyan a quienes más lo necesitan.

<br>

El sistema deberá contemplar los siguientes aspectos:
Registro de donadores: cada donador podrá registrarse proporcionando información como nombre, tipo de donador (supermercado, restaurante, particular, etc.), dirección, teléfono y correo electrónico.

<br>

Registro de alimentos donados: cada vez que un donador realice una aportación, se deberá registrar el nombre del alimento, su categoría (por ejemplo: frutas, verduras, enlatados, panadería, etc.), la fecha de caducidad y la cantidad disponible, vinculándolo con el donador que lo proporciona.

<br>

Registro de organizaciones beneficiarias: las organizaciones comunitarias que recibirán los apoyos también deberán registrarse, indicando nombre, responsable, dirección, teléfono y correo electrónico.

<br>

Gestión de entregas: cada entrega deberá quedar registrada con información como la fecha de entrega, el alimento entregado, la organización beneficiada y el estado de la entrega (pendiente, en tránsito, completada, cancelada), lo que permitirá dar un seguimiento claro y transparente de los productos desde su donación hasta


<br>
<b>Diagrama Entidad Relacion</b>
<br>

![MER_V2](./assets/MER_V2.png)

<br>

> **Nota**: Este modelo se simplifico por cuestiones del curso.

### Script MySQL

```sql
-- Creación de la base de datos implementando configuraciones de cascada
CREATE DATABASE IF NOT EXISTS sistema_donaciones;
USE sistema_donaciones;

-- Tabla de personas
CREATE TABLE personas (
    id_persona INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellido_paterno VARCHAR(70) NOT NULL,
    apellido_materno VARCHAR(70),
    correo_electronico VARCHAR(80) NOT NULL,
    numero_telefono VARCHAR(13) NOT NULL,
    direccion VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_persona)
);

-- Tabla de donantes
CREATE TABLE donantes (
    id_persona INT NOT NULL,
    tipo_donante VARCHAR(50),
    PRIMARY KEY (id_persona),
    FOREIGN KEY (id_persona) REFERENCES personas(id_persona)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Tabla de organizaciones  
CREATE TABLE organizaciones (
    id_persona INT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_persona),
    FOREIGN KEY (id_persona) REFERENCES personas(id_persona)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Tabla de alimentos
CREATE TABLE alimentos (
    id_alimento INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(20) NOT NULL,
    cantidad DECIMAL(10,2) NOT NULL,
    tipo_alimento VARCHAR(20),
    PRIMARY KEY (id_alimento)
);

-- Tabla de aportaciones
CREATE TABLE aportaciones (
    id_aportacion INT NOT NULL AUTO_INCREMENT,
    fecha_caducidad DATE NOT NULL,
    cantidad DECIMAL(10,2) NOT NULL,
    
    id_donante INT NOT NULL,
    id_alimento INT NOT NULL,
    PRIMARY KEY (id_aportacion),
    FOREIGN KEY (id_donante) REFERENCES donantes(id_persona)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    FOREIGN KEY (id_alimento) REFERENCES alimentos(id_alimento)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- Tabla de entregas
CREATE TABLE entregas (
    id_entrega INT NOT NULL AUTO_INCREMENT,
    fecha_entrega DATE NOT NULL,
    estado_entrega ENUM('pendiente', 'en_transito', 'entregada', 'cancelada') NOT NULL,
    id_organizacion INT NOT NULL,
    PRIMARY KEY (id_entrega),
    FOREIGN KEY (id_organizacion) REFERENCES organizaciones(id_persona)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- Tabla de contenidos
CREATE TABLE contenidos (
    id_contenido INT NOT NULL AUTO_INCREMENT,
    id_alimento INT NOT NULL,
    id_entrega INT NOT NULL,
    id_aportacion INT NOT NULL,
    
    PRIMARY KEY (id_contenido),
    FOREIGN KEY (id_alimento) REFERENCES alimentos(id_alimento)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    FOREIGN KEY (id_entrega) REFERENCES entregas(id_entrega)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (id_aportacion) REFERENCES aportaciones(id_aportacion)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- SP para registrar una aportacion
CREATE PROCEDURE sp_registrar_aportacion(
    IN p_fecha_caducidad DATE,
    IN p_cantidad DECIMAL(10,2),
    IN p_id_donante INT,
    IN p_id_alimento INT,
    OUT p_id_aportacion INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- Verificar que el donante existe
    IF NOT EXISTS (SELECT 1 FROM donantes WHERE id_persona = p_id_donante) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Donante no existe';
    END IF;
    
    -- Verificar que el alimento existe
    IF NOT EXISTS (SELECT 1 FROM alimentos WHERE id_alimento = p_id_alimento) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Alimento no existe';
    END IF;
    
    INSERT INTO aportaciones (fecha_caducidad, cantidad, id_donante, id_alimento)
    VALUES (p_fecha_caducidad, p_cantidad, p_id_donante, p_id_alimento);
    
    SET p_id_aportacion = LAST_INSERT_ID();
    
    COMMIT;
END 
```

