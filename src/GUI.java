import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;

public class GUI extends JFrame {

    // palette
    static final Color BG_DARK       = new Color(14, 20, 34);
    static final Color BG_CARD       = new Color(28, 36, 54);
    static final Color BG_PANEL      = new Color(20, 28, 44);
    static final Color BG_PANEL_BOT  = new Color(26, 18, 44);
    static final Color ACCENT_GOLD   = new Color(255, 198, 60);
    static final Color ACCENT_BLUE   = new Color(70, 150, 255);
    static final Color ACCENT_PURPLE = new Color(160, 80, 255);
    static final Color ELIM_U        = new Color(14, 20, 34, 205);
    static final Color ELIM_B        = new Color(44, 10, 34, 205);
    static final Color TEXT_WHITE    = new Color(238, 240, 250);
    static final Color TEXT_MUTED    = new Color(128, 140, 168);
    static final Color BTN_SI        = new Color(45, 185, 95);
    static final Color BTN_NO        = new Color(215, 60, 60);
    static final Color BTN_SI_H      = new Color(65, 210, 115);
    static final Color BTN_NO_H      = new Color(235, 75, 75);

    //layout
    static final int COLS = 6;
    static final int CW = 110;
    static final int CH = 145;
    static final int IW = 80;
    static final int IH = 92;

    // dati
    private Albero albero;
    private Personaggio[] tutti;

    private Personaggio segUmano;
    private Personaggio segBot;
    private GiocatoreUmanoS giocatoreUmano;
    private GiocatoreBotS giocatoreBot;

    private boolean[] elimU;
    private boolean[] elimB;

    private boolean turnoUmano;
    private List<DomandaGiocatore> domande;
    private Set<String> giàFatte = new HashSet<>();

    // componenti UI
    private PersonaggioCard[] cardsU, cardsB;
    private JPanel tabU, tabB, cardCont;
    private JPanel sidePanel, panUmano, panBot;
    private JLabel lblTurno, lblContatore, lblDomandaBot, lblRispostaBot;
    private JButton btnSi, btnNo, btnFai, btnIndovina;
    private JComboBox<DomandaGiocatore> combo;

    public GUI() {
        super("Indovina Chi!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        getContentPane().setBackground(BG_DARK);
        caricaDati();
        schermataSetup();
    }

    //  CARICAMENTO DATI  (GestoreFile)
    private void caricaDati() {
        try {
            albero = GestoreFile.Leggi_binarioAlbero();
            tutti  = GestoreFile.Leggi_binarioPersonaggi();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Impossibile caricare i dati!\nEsegui prima MainCaricaAlbero.\n\n" + e.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    //  SCHERMATA SETUP — scelta personaggio segreto
    private void schermataSetup() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JPanel bg = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, new Color(10,14,26),
                        getWidth(), getHeight(), new Color(28,12,50)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(28, 28, 12, 28));
        JLabel t = new JLabel("INDOVINA CHI?");
        t.setFont(new Font("SansSerif", Font.BOLD, 40));
        t.setForeground(ACCENT_GOLD);
        JLabel s = new JLabel("Clicca il tuo personaggio segreto — il bot dovrà indovinarlo!");
        s.setFont(new Font("SansSerif", Font.PLAIN, 14));
        s.setForeground(TEXT_MUTED);
        JPanel ht = new JPanel(new BorderLayout(0, 4));
        ht.setOpaque(false);
        ht.add(t, BorderLayout.NORTH);
        ht.add(s, BorderLayout.SOUTH);
        header.add(ht, BorderLayout.WEST);
        bg.add(header, BorderLayout.NORTH);

        // griglia personaggi selezionabili
        int rows = (int) Math.ceil((double) tutti.length / COLS);
        JPanel grid = new JPanel(new GridLayout(rows, COLS, 8, 8));
        grid.setOpaque(false);
        grid.setBorder(new EmptyBorder(8, 24, 24, 24));
        for (Personaggio p : tutti) {
            MiniCard mc = new MiniCard(p);
            mc.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) { avviaPartita(p); }
            });
            grid.add(mc);
        }
        bg.add(grid, BorderLayout.CENTER);
        add(bg, BorderLayout.CENTER);

        pack();
        setMinimumSize(new Dimension(980, 720));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //  AVVIO PARTITA — inizializza GiocatoreUmanoS e GiocatoreBotS
    private void avviaPartita(Personaggio sceltaUmano) {
        segUmano = sceltaUmano;
        Random rand = new Random();
        do { segBot = tutti[rand.nextInt(tutti.length)]; }
        while (segBot.equals(segUmano));

        Personaggio[] tabelloneUmano = Arrays.copyOf(tutti, tutti.length);
        giocatoreUmano = new GiocatoreUmanoS("Giocatore", albero, tabelloneUmano);

        Personaggio[] tabelloneBot = Arrays.copyOf(tutti, tutti.length);
        giocatoreBot = new GiocatoreBotS("Bot", albero, tabelloneBot);

        elimU = new boolean[tutti.length];
        elimB= new boolean[tutti.length];
        turnoUmano = true;
        giàFatte= new HashSet<>();
        domande = generaDomande();

        getContentPane().removeAll();
        buildMainUI();
        aggiornaTurno();

        pack();
        setMinimumSize(new Dimension(1120, 730));
        setLocationRelativeTo(null);
        revalidate();
        repaint();
    }

    private void buildMainUI() {
        setLayout(new BorderLayout());
        add(buildTitleBar(), BorderLayout.NORTH);

        cardsU= new PersonaggioCard[tutti.length];
        cardsB= new PersonaggioCard[tutti.length];
        tabU = buildTabellone(cardsU, elimU, false);
        tabB = buildTabellone(cardsB, elimB, true);

        cardCont = new JPanel(new CardLayout());
        cardCont.setBackground(BG_DARK);
        cardCont.add(tabU, "U");
        cardCont.add(tabB, "B");

        JScrollPane sc = new JScrollPane(cardCont);
        sc.setBorder(null);
        sc.getViewport().setBackground(BG_DARK);
        add(sc, BorderLayout.CENTER);

        sidePanel = buildSide();
        add(sidePanel, BorderLayout.EAST);
    }

    private JPanel buildTitleBar() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(10, 14, 24));
        p.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel t = new JLabel("INDOVINA CHI?");
        t.setFont(new Font("SansSerif", Font.BOLD, 28));
        t.setForeground(ACCENT_GOLD);

        lblTurno = new JLabel();
        lblTurno.setFont(new Font("SansSerif", Font.BOLD, 13));

        JPanel left = new JPanel(new BorderLayout(0, 3));
        left.setOpaque(false);
        left.add(t, BorderLayout.NORTH);
        left.add(lblTurno, BorderLayout.SOUTH);

        JButton bNew = roundBtn("↺  Nuova partita", new Color(50, 62, 96), TEXT_WHITE);
        bNew.addActionListener(e -> schermataSetup());

        p.add(left, BorderLayout.WEST);
        p.add(bNew, BorderLayout.EAST);
        return p;
    }

    private JPanel buildTabellone(PersonaggioCard[] cards, boolean[] elim, boolean botBoard) {
        int rows = (int) Math.ceil((double) tutti.length / COLS);
        JPanel g = new JPanel(new GridLayout(rows, COLS, 8, 8));
        g.setBackground(BG_DARK);
        g.setBorder(new EmptyBorder(14, 14, 14, 8));
        for (int i = 0; i < tutti.length; i++) {
            cards[i] = new PersonaggioCard(tutti[i], i, elim, botBoard);
            g.add(cards[i]);
        }
        return g;
    }

   // PANNELLO LATERALE
    private JPanel buildSide() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setPreferredSize(new Dimension(258, 0));
        p.setBorder(new EmptyBorder(18, 14, 18, 14));

        lblContatore = new JLabel();
        lblContatore.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblContatore.setForeground(TEXT_MUTED);
        lblContatore.setAlignmentX(CENTER_ALIGNMENT);

        // pannello turno UMANO
        panUmano = new JPanel();
        panUmano.setLayout(new BoxLayout(panUmano, BoxLayout.Y_AXIS));
        panUmano.setOpaque(false);

        lblRispostaBot = new JLabel(" ");
        lblRispostaBot.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblRispostaBot.setAlignmentX(CENTER_ALIGNMENT);

        combo = new JComboBox<>();
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        combo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        combo.setBackground(BG_CARD);
        combo.setForeground(TEXT_WHITE);

        btnFai = roundBtn("Fai la domanda", ACCENT_BLUE, Color.WHITE);
        btnFai.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnFai.setAlignmentX(CENTER_ALIGNMENT);
        btnFai.addActionListener(e -> gestisciDomandaUmano());

        btnIndovina = roundBtn("Indovina personaggio!", new Color(160, 90, 20), Color.WHITE);
        btnIndovina.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnIndovina.setAlignmentX(CENTER_ALIGNMENT);
        btnIndovina.addActionListener(e -> tentativoUmano());

        panUmano.add(sideTitle("Il tuo turno", ACCENT_BLUE));
        panUmano.add(Box.createVerticalStrut(3));
        panUmano.add(sideSub("Fai una domanda al bot sul suo personaggio segreto"));
        panUmano.add(Box.createVerticalStrut(14));
        panUmano.add(tiny("Scegli domanda:"));
        panUmano.add(Box.createVerticalStrut(4));
        panUmano.add(combo);
        panUmano.add(Box.createVerticalStrut(8));
        panUmano.add(btnFai);
        panUmano.add(Box.createVerticalStrut(10));
        panUmano.add(tiny("Risposta del bot:"));
        panUmano.add(Box.createVerticalStrut(3));
        panUmano.add(lblRispostaBot);
        panUmano.add(Box.createVerticalStrut(18));
        panUmano.add(sep());
        panUmano.add(Box.createVerticalStrut(10));
        panUmano.add(btnIndovina);

        // pannello turno BOT
        panBot = new JPanel();
        panBot.setLayout(new BoxLayout(panBot, BoxLayout.Y_AXIS));
        panBot.setOpaque(false);

        lblDomandaBot = new JLabel();
        lblDomandaBot.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblDomandaBot.setForeground(TEXT_WHITE);
        lblDomandaBot.setAlignmentX(CENTER_ALIGNMENT);
        lblDomandaBot.setBorder(new EmptyBorder(6, 0, 6, 0));

        btnSi = roundBtn("✔  SI", BTN_SI, Color.WHITE);
        btnNo = roundBtn("✘  NO", BTN_NO, Color.WHITE);
        btnSi.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnNo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnSi.setAlignmentX(CENTER_ALIGNMENT);
        btnNo.setAlignmentX(CENTER_ALIGNMENT);
        btnSi.addActionListener(e -> rispondiBotDomanda("si"));
        btnNo.addActionListener(e -> rispondiBotDomanda("no"));
        hoverFx(btnSi, BTN_SI, BTN_SI_H);
        hoverFx(btnNo, BTN_NO, BTN_NO_H);

        panBot.add(sideTitle("Turno del Bot", ACCENT_PURPLE));
        panBot.add(Box.createVerticalStrut(3));
        panBot.add(sideSub("Rispondi onestamente pensando al tuo personaggio segreto"));
        panBot.add(Box.createVerticalStrut(16));
        panBot.add(tiny("Il bot chiede:"));
        panBot.add(Box.createVerticalStrut(4));
        panBot.add(lblDomandaBot);
        panBot.add(Box.createVerticalStrut(14));
        panBot.add(btnSi);
        panBot.add(Box.createVerticalStrut(8));
        panBot.add(btnNo);

        // ── assemblaggio ─────────────────────────────────────────────────────
        p.add(lblContatore);
        p.add(Box.createVerticalStrut(12));
        p.add(sep());
        p.add(Box.createVerticalStrut(14));
        p.add(panUmano);
        p.add(panBot);
        p.add(Box.createVerticalGlue());
        return p;
    }

    //  AGGIORNA UI AL CAMBIO TURNO
    private void aggiornaTurno() {
        if (sidePanel == null) return;
        sidePanel.setBackground(turnoUmano ? BG_PANEL : BG_PANEL_BOT);
        panUmano.setVisible(turnoUmano);
        panBot.setVisible(!turnoUmano);
        ((CardLayout) cardCont.getLayout()).show(cardCont, turnoUmano ? "U" : "B");

        if (turnoUmano) {
            lblTurno.setText("Tocca a TE — elimina i personaggi del bot");
            lblTurno.setForeground(ACCENT_BLUE);
            aggiornaCombo();
            lblRispostaBot.setText(" ");
        } else {
            lblTurno.setText("Turno BOT — rispondi alla sua domanda");
            lblTurno.setForeground(ACCENT_PURPLE);

            // GiocatoreBotS.ChiediDomanda(nodo) restituisce la domanda del nodo corrente.
            // Usiamo getRoot() come nodo da cui leggere — il bot gestisce internamente
            // il proprio nodo corrente tramite avanza().
            // Poiché nodoCorrente è privato in GiocatoreBotS, recuperiamo la domanda
            // direttamente da ChiediDomanda passandogli il nodo che il bot sta osservando:
            // lo otteniamo tramite albero navigato con le risposte precedenti.
            String domanda = getDomandaBot();
            lblDomandaBot.setText("<html><div style='text-align:center;width:205px;'>" + domanda + "</div></html>");
            boolean hasDomanda = domanda != null && !domanda.equals("—");
            btnSi.setEnabled(hasDomanda);
            btnNo.setEnabled(hasDomanda);
        }
        aggiornaContatore();
        revalidate();
        repaint();
    }

    //  TURNO UMANO — usa GiocatoreUmanoS
    private void gestisciDomandaUmano() {
        if (combo.getItemCount() == 0) return;
        DomandaGiocatore dq = (DomandaGiocatore) combo.getSelectedItem();
        if (dq == null) return;

        boolean risposta = dq.check(segBot);
        lblRispostaBot.setText(risposta ? "✔  SI" : "✘  NO");
        lblRispostaBot.setForeground(risposta ? BTN_SI : BTN_NO);
        giàFatte.add(dq.testo);

        // elimina dal tabellone umano i personaggi che non corrispondono alla risposta
        for (int i = 0; i < tutti.length; i++) {
            if (!elimU[i] && dq.check(tutti[i]) != risposta) {
                elimU[i] = true;
                cardsU[i].repaint();
            }
        }

        try {
            // verifichiamo che ci siano ancora personaggi rimasti sul tabellone umano
            List<Personaggio> rimasti = rimasti(elimU);
            if (rimasti.size() == 1) {
                btnFai.setEnabled(false);
            }
        } catch (Exception ignored) {}

        aggiornaCombo();
        aggiornaContatore();

        List<Personaggio> rimastiU = rimasti(elimU);
        if (rimastiU.size() == 1) {
            lblRispostaBot.setText(risposta ? "✔  SI" : "✘  NO");
        }

        Timer tmr = new Timer(900, e -> { turnoUmano = false; aggiornaTurno(); });
        tmr.setRepeats(false);
        tmr.start();
    }

    private void tentativoUmano() {
        List<Personaggio> rim = rimasti(elimU);
        if (rim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hai eliminato tutti! Ricomincia.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String[] nomi = new String[rim.size()];
        for (int i = 0; i < rim.size(); i++) nomi[i] = rim.get(i).getNome();

        String sc = (String) JOptionPane.showInputDialog(this,
                "Chi è il personaggio segreto del bot?", "Indovina!",
                JOptionPane.QUESTION_MESSAGE, null, nomi, nomi[0]);
        if (sc == null) return;

        if (sc.equals(segBot.getNome())) {
            evidenzia(cardsU, segBot, elimU);
            fine(true, "HAI INDOVINATO! \nIl personaggio del bot era: " + segBot.getNome());
        } else {
            JOptionPane.showMessageDialog(this,
                    "Sbagliato! Continua a fare domande...", "Risposta errata", JOptionPane.WARNING_MESSAGE);
            turnoUmano = false;
            aggiornaTurno();
        }
    }

    //  TURNO BOT — usa GiocatoreBotS

    /**
     * Recupera la domanda corrente del bot tramite GiocatoreBotS.ChiediDomanda().
     * Poiché nodoCorrente è privato, usiamo il nodo dell'albero navigato fino ad ora:
     * manteniamo un riferimento al nodo corrente del bot in nodoBotNav.
     */
    private Nodo nodoBotNav = null;   // traccia il nodo corrente del bot (parallelo a GiocatoreBotS interno)

    private String getDomandaBot() {
        // Al primo turno bot partiamo dalla root
        if (nodoBotNav == null) nodoBotNav = albero.getRoot();
        // GiocatoreBotS.ChiediDomanda(nodo) — legge la domanda dal nodo passato
        String d = giocatoreBot.ChiediDomanda(nodoBotNav);
        return (d != null) ? d : "—";
    }

    private void rispondiBotDomanda(String risposta) {
        if (nodoBotNav == null) nodoBotNav = albero.getRoot();
        giocatoreBot.avanza(risposta);
        nodoBotNav = risposta.equalsIgnoreCase("si")
                ? nodoBotNav.getNododx()
                : nodoBotNav.getNodosx();

        if (nodoBotNav == null) {
            fine(true, "Il bot si è perso... Hai vinto tu!");
            return;
        }

        // nodo foglia: GiocatoreBotS.indovinaPersonaggio() restituisce il personaggio trovato
        if (nodoBotNav.getPersonaggio() != null) {
            Personaggio trovato = giocatoreBot.indovinaPersonaggio();
            if (trovato == null) trovato = nodoBotNav.getPersonaggio(); // fallback
            evidenzia(cardsB, trovato, elimB);
            if (trovato.equals(segUmano)) {
                fine(false, "Il bot ha indovinato! \nIl tuo personaggio era: " + segUmano.getNome());
            } else {
                fine(true, "Il bot ha sbagliato — hai vinto tu! \n(Il tuo era: " + segUmano.getNome() + ")");
            }
            return;
        }

        // elimina dal tabellone bot i personaggi del branch escluso
        Nodo padre = albero.getNodoPadre(albero.getRoot(), null, nodoBotNav);
        if (padre != null) {
            Nodo escluso = (nodoBotNav == padre.getNododx()) ? padre.getNodosx() : padre.getNododx();
            if (escluso != null) {
                for (Personaggio pg : albero.getPersoneRimaste(escluso)) {
                    for (int i = 0; i < tutti.length; i++) {
                        if (!elimB[i] && tutti[i].equals(pg)) {
                            elimB[i] = true;
                            cardsB[i].repaint();
                        }
                    }
                }
            }
        }

        // GiocatoreBotS.getPersonaggiAttivi() — se rimane 1 solo attivo il bot ha trovato
        if (giocatoreBot.getPersonaggiAttivi().size() == 1) {
            Personaggio trovato = giocatoreBot.indovinaPersonaggio();
            if (trovato != null) {
                evidenzia(cardsB, trovato, elimB);
                if (trovato.equals(segUmano)) {
                    fine(false, "Il bot ha indovinato! \nIl tuo personaggio era: " + segUmano.getNome());
                } else {
                    fine(true, "Il bot ha sbagliato — hai vinto tu! \n(Il tuo era: " + segUmano.getNome() + ")");
                }
                return;
            }
        }

        turnoUmano = true;
        aggiornaTurno();
    }

    //  FINE PARTITA
    private void evidenzia(PersonaggioCard[] cards, Personaggio trovato, boolean[] elim) {
        for (int i = 0; i < tutti.length; i++) {
            if (tutti[i].equals(trovato)) cards[i].setEvidenziato(true);
            else if (!elim[i]) { elim[i] = true; cards[i].repaint(); }
        }
    }

    private void fine(boolean umanoVince, String msg) {
        btnSi.setEnabled(false);
        btnNo.setEnabled(false);
        btnFai.setEnabled(false);
        btnIndovina.setEnabled(false);
        new Timer(350, e -> {
            int sc = JOptionPane.showConfirmDialog(this,
                    msg + "\n\nVuoi giocare ancora?", "Fine partita!",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (sc == JOptionPane.YES_OPTION) schermataSetup();
        }) {{ setRepeats(false); }}.start();
    }

    interface Checker { boolean check(Personaggio p); }

    static class DomandaGiocatore {
        final String  testo;
        final Checker checker;
        DomandaGiocatore(String t, Checker c) { testo = t; checker = c; }
        boolean check(Personaggio p) { return checker.check(p); }
        @Override public String toString() { return testo; }
    }

    private List<DomandaGiocatore> generaDomande() {
        List<DomandaGiocatore> l = new ArrayList<>();
        // sesso
        l.add(new DomandaGiocatore("È una donna?",           p -> !p.isSesso()));
        l.add(new DomandaGiocatore("È un uomo?",             p ->  p.isSesso()));
        // ColoriCapelli
        l.add(new DomandaGiocatore("Ha i capelli biondi?",   p -> p.getColoreCapelli() == ColoriCapelli.BIONDI));
        l.add(new DomandaGiocatore("Ha i capelli neri?",     p -> p.getColoreCapelli() == ColoriCapelli.NERI));
        l.add(new DomandaGiocatore("Ha i capelli castani?",  p -> p.getColoreCapelli() == ColoriCapelli.CASTANI));
        l.add(new DomandaGiocatore("Ha i capelli rossi?",    p -> p.getColoreCapelli() == ColoriCapelli.ROSSI));
        l.add(new DomandaGiocatore("Ha i capelli bianchi?",  p -> p.getColoreCapelli() == ColoriCapelli.BIANCHI));
        l.add(new DomandaGiocatore("È calvo?",               p -> p.getColoreCapelli() == ColoriCapelli.NESSUNO));
        // ColoriOcchi
        l.add(new DomandaGiocatore("Ha gli occhi azzurri?",  p -> p.getColoreOcchi() == ColoriOcchi.AZZURRI));
        l.add(new DomandaGiocatore("Ha gli occhi marroni?",  p -> p.getColoreOcchi() == ColoriOcchi.MARRONI));
        // ColorePelle
        l.add(new DomandaGiocatore("Ha la pelle chiara?",    p -> p.getColorePelle() == ColorePelle.CHIARA));
        l.add(new DomandaGiocatore("Ha la pelle scura?",     p -> p.getColorePelle() == ColorePelle.SCURA));
        // accessori
        l.add(new DomandaGiocatore("Ha gli occhiali?",       p -> p.isOcchiali()));
        l.add(new DomandaGiocatore("Ha i baffi?",            p -> p.isBaffi()));
        l.add(new DomandaGiocatore("Ha la barba?",           p -> p.isBarba()));
        l.add(new DomandaGiocatore("Ha il pizzetto?",        p -> p.isPizzettto()));
        l.add(new DomandaGiocatore("Ha gli orecchini?",      p -> p.isOrecchini()));
        l.add(new DomandaGiocatore("Ha il cappello?",        p -> p.isCappello()));
        return l;
    }

    private void aggiornaCombo() {
        if (combo == null) return;
        combo.removeAllItems();
        for (DomandaGiocatore d : domande)
            if (!giàFatte.contains(d.testo)) combo.addItem(d);
    }

    private void aggiornaContatore() {
        if (lblContatore == null) return;
        long r = rimasti(elimU).size();
        lblContatore.setText("Tabellone: " + r + " personagg" + (r == 1 ? "io rimasto" : "i rimasti"));
    }

    private List<Personaggio> rimasti(boolean[] elim) {
        List<Personaggio> r = new ArrayList<>();
        for (int i = 0; i < tutti.length; i++) if (!elim[i]) r.add(tutti[i]);
        return r;
    }

    class MiniCard extends JPanel {
        final Personaggio p;
        final ImagePanel imgPanel;
        boolean hov;

        MiniCard(Personaggio pg) {
            this.p = pg;
            setPreferredSize(new Dimension(CW, CH));
            setOpaque(false);
            setLayout(new BorderLayout(0, 2));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // TODO: ImagePanel (da inizializzare), try-catch con file,JLabel e MouseListener
            imgPanel=null;

        }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(hov ? new Color(32, 52, 82) : BG_CARD);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            g2.setColor(hov ? ACCENT_BLUE : new Color(45, 58, 85));
            g2.setStroke(new BasicStroke(hov ? 2f : 1f));
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    //  CARD TABELLONE
    class PersonaggioCard extends JPanel {
        final Personaggio p;
        final int idx;
        final boolean[] elimRef;
        final boolean botBoard;
        final ImagePanel imgPanel;
        boolean evid;

        PersonaggioCard(Personaggio pg, int i, boolean[] el, boolean bb) {
            this.p       = pg;
            this.idx     = i;
            this.elimRef = el;
            this.botBoard= bb;
            setPreferredSize(new Dimension(CW, CH));
            setOpaque(false);
            setLayout(new BorderLayout(0, 2));
            setToolTipText(pg.getNome());

            //TODO: ImagePanel (da inizializzare), try-catch con file e ignoro dell'eccezzione, JLabel(nome, imgWrap)

            imgPanel = null;
        }

        void setEvidenziato(boolean v) { evid = v; repaint(); }

        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            boolean el  = elimRef[idx];
            int w = getWidth(), h = getHeight(), arc = 13;

            // sfondo
            g2.setColor(evid ? new Color(28, 58, 36) : BG_CARD);
            g2.fillRoundRect(0, 0, w, h, arc, arc);

            // TODO: manca bordo da fare

            g2.dispose();
            super.paintComponent(g);

            if (el && !evid) {
                Graphics2D ov = (Graphics2D) g.create();
                ov.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                ov.setColor(botBoard ? ELIM_B : ELIM_U);
                ov.fillRoundRect(0, 0, w, h, arc, arc);
                ov.setColor(botBoard ? new Color(195, 50, 95) : new Color(195, 55, 55));
                ov.setStroke(new BasicStroke(3.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int m = 10;
                ov.drawLine(m, m, w-m, h-m-20);
                ov.drawLine(w-m, m, m, h-m-20);
                ov.dispose();
            }

            if (evid) {
                Graphics2D gv = (Graphics2D) g.create();
                gv.setColor(ACCENT_GOLD);
                gv.setFont(new Font("SansSerif", Font.PLAIN, 14));
                gv.drawString("★", (w-10)/2, h - 4);
                gv.dispose();
            }
        }
    }

    JButton roundBtn(String txt, Color bg, Color fg) {
        JButton b = new JButton(txt) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 11, 11);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setForeground(fg);
        b.setBackground(bg);
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    void hoverFx(JButton b, Color n, Color h) {
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(h); b.repaint(); }
            public void mouseExited (MouseEvent e) { b.setBackground(n); b.repaint(); }
        });
    }

    JLabel sideTitle(String t, Color c) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("SansSerif", Font.BOLD, 16));
        l.setForeground(c);
        l.setAlignmentX(CENTER_ALIGNMENT);
        return l;
    }

    JLabel sideSub(String t) {
        JLabel l = new JLabel("<html><div style='text-align:center;width:210px;'>" + t + "</div></html>");
        l.setFont(new Font("SansSerif", Font.PLAIN, 11));
        l.setForeground(TEXT_MUTED);
        l.setAlignmentX(CENTER_ALIGNMENT);
        return l;
    }

    JLabel tiny(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("SansSerif", Font.BOLD, 11));
        l.setForeground(TEXT_MUTED);
        l.setAlignmentX(CENTER_ALIGNMENT);
        return l;
    }

    Component sep() {
        JSeparator s = new JSeparator();
        s.setForeground(new Color(40, 50, 72));
        s.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return s;
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  ENTRY POINT
    // ════════════════════════════════════════════════════════════════════════════
    public static void avvia() {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored){}
            new GUI().setVisible(true);
        });
    }

    public static void main(String[] args) { avvia(); }
}



