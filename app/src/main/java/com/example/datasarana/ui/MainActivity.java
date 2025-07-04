package com.example.datasarana.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.datasarana.DatabaseHelper;
import com.example.datasarana.R;
import com.example.datasarana.adapter.SaranaAdapter;
import com.example.datasarana.dao.SaranaDAO;
import com.example.datasarana.model.Sarana;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SaranaAdapter.OnItemClickListener {
    private ListView listView;
    private DatabaseHelper dbHelper;
    private SaranaDAO saranaDAO;
    private List<Sarana> saranaList;
    private SaranaAdapter adapter;
    private Sarana sarana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
        FloatingActionButton fab = findViewById(R.id.fab);

        // Load data
        refreshData();

        // Tombol tambah
        fab.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, TambahActivity.class));
        });
    }

    private void refreshData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        saranaDAO = new SaranaDAO(db);
        saranaList = saranaDAO.getAll();

        // Gunakan Custom Adapter dengan listener
        adapter = new SaranaAdapter(this, saranaList, this);
        listView.setAdapter(adapter);

        db.close();
    }

    @Override
    public void onEditClick(int position) {
        Sarana selected = saranaList.get(position);
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("SARANA_ID", selected.getId());
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        Sarana selected = saranaList.get(position); // Dapatkan data yang dipilih

        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Hapus data ini?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    SaranaDAO dao = new SaranaDAO(db);
                    int result = dao.delete(selected.getId()); // Gunakan ID dari data yang dipilih

                    if (result > 0) {
                        Toast.makeText(this, "Data dihapus", Toast.LENGTH_SHORT).show();
                        refreshData(); // Segarkan data setelah penghapusan
                    } else {
                        Toast.makeText(this, "Gagal menghapus", Toast.LENGTH_SHORT).show();
                    }
                    db.close(); // Tutup koneksi database
                })
                .setNegativeButton("Tidak", null)
                .show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}