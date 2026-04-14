import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Giocatore {
    private String nome;
    protected Albero albero;
    private Personaggio[] tabellone;

    public Giocatore(String nome, Albero albero, Personaggio[] tabellone) {
        this.nome = nome;
        this.albero = albero;
        this.tabellone = tabellone;
    }
    protected void EliminaPersonaggio(String domanda,String risposta){
        int pos = 0;
        boolean trovato;
        Nodo nodo = albero.getNodo(albero.getRoot(),null, domanda);
        ArrayList<Personaggio> personerimaste;
        if (risposta.equals("si")){
            personerimaste = albero.getPersoneRimaste(nodo.getNododx());
        }
        else {
            personerimaste = albero.getPersoneRimaste(nodo.getNodosx());
        }
        for (int i=0; i<personerimaste.size();i++){
            trovato= false;
            for (int j=0; j< tabellone.length;j++){
                if (i==j){
                    trovato = true;
                    pos = j;
                }
            }
            if (trovato){
                tabellone[pos] = null;
            }
        }

    }

}
