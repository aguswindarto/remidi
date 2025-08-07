package com.agus.remidi_mobile1_windarto_2025;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    // Inisialisasi Objek + Variabel + Class
    Mahasiswa mahasiswa = new Mahasiswa();

    TableLayout tbMahasiswa;

    Button btTambahMahasiswa, btRefreshDataMahasiswa;

    ArrayList<Button> buttonEdit = new ArrayList<>();
    ArrayList<Button> buttonDelete = new ArrayList<>();

    JSONArray arrayMahasiswa;

    @SuppressLint("UnsafeIntentLaunch")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Pemberian Nama komponen
        tbMahasiswa = findViewById(R.id.tbMahasiswa);
        btTambahMahasiswa = findViewById(R.id.btTambahMahasiswa);
        btRefreshDataMahasiswa = findViewById(R.id.btRefreshDataMahasiswa);

        tampildataMahasiswa();
        btTambahMahasiswa.setOnClickListener(view -> tambahMahasiswa());

        btRefreshDataMahasiswa.setOnClickListener(view -> {
            tbMahasiswa.removeAllViews();
            buttonEdit.clear();
            buttonDelete.clear();
            tampildataMahasiswa();
        });
    }

    // Tampil data mahasiswa
    @SuppressLint("SetTextI18n")
    public void tampildataMahasiswa() {

        // Menampilkan indikator loading
        final Toast loadingToast = Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT);
        loadingToast.show();

        TableRow barisTabel = new TableRow(this);
        barisTabel.setBackgroundColor(Color.CYAN);
        //barisTabel.setPadding(5, 1, 5, 1);

        // Memberi ID Header Tabel
        TextView viewHeaderNpm = new TextView(this);
        TextView viewHeaderNama = new TextView(this);
        TextView viewHeaderKelas = new TextView(this);
        TextView viewHeaderAction = new TextView(this);

        // Memberi Nama kolom HEADER
        viewHeaderNpm.setText("NPM");
        viewHeaderNama.setText("NAMA");
        viewHeaderKelas.setText("KELAS");
        viewHeaderAction.setText("ACTION");

        // Memberi warna hitam di judul tabel
        viewHeaderNpm.setTextColor(Color.BLACK);
        viewHeaderNama.setTextColor(Color.BLACK);
        viewHeaderKelas.setTextColor(Color.BLACK);
        viewHeaderAction.setTextColor(Color.BLACK);

        // Memberi align center di judul tabel
        viewHeaderNpm.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        viewHeaderNama.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        viewHeaderKelas.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        viewHeaderAction.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        // Padding Header
        viewHeaderNpm.setPadding(5, 1, 5, 1);
        viewHeaderNama.setPadding(5, 1, 5, 1);
        viewHeaderKelas.setPadding(5, 1, 5, 1);
        viewHeaderAction.setPadding(5, 1, 5, 1);

        // Tambahkan header ke baris tabel
        barisTabel.addView(viewHeaderNpm);
        barisTabel.addView(viewHeaderNama);
        barisTabel.addView(viewHeaderKelas);
        barisTabel.addView(viewHeaderAction);

        // Tambahkan baris header ke tabel utama
        tbMahasiswa.addView(barisTabel,
                new TableLayout.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                )
        );

        try {
            arrayMahasiswa = new JSONArray(mahasiswa.tampilMahasiswa());

            // Menampilkan Header Kolom
            for (int i = 0; i < arrayMahasiswa.length(); i++) {
                JSONObject jsonChildNode = arrayMahasiswa.getJSONObject(i);

                // Ambil data dari nama tabel database
                String id = jsonChildNode.optString("id");
                String npm = jsonChildNode.optString("npm");
                String nama = jsonChildNode.optString("nama");
                String kelas = jsonChildNode.optString("kelas");

                System.out.println("id : " + id);
                System.out.println("npm : " + npm);
                System.out.println("nama : " + nama);
                System.out.println("kelas : " + kelas);

                barisTabel = new TableRow(this);

                if (i % 2 == 0) {
                    barisTabel.setBackgroundColor(Color.LTGRAY);
                }

                // TextView untuk NPM
                TextView viewNpm = new TextView(this);
                viewNpm.setText(npm);
                viewNpm.setPadding(5, 1, 5, 1);
                viewNpm.setTextColor(Color.BLACK);
                barisTabel.addView(viewNpm);

                // TextView untuk Nama
                TextView viewNama = new TextView(this);
                viewNama.setText(nama);
                viewNama.setPadding(5, 1, 5, 1);
                viewNama.setTextColor(Color.BLACK);
                barisTabel.addView(viewNama);

                // TextView untuk Kelas
                TextView viewKelas = new TextView(this);
                viewKelas.setText(kelas);
                viewKelas.setPadding(5, 1, 5, 1);
                viewKelas.setTextColor(Color.BLACK);
                barisTabel.addView(viewKelas);

                // Membuat Button Edit pada Baris
                buttonEdit.add(i, new Button(this));
                buttonEdit.get(i).setId(Integer.parseInt(id));
                buttonEdit.get(i).setTag("Edit");
                buttonEdit.get(i).setText("Edit");
                buttonEdit.get(i).setOnClickListener(this);
                barisTabel.addView(buttonEdit.get(i));

                // Membuat Button Delete pada Baris
                buttonDelete.add(i, new Button(this));
                buttonDelete.get(i).setId(Integer.parseInt(id));
                buttonDelete.get(i).setTag("Delete");
                buttonDelete.get(i).setText("Delete");
                buttonDelete.get(i).setOnClickListener(this);
                barisTabel.addView(buttonDelete.get(i));

                tbMahasiswa.addView(barisTabel,
                        new TableLayout.LayoutParams(
                                TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT
                        )
                );
            }
        } catch (JSONException e) {
            Log.e("MainActivity", "JSON error in tampildataMahasiswa", e);
        }
        // Sembunyikan indikator loading setelah selesai load data
        loadingToast.cancel();
    }

    // Menampilkan dialog konfirmasi sebelum menghapus data
    private void showDeleteConfirmationDialog(final int id) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    mahasiswa.deleteMahasiswa(id);
                    Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                    refreshMahasiswaTable();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // Refresh data mahasiswa tanpa loading ulang activity
    private void refreshMahasiswaTable() {
        tbMahasiswa.removeAllViews();
        buttonEdit.clear();
        buttonDelete.clear();
        tampildataMahasiswa();
    }

    // Hapus Mahasiswa berdasarkan ID
    @SuppressLint("UnsafeIntentLaunch")
    public void deleteMahasiswa(int id) {
        showDeleteConfirmationDialog(id);
    }

    // Ambil data mahasiswa berdasarkan ID
    @SuppressLint("UnsafeIntentLaunch")
    public void getDataByID(int id) {
        String npmEdit = null;
        String namaEdit = null;
        String kelasEdit = null;
        JSONArray arrayPersonal;

        try {
            arrayPersonal = new JSONArray(mahasiswa.getMahasiswaById(id));

            for (int i = 0; i < arrayPersonal.length(); i++) {
                JSONObject jsonChildNode = arrayPersonal.getJSONObject(i);
                npmEdit = jsonChildNode.optString("npm");
                namaEdit = jsonChildNode.optString("nama");
                kelasEdit = jsonChildNode.optString("kelas");
            }

        } catch (JSONException e) {
            Log.e("MainActivity", "JSON error in getDataByID", e);
        }

        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        TextView viewId = new TextView(this);
        viewId.setText(String.valueOf(id));
        viewId.setTextColor(Color.TRANSPARENT);
        layoutInput.addView(viewId);

        final EditText editNpm = new EditText(this);
        editNpm.setText(npmEdit);
        layoutInput.addView(editNpm);

        final EditText editNama = new EditText(this);
        editNama.setText(namaEdit);
        layoutInput.addView(editNama);

        final EditText editKelas = new EditText(this);
        editKelas.setText(kelasEdit);
        layoutInput.addView(editKelas);

        AlertDialog.Builder builderEditMahasiswa = new AlertDialog.Builder(this);
        // builderEditMahasiswa.setIcon(R.drawable.batagrams);
        builderEditMahasiswa.setTitle("Update Mahasiswa");
        builderEditMahasiswa.setView(layoutInput);
        builderEditMahasiswa.setPositiveButton("Update", (dialog, which) -> {
            String npm = editNpm.getText().toString();
            String nama = editNama.getText().toString();
            String kelas = editKelas.getText().toString();

            System.out.println("Npm : " + npm + " Nama : " + nama + " Kelas : " + kelas);

            String laporan = mahasiswa.updateMahasiswa(Integer.parseInt(viewId.getText().toString()), editNpm.getText().toString(), editNama.getText().toString(), editKelas.getText().toString());
            Toast.makeText(MainActivity.this, laporan, Toast.LENGTH_SHORT).show();

            // refresh data mahasiswa
            refreshMahasiswaTable();
        });

        builderEditMahasiswa.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builderEditMahasiswa.show();
    }

    // Metode tambah Mahasiswa
    @SuppressLint("UnsafeIntentLaunch")
    public void tambahMahasiswa() {
        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        final EditText editNpm = new EditText(this);
        editNpm.setHint("Npm");
        layoutInput.addView(editNpm);

        final EditText editNama = new EditText(this);
        editNama.setHint("Nama");
        layoutInput.addView(editNama);

        final EditText editKelas = new EditText(this);
        editKelas.setHint("Kelas");
        layoutInput.addView(editKelas);

        AlertDialog.Builder builderInsertBiodata = new AlertDialog.Builder(this);
        builderInsertBiodata.setTitle("Insert Mahasiswa");
        builderInsertBiodata.setView(layoutInput);
        builderInsertBiodata.setPositiveButton("Insert", (dialog, which) -> {
            String npm = editNpm.getText().toString();
            String nama = editNama.getText().toString();
            String kelas = editKelas.getText().toString();

            System.out.println("Npm : " + npm + " Nama : " + nama + " Kelas : " + kelas);

            String laporan = mahasiswa.insertMahasiswa(npm, nama, kelas);
            Toast.makeText(MainActivity.this, laporan, Toast.LENGTH_SHORT).show();

            // refreesh data mahasiswa
            refreshMahasiswaTable();
        });

        builderInsertBiodata.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builderInsertBiodata.show();
    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < buttonEdit.size(); i++) {
            if (view.getId() == buttonEdit.get(i).getId() && view.getTag().toString().trim().equals("Edit")) {
                int id = buttonEdit.get(i).getId();
                getDataByID(id);
            } else if (view.getId() == buttonDelete.get(i).getId() && view.getTag().toString().trim().equals("Delete")) {
                int id = buttonDelete.get(i).getId();
                deleteMahasiswa(id);
            }
        }
    }
}
