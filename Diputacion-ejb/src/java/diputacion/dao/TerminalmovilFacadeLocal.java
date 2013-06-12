/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Terminalmovil;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JuanM
 */
@Local
public interface TerminalmovilFacadeLocal {

    void create(Terminalmovil terminalmovil);

    void edit(Terminalmovil terminalmovil);

    void remove(Terminalmovil terminalmovil);

    Terminalmovil find(Object id);

    List<Terminalmovil> findAll();

    List<Terminalmovil> findRange(int[] range);

    int count();
    
}
