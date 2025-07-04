package com.example.datasarana.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datasarana.DatabaseHelper;
import com.example.datasarana.R;
import com.example.datasarana.dao.SaranaDAO;
import com.example.datasarana.model.Sarana;

public class EditActivity extends AppCompatActivity {
    private EditText etNama, etJenis, etLokasi, etKondisi, etJumlah;
    private Button btnUpdate, btnDelete;
    private DatabaseHelper dbHelper;
    private Sarana sarana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        dbHelper = new DatabaseHelper(this);
        int saranaId = getIntent().getIntExtra("SARANA_ID", -1);

        if (saranaId == -1) {
            Toast.makeText(this, "Data tidak valid", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        loadData(saranaId);
    }

    private void initViews() {
        etNama = findViewById(R.id.etNama);
        etJenis = findViewById(R.id.etJenis);
        etLokasi = findViewById(R.id.etLokasi);
        etKondisi = findViewById(R.id.etKondisi);
        etJumlah = findViewById(R.id.etJumlah);
        btnUpdate = findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(v -> updateData());
    }

    private void loadData(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SaranaDAO dao = new SaranaDAO(db);
        sarana = dao.getAll().stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);

        if (sarana != null) {
            etNama.setText(sarana.getNama());
            etJenis.setText(sarana.getJenis());
            etLokasi.setText(sarana.getLokasi());
            etKondisi.setText(sarana.getKondisi());
            etJumlah.setText(String.valueOf(sarana.getJumlah()));
        } else {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateData() {
        String nama = etNama.getText().toString().trim();
        String jenis = etJenis.getText().toString().trim();
        String lokasi = etLokasi.getText().toString().trim();
        String kondisi = etKondisi.getText().toString().trim();
        String strJumlah = etJumlah.getText().toString().trim();

        if (nama.isEmpty() || jenis.isEmpty() || lokasi.isEmpty() || kondisi.isEmpty() || strJumlah.isEmpty()) {
            Toast.makeText(this, "Harap isi semua field!", Toast.LENGTH_SHORT).show();
            return;
        }

        sarana.setNama(nama);
        sarana.setJenis(jenis);
        sarana.setLokasi(lokasi);
        sarana.setKondisi(kondisi);
        sarana.setJumlah(Integer.parseInt(strJumlah));

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SaranaDAO dao = new SaranaDAO(db);
        int result = dao.update(sarana);

        if (result > 0) {
            Toast.makeText(this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Gagal mengupdate data", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteData() {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Hapus data ini?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    SaranaDAO dao = new SaranaDAO(db);
                    int result = dao.delete(sarana.getId());

                    if (result > 0) {
                        Toast.makeText(this, "Data dihapus", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Gagal menghapus", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Tidak", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}