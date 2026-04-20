import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un personaggio del gioco "Indovina Chi".
 * <p>
 * Ogni personaggio è identificato univocamente dal proprio nome e possiede
 * una serie di caratteristiche fisiche (colore capelli, occhi, pelle, accessori, ecc.)
 * che vengono usate per costruire le domande dell'albero di decisione.
 * </p>
 * <p>
 * La classe mantiene internamente una lista statica dei nomi già usati per
 * impedire la creazione di due personaggi con lo stesso nome.
 * </p>
 *
 * @see ColoriCapelli
 * @see ColoriOcchi
 * @see ColorePelle
 */
public class Personaggio implements Serializable {

    /** Nome univoco del personaggio. */
    private String nome;

    /** Colore dei capelli del personaggio. */
    private ColoriCapelli coloreCapelli;

    /** Colore degli occhi del personaggio. */
    private ColoriOcchi coloreOcchi;

    /** Colore della pelle del personaggio. */
    private ColorePelle colorePelle;

    /** Indica se il personaggio ha il pizzetto. */
    private boolean pizzettto;

    /** Indica se il personaggio ha i baffi. */
    private boolean baffi;

    /** Indica se il personaggio porta orecchini. */
    private boolean orecchini;

    /** Indica se il personaggio porta occhiali. */
    private boolean occhiali;

    /** Indica il sesso del personaggio ({@code true} = maschio, {@code false} = femmina). */
    private boolean sesso;

    /** Indica se il personaggio ha i capelli lunghi. */
    private boolean capelliLunghi;

    /** Indica se il personaggio ha la barba. */
    private boolean barba;

    /** Indica se il personaggio indossa un cappello. */
    private boolean cappello;

    /** Indica se il personaggio è pelato. */
    private boolean pelato;

    /** Registro globale dei nomi già assegnati, per garantire l'unicità. */
    private final static List<String> nomi = new ArrayList<>();

    /** URL dell'immagine del personaggio. */
    private String Url;

    /** Versione di serializzazione per la compatibilità binaria. */
    private static final long serialVersionUID = 4606382356071024846L;

    /**
     * Costruisce un nuovo personaggio con tutte le caratteristiche specificate.
     *
     * @param capelli colore dei capelli
     * @param occhi colore degli occhi
     * @param pelle colore della pelle
     * @param pizzettto {@code true} se ha il pizzetto
     * @param baffi {@code true} se ha i baffi
     * @param orecchini {@code true} se porta orecchini
     * @param occhiali {@code true} se porta occhiali
     * @param sesso {@code true} se maschio, {@code false} se femmina
     * @param capelliLunghi {@code true} se ha i capelli lunghi
     * @param barba {@code true} se ha la barba
     * @param cappello {@code true} se indossa un cappello
     * @param pelato {@code true} se è pelato
     * @param url URL dell'immagine del personaggio
     * @param nome nome univoco del personaggio
     * @throws IllegalArgumentException se il nome è già stato utilizzato da un altro personaggio
     */
    public Personaggio(ColoriCapelli capelli, ColoriOcchi occhi, ColorePelle pelle, boolean pizzettto, boolean baffi, boolean orecchini, boolean occhiali, boolean sesso, boolean capelliLunghi, boolean barba, boolean cappello, boolean pelato, String url, String nome) {
        Url = url;
        setNome(nome);
        setColoreCapelli(capelli);
        setColoreOcchi(occhi);
        setColorePelle(pelle);
        setBaffi(baffi);
        setPizzettto(pizzettto);
        setOrecchini(orecchini);
        setOcchiali(occhiali);
        setSesso(sesso);
        setCapelliLunghi(capelliLunghi);
        setBarba(barba);
        setCappello(cappello);
        setPelato(pelato);
    }

    /**
     * Imposta il nome del personaggio, verificando che non sia già in uso.
     * @param nome il nome da assegnare
     * @throws IllegalArgumentException se il nome è già stato assegnato a un altro personaggio
     */
    public void setNome(String nome) {
        if (nomi.contains(nome)) {
            throw new IllegalArgumentException("Non puoi inserire questo personaggio");
        }
        this.nome = nome;
        nomi.add(nome);
    }

    /**
     * Imposta la presenza del pizzetto.
     * @param pizzettto {@code true} se il personaggio ha il pizzetto
     */
    public void setPizzettto(boolean pizzettto) {
        this.pizzettto = pizzettto;
    }

    /**
     * Imposta la presenza dei baffi.
     * @param baffi {@code true} se il personaggio ha i baffi
     */
    public void setBaffi(boolean baffi) {
        this.baffi = baffi;
    }

    /**
     * Imposta la presenza degli orecchini.
     * @param orecchini {@code true} se il personaggio porta orecchini
     */
    public void setOrecchini(boolean orecchini) {
        this.orecchini = orecchini;
    }

    /**
     * Imposta il colore dei capelli.
     * @param coloreCapelli il valore {@link ColoriCapelli} da assegnare
     */
    public void setColoreCapelli(ColoriCapelli coloreCapelli) {
        this.coloreCapelli = coloreCapelli;
    }

    /**
     * Imposta il colore degli occhi.
     * @param coloreOcchi il valore {@link ColoriOcchi} da assegnare
     */
    public void setColoreOcchi(ColoriOcchi coloreOcchi) {
        this.coloreOcchi = coloreOcchi;
    }

    /**
     * Imposta il colore della pelle.
     * @param colorePelle il valore {@link ColorePelle} da assegnare
     */
    public void setColorePelle(ColorePelle colorePelle) {
        this.colorePelle = colorePelle;
    }

    /**
     * Imposta la presenza degli occhiali.
     * @param occhiali {@code true} se il personaggio porta occhiali
     */
    public void setOcchiali(boolean occhiali) {
        this.occhiali = occhiali;
    }

    /**
     * Imposta il sesso del personaggio.
     * @param sesso {@code true} per maschio, {@code false} per femmina
     */
    public void setSesso(boolean sesso) {
        this.sesso = sesso;
    }

    /**
     * Imposta la presenza di capelli lunghi.
     * @param capelliLunghi {@code true} se il personaggio ha i capelli lunghi
     */
    public void setCapelliLunghi(boolean capelliLunghi) {
        this.capelliLunghi = capelliLunghi;
    }

    /**
     * Imposta la presenza della barba.
     * @param barba {@code true} se il personaggio ha la barba
     */
    public void setBarba(boolean barba) {
        this.barba = barba;
    }

    /**
     * Imposta la presenza del cappello.
     * @param cappello {@code true} se il personaggio indossa un cappello
     */
    public void setCappello(boolean cappello) {
        this.cappello = cappello;
    }

    /**
     * Imposta se il personaggio è pelato.
     * @param pelato {@code true} se il personaggio è pelato
     */
    public void setPelato(boolean pelato) {
        this.pelato = pelato;
    }

    /**
     * Restituisce il nome del personaggio.
     * @return il nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce l'URL dell'immagine del personaggio.
     * @return l'URL come stringa
     */
    public String getUrl() {
        return Url;
    }

    /**
     * Indica se il personaggio ha il pizzetto.
     * @return {@code true} se ha il pizzetto
     */
    public boolean isPizzettto() {
        return pizzettto;
    }

    /**
     * Indica se il personaggio ha i baffi.
     * @return {@code true} se ha i baffi
     */
    public boolean isBaffi() {
        return baffi;
    }

    /**
     * Indica se il personaggio porta orecchini.
     * @return {@code true} se porta orecchini
     */
    public boolean isOrecchini() {
        return orecchini;
    }

    /**
     * Indica se il personaggio porta occhiali.
     * @return {@code true} se porta occhiali
     */
    public boolean isOcchiali() {
        return occhiali;
    }

    /**
     * Indica se il personaggio ha la barba.
     * @return {@code true} se ha la barba
     */
    public boolean isBarba() {
        return barba;
    }

    /**
     * Indica se il personaggio indossa un cappello.
     * @return {@code true} se indossa un cappello
     */
    public boolean isCappello() {
        return cappello;
    }

    /**
     * Indica il sesso del personaggio.
     * @return {@code true} se maschio, {@code false} se femmina
     */
    public boolean isSesso() {
        return sesso;
    }

    /**
     * Restituisce il colore della pelle del personaggio.
     * @return il {@link ColorePelle}
     */
    public ColorePelle getColorePelle() {
        return colorePelle;
    }

    /**
     * Restituisce il colore degli occhi del personaggio.
     * @return il {@link ColoriOcchi}
     */
    public ColoriOcchi getColoreOcchi() {
        return coloreOcchi;
    }

    /**
     * Restituisce il colore dei capelli del personaggio.
     * @return il {@link ColoriCapelli}
     */
    public ColoriCapelli getColoreCapelli() {
        return coloreCapelli;
    }

    /**
     * Verifica l'uguaglianza tra due personaggi in base al nome.
     * @param obj l'oggetto con cui confrontare
     * @return {@code true} se i due personaggi hanno lo stesso nome
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        return this.getNome().equals(((Personaggio) obj).getNome());
    }

    /**
     * Restituisce una rappresentazione testuale del personaggio.
     * @return stringa nel formato {@code Personaggio{nome='...'}}
     */
    @Override
    public String toString() {
        return "Personaggio{" +
                "nome='" + nome + '\'' +
                '}';
    }
}