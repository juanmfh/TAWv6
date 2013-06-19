/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.directorio_telefonico;

import diputacion.dao.AdministradorFacadeLocal;
import diputacion.dao.TerminalfijoFacadeLocal;
import diputacion.dao.UsuarioFacadeLocal;
import diputacion.entity.Administrador;
import diputacion.entity.Terminalfijo;
import diputacion.entity.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Joaquin
 */
@ManagedBean(name = "ctrlDirectorioTelefonico")
@SessionScoped
public class CtrlDirectorioTelefonico implements Serializable {

    @EJB
    private TerminalfijoFacadeLocal terminalfijoFacade;
    @EJB
    private AdministradorFacadeLocal administradorFacade;
    @EJB
    private UsuarioFacadeLocal usuarioFacade;
    //VARIABLES
    private Collection<Terminalfijo> terminales;
    private String municipio, nombre, apellido1, numero;
    private Usuario usuario;
    private Administrador administrador;
    private boolean admin = true;

    public CtrlDirectorioTelefonico() {
    }

    //GETTER AND SETTER
    public void setNumero(String n) {
        numero = n;
    }

    public String getNumero() {
        return numero;
    }

    public void setMunicipio(String m) {
        municipio = m;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setNombre(String m) {
        nombre = m;
    }

    public String getNombre() {
        return nombre;
    }

    public void setApellido1(String m) {
        apellido1 = m;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setTerminales(Collection<Terminalfijo> tf) {
        terminales = tf;
    }

    public Collection<Terminalfijo> getTerminales() {
        return terminales;
    }

    //METODOS------------------------------------------------------------------
    @PostConstruct
    public void inicializacionDirectorio() {
        terminales = terminalfijoFacade.terminalesOrdenadas();
    }

    public String listadoTelefonico() {
        this.inicializacionDirectorio();
        return "directorio_telefonico/DirectorioTelefonico";
    }

    public void volver() throws IOException {
        inicializacionDirectorio();
        FacesContext.getCurrentInstance().getExternalContext().redirect("../../index-logued.jsf");
    }

    public String listadoFiltrado() {
        nombre = "";
        apellido1 = "";
        numero = "";
        if (municipio.length() > 0) {
            terminales = terminalfijoFacade.terminalesfiltradas(municipio);
            return "LineasFijasMunicipiosFiltrado";
        } else {
            return "LineasFijasMunicipios";
        }
    }

    public String listadoFiltradoNumero() {
        municipio = "";
        nombre = "";
        apellido1 = "";
        if (numero.length() > 0 && esNumero(numero)) {
            terminales = terminalfijoFacade.terminalesfiltradasNumero(Integer.parseInt(numero));
            return "DirectorioFiltradoNumero";
        } else {
            return directorioTelefonicoCompleto();
        }
    }

    public String listadoFiltradoNombreApellido() {
        municipio = "";
        numero = "";
        if (nombre.length() > 0 || apellido1.length() > 0) {
            terminales = terminalfijoFacade.terminalesfiltradasNombre(nombre, apellido1);
            return "DirectorioFiltradoNombre";
        } else {
            return directorioTelefonicoCompleto();
        }
    }

    public String directorioTelefonicoCompleto() {
        this.inicializacionDirectorio();
        return "DirectorioTelefonico";
    }

    public String pagListado() {

        admin = esAdministrador();

        if (admin) {
            this.inicializacionDirectorio();
            return "jsf/directorio_telefonico/LineasFijasMunicipios.jsf";
        } else {

            return "ErrorAutorizacion.jsf";
        }
    }

    //METODO QUE COMPRUEBA SI SOMOS ADMINISTRADOR
    public boolean esAdministrador() {
        boolean res = true;

        // Obtenemos el usuario logeado desde la sesion
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        usuario = (Usuario) externalContext.getSessionMap().get("usuario");

        if (usuario != null) {
            administrador = administradorFacade.find(usuario.getIdusuario());

            if (administrador == null) {
                res = false;
            }
        } else {
            res = true;
        }

        return res;
    }

    //FUNCION AUXILIAR PARA SABER SI EL STRING ES UN NUMERO
    private boolean esNumero(String numero) {
        boolean res = true;
        try {
            System.out.println("EEEEEEEEEE ENTRA");
            Integer.parseInt(numero);
        } catch (Exception e) {
            res = false;
        }
        return res;
    }
}
