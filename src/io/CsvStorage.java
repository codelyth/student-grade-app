package io;

import model.Student;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvStorage {
	// MEVCUT METOT: Listeyi CSV dosyasına yazar
    public static void save(List<Student> students, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            
            // Başlık satırı
            writer.write("no,name,midterm,final,average,letter");
            writer.newLine();

            for (Student s : students) {
                String line = s.getNo() + "," +
                              s.getName() + "," +
                              s.getMidterm() + "," +
                              s.getFin() + "," +
                              s.getAverage() + "," +
                              s.getLetter();

                writer.write(line);
                writer.newLine();
            }
            System.out.println("Dosya kaydedildi: " + path);

        } catch (IOException e) {
            System.err.println("Kaydetme hatası: " + e.getMessage());
        }
    }

    // CSV dosyasından okuyup liste döndürür
    public static List<Student> load(String path) {
        List<Student> loadedStudents = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            
            // İlk satır başlıktır, okuyup geçiyoruz
            String headerLine = reader.readLine(); 

            String line;
            // Dosya bitene kadar satır satır oku
            while ((line = reader.readLine()) != null) {
                // Virgül ile ayır
                String[] parts = line.split(",");

                // En az 4 temel veri (no, name, vize, final) olmalı
                if (parts.length >= 4) {
                    try {
                        String no = parts[0];
                        String name = parts[1];
                        double midterm = Double.parseDouble(parts[2]);
                        double fin = Double.parseDouble(parts[3]);

                        // Constructor ortalamayı ve harfi otomatik hesaplar.
                        // CSV'deki average ve letter sütunlarını (parts[4], parts[5]) okumaya gerek yoktur,
                        // veri tutarlılığı için yeniden hesaplanması daha güvenlidir.
                        Student student = new Student(no, name, midterm, fin);
                        
                        loadedStudents.add(student);

                    } catch (NumberFormatException e) {
                        System.err.println("Satırda sayısal hata var, atlanıyor: " + line);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Okuma hatası: " + e.getMessage());
        }

        return loadedStudents;
    }
}
