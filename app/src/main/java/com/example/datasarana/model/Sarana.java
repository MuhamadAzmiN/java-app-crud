package com.example.datasarana.model;

public class Sarana {
    private int id;
    private String nama;
    private String jenis;
    private String lokasi;
    private String kondisi;
    private int jumlah;

    // Constructor
    public Sarana(int id, String nama, String jenis, String lokasi, String kondisi, int jumlah) {
        this.id = id;
        this.nama = nama;
        this.jenis = jenis;
        this.lokasi = lokasi;
        this.kondisi = kondisi;
        this.jumlah = jumlah;
    }

    // Getter dan Setter (wajib ada untuk CRUD)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; } // ← Inilah yang dipanggil di EditActivity

    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }

    public String getLokasi() { return lokasi; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }

    public String getKondisi() { return kondisi; }
    public void setKondisi(String kondisi) { this.kondisi = kondisi; }

    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
}