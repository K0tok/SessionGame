package com.nti.sessiongame;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nti.sessiongame.ui.LobbyActivity;
import com.nti.sessiongame.ui.RulesActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnNewGame).setOnClickListener(v ->
                startActivity(new Intent(this, LobbyActivity.class)));

        findViewById(R.id.btnRules).setOnClickListener(v ->
                startActivity(new Intent(this, RulesActivity.class)));
    }
}
