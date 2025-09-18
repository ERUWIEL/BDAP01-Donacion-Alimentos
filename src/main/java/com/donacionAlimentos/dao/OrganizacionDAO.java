package com.donacionAlimentos.dao;

import com.donacionAlimentos.config.ConexionDB;
import com.donacionAlimentos.interfaces.IOrganizacionDAO;
import com.donacionAlimentos.models.Organizacion;
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
public class OrganizacionDAO implements IOrganizacionDAO {

    /**
     * Método que permite insertar una organización
     *
     * @param organizacion
     * @return true si se insertó correctamente, false en caso contrario
     */
    @Override
    public boolean create(Organizacion organizacion) {
        String sql = "INSERT INTO organizaciones(id_persona, nombre) VALUES(?,?)";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, organizacion.getIdPersona());
            ps.setString(2, organizacion.getNombreOrganizacion());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al agregar organización");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Método que busca organización en la base de datos
     *
     * @param id
     * @return Organizacion si existe, null caso contrario
     */
    @Override
    public Organizacion read(int id) {
        String sql = "SELECT id_persona, nombre FROM organizaciones WHERE id_persona = ?";
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Organizacion organizacion = new Organizacion();
                    organizacion.setIdPersona(rs.getInt("id_persona"));
                    organizacion.setNombreOrganizacion(rs.getString("nombre"));
                    return organizacion;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar organización");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Método que consulta todas las organizaciones en la base de datos
     *
     * @return Lista de Organizaciones
     */
    @Override
    public List<Organizacion> readAll() {
        String sql = "SELECT id_persona, nombre FROM organizaciones";
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<Organizacion> organizaciones = new ArrayList<>();
            while (rs.next()) {
                Organizacion organizacion = new Organizacion();
                organizacion.setIdPersona(rs.getInt("id_persona"));
                organizacion.setNombreOrganizacion(rs.getString("nombre"));
                organizaciones.add(organizacion);
            }
            return organizaciones;

        } catch (SQLException ex) {
            System.out.println("Error al consultar organizaciones");
            System.out.println(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Método que actualiza la información de una organización
     *
     * @param organizacion
     * @return true si se modificó, false en caso contrario
     */
    @Override
    public boolean update(Organizacion organizacion) {
        String sql = "UPDATE organizaciones SET nombre = ? WHERE id_persona = ?";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, organizacion.getNombreOrganizacion());
            ps.setInt(2, organizacion.getIdPersona());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al modificar organización");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Método que permite eliminar una organización
     *
     * @param id
     * @return true si se eliminó, false en caso contrario
     */
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM organizaciones WHERE id_persona = ?";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al eliminar organización");
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
