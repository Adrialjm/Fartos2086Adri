package com.example.fartos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fartos.Cards.Card;
import com.example.fartos.Cards.CardRecycler;
import com.example.fartos.BoardRecycler.Casilla;
import com.example.fartos.BoardRecycler.CasillaRecycler;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;


public class PartidaActivity extends AppCompatActivity {

    int numJugadores;
    LinearLayout tablero;

    List<Casilla> casillas = new ArrayList<>();
    List<Card> cards = new ArrayList<>();

    List<Player> players = new ArrayList<>();

    private final int NumRondes = 5;

    public enum ResultatMoviment {GUANYADOR, CASELLA_ESPECIAL, CONTINUAR_JOC};

    private final int RondaElimCela = 3;

    private final int MovCasellaEspecial = 5;
    int numRonda = 1;
    private Board board;
    private int cartesJugades;
    static int pase = -1 ;
    Player playerActual;

    Player playerSelecionado;

    RecyclerView recycler;
    CasillaRecycler adapter = new CasillaRecycler(casillas, this);
    CardRecycler adapterCartas = new CardRecycler(cards);

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);
        getSupportActionBar().hide();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ArrayList<Parcelable> parcelableJugadors = extras.getParcelableArrayList("jugadors");
            if (parcelableJugadors != null) {
                for (Parcelable parcelableJugador : parcelableJugadors) {
                    players.add((Player) parcelableJugador);
                }
            }
        }
        iniciar(board);

        recycler = findViewById(R.id.cartas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(adapterCartas);



        recycler = findViewById(R.id.tablero);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(adapter);
        adapter.add(new Casilla(players));
        for(int i = 0; i< 15; i++){
            adapter.add(new Casilla());
        }

        adapter.setOnItemClick(new CasillaRecycler.OnItemClick() {
            @Override
            public void getPosition(int pos) {
                List<TextView> jugadores = adapter.getJugadores();
                for (int i = 0; i < jugadores.size(); i++) {
                    TextView textView = jugadores.get(i);
                    int finalI = i;
                    int finalI1 = i;
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(finalI < players.size()){
                                playerSelecionado = players.get(finalI1);
                            }


                            adapter.notifyDataSetChanged();
                            adapterCartas.notifyDataSetChanged();

                        }
                    });
                }

            }
        });
        adapterCartas.setOnItemClick(new CardRecycler.OnItemClick() {
            @Override
            public void getPosition(int pos) {
                if(numRonda >= 3  && pase% players.size() == 0){

                }
                casillaEspecial();
                switch(playerActual.getMa().get(pos).getEfecte()) {
                    case HUNDIMIENTO:
                    {
                        if(playerSelecionado == null){
                            Toast.makeText(PartidaActivity.this, "seleciona un jugador", Toast.LENGTH_SHORT).show();
                        }else {
                            playerActual.removdeCarta(pos);
                            casillas.get(playerSelecionado.getPosition()).setJugador(players.indexOf(playerSelecionado) + 1, "");
                            playerSelecionado.setPosition(0);
                            casillas.get(0).setJugador(players.indexOf(playerSelecionado) + 1, playerSelecionado.getNom());
                            canviarJugadorActual();
                        }
                        break;
                    }
                    case ZANCADILLA:
                    {
                        if(playerSelecionado == null){
                            Toast.makeText(PartidaActivity.this, "seleciona un jugador", Toast.LENGTH_SHORT).show();

                        }else {
                            if (!playerSelecionado.isZancadilla()) {
                                Random rnd = new Random();
                                playerActual.removdeCarta(pos);
                                playerSelecionado.setZancadilla(true);
                                if (playerSelecionado.getMa().size() >= 1)
                                    playerSelecionado.getMa().remove(rnd.nextInt(playerSelecionado.getMa().size()));
                            }
                            playerSelecionado = null;
                            canviarJugadorActual();


                        }

                        break;
                    }

                    case PATADA:
                    {
                        if(playerSelecionado == null){
                            Toast.makeText(PartidaActivity.this, "seleciona un jugador", Toast.LENGTH_SHORT).show();
                        }else{
                            playerActual.removdeCarta(pos);
                            playerSelecionado.setPatada(true);
                            playerSelecionado = null;
                            canviarJugadorActual();
                        }
                        break;
                    }

                    case BROMA:
                    {
                        if(playerSelecionado == null){
                            Toast.makeText(PartidaActivity.this, "seleciona un jugador", Toast.LENGTH_SHORT).show();
                        }else {
                            playerActual.removdeCarta(pos);
                            List<Card> cartesJugador = playerActual.getMa();
                            List<Card> cartasJugadorContrari = playerSelecionado.getMa();


                            playerActual.setMa(cartasJugadorContrari);
                            playerSelecionado.setMa(cartesJugador);

                            playerSelecionado = null;
                            canviarJugadorActual();
                        }

                        adapter.notifyDataSetChanged();
                        adapterCartas.notifyDataSetChanged();


                        break;
                    }
                    case TELEPORT:
                    {
                        if(playerSelecionado == null){
                            Toast.makeText(PartidaActivity.this, "seleciona un jugador", Toast.LENGTH_SHORT).show();
                        }else{
                            playerActual.removdeCarta(pos);
                            int jugadorpos = playerActual.getPosition();
                            playerActual.setPosition(playerSelecionado.getPosition());
                            playerSelecionado.setPosition(jugadorpos);
                            casillas.get(jugadorpos).setJugador(players.indexOf(playerActual) + 1, "" );
                            casillas.get(playerActual.getPosition()).setJugador(players.indexOf(playerSelecionado) + 1, "" );
                            casillas.get(jugadorpos).setJugador(players.indexOf(playerSelecionado) + 1, playerSelecionado.getNom() );

                            casillas.get(playerActual.getPosition()).setJugador(players.indexOf(playerActual) + 1, playerActual.getNom() );


                            playerSelecionado = null;
                            canviarJugadorActual();
                        }

                        adapter.notifyDataSetChanged();
                        adapterCartas.notifyDataSetChanged();

                    }
                    case MOV3:
                    {
                        int step = 3;

                        if(playerSelecionado == null){
                            Toast.makeText(PartidaActivity.this, "seleciona un jugador", Toast.LENGTH_SHORT).show();
                        }else{
                            playerActual.removdeCarta(pos);
                            if(playerSelecionado == playerActual){
                                mover(playerActual, step);

                                playerSelecionado = null;
                            }else{

                                mover(playerSelecionado, -step);

                                playerSelecionado = null;
                            }
                            canviarJugadorActual();
                        }
                        adapter.notifyDataSetChanged();
                        adapterCartas.notifyDataSetChanged();
                    }
                        break;
                    case MOV2:
                    {
                        int step = 2;

                        if(playerSelecionado == null){
                            Toast.makeText(PartidaActivity.this, "seleciona un jugador", Toast.LENGTH_SHORT).show();
                        }else{
                            playerActual.removdeCarta(pos);
                            if(playerSelecionado == playerActual){
                                mover(playerActual, step);
                                playerSelecionado = null;
                            }else{

                                mover(playerSelecionado, -step);
                                playerSelecionado = null;

                            }
                            canviarJugadorActual();
                        }
                        adapter.notifyDataSetChanged();
                        adapterCartas.notifyDataSetChanged();
                    }
                        break;
                    case MOV1:
                    {
                        int step = 1;

                        if(playerSelecionado == null){
                            Toast.makeText(PartidaActivity.this, "seleciona un jugador", Toast.LENGTH_SHORT).show();
                        }else{
                            playerActual.removdeCarta(pos);

                            if(playerSelecionado == playerActual){
                                mover(playerActual, step);
                                playerSelecionado = null;
                            }else{
                                mover(playerSelecionado, -step);
                                playerSelecionado = null;

                            }

                            canviarJugadorActual();

                        }
                        adapter.notifyDataSetChanged();
                        adapterCartas.notifyDataSetChanged();
                    }
                        break;
                    default:
                }
            }
        });
    }

    private boolean rondaAcabada() {
        boolean fin = true;
        for (Player player : players){
            if(player.getMa().size() != 0){
                fin = false;
                break;
            }
        }
        return fin;
    }

    public void casillaEspecial(){

        if(playerActual.getPosition() == 7){
            int step = 5;

            if(playerSelecionado == null){
                Toast.makeText(PartidaActivity.this, "seleciona un jugador", Toast.LENGTH_SHORT).show();
            }else{
                if(playerSelecionado == playerActual){
                    mover(playerActual, step);

                    playerSelecionado = null;
                }else{
                    mover(playerSelecionado, -step);
                    playerSelecionado = null;
                }
                canviarJugadorActual();
            }
            adapter.notifyDataSetChanged();
            adapterCartas.notifyDataSetChanged();
        }
    }

    public void mover(Player player, int step){
        if(playerSelecionado.getPosition() + step >= 0 ){
            casillas.get(player.getPosition()).setJugador(players.indexOf(player) + 1, "");
            casillas.get(player.getPosition() + step).setJugador(players.indexOf(player) + 1, player.getNom());
            player.setPosition(player.getPosition() + step);
        }else{
            casillas.get(player.getPosition()).setJugador(players.indexOf(player) + 1, "");
            casillas.get(0).setJugador(players.indexOf(player) + 1, player.getNom());
            player.setPosition(0);
        }
    }

    public void jugadorCartasSleccionado(Player player, int pos){

        String nom = player.getNom();
        int  i = players.indexOf(player);
        List<TextView> jugadores = this.adapter.getJugadores();
        TextView textView = jugadores.get(i);
        int finalI = i;

        int viewId = textView.getId();
        playerActual = players.get(finalI);
        casillas.get(pos).setJugador(i, "");
        casillas.get(pos + 1).setJugador(i, nom);

        adapter.notifyDataSetChanged();
        adapterCartas.notifyDataSetChanged();
    }

    public void mostrarJugadoActualNombre(Player player){
        TextView inf = (TextView) findViewById(R.id.jugador_actual);

        inf.setText(player.getNom());
    }

    private void repartirCartes() {
        List<Card> ma;

        board = crearBoard(players.toArray(new Player[0]));

        // Crea el mazo
        cards = Card.generarMazo();

        // Reparte las cartas a los jugadores
        for (Player player : board.getJugadors()) {
            ma = cards.subList(0, board.NumCartes); // Crea la mano
            player.setMa(ma); // Assigna la mano al jugador

            cards.removeAll(ma); // Elimina las cartas
        }
    }

    public void ganador(){
        if(players.size() == 1){
            Toast.makeText(PartidaActivity.this, "ganador es " + players.get(0).getNom(), Toast.LENGTH_SHORT).show();
        }else{
            for (Player player : players){
                if(player.getPosition() == casillas.size() - 1){
                    Toast.makeText(PartidaActivity.this, "ganador es " + player.getNom(), Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }


    }

    private void canviarJugadorActual() {
        ganador();

        Random rnd = new Random();
        int indexJugadorActual;
        List<Player> players = board.getJugadors();

        // Asigna un pj aleatorio si no hay
        if (playerActual == null) {
            indexJugadorActual = rnd.nextInt(this.board.getTaulell().size());
            int i = 0;
            for (ListIterator<Player> jugador = board.getJugadors().listIterator();
                 jugador.hasNext() && i <= indexJugadorActual;
                 i++) {

                if (i == indexJugadorActual) playerActual = jugador.next();
            }
        }

        // Te lleva al siguiente pj asignado
        else {
            if (players.indexOf(playerActual) == players.size() - 1)
                playerActual = players.get(0);

            else
                playerActual = players.listIterator(players.indexOf(playerActual) + 1).next();
        }


        if(rondaAcabada()){
            numRonda++;
            System.out.println("ronda");
            if(numRonda >= 3){
                casillas.remove(0);
                for(Player player : board.getJugadors()){

                    if(player.getPosition() == 0){
                        this.board.eliminarJugador(player);

                        this.players.remove(this.players.indexOf(player));
                    }
                    player.setPosition(player.getPosition() - 1);
                }
            }
            repartirCartes();
        }

        if (playerActual.getMa().size() == 0) {canviarJugadorActual();}

        pase++;
        mostrarCartas(playerActual);

        mostrarJugadoActualNombre(playerActual);

        adapter.notifyDataSetChanged();
        adapterCartas.notifyDataSetChanged();
        if(pase% players.size() == 0 && numRonda >= 3) {
            for(Player player : board.getJugadors()){
                if(player.getPosition() == 0){
                    this.board.eliminarJugador(player);
                    this.players.remove(this.players.indexOf(player));
                }
                player.setPosition(player.getPosition() - 1);
            }
            casillas.remove(0);
        }

        System.out.println("ronda " + numRonda);



    }

    private Board crearBoard(Player[] players) {

        Board board = null;

        while (board == null) {

            try {
                board = new Board(players);
            } catch (IllegalArgumentException e) {
            }
        }

        return board;
    }

    public void iniciar(Board board) {
        this.board = board;

        numRonda = 1;
        cartesJugades = 0;

        repartirCartes();
        canviarJugadorActual();

    }

    public void mostrarCartas(Player player) {
        adapterCartas.flush();
        adapterCartas.add(players.get(players.indexOf(player)).getMa());
        adapterCartas.notifyDataSetChanged();

    }


}