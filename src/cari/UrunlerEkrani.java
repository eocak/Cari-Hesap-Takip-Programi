/**
 *
 * @author Erdinç Ocak
 */
package cari;

import HibClasses.HibernateUtil;
import HibClasses.Kategoriler;
import HibClasses.Urunler;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;


public class UrunlerEkrani extends JPanel {

    GridBagConstraints gbc = new GridBagConstraints();
    DefaultTableModel dtmUrunListe = new DefaultTableModel(new String[]{"ÜRÜN ADI", "KATEGORİ", "ALIŞ FİYATI(TL)", "SATIŞ FİYATI(TL)", "STOK", "AÇIKLAMA"}, 0);
    JTable tblUrunListe = new JTable(dtmUrunListe);
    JScrollPane scrPaneUrunListe = new JScrollPane(tblUrunListe);
    JPanel pnlKarar = new JPanel();
    JPanel pnlUrunListe = new JPanel();
    JPanel pnlEkle = new JPanel();
    JPanel pnlDuzenle = new JPanel();

    JLabel lblUrunAd = new JLabel("Ürün Adı: ");
    JLabel lblKategori = new JLabel("Kategori: ");
    JLabel lblGelisUcreti = new JLabel("Geliş Ücreti: ");
    JLabel lblSatisUcreti = new JLabel("Satış Ücreti: ");
    JLabel lblStok = new JLabel("Stok: ");
    JLabel lblAciklama = new JLabel("Açıklama: ");

    JTextField txtUrunAd = new JTextField(20);
    JTextField txtGelisUcreti = new JTextField(20);
    JTextField txtSaticUcreti = new JTextField(20);
    JTextField txtStok = new JTextField(20);

    JLabel lblUrunAd2 = new JLabel("Ürün Adı: ");
    JLabel lblKategori2 = new JLabel("Kategori: ");
    JLabel lblGelisUcreti2 = new JLabel("Geliş Ücreti: ");
    JLabel lblSatisUcreti2 = new JLabel("Satış Ücreti: ");
    JLabel lblStok2 = new JLabel("Stok: ");
    JLabel lblAciklama2 = new JLabel("Açıklama: ");

    JTextField txtUrunAd2 = new JTextField(20);
    JTextField txtGelisUcreti2 = new JTextField(20);
    JTextField txtSaticUcreti2 = new JTextField(20);
    JTextField txtStok2 = new JTextField(20);

    JButton btnEkle = new JButton("EKLE");
    JButton btnDuzenle = new JButton("DÜZENLE");
    JButton btnDuzenle2 = new JButton("DÜZENLE");
    JButton btnSil = new JButton("SİL");

    JComboBox<String> cbKategori = new JComboBox<>();
    JComboBox<String> cbKategori2 = new JComboBox<>();
    JTextArea taAciklama = new JTextArea(2, 20);
    JTextArea taAciklama2 = new JTextArea(2, 20);

    List<Urunler> gelenUrunler = null;
    List<Kategoriler> sunucudanGelen = null;

    UrunlerEkrani() {

        scrPaneUrunListe.setPreferredSize(new Dimension(700, 200));

        pnlKarar.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 0, 10, 0);
        pnlKarar.add(btnDuzenle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 50, 0);
        pnlKarar.add(btnSil, gbc);

        pnlKarar.setBorder(new TitledBorder("Seçili Satır İşlemleri"));
        Border border = pnlKarar.getBorder();
        Border margin = new EmptyBorder(5, 5, 5, 5);
        pnlKarar.setBorder(new CompoundBorder(border, margin));
        pnlKarar.setPreferredSize(new Dimension(100, 200));

        pnlUrunListe.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlUrunListe.add(scrPaneUrunListe);
        pnlUrunListe.add(pnlKarar);
        pnlUrunListe.setBorder(new TitledBorder("Ürün Listesi"));
        border = pnlUrunListe.getBorder();
        margin = new EmptyBorder(10, 10, 10, 10);
        pnlUrunListe.setBorder(new CompoundBorder(border, margin));

        pnlEkle.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.EAST;
        pnlEkle.add(lblUrunAd, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        pnlEkle.add(txtUrunAd, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlEkle.add(lblKategori, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        pnlEkle.add(cbKategori, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        pnlEkle.add(lblGelisUcreti, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        pnlEkle.add(txtGelisUcreti, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        pnlEkle.add(lblSatisUcreti, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        pnlEkle.add(txtSaticUcreti, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        pnlEkle.add(lblStok, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        pnlEkle.add(txtStok, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        pnlEkle.add(lblAciklama, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        pnlEkle.add(taAciklama, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        pnlEkle.add(btnEkle, gbc);

        pnlEkle.setBorder(new TitledBorder("Yeni Ürün Ekle"));
        border = pnlEkle.getBorder();
        margin = new EmptyBorder(10, 10, 10, 10);
        pnlEkle.setBorder(new CompoundBorder(border, margin));
        pnlEkle.setPreferredSize(new Dimension(400, 300));

        pnlDuzenle.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.anchor = GridBagConstraints.EAST;
        pnlDuzenle.add(lblUrunAd2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        pnlDuzenle.add(txtUrunAd2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlDuzenle.add(lblKategori2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        pnlDuzenle.add(cbKategori2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        pnlDuzenle.add(lblGelisUcreti2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        pnlDuzenle.add(txtGelisUcreti2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        pnlDuzenle.add(lblSatisUcreti2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        pnlDuzenle.add(txtSaticUcreti2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        pnlDuzenle.add(lblStok2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        pnlDuzenle.add(txtStok2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        pnlDuzenle.add(lblAciklama2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        pnlDuzenle.add(taAciklama2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        pnlDuzenle.add(btnDuzenle2, gbc);

        aktifPasif(pnlDuzenle, false);

        pnlDuzenle.setBorder(new TitledBorder("Ürün Düzenle"));
        border = pnlDuzenle.getBorder();
        margin = new EmptyBorder(10, 10, 10, 10);
        pnlDuzenle.setBorder(new CompoundBorder(border, margin));
        pnlDuzenle.setPreferredSize(new Dimension(400, 300));

        add(pnlUrunListe);
        add(pnlEkle);
        add(pnlDuzenle);

        //Fonksiyonlar
        urunlerTablosunuDoldur();
        kategorileriGetir();

        txtUrunAd.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {

            }

            @Override
            public void keyReleased(KeyEvent ke) {
                txtUrunAd.setBackground(Color.WHITE);
            }
        });

        txtUrunAd2.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {

            }

            @Override
            public void keyReleased(KeyEvent ke) {
                txtUrunAd2.setBackground(Color.WHITE);

            }
        });

        txtGelisUcreti.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {

            }

            @Override
            public void keyReleased(KeyEvent ke) {
                txtGelisUcreti.setBackground(Color.WHITE);
                if (Character.isLetter(ke.getKeyChar())) {
                    String formattedTex = txtGelisUcreti.getText().trim().replace(ke.getKeyChar(), '\0');
                    txtGelisUcreti.setText(formattedTex.trim());
                }

            }
        });

        txtGelisUcreti2.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {

            }

            @Override
            public void keyReleased(KeyEvent ke) {
                txtGelisUcreti2.setBackground(Color.WHITE);
                if (Character.isLetter(ke.getKeyChar())) {
                    String formattedTex = txtGelisUcreti2.getText().trim().replace(ke.getKeyChar(), '\0');
                    txtGelisUcreti2.setText(formattedTex.trim());
                }

            }
        });

        txtSaticUcreti.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {

            }

            @Override
            public void keyReleased(KeyEvent ke) {
                txtSaticUcreti.setBackground(Color.WHITE);
                if (Character.isLetter(ke.getKeyChar())) {
                    String formattedTex = txtSaticUcreti.getText().trim().replace(ke.getKeyChar(), '\0');
                    txtSaticUcreti.setText(formattedTex.trim());
                }

            }
        });

        txtSaticUcreti2.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {

            }

            @Override
            public void keyReleased(KeyEvent ke) {
                txtSaticUcreti2.setBackground(Color.WHITE);
                if (Character.isLetter(ke.getKeyChar())) {
                    String formattedTex = txtSaticUcreti2.getText().trim().replace(ke.getKeyChar(), '\0');
                    txtSaticUcreti2.setText(formattedTex.trim());
                }

            }
        });

        txtStok.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {

            }

            @Override
            public void keyReleased(KeyEvent ke) {
                txtStok.setBackground(Color.WHITE);
                if (Character.isLetter(ke.getKeyChar())) {
                    String formattedTex = txtStok.getText().trim().replace(ke.getKeyChar(), '\0');
                    txtStok.setText(formattedTex.trim());
                }

            }
        });
        
        
        txtStok2.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {

            }

            @Override
            public void keyReleased(KeyEvent ke) {
                txtStok2.setBackground(Color.WHITE);
                if (Character.isLetter(ke.getKeyChar())) {
                    String formattedTex = txtStok2.getText().trim().replace(ke.getKeyChar(), '\0');
                    txtStok2.setText(formattedTex.trim());
                }

            }
        });

        btnEkle.addActionListener(new ActionListener() {
            //Ürün ekler
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!alanlariKontrolEt(pnlEkle)) {
                    JOptionPane.showMessageDialog(UrunlerEkrani.this, "Bazı alanları doldurmadınız!", "Ürün Ekleme İşlemi", JOptionPane.ERROR_MESSAGE);
                } else {

                    Urunler eklenenUrun = new Urunler();
                    eklenenUrun.setAd(txtUrunAd.getText().trim());
                    eklenenUrun.setAlis(Integer.valueOf(txtGelisUcreti.getText().trim()));
                    eklenenUrun.setSatis(Integer.valueOf(txtSaticUcreti.getText().trim()));
                    eklenenUrun.setStok(Integer.valueOf(txtStok.getText().trim()));
                    eklenenUrun.setKategori(sunucudanGelen.get(cbKategori.getSelectedIndex()));
                    eklenenUrun.setAciklama(taAciklama.getText());
                    

                    Session session = null;
                    Transaction tx = null;

                    try {
                        session = HibernateUtil.getSessionFactory().openSession();
                        tx = session.beginTransaction();

                        session.save(eklenenUrun);

                        tx.commit();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(UrunlerEkrani.this, "Ürün ekleme sırasında hata oluştu.", "Ürün Ekleme İşlemi", JOptionPane.ERROR_MESSAGE);
                        tx.rollback();

                    } finally {
                        
                        if (!tx.getStatus().equals(TransactionStatus.ROLLED_BACK)) {
                            JOptionPane.showMessageDialog(UrunlerEkrani.this, "Ürün eklendi", "Ürün Ekleme İşlemi", JOptionPane.INFORMATION_MESSAGE);
                            dtmUrunListe.addRow(new String[]{eklenenUrun.getAd(), eklenenUrun.getKategori().getAd(),
                                eklenenUrun.getAlis().toString(), eklenenUrun.getSatis().toString(), eklenenUrun.getStok().toString(), eklenenUrun.getAciklama()});
                            dtmUrunListe.fireTableDataChanged();
                        }

                    }

                }

            }
        });

        btnDuzenle.addActionListener(new ActionListener() {
            //Seçilen ürünü düzenleme panelin aktifleştirir
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (tblUrunListe.getSelectedRow() < 0) {
                    JOptionPane.showMessageDialog(UrunlerEkrani.this, "Düzenlenecek ürünü seçmediniz.", "Ürün Düzenleme İşlemi", JOptionPane.WARNING_MESSAGE);
                } else {
                    aktifPasif(pnlDuzenle, true);
                    int secilenSatir = tblUrunListe.getSelectedRow();
                    txtUrunAd2.setText(dtmUrunListe.getValueAt(secilenSatir, 0).toString());
                    cbKategori2.setSelectedItem(dtmUrunListe.getValueAt(secilenSatir, 1));
                    txtGelisUcreti2.setText(dtmUrunListe.getValueAt(secilenSatir, 2).toString());
                    txtSaticUcreti2.setText(dtmUrunListe.getValueAt(secilenSatir, 3).toString());
                    txtStok2.setText(dtmUrunListe.getValueAt(secilenSatir, 4).toString());
                    taAciklama2.setText(dtmUrunListe.getValueAt(secilenSatir, 5).toString());
                    
                }
            }
        });

        btnDuzenle2.addActionListener(new ActionListener() {
            //Seçilen ürünü düzenler
            @Override
            public void actionPerformed(ActionEvent ae) {
                

                if (!alanlariKontrolEt(pnlDuzenle)) {
                    JOptionPane.showMessageDialog(UrunlerEkrani.this, "Bazı alanları doldurmadınız!", "Ürün Düzenleme İşlemi", JOptionPane.ERROR_MESSAGE);
                } else {
                    int a = JOptionPane.showConfirmDialog(UrunlerEkrani.this, "Bu ürünün bilgilerini değiştirmek istediğinize emin misiniz?", "Ürün Düzenleme İşlemi", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (a == JOptionPane.YES_OPTION) {

                        Urunler eklenenUrun = gelenUrunler.get(tblUrunListe.getSelectedRow());
                        eklenenUrun.setAd(txtUrunAd2.getText().trim());
                        eklenenUrun.setAlis(Integer.valueOf(txtGelisUcreti2.getText().trim()));
                        eklenenUrun.setSatis(Integer.valueOf(txtSaticUcreti2.getText().trim()));
                        eklenenUrun.setStok(Integer.valueOf(txtStok2.getText().trim()));
                        eklenenUrun.setKategori(sunucudanGelen.get(cbKategori2.getSelectedIndex()));
                        eklenenUrun.setAciklama(taAciklama2.getText());

                        Session session = null;
                        Transaction tx = null;

                        try {
                            session = HibernateUtil.getSessionFactory().openSession();
                            tx = session.beginTransaction();

                            session.merge(eklenenUrun);

                            tx.commit();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(UrunlerEkrani.this, "Ürün düzenleme sırasında hata oluştu.", "Ürün Düzenleme İşlemi", JOptionPane.ERROR_MESSAGE);
                            tx.rollback();

                        } finally {
                            if (!tx.getStatus().equals(TransactionStatus.ROLLED_BACK)) {
                                JOptionPane.showMessageDialog(UrunlerEkrani.this, "Ürün düzenlendi", "Ürün Düzenleme İşlemi", JOptionPane.INFORMATION_MESSAGE);
                                int secilenSatir = tblUrunListe.getSelectedRow();
                                dtmUrunListe.setValueAt(eklenenUrun.getAd(), secilenSatir, 0);
                                dtmUrunListe.setValueAt(eklenenUrun.getKategori().getAd(), secilenSatir, 1);
                                dtmUrunListe.setValueAt(eklenenUrun.getAlis(), secilenSatir, 2);
                                dtmUrunListe.setValueAt(eklenenUrun.getSatis(), secilenSatir, 3);
                                dtmUrunListe.setValueAt(eklenenUrun.getStok(), secilenSatir, 4);
                                dtmUrunListe.setValueAt(eklenenUrun.getAciklama(), secilenSatir, 5);
                                dtmUrunListe.fireTableDataChanged();
                                
                                panelTemizle(pnlDuzenle);
                                aktifPasif(pnlDuzenle, false);
                            }

                        }

                    }
                }

            }
        });

        btnSil.addActionListener(new ActionListener() {
            //Seçilen ürünü siler
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (tblUrunListe.getSelectedRow() >= 0) {

                    int a = JOptionPane.showConfirmDialog(UrunlerEkrani.this, "Ürünü silmek istediğinizden emin misiniz?", "Ürün Silme İşlemi", JOptionPane.YES_NO_CANCEL_OPTION);

                    if (a == JOptionPane.YES_OPTION) {

                        Session session = null;
                        Transaction tx = null;

                        try {
                            session = HibernateUtil.getSessionFactory().openSession();
                            tx = session.beginTransaction();
                            Urunler silinecekUrun = (Urunler) session.load(Urunler.class, gelenUrunler.get(tblUrunListe.getSelectedRow()).getId());
                            session.delete(silinecekUrun);
                            session.flush();
                            tx.commit();
                        } catch (Exception e) {
                            System.out.println("Silme sırasında hata oluştu: " + e);
                            tx.rollback();
                        } finally {
                            session.close();
                           if (!tx.getStatus().equals(TransactionStatus.ROLLED_BACK)) {

                                dtmUrunListe.removeRow(tblUrunListe.getSelectedRow());
                                dtmUrunListe.fireTableDataChanged();
                                JOptionPane.showMessageDialog(UrunlerEkrani.this, "Ürün silindi!", "Ürün Silme İşlemi", JOptionPane.INFORMATION_MESSAGE);

                            }
                        }

                    }

                } else {
                    JOptionPane.showMessageDialog(UrunlerEkrani.this, "Ürün seçimi yapmadınız!", "Ürün Silme İşlemi", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

    }

    //Ürün ekleme sırasında gerekli alanların doluluğunu kontrol eder
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

    public void kategorileriGetir() {
        //Ürün ekleme ve düzenleme panelindeki kategoriler combobox unu doldurur
        Session session = HibernateUtil.getSessionFactory().openSession();

        cbKategori.removeAllItems();
        cbKategori2.removeAllItems();
        
        Query query = session.createQuery("from Kategoriler");
        sunucudanGelen = query.list();

        for (int i = 0; i < sunucudanGelen.size(); i++) {

            Kategoriler kategori = (Kategoriler) sunucudanGelen.get(i);
            cbKategori.addItem(kategori.getAd());
            cbKategori2.addItem(kategori.getAd());

        }
    }

    public void urunlerTablosunuDoldur() {
        
        dtmUrunListe.setRowCount(0);

        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Urunler");
        gelenUrunler = query.list();

        for (int i = 0; i < gelenUrunler.size(); i++) {

            Urunler urun = (Urunler) (gelenUrunler.get(i));

            dtmUrunListe.addRow(new String[]{urun.getAd(), urun.getKategori().getAd(), urun.getAlis().toString(), urun.getSatis().toString(),
                urun.getStok().toString(), urun.getAciklama()});

        }

        tblUrunListe.setModel(dtmUrunListe);

    }

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
