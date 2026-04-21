import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class Pannello extends JFrame {

    public static final int RISULTATO_NUOVA_PARTITA = 1;
    public static final int RISULTATO_ESCI = 2;

    private static final Color COL_BG      = new Color(15, 12, 41);
    private static final Color COL_PANEL   = new Color(26, 22, 65);
    private static final Color COL_ACCENT  = new Color(255, 214, 0);
    private static final Color COL_ACCENT2 = new Color(255, 100, 80);
    private static final Color COL_TEXT    = new Color(240, 235, 255);
    private static final Color COL_SUBTEXT = new Color(160, 150, 200);
    private static final Color COL_BOT_BG  = new Color(10, 8, 30);
    private static final Color COL_GREEN   = new Color(40, 160, 90);

    private Personaggio[] personaggi;
    private JButton[]     bottoni;
    private Personaggio[] personaggiBot;
    private JButton[]     bottoniBot;
    private JLabel        lblContatoreBot;

    private boolean     faseScelta        = true;
    private String      risposta          = null;
    private Personaggio scelto            = null;
    private int         sceltaDomanda     = -1;
    private String      sceltaNome        = null;
    private int         sceltaFinePartita = -1;

    private JLabel  lblDomanda;
    private JPanel  footer;
    private JButton btnSi, btnNo;
    private JPanel  panDomande;

    public Pannello(Personaggio[] personaggi, Personaggio[] personaggiBot) {
        this.personaggi    = personaggi;
        this.bottoni       = new JButton[personaggi.length];
        this.personaggiBot = personaggiBot;
        this.bottoniBot    = new JButton[personaggiBot.length];

        setTitle("Indovina Chi — Partita");
        setSize(1400, 820);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COL_BG);

        // header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COL_PANEL);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, COL_ACCENT),
                new EmptyBorder(14, 24, 14, 24)));
        lblDomanda = new JLabel("Scegli il tuo personaggio", SwingConstants.CENTER);
        lblDomanda.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
        lblDomanda.setForeground(COL_ACCENT);
        header.add(lblDomanda, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // tabelloni
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                creaTabelloneGiocatore(), creaTabelloneBot());
        split.setDividerLocation(700);
        split.setDividerSize(6);
        split.setBackground(COL_BG);
        split.setBorder(null);
        add(split, BorderLayout.CENTER);

        // footer
        footer = new JPanel(new BorderLayout());
        footer.setBackground(COL_PANEL);
        footer.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(60, 50, 100)));

        JPanel footerBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 12));
        footerBtnPanel.setBackground(COL_PANEL);
        btnSi = creaBottoneRisposta("SI'",  COL_GREEN);
        btnNo = creaBottoneRisposta("NO",   COL_ACCENT2);
        btnSi.addActionListener(e -> risposta = "si");
        btnNo.addActionListener(e -> risposta = "no");
        footerBtnPanel.add(btnSi);
        footerBtnPanel.add(btnNo);
        footer.add(footerBtnPanel, BorderLayout.NORTH);

        panDomande = new JPanel();
        panDomande.setBackground(COL_PANEL);
        panDomande.setVisible(false);
        footer.add(panDomande, BorderLayout.CENTER);

        add(footer, BorderLayout.SOUTH);
        setVisible(true);
    }

    private JPanel creaTabelloneGiocatore() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COL_BG);

        JLabel lbl = new JLabel("I TUOI PERSONAGGI", SwingConstants.CENTER);
        lbl.setFont(new Font("Serif", Font.BOLD, 13));
        lbl.setForeground(COL_ACCENT);
        lbl.setBorder(new EmptyBorder(8, 8, 6, 8));
        wrapper.add(lbl, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 4, 6, 6));
        grid.setBackground(COL_BG);
        grid.setBorder(new EmptyBorder(8, 10, 8, 6));

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
                    lblDomanda.setText("Hai scelto: " + scelto.getNome());
                }
            });
            grid.add(b);
        }

        JScrollPane sp = new JScrollPane(grid);
        sp.setBorder(null);
        sp.getViewport().setBackground(COL_BG);
        wrapper.add(sp, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel creaTabelloneBot() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COL_BOT_BG);

        JPanel intestazione = new JPanel(new BorderLayout());
        intestazione.setBackground(COL_BOT_BG);
        intestazione.setBorder(new EmptyBorder(8, 8, 6, 8));

        JLabel lbl = new JLabel("PERSONAGGI DEL BOT", SwingConstants.CENTER);
        lbl.setFont(new Font("Serif", Font.BOLD, 13));
        lbl.setForeground(COL_ACCENT2);
        intestazione.add(lbl, BorderLayout.CENTER);

        lblContatoreBot = new JLabel("Rimasti: " + personaggiBot.length, SwingConstants.RIGHT);
        lblContatoreBot.setFont(new Font("Serif", Font.ITALIC, 12));
        lblContatoreBot.setForeground(COL_SUBTEXT);
        intestazione.add(lblContatoreBot, BorderLayout.EAST);
        wrapper.add(intestazione, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(0, 4, 6, 6));
        grid.setBackground(COL_BOT_BG);
        grid.setBorder(new EmptyBorder(8, 6, 8, 10));

        for (int i = 0; i < personaggiBot.length; i++) {
            JButton b = new JButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    g.setColor(getBackground());
                    g.fillRect(0, 0, getWidth(), getHeight());
                    Icon ic = getIcon();
                    if (ic != null) {
                        int x = (getWidth()  - ic.getIconWidth())  / 2;
                        int y = (getHeight() - ic.getIconHeight()) / 2;
                        ic.paintIcon(this, g, x, y);
                    }
                }
            };
            b.setBackground(new Color(35, 28, 80));
            b.setBorder(BorderFactory.createLineBorder(new Color(80, 40, 40), 1));
            b.setFocusPainted(false);
            b.setEnabled(false);
            bottoniBot[i] = b;
            setImmagineBotNormale(i);
            grid.add(b);
        }

        JScrollPane sp = new JScrollPane(grid);
        sp.setBorder(null);
        sp.getViewport().setBackground(COL_BOT_BG);
        wrapper.add(sp, BorderLayout.CENTER);
        return wrapper;
    }

    // ── API pubblica ──────────────────────────────────────────────────────────

    public Personaggio aspettaScelta() {
        lblDomanda.setText("SCEGLI IL TUO PERSONAGGIO SEGRETO");
        while (scelto == null) sleep();
        faseScelta = false;
        return scelto;
    }

    public String aspettaRisposta(String domanda) {
        SwingUtilities.invokeLater(() -> {
            lblDomanda.setText(domanda);
            lblDomanda.setForeground(COL_ACCENT);
            nascondiPanDomande();
            mostraBottoniSiNo(true);
        });
        risposta = null;
        while (risposta == null) sleep();
        return risposta;
    }

    public int aspettaSceltaDomanda(List<String> domande) {
        sceltaDomanda = -1;
        SwingUtilities.invokeLater(() -> {
            lblDomanda.setText("Scegli la domanda da fare al bot:");
            lblDomanda.setForeground(COL_ACCENT);
            mostraBottoniSiNo(false);

            panDomande.removeAll();
            panDomande.setLayout(new FlowLayout(FlowLayout.CENTER, 14, 10));
            panDomande.setBackground(COL_PANEL);

            for (int i = 0; i < domande.size(); i++) {
                final int indice = i + 1;
                JButton btn = creaBottoneInline(indice + ")  " + domande.get(i));
                btn.addActionListener(e -> {
                    sceltaDomanda = indice;
                    nascondiPanDomande();
                });
                panDomande.add(btn);
            }

            panDomande.setVisible(true);
            footer.revalidate();
            footer.repaint();
        });

        while (sceltaDomanda == -1) sleep();
        return sceltaDomanda;
    }

    public String aspettaSceltaNome(List<String> nomi) {
        sceltaNome = null;
        SwingUtilities.invokeLater(() -> {
            lblDomanda.setText("Chi pensi che sia il personaggio del bot?");
            lblDomanda.setForeground(COL_ACCENT);
            mostraBottoniSiNo(false);

            panDomande.removeAll();
            panDomande.setLayout(new FlowLayout(FlowLayout.CENTER, 14, 10));
            panDomande.setBackground(COL_PANEL);

            for (String nome : nomi) {
                JButton btn = creaBottoneInline(nome);
                btn.addActionListener(e -> {
                    sceltaNome = nome;
                    nascondiPanDomande();
                });
                panDomande.add(btn);
            }

            panDomande.setVisible(true);
            footer.revalidate();
            footer.repaint();
        });

        while (sceltaNome == null) sleep();
        return sceltaNome;
    }

    public void eliminaPersonaggi(ArrayList<Personaggio> daEliminare) {
        for (int i = 0; i < personaggi.length; i++) {
            if (daEliminare.contains(personaggi[i])) setImmagineSbarrata(i);
        }
    }

    public void aggiornaPersonaggiBot(Personaggio[] candidatiRimasti) {
        int rimasti = 0;
        for (int i = 0; i < personaggiBot.length; i++) {
            boolean ancora = false;
            for (Personaggio c : candidatiRimasti) {
                if (personaggiBot[i].equals(c)) { ancora = true; }
            }
            if (ancora) { rimasti++; setImmagineBotNormale(i); }
            else        { setImmagineBotSbarrata(i); }
        }
        lblContatoreBot.setText("Rimasti: " + rimasti);
        lblContatoreBot.repaint();
    }

    public int mostraRisultato(boolean indovinato, Personaggio mioPersonaggio, Personaggio personaggioBot) {
        sceltaFinePartita = -1;

        SwingUtilities.invokeLater(() -> {
            mostraBottoniSiNo(false);

            if (indovinato) {
                lblDomanda.setText("HAI VINTO! Il personaggio del bot era: " + personaggioBot.getNome());
                lblDomanda.setForeground(COL_ACCENT);
            } else {
                lblDomanda.setText("HAI PERSO! Il bot ha indovinato che eri: " + mioPersonaggio.getNome());
                lblDomanda.setForeground(COL_ACCENT2);
            }

            panDomande.removeAll();
            panDomande.setLayout(new BorderLayout());
            panDomande.setBackground(COL_PANEL);

            JPanel corpo = new JPanel();
            corpo.setBackground(COL_PANEL);
            corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));
            corpo.setBorder(new EmptyBorder(8, 0, 12, 0));

            // miniatura personaggio
            JLabel lblImg = new JLabel();
            lblImg.setAlignmentX(Component.CENTER_ALIGNMENT);
            try {
                ImageIcon ico = new ImageIcon(personaggioBot.getUrl());
                Image img = ico.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                lblImg.setIcon(new ImageIcon(img));
            } catch (Exception ignored) {}
            corpo.add(lblImg);
            corpo.add(Box.createVerticalStrut(8));

            String msg = indovinato
                    ? "<html><center>Era <b>" + personaggioBot.getNome() + "</b>. Complimenti!</center></html>"
                    : "<html><center>Il bot ha indovinato che eri <b>" + mioPersonaggio.getNome() + "</b>. Ritenta!</center></html>";
            JLabel lblMsg = new JLabel(msg, SwingConstants.CENTER);
            lblMsg.setFont(new Font("Serif", Font.PLAIN, 13));
            lblMsg.setForeground(COL_TEXT);
            lblMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
            corpo.add(lblMsg);
            corpo.add(Box.createVerticalStrut(10));

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
            btnPanel.setBackground(COL_PANEL);

            JButton btnNuova = creaBottoneRisposta("NUOVA PARTITA", COL_ACCENT);
            btnNuova.setForeground(COL_BG);
            btnNuova.addActionListener(e -> sceltaFinePartita = RISULTATO_NUOVA_PARTITA);
            btnPanel.add(btnNuova);

            JButton btnEsci = creaBottoneRisposta("ESCI", COL_ACCENT2);
            btnEsci.setForeground(Color.WHITE);
            btnEsci.addActionListener(e -> sceltaFinePartita = RISULTATO_ESCI);
            btnPanel.add(btnEsci);

            corpo.add(btnPanel);
            panDomande.add(corpo, BorderLayout.CENTER);
            panDomande.setVisible(true);
            footer.revalidate();
            footer.repaint();
        });

        while (sceltaFinePartita == -1) sleep();
        return sceltaFinePartita;
    }

    // ── helper ────────────────────────────────────────────────────────────────

    private void mostraBottoniSiNo(boolean visibili) {
        btnSi.setVisible(visibili);
        btnNo.setVisible(visibili);
        btnSi.getParent().revalidate();
        btnSi.getParent().repaint();
    }

    private void nascondiPanDomande() {
        panDomande.setVisible(false);
        panDomande.removeAll();
        footer.revalidate();
        footer.repaint();
    }

    private void setImmagineNormale(int i) {
        bottoni[i].setIcon(new ImageIcon(personaggi[i].getUrl()));
    }

    private void setImmagineSbarrata(int i) {
        bottoni[i].setIcon(new ImageIcon(personaggi[i].getUrl().replace("Giocatori", "Sbarrati")));
    }

    private void setImmagineBotNormale(int i) {
        bottoniBot[i].setIcon(new ImageIcon(personaggiBot[i].getUrl()));
        bottoniBot[i].setToolTipText(personaggiBot[i].getNome());
    }

    private void setImmagineBotSbarrata(int i) {
        bottoniBot[i].setIcon(new ImageIcon(personaggiBot[i].getUrl().replace("Giocatori", "Sbarrati")));
        bottoniBot[i].setToolTipText(personaggiBot[i].getNome() + " (eliminato)");
    }

    private JButton creaBottoneRisposta(String testo, Color colore) {
        JButton btn = new JButton(testo);
        btn.setFont(new Font("Serif", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(colore);
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colore.darker(), 1),
                new EmptyBorder(8, 36, 8, 36)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton creaBottoneInline(String testo) {
        JButton btn = new JButton(testo);
        btn.setFont(new Font("Serif", Font.BOLD, 13));
        btn.setForeground(COL_TEXT);
        btn.setBackground(new Color(40, 35, 90));
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 70, 140), 1),
                new EmptyBorder(7, 16, 7, 16)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void sleep() {
        try { Thread.sleep(80); } catch (Exception ignored) {}
    }
}
