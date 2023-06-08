package com.example.fartos.BoardRecycler;

import com.example.fartos.Player;

import java.util.List;

public class Casilla {
    private String jugador1;
    private String jugador2;
    private String jugador3;
    private String jugador4;
    private String jugador5;
    private String jugador6;

    public Casilla() {

    }
    public Casilla(List<Player> players) {
        for(int i = 0; i < players.size(); i++){
            setJugador(i + 1, players.get(i).getNom());
        }
    }

    public Casilla(String jugador1, String jugador2, String jugador3) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugador3 = jugador3;
    }

    public Casilla(String jugador1, String jugador2, String jugador3, String jugador4) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugador3 = jugador3;
        this.jugador4 = jugador4;
    }

    public Casilla(String jugador1, String jugador2, String jugador3, String jugador4, String jugador5) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugador3 = jugador3;
        this.jugador4 = jugador4;
        this.jugador5 = jugador5;
    }

    public Casilla(String jugador1, String jugador2, String jugador3, String jugador4, String jugador5, String jugador6) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.jugador3 = jugador3;
        this.jugador4 = jugador4;
        this.jugador5 = jugador5;
        this.jugador6 = jugador6;
    }

    public void setJugador(int num, String name){
        switch (num) {
            case 1:
                setJugador1(name);
                break;
            case 2:
                setJugador2(name);
                break;
            case 3:
                setJugador3(name);
                break;
            case 4:
                setJugador4(name);
                break;
            case 5:
                setJugador5(name);
                break;
            case 6:
                setJugador6(name);
                break;
            default:

        }
    }
    public String getJugador1() {
        return jugador1;
    }

    public void setJugador1(String jugador1) {
        this.jugador1 = jugador1;
    }

    public String getJugador2() {
        return jugador2;
    }

    public void setJugador2(String jugador2) {
        this.jugador2 = jugador2;
    }

    public String getJugador3() {
        return jugador3;
    }

    public void setJugador3(String jugador3) {
        this.jugador3 = jugador3;
    }

    public String getJugador4() {
        return jugador4;
    }

    public void setJugador4(String jugador4) {
        this.jugador4 = jugador4;
    }

    public String getJugador5() {
        return jugador5;
    }

    public void setJugador5(String jugador5) {
        this.jugador5 = jugador5;
    }

    public String getJugador6() {
        return jugador6;
    }

    public void setJugador6(String jugador6) {
        this.jugador6 = jugador6;
    }
}
