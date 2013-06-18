/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.gestion_terminales;

import diputacion.dao.LineamovilFacadeLocal;
import diputacion.dao.TerminalmovilFacadeLocal;
import diputacion.entity.Lineamovil;
import diputacion.entity.Terminalmovil;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.StringTokenizer;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
    
    //VARIABLES
    private String marca, modelo, sistemaOperativo, fecha, linea;
    private Date fechaAlta;
    private Collection<Terminalmovil> terminales;
    private Terminalmovil terminalSeleccinado;

    //CONSTRUCTOR
    public CtrGestionTerminalesMovil() {
    }

    //GETTER AND SETTER
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

    public Terminalmovil getTerminalSeleccionado() {
        return terminalSeleccinado;
    }

    public void setTerminalSeleccionado(Terminalmovil t) {
        terminalSeleccinado = t;
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
        sistemaOperativo="";

    }

    public String insertarMovil() {
        //CREAMOS EL OBJETO TERMINAL MOVIL
        Terminalmovil tmnuevo;
        Lineamovil lm;
        int id;

        id= terminalmovilFacade.maxID()+1;
        tmnuevo = new Terminalmovil(id);
        tmnuevo.setMarca(marca);
        tmnuevo.setModelo(modelo);
        tmnuevo.setSistemaOperativo(sistemaOperativo);


        //SI LA LINEA NO ES VACIA INSERTAMOS UNA NUEVA LINEA EN EL SISTEMA
        if (linea.length() > 0) {
            int idLinea;
            idLinea = lineamovilFacade.maxID() +1;
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
        return "FormularioModificaMovil";
    }

    public void borrarTerminalMovil() {
        terminalmovilFacade.remove(terminalSeleccinado);
        this.inicializacion();
    }

    public String modificarTerminalMovilDatos() {
        //CREAMOS EL OBJETO TERMINAL FIJO
        Lineamovil lm;

        terminalSeleccinado.setMarca(marca);
        terminalSeleccinado.setModelo(modelo);
        terminalSeleccinado.setSistemaOperativo(sistemaOperativo);
        //SI LA LINEA NO ES VACIA INSERTAMOS UNA NUEVA LINEA EN EL SISTEMA
        if (linea.length() > 0) {
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
        }

        //MODIFICAMOS EN LA BD
        terminalmovilFacade.edit(terminalSeleccinado);

        this.inicializacion();
        return "ListadoTerminalMovil";
    }

    public String volver() {

        return "ListadoTerminalMovil";
    }

    public String formularioInsertar() {
        this.inicializacion();
        return "FormularioInsertarMovil";
    }
}
