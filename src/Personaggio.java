import java.io.Serializable;
import java.util.List;

public class Personaggio implements Serializable {
    private List<String> caratteristiche;
    private String nome;
    private String Url;

    public Personaggio(List<String> caratteristiche, String url, String nome) {
        this.caratteristiche = caratteristiche;
        Url = url;
        this.nome = nome;
    }

    public String getNome() {
        return nome; }

    public String getUrl()  {
        return Url; }

}
