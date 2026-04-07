import javax.swing.JOptionPane;

public class GiocatoreUmano extends Giocatore {

    public GiocatoreUmano(String nome, Albero albero, Personaggio[] tabellone) {
        super(nome, albero, tabellone);
    }

    @Override
    public String ChiediDomanda() {
        String domanda = JOptionPane.showInputDialog(null, "Fai una domanda sul personaggio", "Indovina Chi", JOptionPane.QUESTION_MESSAGE);
        return domanda;
    }
}