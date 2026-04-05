public class GiocatoreBot extends Giocatore {


    public GiocatoreBot(String nome, Albero albero, Personaggio[] tabellone) {
        super(nome, albero, tabellone);
    }

    @Override
    public String ChiediDomanda() {
        return "";
    }
}
