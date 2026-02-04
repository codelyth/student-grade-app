package app;

import model.Student;
import service.GradeService;
import io.CsvStorage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StudentGradeApp extends JFrame {

    // --- GENEL DEĞİŞKENLER ---
    private List<Student> studentList;

    // --- KAYIT SEKMESİ BİLEŞENLERİ ---
    private JTextField txtNo, txtAd, txtVize, txtFinal;
    private DefaultTableModel tableModel;
    private JTable table; 

    // --- RAPOR SEKMESİ BİLEŞENLERİ ---
    private JLabel lblSinifOrt, lblEnYuksek, lblEnDusuk, lblGecen, lblKalan;

    public StudentGradeApp() {
        // Listeyi başlat
        studentList = new ArrayList<>();

        // Frame Ayarları
        setTitle("Öğrenci Not Sistemi");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // TabbedPane oluştur
        JTabbedPane tabbedPane = new JTabbedPane();

        // 1. SEKME: KAYIT PANELİ
        JPanel pnlKayit = createKayitPanel();
        tabbedPane.addTab("Kayıt İşlemleri", pnlKayit);

        // 2. SEKME: RAPOR PANELİ
        JPanel pnlRapor = createRaporPanel();
        tabbedPane.addTab("Genel Rapor", pnlRapor);

        add(tabbedPane);
    }

    // --- 1. SEKME OLUŞTURUCU (KAYIT) ---
    private JPanel createKayitPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // -- ÜST FORM --
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Öğrenci No:"));
        txtNo = new JTextField();
        inputPanel.add(txtNo);

        inputPanel.add(new JLabel("Ad Soyad:"));
        txtAd = new JTextField();
        inputPanel.add(txtAd);

        inputPanel.add(new JLabel("Vize:"));
        txtVize = new JTextField();
        inputPanel.add(txtVize);

        inputPanel.add(new JLabel("Final:"));
        txtFinal = new JTextField();
        inputPanel.add(txtFinal);

        // Butonlar için ayrı panel
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnEkle = new JButton("Ekle");
        JButton btnSil = new JButton("Seçileni Sil"); // YENİ BUTON
        JButton btnKaydet = new JButton("Kaydet (CSV)");
        JButton btnYukle = new JButton("Yükle (CSV)");

        // Renklendirme 
        btnSil.setForeground(Color.RED); 

        btnPanel.add(btnEkle);
        btnPanel.add(btnSil); 
        btnPanel.add(btnKaydet);
        btnPanel.add(btnYukle);

        // Form ve Butonları birleştir
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(inputPanel, BorderLayout.CENTER);
        topContainer.add(btnPanel, BorderLayout.SOUTH);

        panel.add(topContainer, BorderLayout.NORTH);

        // -- TABLO --
        String[] columns = {"Öğrenci No", "Ad Soyad", "Vize", "Final", "Ortalama", "Harf"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // -- ACTION LISTENERS --
        btnEkle.addActionListener(e -> ekleIslemi());
        btnSil.addActionListener(e -> silmeIslemi()); 
        btnKaydet.addActionListener(e -> kaydetIslemi());
        btnYukle.addActionListener(e -> yukleIslemi());

        return panel;
    }

    // --- 2. SEKME OLUŞTURUCU (RAPOR) ---
    private JPanel createRaporPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("SINIF GENEL DURUM RAPORU");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title);

        lblSinifOrt = new JLabel("Sınıf Ortalaması: -");
        lblEnYuksek = new JLabel("En Yüksek Ortalama: -");
        lblEnDusuk = new JLabel("En Düşük Ortalama: -");
        lblGecen = new JLabel("Geçen Öğrenci Sayısı (>=60): -");
        lblKalan = new JLabel("Kalan Öğrenci Sayısı (<60): -");
        
        Font font = new Font("Arial", Font.PLAIN, 14);
        lblSinifOrt.setFont(font);
        lblEnYuksek.setFont(font);
        lblEnDusuk.setFont(font);
        lblGecen.setFont(font);
        lblKalan.setFont(font);

        panel.add(lblSinifOrt);
        panel.add(lblEnYuksek);
        panel.add(lblEnDusuk);
        panel.add(lblGecen);
        panel.add(lblKalan);

        JButton btnHesapla = new JButton("Raporu Güncelle / Hesapla");
        btnHesapla.setFont(new Font("Arial", Font.BOLD, 14));
        btnHesapla.addActionListener(e -> hesaplaRapor());
        panel.add(btnHesapla);

        return panel;
    }

    // --- İŞLEM METOTLARI ---

    private void ekleIslemi() {
        try {
            String no = txtNo.getText();
            String ad = txtAd.getText();
            
            if (no.isEmpty() || ad.isEmpty() || txtVize.getText().isEmpty() || txtFinal.getText().isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurunuz.");
                 return;
            }

            double vize = Double.parseDouble(txtVize.getText());
            double finalNot = Double.parseDouble(txtFinal.getText());

            if (vize < 0 || vize > 100 || finalNot < 0 || finalNot > 100) {
                JOptionPane.showMessageDialog(this, "Notlar 0-100 arasında olmalı.");
                return;
            }

            Student s = new Student(no, ad, vize, finalNot);
            studentList.add(s);
            tabloyaSatirEkle(s);
            
            temizle();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lütfen sayısal değerleri doğru giriniz.");
        }
    }

    private void silmeIslemi() {
        // Tablodan seçili satırın indeksini al
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için tablodan bir satır seçiniz.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Onay kutusu
        int response = JOptionPane.showConfirmDialog(this, 
                "Seçili öğrenciyi silmek istediğinize emin misiniz?", 
                "Silme Onayı", 
                JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            // 1. Listeden sil (Veri tabanı/Hafıza)
            // Tablodaki sıra ile listedeki sıra aynı olduğu için index'i kullanabiliriz.
            studentList.remove(selectedRow);

            // 2. Tablodan sil (Görünüm)
            tableModel.removeRow(selectedRow);

            JOptionPane.showMessageDialog(this, "Kayıt başarıyla silindi.", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void kaydetIslemi() {
        if(studentList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kaydedilecek veri yok.");
            return;
        }
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getAbsolutePath();
            if(!path.endsWith(".csv")) path += ".csv";
            CsvStorage.save(studentList, path);
            JOptionPane.showMessageDialog(this, "Dosya kaydedildi.");
        }
    }

    private void yukleIslemi() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            List<Student> loaded = CsvStorage.load(fc.getSelectedFile().getAbsolutePath());
            if (loaded != null && !loaded.isEmpty()) {
                studentList = loaded;
                tableModel.setRowCount(0); // Tabloyu temizle
                for (Student s : studentList) {
                    tabloyaSatirEkle(s);
                }
                JOptionPane.showMessageDialog(this, "Veriler yüklendi. Kişi sayısı: " + loaded.size());
            }
        }
    }

    private void hesaplaRapor() {
        if (studentList.isEmpty()) {
            lblSinifOrt.setText("Sınıf Ortalaması: Veri Yok");
            lblEnYuksek.setText("En Yüksek: Veri Yok");
            lblEnDusuk.setText("En Düşük: Veri Yok");
            lblGecen.setText("Geçen: 0");
            lblKalan.setText("Kalan: 0");
            return;
        }

        double toplamOrt = 0;
        double maxOrt = -1;
        String maxOgrenci = "";
        double minOrt = 101;
        int gecenSayisi = 0;
        int kalanSayisi = 0;

        for (Student s : studentList) {
            double ort = s.getAverage();
            toplamOrt += ort;

            if (ort > maxOrt) {
                maxOrt = ort;
                maxOgrenci = s.getName();
            }
            if (ort < minOrt) {
                minOrt = ort;
            }
            if (ort >= 60) gecenSayisi++;
            else kalanSayisi++;
        }

        double genelOrtalama = toplamOrt / studentList.size();

        lblSinifOrt.setText(String.format("Sınıf Ortalaması: %.2f", genelOrtalama));
        lblEnYuksek.setText(String.format("En Yüksek Ortalama: %.2f (%s)", maxOrt, maxOgrenci));
        lblEnDusuk.setText(String.format("En Düşük Ortalama: %.2f", minOrt));
        lblGecen.setText("Geçen Öğrenci Sayısı (>=60): " + gecenSayisi);
        lblKalan.setText("Kalan Öğrenci Sayısı (<60): " + kalanSayisi);
        
        JOptionPane.showMessageDialog(this, "Rapor güncellendi.");
    }

    private void tabloyaSatirEkle(Student s) {
        Object[] row = { s.getNo(), s.getName(), s.getMidterm(), s.getFin(), 
                         String.format("%.2f", s.getAverage()), s.getLetter() };
        tableModel.addRow(row);
    }
    
    private void temizle() {
        txtNo.setText(""); txtAd.setText(""); txtVize.setText(""); txtFinal.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentGradeApp().setVisible(true));
    }
}