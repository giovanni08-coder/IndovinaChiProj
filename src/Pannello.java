import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class Pannello extends JFrame {

    public static final int RISULTATO_NUOVA_PARTITA = 1;
    public static final int RISULTATO_ESCI          = 2;

    private static final Color COL_BG = new Color(15, 12, 41);
    private static final Color COL_PANEL = new Color(26, 22, 65);
    private static final Color COL_ACCENT = new Color(255, 214, 0);
    private static final Color COL_ACCENT2 = new Color(255, 100, 80);
    private static final Color COL_TEXT = new Color(240, 235, 255);
    private static final Color COL_SUBTEXT = new Color(160, 150, 200);

    private Personaggio[] personaggi;
    private JButton[] bottoni;
    private boolean faseScelta       = true;
    private JLabel lblDomanda;
    private JButton btnSi, btnNo;

    private String risposta = null;
    private Personaggio scelto = null;
    private int sceltaDomanda = -1;
    private String sceltaNome = null;
    private int sceltaFinePartita = -1;

    public Pannello(Personaggio[] personaggi) {
        this.personaggi = personaggi;
        this.bottoni    = new JButton[personaggi.length];

        setTitle("Indovina Chi — Partita");
        setSize(960, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COL_BG);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COL_PANEL);
        header.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, COL_ACCENT), new EmptyBorder(14, 24, 14, 24)));

        lblDomanda = new JLabel("Scegli il tuo personaggio", SwingConstants.CENTER);
        lblDomanda.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
        lblDomanda.setForeground(COL_ACCENT);
        header.add(lblDomanda, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 6, 8, 8));
        grid.setBackground(COL_BG);
        grid.setBorder(new EmptyBorder(14, 14, 14, 14));

        for (int i = 0; i < personaggi.length; i++) {
            JButton b = new JButton();
            b.setBackground(COL_PANEL);
            b.setBorder(BorderFactory.createLineBorder(new Color(60, 50, 100), 1));
            b.setFocusPainted(false);
            bottoni[i] = b;
            setImmagineNormale(i);

            final int idx = i;
            b.addActionListener(e -> {
                if (faseScelta) {
                    scelto = personaggi[idx];
                    lblDomanda.setText("✔ Hai scelto: " + scelto.getNome());
                }
            });
            grid.add(b);
        }
        add(new JScrollPane(grid), BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 12));
        footer.setBackground(COL_PANEL);
        footer.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(60, 50, 100)));

        btnSi = creaBottoneRisposta("SÌ",  new Color(40, 160, 90));
        btnNo = creaBottoneRisposta("NO",  COL_ACCENT2);
        btnSi.addActionListener(e -> risposta = "si");
        btnNo.addActionListener(e -> risposta = "no");

        footer.add(btnSi);
        footer.add(btnNo);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    public Personaggio aspettaScelta() {
        lblDomanda.setText("SCEGLI IL TUO PERSONAGGIO");
        while (scelto == null) sleep();
        faseScelta = false;
        return scelto;
    }

    public String aspettaRisposta(String domanda) {
        lblDomanda.setText(domanda);
        lblDomanda.repaint();
        lblDomanda.revalidate();
        risposta = null;
        while (risposta == null) sleep();
        return risposta;
    }

    public int aspettaSceltaDomanda(List<String> domande) {
        sceltaDomanda = -1;

        JDialog dialog = new JDialog(this, "Quale domanda vuoi fare al bot?", true);
        dialog.getContentPane().setBackground(COL_BG);
        dialog.setLayout(new BorderLayout());

        JLabel titolo = new JLabel("Scegli una domanda:", SwingConstants.CENTER);
        titolo.setFont(new Font("Serif", Font.BOLD, 15));
        titolo.setForeground(COL_ACCENT);
        titolo.setBorder(new EmptyBorder(16, 20, 10, 20));
        dialog.add(titolo, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setBackground(COL_BG);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(0, 24, 20, 24));

        for (int i = 0; i < domande.size(); i++) {
            final int indice = i + 1;
            JButton btn = creaBottoneDialog(indice + ")  " + domande.get(i));
            btn.addActionListener(e -> { sceltaDomanda = indice; dialog.dispose(); });
            panel.add(btn);
            panel.add(Box.createVerticalStrut(10));
        }

        dialog.add(panel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setMinimumSize(new Dimension(400, 200));
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        return sceltaDomanda;
    }

    public String aspettaSceltaNome(List<String> nomi) {
        sceltaNome = null;

        JDialog dialog = new JDialog(this, "Indovina il personaggio del bot!", true);
        dialog.getContentPane().setBackground(COL_BG);
        dialog.setLayout(new BorderLayout());

        JLabel titolo = new JLabel("Chi pensi che sia?", SwingConstants.CENTER);
        titolo.setFont(new Font("Serif", Font.BOLD, 15));
        titolo.setForeground(COL_ACCENT);
        titolo.setBorder(new EmptyBorder(16, 20, 10, 20));
        dialog.add(titolo, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setBackground(COL_BG);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(0, 24, 20, 24));

        for (String nome : nomi) {
            JButton btn = creaBottoneDialog(nome);
            btn.addActionListener(e -> { sceltaNome = nome; dialog.dispose(); });
            panel.add(btn);
            panel.add(Box.createVerticalStrut(10));
        }

        dialog.add(panel, BorderLayout.CENTER);
        dialog.pack();
        dialog.setMinimumSize(new Dimension(360, 200));
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        return sceltaNome;
    }

    public void eliminaPersonaggi(ArrayList<Personaggio> daEliminare) {
        for (int i = 0; i < personaggi.length; i++) {
            if (daEliminare.contains(personaggi[i])) {
                setImmagineSbarrata(i);
            }
        }
    }

    public int mostraRisultato(boolean indovinato, Personaggio mioPersonaggio, Personaggio personaggioBot) {
        sceltaFinePartita = -1;
        btnSi.setEnabled(false);
        btnNo.setEnabled(false);

        if (indovinato) {
            lblDomanda.setText("HAI VINTO! Era: " + personaggioBot.getNome());
        } else {
            lblDomanda.setText("HAI PERSO! Il bot ha indovinato: " + mioPersonaggio.getNome());
        }

        JDialog dialog = new JDialog(this, "Fine Partita", true);
        dialog.getContentPane().setBackground(COL_BG);
        dialog.setLayout(new BorderLayout());
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JLabel lblTitolo = new JLabel(
                indovinato ? "🏆  HAI VINTO!" : "💀  HAI PERSO!",
                SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 30));
        lblTitolo.setForeground(indovinato ? COL_ACCENT : COL_ACCENT2);
        lblTitolo.setBorder(new EmptyBorder(24, 20, 8, 20));
        dialog.add(lblTitolo, BorderLayout.NORTH);

        JPanel corpo = new JPanel();
        corpo.setBackground(COL_BG);
        corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));
        corpo.setBorder(new EmptyBorder(0, 32, 20, 32));

        JLabel lblImg = new JLabel();
        lblImg.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            ImageIcon ico = new ImageIcon(personaggioBot.getUrl());
            Image img = ico.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            lblImg.setIcon(new ImageIcon(img));
        } catch (Exception ignored) {}
        corpo.add(lblImg);
        corpo.add(Box.createVerticalStrut(12));

        String msg;
        if (indovinato) {
            msg = String.format("Il personaggio del bot era <b>%s</b>.<br>Complimenti!", personaggioBot.getNome());
        } else {
            msg = String.format("Il bot ha indovinato che eri <b>%s</b>.<br>Ritenta!", mioPersonaggio.getNome());
        }
        JLabel lblMsg = new JLabel("<html><div style='text-align:center;font-family:Serif;font-size:13px;color:#d0c8f0;'>" + msg + "</div></html>", SwingConstants.CENTER);
        lblMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        corpo.add(lblMsg);
        corpo.add(Box.createVerticalStrut(20));

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(80, 70, 130));
        sep.setMaximumSize(new Dimension(300, 2));
        corpo.add(sep);
        corpo.add(Box.createVerticalStrut(18));

        JLabel lblScelta = new JLabel("Cosa vuoi fare?", SwingConstants.CENTER);
        lblScelta.setFont(new Font("Serif", Font.BOLD, 14));
        lblScelta.setForeground(COL_SUBTEXT);
        lblScelta.setAlignmentX(Component.CENTER_ALIGNMENT);
        corpo.add(lblScelta);
        corpo.add(Box.createVerticalStrut(14));

        JButton btnNuova = creaBottoneDialog("▶   NUOVA PARTITA");
        btnNuova.setForeground(COL_BG);
        btnNuova.setBackground(COL_ACCENT);
        btnNuova.addActionListener(e -> { sceltaFinePartita = RISULTATO_NUOVA_PARTITA; dialog.dispose(); });
        corpo.add(btnNuova);
        corpo.add(Box.createVerticalStrut(10));
        JButton btnEsci = creaBottoneDialog("✕   ESCI");
        btnEsci.setForeground(Color.WHITE);
        btnEsci.setBackground(COL_ACCENT2);
        btnEsci.addActionListener(e -> { sceltaFinePartita = RISULTATO_ESCI; dialog.dispose(); });

        corpo.add(btnEsci);
        corpo.add(Box.createVerticalStrut(16));
        dialog.add(corpo, BorderLayout.CENTER);
        dialog.pack();
        dialog.setMinimumSize(new Dimension(380, 460));
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        btnSi.setEnabled(true);
        btnNo.setEnabled(true);
        return sceltaFinePartita;
    }

    private void setImmagineNormale(int i) {
        bottoni[i].setIcon(new ImageIcon(personaggi[i].getUrl()));
    }

    private void setImmagineSbarrata(int i) {
        String sbarrato = personaggi[i].getUrl().replace("Giocatori", "Sbarrati");
        bottoni[i].setIcon(new ImageIcon(sbarrato));
    }

    private JButton creaBottoneRisposta(String testo, Color colore) {
        JButton btn = new JButton(testo);
        btn.setFont(new Font("Serif", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(colore);
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colore.darker(), 1), new EmptyBorder(8, 36, 8, 36)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton creaBottoneDialog(String testo) {
        JButton btn = new JButton(testo);
        btn.setFont(new Font("Serif", Font.BOLD, 14));
        btn.setForeground(COL_TEXT);
        btn.setBackground(new Color(40, 35, 90));
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(80, 70, 140), 1), new EmptyBorder(9, 20, 9, 20)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(320, 44));
        return btn;
    }

    private void sleep() {
        try { Thread.sleep(100); } catch (Exception ignored) {}
    }
}