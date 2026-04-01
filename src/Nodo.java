public abstract class Nodo {
private Nodo Nododx;
private Nodo Nodosx;
private String domanda;
private Personaggio personaggio;

    public Nodo(Personaggio personaggio) {
        this.personaggio = personaggio;
    }

    public Nodo(String domanda) {
        this.domanda = domanda;
    }


    public Nodo getNodosx() {
        return Nodosx;
    }


    public Nodo getNododx() {
        return Nododx;
    }

    public String getDomanda() {
        return domanda;
    }

    public Personaggio getPersonaggio() {
        return personaggio;
    }

    public void AggiungiNodo(Nodo nodo, boolean dx){
        if (dx){
            this.Nododx=nodo;
        }else{
            this.Nodosx= nodo;
        }
    }
}
