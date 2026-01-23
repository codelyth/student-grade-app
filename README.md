# 🎓 Öğrenci Not Takip Sistemi (Student Grade Management System)

Bu proje, Java programlama dili ve Swing kütüphanesi kullanılarak geliştirilmiş masaüstü tabanlı bir öğrenci not takip ve raporlama uygulamasıdır. Katmanlı mimari (Layered Architecture) prensiplerine uygun olarak tasarlanmıştır.

## 🚀 Özellikler

Uygulama iki ana sekmeden oluşmaktadır:

### 1. Kayıt İşlemleri
* **Öğrenci Ekleme:** Öğrenci numarası, adı, vize (%40) ve final (%60) notları girilerek sisteme eklenir.
* **Otomatik Hesaplama:** Ortalama ve harf notu (AA, BA, BB vb.) sistem tarafından otomatik hesaplanır.
* **Silme İşlemi:** Listeden seçilen öğrenci kaydı hem tablodan hem de hafızadan silinebilir.
* **Dosya İşlemleri (CSV):**
    * **Kaydet:** Girilen veriler `.csv` formatında dışa aktarılabilir.
    * **Yükle:** Daha önce kaydedilmiş CSV dosyaları sisteme tekrar yüklenebilir.

### 2. Genel Raporlama
Sınıfın genel durumu hakkında anlık istatistikler sunar:
* Sınıf Ortalaması
* En Yüksek ve En Düşük Ortalama (Öğrenci adıyla birlikte)
* Dersi Geçen (>=60) ve Kalan (<60) Öğrenci Sayıları

## 🛠 Kullanılan Teknolojiler ve Mimari

Proje **MVC (Model-View-Controller)** benzeri katmanlı bir yapıya sahiptir:

* **Dil:** Java (JDK 17+)
* **Arayüz (GUI):** Java Swing (JFrame, JTabbedPane, JTable)
* **Veri Yönetimi:** Java Collections (ArrayList)
* **Dosya I/O:** `BufferedReader` / `BufferedWriter` (CSV İşlemleri)

### Proje Yapısı (Package Structure)
* `app`: Arayüz kodlarını ve `main` metodunu barındırır (`StudentGradeApp.java`).
* `model`: Veri modelini temsil eder (`Student.java`).
* `service`: Hesaplama mantığını içerir (`GradeService.java`).
* `io`: Dosya okuma/yazma işlemlerini yönetir (`CsvStorage.java`).


---
Bu proje eğitim amaçlı geliştirilmiştir.
