import java.util.ArrayList;

public class Albero {
    private Nodo root;

    public Albero(Nodo root) {
        this.root = root;
    }

    public void AggiungiNodo(Nodo nodo, Nodo NodoArrivo, boolean dx) {
        Nodo NodoSostituire = null;
       if (getNodo(NodoArrivo,NodoArrivo.getDomanda())==null){
           throw new IllegalArgumentException("Non puoi aggiungere ad un nodo che non esiste");
       }
        if (NodoArrivo.getPersonaggio() != null){
            if (nodo.getPersonaggio() != null){
                throw new IllegalArgumentException("Non puoi inserire un personaggio sotto ad un personaggio");
            }
            else{
                NodoSostituire = NodoArrivo;
                NodoArrivo = nodo;
                NodoArrivo.AggiungiNodo(NodoSostituire,dx);

            }
        }
        else {
            NodoArrivo.AggiungiNodo(nodo, dx);
        }
    }

    public Personaggio[] getPersone(String domanda, boolean dx) {
        Nodo nodoDomanda = getNodo(root,domanda,dx);
        if (nodoDomanda.getNododx()== null){

        }

    }

    public Nodo getNodo(Nodo partenza, String domanda) {
        Nodo arrivo = null;
        if (partenza.getDomanda() != null) {
                if (partenza.getNododx().getDomanda() == domanda) {
                    arrivo = partenza;
                } else {
                    getNodo(partenza.getNododx(), domanda);
                }

            if (partenza.getNodosx().getDomanda() == domanda) {
                arrivo = partenza;
            } else {
                getNodo(partenza.getNodosx(), domanda);
            }
        }
        if (arrivo==null){
            throw new IllegalArgumentException("Non ho trovato il nodo che mi hai detto");
        }
        return arrivo;
    }
}
