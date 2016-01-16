/**
 *
 * @author Erdinç Ocak
 */
package cari;

import HibClasses.HibernateUtil;
import HibClasses.Kategoriler;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class KategorilerEkrani extends JPanel {

    GridBagConstraints gbc = new GridBagConstraints();

    JPanel pnlKategori = new JPanel();
    JPanel pnlKarar = new JPanel();
    JPanel pnlEkle = new JPanel();
    JPanel pnlDuzenle = new JPanel();

    DefaultTableModel dtmKategori = new DefaultTableModel(new String[]{"KATEGORİ", "AÇIKLAMA"}, 0);
    JTable tbKategori = new JTable(dtmKategori);
    JScrollPane scrPaneKategori = new JScrollPane(tbKategori);

    JButton btnDuzenle = new JButton("DÜZENLE");
    JButton btnDuzenle2 = new JButton("DÜZENLE");
    JButton btnSil = new JButton("SİL");
    JButton btnKaydet = new JButton("KAYDET");

    JLabel lblKategori = new JLabel("Kategori Adı: ");
    JLabel lblKategori2 = new JLabel("Kategori Adı: ");
    JLabel lblAciklama = new JLabel("Açıklama: ");
    JLabel lblAciklama2 = new JLabel("Açıklama: ");

    JTextField txtKategori = new JTextField(20);
    JTextField txtKategori2 = new JTextField(20);
    JTextArea taAciklama = new JTextArea(2, 20);
    JTextArea taAciklama2 = new JTextArea(2, 20);

    List<Kategoriler> gelenKategoriler = null;

    KategorilerEkrani() {

        setLayout(new GridBagLayout());
        scrPaneKategori.setPreferredSize(new Dimension(350, 200));

        pnlKarar.setLayout(new GridBagLayout());
        pnlKarar.setBorder(new TitledBorder("Seçili Satır İşlemleri"));
        Border border = pnlKarar.getBorder();
        Border margin = new EmptyBorder(5, 5, 5, 5);
        pnlKarar.setBorder(new CompoundBorder(border, margin));
        pnlKarar.setPreferredSize(new Dimension(350, 150));

        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlKarar.add(btnDuzenle, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        pnlKarar.add(btnSil, gbc);

        pnlKategori.setBorder(new TitledBorder("Kategori Listesi"));
        border = pnlKategori.getBorder();
        margin = new EmptyBorder(10, 10, 10, 10);
        pnlKategori.setBorder(new CompoundBorder(border, margin));

        pnlKategori.add(scrPaneKategori);
        pnlKategori.add(pnlKarar);
        pnlKategori.setPreferredSize(new Dimension(400, 600));

        pnlEkle.setLayout(new GridBagLayout());
        pnlEkle.setBorder(new TitledBorder("Yeni Kategori Ekle"));
        border = pnlEkle.getBorder();
        margin = new EmptyBorder(10, 10, 10, 10);
        pnlEkle.setBorder(new CompoundBorder(border, margin));
        pnlEkle.setPreferredSize(new Dimension(400, 300));

        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlEkle.add(lblKategori, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        pnlEkle.add(txtKategori, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlEkle.add(lblAciklama, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        pnlEkle.add(taAciklama, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        pnlEkle.add(btnKaydet, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        add(pnlKategori, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        add(pnlEkle, gbc);

        pnlDuzenle.setLayout(new GridBagLayout());
        pnlDuzenle.setBorder(new TitledBorder("Kategori Düzenle"));
        border = pnlDuzenle.getBorder();
        margin = new EmptyBorder(10, 10, 10, 10);
        pnlDuzenle.setBorder(new CompoundBorder(border, margin));
        pnlDuzenle.setPreferredSize(new Dimension(400, 300));

        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlDuzenle.add(lblKategori2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        pnlDuzenle.add(txtKategori2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlDuzenle.add(lblAciklama2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        pnlDuzenle.add(taAciklama2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        pnlDuzenle.add(btnDuzenle2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(pnlDuzenle, gbc);

        //Fonksiyonlar
        aktifPasif(pnlDuzenle, false);
        kategorilerTablosunuDoldur();

        btnDuzenle.addActionListener(new ActionListener() {
            //Düzenleme panelini etkinleştirir
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (tbKategori.getSelectedRow() < 0) {
                    JOptionPane.showMessageDialog(KategorilerEkrani.this, "Düzenlenecek kategori seçmediniz.", "Kategori Düzenleme İşlemi", JOptionPane.WARNING_MESSAGE);
                } else {
                    aktifPasif(pnlDuzenle, true);
                    int secilenSatir = tbKategori.getSelectedRow();
                    txtKategori2.setText(dtmKategori.getValueAt(secilenSatir, 0).toString());
                    taAciklama2.setText(dtmKategori.getValueAt(secilenSatir, 1).toString());
                    
                }
            }
        });

        btnDuzenle2.addActionListener(new ActionListener() {
            //Seçilen kategoriyi düzenler
            @Override
            public void actionPerformed(ActionEvent ae) {
                

                if (!alanlariKontrolEt(pnlDuzenle)) {
                    JOptionPane.showMessageDialog(KategorilerEkrani.this, "Bazı alanları doldurmadınız!", "Kategori Düzenleme İşlemi", JOptionPane.ERROR_MESSAGE);
                } else {
                    int a = JOptionPane.showConfirmDialog(KategorilerEkrani.this, "Bu kategorinin bilgilerini değiştirmek istediğinize emin misiniz?", "Kategori Düzenleme İşlemi", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (a == JOptionPane.YES_OPTION) {

                        Kategoriler duzenlenenKategori = gelenKategoriler.get(tbKategori.getSelectedRow());
                        duzenlenenKategori.setAd(txtKategori2.getText().trim());
                        duzenlenenKategori.setAciklama(taAciklama2.getText());

                        Session session = null;
                        Transaction tx = null;

                        try {
                            session = HibernateUtil.getSessionFactory().openSession();
                            tx = session.beginTransaction();

                            session.saveOrUpdate(duzenlenenKategori);

                            tx.commit();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(KategorilerEkrani.this, "Kategori düzenleme sırasında hata oluştu.", "Kategori Düzenleme İşlemi", JOptionPane.ERROR_MESSAGE);
                            tx.rollback();

                        } finally {
                            if (!tx.getStatus().equals(TransactionStatus.ROLLED_BACK)) {
                                JOptionPane.showMessageDialog(KategorilerEkrani.this, "Kategori düzenlendi", "Kategori Düzenleme İşlemi", JOptionPane.INFORMATION_MESSAGE);
                                int secilenSatir = tbKategori.getSelectedRow();
                                dtmKategori.setValueAt(duzenlenenKategori.getAd(), secilenSatir, 0);
                                dtmKategori.setValueAt(duzenlenenKategori.getAciklama(), secilenSatir, 1);
                                dtmKategori.fireTableDataChanged();
                                
                                panelTemizle(pnlDuzenle);
                                aktifPasif(pnlDuzenle, false);

                                //Yapılan düzenlemeden dolayı etkilenen diğer alanlar yenilenir
                                UrunlerEkrani ue = (UrunlerEkrani) CariEkrani.jtpCari.getComponent(1);
                                ue.urunlerTablosunuDoldur();
                                SatisYonetimiEkrani sye = (SatisYonetimiEkrani) CariEkrani.jtpCari.getComponent(4);
                                sye.kategorileriGetir();

                            }

                        }

                    }
                }

            }
        });

        btnSil.addActionListener(new ActionListener() {
            //Seçilen kategoriyi siler
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (tbKategori.getSelectedRow() >= 0) {

                    int a = JOptionPane.showConfirmDialog(KategorilerEkrani.this, new JLabel(
                            "<html>Kategoriyi silmek istediğinize emin misiniz?<br><font color='red'>Bu kategorideki ürünler de silinecektir.</font> </html>"),
                            "Kategori Silme İşlemi", JOptionPane.YES_NO_CANCEL_OPTION);

                    if (a == JOptionPane.YES_OPTION) {

                        Session session = null;
                        Transaction tx = null;

                        try {
                            session = HibernateUtil.getSessionFactory().openSession();
                            tx = session.beginTransaction();
                            Kategoriler silinecekKategori = (Kategoriler) session.load(Kategoriler.class, gelenKategoriler.get(tbKategori.getSelectedRow()).getId());
                            session.delete(silinecekKategori);
                            
                            tx.commit();
                        } catch (Exception e) {
                            System.out.println("Silme sırasında hata oluştu: " + e);
                            tx.rollback();
                        } finally {
                            session.close();
                            if (!tx.getStatus().equals(TransactionStatus.ROLLED_BACK)) {

                                dtmKategori.removeRow(tbKategori.getSelectedRow());
                                dtmKategori.fireTableDataChanged();
                                JOptionPane.showMessageDialog(KategorilerEkrani.this, "Kategori silindi!", "Kategori Silme İşlemi", JOptionPane.INFORMATION_MESSAGE);
                                
                                //Yapılan düzenlemeden dolayı etkilenen diğer alanlar yenilenir
                                UrunlerEkrani ue = (UrunlerEkrani) CariEkrani.jtpCari.getComponent(1);
                                ue.urunlerTablosunuDoldur();
                                SatisYonetimiEkrani sye = (SatisYonetimiEkrani) CariEkrani.jtpCari.getComponent(4);
                                sye.kategorileriGetir();

                            }
                        }

                    }

                } else {
                    JOptionPane.showMessageDialog(KategorilerEkrani.this, "Kategori seçimi yapmadınız!", "Kategori Silme İşlemi", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        btnKaydet.addActionListener(new ActionListener() {
            //Yeni kategori ekler
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!alanlariKontrolEt(pnlEkle)) {
                    JOptionPane.showMessageDialog(KategorilerEkrani.this, "Bazı alanları doldurmadınız!", "Kategori Ekleme İşlemi", JOptionPane.ERROR_MESSAGE);
                } else {

                    Kategoriler eklenenKategori = new Kategoriler();
                    eklenenKategori.setAd(txtKategori.getText().trim());
                    eklenenKategori.setAciklama(taAciklama.getText());

                    Session session = null;
                    Transaction tx = null;

                    try {
                        session = HibernateUtil.getSessionFactory().openSession();
                        tx = session.beginTransaction();

                        session.save(eklenenKategori);

                        tx.commit();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(KategorilerEkrani.this, "Kategori ekleme sırasında hata oluştu.", "Kategori Ekleme İşlemi", JOptionPane.ERROR_MESSAGE);
                        tx.rollback();

                    } finally {
                        if (!tx.getStatus().equals(TransactionStatus.ROLLED_BACK)) {
                            JOptionPane.showMessageDialog(KategorilerEkrani.this, "Kategori eklendi", "Kategori Ekleme İşlemi", JOptionPane.INFORMATION_MESSAGE);
                            dtmKategori.addRow(new String[]{eklenenKategori.getAd(), eklenenKategori.getAciklama()});
                            kategorilerTablosunuDoldur();

                            //Yapılan düzenlemeden dolayı etkilenen diğer alanlar yenilenir
                            UrunlerEkrani ue = (UrunlerEkrani) CariEkrani.jtpCari.getComponent(1);
                            ue.urunlerTablosunuDoldur();
                            ue.kategorileriGetir();
                            SatisYonetimiEkrani sye = (SatisYonetimiEkrani) CariEkrani.jtpCari.getComponent(4);
                            sye.kategorileriGetir();
                        }

                    }

                }
            }
        });

        
        txtKategori.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {

            }

            @Override
            public void keyReleased(KeyEvent ke) {
                txtKategori.setBackground(Color.WHITE);

            }
        });

        txtKategori2.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {

            }

            @Override
            public void keyReleased(KeyEvent ke) {
                txtKategori2.setBackground(Color.WHITE);

            }
        });

    }

    //Textfield üzerinde işlem yapıldıktan sonra textfield alanlarını temizler.
    public void panelTemizle(JPanel panel) {

        Component[] icerdekiElementler = panel.getComponents();

        for (int i = 0; i < icerdekiElementler.length; i++) {

            if (icerdekiElementler[i] instanceof JTextField) {

                JTextField item2 = (JTextField) icerdekiElementler[i];
                item2.setText("");

            }

            if (icerdekiElementler[i] instanceof JTextArea) {

                JTextArea item2 = (JTextArea) icerdekiElementler[i];
                item2.setText("");

            }

        }

    }

    //Girilmesi zorunlu alanları kontrol eder
    private boolean alanlariKontrolEt(JPanel panel) {

        boolean isOk = true;

        Component[] icerdekiElementler = panel.getComponents();

        for (int i = 0; i < icerdekiElementler.length; i++) {

            if (icerdekiElementler[i] instanceof JTextField) {

                JTextField item2 = (JTextField) icerdekiElementler[i];
                if (item2.getText().trim().equals("")) {
                    item2.setBackground(Color.RED);
                    isOk = false;
                }

            }

        }

        return isOk;

    }

    //Açılışta kategorileri veritabanından getirir
    private void kategorilerTablosunuDoldur() {

        dtmKategori.setRowCount(0);

        Session session = HibernateUtil.getSessionFactory().openSession();

        gelenKategoriler = session.createCriteria(Kategoriler.class).list();

        for (int i = 0; i < gelenKategoriler.size(); i++) {

            Kategoriler kategori = (Kategoriler) (gelenKategoriler.get(i));

            dtmKategori.addRow(new String[]{kategori.getAd(), kategori.getAciklama()});

        }

        tbKategori.setModel(dtmKategori);

    }

    //Kullanım kolaylığı açısından istenilen paneli aktif-pasif olarak değiştirir. 
    private void aktifPasif(JPanel pnl, boolean aktif) {

        if (aktif) {

            Component[] icerdekiElementler = pnl.getComponents();

            for (Component item : icerdekiElementler) {

                if (item instanceof JTextField || item instanceof JButton || item instanceof JComboBox || item instanceof JTextArea) {

                    item.setEnabled(true);

                }

            }

        } else {
            Component[] icerdekiElementler = pnl.getComponents();

            for (Component item : icerdekiElementler) {

                if (item instanceof JTextField || item instanceof JButton || item instanceof JComboBox || item instanceof JTextArea) {

                    item.setEnabled(false);

                }

            }
        }

    }

}
