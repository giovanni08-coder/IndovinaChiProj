import java.io.Serializable;
import java.util.ArrayList;

public class Albero implements Serializable {
    private Nodo root;

    public Albero(Nodo root) {
        this.root = root;
    }

    public Nodo getRoot() {
        return root;
    }

    public void AggiungiNodo(Nodo nodo, Nodo NodoArrivo, boolean dx) {
        Nodo NodoSostituire;
        if (nodo == null) {
            throw new IllegalArgumentException("Non puoi aggiungere ad un nodo che non esiste");
        }
        if (NodoArrivo.getPersonaggio() != null) {
            if (nodo.getPersonaggio() != null) {
                throw new IllegalArgumentException("Non puoi inserire un personaggio sotto ad un personaggio");
            } else {
                NodoSostituire = NodoArrivo;
                NodoArrivo = nodo;
                NodoArrivo.AggiungiNodo(NodoSostituire, dx);
            }
        } else {
            NodoArrivo.AggiungiNodo(nodo, dx);
        }
    }

    private void getPersoneRimasteRic(Nodo nodo, ArrayList<Personaggio> persone) {
        if (nodo.getPersonaggio() != null) persone.add(nodo.getPersonaggio());
        else {
            if (nodo.getNododx() != null) {
                getPersoneRimasteRic(nodo.getNododx(), persone);
            }
            if (nodo.getNodosx() != null) {
                getPersoneRimasteRic(nodo.getNodosx(), persone);
            }
        }
    }

    public ArrayList<Personaggio> getPersoneRimaste(Nodo nodo) {
        if (nodo == null) throw new IllegalArgumentException("Il nodo non può essere null!");
        ArrayList<Personaggio> persone = new ArrayList<>();
        getPersoneRimasteRic(nodo, persone);
        return persone;
    }

    // FIX 1: uso di .equals() al posto di == per confrontare stringhe
    // FIX 2: il risultato della ricorsione viene ora ritornato correttamente
    public Nodo getNodo(Nodo partenza, Nodo arrivo, String domanda) {
        if (partenza != null) {
            if (partenza.getNododx() != null && domanda.equals(partenza.getNododx().getDomanda())) {
                return partenza.getNododx();
            } else if (partenza.getNodosx() != null && domanda.equals(partenza.getNodosx().getDomanda())) {
                return partenza.getNodosx();
            } else {
                Nodo risultato = getNodo(partenza.getNododx(), arrivo, domanda);
                if (risultato != null) return risultato;
                return getNodo(partenza.getNodosx(), arrivo, domanda);
            }
        }
        return null;
    }

    // FIX 3: il risultato della ricorsione viene ora ritornato correttamente
    public Nodo getNodoPadre(Nodo partenza, Nodo Padre, Nodo Figlio) {
        if (partenza == null) return null;
        if (partenza.getNododx() != null && partenza.getNododx() == Figlio) {
            return partenza;
        } else if (partenza.getNodosx() != null && partenza.getNodosx() == Figlio) {
            return partenza;
        } else {
            Nodo risultato = getNodoPadre(partenza.getNododx(), Padre, Figlio);
            if (risultato != null) return risultato;
            return getNodoPadre(partenza.getNodosx(), Padre, Figlio);
        }
    }
}