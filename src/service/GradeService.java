package service;

public class GradeService {
	// 1. Metot: Ortalama Hesaplama
    // Vize %40, Final %60 etkili olacak şekilde hesaplar.
    // Nesne oluşturmaya gerek kalmadan kullanılabilmesi için 'static' yapıldı.
    public static double calcAverage(double midterm, double fin) {
        return (midterm * 0.4) + (fin * 0.6);
    }

    // 2. Metot: Harf Notu Belirleme
    // Hesaplanan ortalamaya göre ilgili harf notunu döndürür.
    public static String letterFromAverage(double avg) {
        if (avg >= 90) {
            return "AA";
        } else if (avg >= 85) {
            return "BA";
        } else if (avg >= 80) {
            return "BB";
        } else if (avg >= 75) {
            return "CB";
        } else if (avg >= 70) {
            return "CC";
        } else if (avg >= 60) {
            return "DC";
        } else if (avg >= 50) {
            return "DD";
        } else {
            return "FF";
        }
    }
}
