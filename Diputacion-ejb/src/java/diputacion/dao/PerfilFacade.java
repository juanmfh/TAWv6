/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Perfil;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JuanM
 */
@Stateless
public class PerfilFacade extends AbstractFacade<Perfil> implements PerfilFacadeLocal {

    @PersistenceContext(unitName = "Diputacion-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PerfilFacade() {
        super(Perfil.class);
    }

    @Override
    public int maxID() {
        int res;
        if (count() == 0) {
            res = 0;
        } else {
            List<Perfil> listaPerfiles;
            listaPerfiles = (List<Perfil>) em.createQuery("SELECT t FROM Perfil t").getResultList();
            res = listaPerfiles.size();
        }

        return res;
    }
}
