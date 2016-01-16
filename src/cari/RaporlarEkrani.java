/**
 *
 * @author Erdinç Ocak
 */
package cari;

import HibClasses.HibernateUtil;
import HibClasses.Satislar;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.hibernate.Criteria;
import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

public class RaporlarEkrani extends JPanel {

    GridBagConstraints gbc = new GridBagConstraints();

    JPanel pnlFiltrele = new JPanel();
    JPanel pnlKarZarar = new JPanel();

    JLabel lblAranilacakBolge = new JLabel("Aranılacak Bölge: ");
    JLabel lblArama = new JLabel("Arama: ");
    JLabel lblTarih = new JLabel("Tarih Aralığı: ");

    JLabel lblGun = new JLabel("Gün");
    JLabel lblGun2 = new JLabel("Gün");
    JLabel lblAy = new JLabel("Ay");
    JLabel lblAy2 = new JLabel("Ay");
    JLabel lblYil = new JLabel("Yıl");
    JLabel lblYil2 = new JLabel("Yıl");
    JLabel lblArasinda = new JLabel("Arasında");
    JLabel lblKarZarar = new JLabel("KAR-ZARAR DURUMU: ");
    JLabel lblSatilan = new JLabel("Satılan: ");
    JLabel lblBilgilendirme = new JLabel("Bilgilendirme: ");

    JRadioButton rbMusteri = new JRadioButton("Müşteri");
    JRadioButton rbUrun = new JRadioButton("Ürün Adı");
    JRadioButton rbKategori = new JRadioButton("Kategori");

    JTextField txtArama = new JTextField(10);

    SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, Calendar.ERA);
    JSpinner spn = new JSpinner(model);
    SpinnerDateModel model2 = new SpinnerDateModel(new Date(), null, null, Calendar.ERA);
    JSpinner spn2 = new JSpinner(model2);

    DefaultTableModel dtmSonuc = new DefaultTableModel(new String[]{"MÜŞTERİ ADI", "ÜRÜN KATEGORİ", "ÜRÜN ADI", "ADEDİ", "FİYATI", "EKLENME TARİHİ"}, 5);
    JTable tblSonuc = new JTable(dtmSonuc);
    JScrollPane scrPaneSonuc = new JScrollPane(tblSonuc);

    List<Satislar> gelenSatislar = null;
    FullTextSession fullTextSession;
    ArrayList<String> arama = new ArrayList<>();

    RaporlarEkrani() {

        pnlFiltrele.setPreferredSize(new Dimension(800, 150));

        pnlFiltrele.setLayout(new GridBagLayout());
        //gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        gbc.weighty = 0.2;
        pnlFiltrele.add(lblAranilacakBolge, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.2;
        pnlFiltrele.add(rbMusteri, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.2;
        pnlFiltrele.add(rbUrun, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.2;
        pnlFiltrele.add(rbKategori, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        pnlFiltrele.add(lblArama, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.8;
        pnlFiltrele.add(txtArama, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        pnlFiltrele.add(lblTarih, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.1;
        pnlFiltrele.add(spn, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        pnlFiltrele.add(spn2, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        pnlFiltrele.add(new JLabel("              < tarihleri arasında >"), gbc);

        pnlFiltrele.setBorder(new TitledBorder("Arama"));
        Border margin = new EmptyBorder(10, 10, 10, 10);
        Border border = pnlFiltrele.getBorder();
        pnlFiltrele.setBorder(new CompoundBorder(border, margin));

        scrPaneSonuc.setBorder(new TitledBorder("Sonuç"));
        border = scrPaneSonuc.getBorder();
        scrPaneSonuc.setBorder(new CompoundBorder(border, margin));
        scrPaneSonuc.setPreferredSize(new Dimension(800, 300));

        //pnlKarZarar.setLayout(new BorderLayout());
        pnlKarZarar.add(lblKarZarar);
        pnlKarZarar.add(lblSatilan);
        pnlKarZarar.add(lblBilgilendirme);

        pnlKarZarar.setBorder(new TitledBorder("Kar-Zarar Durumu"));
        border = pnlKarZarar.getBorder();
        pnlKarZarar.setBorder(new CompoundBorder(border, margin));
        pnlKarZarar.setPreferredSize(new Dimension(800, 150));

        add(pnlFiltrele);
        add(scrPaneSonuc);
        add(pnlKarZarar);
        rbMusteri.setSelected(true);

        //Fonksiyonlar
        raporlarTablosunuDoldur();
        indexle();

        tblSonuc.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {

                if (lse.getValueIsAdjusting()) {

                    Satislar satis = gelenSatislar.get(tblSonuc.getSelectedRow());
                    int adet = satis.getAdet();
                    int karZarar = (satis.getUrunfiyat() * adet) - (satis.getUrunalis() * adet);
                    lblKarZarar.setText("KAR-ZARAR DURUMU: " + karZarar + " TL");

                }
            }
        });

        txtArama.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {

            }

            @Override
            public void keyReleased(KeyEvent ke) {

                aramaYap(txtArama.getText().trim());

            }
        });
    }

    public void raporlarTablosunuDoldur() {

        dtmSonuc.setRowCount(0);

        Session session = HibernateUtil.getSessionFactory().openSession();

        Criteria c = session.createCriteria(Satislar.class);
        c.addOrder(Order.asc("tarih"));
        gelenSatislar = c.list();

        for (int i = 0; i < gelenSatislar.size(); i++) {

            Satislar satis = (Satislar) (gelenSatislar.get(i));

            dtmSonuc.addRow(new String[]{satis.getMusteriadi(), satis.getUrunkategori(), satis.getUrunadi(),
                Integer.toString(satis.getAdet()), Integer.toString(satis.getUrunfiyat()), satis.getTarih().toString()});

        }

        tblSonuc.setModel(dtmSonuc);

    }

    private void aramaYap(String kelime) {

        arama.clear();
        
        //Arama yapılacak sütunların isimleri arraylist'e atılır
        if (rbMusteri.isSelected()) {
            arama.add("musteriadi");
        }
        if (rbUrun.isSelected()) {
            arama.add("urunadi");
        }
        if (rbKategori.isSelected()) {
            arama.add("urunkategori");
        }

        String[] fields = arama.toArray(new String[arama.size()]);

        fullTextSession.beginTransaction();

        
        QueryBuilder b = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(Satislar.class).get();

        //Aranmak istenen kelimenin, aranmak istenen sütunlarda aranması için oluşturulan query
        Query luceneQuery
                = b.keyword()
                .wildcard()
                .onFields(fields)
                .matching(kelime + "*")
                .createQuery();

        //Tarih aralığına göre arama yapan query
        Query datequery = b
                .range()
                .onField("tarih").ignoreFieldBridge()
                .from(DateTools.dateToString(model.getDate(), DateTools.Resolution.MILLISECOND))
                .to(DateTools.dateToString(model2.getDate(), DateTools.Resolution.MILLISECOND)).excludeLimit()
                .createQuery();

        //Yukardaki 2 query birleştirilerek hem keyword hem de tarih aralığına göre arama yaptırılır. 
        BooleanQuery bq = new BooleanQuery();
        bq.add(luceneQuery, BooleanClause.Occur.MUST);
        bq.add(datequery, BooleanClause.Occur.MUST);

        org.hibernate.Query fullTextQuery = fullTextSession.createFullTextQuery(bq);

        List<Satislar> kayitliSatislar = fullTextQuery.list();

        dtmSonuc.setRowCount(0);

        for (Satislar satis : kayitliSatislar) {
            System.out.println(satis);
            dtmSonuc.addRow(new String[]{satis.getMusteriadi(), satis.getUrunkategori(), satis.getUrunadi(),
                Integer.toString(satis.getAdet()), Integer.toString(satis.getUrunfiyat()), satis.getTarih().toString()});
        }

        tblSonuc.setModel(dtmSonuc);

    }

    private void indexle() {
        //Bu fonksiyon Lucene fulltext search işleminin yapılabilmesi için belirtilen tabloyu dökümanlar halinde indexler.
        //Indexlemenin yapılacağı klasörün yolu "hibernate.cfg.xml" dosyasının içinde property olarak belirlenir
        Session session = HibernateUtil.getSessionFactory().openSession();

        fullTextSession = Search.getFullTextSession(session);

        try {
            fullTextSession.createIndexer(Satislar.class
            ).startAndWait();
        } catch (InterruptedException ex) {
            Logger.getLogger(SatisYonetimiEkrani.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
