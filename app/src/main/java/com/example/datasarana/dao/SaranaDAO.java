package com.example.datasarana.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.datasarana.DatabaseHelper;
import com.example.datasarana.model.Sarana;

import java.util.ArrayList;
import java.util.List;

public class SaranaDAO {
    private SQLiteDatabase db;

    public SaranaDAO(SQLiteDatabase db) {
        this.db = db;
    }

    // CREATE
    public long insert(Sarana sarana) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAMA, sarana.getNama());
        values.put(DatabaseHelper.COLUMN_JENIS, sarana.getJenis());
        values.put(DatabaseHelper.COLUMN_LOKASI, sarana.getLokasi());
        values.put(DatabaseHelper.COLUMN_KONDISI, sarana.getKondisi());
        values.put(DatabaseHelper.COLUMN_JUMLAH, sarana.getJumlah());
        return db.insert(DatabaseHelper.TABLE_SARANA, null, values);
    }

    // READ ALL
    public List<Sarana> getAll() {
        List<Sarana> list = new ArrayList<>();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_SARANA,
                null, null, null, null, null, null
        );

        while (cursor.moveToNext()) {
            Sarana sarana = new Sarana(
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAMA)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_JENIS)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOKASI)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_KONDISI)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_JUMLAH))
            );
            list.add(sarana);
        }
        cursor.close();
        return list;
    }

    // UPDATE
    public int update(Sarana sarana) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAMA, sarana.getNama());
        values.put(DatabaseHelper.COLUMN_JENIS, sarana.getJenis());
        values.put(DatabaseHelper.COLUMN_LOKASI, sarana.getLokasi());
        values.put(DatabaseHelper.COLUMN_KONDISI, sarana.getKondisi());
        values.put(DatabaseHelper.COLUMN_JUMLAH, sarana.getJumlah());
        return db.update(
                DatabaseHelper.TABLE_SARANA,
                values,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(sarana.getId())}
        );
    }

    // DELETE
    public int delete(int id) {
        return db.delete(
                DatabaseHelper.TABLE_SARANA,
                DatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }
}