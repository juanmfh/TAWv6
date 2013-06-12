/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Tarifamovil;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JuanM
 */
@Local
public interface TarifamovilFacadeLocal {

    void create(Tarifamovil tarifamovil);

    void edit(Tarifamovil tarifamovil);

    void remove(Tarifamovil tarifamovil);

    Tarifamovil find(Object id);

    List<Tarifamovil> findAll();

    List<Tarifamovil> findRange(int[] range);

    int count();
    
}
