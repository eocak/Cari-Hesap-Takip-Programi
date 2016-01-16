/**
 *
 * @author Erdinç Ocak
 */
package HibClasses;


import java.io.Serializable;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.EncodingType;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;




@Entity 
@Indexed
@Table(name = "satislar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Satislar.findAll", query = "SELECT s FROM Satislar s"),
    @NamedQuery(name = "Satislar.findById", query = "SELECT s FROM Satislar s WHERE s.id = :id"),
    @NamedQuery(name = "Satislar.findByAdet", query = "SELECT s FROM Satislar s WHERE s.adet = :adet"),
    @NamedQuery(name = "Satislar.findByTarih", query = "SELECT s FROM Satislar s WHERE s.tarih = :tarih"),
    @NamedQuery(name = "Satislar.findByUrunadi", query = "SELECT s FROM Satislar s WHERE s.urunadi = :urunadi"),
    @NamedQuery(name = "Satislar.findByMusteriadi", query = "SELECT s FROM Satislar s WHERE s.musteriadi = :musteriadi"),
    @NamedQuery(name = "Satislar.findByUrunkategori", query = "SELECT s FROM Satislar s WHERE s.urunkategori = :urunkategori"),
    @NamedQuery(name = "Satislar.findByUrunfiyat", query = "SELECT s FROM Satislar s WHERE s.urunfiyat = :urunfiyat"),
    @NamedQuery(name = "Satislar.findByUrunalis", query = "SELECT s FROM Satislar s WHERE s.urunalis = :urunalis")})
public class Satislar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @DocumentId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "adet")
    private int adet;
    @Basic(optional = false)
    @Column(name = "tarih")
    @Temporal(TemporalType.TIMESTAMP)
    @DateBridge(resolution = Resolution.MILLISECOND, encoding=EncodingType.STRING)
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private Date tarih;
    @Basic(optional = false)
    @Column(name = "urunadi")
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String urunadi;
    @Basic(optional = false)
    @Column(name = "musteriadi")
    @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
    private String musteriadi;
    @Basic(optional = false)
    @Column(name = "urunkategori")
    @Field(index=Index.YES, analyze=Analyze.NO, store=Store.NO)
    private String urunkategori;
    @Basic(optional = false)
    @Column(name = "urunfiyat")
    private int urunfiyat;
    @Basic(optional = false)
    @Column(name = "urunalis")
    private int urunalis;

    public Satislar() {
    }

    public Satislar(Integer id) {
        this.id = id;
    }

    public Satislar(Integer id, int adet, Date tarih, String urunadi, String musteriadi, String urunkategori, int urunfiyat, int urunalis) {
        this.id = id;
        this.adet = adet;
        this.tarih = tarih;
        this.urunadi = urunadi;
        this.musteriadi = musteriadi;
        this.urunkategori = urunkategori;
        this.urunfiyat = urunfiyat;
        this.urunalis = urunalis;
    }

    
    public int getUrunalis() {
        return urunalis;
    }

    public void setUrunalis(int urunalis) {
        this.urunalis = urunalis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAdet() {
        return adet;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }

    public Date getTarih() {
        return tarih;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }

    public String getUrunadi() {
        return urunadi;
    }

    public void setUrunadi(String urunadi) {
        this.urunadi = urunadi;
    }

    public String getMusteriadi() {
        return musteriadi;
    }

    public void setMusteriadi(String musteriadi) {
        this.musteriadi = musteriadi;
    }

    public String getUrunkategori() {
        return urunkategori;
    }

    public void setUrunkategori(String urunkategori) {
        this.urunkategori = urunkategori;
    }

    public int getUrunfiyat() {
        return urunfiyat;
    }

    public void setUrunfiyat(int urunfiyat) {
        this.urunfiyat = urunfiyat;
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
        if (!(object instanceof Satislar)) {
            return false;
        }
        Satislar other = (Satislar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HibClasses.Satislar[ id=" + id + " ]";
    }

}
