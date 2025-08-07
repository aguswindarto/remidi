<?php
$server = "localhost";
$username = "api_agus";
$password = "DecKPdavpsLr9HKw";
$database = "api_db_android_1";
$koneksi = mysqli_connect($server, $username, $password, $database);
@$operasi = $_GET['operasi'];
        switch ($operasi) {
        case "view":
                /* Source code untuk Menampilkan mahasiswa */
                $query_tampil_mahasiswa = mysqli_query($koneksi, "SELECT * FROM mahasiswa")
                or die(mysqli_error());
                $data_array = array();
                while ($data = mysqli_fetch_assoc($query_tampil_mahasiswa)) {
                $data_array[] = $data;
                }
                echo json_encode($data_array);
        break;
        case "insert":
                /* Source code untuk Insert data */
                @$npm = $_GET['npm'];
                @$nama = $_GET['nama'];
                @$kelas = $_GET['kelas'];
                $query_insert_data = mysqli_query($koneksi, "INSERT INTO mahasiswa (npm,
                nama, kelas) VALUES('$npm', '$nama', '$kelas')");
                if ($query_insert_data) {
                echo "Data Berhasil Disimpan";
                } else {
                echo "Error Inser mahasiswa " . mysqli_error();
                }
        break;
        case "get_mahasiswa_by_id":
                /* Source code untuk Edit data dan mengirim data berdasarkan id yang
                diminta */
                @$id = $_GET['id'];
                $query_tampil_mahasiswa = mysqli_query($koneksi, "SELECT * FROM mahasiswa
                WHERE id='$id'") or die(mysqli_error());
                $data_array = array();
                $data_array = mysqli_fetch_assoc($query_tampil_mahasiswa);
                echo "[" . json_encode($data_array) . "]";
        break;
        case "update":
                /* Source code untuk Updatedata */
                @$npm = $_GET['npm'];
                @$nama = $_GET['nama'];
                @$kelas = $_GET['kelas'];
                @$id = $_GET['id'];
                $query_update_mahasiswa = mysqli_query($koneksi, "UPDATE mahasiswa SET
                npm='$npm', nama='$nama', kelas='$kelas' WHERE id='$id'");
                if ($query_update_mahasiswa) {
                echo "Update Data Berhasil";
                } else {
                echo mysqli_error();
                }
        break;
        case "delete":
                /* Source code untuk Deletedata */
                @$id = $_GET['id'];
                $query_delete_mahasiswa = mysqli_query($koneksi, "DELETE FROM mahasiswa
                WHERE id='$id'");
                if ($query_delete_mahasiswa) {
                echo "Delete Data Berhasil";
                } else {
                echo mysqli_error();
                }
                break;
                default:
        break;
        }
?>