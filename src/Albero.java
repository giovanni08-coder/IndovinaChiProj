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

    public Nodo getNodo(Nodo partenza, Nodo arrivo, String domanda) { // QUANDO CHIAMI LA FUNZIONE arrivo è null PERCHè è SOLO UNA VARIBILE PER SALVARE IL RISULTATO
        if (partenza != null) {
            if (partenza.getNododx() !=null && partenza.getNododx().getDomanda() == domanda) {
                arrivo = partenza.getNododx();
                return arrivo;
            } else if (partenza.getNodosx() !=null &&partenza.getNodosx().getDomanda() == domanda) {
                arrivo = partenza.getNodosx();
                return arrivo;
            } else {
                getNodo(partenza.getNododx(), arrivo, domanda);
                getNodo(partenza.getNodosx(), arrivo, domanda);
            }
        }
        return arrivo;
    }

    public Nodo getNodoPadre(Nodo partenza, Nodo Padre, Nodo Figlio) {
        if (partenza.getNododx()!= null && partenza.getNododx() == Figlio) {
            Padre = partenza;
        } else if (partenza.getNodosx()!=null && partenza.getNodosx() == Figlio) {
            Padre = partenza;
        } else {
            getNodoPadre(partenza.getNododx(), Padre, Figlio);
            getNodoPadre(partenza.getNodosx(), Padre, Figlio);
        }
        if (Padre==null){
            throw new IllegalArgumentException("Non ho trovato il nodo");
        }
        return Padre;
    }
}
