package com.example.fartos;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class Board {

    public final int LongTaulell = 15 + 1;
    public final int[] PosEspecial = { 8 };
    public final int MaxAforo = 2;
    public final int NumCartes;


    private Dictionary<Player, Number> taulell;
    private Dictionary<Player, Number> jugadorsEliminats;
    private List<Player> guanyadors;
    private int casellaEliminada = 0;


    public Dictionary<Player, Number> getTaulell() {
        return taulell;
    }
    public int getNumJugadors() {
        return taulell.size();
    }
    public List<Player> getJugadors() {
        return Collections.list(taulell.keys());
    }
    public List<Player> getGuanyadors() {
        return guanyadors;
    }
    public int getCasellaEliminada() {
        return casellaEliminada;
    }


    public Board(Player[] players) {
        taulell = new Hashtable<>();
        jugadorsEliminats = new Hashtable<>();
        guanyadors = new ArrayList<>();


        // Revisa jugadores
        if (players.length < 3)
            throw new IllegalArgumentException("No es pot jugar amb menys de 3 jugadors.");
        if (players.length > 6)
            throw new IllegalArgumentException("No es pot jugar amb més de 6 jugadors.\"");


        // Añade jugadores
        for (Player player : players)
            taulell.put(player, 0);


        // Comprueba las cartas de la mano
        if (taulell.size() < 5) NumCartes = 6;
        else NumCartes = 5;
    }




    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int numJugador = 1;

        // Lista
        for (Player player : getJugadors())
            result.append(numJugador++ + ". ").append(player + "\n");
        numJugador = 1;


        // Línea
        result.append("+");
        for (int i = 0; i < LongTaulell; i++) {
            int finalI = i;
            int comprovacioEspecial = Arrays.stream(PosEspecial).filter(n -> n == finalI).findAny().orElse(-1);


            if (comprovacioEspecial == -1) result.append("-----+");
            else result.append("--*--+");
        }
        result.append("\n");

        // Línea per cada jugador
        for (Enumeration<Player> jugadors = taulell.keys(); jugadors.hasMoreElements(); ) {
            int posJugador = taulell.get(jugadors.nextElement()).intValue();

            result.append("|");
            // Meter al jugador en su posición
            for (int i = 0; i < LongTaulell; i++) {
                if (casellaEliminada > 0 && i <= casellaEliminada)
                    result.append("*****|");
                else {
                    if (posJugador == i)
                        result.append("  " + numJugador + "  |");
                    else
                        result.append("     |");
                }
            }
            result.append("\n");
            numJugador++;
        }

        // Ultima linea
        result.append("+");
        for (int i = 0; i < LongTaulell; i++) result.append("-----+");
        result.append("\n");

        return result.toString();
    }




    // Tablero


    public boolean comprobarCasellaEspecial(int casella) {
        int comprovacioEspecial = Arrays.stream(PosEspecial).filter(n -> n == casella).findAny().orElse(-1);
        if (comprovacioEspecial != -1) return true;
        return false;
    }
    public boolean comprobarCasellaEspecial(Player player) {
        int comprovacioEspecial = Arrays.stream(PosEspecial).filter(n -> n == (int) taulell.get(player))
                        .findAny()
                        .orElse(-1);
        if (comprovacioEspecial != -1) return true;
        return false;
    }


    private boolean comprovarAforamentSuperat(int casella) {
        int sumaOcupants = 0;


        // Comprova quants jugadors estan a la casella
        for (Enumeration<Number> posicions = taulell.elements(); posicions.hasMoreElements(); ) {
            if (posicions.nextElement().intValue() == casella)
                sumaOcupants++;
        }

        if (sumaOcupants >= MaxAforo) return true;
        else return false;
    }


    public void eliminarCasilla() {
        // Elimina la casella
        casellaEliminada++;

        // Elimina als jugadors desbordats
        for (Player player : getJugadors()) {
            if ((int) taulell.get(player) <= casellaEliminada)
                eliminarJugador(player);
        }
    }



    // * Jugadors

    public int posicionJugador(Player player) {
        return (int) taulell.get(player);
    }



    public void colocarJugador(Player player, int posicio) {
        taulell.put(player, posicio);

        // Si va a una casella eliminada elimina al jugador
        if (posicio <= casellaEliminada && casellaEliminada > 0)
            eliminarJugador(player);
    }


    public void eliminarJugador(Player player) {
        if (taulell.size() >= 1) {
            // Afegeix al jugador a la llista de jugadors eliminats
            jugadorsEliminats.put(player, taulell.get(player));
            // L'elimina del taulell
            taulell.remove(player);
        }
    }


    public boolean comprobarGanador(boolean finalPartida) {

        if (!guanyadors.isEmpty()) return true;


        // Si no es final de partida
        if (!finalPartida) {
            // Por si todos los pjs han sido eliminados
            if (taulell.size() == 1) {
                guanyadors.add(getJugadors().get(0));
                return true;
            }

            // comprueba si no quedan jugadores
            else if (taulell.isEmpty()) {
                guanyadors.addAll(jugadorAdelantado(jugadorsEliminats));
                return true;
            }


            // Condicion de victoria META
            for (Player player : getJugadors()) {
                if ((int) taulell.get(player) == LongTaulell - 1) {
                    guanyadors.add(player);
                    return true;
                }
            }
        }


        // Para que gane el más adelantado
        else {
            guanyadors.addAll(jugadorAdelantado(taulell));
            return true;
        }

        return false;
    }


    public List<Player> jugadorAdelantado(Dictionary<Player, Number> lista) {
        List<Player> jugadorAdelantado = new ArrayList<>();


        for (Enumeration<Player> jugadors = lista.keys(); jugadors.hasMoreElements(); ) {
            Player playerActual = jugadors.nextElement();
            int posResult;
            int posJugAct;


            // Asigna aleatoriamente un personaje adelantado
            if (jugadorAdelantado.isEmpty()) jugadorAdelantado.add(playerActual);
            else {

                posResult = lista.get(jugadorAdelantado.get(0)).intValue();
                posJugAct = lista.get(playerActual).intValue();

                // Comprueba si el jugador actual està mas lejos que el ganador actual
                if (posJugAct > posResult) {
                    jugadorAdelantado.removeAll(jugadorAdelantado);
                    jugadorAdelantado.add(playerActual);
                }
                else if (posJugAct == posResult) jugadorAdelantado.add(playerActual);
            }
        }

        return jugadorAdelantado;
    }
}
