package com.example.fartos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PlayerSelection extends AppCompatActivity {
    Button buttonAgregar, buttonEliminar, buttonStart;
    LinearLayout playersLayout;
    List<EditText> playerEditTexts;
    MediaPlayer reproductor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_jugadores);
        getSupportActionBar().hide();

        //Fartos banda sonora
        reproductor = MediaPlayer.create(getApplicationContext(), R.raw.fartost);
        reproductor.start();
        reproductor.setLooping(true);

        buttonAgregar = findViewById(R.id.buttonAgregar);
        buttonEliminar = findViewById(R.id.buttonEliminar);
        buttonStart = findViewById(R.id.buttonStart);
        playersLayout = findViewById(R.id.players_layout);
        playerEditTexts = new ArrayList<>();

        //Añadimos 3 players, los necesarios
        for (int i = 0; i < 3; i++) {
            addPlayerEditText();
        }

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerEditTexts.size() < 6) {
                    addPlayerEditText();
                }
            }
        });

        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerEditTexts.size() > 3) {
                    removePlayerEditText();
                }
            }
        });

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarPartida();
            }
        });
    }

    private void addPlayerEditText() {
        EditText editText = new EditText(this);
        editText.setHint("Jugador " + (playerEditTexts.size() + 1));
        playerEditTexts.add(editText);
        playersLayout.addView(editText);
    }

    private void removePlayerEditText() {
        EditText editText = playerEditTexts.remove(playerEditTexts.size() - 1);
        playersLayout.removeView(editText);
    }

    private void iniciarPartida() {
        ArrayList<Player> players = new ArrayList<>();
        for (EditText editText : playerEditTexts) {
            String name = editText.getText().toString().trim();
            if (!name.isEmpty()) {
                players.add(new Player(name));
            }
        }

        if (players.size() >= 3) {
            Intent intent = new Intent(this, PartidaActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("jugadors", players);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Pon al menos 3 nombres", Toast.LENGTH_SHORT).show();
        }
    }



}