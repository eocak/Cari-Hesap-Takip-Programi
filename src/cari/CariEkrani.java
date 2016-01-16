/**
 *
 * @author Erdinç Ocak
 */
package cari;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;


public class CariEkrani extends JFrame{
    
    static JTabbedPane jtpCari = new JTabbedPane();
    static JLabel admin = new JLabel("", JLabel.RIGHT);
    
    CariEkrani(){
        
        add(admin, BorderLayout.NORTH);
      
        jtpCari.addTab("Müşteriler", new MusterilerEkrani());
        jtpCari.addTab("Ürünler", new UrunlerEkrani());
        jtpCari.addTab("Kategoriler", new KategorilerEkrani());
        jtpCari.addTab("Raporlar", new RaporlarEkrani());
        jtpCari.addTab("Satış Yönetimi", new SatisYonetimiEkrani());
        jtpCari.addTab("Kullanıcı Ayarı", new KullaniciAyariEkrani());
        
        
        jtpCari.setPreferredSize(new Dimension(650, 600));
        
        
        getContentPane().add(jtpCari, BorderLayout.CENTER);
        
        setTitle("Cari Program");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        
       
    
    }

   

}
