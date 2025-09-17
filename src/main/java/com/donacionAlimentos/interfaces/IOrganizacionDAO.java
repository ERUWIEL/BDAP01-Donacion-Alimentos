package com.donacionAlimentos.interfaces;

import com.donacionAlimentos.models.Organizacion;
import java.util.List;

/**
 * interfaz funcional crud para los dao
 *
 * @author erwbyel
 */
public interface IOrganizacionDAO {

    void create(Organizacion organizacion);

    Organizacion read(int id);

    void update(Organizacion organizacion);

    void delete(int id);

    List<Organizacion> readAll();
}
