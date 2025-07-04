package com.example.datasarana.ui;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datasarana.R;
import com.example.datasarana.DatabaseHelper;
import com.example.datasarana.dao.SaranaDAO;
import com.example.datasarana.model.Sarana;

public class TambahActivity extends AppCompatActivity {
    private EditText etNama, etJenis, etLokasi, etKondisi, etJumlah;
    private Button btnSimpan;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        dbHelper = new DatabaseHelper(this);

        etNama = findViewById(R.id.etNama);
        etJenis = findViewById(R.id.etJenis);
        etLokasi = findViewById(R.id.etLokasi);
        etKondisi = findViewById(R.id.etKondisi);
        etJumlah = findViewById(R.id.etJumlah);
        btnSimpan = findViewById(R.id.btnSimpan);

        btnSimpan.setOnClickListener(v -> simpanData());
    }

    private void simpanData() {
        String nama = etNama.getText().toString().trim();
        String jenis = etJenis.getText().toString().trim();
        String lokasi = etLokasi.getText().toString().trim();
        String kondisi = etKondisi.getText().toString().trim();
        String strJumlah = etJumlah.getText().toString().trim();

        if (nama.isEmpty() || jenis.isEmpty() || lokasi.isEmpty() || kondisi.isEmpty() || strJumlah.isEmpty()) {
            Toast.makeText(this, "Harap isi semua field!", Toast.LENGTH_SHORT).show();
            return;
        }

        int jumlah = Integer.parseInt(strJumlah);
        Sarana sarana = new Sarana(0, nama, jenis, lokasi, kondisi, jumlah);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SaranaDAO dao = new SaranaDAO(db);
        long result = dao.insert(sarana);

        if (result > 0) {
            Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}