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
 * clase de acceso de datos ala tabla organizaciones
 *
 * @author erwbyel
 */
public class OrganizacionDAO implements IOrganizacionDAO {

    private final String SQL_READ_BY_ID = """
                                        SELECT 
                                            o.id_persona,
                                            o.nombre AS nombre_organizacion,
                                            p.nombre AS nombre_persona,
                                            p.apellido_paterno,
                                            p.apellido_materno,
                                            p.correo_electronico,
                                            p.numero_telefono,
                                            p.direccion
                                        FROM organizaciones o
                                        LEFT JOIN personas p ON o.id_persona = p.id_persona
                                        WHERE o.id_persona = ?
                                        """;
    
    private final String SQL_READ_ALL = """
                                        SELECT 
                                            o.id_persona,
                                            o.nombre AS nombre_organizacion,
                                            p.nombre AS nombre_persona,
                                            p.apellido_paterno,
                                            p.apellido_materno,
                                            p.correo_electronico,
                                            p.numero_telefono,
                                            p.direccion
                                        FROM organizaciones o
                                        LEFT JOIN personas p ON o.id_persona = p.id_persona
                                        """;
    
    private final String SQL_READ_BY_FILTER = """
                                        SELECT 
                                            o.id_persona,
                                            o.nombre AS nombre_organizacion,
                                            p.nombre AS nombre_persona,
                                            p.apellido_paterno,
                                            p.apellido_materno,
                                            p.correo_electronico,
                                            p.numero_telefono,
                                            p.direccion
                                        FROM organizaciones o
                                        LEFT JOIN personas p ON o.id_persona = p.id_persona
                                        """;
    

    

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
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_READ_BY_ID)) {
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
        List<Organizacion> organizaciones = new ArrayList<>();
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_READ_ALL); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Organizacion organizacion = new Organizacion();
                organizacion.setIdPersona(rs.getInt("id_persona"));
                organizacion.setNombreOrganizacion(rs.getString("nombre"));
                organizaciones.add(organizacion);
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar organizaciones: " + ex.getMessage());
        }
        return organizaciones;
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

    /**
     * metodo que lista a las organizaciones por nombre
     *
     * @param filtro
     * @return
     */
    @Override
    public List<Organizacion> readByFilter(String filtro) {
        List<Organizacion> organizaciones = new ArrayList<>();
        
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(SQL_READ_BY_FILTER); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Organizacion organizacion = new Organizacion();
                organizacion.setIdPersona(rs.getInt("id_persona"));
                organizacion.setNombreOrganizacion(rs.getString("nombre"));
                organizaciones.add(organizacion);
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar organizaciones: " + ex.getMessage());
        }
        return organizaciones;
    }

    @Override
    public List<Organizacion> readByFilterModal(String filtro) {
        String sql = "SELECT id_persona, nombre FROM organizaciones";
        List<Organizacion> organizaciones = new ArrayList<>();
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Organizacion organizacion = new Organizacion();
                organizacion.setIdPersona(rs.getInt("id_persona"));
                organizacion.setNombreOrganizacion(rs.getString("nombre"));
                organizaciones.add(organizacion);
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar organizaciones: " + ex.getMessage());
        }
        return organizaciones;
    }
}
