/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.directorio_telefonico;

import diputacion.dao.TerminalfijoFacadeLocal;
import diputacion.entity.Terminalfijo;
import java.io.Serializable;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Joaquin
 */
@ManagedBean(name = "ctrlDirectorioTelefonico")
@SessionScoped
public class CtrlDirectorioTelefonico implements Serializable {

    @EJB
    private TerminalfijoFacadeLocal terminalfijoFacade;
    
    //VARIABLES
    private Collection<Terminalfijo> terminales;
    private String municipio, nombre, apellido1, numero;

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

    public String volver() {
        inicializacionDirectorio();
        return "../../index";
    }

    public String listadoFiltrado() {
        nombre = "";
        apellido1 = "";
        numero = "";
        terminales = terminalfijoFacade.terminalesfiltradas(municipio);
        return "LineasFijasMunicipiosFiltrado";
    }

    public String listadoFiltradoNumero() {
        municipio = "";
        nombre = "";
        apellido1 = "";
        if (numero.length() > 0) {
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

    
}
