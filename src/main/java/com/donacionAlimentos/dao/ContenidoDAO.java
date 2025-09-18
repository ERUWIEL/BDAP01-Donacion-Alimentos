package com.donacionAlimentos.dao;

import com.donacionAlimentos.config.ConexionDB;
import com.donacionAlimentos.interfaces.IContenidoDAO;
import com.donacionAlimentos.models.Contenido;
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
public class ContenidoDAO implements IContenidoDAO {

    /**
     * Método que permite insertar un contenido
     *
     * @param contenido
     * @return true si se insertó correctamente, false en caso contrario
     */
    @Override
    public boolean create(Contenido contenido) {
        String sql = "INSERT INTO contenidos(id_alimento, id_entrega, id_aportacion) VALUES(?,?,?)";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, contenido.getIdAlimento());
            ps.setInt(2, contenido.getIdEntrega());
            ps.setInt(3, contenido.getIdAportacion());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al agregar contenido");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Método que busca contenido en la base de datos
     *
     * @param id
     * @return Contenido si existe, null caso contrario
     */
    @Override
    public Contenido read(int id) {
        String sql = "SELECT id_contenido, id_alimento, id_entrega, id_aportacion FROM contenidos WHERE id_contenido = ?";
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Contenido contenido = new Contenido();
                    contenido.setIdContenido(rs.getInt("id_contenido"));
                    contenido.setIdAlimento(rs.getInt("id_alimento"));
                    contenido.setIdEntrega(rs.getInt("id_entrega"));
                    contenido.setIdAportacion(rs.getInt("id_aportacion"));
                    return contenido;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar contenido");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Método que consulta todos los contenidos en la base de datos
     *
     * @return Lista de Contenidos
     */
    @Override
    public List<Contenido> readAll() {
        String sql = "SELECT id_contenido, id_alimento, id_entrega, id_aportacion FROM contenidos";
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<Contenido> contenidos = new ArrayList<>();
            while (rs.next()) {
                Contenido contenido = new Contenido();
                contenido.setIdContenido(rs.getInt("id_contenido"));
                contenido.setIdAlimento(rs.getInt("id_alimento"));
                contenido.setIdEntrega(rs.getInt("id_entrega"));
                contenido.setIdAportacion(rs.getInt("id_aportacion"));
                contenidos.add(contenido);
            }
            return contenidos;

        } catch (SQLException ex) {
            System.out.println("Error al consultar contenidos");
            System.out.println(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Método que actualiza la información de un contenido
     *
     * @param contenido
     * @return true si se modificó, false en caso contrario
     */
    @Override
    public boolean update(Contenido contenido) {
        String sql = "UPDATE contenidos SET id_alimento = ?, id_entrega = ?, id_aportacion = ? WHERE id_contenido = ?";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, contenido.getIdAlimento());
            ps.setInt(2, contenido.getIdEntrega());
            ps.setInt(3, contenido.getIdAportacion());
            ps.setInt(4, contenido.getIdContenido());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al modificar contenido");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Método que permite eliminar un contenido
     *
     * @param id
     * @return true si se eliminó, false en caso contrario
     */
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM contenidos WHERE id_contenido = ?";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al eliminar contenido");
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
