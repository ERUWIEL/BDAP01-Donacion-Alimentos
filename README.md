![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
# BDAP01-Donacion-Alimentos
Aplicación en java que permite gestionar la recepción y distribución de alimentos donados mediante MySQL y Java de la manera mas simple posible.
<br><br>
![MER_V2](./assets/MER_V2.png)
<br>



# 🍽️ Sistema de Donaciones de Alimentos

Un sistema de gestión de donaciones que permite la **trazabilidad completa** desde el donante hasta la organización beneficiaria.

## 📋 Características Principales

- ✅ **Trazabilidad completa**: Rastrea cada alimento desde su donación hasta su entrega final
- ✅ **Gestión de inventario**: Control de stock disponible vs entregado
- ✅ **Control de caducidad**: Seguimiento de fechas de vencimiento
- ✅ **Reportes de impacto**: Estadísticas de donantes y organizaciones beneficiadas
- ✅ **Flexibilidad**: Permite dividir aportaciones en múltiples entregas

## 🗄️ Modelo de Base de Datos

### Diagrama de Flujo
```
DONANTE → APORTACIONES → CONTENIDOS → ENTREGAS → ORGANIZACIONES
    ↖️_____________↗️         ↖️_________↗️
   (Trazabilidad completa de origen a destino)
```

### Script MySQL Completo

```sql
-- =====================================================
-- SISTEMA DE DONACIONES DE ALIMENTOS - BASE DE DATOS
-- =====================================================
-- Creación de la base de datos implementando configuraciones de cascada
CREATE DATABASE IF NOT EXISTS sistema_donaciones_v3;
USE sistema_donaciones_v3;

-- =====================================================
-- TABLA BASE DE PERSONAS
-- =====================================================
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

-- =====================================================
-- TABLA DE DONANTES (HERENCIA DE PERSONAS)
-- =====================================================
-- Cascada RU a persona
CREATE TABLE donantes (
    id_persona INT NOT NULL,
    tipo_donante VARCHAR(50),
    PRIMARY KEY (id_persona),
    FOREIGN KEY (id_persona) REFERENCES personas(id_persona)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- =====================================================
-- TABLA DE ORGANIZACIONES (HERENCIA DE PERSONAS)  
-- =====================================================
-- Cascada RU a persona
CREATE TABLE organizaciones (
    id_persona INT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_persona),
    FOREIGN KEY (id_persona) REFERENCES personas(id_persona)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- =====================================================
-- CATÁLOGO DE ALIMENTOS
-- =====================================================
CREATE TABLE alimentos (
    id_alimento INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(20) NOT NULL,
    cantidad DECIMAL(10,2) NOT NULL,
    tipo_alimento VARCHAR(20),
    PRIMARY KEY (id_alimento)
);

-- =====================================================
-- TABLA DE APORTACIONES (LAS DONACIONES)
-- =====================================================
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

-- =====================================================
-- TABLA DE ENTREGAS A ORGANIZACIONES
-- =====================================================
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

-- =====================================================
-- TABLA INTERMEDIARIA ENTRE ENTREGAS Y ALIMENTOS
-- =====================================================
-- *** CLAVE: Mantiene trazabilidad completa ***
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

-- =====================================================
-- VISTA PARA CONSULTA RÁPIDA DE STOCK
-- =====================================================
CREATE VIEW stock_disponible AS
SELECT 
    al.id_alimento,
    al.nombre as alimento,
    COALESCE(SUM(ap.cantidad), 0) as total_donado,
    COALESCE(SUM(CASE 
        WHEN c.id_contenido IS NOT NULL AND e.estado_entrega = 'entregada' 
        THEN ap.cantidad 
        ELSE 0 
    END), 0) as ya_entregado,
    COALESCE(SUM(ap.cantidad), 0) - COALESCE(SUM(CASE 
        WHEN c.id_contenido IS NOT NULL AND e.estado_entrega = 'entregada' 
        THEN ap.cantidad 
        ELSE 0 
    END), 0) as disponible
FROM alimentos al
LEFT JOIN aportaciones ap ON al.id_alimento = ap.id_alimento
LEFT JOIN contenidos c ON ap.id_aportacion = c.id_aportacion
LEFT JOIN entregas e ON c.id_entrega = e.id_entrega
GROUP BY al.id_alimento, al.nombre;
```

## 📊 Consultas Principales

### 1. Stock Disponible por Alimento
```sql
SELECT * FROM stock_disponible WHERE alimento = 'Arroz';
```

### 2. Trazabilidad: ¿Qué organizaciones recibieron alimentos de un donante?
```sql
SELECT DISTINCT
    CONCAT(p.nombre, ' ', p.apellido_paterno) as donante,
    org.nombre as organizacion_beneficiada,
    e.fecha_entrega,
    al.nombre as alimento_donado,
    ap.cantidad as cantidad_kg
FROM donantes d
JOIN personas p ON d.id_persona = p.id_persona
JOIN aportaciones ap ON d.id_persona = ap.id_donante
JOIN contenidos c ON ap.id_aportacion = c.id_aportacion
JOIN entregas e ON c.id_entrega = e.id_entrega
JOIN organizaciones org ON e.id_organizacion = org.id_persona
JOIN alimentos al ON ap.id_alimento = al.id_alimento
WHERE d.id_persona = ?; -- ID del donante
```

### 3. Contenido de una Entrega Específica
```sql
SELECT 
    e.id_entrega,
    e.fecha_entrega,
    org_p.nombre as organizacion_destino,
    al.nombre as alimento,
    ap.cantidad as cantidad_kg,
    ap.fecha_caducidad,
    CONCAT(don_p.nombre, ' ', don_p.apellido_paterno) as donante_original
FROM entregas e
JOIN organizaciones org ON e.id_organizacion = org.id_persona
JOIN personas org_p ON org.id_persona = org_p.id_persona
JOIN contenidos c ON e.id_entrega = c.id_entrega
JOIN alimentos al ON c.id_alimento = al.id_alimento
JOIN aportaciones ap ON c.id_aportacion = ap.id_aportacion
JOIN donantes don ON ap.id_donante = don.id_persona
JOIN personas don_p ON don.id_persona = don_p.id_persona
WHERE e.id_entrega = ?; -- ID de la entrega
```

## 🚀 Flujo Operativo

### Ejemplo: Juan dona 10kg de arroz → Cruz Roja lo recibe

1. **Registro del donante**
   ```sql
   INSERT INTO personas (...) VALUES ('Juan', 'Pérez', ...);
   INSERT INTO donantes (id_persona, tipo_donante) VALUES (LAST_INSERT_ID(), 'individual');
   ```

2. **Registro de la donación**
   ```sql
   INSERT INTO aportaciones (fecha_caducidad, cantidad, id_donante, id_alimento) 
   VALUES ('2024-12-31', 10.00, @id_juan, @id_arroz);
   ```

3. **Creación de entrega**
   ```sql
   INSERT INTO entregas (fecha_entrega, estado_entrega, id_organizacion) 
   VALUES ('2024-02-15', 'pendiente', @id_cruzroja);
   ```

4. **Asignación de contenido específico**
   ```sql
   INSERT INTO contenidos (id_alimento, id_entrega, id_aportacion) 
   VALUES (@id_arroz, @id_entrega, @id_aportacion_juan);
   ```

## 🎯 Ventajas del Diseño

- **Trazabilidad**: Cada alimento mantiene su origen desde la donación hasta la entrega
- **Flexibilidad**: Una aportación grande puede dividirse en múltiples entregas
- **Control**: Stock calculado dinámicamente basado en donaciones vs entregas
- **Auditoría**: Historial completo de movimientos
- **Escalabilidad**: Estructura preparada para crecimiento

## 🛠️ Tecnologías

- **Base de datos**: MySQL 8.0+
- **Motor de almacenamiento**: InnoDB (para integridad referencial)
- **Características utilizadas**: 
  - Claves foráneas con cascadas
  - Enums para estados
  - Triggers (opcional para automatización)

## 📄 Licencia

[Tu licencia aquí]

## 👥 Equipo de Desarrollo

- [Nombres del equipo]

---

> **Nota**: Este modelo prioriza la **trazabilidad completa** sobre la simplicidad de consultas. Cada alimento puede rastrearse desde su donante original hasta su destino final.





