import javax.swing.*;
<<<<<<< HEAD
import java.awt.*;
import java.util.ArrayList;

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
=======
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Pannello extends JFrame {

        static final Color BG_DARK = new Color(14, 20, 34);
        static final Color BG_CARD = new Color(28, 36, 54);
        static final Color ACCENT_BLUE = new Color(70, 150, 255);
        static final Color TEXT_WHITE = new Color(238, 240, 250);

        private Personaggio[] tutti;
        private boolean[] elimU;
        private JPanel tabU;

        public Pannello() {
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
}
>>>>>>> 2788e406bfd495f9b2e98a8451b9916158e89dee
