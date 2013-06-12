/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Controlador;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JuanM
 */
@Local
public interface ControladorFacadeLocal {

    void create(Controlador controlador);

    void edit(Controlador controlador);

    void remove(Controlador controlador);

    Controlador find(Object id);

    List<Controlador> findAll();

    List<Controlador> findRange(int[] range);

    int count();
    
}
