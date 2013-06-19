/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.facturacion;

import diputacion.dao.ComparativaFacadeLocal;
import diputacion.entity.Comparativa;
import java.io.Serializable;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author David Dona Corrales
 */
@ManagedBean
public class CtrGestionComparativas implements Serializable {

    @EJB
    private ComparativaFacadeLocal comparativaFacade;
    private Collection<Comparativa> comparativas;
    private Comparativa comparativaSelec;

    public Comparativa getComparativaSelec() {
        return comparativaSelec;
    }

    public void setComparativaSelec(Comparativa comparativaSelec) {
        this.comparativaSelec = comparativaSelec;
    }

    public CtrGestionComparativas() {
    }

    public Collection<Comparativa> getComparativas() {
        return comparativas;
    }

    public void setComparativas(Collection<Comparativa> comparativas) {
        this.comparativas = comparativas;
    }

    public String borrarComparativa() {
        this.comparativaFacade.remove(comparativaSelec);
        comparativas.remove(comparativaSelec);
        return "VistaGestionComparativas";
    }

    public void verComparativa() {


        String valor = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap().get("comparativaSeleccionada");
        /*Comparativa c = new Comparativa();
        c.setIdcomparativa(Integer.parseInt(valor));
        comparativaFacade.create(c);*/
        
        comparativaSelec = comparativaFacade.find(Integer.parseInt(valor));
        
        

    }

    @PostConstruct
    public void create() {
        comparativas = comparativaFacade.findAll();
        comparativaSelec = new Comparativa();



    }
}
