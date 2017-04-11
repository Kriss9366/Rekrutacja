package com.example.krzysztof.quiz;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Krzyś on 2017-04-11.
 */

public class Ustawienia {
    public Ustawienia(){}
    public String Zrob_polaczenie(String zrodlo)
    {
        String odpowiedz=null;
        try{
            URL url = new URL(zrodlo);
            HttpURLConnection polaczenie = (HttpURLConnection) url.openConnection();
            polaczenie.setRequestMethod("GET");
            // read the response
            InputStream wejscie = new BufferedInputStream(polaczenie.getInputStream());
            odpowiedz = ToString(wejscie);
        } catch (MalformedURLException e) {
            Log.e("Zrob_polaczenie", "MalformedURLException: " + e.getMessage());
        }
        catch (Exception e) {
            Log.e("Zrob_polaczenie", "Exception: " + e.getMessage());
        }

        return odpowiedz;
    }

    private String ToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    /*public Bitmap Zdjecie(String url){
        Bitmap zdjecie = null;

        try {
            byte[] bajty = Base64.decode(Zrob_polaczenie(url), Base64.DEFAULT);
            zdjecie = BitmapFactory.decodeByteArray(bajty, 0,
                    bajty.length);
        }
        catch(Exception e)
        {
            Log.e("ustawienia",e.getMessage());
        }

        return zdjecie;
    }*/
}
