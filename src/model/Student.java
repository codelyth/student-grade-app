package model;

public class Student {
	// Fields
    private String no;
    private String name;
    private double midterm;
    private double fin;
    private double average;
    private String letter;

    // Constructor 
    // Ortalama ve harf notunu dışarıdan almak yerine, burada hesaplamak daha güvenlidir.
    public Student(String no, String name, double midterm, double fin) {
        this.no = no;
        this.name = name;
        this.midterm = midterm;
        this.fin = fin;
        
        // Nesne oluşturulurken hesaplamaları otomatik yap
        hesapla();
    }

    // Ortalama ve Harf Notu Hesaplama Yardımcı Metodu
    private void hesapla() {
        // Vize %40, Final %60
        this.average = (this.midterm * 0.4) + (this.fin * 0.6);
        
        // Harf Notu Belirleme
        if (this.average >= 90) this.letter = "AA";
        else if (this.average >= 85) this.letter = "BA";
        else if (this.average >= 80) this.letter = "BB";
        else if (this.average >= 75) this.letter = "CB";
        else if (this.average >= 70) this.letter = "CC";
        else if (this.average >= 60) this.letter = "DC";
        else if (this.average >= 50) this.letter = "DD";
        else this.letter = "FF";
    }

    // Getter Metotları
    public String getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public double getMidterm() {
        return midterm;
    }

    public double getFin() {
        return fin;
    }

    public double getAverage() {
        return average;
    }

    public String getLetter() {
        return letter;
    }
}
