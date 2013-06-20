/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.gestion_terminales;

import diputacion.dao.AdministradorFacadeLocal;
import diputacion.dao.LineamovilFacadeLocal;
import diputacion.dao.TerminalmovilFacadeLocal;
import diputacion.dao.UsuarioFacadeLocal;
import diputacion.entity.Administrador;
import diputacion.entity.Lineamovil;
import diputacion.entity.Terminalmovil;
import diputacion.entity.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.StringTokenizer;
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
@ManagedBean(name = "ctrGestionTerminalesMovil")
@SessionScoped
public class CtrGestionTerminalesMovil implements Serializable {

    @EJB
    private TerminalmovilFacadeLocal terminalmovilFacade;
    @EJB
    private LineamovilFacadeLocal lineamovilFacade;
    @EJB
    private AdministradorFacadeLocal administradorFacade;
    @EJB
    private UsuarioFacadeLocal usuarioFacade;
    //VARIABLES
    private String marca, modelo, sistemaOperativo, fecha, linea;
    private Date fechaAlta;
    private Collection<Terminalmovil> terminales;
    private Terminalmovil terminalSeleccinado;
    private Usuario usuarioSeleccionado, usuario;
    private Administrador administrador;
    private boolean admin = true;
    private Collection<Usuario> usuarios;
    private Collection<Terminalmovil> terminalesLibres;
    private String nombre, apellidos;

    //CONSTRUCTOR
    public CtrGestionTerminalesMovil() {
    }

    //GETTER AND SETTER
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String n) {
        nombre = n;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String a) {
        apellidos = a;
    }

    public Terminalmovil getTerminalSeleccionado() {
        return terminalSeleccinado;
    }

    public void setTerminalSeleccionado(Terminalmovil tm) {
        terminalSeleccinado = tm;
    }

    public Collection<Terminalmovil> getTerminalesLibres() {
        return terminalesLibres;
    }

    public void setTerminalesLibres(Collection<Terminalmovil> t) {
        terminalesLibres = t;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario u) {
        usuarioSeleccionado = u;
    }

    public Collection<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Collection<Usuario> u) {
        usuarios = u;
    }

    public void setMarca(String m) {
        marca = m;
    }

    public String getMarca() {
        return marca;
    }

    public void setModelo(String m) {
        modelo = m;
    }

    public String getModelo() {
        return modelo;
    }

    public void setSistemaOperativo(String so) {
        sistemaOperativo = so;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setLinea(String l) {
        linea = l;
    }

    public String getLinea() {
        return linea;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String f) {
        fecha = f;
    }

    public Collection<Terminalmovil> getTerminales() {
        return terminales;
    }

    public void setFecha(Collection<Terminalmovil> t) {
        terminales = t;
    }

    //METODOS-------------------------------------------------------------------
    @PostConstruct
    public void inicializacion() {
        terminales = terminalmovilFacade.findAll();
        //OBTENEMOS LA FECHA DE SISTEMA
        int dia = new java.util.Date().getDate();
        int mes = new java.util.Date().getMonth() + 1;
        int ano = new java.util.Date().getYear() + 1900;
        fecha = "";
        fecha += dia + "/" + mes + "/" + ano;
        marca = "";
        modelo = "";
        linea = "";
        sistemaOperativo = "";
        nombre = "";
        apellidos = "";
        //RECOGEMOS LOS USUARIOS
        usuarios = usuarioFacade.findAll();

    }

    public String insertarMovil() {
        //CREAMOS EL OBJETO TERMINAL MOVIL
        Terminalmovil tmnuevo;
        Lineamovil lm;
        int id;

        id = terminalmovilFacade.maxID() + 1;
        tmnuevo = new Terminalmovil(id);
        tmnuevo.setMarca(marca);
        tmnuevo.setModelo(modelo);
        tmnuevo.setSistemaOperativo(sistemaOperativo);


        //SI LA LINEA NO ES VACIA INSERTAMOS UNA NUEVA LINEA EN EL SISTEMA
        if (linea.length() > 0 && esNumero(linea)) {
            int idLinea;
            idLinea = lineamovilFacade.maxID() + 1;
            lm = new Lineamovil(idLinea);
            //PASEAMOS LA FECHA
            if (fecha.length() > 0) {
                StringTokenizer tokens = new StringTokenizer(fecha, "/");
                int[] datos = new int[3];
                int i = 0;
                while (tokens.hasMoreTokens()) {
                    String str = tokens.nextToken();
                    datos[i] = Integer.parseInt(str);
                    System.out.println(datos[i]);
                    i++;
                }
                fechaAlta = new java.util.Date(datos[2] - 1900, datos[1] - 1, datos[0]);
                lm.setFechaAlta(fechaAlta);
            }

            lm.setNumero(Integer.parseInt(linea));
            lineamovilFacade.create(lm);
            tmnuevo.setLineaidlineaMovil(lm);
        } else {
            return "FormularioInsertarMovilErrorLinea";
        }
        //INSERTAMOS EN LA BD
        terminalmovilFacade.create(tmnuevo);
        this.inicializacion();

        return "ListadoTerminalMovil";
    }

    public String modificarTerminalMovil() {
        marca = terminalSeleccinado.getMarca();
        modelo = terminalSeleccinado.getModelo();
        sistemaOperativo = terminalSeleccinado.getSistemaOperativo();
        linea = "";
        if (terminalSeleccinado.getLineaidlineaMovil() != null) {
            linea += terminalSeleccinado.getLineaidlineaMovil().getNumero();
        }
        fecha = "";
        if (terminalSeleccinado.getLineaidlineaMovil() != null || terminalSeleccinado.getLineaidlineaMovil().getFechaAlta() != null) {

            int dia = terminalSeleccinado.getLineaidlineaMovil().getFechaAlta().getDate();
            int mes = terminalSeleccinado.getLineaidlineaMovil().getFechaAlta().getMonth() + 1;
            int ano = terminalSeleccinado.getLineaidlineaMovil().getFechaAlta().getYear() + 1900;
            fecha += dia + "/" + mes + "/" + ano;
        }
        if(terminalSeleccinado.getLineaidlineaMovil().getUsuarioIdusuario()!=null && terminalSeleccinado.getLineaidlineaMovil().getUsuarioIdusuario().getNombre().length()>0)
        {
            nombre=terminalSeleccinado.getLineaidlineaMovil().getUsuarioIdusuario().getNombre();
        }
        else
        {
            nombre="";
        }
        if(terminalSeleccinado.getLineaidlineaMovil().getUsuarioIdusuario()!=null && terminalSeleccinado.getLineaidlineaMovil().getUsuarioIdusuario().getApellido1().length()>0 )
        {
            apellidos=terminalSeleccinado.getLineaidlineaMovil().getUsuarioIdusuario().getApellido1();
            if(terminalSeleccinado.getLineaidlineaMovil().getUsuarioIdusuario().getApellido2().length()>0)
            {
                apellidos += " ";
                apellidos+=terminalSeleccinado.getLineaidlineaMovil().getUsuarioIdusuario().getApellido2();
            }
        }
        else
        {
            apellidos="";
        }
        return "FormularioModificaMovil";
    }

    public void borrarTerminalMovil() {
        terminalmovilFacade.remove(terminalSeleccinado);
        this.inicializacion();
    }

    public String modificarTerminalMovilDatos() {
        //CREAMOS EL OBJETO TERMINAL FIJO
        Lineamovil lm = null;

        terminalSeleccinado.setMarca(marca);
        terminalSeleccinado.setModelo(modelo);
        terminalSeleccinado.setSistemaOperativo(sistemaOperativo);

        //SI LA LINEA NO ES VACIA INSERTAMOS UNA NUEVA LINEA EN EL SISTEMA
        //COMPROBAMOS SI LA LINEA EXISTE O ES UNA NUEVA.
        String aux;
        aux = linea;
        if (aux.length() == 0) {
            aux = "0";
        }
        lm = lineamovilFacade.buscarNumero(Integer.parseInt(aux));

        if (linea.length() > 0 && lm != null) {
            //int idLinea;
            //idLinea = lineamovilFacade.maxID() + 1;
            //lm = new Lineamovil(idLinea);

            //PASEAMOS LA FECHA
            if (fecha.length() > 0) {
                StringTokenizer tokens = new StringTokenizer(fecha, "/");
                int[] datos = new int[3];
                int i = 0;
                while (tokens.hasMoreTokens()) {
                    String str = tokens.nextToken();
                    datos[i] = Integer.parseInt(str);
                    System.out.println(datos[i]);
                    i++;
                }
                fechaAlta = new java.util.Date(datos[2] - 1900, datos[1] - 1, datos[0]);
                lm.setFechaAlta(fechaAlta);
            }

            lm.setNumero(Integer.parseInt(linea));
            lineamovilFacade.edit(lm);
            terminalSeleccinado.setLineaidlineaMovil(lm);
        } else if (linea.length() > 0 && lm == null) {
            int idLinea;
            idLinea = lineamovilFacade.maxID() + 1;
            lm = new Lineamovil(idLinea);

            //PASEAMOS LA FECHA
            if (fecha.length() > 0) {
                StringTokenizer tokens = new StringTokenizer(fecha, "/");
                int[] datos = new int[3];
                int i = 0;
                while (tokens.hasMoreTokens()) {
                    String str = tokens.nextToken();
                    datos[i] = Integer.parseInt(str);
                    System.out.println(datos[i]);
                    i++;
                }
                fechaAlta = new java.util.Date(datos[2] - 1900, datos[1] - 1, datos[0]);
                lm.setFechaAlta(fechaAlta);
            }

            lm.setNumero(Integer.parseInt(linea));
            lineamovilFacade.create(lm);
            terminalSeleccinado.setLineaidlineaMovil(lm);
        } else {
            this.inicializacion();
            return "FormularioInsertarMovilErrorLinea";
        }

        //MODIFICAMOS EN LA BD
        terminalmovilFacade.edit(terminalSeleccinado);

        this.inicializacion();
        return "ListadoTerminalMovil";
    }

    public void volver() throws IOException {

        FacesContext.getCurrentInstance().getExternalContext().redirect("../../index-logued.jsf");
    }

    public String formularioInsertar() {

        admin = esAdministrador();

        if (admin) {
            this.inicializacion();
            return "jsf/gestion_terminales/FormularioInsertarMovil";
        } else {

            return "ErrorAutorizacion";
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

    public String pagListadoTerminalMovil() {

        admin = esAdministrador();

        if (admin) {
            this.inicializacion();
            return "jsf/gestion_terminales/ListadoTerminalMovil.jsf";
        } else {

            return "ErrorAutorizacion.jsf";
        }
    }

    public String formularioInsertar2() {

        admin = esAdministrador();

        if (admin) {
            this.inicializacion();
            return "FormularioInsertarMovil";
        }

        return null;
    }

    public String pagListadoUsuarios() {

        admin = esAdministrador();

        if (admin) {
            this.inicializacion();

            return "jsf/gestion_terminales/ListadoUsuariosMovil.jsf";
        } else {

            return "ErrorAutorizacion.jsf";
        }
    }

    public String terminalesLibresMovil() {

        terminalesLibres = new ArrayList<Terminalmovil>();
        terminalesLibres = terminalmovilFacade.terminaleslibres();
        return "TerminalesLibresMovil";
    }

    public void asignarTerminal() {

        Terminalmovil tm = terminalmovilFacade.find(terminalSeleccinado.getIdterminalMovil());
        Usuario user = usuarioFacade.find(usuarioSeleccionado.getIdusuario());
        Lineamovil lm = lineamovilFacade.find(tm.getLineaidlineaMovil().getIdlineaMovil());
        lm.setUsuarioIdusuario(usuarioSeleccionado);

        lineamovilFacade.edit(lm);
        this.terminalesLibresMovil();
    }

    //FUNCION AUXILIAR PARA SABER SI EL STRING ES UN NUMERO
    private boolean esNumero(String numero) {
        boolean res = true;
        try {
            Integer.parseInt(numero);
        } catch (Exception e) {
            res = false;
        }
        return res;
    }
}
