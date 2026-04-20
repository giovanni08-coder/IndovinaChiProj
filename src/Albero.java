import java.io.Serializable;
import java.util.ArrayList;

/**
 * Rappresenta un albero binario di decisione utilizzato nel gioco "Indovina Chi".
 * <p>
 * Ogni nodo interno contiene una domanda che divide i personaggi in due gruppi
 * (risposta "sì" → figlio destro, risposta "no" → figlio sinistro).
 * Le foglie contengono un {@link Personaggio}.
 * </p>
 *
 * @see Nodo
 * @see Personaggio
 */
public class Albero implements Serializable {

    /** Radice dell'albero. */
    private Nodo root;

    /**
     * Costruisce un albero con il nodo radice specificato.
     * @param root il nodo radice dell'albero; non dovrebbe essere {@code null}
     */
    public Albero(Nodo root) {
        this.root = root;
    }

    /**
     * Restituisce il nodo radice dell'albero.
     * @return il nodo radice
     */
    public Nodo getRoot() {
        return root;
    }

    /**
     * Aggiunge un nodo all'albero in corrispondenza di {@code NodoArrivo}.
     * <p>
     * Se {@code NodoArrivo} contiene già un personaggio (è una foglia), il nodo
     * {@code nodo} diventa il nuovo nodo intermedio e la vecchia foglia viene
     * ricollocata come suo figlio (destro o sinistro secondo {@code dx}).
     * </p>
     *
     * @param nodo: il nodo da inserire; non può essere {@code null}
     * @param NodoArrivo: il nodo di destinazione in cui inserire
     * @param dx: {@code true} per inserire come figlio destro, {@code false} per sinistro
     * @throws IllegalArgumentException: se {@code nodo} è {@code null} oppure se si tenta di inserire un personaggio sotto a un altro personaggio
     */
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

    /**
     * Metodo ricorsivo ausiliario che raccoglie tutti i personaggi presenti
     * nel sottoalbero radicato in {@code nodo}.
     * @param nodo    radice del sottoalbero da esplorare
     * @param persone lista in cui aggiungere i personaggi trovati
     */
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

    /**
     * Restituisce la lista di tutti i personaggi presenti nel sottoalbero
     * radicato in {@code nodo}.
     * @param nodo la radice del sottoalbero da esplorare; non può essere {@code null}
     * @return lista dei personaggi ancora presenti nel sottoalbero
     * @throws IllegalArgumentException se {@code nodo} è {@code null}
     */
    public ArrayList<Personaggio> getPersoneRimaste(Nodo nodo) {
        if (nodo == null) throw new IllegalArgumentException("Il nodo non può essere null!");
        ArrayList<Personaggio> persone = new ArrayList<>();
        getPersoneRimasteRic(nodo, persone);
        return persone;
    }

    /**
     * Cerca nell'albero il nodo il cui testo di domanda corrisponde a {@code domanda},
     * a partire dal nodo {@code partenza}.
     * <p>
     * La ricerca viene effettuata prima controllando i figli diretti di {@code partenza},
     * poi ricorsivamente sui sottoalberi.
     * </p>
     *
     * @param partenza il nodo da cui iniziare la ricerca
     * @param arrivo   parametro non utilizzato (mantenuto per compatibilità)
     * @param domanda  il testo della domanda da cercare
     * @return il {@link Nodo} con la domanda specificata, oppure {@code null} se non trovato
     */
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

    /**
     * Trova il nodo padre di {@code Figlio} a partire da {@code partenza}.
     * <p>
     * Il confronto tra nodi avviene per riferimento ({@code ==}).
     * </p>
     *
     * @param partenza il nodo da cui iniziare la ricerca
     * @param Padre    parametro non utilizzato (mantenuto per compatibilità)
     * @param Figlio   il nodo di cui si cerca il padre
     * @return il nodo padre di {@code Figlio}, oppure {@code null} se non trovato
     */
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