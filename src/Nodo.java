public class Nodo {
private Nodo Nododx;
private Nodo Nodosx;
private String domanda;
private Personaggio personaggio;

    public Nodo(String domanda) {
        this.domanda = domanda;
    }

    public Nodo(Personaggio personaggio) {
        this.personaggio = personaggio;
    }

    public Nodo getNodosx() {
        return Nodosx;
    }

    public void setNodosx(Nodo nodosx) {
        Nodosx = nodosx;
    }

    public Nodo getNododx() {
        return Nododx;
    }

    public void setNododx(Nodo nododx) {
        Nododx = nododx;
    }
    public void AggiungiNodo(Nodo nodo,boolean dx){
        if (dx){
            this.Nododx=nodo;
        }else{
            this.Nodosx= nodo;
        }
    }
}
