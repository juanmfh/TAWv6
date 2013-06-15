package diputacion.control_gasto;

import diputacion.dao.AdministradorFacadeLocal;
import diputacion.dao.LineamovilFacadeLocal;
import diputacion.dao.PerfilFacadeLocal;
import diputacion.entity.Administrador;
import diputacion.entity.Lineamovil;
import diputacion.entity.Perfil;
import diputacion.entity.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * @author Alejandro Ruiz Moyano
 */
@ManagedBean(name = "ctrVistaPerfilesAdministrador")
@SessionScoped
public class CtrVistaPerfilesAdministrador implements Serializable {

    /**
     * Creates a new instance of CtrVistaPerfilesAdministrador
     */
    @EJB
    private AdministradorFacadeLocal adminFacade;
    @EJB
    private LineamovilFacadeLocal lineaMovilFacade;
    @EJB
    private PerfilFacadeLocal perfilFacade;
    private Administrador admin;
    private Collection<Lineamovil> coleccionMoviles;
    private LinkedList<Lineamovil> moviles;
    private LinkedList<Perfil> perfiles;
    private Integer idLinea;
    private Integer idPerfil;

    public CtrVistaPerfilesAdministrador() {
    }

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Usuario sessionUser = (Usuario) externalContext.getSessionMap().get("usuario");
        if (sessionUser != null) {
            admin = adminFacade.find(sessionUser.getIdusuario());
            if (admin == null) {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().dispatch("/index-logued.jsf");
                } catch (IOException ex) {
                    Logger.getLogger(CtrVistaPerfilesAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {

            try {
                FacesContext.getCurrentInstance().getExternalContext().dispatch("/index-logued.jsf");
            } catch (IOException ex) {
                Logger.getLogger(CtrVistaPerfilesAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        coleccionMoviles = lineaMovilFacade.findAll();
        moviles = new LinkedList<Lineamovil>();
        perfiles = new LinkedList<Perfil>();
        for (Lineamovil l : coleccionMoviles) {
            moviles.add(l);
            perfiles.add(l.getPerfilIdperfil());
        }
    }

    public LinkedList<Lineamovil> getListaMoviles() {
        return moviles;
    }

    public void setListaMoviles(LinkedList<Lineamovil> moviles) {
        this.moviles = moviles;
    }

    public LinkedList<Perfil> getListaPerfiles() {
        return perfiles;
    }

    public void setListaPerfiles(LinkedList<Perfil> perfiles) {
        this.perfiles = perfiles;
    }

    public Integer getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(Integer id) {
        this.idLinea = id;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer id) {
        this.idPerfil = id;
    }

    public String add() {
        Lineamovil lm = lineaMovilFacade.find(idLinea);
        if (lm.getPerfilIdperfil() == null) {
            Collection<Perfil> coleccionPerfiles = perfilFacade.findAll();
            perfiles = new LinkedList<Perfil>();
            for (Perfil pf : coleccionPerfiles) {
                perfiles.add(pf);
            }
            return "VistaAddPerfil";
        } else {
            return "VistaPerfilesAdministrador";
        }
    }

    public String delete() {
        Lineamovil lm = lineaMovilFacade.find(idLinea);
        Perfil pf = lm.getPerfilIdperfil();
        if (pf != null) {
            pf.getLineamovilCollection().remove(lm);
            lm.setPerfilIdperfil(null);
            lineaMovilFacade.edit(lm);
            perfilFacade.edit(pf);
        }
        this.init();
        return "VistaPerfilesAdministrador";
    }

    public String perfilSeleccionado() {
        Lineamovil lm = lineaMovilFacade.find(idLinea);
        Perfil pf = perfilFacade.find(idPerfil);
        lm.setPerfilIdperfil(pf);
        pf.getLineamovilCollection().add(lm);
        lineaMovilFacade.edit(lm);
        perfilFacade.edit(pf);
        this.init();
        return "VistaPerfilesAdministrador";
    }
}