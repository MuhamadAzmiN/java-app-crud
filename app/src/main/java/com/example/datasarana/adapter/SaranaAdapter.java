package com.example.datasarana.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.datasarana.R;
import com.example.datasarana.model.Sarana;

import java.util.List;

public class SaranaAdapter extends ArrayAdapter<Sarana> {
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public SaranaAdapter(Context context, List<Sarana> objects, OnItemClickListener listener) {
        super(context, 0, objects);
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Sarana sarana = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_sarana, parent, false);
        }

        TextView tvNama = convertView.findViewById(R.id.tvNama);
        TextView tvJenis = convertView.findViewById(R.id.tvJenis);
        TextView tvLokasi = convertView.findViewById(R.id.tvLokasi);
        TextView tvKondisi = convertView.findViewById(R.id.tvKondisi);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnHapus = convertView.findViewById(R.id.btnHapus);

        tvNama.setText(sarana.getNama());
        tvJenis.setText("Jenis: " + sarana.getJenis());
        tvLokasi.setText("Lokasi: " + sarana.getLokasi());
        tvKondisi.setText("Kondisi: " + sarana.getKondisi() + " Jumlah: " + sarana.getJumlah());

        btnEdit.setOnClickListener(v -> listener.onEditClick(position));
        btnHapus.setOnClickListener(v -> listener.onDeleteClick(position));

        return convertView;
    }
}