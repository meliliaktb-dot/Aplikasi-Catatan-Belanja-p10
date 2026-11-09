
package com.example.catatanbelanja;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class PengaturanActivity extends Activity {

    EditText etNama;
    RadioButton rbTerang, rbGelap;
    Button btnSimpan;

    PrefsManager prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        prefs = new PrefsManager(this);

        etNama = findViewById(R.id.etNama);
        rbTerang = findViewById(R.id.rbTerang);
        rbGelap = findViewById(R.id.rbGelap);
        btnSimpan = findViewById(R.id.btnSimpan);

        etNama.setText(prefs.ambilNama());

        if (prefs.ambilTema()) {
            rbGelap.setChecked(true);
        } else {
            rbTerang.setChecked(true);
        }

        btnSimpan.setOnClickListener(v -> {

            String nama = etNama.getText().toString().trim();

            if (nama.isEmpty()) {
                etNama.setError("Nama harus diisi");
                return;
            }

            prefs.simpanNama(nama);

            boolean temaGelap = rbGelap.isChecked();

            prefs.simpanTema(temaGelap);

            Toast.makeText(
                    this,
                    "Pengaturan berhasil disimpan",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        });
    }
}
