/**
 *
 * @author Erdinç Ocak 
 */

package HibClasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "kullanicilar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kullanicilar.findAll", query = "SELECT k FROM Kullanicilar k"),
    @NamedQuery(name = "Kullanicilar.findById", query = "SELECT k FROM Kullanicilar k WHERE k.id = :id"),
    @NamedQuery(name = "Kullanicilar.findByAd", query = "SELECT k FROM Kullanicilar k WHERE k.ad = :ad"),
    @NamedQuery(name = "Kullanicilar.findBySifre", query = "SELECT k FROM Kullanicilar k WHERE k.sifre = :sifre")})
public class Kullanicilar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "ad")
    private String ad;
    @Column(name = "sifre")
    private String sifre;

    public Kullanicilar() {
    }

    public Kullanicilar(Integer id) {
        this.id = id;
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

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
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
        if (!(object instanceof Kullanicilar)) {
            return false;
        }
        Kullanicilar other = (Kullanicilar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HibClasses.Kullanicilar[ id=" + id + " ]";
    }

}
