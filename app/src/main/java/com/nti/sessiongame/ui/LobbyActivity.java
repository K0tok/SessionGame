package com.nti.sessiongame.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nti.sessiongame.R;

/** Никита: лобби — имена, цвета фишек, 2–4 игрока. */
public class LobbyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        findViewById(R.id.btnStartGame).setOnClickListener(v ->
                startActivity(new Intent(this, GameActivity.class)));

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}
