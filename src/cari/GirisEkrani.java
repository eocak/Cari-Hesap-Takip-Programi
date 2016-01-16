/**
 *
 * @author Erdinç Ocak
 */
package cari;

import HibClasses.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.hibernate.Query;
import org.hibernate.Session;

public class GirisEkrani extends JFrame {

    JLabel lblKullaniciAdi = new JLabel("Kullanıcı Adı: ", JLabel.RIGHT);
    JLabel lblKullaniciParola = new JLabel("Parola: ", JLabel.RIGHT);
    JTextField txtKullaniciAdi = new JTextField(15);
    JPasswordField txtKullaniciParola = new JPasswordField(15);
    JButton btnGiris = new JButton("Giriş");

    GridBagConstraints gbc = new GridBagConstraints();

    protected GirisEkrani() {

        setLayout(new GridBagLayout());
        txtKullaniciParola.setEchoChar('*');

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblKullaniciAdi, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(txtKullaniciAdi, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblKullaniciParola, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(txtKullaniciParola, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(btnGiris, gbc);

        setTitle("Admin Giriş");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        btnGiris.addActionListener((ActionEvent ae) -> {
            Session session = HibernateUtil.getSessionFactory().openSession();

            Query query = session.createQuery("from Kullanicilar where ad = :ad1 and sifre = :sifre1");
            query.setParameter("ad1", txtKullaniciAdi.getText().trim());
            query.setParameter("sifre1", new String(txtKullaniciParola.getPassword()));
            List ls = query.list();

            if (ls.size() == 1) {
                //Başarılı girişte giriş ekranı kapanır ve program açılır
                dispose();
                KullaniciAyariEkrani.kullaniciAdi = txtKullaniciAdi.getText().trim();
                KullaniciAyariEkrani.kullaniciSifre = new String(txtKullaniciParola.getPassword()).trim();
                CariEkrani.admin.setText("Hoşgeldiniz Sn. " + txtKullaniciAdi.getText().trim());
                new CariEkrani().setVisible(true);
                
            } else {

                JOptionPane.showMessageDialog(GirisEkrani.this, "Yanlış kullanıcı adı ya da şifre girdiniz!", "Hata", JOptionPane.ERROR_MESSAGE);

            }
        });

    }

    public static void main(String[] args) {
        //Java aplikasyonunun teması Nimbus style olarak ayarlanır
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GirisEkrani.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new GirisEkrani().setVisible(true);
        });
    }

}
