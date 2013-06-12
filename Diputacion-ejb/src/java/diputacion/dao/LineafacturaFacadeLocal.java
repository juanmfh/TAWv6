/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Lineafactura;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JuanM
 */
@Local
public interface LineafacturaFacadeLocal {

    void create(Lineafactura lineafactura);

    void edit(Lineafactura lineafactura);

    void remove(Lineafactura lineafactura);

    Lineafactura find(Object id);

    List<Lineafactura> findAll();

    List<Lineafactura> findRange(int[] range);

    int count();
    
}
