/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.dao;

import diputacion.entity.Tarifamovil;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JuanM
 */
@Stateless
public class TarifamovilFacade extends AbstractFacade<Tarifamovil> implements TarifamovilFacadeLocal {

    @PersistenceContext(unitName = "Diputacion-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TarifamovilFacade() {
        super(Tarifamovil.class);
    }

    @Override
    public int maxID() {
        int res;
        if (count() == 0) {
            res = 0;
        } else {
            List<Tarifamovil> listaTarifas;
            listaTarifas = (List<Tarifamovil>) em.createQuery("SELECT t FROM Tarifamovil t").getResultList();
            res = listaTarifas.size();
        }

        return res;
    }
}
