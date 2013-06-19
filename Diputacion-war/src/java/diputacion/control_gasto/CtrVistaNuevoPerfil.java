package diputacion.control_gasto;

import diputacion.dao.PerfilFacadeLocal;
import diputacion.entity.Perfil;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author Alejandro Ruiz Moyano
 */
@ManagedBean(name = "ctrVistaNuevoPerfil")
@SessionScoped
public class CtrVistaNuevoPerfil implements Serializable {

    /**
     * Creates a new instance of CtrVistaNuevoPerfil
     */
    @EJB
    private PerfilFacadeLocal perfilFacade;
    private Date fechaFin = null;
    private double limite = 0;

    public CtrVistaNuevoPerfil() {
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public String addPerfil() {
        if (fechaFin == null || limite == 0) {
            return "VistaNuevoPerfil";
        } else {
            Perfil pf = new Perfil();
            pf.setFechaFin(fechaFin);
            pf.setLimite(limite);
            pf.setTarifaMovilidtarifaMovil(null);
            perfilFacade.create(pf);

            return "VistaAddPerfil";
        }
    }

    public String addTarifa() {
        return "VistaNuevaTarifa";
    }
}