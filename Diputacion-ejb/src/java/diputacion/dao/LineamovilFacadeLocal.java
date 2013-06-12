/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Lineamovil;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JuanM
 */
@Local
public interface LineamovilFacadeLocal {

    void create(Lineamovil lineamovil);

    void edit(Lineamovil lineamovil);

    void remove(Lineamovil lineamovil);

    Lineamovil find(Object id);

    List<Lineamovil> findAll();

    List<Lineamovil> findRange(int[] range);

    int count();
    
}
