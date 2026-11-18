package com.nti.sessiongame.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nti.sessiongame.R;

public class LobbyActivity extends AppCompatActivity {

    private LinearLayout layoutPlayerInputs;
    private RadioGroup radioGroupPlayers;
    private final int[] playerColors = {
            Color.parseColor("#f71622"),
            Color.parseColor("#ff8426"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#25d8f7"),
            Color.parseColor("#aa21ff"),
            Color.parseColor("#edff26"),
            Color.parseColor("#99f725"),
            Color.parseColor("#f05bf5"),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        layoutPlayerInputs = findViewById(R.id.layoutPlayerInputs);
        radioGroupPlayers = findViewById(R.id.radioGroupPlayers);

        updatePlayerInputs(2);

        // Слушатель
        radioGroupPlayers.setOnCheckedChangeListener((group, checkedId) -> {
            int playerCount = 2;
            if (checkedId == R.id.rb3Players) playerCount = 3;
            else if (checkedId == R.id.rb4Players) playerCount = 4;
            updatePlayerInputs(playerCount);
        });

        // Кнопка начать
        findViewById(R.id.btnStartGame).setOnClickListener(v -> {
            // TODO: Позже здесь собрать имена и цвета, и передать в GameActivity
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    //метод создания интерфейса
    private void updatePlayerInputs(int count) {
        layoutPlayerInputs.removeAllViews();

        for (int i = 0; i < count; i++) {
            LinearLayout playerBlock = new LinearLayout(this);
            playerBlock.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams blockParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            blockParams.setMargins(0, 0, 0, 24);
            playerBlock.setLayoutParams(blockParams);

            TextInputLayout textInputLayout = new TextInputLayout(this);
            textInputLayout.setHint("Имя игрока " + (i + 1));
            textInputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);

            TextInputEditText editText = new TextInputEditText(textInputLayout.getContext());
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            editText.setText("Игрок" + (i + 1));

            textInputLayout.addView(editText);
            playerBlock.addView(textInputLayout);

            TextView colorLabel = new TextView(this);
            colorLabel.setText("Цвет фишки:");
            colorLabel.setTextSize(14);
            colorLabel.setTextColor(getResources().getColor(R.color.text_secondary, getTheme()));
            LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            labelParams.setMargins(0, 8, 0, 8);
            colorLabel.setLayoutParams(labelParams);
            playerBlock.addView(colorLabel);

            LinearLayout colorContainer = new LinearLayout(this);
            colorContainer.setOrientation(LinearLayout.HORIZONTAL);

            for (int c = 0; c < playerColors.length; c++) {
                MaterialButton colorBtn = new MaterialButton(this);
                colorBtn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(playerColors[c]));
                colorBtn.setText("");
                colorBtn.setWidth(80);
                colorBtn.setHeight(70);
                colorBtn.setStrokeWidth(4);
                colorBtn.setStrokeColor(android.content.res.ColorStateList.valueOf(Color.parseColor("#555555")));
                colorBtn.setShapeAppearanceModel(
                        colorBtn.getShapeAppearanceModel().toBuilder()
                                .setAllCornerSizes(1000f)
                                .build()
                );

                int finalC = c;
                colorBtn.setOnClickListener(v -> {
                    //TODO: Визуально показываем, что цвет выбран (например, меняем обводку)
                    // Пока запоминаем в теге
                    playerBlock.setTag(playerColors[finalC]);
                });

                LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(80, 80);
                btnParams.setMargins(0, 0, 16, 0);
                colorBtn.setLayoutParams(btnParams);

                colorContainer.addView(colorBtn);
            }
            playerBlock.addView(colorContainer);

            layoutPlayerInputs.addView(playerBlock);
        }
    }
}