
package com.example.catatanbelanja;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class BerandaActivity extends Activity {

    TextView tvNamaBeranda;
    Button btnKeBelanja, btnKePengaturan;

    PrefsManager prefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_beranda);

        tvNamaBeranda = findViewById(R.id.tvNamaBeranda);
        btnKeBelanja = findViewById(R.id.btnKeBelanja);
        btnKePengaturan = findViewById(R.id.btnKePengaturan);

        prefsManager = new PrefsManager(this);

        tampilkanNama();

        btnKeBelanja.setOnClickListener(v -> {
            Intent intent = new Intent(
                    BerandaActivity.this,
                    MainActivity.class
            );

            startActivity(intent);
        });

        btnKePengaturan.setOnClickListener(v -> {
            Intent intent = new Intent(
                    BerandaActivity.this,
                    PengaturanActivity.class
            );

            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        tampilkanNama();
    }

    private void tampilkanNama() {

        String nama = prefsManager.ambilNama();

        tvNamaBeranda.setText("Halo, " + nama + "!");
    }
}
