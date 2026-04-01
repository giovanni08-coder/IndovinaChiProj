public abstract class Giocatore {
    private String nome;
    private Albero albero;
    private Personaggio[] tabellone;

    public Giocatore(String nome, Albero albero, Personaggio[] tabellone) {
        this.nome = nome;
        this.albero = albero;
        this.tabellone = tabellone;
    }
    public void EliminaPersonaggio(){

    }
    abstract public String ChiediDomanda();

}
