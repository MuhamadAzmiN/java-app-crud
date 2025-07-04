package com.example.datasarana;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sarana.db";
    private static final int DATABASE_VERSION = 1;

    // Nama tabel dan kolom
    public static final String TABLE_SARANA = "sarana";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_JENIS = "jenis";
    public static final String COLUMN_LOKASI = "lokasi";
    public static final String COLUMN_KONDISI = "kondisi";
    public static final String COLUMN_JUMLAH = "jumlah";

    // Query pembuatan tabel
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_SARANA + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAMA + " TEXT, " +
                    COLUMN_JENIS + " TEXT, " +
                    COLUMN_LOKASI + " TEXT, " +
                    COLUMN_KONDISI + " TEXT, " +
                    COLUMN_JUMLAH + " INTEGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE); // Buat tabel saat pertama kali install
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SARANA); // Hapus tabel jika versi berubah
        onCreate(db);
    }
}