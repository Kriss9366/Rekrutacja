package com.example.krzysztof.quiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Krzyś on 2017-04-11.
 */

public class Pytanie extends AppCompatActivity {
    private ProgressDialog pDialog;
    String StringJson,url;
    RadioGroup grupa_odpowiedzi;
    TextView pytanie;
    String [] pytania;
    String [] odpowiedzi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pytanie);

        Intent intent = getIntent();
        TextView tytul_quiz = (TextView) findViewById(R.id.quiz_tytul);
        grupa_odpowiedzi = (RadioGroup) findViewById(R.id.grupa_odpowiedzi);
         pytanie = (TextView) findViewById(R.id.pytanie);

        tytul_quiz.setText(intent.getStringExtra("tytul"));

        url="http://quiz.o2.pl/api/v1/quiz/"+intent.getStringExtra("id")+"/0";

        new Polaczenie().execute();
        for (int i=0;i<pytania.length;i++)
        {
            Log.e("Pytania","Tablica: "+pytania[i]);
        }
    }


    private class Polaczenie extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Pytanie.this);
            pDialog.setMessage("Pobieram. Proszę czekać...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Ustawienia ustawienia = new Ustawienia();
            StringJson = ustawienia.Zrob_polaczenie(url);
            Log.e("Pytanie", "Odpowiedz: " + StringJson);

            if (StringJson != null) {
                try {
                  JSONObject  JSONObiekt = new JSONObject(StringJson);
                    JSONArray tablica_pytań = new JSONArray(JSONObiekt.getString("questions"));
                    for (int i=0;i<tablica_pytań.length();i++)
                    {JSONObject pomoc = new JSONObject(tablica_pytań.get(i).toString());
                        pytania[i]=pomoc.getString("text");
                    }


                } catch (JSONException e) {
                    Log.e("Pytanie", "Problem parsowania pliku JSON" + e.getMessage());
                }
            } else {
                Log.e("Pytanie", "Problem pobierania pliku JSON z serwera");
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            /*ListAdapter adapter = new SimpleAdapter(
                    Listing.this, lista_quiz,
                    R.layout.pola_listy,new String[]{"tytul"},
                    new int[]{R.id.tytul}
            );
            lista.setAdapter(adapter);*/
        }
    }
}
