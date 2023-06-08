package com.example.fartos.BoardRecycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fartos.R;

public class CasillaViewManager extends RecyclerView.ViewHolder {

    private TextView jugador1, jugador2, jugador3, jugador4, jugador5, jugador6;
    CasillaViewManager(@NonNull View itemView) {
        super(itemView);
        jugador1 = itemView.findViewById(R.id.jugador1);
        jugador2 = itemView.findViewById(R.id.jugador2);
        jugador3 = itemView.findViewById(R.id.jugador3);
        jugador4 = itemView.findViewById(R.id.jugador4);
        jugador5 = itemView.findViewById(R.id.jugador5);
        jugador6 = itemView.findViewById(R.id.jugador6);

    }

    public TextView getJugador1() {
        return jugador1;
    }

    public TextView getJugador2() {
        return jugador2;
    }

    public TextView getJugador3() {
        return jugador3;
    }

    public TextView getJugador4() {
        return jugador4;
    }

    public TextView getJugador5() {
        return jugador5;
    }

    public TextView getJugador6() {
        return jugador6;
    }
}
