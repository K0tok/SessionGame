package com.nti.sessiongame.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nti.sessiongame.R;

import java.util.ArrayList;
import java.util.List;

public class LobbyActivity extends AppCompatActivity {

    public static final String EXTRA_PLAYER_NAMES = "EXTRA_PLAYER_NAMES";
    public static final String EXTRA_PLAYER_COLORS = "EXTRA_PLAYER_COLORS";

    private LinearLayout layoutPlayerInputs;
    private RadioGroup radioGroupPlayers;
    private final List<String> playerNamesList = new ArrayList<>();
    private final List<Integer> playerColorsList = new ArrayList<>();
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
    private final boolean[] usedColors = new boolean[playerColors.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        layoutPlayerInputs = findViewById(R.id.layoutPlayerInputs);
        radioGroupPlayers = findViewById(R.id.radioGroupPlayers);

        updatePlayerInputs(2);

        radioGroupPlayers.setOnCheckedChangeListener((group, checkedId) -> {
            int playerCount = 2;
            if (checkedId == R.id.rb3Players) {
                playerCount = 3;
            } else if (checkedId == R.id.rb4Players) {
                playerCount = 4;
            }
            updatePlayerInputs(playerCount);
        });

        findViewById(R.id.btnStartGame).setOnClickListener(v -> {
            if (!allColorsSelected()) {
                Toast.makeText(this, "Выберите цвет для каждого игрока!", Toast.LENGTH_SHORT).show();
                return;
            }

            String[] namesArray = playerNamesList.toArray(new String[0]);
            int[] colorsArray = new int[playerColorsList.size()];
            for (int i = 0; i < playerColorsList.size(); i++) {
                colorsArray[i] = playerColorsList.get(i);
            }

            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra(EXTRA_PLAYER_NAMES, namesArray);
            intent.putExtra(EXTRA_PLAYER_COLORS, colorsArray);
            startActivity(intent);
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private boolean allColorsSelected() {
        if (playerColorsList.size() != playerNamesList.size()) {
            return false;
        }
        for (Integer color : playerColorsList) {
            if (color == null) {
                return false;
            }
        }
        return true;
    }

    private void updatePlayerInputs(int count) {
        layoutPlayerInputs.removeAllViews();
        playerNamesList.clear();
        playerColorsList.clear();
        for (int i = 0; i < usedColors.length; i++) {
            usedColors[i] = false;
        }

        for (int i = 0; i < count; i++) {
            playerNamesList.add("Игрок" + (i + 1));
            playerColorsList.add(null);

            LinearLayout playerBlock = new LinearLayout(this);
            playerBlock.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams blockParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            blockParams.setMargins(0, 0, 0, 24);
            playerBlock.setLayoutParams(blockParams);

            GradientDrawable defaultBorder = new GradientDrawable();
            defaultBorder.setColor(Color.TRANSPARENT);
            defaultBorder.setStroke(0, Color.TRANSPARENT);
            defaultBorder.setCornerRadius(16f);
            playerBlock.setBackground(defaultBorder);
            playerBlock.setPadding(16, 16, 16, 16);

            final int playerIndex = i;

            TextInputLayout textInputLayout = new TextInputLayout(this);
            textInputLayout.setHint("Имя игрока " + (i + 1));
            textInputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);

            TextInputEditText editText = new TextInputEditText(textInputLayout.getContext());
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            editText.setText(playerNamesList.get(i));
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    playerNamesList.set(playerIndex, s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

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
                colorBtn.setStrokeWidth(4);
                colorBtn.setStrokeColor(android.content.res.ColorStateList.valueOf(Color.parseColor("#555555")));
                colorBtn.setShapeAppearanceModel(
                        colorBtn.getShapeAppearanceModel().toBuilder()
                                .setAllCornerSizes(1000f)
                                .build()
                );

                final int colorIndex = c;
                colorBtn.setOnClickListener(v -> selectColor(playerIndex, colorIndex, playerBlock));

                LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(80, 80);
                btnParams.setMargins(0, 0, 16, 0);
                colorBtn.setLayoutParams(btnParams);
                colorContainer.addView(colorBtn);
            }

            playerBlock.addView(colorContainer);
            layoutPlayerInputs.addView(playerBlock);
        }

        refreshColorButtonsVisibility();
    }

    private void selectColor(int playerIndex, int colorIndex, LinearLayout playerBlock) {
        Integer current = playerColorsList.get(playerIndex);
        if (usedColors[colorIndex] && (current == null || current != playerColors[colorIndex])) {
            return;
        }

        Integer previousColor = playerColorsList.get(playerIndex);
        if (previousColor != null) {
            int previousIndex = indexOfColor(previousColor);
            if (previousIndex >= 0) {
                usedColors[previousIndex] = false;
            }
        }

        playerColorsList.set(playerIndex, playerColors[colorIndex]);
        usedColors[colorIndex] = true;

        GradientDrawable border = new GradientDrawable();
        border.setColor(Color.TRANSPARENT);
        border.setStroke(8, playerColors[colorIndex]);
        border.setCornerRadius(16f);
        playerBlock.setBackground(border);

        refreshColorButtonsVisibility();
    }

    private int indexOfColor(int color) {
        for (int i = 0; i < playerColors.length; i++) {
            if (playerColors[i] == color) {
                return i;
            }
        }
        return -1;
    }

    private void refreshColorButtonsVisibility() {
        int playerCount = layoutPlayerInputs.getChildCount();
        for (int p = 0; p < playerCount; p++) {
            LinearLayout playerBlock = (LinearLayout) layoutPlayerInputs.getChildAt(p);
            LinearLayout colorContainer = (LinearLayout) playerBlock.getChildAt(2);
            Integer selected = playerColorsList.get(p);

            for (int c = 0; c < playerColors.length; c++) {
                MaterialButton colorBtn = (MaterialButton) colorContainer.getChildAt(c);
                boolean takenByOther = usedColors[c] && (selected == null || selected != playerColors[c]);
                colorBtn.setVisibility(takenByOther ? View.GONE : View.VISIBLE);
            }
        }
    }
}
