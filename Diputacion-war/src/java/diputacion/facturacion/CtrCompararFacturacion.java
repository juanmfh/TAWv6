/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.facturacion;


import diputacion.dao.ComparativaFacadeLocal;
import diputacion.dao.FacturainFacadeLocal;
import diputacion.dao.FacturaoutFacadeLocal;
import diputacion.entity.Comparativa;
import diputacion.entity.Facturain;
import diputacion.entity.Facturaout;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author olivo
 */
@ManagedBean
@SessionScoped
public class CtrCompararFacturacion implements Serializable {
    
    @EJB
    private ComparativaFacadeLocal comparativaFacade;
    @EJB
    private FacturaoutFacadeLocal facturaOutFacade;
    @EJB
    private FacturainFacadeLocal facturaInFacade;
    Collection<Facturain> facturasIn;
    Collection<Facturaout> facturasOut;
    private Integer factInSelec;
    private Integer factOutSelec;
    private String resultado;
    private String comentario;
    
    public CtrCompararFacturacion() {
    }
    
    public Integer getFactInSelec() {
        return factInSelec;
    }
    
    public void setFactInSelec(Integer factInSelec) {
        this.factInSelec = factInSelec;
    }
    
    public Integer getFactOutSelec() {
        return factOutSelec;
    }
    
    public void setFactOutSelec(Integer factOutSelec) {
        this.factOutSelec = factOutSelec;
    }
    
    public Collection<Facturain> getFacturasIn() {
        return facturasIn;
    }
    
    public void setFacturasIn(Collection<Facturain> facturasIn) {
        this.facturasIn = facturasIn;
    }
    
    public Collection<Facturaout> getFacturasOut() {
        return facturasOut;
    }
    
    public void setFacturasOut(Collection<Facturaout> facturasOut) {
        this.facturasOut = facturasOut;
    }
    
    public String getResultado() {
        Random r = new Random();
        String aux1 = "Diferencia en linea " + r.nextInt(6);
        String aux2 = "No existen diferencias";
        
        if (r.nextBoolean()) {
            resultado = aux1;
        } else {
            resultado = aux2;
        }
        
        return resultado;
    }
    
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    
    public String getComentario() {
        return comentario;
    }
    
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public String comparar() {
        
        return "VistaResultadoComparacion";
    }
    
    public String guardarComparacion() throws ParseException {
        
        Calendar calendario = Calendar.getInstance();
        
        
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(calendario.get(Calendar.YEAR) + "-" + calendario.get(Calendar.MONTH) + "-" + calendario.get(Calendar.DAY_OF_MONTH) + " 00:00:00"); // mysql datetime format
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        
        Comparativa c = new Comparativa();
        c.setComentario(comentario);
        c.setDiferencias(resultado);
        c.setFechaCreacion(calendar.getTime());
        /*if (comparativaFacade.maxComparativa() != null) {
            c.setIdcomparativa((Integer) comparativaFacade.maxComparativa() + 1);
        } else {
            c.setIdcomparativa(1);
        }*/
        
        c.setFacturaInidfacturaIn(facturaInFacade.find(this.factInSelec));
        c.setFacturaOutidfacturaOut(facturaOutFacade.find(factOutSelec));
        
        comparativaFacade.create(c);
        
        return "VistaGestionComparativas";
    }
    
    @PostConstruct
    public void create() {
        facturasIn = facturaInFacade.findAll();
        facturasOut = facturaOutFacade.findAll();
    }
}
