import java.util.ArrayList;

public class Albero {
    private Nodo root;

    public Albero(Nodo root) {
        this.root = root;
    }

    public Nodo getRoot() {
        return root;
    }

    public void AggiungiNodo(Nodo nodo, Nodo NodoArrivo, boolean dx) {
        Nodo NodoSostituire;
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

    private void getPersoneRimasteRic(Nodo nodo, ArrayList<Personaggio> persone) {
        if(nodo.getPersonaggio()!=null) persone.add(nodo.getPersonaggio());
        else{
            if(nodo.getNododx()!=null){
                getPersoneRimasteRic(nodo.getNododx(), persone);
            }
            if(nodo.getNodosx()!=null){
                getPersoneRimasteRic(nodo.getNodosx(), persone);
            }
        }
    }

    public ArrayList<Personaggio> getPersoneRimaste(Nodo nodo){
        if (nodo== null) throw new IllegalArgumentException("Il nodo non può essere null!");
        ArrayList<Personaggio> persone = new ArrayList<>();
        getPersoneRimasteRic(nodo, persone);
        return persone;
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
