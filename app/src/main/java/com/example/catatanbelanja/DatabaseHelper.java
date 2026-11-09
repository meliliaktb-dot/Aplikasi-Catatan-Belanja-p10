
package com.example.catatanbelanja;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME =
            "belanja.db";

    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {

        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE barang (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nama TEXT NOT NULL, " +
                        "harga REAL DEFAULT 0, " +
                        "dibeli INTEGER DEFAULT 0)"
        );
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        // Database belum membutuhkan perubahan struktur.
    }

    public boolean tambahBarang(
            String nama,
            double harga) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("nama", nama);
        values.put("harga", harga);
        values.put("dibeli", 0);

        long hasil = db.insert(
                "barang",
                null,
                values
        );

        return hasil != -1;
    }

    public Cursor ambilBarang() {

        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM barang ORDER BY id DESC",
                null
        );
    }

    public void ubahStatus(
            int id,
            boolean dibeli) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(
                "dibeli",
                dibeli ? 1 : 0
        );

        db.update(
                "barang",
                values,
                "id=?",
                new String[]{String.valueOf(id)}
        );
    }

    public void hapusBarang(int id) {

        SQLiteDatabase db = getWritableDatabase();

        db.delete(
                "barang",
                "id=?",
                new String[]{String.valueOf(id)}
        );
    }
}
