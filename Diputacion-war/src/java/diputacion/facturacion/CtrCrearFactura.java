/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.facturacion;


import diputacion.dao.FacturainFacadeLocal;
import diputacion.dao.LineamovilFacadeLocal;
import diputacion.entity.Facturain;
import diputacion.entity.Lineamovil;
import java.io.Serializable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author David Dona Corrales
 */
@ManagedBean
public class CtrCrearFactura implements Serializable {

    @EJB
    private FacturainFacadeLocal facturaInFacade;
    @EJB
    private LineamovilFacadeLocal lineaMovilFacade;
    private Collection<Lineamovil> lineasMovil;
    private Collection<String> fechas;
    private Integer numSeleccionado;
    private String fechaSeleccionado;

    public CtrCrearFactura() {
    }

    public Collection<String> calendario() {

        fechas = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        c.get(Calendar.MONTH);
        c.get(Calendar.YEAR);

        for (int i = 2005; i <= c.get(Calendar.YEAR); i++) {
            if (i != c.get(Calendar.YEAR)) {
                for (int j = 1; j < 13; j++) {
                    fechas.add(i + "-" + j + "-" + "01");
                }
            } else {
                for (int j = 1; j <= c.get(Calendar.MONTH); j++) {
                    fechas.add(i + "-" + j + "-" + "01");
                }

            }

        }

        return fechas;
    }

    public Collection<Lineamovil> getLineasMovil() {
        return lineasMovil;
    }

    public void setLineasMovil(Collection<Lineamovil> lineasMovil) {
        this.lineasMovil = lineasMovil;
    }

    public Collection<String> getFechas() {
        fechas = calendario();
        return fechas;
    }

    public void setFechas(Collection<String> fechas) {
        this.fechas = fechas;
    }

    public Integer getNumSeleccionado() {
        return numSeleccionado;
    }

    public void setNumSeleccionado(Integer numSeleccionado) {
        this.numSeleccionado = numSeleccionado;
    }

    public String getFechaSeleccionado() {
        return fechaSeleccionado;
    }

    public void setFechaSeleccionado(String fechaSeleccionado) {
        this.fechaSeleccionado = fechaSeleccionado;
    }

    public String crearFactura() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(this.fechaSeleccionado + " 00:00:00"); // mysql datetime format
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int id = 1;
        if (facturaInFacade.maxFacturaIn() != null) {
             id = (Integer) facturaInFacade.maxFacturaIn() + 1;
        }

        Facturain f = new Facturain();
        f.setIdfacturaIn(id);
        f.setPeriodo(calendar.getTime());


        f.setLineaMovilidlineaMovil(lineaMovilFacade.find(this.numSeleccionado));

        facturaInFacade.create(f);
        return "VistaFacturacion";
    }

    @PostConstruct
    public void create() {

        lineasMovil = lineaMovilFacade.findAll();


    }
}
