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
@Table(name = "musteriler")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Musteriler.findAll", query = "SELECT m FROM Musteriler m"),
    @NamedQuery(name = "Musteriler.findById", query = "SELECT m FROM Musteriler m WHERE m.id = :id"),
    @NamedQuery(name = "Musteriler.findByAd", query = "SELECT m FROM Musteriler m WHERE m.ad = :ad"),
    @NamedQuery(name = "Musteriler.findBySoyad", query = "SELECT m FROM Musteriler m WHERE m.soyad = :soyad"),
    @NamedQuery(name = "Musteriler.findByTelefon", query = "SELECT m FROM Musteriler m WHERE m.telefon = :telefon"),
    @NamedQuery(name = "Musteriler.findByAdres", query = "SELECT m FROM Musteriler m WHERE m.adres = :adres")})
public class Musteriler implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    
    
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    
    @Basic(optional = false)
    @Column(name = "ad")
    private String ad;
    @Basic(optional = false)
    @Column(name = "soyad")
    private String soyad;
    @Column(name = "telefon")
    private String telefon;
    @Column(name = "adres")
    private String adres;

    public Musteriler() {
    }

    public Musteriler(Integer id) {
        this.id = id;
    }

    public Musteriler(Integer id, String ad, String soyad) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
    }

    
    

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
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
        if (!(object instanceof Musteriler)) {
            return false;
        }
        Musteriler other = (Musteriler) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HibClasses.Musteriler[ id=" + id + " ]";
    }

}
