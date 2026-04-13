import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class MainDinamico extends JFrame {

    static final Color BG_DARK = new Color(14, 20, 34);
    static final Color BG_CARD = new Color(28, 36, 54);
    static final Color ACCENT_BLUE = new Color(70, 150, 255);
    static final Color TEXT_WHITE = new Color(238, 240, 250);

    private Personaggio[] tutti;
    private boolean[] elimU;
    private JPanel tabU;

    public MainDinamico() {
        super("Indovina Chi!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);

        caricaDati();
        buildUI();
    }

    private void caricaDati() {
        try {
            tutti = GestoreFile.Leggi_binarioPersonaggi();
            elimU = new boolean[tutti.length];
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errore caricamento dati");
            System.exit(1);
        }
    }

    private void buildUI() {
        setLayout(new BorderLayout());

        tabU = new JPanel(new GridLayout(0, 6, 8, 8));
        tabU.setBorder(new EmptyBorder(20, 20, 20, 20));
        tabU.setBackground(BG_DARK);

        for (int i = 0; i < tutti.length; i++) {
            int index = i;
            JButton btn = new JButton(tutti[i].getNome());
            btn.setBackground(BG_CARD);
            btn.setForeground(TEXT_WHITE);

            btn.addActionListener(e -> {
                elimU[index] = !elimU[index];
                btn.setEnabled(!elimU[index]);
            });

            tabU.add(btn);
        }

        add(new JScrollPane(tabU), BorderLayout.CENTER);
    }

    void EliminazioneDomandeInutili(Map<String, Map<String, ArrayList<Personaggio>>> GeneriDomande,Personaggio[] personaggi){
        int conta;
        boolean caratteristicaPresente;
        for (String genere : new ArrayList<>(GeneriDomande.keySet())) {
            Map<String, ArrayList<Personaggio>> domande = GeneriDomande.get(genere);
            for(String domanda: new ArrayList<>(domande.keySet())){
                conta=0;
                caratteristicaPresente=false;
                for (Personaggio p: personaggi){
                    if (domande.get(domanda).contains(p)){
                        caratteristicaPresente=true;
                        break;
                    }
                }
                for (Personaggio pers: personaggi){
                    if (domande.get(domanda).contains(pers)){
                        conta++;
                    }
                }
                if (!caratteristicaPresente){
                    domande.remove(domanda);
                }
                if (caratteristicaPresente && conta == personaggi.length){
                    domande.remove(domanda);
                }
            }
            if (domande.isEmpty())
                GeneriDomande.remove(genere);
        }
    }

    Personaggio[] VerificaRisposta(Personaggio MiopersonaggioRandom,Personaggio[] MieiPersonaggi,String scelta,String GenereRandom,String DomandaRandom,Map<String, Map<String, ArrayList<Personaggio>>> MieiGeneriDomande){
        Personaggio[] CopiaMieiPersonaggi = MieiPersonaggi.clone();
        if (scelta.equals("si")) {
            for (int i = 0; i < CopiaMieiPersonaggi.length; i++) {
                if (!(MieiGeneriDomande.get(GenereRandom).get(DomandaRandom).contains(MieiPersonaggi[i]))) {
                    CopiaMieiPersonaggi[i] = null;
                }
            }
        } else if (scelta.equals("no")) {
            for (int i = 0; i < CopiaMieiPersonaggi.length; i++) {
                if (MieiGeneriDomande.get(GenereRandom).get(DomandaRandom).contains(MieiPersonaggi[i])) {
                    CopiaMieiPersonaggi[i] = null;
                }
            }

        } else {
            throw new IllegalArgumentException("Non puoi inserire questa risposta");
        }
        List<Personaggio> lista = new ArrayList<>(Arrays.asList(CopiaMieiPersonaggi));
        for (int i=0;i<lista.size();i++){
            if (lista.get(i)==null) {
                lista.remove(i);
                i--;
            }

        }
        if (!lista.contains(MiopersonaggioRandom)){
            throw new IllegalArgumentException("HAI MENTITO");
        }
        else {
            MieiPersonaggi = lista.toArray(new Personaggio[0]);
            if (scelta.equals("si")){
                MieiGeneriDomande.remove(GenereRandom);
            }
            if (scelta.equals("no")){
                if (GenereRandom.equals("Capelli")){
                    MieiGeneriDomande.get("Capelli").remove(DomandaRandom);
                }
                else {
                    MieiGeneriDomande.remove(GenereRandom);
                }
            }
        }
        return MieiPersonaggi;
    }

    public static void main(String[] args) {
        try {
            MainDinamico frame = new MainDinamico();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
