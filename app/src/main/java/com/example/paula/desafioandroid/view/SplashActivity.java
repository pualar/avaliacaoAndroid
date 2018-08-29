package com.example.paula.desafioandroid.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.paula.desafioandroid.R;
import com.example.paula.desafioandroid.adapter.DataBaseAdapter;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Executar algumas regras de negócios
        // enquanto carrega a tela Splash
        // - GPS
        // - Ler Preferência do Usuário
        // - Enviar notificações

        DataBaseAdapter db = new DataBaseAdapter(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                finish();
                startActivity(i);

            }
        },SPLASH_TIME_OUT);

    }
}