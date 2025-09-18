package com.donacionAlimentos.dao;

import com.donacionAlimentos.config.ConexionDB;
import com.donacionAlimentos.interfaces.IAportacionDAO;
import com.donacionAlimentos.models.Aportacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * clase de acceso de datos ala tabla aportaciones
 *
 * @author erwbyel
 */
public class AportacionDAO implements IAportacionDAO {

    /**
     * Método que permite insertar una aportación
     *
     * @param aportacion
     * @return true si se insertó correctamente, false en caso contrario
     */
    @Override
    public boolean create(Aportacion aportacion) {
        String sql = "INSERT INTO aportaciones(fecha_caducidad, cantidad, id_donante, id_alimento) VALUES(?,?,?,?)";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(aportacion.getFechaCaducidad().getTime()));
            ps.setBigDecimal(2, aportacion.getCantidad());
            ps.setInt(3, aportacion.getIdDonante());
            ps.setInt(4, aportacion.getIdAlimento());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al agregar aportación");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Método que busca aportación en la base de datos
     *
     * @param id
     * @return Aportacion si existe, null caso contrario
     */
    @Override
    public Aportacion read(int id) {
        String sql = "SELECT id_aportacion, fecha_caducidad, cantidad, id_donante, id_alimento FROM aportaciones WHERE id_aportacion = ?";
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Aportacion aportacion = new Aportacion();
                    aportacion.setIdAportacion(rs.getInt("id_aportacion"));
                    aportacion.setFechaCaducidad(rs.getDate("fecha_caducidad"));
                    aportacion.setCantidad(rs.getBigDecimal("cantidad"));
                    aportacion.setIdDonante(rs.getInt("id_donante"));
                    aportacion.setIdAlimento(rs.getInt("id_alimento"));
                    return aportacion;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar aportación");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Método que consulta todas las aportaciones en la base de datos
     *
     * @return Lista de Aportaciones
     */
    @Override
    public List<Aportacion> readAll() {
        String sql = "SELECT id_aportacion, fecha_caducidad, cantidad, id_donante, id_alimento FROM aportaciones";
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<Aportacion> aportaciones = new ArrayList<>();
            while (rs.next()) {
                Aportacion aportacion = new Aportacion();
                aportacion.setIdAportacion(rs.getInt("id_aportacion"));
                aportacion.setFechaCaducidad(rs.getDate("fecha_caducidad"));
                aportacion.setCantidad(rs.getBigDecimal("cantidad"));
                aportacion.setIdDonante(rs.getInt("id_donante"));
                aportacion.setIdAlimento(rs.getInt("id_alimento"));
                aportaciones.add(aportacion);
            }
            return aportaciones;

        } catch (SQLException ex) {
            System.out.println("Error al consultar aportaciones");
            System.out.println(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Método que actualiza la información de una aportación
     *
     * @param aportacion
     * @return true si se modificó, false en caso contrario
     */
    @Override
    public boolean update(Aportacion aportacion) {
        String sql = "UPDATE aportaciones SET fecha_caducidad = ?, cantidad = ?, id_donante = ?, id_alimento = ? WHERE id_aportacion = ?";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(aportacion.getFechaCaducidad().getTime()));
            ps.setBigDecimal(2, aportacion.getCantidad());
            ps.setInt(3, aportacion.getIdDonante());
            ps.setInt(4, aportacion.getIdAlimento());
            ps.setInt(5, aportacion.getIdAportacion());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al modificar aportación");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Método que permite eliminar una aportación
     *
     * @param id
     * @return true si se eliminó, false en caso contrario
     */
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM aportaciones WHERE id_aportacion = ?";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al eliminar aportación");
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
