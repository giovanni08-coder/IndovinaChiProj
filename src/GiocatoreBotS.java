public class GiocatoreBotS extends Giocatore {
    public GiocatoreBotS(String nome, Albero albero, Personaggio[] tabellone) {
        super(nome, albero, tabellone);
    }

    @Override
    public String ChiediDomanda(Nodo nodoCorrente) {
        if (nodoCorrente == null || nodoCorrente.getDomanda() == null) {
           throw new IllegalArgumentException("Hai finito le domande e hai trovato il personaggio finale");
        }
        return nodoCorrente.getDomanda();
    }
}
