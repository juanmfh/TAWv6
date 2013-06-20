/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.grupos_rescate;


import diputacion.entity.Administrador;
import diputacion.entity.Gruporescate;
import diputacion.entity.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import diputacion.dao.AdministradorFacadeLocal;
import diputacion.dao.GruporescateFacadeLocal;
import diputacion.dao.UsuarioFacadeLocal;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JuanM
 */
@ManagedBean(name = "ctrVistaGrupos_Rescate")
@SessionScoped
public class CtrVistaGrupos_Rescate implements Serializable {
    
    
    @EJB
    private AdministradorFacadeLocal administradorFacade;
    @EJB
    private UsuarioFacadeLocal usuarioFacade;
    @EJB
    private GruporescateFacadeLocal grupoRescateFacade;
    
    private Collection<Gruporescate> gruposRescate;
    private Gruporescate selectedGroup;
    private Usuario selectedUser;
    private String localizacion;
    private Map<Integer, Boolean> seleccionados;
    private Collection<Usuario> usuarios;
    // Para la sesion
    private Administrador administrador;
    private Usuario usuario;

    /**
     * Creates a new instance of CtrVistaGrupos_Rescate
     */
    public CtrVistaGrupos_Rescate() {
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public Map<Integer, Boolean> getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(Map<Integer, Boolean> seleccionados) {
        this.seleccionados = seleccionados;
    }

    public Collection<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Collection<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Usuario selectedUser) {
        this.selectedUser = selectedUser;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public Collection<Gruporescate> getGruposRescate() {
        return gruposRescate;
    }

    public void setGruposRescate(Collection<Gruporescate> gruposRescate) {
        this.gruposRescate = gruposRescate;
    }

    public Gruporescate getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(Gruporescate selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public String borrarGrupoRescate() {
        Collection<Usuario> userSelectGroup = selectedGroup.getUsuarioCollection();
        for (Usuario u : userSelectGroup) {
            u.setGrupoRescateidgrupoRescate(null);
            usuarioFacade.edit(u);
        }
        grupoRescateFacade.remove(selectedGroup);
        gruposRescate.remove(selectedGroup);
        return "VistaGrupos_Rescate";
    }

    public String ver() {
        return "VistaVerGrupo_Rescate";
    }

    public String eliminarUsuario() {
        selectedGroup.getUsuarioCollection().remove(selectedUser);
        grupoRescateFacade.edit(selectedGroup);
        selectedUser.setGrupoRescateidgrupoRescate(null);
        usuarioFacade.edit(selectedUser);
        usuarios.add(selectedUser);
        return "VistaModificarGrupo_Rescate";
    }

    public String modificar() {
        localizacion = selectedGroup.getLocalizacion();
        usuarios = usuarioFacade.findNotGrupoRescate();
        return "VistaModificarGrupo_Rescate";
    }

    public String modificarGrupo() {
        selectedGroup.setLocalizacion(localizacion);
        for (Usuario item : usuarios) {
            if (seleccionados.get(item.getIdusuario())) {
                selectedGroup.getUsuarioCollection().add(item);
                item.setGrupoRescateidgrupoRescate(selectedGroup);
                usuarioFacade.edit(item);
            }
        }
        grupoRescateFacade.edit(selectedGroup);
        this.inicializar();
        return "VistaGrupos_Rescate";
    }

    public String verInfoUsuario() {
        return "VerInfoUsuario";
    }

    public String listaUsuarios() {
        usuarios = usuarioFacade.findAll();
        return "VistaListaUsuariosGrupoRescate";
    }

    public String crear() {
        usuarios = usuarioFacade.findAll();
        seleccionados = new HashMap<Integer, Boolean>();
        localizacion = "";
        return "VistaAnadirGrupo_Rescate";
    }

    public String crearGrupoRescate() {
            Gruporescate g = new Gruporescate();

            List<Usuario> checkedItems = new ArrayList<Usuario>();

            for (Usuario item : usuarios) {
                if (seleccionados.get(item.getIdusuario())) {
                    checkedItems.add(item);
                }
            }

            g.setLocalizacion(this.localizacion);
            g.setUsuarioCollection(checkedItems);
            grupoRescateFacade.create(g);

            for (Usuario item : checkedItems) {
                item.setGrupoRescateidgrupoRescate(g);
                usuarioFacade.edit(item);
            }
            gruposRescate.add(g);
            usuarios = usuarioFacade.findAll();
            seleccionados = new HashMap<Integer, Boolean>();
            return "VistaGrupos_Rescate";
    }

    public boolean administradorLogeado() {
        return administrador != null;
    }

    @PostConstruct
    public void inicializar() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        usuario = (Usuario) externalContext.getSessionMap().get("usuario");
        if (usuario != null) {
            administrador = administradorFacade.find(usuario.getIdusuario());
            if (administrador == null) {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("../../ErrorAutorizacion.jsf");
                } catch (IOException ex) {
                    Logger.getLogger(CtrVistaGrupos_Rescate.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../../ErrorAutorizacion.jsf");
            } catch (IOException ex) {
                Logger.getLogger(CtrVistaGrupos_Rescate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        gruposRescate = grupoRescateFacade.findAll();
        usuarios = usuarioFacade.findAll();
        seleccionados = new HashMap<Integer, Boolean>();
    }
}