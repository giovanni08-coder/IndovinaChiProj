import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Pannello extends JFrame {

    private Personaggio[] personaggi;
    private JButton[] bottoni;
    private boolean faseScelta = true;
    private JLabel lblDomanda;
    private JButton btnSi, btnNo;

    private String risposta = null;
    private Personaggio scelto = null;

    public Pannello(Personaggio[] personaggi) {
        this.personaggi = personaggi;
        this.bottoni = new JButton[personaggi.length];

        setTitle("Indovina Chi");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== DOMANDA =====
        lblDomanda = new JLabel("Scegli il tuo personaggio", SwingConstants.CENTER);
        lblDomanda.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblDomanda, BorderLayout.NORTH);

        // ===== GRIGLIA =====
        JPanel panelGrid = new JPanel(new GridLayout(0, 6, 10, 10));

        for (int i = 0; i < personaggi.length; i++) {
            JButton b = new JButton();
            bottoni[i] = b;

            setImmagineNormale(i);

            int index = i;

            // SCELTA PERSONAGGIO
            b.addActionListener(e -> {
                if (faseScelta) {
                    scelto = personaggi[index];
                    lblDomanda.setText("Hai scelto: " + scelto.getNome());
                }
            });

            panelGrid.add(b);
        }

        add(new JScrollPane(panelGrid), BorderLayout.CENTER);

        // ===== BOTTONI =====
        JPanel panelBottoni = new JPanel();

        btnSi = new JButton("SI");
        btnNo = new JButton("NO");

        panelBottoni.add(btnSi);
        panelBottoni.add(btnNo);

        add(panelBottoni, BorderLayout.SOUTH);

        btnSi.addActionListener(e -> risposta = "si");
        btnNo.addActionListener(e -> risposta = "no");

        setVisible(true);
    }

    // ===== SCELTA PERSONAGGIO =====
    public Personaggio aspettaScelta() {
        lblDomanda.setText("SCEGLI IL TUO PERSONAGGIO");

        while (scelto == null) {
            sleep();
        }

        // 🔥 DOPO LA SCELTA BLOCCA I CLICK
        faseScelta = false;

        return scelto;
    }

    // ===== DOMANDA =====
    public String aspettaRisposta(String domanda) {
        lblDomanda.setText(domanda);

        // 🔥 FORZA AGGIORNAMENTO GRAFICO
        lblDomanda.repaint();
        lblDomanda.revalidate();

        risposta = null;

        while (risposta == null) {
            sleep();
        }

        return risposta;
    }

    // ===== ELIMINA =====
    public void eliminaPersonaggi(ArrayList<Personaggio> daEliminare) {
        for (int i = 0; i < personaggi.length; i++) {
            if (daEliminare.contains(personaggi[i])) {
                setImmagineSbarrata(i);
            }
        }
    }

    public void mostraRisultato(boolean indovinato, Personaggio scelto, Personaggio trovato) {

        if (indovinato) {
            lblDomanda.setText("HO INDOVINATO! È: " + trovato.getNome());
        } else {
            lblDomanda.setText("NON HO INDOVINATO! Tu avevi: " + scelto.getNome());
        }

        // disabilita bottoni
        btnSi.setEnabled(false);
        btnNo.setEnabled(false);

        JOptionPane.showMessageDialog(this,
                "Risultato finale",
                "Fine partita",
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon(trovato.getUrl()));
    }

    // ===== IMMAGINI REALI =====

    private void setImmagineNormale(int i) {
        String path = personaggi[i].getUrl(); // 🔥 USA QUELLO GIUSTO
        bottoni[i].setIcon(new ImageIcon(path));
    }

    private void setImmagineSbarrata(int i) {
        // 🔥 VERSIONE SBARRATA (devi avere questi file)
        String originale = personaggi[i].getUrl();

        // esempio: Immagini/Giocatori/1.png → Immagini/Sbarrati/1.png
        String sbarrato = originale.replace("Giocatori", "Sbarrati");

        bottoni[i].setIcon(new ImageIcon(sbarrato));
    }

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (Exception e) {}
    }
}
