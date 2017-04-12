package com.example.krzysztof.quiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
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
    TextView pytanie;
    RadioButton odp1,odp2,odp3,odp4;
    int wskaznik_odpowiedzi=0,wskaznik_pytania=0,Ile_pytan,odpowiedz=0,poprawne=0;;
    String [] odpowiedzi,pytania;
    int [] odpowiedzi_poprawne;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pytanie);

        Intent intent = getIntent();
        TextView tytul_quiz = (TextView) findViewById(R.id.quiz_tytul);

        odp1 = (RadioButton) findViewById(R.id.odp1);
        odp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Obsluga_klikniecia();
                odznacz();
            }
        });
        odp2 = (RadioButton) findViewById(R.id.odp2);
        odp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Obsluga_klikniecia();
                odznacz();
            }
        });
        odp3 = (RadioButton) findViewById(R.id.odp3);
        odp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Obsluga_klikniecia();
                odznacz();
            }
        });
        odp4 = (RadioButton) findViewById(R.id.odp4);
        odp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Obsluga_klikniecia();
                odznacz();
            }
        });


         pytanie = (TextView) findViewById(R.id.pytanie);

        tytul_quiz.setText(intent.getStringExtra("tytul"));

        url="http://quiz.o2.pl/api/v1/quiz/"+intent.getStringExtra("id")+"/0";

        new Polaczenie().execute();

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
            int j=0;
            if (StringJson != null) {
                try {
                  JSONObject  JSONObiekt = new JSONObject(StringJson);
                    JSONArray tablica_pytań = new JSONArray(JSONObiekt.getString("questions"));
                    Ile_pytan=tablica_pytań.length();
                    pytania = new String[tablica_pytań.length()];
                    odpowiedzi_poprawne = new int[tablica_pytań.length()];
                    odpowiedzi = new String[tablica_pytań.length()*4];
                    for (int i=0;i<tablica_pytań.length();i++)
                    {JSONObject pomoc = new JSONObject(tablica_pytań.get(i).toString());
                        pytania[i]=pomoc.getString("text");
                        odpowiedzi_poprawne[i]= 1 + (int)(Math.random() * 4);
                        JSONArray tablicapomoc = pomoc.getJSONArray("answers");
                        for (int k=0;k<tablicapomoc.length();k++)
                        {
                            JSONObject objektpomoc = new JSONObject(tablicapomoc.get(k).toString());
                            odpowiedzi[j]=objektpomoc.getString("text");
                            j++;
                        }
                    }

                    for (int i=0;i<odpowiedzi.length;i++)
                    {
                        Log.e("Pytania","Tablica: "+odpowiedzi[i]);
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
                Obsluga_klikniecia();

        }
    }

    public void Obsluga_klikniecia()
    {

        if(wskaznik_pytania<Ile_pytan) {
            pytanie.setText(pytania[0 + wskaznik_pytania]);
            odp1.setText(odpowiedzi[0 + wskaznik_odpowiedzi]);
            odp2.setText(odpowiedzi[1 + wskaznik_odpowiedzi]);
            odp3.setText(odpowiedzi[2 + wskaznik_odpowiedzi]);
            odp4.setText(odpowiedzi[3 + wskaznik_odpowiedzi]);
            if (odp1.isChecked()) {odpowiedz=1;}
            if (odp2.isChecked()) {odpowiedz=2;}
            if (odp3.isChecked()) {odpowiedz=3;}
            if (odp4.isChecked()) {odpowiedz=4;}

            if( odpowiedz == odpowiedzi_poprawne[0 + wskaznik_pytania])
            {
                poprawne++;
            }
            wskaznik_pytania++;
            wskaznik_odpowiedzi += 4;
        }
        else
        {
            intent = new Intent(Pytanie.this, Wynik.class);
            intent.putExtra("Wynik",(double)(poprawne/odpowiedzi_poprawne.length));

            startActivity(intent);
        }
    }
    public void odznacz()
    {
        odp1.setChecked(false);
        odp2.setChecked(false);
        odp3.setChecked(false);
        odp4.setChecked(false);
    }
}
