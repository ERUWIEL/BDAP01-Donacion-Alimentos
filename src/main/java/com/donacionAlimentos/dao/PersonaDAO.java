package com.donacionAlimentos.dao;

import com.donacionAlimentos.config.ConexionDB;
import com.donacionAlimentos.interfaces.IPersonaDAO;
import com.donacionAlimentos.models.Persona;
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
public class PersonaDAO implements IPersonaDAO {

    /**
     * Método que permite insertar una persona
     *
     * @param persona
     * @return true si se insertó correctamente, false en caso contrario
     */
    @Override
    public boolean create(Persona persona) {
        String sql = "INSERT INTO personas(nombre, apellido_paterno, apellido_materno, correo_electronico, numero_telefono, direccion) VALUES(?,?,?,?,?,?)";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApPaterno());
            ps.setString(3, persona.getApMaterno());
            ps.setString(4, persona.getCorreo());
            ps.setString(5, persona.getTelefono());
            ps.setString(6, persona.getDireccion());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al agregar persona");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Método que busca persona en la base de datos
     *
     * @param id
     * @return Persona si existe, null caso contrario
     */
    @Override
    public Persona read(int id) {
        String sql = "SELECT id_persona, nombre, apellido_paterno, apellido_materno, correo_electronico, numero_telefono, direccion FROM personas WHERE id_persona = ?";
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Persona persona = new Persona();
                    persona.setIdPersona(rs.getInt("id_persona"));
                    persona.setNombre(rs.getString("nombre"));
                    persona.setApPaterno(rs.getString("apellido_paterno"));
                    persona.setApMaterno(rs.getString("apellido_materno"));
                    persona.setCorreo(rs.getString("correo_electronico"));
                    persona.setTelefono(rs.getString("numero_telefono"));
                    persona.setDireccion(rs.getString("direccion"));
                    return persona;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar persona");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Método que consulta todas las personas en la base de datos
     *
     * @return Lista de Personas
     */
    @Override
    public List<Persona> readAll() {
        String sql = "SELECT id_persona, nombre, apellido_paterno, apellido_materno, correo_electronico, numero_telefono, direccion FROM personas";
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<Persona> personas = new ArrayList<>();
            while (rs.next()) {
                Persona persona = new Persona();
                persona.setIdPersona(rs.getInt("id_persona"));
                persona.setNombre(rs.getString("nombre"));
                persona.setApPaterno(rs.getString("apellido_paterno"));
                persona.setApMaterno(rs.getString("apellido_materno"));
                persona.setCorreo(rs.getString("correo_electronico"));
                persona.setTelefono(rs.getString("numero_telefono"));
                persona.setDireccion(rs.getString("direccion"));
                personas.add(persona);
            }
            return personas;

        } catch (SQLException ex) {
            System.out.println("Error al consultar personas");
            System.out.println(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Método que actualiza la información de una persona
     *
     * @param persona
     * @return true si se modificó, false en caso contrario
     */
    @Override
    public boolean update(Persona persona) {
        String sql = "UPDATE personas SET nombre = ?, apellido_paterno = ?, apellido_materno = ?, correo_electronico = ?, numero_telefono = ?, direccion = ? WHERE id_persona = ?";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApPaterno());
            ps.setString(3, persona.getApMaterno());
            ps.setString(4, persona.getCorreo());
            ps.setString(5, persona.getTelefono());
            ps.setString(6, persona.getDireccion());
            ps.setInt(7, persona.getIdPersona());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al modificar persona");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Método que permite eliminar una persona
     *
     * @param id
     * @return true si se eliminó, false en caso contrario
     */
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM personas WHERE id_persona = ?";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al eliminar persona");
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
