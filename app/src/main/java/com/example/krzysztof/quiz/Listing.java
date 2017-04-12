package com.example.krzysztof.quiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Listing extends AppCompatActivity {
    ArrayList<HashMap<String,String>> lista_quiz;
    private ProgressDialog pDialog;
    ListView lista;
    Bitmap mapa;
    JSONArray tablica_quizów;
    String url;
    String StringJson,StringJson2=null;
    boolean wcisk=false;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        url = "http://quiz.o2.pl/api/v1/quizzes/0/100";
        lista_quiz = new ArrayList<>();
        lista = (ListView) findViewById(R.id.lista);
         intent = new Intent(Listing.this, Pytanie.class);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                wcisk = true;
                try {
                        JSONObject quiz = new JSONObject(tablica_quizów.get(position).toString());
                        intent.putExtra("tytul",quiz.getString("content"));
                        intent.putExtra("id",quiz.getString("id"));
                        startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        new Polaczenie().execute();
    }

    private class Polaczenie extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Listing.this);
            pDialog.setMessage("Pobieram. Proszę czekać...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Ustawienia ustawienia = new Ustawienia();
                StringJson = ustawienia.Zrob_polaczenie(url);
                Log.e("Listing", "Odpowiedz: " + StringJson);

                if (StringJson != null) {
                    try {
                        JSONObject JSONObiekt = new JSONObject(StringJson);

                        tablica_quizów = JSONObiekt.getJSONArray("items");
                        for (int i = 0; i < tablica_quizów.length(); i++) {
                            JSONObject quiz = new JSONObject(tablica_quizów.getString(i));

                            HashMap<String, String> tytuly = new HashMap<>();

                            tytuly.put("tytul", quiz.getString("content"));

                            lista_quiz.add(tytuly);

                            JSONObject zdjecie = new JSONObject(quiz.getString("mainPhoto"));
                            //mapa =  ustawienia.Zdjecie(zdjecie.getString("url"));
                        }

                    } catch (JSONException e) {
                        Log.e("Listing", "Problem parsowania pliku JSON" + e.getMessage());
                    }
                } else {
                    Log.e("Listing", "Problem pobierania pliku JSON z serwera");
                }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            ListAdapter adapter = new SimpleAdapter(
                    Listing.this, lista_quiz,
                    R.layout.pola_listy,new String[]{"tytul"},
                    new int[]{R.id.tytul}
            );
            lista.setAdapter(adapter);
        }
    }
}
