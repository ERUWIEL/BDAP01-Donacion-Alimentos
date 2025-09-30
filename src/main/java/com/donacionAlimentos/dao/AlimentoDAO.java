package com.donacionAlimentos.dao;

import com.donacionAlimentos.config.ConexionDB;
import com.donacionAlimentos.interfaces.IAlimentoDAO;
import com.donacionAlimentos.models.Alimento;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * clase de acceso de datos ala tabla alimentos
 *
 * @author erwbyel
 */
public class AlimentoDAO implements IAlimentoDAO {

    public AlimentoDAO() {
    }

    /**
     * Método que permite insertar un alimento
     *
     * @param alimento
     * @return true si se insertó correctamente, false en caso contrario
     */
    @Override
    public boolean create(Alimento alimento) {
        String sql = "INSERT INTO alimentos(nombre, cantidad, tipo_alimento) VALUES(?,?,?)";
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, alimento.getNombre());
            ps.setBigDecimal(2, alimento.getCantidad());
            ps.setString(3, alimento.getTipo());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al agregar alimento");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Método que busca alimento en la base de datos
     *
     * @param id
     * @return Alimento si existe, null caso contrario
     */
    @Override
    public Alimento read(int id) {
        String sql = "SELECT id_alimento, nombre, cantidad, tipo_alimento FROM alimentos WHERE id_alimento = ?";
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Alimento alimento = new Alimento();
                    alimento.setIdAlimento(rs.getInt("id_alimento"));
                    alimento.setNombre(rs.getString("nombre"));
                    alimento.setCantidad(rs.getBigDecimal("cantidad"));
                    alimento.setTipo(rs.getString("tipo_alimento"));
                    return alimento;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar alimento");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Método que consulta todos los alimentos en la base de datos
     *
     * @return Lista de Alimentos
     */
    @Override
    public List<Alimento> readAll() {
        String sql = "SELECT id_alimento, nombre, cantidad, tipo_alimento FROM alimentos";
        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<Alimento> alimentos = new ArrayList<>();
            while (rs.next()) {
                Alimento alimento = new Alimento();
                alimento.setIdAlimento(rs.getInt("id_alimento"));
                alimento.setNombre(rs.getString("nombre"));
                alimento.setCantidad(rs.getBigDecimal("cantidad"));
                alimento.setTipo(rs.getString("tipo_alimento"));
                alimentos.add(alimento);
            }
            return alimentos;

        } catch (SQLException ex) {
            System.out.println("Error al consultar alimentos");
            System.out.println(ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Método que actualiza la información de un alimento
     *
     * @param alimento
     * @return true si se modificó, false en caso contrario
     */
    @Override
    public boolean update(Alimento alimento) {
        String sql = "UPDATE alimentos SET nombre = ?, cantidad = ?, tipo_alimento = ? WHERE id_alimento = ?";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, alimento.getNombre());
            ps.setBigDecimal(2, alimento.getCantidad());
            ps.setString(3, alimento.getTipo());
            ps.setInt(4, alimento.getIdAlimento());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al modificar alimento");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Método que permite eliminar un alimento
     *
     * @param id
     * @return true si se eliminó, false en caso contrario
     */
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM alimentos WHERE id_alimento = ?";

        try (Connection cn = ConexionDB.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Error al eliminar alimento");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * metodo que filtra por filtro xd
     * @param filtro
     * @return 
     */
    @Override
    public List<Alimento> readByFilter(String filtro) {
        String sql = "SELECT id_alimento, nombre, cantidad, tipo_alimento FROM alimentos WHERE nombre LIKE ? LIMIT 100";
        List<Alimento> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + filtro + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Alimento alimento = new Alimento();
                alimento.setIdAlimento(rs.getInt("id_alimento"));
                alimento.setNombre(rs.getString("nombre"));
                alimento.setTipo(rs.getString("tipo_alimento"));
                alimento.setCantidad(new BigDecimal(rs.getString("cantidad")));

                lista.add(alimento);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener alimentos por filtro: " + e.getMessage());
        }

        return lista;
    }

    /**
     * metodo que filtra modal
     * @param filtro
     * @return 
     */
    @Override
    public List<Alimento> readByFilterModal(String filtro) {
        String sql = "SELECT id_alimento, nombre, cantidad, tipo_alimento FROM alimentos WHERE nombre LIKE ? OR tipo_alimento LIKE ? LIMIT 100";
        List<Alimento> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + filtro + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Alimento alimento = new Alimento();
                alimento.setIdAlimento(rs.getInt("id_alimento"));
                alimento.setNombre(rs.getString("nombre"));
                alimento.setTipo(rs.getString("tipo_alimento"));
                alimento.setCantidad(new BigDecimal(rs.getString("cantidad")));

                lista.add(alimento);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener alimentos por filtro: " + e.getMessage());
        }

        return lista;
    }
}
