import java.util.ArrayList;

public class GiocatoreBotS extends Giocatore {

    public GiocatoreBotS(String nome, Albero albero, Personaggio[] tabellone) {
        super(nome, albero, tabellone);
    }

    public String ChiediDomanda(Nodo nodoCorrente) {
        if (nodoCorrente == null || nodoCorrente.getDomanda() == null) {
            return null; // Nessuna domanda disponibile → dovrebbe indovinare
        }
        return nodoCorrente.getDomanda();
    }

    private Nodo trovaNodoDomanda(Nodo nodo) {
        if (nodo == null || nodo.getPersonaggio() != null) return null;

        ArrayList<Personaggio> attivi = getPersonaggiAttivi();
        if (attivi.isEmpty()) return null;

        // Controlla se questo nodo è utile (almeno 1 personaggio attivo nel ramo dx E sx)
        ArrayList<Personaggio> sinistri = albero.getPersoneRimaste(nodo.getNodosx() != null ? nodo.getNodosx() : nodo);
        ArrayList<Personaggio> destri   = albero.getPersoneRimaste(nodo.getNododx() != null ? nodo.getNododx() : nodo);

        boolean haAttivi_sx = sinistri.stream().anyMatch(p -> attivi.contains(p));
        boolean haAttivi_dx = destri.stream().anyMatch(p -> attivi.contains(p));

        if (haAttivi_dx && haAttivi_sx) {
            return nodo; // Questo nodo è utile: divide i personaggi rimanenti
        }

        // Altrimenti scendi nel ramo più promettente
        Nodo fromDx = trovaNodoDomanda(nodo.getNododx());
        if (fromDx != null) return fromDx;
        return trovaNodoDomanda(nodo.getNodosx());
    }

    public void avanza(String risposta,Nodo nodoCorrente) {
        if (nodoCorrente == null) return;
        if (risposta.equalsIgnoreCase("si")) {
            nodoCorrente = nodoCorrente.getNododx();
        } else {
            nodoCorrente = nodoCorrente.getNodosx();
        }
    }


     // Se rimane un solo personaggio attivo, lo restituisce come guess finale.
    public Personaggio indovinaPersonaggio(Nodo nodoCorrente) {
        ArrayList<Personaggio> attivi = getPersonaggiAttivi();
        if (attivi.size() == 1) return attivi.get(0);
        // Se siamo arrivati a una foglia dell'albero
        if (nodoCorrente != null && nodoCorrente.getPersonaggio() != null) {
            return nodoCorrente.getPersonaggio();
        }
        return null;
    }
}

