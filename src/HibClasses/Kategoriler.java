/**
 *
 * @author Erdinç Ocak
 */
package HibClasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "kategoriler")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kategoriler.findAll", query = "SELECT k FROM Kategoriler k"),
    @NamedQuery(name = "Kategoriler.findById", query = "SELECT k FROM Kategoriler k WHERE k.id = :id"),
    @NamedQuery(name = "Kategoriler.findByAd", query = "SELECT k FROM Kategoriler k WHERE k.ad = :ad"),
    @NamedQuery(name = "Kategoriler.findByAciklama", query = "SELECT k FROM Kategoriler k WHERE k.aciklama = :aciklama")})
public class Kategoriler implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "ad")
    private String ad;
    @Column(name = "aciklama")
    private String aciklama;

    public Kategoriler() {
    }

    public Kategoriler(Integer id) {
        this.id = id;
    }

    public Kategoriler(String ad) {
        this.ad = ad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kategoriler)) {
            return false;
        }
        Kategoriler other = (Kategoriler) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HibClasses.Kategoriler[ id=" + id + " ]";
    }

}
