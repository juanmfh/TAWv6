/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.facturacion;


import diputacion.dao.FacturainFacadeLocal;
import diputacion.entity.Facturain;
import java.io.Serializable;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author David Doña Corrales
 */
@ManagedBean
public class CtrFacturacion implements Serializable {

    @EJB
    private FacturainFacadeLocal facturaInFacade;
    private Collection<Facturain> facturas;
    private Facturain facturaSelec;

    public CtrFacturacion() {
    }

    public Collection<Facturain> getFacturas() {
        return facturas;
    }

    public void setFacturas(Collection<Facturain> facturas) {
        this.facturas = facturas;
    }

    public Facturain getFacturaSelec() {
        return facturaSelec;
    }

    public void setFacturaSelec(Facturain facturaSelec) {
        this.facturaSelec = facturaSelec;
    }
    
    public String verFacturaIn(){
        return "VistaVerFactura";
    }
    
    public String borrarFacturaIn(){
        this.facturaInFacade.remove(facturaSelec);
        facturas.remove(facturaSelec);
        return "VistaFacturacion";
    }

    @PostConstruct
    public void create() {

        facturas = facturaInFacade.findAll();


    }
}
