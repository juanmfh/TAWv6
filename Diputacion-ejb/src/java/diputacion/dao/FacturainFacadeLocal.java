/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Facturain;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JuanM
 */
@Local
public interface FacturainFacadeLocal {

    void create(Facturain facturain);

    void edit(Facturain facturain);

    void remove(Facturain facturain);

    Facturain find(Object id);

    List<Facturain> findAll();

    List<Facturain> findRange(int[] range);

    int count();

    public Object maxFacturaIn();
    
}