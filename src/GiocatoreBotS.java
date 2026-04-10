import java.util.ArrayList;

public class GiocatoreBotS extends Giocatore {

    // FIX: aggiunto campo mancante nodoCorrente e lista attivi
    private Nodo nodoCorrente;
    private ArrayList<Personaggio> attivi;

    public GiocatoreBotS(String nome, Albero albero, Personaggio[] tabellone) {
        super(nome, albero, tabellone);
        this.nodoCorrente = albero.getRoot();
        // FIX: inizializzazione della lista attivi dal tabellone
        this.attivi = new ArrayList<>();
        for (Personaggio p : tabellone) {
            if (p != null) attivi.add(p);
        }
    }

    // FIX: aggiunto metodo mancante getPersonaggiAttivi()
    public ArrayList<Personaggio> getPersonaggiAttivi() {
        return attivi;
    }

    public String ChiediDomanda(Nodo nodo) {
        if (nodo == null || nodo.getDomanda() == null) {
            return null;
        }
        return nodo.getDomanda();
    }

    private Nodo trovaNodoDomanda(Nodo nodo) {
        if (nodo == null || nodo.getPersonaggio() != null) return null;

        ArrayList<Personaggio> sinistri = albero.getPersoneRimaste(nodo.getNodosx() != null ? nodo.getNodosx() : nodo);
        ArrayList<Personaggio> destri   = albero.getPersoneRimaste(nodo.getNododx() != null ? nodo.getNododx() : nodo);

        boolean haAttivi_sx = sinistri.stream().anyMatch(p -> attivi.contains(p));
        boolean haAttivi_dx = destri.stream().anyMatch(p -> attivi.contains(p));

        if (haAttivi_dx && haAttivi_sx) {
            return nodo;
        }

        Nodo fromDx = trovaNodoDomanda(nodo.getNododx());
        if (fromDx != null) return fromDx;
        return trovaNodoDomanda(nodo.getNodosx());
    }

    // FIX: avanza aggiorna il campo della classe invece di una variabile locale
    public void avanza(String risposta) {
        if (nodoCorrente == null) return;
        if (risposta.equalsIgnoreCase("si")) {
            nodoCorrente = nodoCorrente.getNododx();
        } else {
            nodoCorrente = nodoCorrente.getNodosx();
        }
    }

    public Personaggio indovinaPersonaggio() {
        ArrayList<Personaggio> attiviCorrente = getPersonaggiAttivi();
        if (attiviCorrente.size() == 1) return attiviCorrente.get(0);
        if (nodoCorrente != null && nodoCorrente.getPersonaggio() != null) {
            return nodoCorrente.getPersonaggio();
        }
        return null;
    }
}

