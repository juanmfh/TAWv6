/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Terminalmovil;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JuanM
 */
@Stateless
public class TerminalmovilFacade extends AbstractFacade<Terminalmovil> implements TerminalmovilFacadeLocal {

    @PersistenceContext(unitName = "Diputacion-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TerminalmovilFacade() {
        super(Terminalmovil.class);
    }

    //METODOS JOAQUIN GARCIA
    @Override
    public int maxID() {
        int res;
        if (count() == 0) {
            res = 0;
        } else {
            List<Terminalmovil> lista;
            lista = (List<Terminalmovil>) em.createQuery("SELECT t FROM Terminalmovil t").getResultList();
            res = lista.size();
            //res = (Integer) em.createNamedQuery("Terminalmovil.maximoID").getSingleResult();
        }

        return res;
    }
}
