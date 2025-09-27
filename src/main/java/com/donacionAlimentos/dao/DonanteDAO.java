package com.donacionAlimentos.dao;

import com.donacionAlimentos.config.ConexionDB;
import com.donacionAlimentos.interfaces.IDonanteDAO;
import com.donacionAlimentos.models.Donante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * clase de acceso de datos ala tabla donantes
 *
 * @author erwbyel
 */
public class DonanteDAO implements IDonanteDAO {

    /**
     * Método que permite insertar un donante
     *
     * @param donante
     * @return true si se insertó correctamente, false en caso contrario
     */
    @Override
    public boolean create(Donante donante) {
        String sql = "INSERT INTO donantes(id_persona, tipo_donante) VALUES(?,?)";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, donante.getIdPersona());
            ps.setString(2, donante.getTipo());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al agregar donante");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Método que busca donante en la base de datos
     *
     * @param idPersona
     * @return Donante si existe, null caso contrario
     */
    @Override
    public Donante read(int idPersona) {
        String sql = "SELECT id_persona, tipo_donante FROM donantes WHERE id_persona = ?";
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPersona);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Donante donante = new Donante();
                    donante.setIdPersona(rs.getInt("id_persona"));
                    donante.setTipo(rs.getString("tipo_donante"));
                    return donante;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar donante");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Método que consulta todos los donantes en la base de datos
     *
     * @return Lista de Donantes
     */
    @Override
    public List<Donante> readAll() {
        String sql = "SELECT d.id_persona, d.tipo_donante, p.nombre, p.apellido_paterno, p.correo_electronico FROM donantes d INNER JOIN personas p ON d.id_persona = p.id_persona";
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<Donante> donantes = new ArrayList<>();
            while (rs.next()) {
                Donante donante = new Donante();
                donante.setIdPersona(rs.getInt("id_persona"));
                donante.setTipo(rs.getString("tipo_donante"));
                donantes.add(donante);
            }
            return donantes;

        } catch (SQLException ex) {
            System.out.println("Error al consultar donantes");
            System.out.println(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Método que actualiza la información de un donante
     *
     * @param donante
     * @return true si se modificó, false en caso contrario
     */
    @Override
    public boolean update(Donante donante) {
        String sql = "UPDATE donantes SET tipo_donante = ? WHERE id_persona = ?";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, donante.getTipo());
            ps.setInt(2, donante.getIdPersona());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al modificar donante");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Método que permite eliminar un donante
     *
     * @param idPersona
     * @return true si se eliminó, false en caso contrario
     */
    @Override
    public boolean delete(int idPersona) {
        String sql = "DELETE FROM donantes WHERE id_persona = ?";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPersona);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al eliminar donante");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Donante> readByFilter(String filtro) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Donante> readByFilterModal(String filtro) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
