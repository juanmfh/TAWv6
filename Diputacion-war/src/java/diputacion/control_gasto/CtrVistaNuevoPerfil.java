package diputacion.control_gasto;

import diputacion.dao.PerfilFacadeLocal;
import diputacion.dao.TarifamovilFacadeLocal;
import diputacion.entity.Perfil;
import diputacion.entity.Tarifamovil;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
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
    @EJB
    private TarifamovilFacadeLocal tarifaFacade;
    private List<String> listaTarifas;
    private List<Tarifamovil> listaTarifasMoviles;
    private String fechaFin;
    private Date fechaFinReal;
    private String limite;
    private double limiteReal;
    private Tarifamovil tarifa;

    public CtrVistaNuevoPerfil() {
    }

    @PostConstruct
    public void init() {
        listaTarifas = new LinkedList<String>();
        listaTarifasMoviles = tarifaFacade.findAll();
        for (int i = 0; i < listaTarifasMoviles.size(); i++) {
            listaTarifas.add(listaTarifasMoviles.get(i).getNombre());
        }
        limite = "";
        fechaFin = "";  
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getLimite() {
        return limite;
    }

    public void setLimite(String limite) {
        this.limite = limite;
    }

    public Tarifamovil getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifamovil tarifa) {
        this.tarifa = tarifa;
    }

    public List<String> getListaTarifas() {
        return listaTarifas;
    }

    public void setListaTarifas(List<String> listaTarifas) {
        this.listaTarifas = listaTarifas;
    }

    public String addPerfil() {
        if ("".equals(limite)) {
            return "VistaNuevoPerfil";
        } else {
            limiteReal = Double.valueOf(limite);
            if (limiteReal == 0) {
                return "VistaNuevoPerfil";
            }
            Perfil pf = new Perfil();
            pf.setIdperfil(perfilFacade.maxID() + 1);
            pf.setLimite(limiteReal);
            perfilFacade.create(pf);
            return "VistaAddPerfil";
        }
    }

    public String addTarifa() {
        return "VistaNuevaTarifa";
    }
}