package com.example.fartos.Cards;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fartos.R;


public class CardViewManager extends RecyclerView.ViewHolder {

    private TextView carta;
    CardViewManager(@NonNull View itemView) {
        super(itemView);
        carta = itemView.findViewById(R.id.card);
    }

    public TextView getCarta() {return carta;}
}


