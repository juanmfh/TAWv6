/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Llamadamovil;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JuanM
 */
@Local
public interface LlamadamovilFacadeLocal {

    void create(Llamadamovil llamadamovil);

    void edit(Llamadamovil llamadamovil);

    void remove(Llamadamovil llamadamovil);

    Llamadamovil find(Object id);

    List<Llamadamovil> findAll();

    List<Llamadamovil> findRange(int[] range);

    int count();
    
}
