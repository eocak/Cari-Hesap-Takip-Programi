/**
 *
 * @author Erdinç Ocak
 */
package cari;

import HibClasses.HibernateUtil;
import HibClasses.Musteriler;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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

public class MusterilerEkrani extends JPanel {

    GridBagConstraints gbc = new GridBagConstraints();
    DefaultTableModel dtmMusteriListe = new DefaultTableModel(new String[]{"AD", "SOYAD", "TELEFON", "ADRES"}, 0);
    JTable tblMusteriListe = new JTable(dtmMusteriListe);
    JScrollPane scrPaneMusteriListe = new JScrollPane(tblMusteriListe);
    JPanel pnlKarar = new JPanel();
    JPanel pnlMusteriListe = new JPanel();
    JPanel pnlEkle = new JPanel();
    JPanel pnlDuzenle = new JPanel();

    JLabel lblAd = new JLabel("Müşteri Adı: ");
    JLabel lblSoyad = new JLabel("Soyadı: ");
    JLabel lblTelefon = new JLabel("Telefon: ");
    JLabel lblAdres = new JLabel("Adres: ");

    JTextField txtAd = new JTextField(20);
    JTextField txtSoyad = new JTextField(20);
    JTextField txtTelefon = new JTextField(20);
    JTextField txtAdres = new JTextField(20);

    JLabel lblAd2 = new JLabel("Müşteri Adı: ");
    JLabel lblSoyad2 = new JLabel("Soyadı: ");
    JLabel lblTelefon2 = new JLabel("Telefon: ");
    JLabel lblAdres2 = new JLabel("Adres: ");

    JTextField txtAd2 = new JTextField(20);
    JTextField txtSoyad2 = new JTextField(20);
    JTextField txtTelefon2 = new JTextField(20);
    JTextField txtAdres2 = new JTextField(20);

    JButton btnEkle = new JButton("EKLE");
    JButton btnDuzenle = new JButton("DÜZENLE");
    JButton btnDuzenle2 = new JButton("DÜZENLE");
    JButton btnSil = new JButton("SİL");

    //Müşterilerin ID lerini tutan Hashmap
    HashMap<Integer, Integer> musteriId = new HashMap<>();

    MusterilerEkrani() {

        scrPaneMusteriListe.setPreferredSize(new Dimension(700, 200));

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

        pnlMusteriListe.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlMusteriListe.add(scrPaneMusteriListe);
        pnlMusteriListe.add(pnlKarar);
        pnlMusteriListe.setBorder(new TitledBorder("Müşteri Listesi"));
        border = pnlMusteriListe.getBorder();
        margin = new EmptyBorder(10, 10, 10, 10);
        pnlMusteriListe.setBorder(new CompoundBorder(border, margin));

        gbc.insets = new Insets(0, 0, 0, 0);
        pnlEkle.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        pnlEkle.add(lblAd, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        pnlEkle.add(txtAd, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlEkle.add(lblSoyad, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        pnlEkle.add(txtSoyad, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        pnlEkle.add(lblTelefon, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        pnlEkle.add(txtTelefon, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        pnlEkle.add(lblAdres, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        pnlEkle.add(txtAdres, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        pnlEkle.add(btnEkle, gbc);

        pnlEkle.setBorder(new TitledBorder("Yeni Müşteri Ekle"));
        border = pnlEkle.getBorder();
        margin = new EmptyBorder(10, 10, 10, 10);
        pnlEkle.setBorder(new CompoundBorder(border, margin));
        pnlEkle.setPreferredSize(new Dimension(400, 300));

        pnlDuzenle.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlDuzenle.add(lblAd2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        pnlDuzenle.add(txtAd2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlDuzenle.add(lblSoyad2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        pnlDuzenle.add(txtSoyad2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        pnlDuzenle.add(lblTelefon2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        pnlDuzenle.add(txtTelefon2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        pnlDuzenle.add(lblAdres2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        pnlDuzenle.add(txtAdres2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        pnlDuzenle.add(btnDuzenle2, gbc);
        //btnDuzenle2.setEnabled(false);

        aktifPasif(pnlDuzenle, false);

        pnlDuzenle.setBorder(new TitledBorder("Müşteri Düzenle"));
        border = pnlDuzenle.getBorder();
        margin = new EmptyBorder(10, 10, 10, 10);
        pnlDuzenle.setBorder(new CompoundBorder(border, margin));
        pnlDuzenle.setPreferredSize(new Dimension(400, 300));

        add(pnlMusteriListe);
        add(pnlEkle);
        add(pnlDuzenle);

        ///Fonksiyonlar
        musteriTablosunuDoldur();

        btnEkle.addActionListener(new ActionListener() {
            //Müşteri ekler
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (!alanlariKontrolEt(pnlEkle)) {
                    JOptionPane.showMessageDialog(MusterilerEkrani.this, "Bazı alanları doldurmadınız!", "Yeni Müşteri Ekleme", JOptionPane.ERROR_MESSAGE);
                } else {
                    Musteriler yeniMusteri = new Musteriler();
                    yeniMusteri.setAd(txtAd.getText().trim());
                    yeniMusteri.setSoyad(txtSoyad.getText().trim());
                    yeniMusteri.setTelefon(txtTelefon.getText().trim());
                    yeniMusteri.setAdres(txtAdres.getText().trim());

                    Transaction tx = null;
                    Session session = null;

                    try {

                        session = HibernateUtil.getSessionFactory().openSession();
                        tx = session.beginTransaction();
                        session.save(yeniMusteri);

                        tx.commit();

                    } catch (Exception e) {
                        System.out.println("Ekleme sırasında hata oluştu: " + e);
                        tx.rollback();
                    } finally {
                        session.close();
                        if (!tx.getStatus().equals(TransactionStatus.ROLLED_BACK)) {
                            JOptionPane.showMessageDialog(MusterilerEkrani.this, "Başarıyla eklendi!", "Yeni Müşteri Ekleme", JOptionPane.INFORMATION_MESSAGE);
                            dtmMusteriListe.addRow(new String[]{yeniMusteri.getAd(), yeniMusteri.getSoyad(), yeniMusteri.getTelefon(), yeniMusteri.getAdres()});
                            dtmMusteriListe.fireTableDataChanged();
                            clearPanel(pnlEkle);

                            //Yapılan değişikliklerden etkilenen alanları yeniler
                            SatisYonetimiEkrani sye = (SatisYonetimiEkrani) CariEkrani.jtpCari.getComponent(4);
                            sye.musterileriGetir();
                        }
                    }
                }

            }
        }
        );

        btnSil.addActionListener(new ActionListener() {
            //Müşteri siler
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (tblMusteriListe.getSelectedRow() >= 0) {

                    int a = JOptionPane.showConfirmDialog(MusterilerEkrani.this, "Müşteriyi silmek istediğinizden emin misiniz?", "Müşteri Silme İşlemi", JOptionPane.YES_NO_CANCEL_OPTION);

                    if (a == JOptionPane.YES_OPTION) {

                        Session session = null;
                        Transaction tx = null;

                        try {
                            session = HibernateUtil.getSessionFactory().openSession();
                            tx = session.beginTransaction();
                            Musteriler silinecekMusteri = (Musteriler) session.load(Musteriler.class, musteriId.get(tblMusteriListe.getSelectedRow()));
                            session.delete(silinecekMusteri);
                            session.flush();
                            tx.commit();
                        } catch (Exception e) {
                            System.out.println("Silme sırasında hata oluştu: " + e);
                            tx.rollback();
                        } finally {
                            session.close();
                            if (!tx.getStatus().equals(TransactionStatus.ROLLED_BACK)) {

                                dtmMusteriListe.removeRow(tblMusteriListe.getSelectedRow());
                                dtmMusteriListe.fireTableDataChanged();
                                JOptionPane.showMessageDialog(MusterilerEkrani.this, "Müşteri silindi!", "Müşteri Silme İşlemi", JOptionPane.INFORMATION_MESSAGE);

                                 //Yapılan değişikliklerden etkilenen alanları yeniler
                                SatisYonetimiEkrani sye = (SatisYonetimiEkrani) CariEkrani.jtpCari.getComponent(4);
                                sye.musterileriGetir();
                            }
                        }

                    }

                } else {
                    JOptionPane.showMessageDialog(MusterilerEkrani.this, "Müşteri seçimi yapmadınız!", "Müşteri Silme İşlemi", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        btnDuzenle.addActionListener(new ActionListener() {
            //Seçilen müşteriyi düzenleme panelini aktifleştirir
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (tblMusteriListe.getSelectedRow() >= 0) {
                    aktifPasif(pnlDuzenle, true);
                    int secilenSatir = tblMusteriListe.getSelectedRow();
                    txtAd2.setText(dtmMusteriListe.getValueAt(secilenSatir, 0).toString());
                    txtSoyad2.setText(dtmMusteriListe.getValueAt(secilenSatir, 1).toString());
                    txtTelefon2.setText(dtmMusteriListe.getValueAt(secilenSatir, 2).toString());
                    txtAdres2.setText(dtmMusteriListe.getValueAt(secilenSatir, 3).toString());
                    
                } else {
                    JOptionPane.showMessageDialog(MusterilerEkrani.this, "Müşteri seçmediniz.", "Müşteri Düzenleme İşlemi", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnDuzenle2.addActionListener(new ActionListener() {
            //Seçilen müşteriyi düzenler
            @Override
            public void actionPerformed(ActionEvent ae) {

                if (tblMusteriListe.getSelectedRow() >= 0) {

                    int a = JOptionPane.showConfirmDialog(MusterilerEkrani.this, "Müşteri bilgilerini değiştirmek istediğinizden emin misiniz?", "Müşteri Düzenleme İşlemi", JOptionPane.YES_NO_CANCEL_OPTION);

                    if (a == JOptionPane.YES_OPTION) {

                        Session session = null;
                        Transaction tx = null;

                        try {
                            session = HibernateUtil.getSessionFactory().openSession();
                            tx = session.beginTransaction();
                            Musteriler degisenMusteri = (Musteriler) session.load(Musteriler.class, musteriId.get(tblMusteriListe.getSelectedRow()));
                            degisenMusteri.setAd(txtAd2.getText().trim());
                            degisenMusteri.setSoyad(txtSoyad2.getText().trim());
                            degisenMusteri.setTelefon(txtTelefon2.getText().trim());
                            degisenMusteri.setAdres(txtAdres2.getText().trim());
                            session.merge(degisenMusteri);

                            tx.commit();
                        } catch (Exception e) {
                            System.out.println("Düzenleme sırasında hata oluştu: " + e);
                            tx.rollback();
                        } finally {
                            session.close();
                            if (!tx.getStatus().equals(TransactionStatus.ROLLED_BACK)) {

                                dtmMusteriListe.setValueAt(txtAd2.getText().trim(), tblMusteriListe.getSelectedRow(), 0);
                                dtmMusteriListe.setValueAt(txtSoyad2.getText().trim(), tblMusteriListe.getSelectedRow(), 1);
                                dtmMusteriListe.setValueAt(txtTelefon2.getText().trim(), tblMusteriListe.getSelectedRow(), 2);
                                dtmMusteriListe.setValueAt(txtAdres2.getText().trim(), tblMusteriListe.getSelectedRow(), 3);
                                dtmMusteriListe.fireTableDataChanged();
                                JOptionPane.showMessageDialog(MusterilerEkrani.this, "Müşteri bilgileri değiştirildi!", "Müşteri Düzenleme İşlemi", JOptionPane.INFORMATION_MESSAGE);

                                SatisYonetimiEkrani sye = (SatisYonetimiEkrani) CariEkrani.jtpCari.getComponent(4);
                                sye.musterileriGetir();

                            }
                        }

                    }

                } else {
                    JOptionPane.showMessageDialog(MusterilerEkrani.this, "Müşteri seçimi yapmadınız!", "Müşteri Düzenleme İşlemi", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

    }

    //Girilmesi zorunlu alanların kontrolü
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

    
    private void aktifPasif(JPanel pnl, boolean aktif) {

        if (aktif) {

            Component[] icerdekiElementler = pnl.getComponents();

            for (Component item : icerdekiElementler) {

                if (item instanceof JTextField || item instanceof JButton) {

                    item.setEnabled(true);

                }

            }

        } else {
            Component[] icerdekiElementler = pnl.getComponents();

            for (Component item : icerdekiElementler) {

                if (item instanceof JTextField || item instanceof JButton) {

                    item.setEnabled(false);

                }

            }
        }

    }

    private void clearPanel(JPanel panel) {

        Component[] icerdekiElementler = panel.getComponents();

        for (Component item : icerdekiElementler) {

            if (item instanceof JTextField) {

                JTextField item2 = (JTextField) item;
                item2.setText("");

            }

        }

    }

    private void musteriTablosunuDoldur() {

        musteriId.clear();
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Musteriler");
        List ls = query.list();

        for (int i = 0; i < ls.size(); i++) {

            Musteriler musteri = (Musteriler) (ls.get(i));
            musteriId.put(i, musteri.getId());
            dtmMusteriListe.addRow(new String[]{musteri.getAd(), musteri.getSoyad(), musteri.getTelefon(), musteri.getAdres()});

        }

        tblMusteriListe.setModel(dtmMusteriListe);

    }

}
