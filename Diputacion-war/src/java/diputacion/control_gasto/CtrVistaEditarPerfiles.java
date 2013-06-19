package diputacion.control_gasto;

import diputacion.dao.PerfilFacadeLocal;
import diputacion.dao.TarifamovilFacadeLocal;
import diputacion.entity.Perfil;
import diputacion.entity.Tarifamovil;
import java.io.Serializable;
import java.util.Collection;
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
@ManagedBean(name = "ctrVistaEditarPerfiles")
@SessionScoped
public class CtrVistaEditarPerfiles implements Serializable {

    /**
     * Creates a new instance of CtrVistaEditarPerfiles
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
    private Collection<Perfil> coleccionPerfiles;
    private LinkedList<Perfil> listaPerfiles;
    private Integer idPerfil;

    public CtrVistaEditarPerfiles() {
    }

    @PostConstruct
    public void init() {
        listaPerfiles = new LinkedList<Perfil>();
        coleccionPerfiles = perfilFacade.findAll();
        for (Perfil pf : coleccionPerfiles) {
            listaPerfiles.add(pf);
        }
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

    public LinkedList<Perfil> getListaPerfiles() {
        return listaPerfiles;
    }

    public void setListaPerfiles(LinkedList<Perfil> perfiles) {
        this.listaPerfiles = perfiles;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer id) {
        this.idPerfil = id;
    }

    public String nuevoPerfil() {
        return "VistaNuevoPerfil";
    }

    public String nuevaTarifa() {
        return "VistaNuevaTarifa";
    }

    public String borrarPerfil() {
        Perfil pf = perfilFacade.find(idPerfil);
        if (pf.getLineamovilCollection().isEmpty()) {
            perfilFacade.remove(pf);
            init();
        }
        return "VistaEditarPerfiles";
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
            pf.setLineamovilCollection(null);
            perfilFacade.create(pf);
            this.init();
            return "VistaEditarPerfiles";
        }
    }

    public String addTarifa() {
        return "VistaNuevaTarifa";
    }
}