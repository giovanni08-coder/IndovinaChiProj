import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Personaggio implements Serializable {

    private String nome;
    private Map<String, String> caratteristiche;
    private boolean attivo;

    public Personaggio(String nome) {
        this.nome = nome;
        this.caratteristiche = new HashMap<>();
        this.attivo = true;
    }

    public void aggiungiCaratteristica(String chiave, String valore) {
        caratteristiche.put(chiave.toLowerCase(), valore.toLowerCase());
    }

    public String getCaratteristica(String chiave) {
        return caratteristiche.get(chiave.toLowerCase());
    }

    public boolean verificaCaratteristica(String chiave, String valore) {
        String val = caratteristiche.get(chiave.toLowerCase());
        if (val == null){
            return false;
        }
        return val.equalsIgnoreCase(valore);
    }

    public String getNome() {
        return nome;
    }

    public boolean isAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    @Override
    public String toString() {
        return nome + " | " + caratteristiche.toString();
    }

    public int confronta(Personaggio altro) {
        int differenze = 0;

        for (String chiave : caratteristiche.keySet()) {
            String val1 = caratteristiche.get(chiave);
            String val2 = altro.getCaratteristica(chiave);

            if (val2 == null || !val1.equals(val2)) {
                differenze++;
            }
        }

        return differenze;
    }
}