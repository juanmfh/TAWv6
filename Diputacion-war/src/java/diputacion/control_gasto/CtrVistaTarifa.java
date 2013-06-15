package diputacion.control_gasto;

import diputacion.entity.Tarifamovil;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

/**
 * @author Alejandro Ruiz Moyano
 */
@ManagedBean(name = "ctrVistaTarifa")
@SessionScoped
public class CtrVistaTarifa implements Serializable {

    /**
     * Creates a new instance of CtrVistaTarifa
     */
    private Tarifamovil tarifa;

    public CtrVistaTarifa() {
    }

    @PostConstruct
    public void init() {
        tarifa = null;
    }

    public Tarifamovil getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifamovil tarifa) {
        this.tarifa = tarifa;
    }

    public String verTarifa() {
        return "VistaVerTarifa";
    }
}