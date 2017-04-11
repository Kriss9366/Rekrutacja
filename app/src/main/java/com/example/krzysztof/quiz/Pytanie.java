package com.example.krzysztof.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Krzyś on 2017-04-11.
 */

public class Pytanie extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pytanie);

        Intent intent = getIntent();
        TextView tytul_quiz = (TextView) findViewById(R.id.quiz_tytul);
        tytul_quiz.setText(intent.getStringExtra("tytul"));

        Ustawienia ustawienia = new Ustawienia();
        String StringJson = ustawienia.Zrob_polaczenie("http://quiz.o2.pl/api/v1/quiz/"+intent.getStringExtra("id")+"/0");
        Log.e("Pytanie",StringJson);
    }
}
