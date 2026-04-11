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






        public static void main(String[] args) { avvia(); }
}