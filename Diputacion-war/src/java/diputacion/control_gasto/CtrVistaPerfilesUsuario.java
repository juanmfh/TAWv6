package diputacion.control_gasto;

import diputacion.dao.UsuarioFacadeLocal;
import diputacion.entity.Lineamovil;
import diputacion.entity.Perfil;
import diputacion.entity.Usuario;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * @author Alejandro Ruiz Moyano
 */
@ManagedBean(name = "ctrVistaPerfilesUsuario")
@RequestScoped
public class CtrVistaPerfilesUsuario implements Serializable {

    /**
     * Creates a new instance of CtrVistaPerfilesUsuario
     */
    @EJB
    private UsuarioFacadeLocal usuarioFacade;
    private Usuario usuario;
    private Usuario sessionUser;
    private Collection<Lineamovil> coleccionMoviles;
    private LinkedList<Lineamovil> moviles;
    private LinkedList<Perfil> perfiles;

    public CtrVistaPerfilesUsuario() {
    }

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        sessionUser = (Usuario) externalContext.getSessionMap().get("usuario");
        usuario = usuarioFacade.find(sessionUser.getIdusuario());
        coleccionMoviles = usuario.getLineamovilCollection();
        moviles = new LinkedList<Lineamovil>();
        perfiles = new LinkedList<Perfil>();
        for (Lineamovil l : coleccionMoviles) {
            moviles.add(l);
            perfiles.add(l.getPerfilIdperfil());
        }
    }

    public LinkedList<Lineamovil> getListaMoviles() {
        init();
        return moviles;
    }

    public void setListaMoviles(LinkedList<Lineamovil> moviles) {
        this.moviles = moviles;
    }

    public LinkedList<Perfil> getListaPerfiles() {
        init();
        return perfiles;
    }

    public void setListaPerfiles(LinkedList<Perfil> perfiles) {
        this.perfiles = perfiles;
    }
}