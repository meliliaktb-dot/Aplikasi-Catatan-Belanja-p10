
package com.example.catatanbelanja;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    EditText etNama;
    EditText etBarang;
    EditText etHarga;

    TextView tvNama;

    Button btnSimpanNama;
    Button btnTambah;
    Button btnTema;

    LinearLayout containerBarang;

    PrefsManager prefsManager;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etNama = findViewById(R.id.etNama);
        etBarang = findViewById(R.id.etBarang);
        etHarga = findViewById(R.id.etHarga);

        tvNama = findViewById(R.id.tvNama);

        btnSimpanNama = findViewById(R.id.btnSimpanNama);
        btnTambah = findViewById(R.id.btnTambah);
        btnTema = findViewById(R.id.btnTema);

        containerBarang = findViewById(R.id.containerBarang);

        prefsManager = new PrefsManager(this);
        databaseHelper = new DatabaseHelper(this);

        // SIMPAN NAMA

        btnSimpanNama.setOnClickListener(v -> {

            String nama = etNama.getText().toString().trim();

            if (nama.isEmpty()) {

                Toast.makeText(
                        this,
                        "Masukkan nama terlebih dahulu",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            prefsManager.simpanNama(nama);

            tampilkanNama();

            Toast.makeText(
                    this,
                    "Nama berhasil disimpan",
                    Toast.LENGTH_SHORT
            ).show();

        });

        // TAMBAH BARANG

        btnTambah.setOnClickListener(v -> {

            String namaBarang =
                    etBarang.getText().toString().trim();

            String hargaText =
                    etHarga.getText().toString().trim();

            if (namaBarang.isEmpty()) {

                etBarang.setError("Nama barang harus diisi");
                return;
            }

            double harga = 0;

            if (!hargaText.isEmpty()) {

                try {

                    harga = Double.parseDouble(hargaText);

                    if (harga < 0) {
                        etHarga.setError("Harga tidak boleh negatif");
                        return;
                    }

                } catch (NumberFormatException e) {

                    etHarga.setError("Harga tidak valid");
                    return;
                }
            }

            boolean berhasil = databaseHelper.tambahBarang(
                    namaBarang,
                    harga
            );

            if (berhasil) {

                etBarang.setText("");
                etHarga.setText("");

                tampilkanBarang();

                Toast.makeText(
                        this,
                        "Barang berhasil ditambahkan",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                Toast.makeText(
                        this,
                        "Barang gagal ditambahkan",
                        Toast.LENGTH_SHORT
                ).show();

            }

        });

        // GANTI TEMA

        btnTema.setOnClickListener(v -> {

            boolean temaGelap =
                    !prefsManager.ambilTema();

            prefsManager.simpanTema(temaGelap);

            terapkanTema();

            Toast.makeText(
                    this,
                    temaGelap
                            ? "Tema gelap dipilih"
                            : "Tema terang dipilih",
                    Toast.LENGTH_SHORT
            ).show();

        });

    }

    @Override
    protected void onResume() {

        super.onResume();

        tampilkanNama();

        terapkanTema();

        tampilkanBarang();

    }

    // TAMPILKAN NAMA

    private void tampilkanNama() {

        String nama = prefsManager.ambilNama();

        tvNama.setText("Halo, " + nama);

        if (etNama != null) {

            if (nama.equals("Pengguna")) {
                etNama.setText("");
            } else {
                etNama.setText(nama);
            }

        }

    }

    // TAMPILKAN BARANG

    private void tampilkanBarang() {

        containerBarang.removeAllViews();

        Cursor cursor = databaseHelper.ambilBarang();

        try {

            while (cursor.moveToNext()) {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id")
                );

                String nama = cursor.getString(
                        cursor.getColumnIndexOrThrow("nama")
                );

                double harga = cursor.getDouble(
                        cursor.getColumnIndexOrThrow("harga")
                );

                int status = cursor.getInt(
                        cursor.getColumnIndexOrThrow("dibeli")
                );

                // BARIS BARANG

                LinearLayout baris = new LinearLayout(this);

                baris.setOrientation(
                        LinearLayout.HORIZONTAL
                );

                baris.setGravity(
                        android.view.Gravity.CENTER_VERTICAL
                );

                baris.setPadding(
                        8,
                        12,
                        8,
                        12
                );

                // CHECKBOX BARANG

                CheckBox checkBox = new CheckBox(this);

                checkBox.setText(
                        nama + " - Rp " + harga
                );

                checkBox.setChecked(status == 1);

                baris.addView(
                        checkBox,
                        new LinearLayout.LayoutParams(
                                0,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1
                        )
                );

                // TOMBOL HAPUS

                Button btnHapus = new Button(this);

                btnHapus.setText("Hapus");

                baris.addView(btnHapus);

                // UBAH STATUS BARANG

                checkBox.setOnCheckedChangeListener(
                        (buttonView, isChecked) -> {

                            databaseHelper.ubahStatus(
                                    id,
                                    isChecked
                            );

                        }
                );

                // HAPUS BARANG

                btnHapus.setOnClickListener(v -> {

                    databaseHelper.hapusBarang(id);

                    tampilkanBarang();

                    Toast.makeText(
                            this,
                            "Barang berhasil dihapus",
                            Toast.LENGTH_SHORT
                    ).show();

                });

                containerBarang.addView(baris);

            }

        } finally {

            cursor.close();

        }

    }

    // TEMA TERANG DAN GELAP

    private void terapkanTema() {

        boolean gelap = prefsManager.ambilTema();

        int warnaLatar = gelap
                ? Color.parseColor("#202124")
                : Color.parseColor("#F4F7F5");

        int warnaTeks = gelap
                ? Color.WHITE
                : Color.parseColor("#202124");

        containerBarang.setBackgroundColor(warnaLatar);

        tvNama.setTextColor(warnaTeks);

    }

}
