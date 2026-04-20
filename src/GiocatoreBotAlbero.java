import java.util.ArrayList;

public class GiocatoreBotAlbero extends GiocatoreBotS {
    private Nodo radiceAlberoDecisionale;
    private Nodo nodoCorrenteAlbero;

    public GiocatoreBotAlbero(String nome, Albero albero, Personaggio[] tabellone) {
        super(nome, albero, tabellone);
        this.radiceAlberoDecisionale = null;
        this.nodoCorrenteAlbero     = null;
    }

    public Albero getAlberoDecisionale() {
        if (radiceAlberoDecisionale == null) return null;
        return new Albero(radiceAlberoDecisionale);
    }

    public void avanzaERegistra(String domanda, String risposta) {
        Nodo nuovoNodo = new Nodo(domanda);
        if (radiceAlberoDecisionale == null) {
            radiceAlberoDecisionale = nuovoNodo;
        } else {
            agganciaFiglio(nodoCorrenteAlbero, nuovoNodo, risposta);
        }

        nodoCorrenteAlbero = nuovoNodo;
        super.avanza(risposta);
    }

    public void registraFogliaFinale(String risposta, Personaggio personaggioFine) {
        if (nodoCorrenteAlbero == null || personaggioFine == null) return;
        Nodo foglia = new Nodo(personaggioFine);
        agganciaFiglio(nodoCorrenteAlbero, foglia, risposta);
    }

    private void agganciaFiglio(Nodo padre, Nodo figlio, String risposta) {
        if (padre == null) return;

        boolean dx = risposta.equalsIgnoreCase("si");

        if (dx) {
            if (padre.getNododx() == null) {
                padre.AggiungiNodo(figlio, true);
            } else {
                agganciaFiglio(padre.getNododx(), figlio, risposta);
            }
        } else {
            if (padre.getNodosx() == null) {
                padre.AggiungiNodo(figlio, false);
            } else {
                agganciaFiglio(padre.getNodosx(), figlio, risposta);
            }
        }
    }
}
