import java.io.Serializable;

/**
 * Rappresenta un singolo nodo dell'albero binario di decisione del gioco "Indovina Chi".
 * <p>
 * Un nodo può essere di due tipi mutuamente esclusivi:
 * <ul>
 *   <li><b>Nodo domanda</b>: contiene una stringa {@code domanda} e può avere
 *       un figlio destro (risposta "sì") e un figlio sinistro (risposta "no").</li>
 *   <li><b>Nodo foglia</b>: contiene un {@link Personaggio} e non ha figli.</li>
 * </ul>
 * </p>
 *
 * @see Albero
 * @see Personaggio
 */
public class Nodo implements Serializable {

    /** Figlio destro del nodo (corrisponde alla risposta "sì"). */
    private Nodo Nododx;

    /** Figlio sinistro del nodo (corrisponde alla risposta "no"). */
    private Nodo Nodosx;

    /** Testo della domanda, valorizzato solo nei nodi interni. */
    private String domanda;

    /** Personaggio associato al nodo, valorizzato solo nelle foglie. */
    private Personaggio personaggio;

    /**
     * Costruisce un nodo foglia contenente il personaggio specificato.
     * @param personaggio il personaggio associato a questo nodo; non dovrebbe essere {@code null}
     */
    public Nodo(Personaggio personaggio) {
        this.personaggio = personaggio;
    }

    /**
     * Costruisce un nodo interno contenente la domanda specificata.
     * @param domanda il testo della domanda; non dovrebbe essere {@code null} o vuoto
     */
    public Nodo(String domanda) {
        this.domanda = domanda;
    }

    /**
     * Restituisce il figlio sinistro di questo nodo (risposta "no").
     * @return il nodo sinistro, oppure {@code null} se non presente
     */
    public Nodo getNodosx() {
        return Nodosx;
    }

    /**
     * Restituisce il figlio destro di questo nodo (risposta "sì").
     * @return il nodo destro, oppure {@code null} se non presente
     */
    public Nodo getNododx() {
        return Nododx;
    }

    /**
     * Restituisce il testo della domanda associata a questo nodo.
     * @return la domanda, oppure {@code null} se il nodo è una foglia
     */
    public String getDomanda() {
        return domanda;
    }

    /**
     * Restituisce il personaggio associato a questo nodo.
     * @return il personaggio, oppure {@code null} se il nodo è un nodo interno
     */
    public Personaggio getPersonaggio() {
        return personaggio;
    }

    /**
     * Collega un nodo figlio a questo nodo
     * @param nodo: il nodo da collegare come figlio
     * @param dx: {@code true} per collegarlo come figlio destro (risposta "sì"),{@code false} per collegarlo come figlio sinistro (risposta "no")
     */
    public void AggiungiNodo(Nodo nodo, boolean dx) {
        if (dx) {
            this.Nododx = nodo;
        } else {
            this.Nodosx = nodo;
        }
    }
}