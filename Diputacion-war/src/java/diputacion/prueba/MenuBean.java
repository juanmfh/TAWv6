package diputacion.prueba;

import java.io.IOException;
import javax.faces.application.FacesMessage;  
import javax.faces.context.FacesContext;  
import org.primefaces.component.menuitem.MenuItem;  
import org.primefaces.component.submenu.Submenu;  
import org.primefaces.model.DefaultMenuModel;  
import org.primefaces.model.MenuModel;  
  
public class MenuBean {  
  
    private MenuModel model;  
  
    public MenuBean() {
    }     
      
    public void save() throws IOException {  
         FacesContext.getCurrentInstance().getExternalContext().redirect("FormularioInsertarFijo.jsf");
    }  
}  