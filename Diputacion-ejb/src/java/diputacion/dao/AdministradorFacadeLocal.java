/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Administrador;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JuanM
 */
@Local
public interface AdministradorFacadeLocal {

    void create(Administrador administrador);

    void edit(Administrador administrador);

    void remove(Administrador administrador);

    Administrador find(Object id);

    List<Administrador> findAll();

    List<Administrador> findRange(int[] range);

    int count();
    
}
