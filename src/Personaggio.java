import Pelle.ColorePelle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Personaggio implements Serializable {
    private String nome;
    private ColoriCapelli coloreCapelli;
    private ColoriOcchi coloreOcchi;
    private ColorePelle colorePelle;
    private boolean pizzettto;
    private boolean baffi;
    private boolean orecchini;
    private boolean occhiali;
    private boolean sesso;
    private boolean capelliLunghi;
    private boolean barba;
    private boolean cappello;
    private boolean pelato;
    private String percorsoImmagine;
    private final static List<String> nomi = new ArrayList<>();
    private String Url;

    public Personaggio(ColoriCapelli capelli,ColoriOcchi occhi,ColorePelle pelle,boolean pizzettto, boolean baffi,boolean orecchini,boolean occhiali,boolean sesso, boolean capelliLunghi,boolean barba,boolean cappello,boolean pelato,String url, String nome) {
        Url = url;
        setNome(nome);
        setColoreCapelli(capelli);
        setColoreOcchi(occhi);
        setColorePelle(pelle);
        setBaffi(baffi);
        setBarba(barba);
        setOrecchini(orecchini);
        setOcchiali(occhiali);
        setSesso(sesso);
        setCapelliLunghi(capelliLunghi);
        setBarba(barba);
        setCappello(cappello);
        setPelato(pelato);
    }

    public void setNome(String nome) {
        if (nomi.contains(nome)){
            throw new IllegalArgumentException("Non puoi inserire questo personaggio");
        }
        this.nome = nome;
    }

    public void setPizzettto(boolean pizzettto) {
        this.pizzettto = pizzettto;
    }

    public void setBaffi(boolean baffi) {
        this.baffi = baffi;
    }

    public void setOrecchini(boolean orecchini) {
        this.orecchini = orecchini;
    }

    public void setColoreCapelli(ColoriCapelli coloreCapelli) {
        this.coloreCapelli = coloreCapelli;
    }

    public void setColoreOcchi(ColoriOcchi coloreOcchi) {
        this.coloreOcchi = coloreOcchi;
    }

    public void setColorePelle(ColorePelle colorePelle) {
        this.colorePelle = colorePelle;
    }

    public void setOcchiali(boolean occhiali) {
        this.occhiali = occhiali;
    }

    public void setSesso(boolean sesso) {
        this.sesso = sesso;
    }

    public void setCapelliLunghi(boolean capelliLunghi) {
        this.capelliLunghi = capelliLunghi;
    }

    public void setBarba(boolean barba) {
        this.barba = barba;
    }

    public void setCappello(boolean cappello) {
        this.cappello = cappello;
    }

    public void setPelato(boolean pelato) {
        this.pelato = pelato;
    }

    public void setPercorsoImmagine(String percorsoImmagine) {
        this.percorsoImmagine = percorsoImmagine;
    }

    public String getNome() {
        return nome; }

    public String getUrl()  {
        return Url; }

}
