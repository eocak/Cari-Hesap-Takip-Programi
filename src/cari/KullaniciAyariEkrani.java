/**
 *
 * @author Erdinç Ocak
 */
package cari;

import HibClasses.HibernateUtil;
import HibClasses.Kullanicilar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class KullaniciAyariEkrani extends JPanel {

    GridBagConstraints gbc = new GridBagConstraints();

    JPanel pnlSifre = new JPanel(new GridBagLayout());
    JLabel lblSifre = new JLabel("Eski Şifre: ");
    JLabel lblYeniSifre = new JLabel("Yeni Şifre: ");
    JLabel lblYeniSifre2 = new JLabel("Yeni Şifre Tekrar: ");
    JLabel lblAd = new JLabel(" Yeni Kullanıcı Adı: ");

    JTextField txtAd = new JTextField(15);
    JPasswordField txtSifre = new JPasswordField(15);
    JPasswordField txtYeniSifre = new JPasswordField(15);
    JPasswordField txtYeniSifre2 = new JPasswordField(15);

    JButton btnDegistir = new JButton("DEĞİŞTİR");

    static String kullaniciAdi;
    static String kullaniciSifre;

    KullaniciAyariEkrani() {

        pnlSifre.setBorder(new TitledBorder("Şifre Değiştir"));
        Border border = pnlSifre.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);
        pnlSifre.setBorder(new CompoundBorder(border, margin));
        pnlSifre.setPreferredSize(new Dimension(400, 400));

        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlSifre.add(lblAd, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        pnlSifre.add(txtAd, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlSifre.add(lblSifre, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        pnlSifre.add(txtSifre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        pnlSifre.add(lblYeniSifre, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        pnlSifre.add(txtYeniSifre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        pnlSifre.add(lblYeniSifre2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        pnlSifre.add(txtYeniSifre2, gbc);

        gbc.gridy = 4;

        pnlSifre.add(btnDegistir, gbc);

        add(pnlSifre);

        //Fonksiyonlar
        btnDegistir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                String a = new String(txtYeniSifre.getPassword()).trim();
                String b = new String(txtYeniSifre2.getPassword()).trim();
                String c = new String(txtSifre.getPassword()).trim();
                String d = txtAd.getText().trim();

                if (!c.equals(kullaniciSifre)) {
                    txtSifre.setBackground(Color.RED);
                    txtAd.setBackground(Color.RED);
                    JOptionPane.showMessageDialog(KullaniciAyariEkrani.this, "Şifreyi ya da kullanıcı adını yanlış girdiniz", "Kullanıcı Bilgisi Değiştirme", JOptionPane.ERROR_MESSAGE);
                } else if (a.equals("")) {
                    txtYeniSifre.setBackground(Color.RED);
                } else if (b.equals("")) {
                    txtYeniSifre2.setBackground(Color.RED);
                } else if (!a.equals(b)) {
                    JOptionPane.showMessageDialog(KullaniciAyariEkrani.this, "Şifreles eşleşmiyor", "Kullanıcı Bilgisi Değiştirme", JOptionPane.ERROR_MESSAGE);
                    txtSifre.setBackground(Color.RED);
                } else {

                    Session session = null;
                    Transaction tx = null;

                    try {

                        session = HibernateUtil.getSessionFactory().openSession();
                        tx = session.beginTransaction();

                        List<Kullanicilar> kl = session.createCriteria(Kullanicilar.class)
                                .add(Restrictions.eq("ad", kullaniciAdi))
                                .add(Restrictions.eq("sifre", kullaniciSifre))
                                .list();

                        Kullanicilar kullanici = kl.get(0);

                        kullanici.setAd(txtAd.getText().trim());
                        kullanici.setSifre(new String(txtYeniSifre.getPassword()).trim());

                        session.save(kullanici);
                        tx.commit();

                    } catch (Exception e) {
                        tx.rollback();
                        JOptionPane.showMessageDialog(KullaniciAyariEkrani.this, "Kullanıcı bilgisi değiştirme sırasında hata", "Kullanıcı Bilgisi Değiştirme", JOptionPane.ERROR_MESSAGE);

                    } finally {

                        if (!tx.getStatus().equals(TransactionStatus.ROLLED_BACK)) {
                            JOptionPane.showMessageDialog(KullaniciAyariEkrani.this, "Kullanıcı bilgisi değiştirildi", "Kullanıcı Bilgisi Değiştirme", JOptionPane.INFORMATION_MESSAGE);
                            JFrame f5 = (JFrame) SwingUtilities.getRootPane(KullaniciAyariEkrani.this).getParent();

                            //Frame kapanır ve kullanıcıdan tekrar giriş yapması istenir
                            f5.dispose();
                            CariEkrani.jtpCari = new JTabbedPane();
                            new GirisEkrani().setVisible(true);
                        }

                    }

                }

            }
        }
        );

        txtYeniSifre2.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {

            }

            @Override
            public void keyReleased(KeyEvent ke) {
                txtYeniSifre2.setBackground(Color.WHITE);

            }
        });

        txtYeniSifre.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {

            }

            @Override
            public void keyReleased(KeyEvent ke) {
                txtYeniSifre.setBackground(Color.WHITE);

            }
        });

        txtAd.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {

            }

            @Override
            public void keyReleased(KeyEvent ke) {
                txtAd.setBackground(Color.WHITE);

            }
        });

        txtSifre.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {

            }

            @Override
            public void keyReleased(KeyEvent ke) {
                txtSifre.setBackground(Color.WHITE);

            }
        });

    }

    private boolean Check(String a, String b) {

        boolean esdeger = false;

        if (a.equals(b) && !a.equals("")) {
            return true;
        } else {
            JOptionPane.showMessageDialog(KullaniciAyariEkrani.this, "Yeni şifreler eşleşmiyor ya da yeni şifre alanlarını boş bıraktınız.",
                    "Kullanıcı Bilgisi Değiştirme", JOptionPane.ERROR_MESSAGE);
            txtYeniSifre.setBackground(Color.RED);
            txtYeniSifre2.setBackground(Color.RED);
            return false;
        }

    }

}
