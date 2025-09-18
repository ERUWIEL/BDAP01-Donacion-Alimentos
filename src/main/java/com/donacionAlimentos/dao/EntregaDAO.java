package com.donacionAlimentos.dao;

import com.donacionAlimentos.config.ConexionDB;
import com.donacionAlimentos.interfaces.IEntregaDAO;
import com.donacionAlimentos.models.Entrega;
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
public class EntregaDAO implements IEntregaDAO {

    /**
     * Método que permite insertar una entrega
     *
     * @param entrega
     * @return true si se insertó correctamente, false en caso contrario
     */
    @Override
    public boolean create(Entrega entrega) {
        String sql = "INSERT INTO entregas(fecha_entrega, estado_entrega, id_organizacion) VALUES(?,?,?)";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(entrega.getFechaEntrega().getTime()));
            ps.setString(2, entrega.getEstadoEntrega().name());
            ps.setInt(3, entrega.getIdOrganizacion());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al agregar entrega");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Método que busca entrega en la base de datos
     *
     * @param id
     * @return Entrega si existe, null caso contrario
     */
    @Override
    public Entrega read(int id) {
        String sql = "SELECT id_entrega, fecha_entrega, estado_entrega, id_organizacion FROM entregas WHERE id_entrega = ?";
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Entrega entrega = new Entrega();
                    entrega.setIdEntrega(rs.getInt("id_entrega"));
                    entrega.setFechaEntrega(rs.getDate("fecha_entrega"));
                    entrega.setEstadoEntrega(Entrega.Estado.valueOf(rs.getString("estado_entrega")));
                    entrega.setIdOrganizacion(rs.getInt("id_organizacion"));
                    return entrega;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar entrega");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Método que consulta todas las entregas en la base de datos
     *
     * @return Lista de Entregas
     */
    @Override
    public List<Entrega> readAll() {
        String sql = "SELECT id_entrega, fecha_entrega, estado_entrega, id_organizacion FROM entregas";
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<Entrega> entregas = new ArrayList<>();
            while (rs.next()) {
                Entrega entrega = new Entrega();
                entrega.setIdEntrega(rs.getInt("id_entrega"));
                entrega.setFechaEntrega(rs.getDate("fecha_entrega"));
                entrega.setEstadoEntrega(Entrega.Estado.valueOf(rs.getString("estado_entrega")));
                entrega.setIdOrganizacion(rs.getInt("id_organizacion"));
                entregas.add(entrega);
            }
            return entregas;

        } catch (SQLException ex) {
            System.out.println("Error al consultar entregas");
            System.out.println(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Método que actualiza la información de una entrega
     *
     * @param entrega
     * @return true si se modificó, false en caso contrario
     */
    @Override
    public boolean update(Entrega entrega) {
        String sql = "UPDATE entregas SET fecha_entrega = ?, estado_entrega = ?, id_organizacion = ? WHERE id_entrega = ?";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(entrega.getFechaEntrega().getTime()));
            ps.setString(2, entrega.getEstadoEntrega().name());
            ps.setInt(3, entrega.getIdOrganizacion());
            ps.setInt(4, entrega.getIdEntrega());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al modificar entrega");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Método que permite eliminar una entrega
     *
     * @param id
     * @return true si se eliminó, false en caso contrario
     */
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM entregas WHERE id_entrega = ?";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al eliminar entrega");
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
