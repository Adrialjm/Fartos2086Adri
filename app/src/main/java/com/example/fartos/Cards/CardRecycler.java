package com.example.fartos.Cards;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fartos.R;

import java.util.ArrayList;
import java.util.List;

public class CardRecycler extends RecyclerView.Adapter<CardViewManager> {

    private List<Card> cards;

    public CardRecycler(List<Card> films) {
        this.cards = films;
    }

    public List<Card> get() {
        return cards;
    }

    public void flush(){
        this.cards = new ArrayList<>();
    }

    public void add(Object grupo) {
        this.cards.add((Card) grupo);
        this.notifyItemInserted(this.cards.size() - 1);
    }

    public void add(List<Card> grupo) {
        this.cards = grupo;
        this.notifyItemInserted(this.cards.size() - 1);
    }

    @NonNull
    @Override
    public CardViewManager onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_carta, parent, false);
        final CardViewManager viewHolder = new CardViewManager(vista);
        vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewManager holder, @SuppressLint("RecyclerView") int position) {
        Card card = this.cards.get(position);
        holder.getCarta().setText(card.getEfecte().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.getPosition(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.cards.size();
    }

    OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void getPosition(int pos);
    }
}
