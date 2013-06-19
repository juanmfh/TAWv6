package diputacion.control_gasto;

import diputacion.dao.PerfilFacadeLocal;
import diputacion.entity.Perfil;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
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

    public String borrarPerfil() {
        Perfil pf = perfilFacade.find(idPerfil);
        perfilFacade.remove(pf);
        init();
        return "VistaEditarPerfiles";
    }
}