package com.example.fartos.Cards;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Card implements Parcelable {

    public enum tipuscarta {
        MOV1(28), MOV2(18), MOV3(10),
        TELEPORT(3), ZANCADILLA(4), PATADA(3),
        HUNDIMIENTO(2), BROMA(2);

        private int numCartes;
        public int getNumCartes() { return numCartes; }

        tipuscarta(int numCartes) {
            this.numCartes = numCartes;
        }
    }


    private int numero;
    private tipuscarta efecte;


    public int getNumero() {
        return numero;
    }
    public tipuscarta getEfecte() {
        return efecte;
    }


    public Card(int numero, tipuscarta efecte) {
        this.numero = numero;
        this.efecte = efecte;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append(numero + " --> ");
        switch (efecte) {
            case MOV1:
                result.append("Mover 1");
                break;

            case MOV2:
                result.append("Mover 2");
                break;

            case MOV3:
                result.append("Mover 3");
                break;

            default:
                result.append(efecte.name() + "");
        }

        return result.toString();
    }


    public static List<Card> generarMazo() {
        List<Card> result = new ArrayList<>();
        int numeroCarta = 1;

        // Crea las cartes
        for (tipuscarta tipuscarta : tipuscarta.values()) {
            for (int i = 0; i < tipuscarta.getNumCartes(); i++)
                result.add(new Card(numeroCarta++, tipuscarta));
        }

        // Reordena las cartes
        Collections.shuffle(result);

        return result;
    }

    protected Card(Parcel in) {
        // Read the Carta object properties from the Parcel
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Write the Carta object properties to the Parcel
    }
}

