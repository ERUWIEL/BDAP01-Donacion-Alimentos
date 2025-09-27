package com.donacionAlimentos.interfaces;

import com.donacionAlimentos.models.Organizacion;
import java.util.List;

/**
 * interfaz funcional crud para los dao
 *
 * @author erwbyel
 */
public interface IOrganizacionDAO {

    boolean create(Organizacion organizacion);

    Organizacion read(int id);

    boolean update(Organizacion organizacion);

    boolean delete(int id);

    List<Organizacion> readAll();
    
    List<Organizacion> readByFilter(String filtro);
    
    List<Organizacion> readByFilterModal(String filtro);
}
