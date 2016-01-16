/**
 *
 * @author Erdinç Ocak
 */
package cari;

import HibClasses.HibernateUtil;
import HibClasses.Kategoriler;
import HibClasses.Musteriler;
import HibClasses.Satislar;
import HibClasses.Urunler;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class SatisYonetimiEkrani extends JPanel {

    GridBagConstraints gbc = new GridBagConstraints();

    JComboBox<String> cmbKategoriler = new JComboBox();
    JComboBox<String> cmbMusteriler = new JComboBox();

    JButton btnListele = new JButton("LİSTELE");
    JButton btnSatisTamamla = new JButton("SATIŞI TAMAMLA");

    JLabel lblKategori = new JLabel("Listelenecek ürün kategorisi seçiniz.");
    JLabel lblUrun = new JLabel("Satış yapmak için listeden seçim yapınız.");
    JLabel lblSecilenUrun = new JLabel("Seçilen Ürün: ");
    JLabel lblAdet = new JLabel("Adet: ");
    JLabel lblMusteri = new JLabel("Müşteri: ");
    JLabel lblMusteriEkleme = new JLabel("Müşteriler bölümünden yeni müşteri ekleyebilirsiniz.");

    JTextField txtUrun = new JTextField(20);
    JTextField txtAdet = new JTextField(20);

    DefaultTableModel dtmUrunler = new DefaultTableModel(new String[]{"ADI", "SATIŞ FİYATI(TL)", "AÇIKLAMA"}, 0);
    JTable tblUrunler = new JTable(dtmUrunler);
    JScrollPane scrPaneUrunler = new JScrollPane(tblUrunler);

    List<Kategoriler> gelenKategoriler = null;
    List<Urunler> gelenUrunler = null;
    List<Musteriler> gelenMusteriler = null;

    SatisYonetimiEkrani() {

        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        scrPaneUrunler.setPreferredSize(new Dimension(700, 200));

        gbc.insets = new Insets(0, 0, 10, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(cmbKategoriler, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblKategori, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        add(btnListele, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 5;
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        add(scrPaneUrunler, gbc);

        gbc.insets = new Insets(5, 0, 20, 0);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 3;
        gbc.gridy = 6;
        add(lblUrun, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(lblSecilenUrun, gbc);

        gbc.gridx = 2;
        gbc.gridy = 10;
        add(txtUrun, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        add(lblAdet, gbc);

        gbc.gridx = 2;
        gbc.gridy = 11;
        add(txtAdet, gbc);

        gbc.gridx = 0;
        gbc.gridy = 12;
        add(lblMusteri, gbc);

        gbc.gridx = 2;
        gbc.gridy = 12;
        add(cmbMusteriler, gbc);

        gbc.gridx = 3;
        gbc.gridy = 10;
        gbc.insets = new Insets(0, 45, 0, 0);
        add(btnSatisTamamla, gbc);

        gbc.gridy = 14;
        gbc.insets = new Insets(0, -280, 0, 0);
        add(lblMusteriEkleme, gbc);

        //Fonksiyonlar
        kategorileriGetir();
        musterileriGetir();

        btnListele.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Session session = null;

                try {

                    session = HibernateUtil.getSessionFactory().openSession();
                    session.beginTransaction();

                    gelenUrunler = session.createCriteria(Urunler.class)
                            .add(Restrictions.eq("katid", gelenKategoriler.get(cmbKategoriler.getSelectedIndex()).getId())
                            ).list();

                    dtmUrunler.setRowCount(0);
                    for (Urunler o : gelenUrunler) {

                        dtmUrunler.addRow(new String[]{o.getAd(), o.getSatis().toString(), o.getAciklama()});

                    }

                } catch (Exception e) {

                    System.out.println("Ürünleri getirirken hata oluştu" + e);

                } finally {
                    session.close();
                }
            }
        });

        tblUrunler.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {

                if (lse.getValueIsAdjusting()) {

                    Urunler tekUrun = gelenUrunler.get(tblUrunler.getSelectedRow());
                    String ad = tekUrun.getAd();
                    txtUrun.setText(ad);

                }
            }
        });

        btnSatisTamamla.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (cmbMusteriler.getSelectedIndex() < 0) {
                    JOptionPane.showMessageDialog(SatisYonetimiEkrani.this, "Müşteri seçmediniz.", "Satış İşlemi", JOptionPane.WARNING_MESSAGE);
                } else if (tblUrunler.getSelectedRow() < 0) {
                    JOptionPane.showMessageDialog(SatisYonetimiEkrani.this, "Ürün seçmediniz", "Satış İşlemi", JOptionPane.WARNING_MESSAGE);
                } else if (txtAdet.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(SatisYonetimiEkrani.this, "Ürün adeti girmediniz", "Satış İşlemi", JOptionPane.WARNING_MESSAGE);
                } else {
                    Urunler tekUrun = gelenUrunler.get(tblUrunler.getSelectedRow());
                    int stok = tekUrun.getStok();

                    if (stok < Integer.valueOf(txtAdet.getText().trim())) {
                        JOptionPane.showMessageDialog(SatisYonetimiEkrani.this, "Elinizde bu satışı yapacak kadar ürün bulunmuyor.\nElinizdeki ürün sayısı: " + stok,
                                "Satış İşlemi", JOptionPane.ERROR_MESSAGE);
                        txtAdet.setBackground(Color.RED);
                    } else {

                        Session session = null;
                        Transaction tx = null;

                        Urunler urun = gelenUrunler.get(tblUrunler.getSelectedRow());
                        Musteriler musteri = gelenMusteriler.get(cmbMusteriler.getSelectedIndex());
                        Satislar satis = new Satislar();
                        satis.setAdet(Integer.valueOf(txtAdet.getText()));
                        satis.setMusteriadi(cmbMusteriler.getSelectedItem().toString());
                        satis.setTarih(new Date());
                        satis.setUrunadi(gelenUrunler.get(tblUrunler.getSelectedRow()).getAd());
                        satis.setUrunfiyat(gelenUrunler.get(tblUrunler.getSelectedRow()).getSatis());
                        satis.setUrunkategori(gelenUrunler.get(tblUrunler.getSelectedRow()).getKategori().getAd());
                        satis.setUrunalis(gelenUrunler.get(tblUrunler.getSelectedRow()).getAlis());
                        satis.setTarih(new Date());

                        try {

                            session = HibernateUtil.getSessionFactory().openSession();
                            tx = (Transaction) session.beginTransaction();

                            session.save(satis);

                            urun.setStok(urun.getStok() - satis.getAdet());

                            if (urun.getStok() == 0) {
                                session.delete(urun);
                            } else {
                                session.saveOrUpdate(urun);
                            }

                            tx.commit();

                        } catch (Exception e) {
                            System.out.println("Satış sırasında hata oluştu." + e);
                            tx.rollback();
                        } finally {
                            session.close();
                            if (!tx.getStatus().equals(TransactionStatus.ROLLED_BACK)) {
                                JOptionPane.showMessageDialog(SatisYonetimiEkrani.this, satis.getMusteriadi()
                                        + " isimli müşteriye " + satis.getAdet() + " adet " + satis.getUrunadi() + " satışı yapıldı", "Satış İşlemi", JOptionPane.INFORMATION_MESSAGE);
                            }

                            UrunlerEkrani ue = (UrunlerEkrani) CariEkrani.jtpCari.getComponent(1);
                            ue.urunlerTablosunuDoldur();
                            RaporlarEkrani re = (RaporlarEkrani) CariEkrani.jtpCari.getComponent(3);
                            re.raporlarTablosunuDoldur();

                        }

                    }
                }
            }
        });

        txtAdet.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {

            }

            @Override
            public void keyReleased(KeyEvent ke) {
                txtAdet.setBackground(Color.WHITE);

                if (Character.isLetter(ke.getKeyChar())) {
                    String formattedTex = txtAdet.getText().trim().replace(ke.getKeyChar(), '\0');
                    txtAdet.setText(formattedTex.trim());
                }
            }
        });

    }

    public void kategorileriGetir() {

        Session session = null;
        cmbKategoriler.removeAllItems();
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            gelenKategoriler = session.createCriteria(Kategoriler.class).list();

            for (Kategoriler i : gelenKategoriler) {
                cmbKategoriler.addItem(i.getAd());
            }

            session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Kategorileri getirme sırasında hata oluştu." + e);
        } finally {
            session.close();
            cmbKategoriler.setSelectedIndex(-1);
        }

    }

    public void musterileriGetir() {

        Session session = null;
        cmbMusteriler.removeAllItems();
        try {

            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            gelenMusteriler = session.createCriteria(Musteriler.class).list();

            for (Musteriler m : gelenMusteriler) {
                cmbMusteriler.addItem(m.getAd());
            }

        } catch (Exception e) {
            System.out.println("Müşterileri getirme sırasında hata oluştu" + e);
        } finally {
            session.close();
            cmbMusteriler.setSelectedIndex(-1);
        }

    }

}
