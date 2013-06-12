/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Facturaout;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JuanM
 */
@Local
public interface FacturaoutFacadeLocal {

    void create(Facturaout facturaout);

    void edit(Facturaout facturaout);

    void remove(Facturaout facturaout);

    Facturaout find(Object id);

    List<Facturaout> findAll();

    List<Facturaout> findRange(int[] range);

    int count();
    
}
