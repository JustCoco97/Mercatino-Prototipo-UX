package com.manuel.newmercatino;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Collega il file XML blu e bianco che hai appena creato
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btn_login);
        Button btnSalta = findViewById(R.id.btn_login_salta);

        // Quando clicchi LOGIN
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vai alla schermata principale
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Chiude la login così non torni indietro con il tasto back
            }
        });

        // Quando clicchi SALTA (stessa cosa, entri nell'app)
        btnSalta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}