/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package diputacion.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JuanM
 */
@Entity
@Table(name = "gruporescate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gruporescate.findAll", query = "SELECT g FROM Gruporescate g"),
    @NamedQuery(name = "Gruporescate.findByIdgrupoRescate", query = "SELECT g FROM Gruporescate g WHERE g.idgrupoRescate = :idgrupoRescate"),
    @NamedQuery(name = "Gruporescate.findByLocalizacion", query = "SELECT g FROM Gruporescate g WHERE g.localizacion = :localizacion")})
public class Gruporescate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idgrupoRescate")
    private Integer idgrupoRescate;
    @Size(max = 20)
    @Column(name = "localizacion")
    private String localizacion;
    @OneToMany(mappedBy = "grupoRescateidgrupoRescate")
    private Collection<Usuario> usuarioCollection;

    public Gruporescate() {
    }

    public Gruporescate(Integer idgrupoRescate) {
        this.idgrupoRescate = idgrupoRescate;
    }

    public Integer getIdgrupoRescate() {
        return idgrupoRescate;
    }

    public void setIdgrupoRescate(Integer idgrupoRescate) {
        this.idgrupoRescate = idgrupoRescate;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idgrupoRescate != null ? idgrupoRescate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gruporescate)) {
            return false;
        }
        Gruporescate other = (Gruporescate) object;
        if ((this.idgrupoRescate == null && other.idgrupoRescate != null) || (this.idgrupoRescate != null && !this.idgrupoRescate.equals(other.idgrupoRescate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "diputacion.entity.Gruporescate[ idgrupoRescate=" + idgrupoRescate + " ]";
    }
    
}
