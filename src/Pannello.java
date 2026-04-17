import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class Pannello extends JFrame {

    private Personaggio[] personaggi;
    private JButton[] bottoni;
    private boolean faseScelta = true;
    private JLabel lblDomanda;
    private JButton btnSi, btnNo;

    private String risposta = null;
    private Personaggio scelto = null;

    // Usato da aspettaSceltaDomanda
    private int sceltaDomanda = -1;

    // Usato da aspettaSceltaNome
    private String sceltaNome = null;

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

        // ===== GRIGLIA PERSONAGGI =====
        JPanel panelGrid = new JPanel(new GridLayout(0, 6, 10, 10));
        for (int i = 0; i < personaggi.length; i++) {
            JButton b = new JButton();
            bottoni[i] = b;
            setImmagineNormale(i);
            int index = i;
            b.addActionListener(e -> {
                if (faseScelta) {
                    scelto = personaggi[index];
                    lblDomanda.setText("Hai scelto: " + scelto.getNome());
                }
            });
            panelGrid.add(b);
        }
        add(new JScrollPane(panelGrid), BorderLayout.CENTER);

        // ===== BOTTONI SI / NO =====
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
        faseScelta = false;
        return scelto;
    }

    // ===== RISPOSTA SI/NO A UNA DOMANDA =====
    public String aspettaRisposta(String domanda) {
        lblDomanda.setText(domanda);
        lblDomanda.repaint();
        lblDomanda.revalidate();
        risposta = null;
        while (risposta == null) {
            sleep();
        }
        return risposta;
    }

    // ===== SCELTA DOMANDA DA FARE AL BOT =====
    // Mostra un dialog con i bottoni delle domande disponibili e aspetta che il
    // giocatore ne scelga una. Restituisce l'indice 1-based della domanda scelta
    // (coerente con l'originale "dai 1...n").
    public int aspettaSceltaDomanda(List<String> domande) {
        sceltaDomanda = -1;

        // Costruisce un pannello con un bottone per ogni domanda
        JPanel panelDomande = new JPanel(new GridLayout(0, 1, 5, 5));
        panelDomande.setBorder(new EmptyBorder(10, 10, 10, 10));

        JDialog dialog = new JDialog(this, "Quale domanda vuoi fare al bot?", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(new JLabel("Scegli una domanda da fare al bot:", SwingConstants.CENTER), BorderLayout.NORTH);

        for (int i = 0; i < domande.size(); i++) {
            final int indice = i + 1; // 1-based
            JButton btnDomanda = new JButton(indice + ") " + domande.get(i));
            btnDomanda.addActionListener(e -> {
                sceltaDomanda = indice;
                dialog.dispose();
            });
            panelDomande.add(btnDomanda);
        }

        dialog.add(panelDomande, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true); // blocca finché non viene chiuso

        return sceltaDomanda;
    }

    // ===== SCELTA NOME PERSONAGGIO DA INDOVINARE =====
    // Mostra un dialog con i nomi dei personaggi rimasti del bot.
    // Restituisce il nome scelto dal giocatore.
    public String aspettaSceltaNome(List<String> nomi) {
        sceltaNome = null;

        JPanel panelNomi = new JPanel(new GridLayout(0, 1, 5, 5));
        panelNomi.setBorder(new EmptyBorder(10, 10, 10, 10));

        JDialog dialog = new JDialog(this, "Indovina il personaggio del bot!", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(new JLabel("Chi pensi che sia il personaggio del bot?", SwingConstants.CENTER), BorderLayout.NORTH);

        for (String nome : nomi) {
            JButton btnNome = new JButton(nome);
            btnNome.addActionListener(e -> {
                sceltaNome = nome;
                dialog.dispose();
            });
            panelNomi.add(btnNome);
        }

        dialog.add(panelNomi, BorderLayout.CENTER);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true); // blocca finché non viene chiuso

        return sceltaNome;
    }

    // ===== ELIMINA PERSONAGGI (li barra nella griglia) =====
    public void eliminaPersonaggi(ArrayList<Personaggio> daEliminare) {
        for (int i = 0; i < personaggi.length; i++) {
            if (daEliminare.contains(personaggi[i])) {
                setImmagineSbarrata(i);
            }
        }
    }

    // ===== RISULTATO FINALE =====
    public void mostraRisultato(boolean indovinato, Personaggio scelto, Personaggio trovato) {
        if (indovinato) {
            lblDomanda.setText("HAI INDOVINATO! Era: " + trovato.getNome());
        } else {
            lblDomanda.setText("NON HAI INDOVINATO! Tu avevi: " + scelto.getNome());
        }
        btnSi.setEnabled(false);
        btnNo.setEnabled(false);
        JOptionPane.showMessageDialog(this,
                "Risultato finale",
                "Fine partita",
                JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon(trovato.getUrl()));
    }

    // ===== IMMAGINI =====
    private void setImmagineNormale(int i) {
        String path = personaggi[i].getUrl();
        bottoni[i].setIcon(new ImageIcon(path));
    }

    private void setImmagineSbarrata(int i) {
        String originale = personaggi[i].getUrl();
        String sbarrato = originale.replace("Giocatori", "Sbarrati");
        bottoni[i].setIcon(new ImageIcon(sbarrato));
    }

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (Exception e) {}
    }
}