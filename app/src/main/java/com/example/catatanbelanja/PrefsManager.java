
package com.example.catatanbelanja;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManager {

    private static final String PREF_NAME =
            "catatan_belanja_prefs";

    private static final String KEY_NAMA =
            "nama_pengguna";

    private static final String KEY_TEMA =
            "tema_aplikasi";

    private SharedPreferences preferences;

    public PrefsManager(Context context) {

        preferences = context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
        );
    }

    public void simpanNama(String nama) {

        preferences.edit()
                .putString(KEY_NAMA, nama)
                .apply();
    }

    public String ambilNama() {

        return preferences.getString(
                KEY_NAMA,
                "Pengguna"
        );
    }

    public void simpanTema(boolean gelap) {

        preferences.edit()
                .putBoolean(KEY_TEMA, gelap)
                .apply();
    }

    public boolean ambilTema() {

        return preferences.getBoolean(
                KEY_TEMA,
                false
        );
    }
}
