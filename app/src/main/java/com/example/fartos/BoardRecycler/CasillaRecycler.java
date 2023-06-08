package com.example.fartos.BoardRecycler;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fartos.R;

import java.util.ArrayList;
import java.util.List;

public class CasillaRecycler extends RecyclerView.Adapter<CasillaViewManager> {

    private List<Casilla> casillas;
    private Context context;
    List<TextView> jugadores;

    public CasillaRecycler(List<Casilla> casillas) {
        this.casillas = casillas;
    }

    public CasillaRecycler(List<Casilla> casillas, Context context) {
        this.casillas = casillas;
        this.context = context;
    }

    public List<Casilla> get() {
        return casillas;
    }

    public void flush(){
        this.casillas = new ArrayList<>();
    }

    public void update(List<Casilla> casillas){
        this.casillas = casillas;
    }

    public void add(Object grupo) {
        this.casillas.add((Casilla) grupo);
        this.notifyItemInserted(this.casillas.size() - 1);
    }

    @NonNull
    @Override
    public CasillaViewManager onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_tres_jugadores, parent, false);
        final CasillaViewManager viewHolder = new CasillaViewManager(vista);
        vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CasillaViewManager holder, @SuppressLint("RecyclerView") int position) {
        Casilla casilla = this.casillas.get(position);

        jugadores = new ArrayList<>();
        jugadores.add(holder.getJugador1());
        jugadores.add(holder.getJugador2());
        jugadores.add(holder.getJugador3());
        jugadores.add(holder.getJugador4());
        jugadores.add(holder.getJugador5());
        jugadores.add(holder.getJugador6());

        holder.getJugador1().setText(casilla.getJugador1());
        holder.getJugador2().setText(casilla.getJugador2());
        holder.getJugador3().setText(casilla.getJugador3());
        holder.getJugador4().setText(casilla.getJugador4());
        holder.getJugador5().setText(casilla.getJugador5());
        holder.getJugador6().setText(casilla.getJugador6());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.getPosition(position);

            }
        });
        holder.itemView.performClick();
    }

    public List<TextView> getJugadores(){
        return jugadores;
    }

    @Override
    public int getItemCount() {
        return this.casillas.size();
    }

    OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void getPosition(int pos);
    }
}