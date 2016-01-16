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
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "urunler")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Urunler.findAll", query = "SELECT u FROM Urunler u"),
    @NamedQuery(name = "Urunler.findById", query = "SELECT u FROM Urunler u WHERE u.id = :id"),
    @NamedQuery(name = "Urunler.findByAd", query = "SELECT u FROM Urunler u WHERE u.ad = :ad"),

    @NamedQuery(name = "Urunler.findByAlis", query = "SELECT u FROM Urunler u WHERE u.alis = :alis"),
    @NamedQuery(name = "Urunler.findBySatis", query = "SELECT u FROM Urunler u WHERE u.satis = :satis"),
    @NamedQuery(name = "Urunler.findByStok", query = "SELECT u FROM Urunler u WHERE u.stok = :stok"),
    @NamedQuery(name = "Urunler.findByAciklama", query = "SELECT u FROM Urunler u WHERE u.aciklama = :aciklama"),
    @NamedQuery(name = "Urunler.findByKatid", query = "SELECT u FROM Urunler u WHERE u.katid = :katid")})
public class Urunler implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "katid")
    private Kategoriler kategori;

    public Kategoriler getKategori() {
        return kategori;
    }

    public void setKategori(Kategoriler kategori) {
        this.kategori = kategori;
    }

    @Basic(optional = false)
    @Column(name = "ad")
    private String ad;
    @Basic(optional = false)
    @Column(name = "alis")
    private Integer alis;
    @Basic(optional = false)
    @Column(name = "satis")
    private Integer satis;
    @Basic(optional = false)
    @Column(name = "stok")
    private Integer stok;
    @Column(name = "aciklama")
    private String aciklama;
    @Basic(optional = false)
    @Column(name = "katid", insertable = false , updatable = false)
    private Integer katid;

    public Integer getKatid() {
        return katid;
    }

    public void setKatid(Integer katid) {
        this.katid = katid;
    }

    public Urunler() {
    }

    public Urunler(Integer id) {
        this.id = id;
    }

    public Urunler(Integer id, String ad, String kategori, Integer alis, Integer satis, Integer stok, int katid) {
        this.id = id;
        this.ad = ad;

        this.alis = alis;
        this.satis = satis;
        this.stok = stok;

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

    public Integer getAlis() {
        return alis;
    }

    public void setAlis(Integer alis) {
        this.alis = alis;
    }

    public Integer getSatis() {
        return satis;
    }

    public void setSatis(Integer satis) {
        this.satis = satis;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
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
        if (!(object instanceof Urunler)) {
            return false;
        }
        Urunler other = (Urunler) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HibClasses.Urunler[ id=" + id + " ]";
    }

}
