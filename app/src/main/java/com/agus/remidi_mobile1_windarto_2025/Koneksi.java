package com.agus.remidi_mobile1_windarto_2025;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
public class Koneksi {
    public String call(String url) {
        int BUFFER_SIZE = 2000;
        InputStream in;
        try {
            in = OpenHttpConnection(url);
        } catch (IOException e) {
            android.util.Log.e("Koneksi", "Connection error: " + e.getMessage(), e);
            return "";
        }
        InputStreamReader isr = new InputStreamReader(in);
        int charRead; StringBuilder str = new StringBuilder();
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
            while ((charRead = isr.read(inputBuffer)) > 0) {
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                str.append(readString);
                inputBuffer = new char[BUFFER_SIZE];
            }
            in.close();
        } catch (IOException e) {
            android.util.Log.e("Koneksi", "Read error: " + e.getMessage(), e);
            return "";
        }
        return str.toString();
    }
    private InputStream OpenHttpConnection(String url) throws IOException {
        InputStream in = null;
        int response;
        URL url1 = new URL(url);
        URLConnection conn = url1.openConnection();
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not An Http Connection");
        try {
            HttpURLConnection httpconn = (HttpURLConnection) conn;
            httpconn.setAllowUserInteraction(false);
            httpconn.setInstanceFollowRedirects(true);
            httpconn.setRequestMethod("GET");
            httpconn.connect();
            response = httpconn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpconn.getInputStream();
            }
        } catch (Exception e) {
            throw new IOException("Error connecting2");
        }
        return in;
    }
}