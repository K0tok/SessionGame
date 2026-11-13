package com.nti.sessiongame.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nti.sessiongame.R;

/** Никита: полный текст правил. */
public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}
