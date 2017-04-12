package com.example.krzysztof.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Krzyś on 2017-04-12.
 */

public class Wynik extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wynik);
        Intent intent = getIntent();

        TextView gratulacje = (TextView) findViewById(R.id.gratulacje);
        TextView wynik = (TextView) findViewById(R.id.odpowiedz);

        gratulacje.setText("Gratulacje");
        wynik.setText(String.valueOf(intent.getDoubleExtra("Wynik",0)));


        ;

    }
}
