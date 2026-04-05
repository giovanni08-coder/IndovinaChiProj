import java.io.Serializable;
import java.util.ArrayList;

// Contiene solo i dati necessari per salvare e riprendere la partita.
public class StatoPartita implements Serializable {

    // personaggi ancora visibili sul tabellone dell'umano (quelli non abbassati)
    public ArrayList<String> visibiliUmano;

    // personaggi ancora visibili sul tabellone del computer
    public ArrayList<String> visibiliComputer;

    // nome del personaggio segreto scelto dall'umano
    public String segretoUmano;

    // nome del personaggio segreto scelto dal computer
    public String segretoComputer;

    // quante domande ha già fatto l'umano (per sapere a che punto è nell'albero)
    public int passiUmano;

    // quante domande ha già fatto il computer
    public int passiComputer;

    // true = tocca all'umano, false = tocca al computer
    public boolean turnoUmano;
}