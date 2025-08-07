package com.agus.remidi_mobile1_windarto_2025;

public class Mahasiswa extends Koneksi{
    String URL = "http://api.jateng.online/server.php";
    String url = "";
    String response = "";
    public String tampilMahasiswa() { try {
        url = URL + "?operasi=view"; System.out.println("URL Tampil Mahasiswa: " + url); response = call(url);
    } catch (Exception ignored) {
    }
        return response;
    }

    public String insertMahasiswa(String npm, String nama, String kelas) { nama = nama.replace(" ", "%20");
        try {
            url = URL + "?operasi=insert&npm=" + npm + "&nama=" + nama + "&kelas=" + kelas;
            System.out.println("URL Insert Mahasiswa : " + url); response = call(url);
        } catch (Exception ignored) {
        }
        return response;
    }
    public String getMahasiswaById(int id) { try {
        url = URL + "?operasi=get_mahasiswa_by_id&id=" + id; System.out.println("URL Insert Mahasiswa: " + url); response = call(url);
    } catch (Exception ignored) {
    }
        return response;
    }
    public String updateMahasiswa(int id, String npm, String nama, String kelas) {
        nama = nama.replace(" ", "%20");
        try {
            url = URL + "?operasi=update&id=" + id + "&npm=" + npm + "&nama="
                    + nama + "&kelas=" + kelas;
            System.out.println("URL Insert Biodata : " + url); response = call(url);
        } catch (Exception ignored) {
        }
        return response;
    }

    public void deleteMahasiswa(int id) { try {
        url = URL + "?operasi=delete&id=" + id; System.out.println("URL Hapus Mahasiswa : " + url); response = call(url);
    } catch (Exception ignored) {
    }
    }
}
