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

/**
 * GUI completa per "Indovina Chi!" — modalità a turni alternati.
 *
 * FLUSSO DI GIOCO:
 *   1. Schermata di setup: il giocatore umano clicca il suo personaggio segreto
 *      (quello che il bot deve indovinare). Il bot sceglie il suo a caso.
 *   2. Turno UMANO  → sceglie una domanda dal menu a tendina; il bot risponde SI/NO
 *      in automatico in base al suo personaggio. L'umano elimina le card dal tabellone.
 *   3. Turno BOT    → il bot fa una domanda navigando l'albero decisionale;
 *      l'umano clicca SI o NO. Il bot elimina automaticamente le card.
 *   4. Chi indovina per primo vince.
 *      - L'umano può tentare di indovinare in qualsiasi momento con "Indovina!".
 *      - Il bot indovina quando raggiunge una foglia dell'albero.
 *
 * COME USARE:
 *   1. Compila tutto il progetto insieme.
 *   2. Esegui MainCaricaAlbero.main() una sola volta (genera albero.bin, personaggi.bin).
 *   3. Lancia: java GuindovinaChiGUI
 */
public class GUI extends JFrame {

    // ── palette ──────────────────────────────────────────────────────────────────
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

    // ── layout ────────────────────────────────────────────────────────────────────
    static final int COLS  = 6;
    static final int CW    = 108;
    static final int CH    = 138;
    static final int IW    = 78;
    static final int IH    = 88;

    // ── dati ─────────────────────────────────────────────────────────────────────
    private Albero       albero;
    private Personaggio[] tutti;

    private Personaggio  segUmano;   // personaggio segreto dell'umano (il bot indovina)
    private Personaggio  segBot;     // personaggio segreto del bot (l'umano indovina)

    private boolean[] elimU;         // eliminati dal tabellone umano
    private boolean[] elimB;         // eliminati dal tabellone bot (nascosto)

    private Nodo nodoBotCorrente;
    private boolean turnoUmano;

    private List<DomandaGiocatore> domande;
    private Set<String> giàFatte = new HashSet<>();

    // ── componenti ───────────────────────────────────────────────────────────────
    private PersonaggioCard[] cardsU, cardsB;
    private JPanel tabU, tabB, cardCont;
    private JPanel sidePanel, panUmano, panBot;
    private JLabel lblTurno, lblContatore, lblDomandaBot, lblRispostaBot;
    private JButton btnSi, btnNo, btnFai, btnIndovina;
    private JComboBox<DomandaGiocatore> combo;

    // ════════════════════════════════════════════════════════════════════════════
    public GUI() {
        super("🎭  Indovina Chi!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        getContentPane().setBackground(BG_DARK);
        caricaDati();
        schermataSetup();
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  CARICAMENTO
    // ════════════════════════════════════════════════════════════════════════════
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

    // ════════════════════════════════════════════════════════════════════════════
    //  SCHERMATA SETUP
    // ════════════════════════════════════════════════════════════════════════════
    private void schermataSetup() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JPanel bg = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0,0,new Color(10,14,26),getWidth(),getHeight(),new Color(28,12,50)));
                g2.fillRect(0,0,getWidth(),getHeight());
            }
        };

        // header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(28,28,12,28));
        JLabel t = new JLabel("INDOVINA CHI?");
        t.setFont(new Font("SansSerif",Font.BOLD,40));
        t.setForeground(ACCENT_GOLD);
        JLabel s = new JLabel("Clicca il tuo personaggio segreto — il bot dovrà indovinarlo!");
        s.setFont(new Font("SansSerif",Font.PLAIN,14));
        s.setForeground(TEXT_MUTED);
        JPanel ht = new JPanel(new BorderLayout(0,4)); ht.setOpaque(false);
        ht.add(t,BorderLayout.NORTH); ht.add(s,BorderLayout.SOUTH);
        header.add(ht, BorderLayout.WEST);
        bg.add(header, BorderLayout.NORTH);

        // griglia
        int rows = (int)Math.ceil((double)tutti.length/COLS);
        JPanel grid = new JPanel(new GridLayout(rows,COLS,8,8));
        grid.setOpaque(false);
        grid.setBorder(new EmptyBorder(8,24,24,24));
        for (Personaggio p : tutti) {
            MiniCard mc = new MiniCard(p);
            mc.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){ scegli(p); }
            });
            grid.add(mc);
        }
        bg.add(grid, BorderLayout.CENTER);
        add(bg, BorderLayout.CENTER);

        pack();
        setMinimumSize(new Dimension(980,720));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void scegli(Personaggio p) {
        segUmano = p;
        // bot sceglie a caso (diverso dall'umano)
        Random r = new Random();
        do { segBot = tutti[r.nextInt(tutti.length)]; } while(segBot.equals(segUmano));
        avviaPartita();
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  AVVIO PARTITA
    // ════════════════════════════════════════════════════════════════════════════
    private void avviaPartita() {
        elimU         = new boolean[tutti.length];
        elimB         = new boolean[tutti.length];
        nodoBotCorrente = albero.getRoot();
        turnoUmano    = true;
        giàFatte      = new HashSet<>();
        domande       = generaDomande();

        getContentPane().removeAll();
        buildMainUI();
        aggiornaTurno();

        pack();
        setMinimumSize(new Dimension(1120,730));
        setLocationRelativeTo(null);
        revalidate(); repaint();
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  UI PRINCIPALE
    // ════════════════════════════════════════════════════════════════════════════
    private void buildMainUI() {
        setLayout(new BorderLayout());
        add(buildTitleBar(), BorderLayout.NORTH);

        cardsU = new PersonaggioCard[tutti.length];
        cardsB = new PersonaggioCard[tutti.length];
        tabU   = buildTab(cardsU, elimU, false);
        tabB   = buildTab(cardsB, elimB, true);

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
        p.setBackground(new Color(10,14,24));
        p.setBorder(new EmptyBorder(10,20,10,20));

        JLabel t = new JLabel("INDOVINA CHI?");
        t.setFont(new Font("SansSerif",Font.BOLD,28));
        t.setForeground(ACCENT_GOLD);

        lblTurno = new JLabel();
        lblTurno.setFont(new Font("SansSerif",Font.BOLD,13));

        JPanel left = new JPanel(new BorderLayout(0,3)); left.setOpaque(false);
        left.add(t,BorderLayout.NORTH); left.add(lblTurno,BorderLayout.SOUTH);

        JButton bNew = roundBtn("↺  Nuova partita", new Color(50,62,96), TEXT_WHITE);
        bNew.addActionListener(e -> schermataSetup());

        p.add(left,BorderLayout.WEST); p.add(bNew,BorderLayout.EAST);
        return p;
    }

    private JPanel buildTab(PersonaggioCard[] cards, boolean[] elim, boolean botBoard) {
        int rows=(int)Math.ceil((double)tutti.length/COLS);
        JPanel g=new JPanel(new GridLayout(rows,COLS,8,8));
        g.setBackground(BG_DARK);
        g.setBorder(new EmptyBorder(14,14,14,8));
        for(int i=0;i<tutti.length;i++){
            cards[i]=new PersonaggioCard(tutti[i],i,elim,botBoard);
            g.add(cards[i]);
        }
        return g;
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  PANNELLO LATERALE
    // ════════════════════════════════════════════════════════════════════════════
    private JPanel buildSide() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        p.setPreferredSize(new Dimension(255,0));
        p.setBorder(new EmptyBorder(18,14,18,14));

        lblContatore = new JLabel();
        lblContatore.setFont(new Font("SansSerif",Font.PLAIN,12));
        lblContatore.setForeground(TEXT_MUTED);
        lblContatore.setAlignmentX(CENTER_ALIGNMENT);

        // ── pannello turno umano ─────────────────────────────────────────────
        panUmano = new JPanel();
        panUmano.setLayout(new BoxLayout(panUmano,BoxLayout.Y_AXIS));
        panUmano.setOpaque(false);

        lblRispostaBot = new JLabel(" ");
        lblRispostaBot.setFont(new Font("SansSerif",Font.BOLD,26));
        lblRispostaBot.setAlignmentX(CENTER_ALIGNMENT);

        combo = new JComboBox<>();
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE,36));
        combo.setFont(new Font("SansSerif",Font.PLAIN,12));
        combo.setBackground(BG_CARD);
        combo.setForeground(TEXT_WHITE);

        btnFai = roundBtn("❓  Fai la domanda", ACCENT_BLUE, Color.WHITE);
        btnFai.setMaximumSize(new Dimension(Integer.MAX_VALUE,42));
        btnFai.setAlignmentX(CENTER_ALIGNMENT);
        btnFai.addActionListener(e -> gestisciDomandaUmano());

        btnIndovina = roundBtn("🎯  Indovina personaggio!", new Color(160,90,20), Color.WHITE);
        btnIndovina.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));
        btnIndovina.setAlignmentX(CENTER_ALIGNMENT);
        btnIndovina.addActionListener(e -> tentativoUmano());

        panUmano.add(sideTitle("🙋  Il tuo turno", ACCENT_BLUE));
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

        // ── pannello turno bot ───────────────────────────────────────────────
        panBot = new JPanel();
        panBot.setLayout(new BoxLayout(panBot,BoxLayout.Y_AXIS));
        panBot.setOpaque(false);

        lblDomandaBot = new JLabel();
        lblDomandaBot.setFont(new Font("SansSerif",Font.BOLD,14));
        lblDomandaBot.setForeground(TEXT_WHITE);
        lblDomandaBot.setAlignmentX(CENTER_ALIGNMENT);
        lblDomandaBot.setBorder(new EmptyBorder(6,0,6,0));

        btnSi = roundBtn("✔  SI", BTN_SI, Color.WHITE);
        btnNo = roundBtn("✘  NO", BTN_NO, Color.WHITE);
        btnSi.setMaximumSize(new Dimension(Integer.MAX_VALUE,44));
        btnNo.setMaximumSize(new Dimension(Integer.MAX_VALUE,44));
        btnSi.setAlignmentX(CENTER_ALIGNMENT);
        btnNo.setAlignmentX(CENTER_ALIGNMENT);
        btnSi.addActionListener(e -> rispondiBotDomanda("si"));
        btnNo.addActionListener(e -> rispondiBotDomanda("no"));
        hoverFx(btnSi,BTN_SI,BTN_SI_H);
        hoverFx(btnNo,BTN_NO,BTN_NO_H);

        panBot.add(sideTitle("🤖  Turno del Bot", ACCENT_PURPLE));
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

    // ════════════════════════════════════════════════════════════════════════════
    //  AGGIORNA UI AL CAMBIO TURNO
    // ════════════════════════════════════════════════════════════════════════════
    private void aggiornaTurno() {
        if (sidePanel==null) return;
        sidePanel.setBackground(turnoUmano ? BG_PANEL : BG_PANEL_BOT);
        panUmano.setVisible(turnoUmano);
        panBot.setVisible(!turnoUmano);

        // mostra tabellone giusto
        ((CardLayout)cardCont.getLayout()).show(cardCont, turnoUmano?"U":"B");

        if (turnoUmano) {
            lblTurno.setText("🙋 Tocca a TE — il tuo tabellone (personaggi del bot)");
            lblTurno.setForeground(ACCENT_BLUE);
            aggiornaCombo();
            lblRispostaBot.setText(" ");
        } else {
            lblTurno.setText("🤖 Turno BOT — tabellone bot (i tuoi possibili personaggi)");
            lblTurno.setForeground(ACCENT_PURPLE);
            String d = getDomandaBot();
            lblDomandaBot.setText("<html><div style='text-align:center;width:200px;'>" + d + "</div></html>");
            boolean attivo = nodoBotCorrente!=null && nodoBotCorrente.getDomanda()!=null;
            btnSi.setEnabled(attivo);
            btnNo.setEnabled(attivo);
        }
        aggiornaContatore();
        revalidate(); repaint();
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  TURNO UMANO
    // ════════════════════════════════════════════════════════════════════════════
    private void gestisciDomandaUmano() {
        if (combo.getItemCount()==0) return;
        DomandaGiocatore dq = (DomandaGiocatore) combo.getSelectedItem();
        if (dq==null) return;

        boolean risposta = dq.check(segBot);
        lblRispostaBot.setText(risposta ? "✔  SI" : "✘  NO");
        lblRispostaBot.setForeground(risposta ? BTN_SI : BTN_NO);
        giàFatte.add(dq.testo);

        // elimina personaggi che NON corrispondono
        for(int i=0;i<tutti.length;i++){
            if(!elimU[i] && dq.check(tutti[i])!=risposta){
                elimU[i]=true;
                cardsU[i].repaint();
            }
        }
        aggiornaCombo();
        aggiornaContatore();

        // passa al bot
        Timer t=new Timer(900, e->{ turnoUmano=false; aggiornaTurno(); });
        t.setRepeats(false); t.start();
    }

    private void tentativoUmano() {
        List<Personaggio> rim = rimasti(elimU);
        if(rim.isEmpty()){ JOptionPane.showMessageDialog(this,"Hai eliminato tutti! Ricomincia.","Attenzione",JOptionPane.WARNING_MESSAGE); return; }
        String[] nomi=new String[rim.size()];
        for(int i=0;i<rim.size();i++) nomi[i]=rim.get(i).getNome();
        String sc=(String)JOptionPane.showInputDialog(this,
                "Chi è il personaggio del bot?","Indovina!",
                JOptionPane.QUESTION_MESSAGE,null,nomi,nomi[0]);
        if(sc==null) return;
        if(sc.equals(segBot.getNome())){
            evidenzia(cardsU, segBot, elimU);
            fine(true,"HAI INDOVINATO! 🎉\nIl personaggio del bot era: "+segBot.getNome());
        } else {
            JOptionPane.showMessageDialog(this,"Sbagliato! Continua a fare domande...","Risposta errata",JOptionPane.WARNING_MESSAGE);
            turnoUmano=false; aggiornaTurno();
        }
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  TURNO BOT
    // ════════════════════════════════════════════════════════════════════════════
    private void rispondiBotDomanda(String risp) {
        if(nodoBotCorrente==null) return;

        // avanza nell'albero
        nodoBotCorrente = risp.equalsIgnoreCase("si")
                ? nodoBotCorrente.getNododx()
                : nodoBotCorrente.getNodosx();

        if(nodoBotCorrente==null){
            fine(true,"Il bot si è perso... Hai vinto tu! 🎉");
            return;
        }
        if(nodoBotCorrente.getPersonaggio()!=null){
            Personaggio trovato=nodoBotCorrente.getPersonaggio();
            evidenzia(cardsB, trovato, elimB);
            if(trovato.equals(segUmano))
                fine(false,"Il bot ha indovinato! 🤖\nIl tuo personaggio era: "+segUmano.getNome());
            else
                fine(true,"Il bot ha sbagliato — hai vinto tu! 🎉\n(Il tuo personaggio era: "+segUmano.getNome()+")");
            return;
        }

        // elimina dal tabellone bot il branch escluso
        Nodo padre=albero.getNodoPadre(albero.getRoot(),null,nodoBotCorrente);
        if(padre!=null){
            Nodo escluso=(nodoBotCorrente==padre.getNododx())?padre.getNodosx():padre.getNododx();
            if(escluso!=null){
                for(Personaggio p: albero.getPersoneRimaste(escluso)){
                    for(int i=0;i<tutti.length;i++){
                        if(!elimB[i]&&tutti[i].equals(p)){ elimB[i]=true; cardsB[i].repaint(); }
                    }
                }
            }
        }
        turnoUmano=true; aggiornaTurno();
    }

    private String getDomandaBot(){
        if(nodoBotCorrente==null||nodoBotCorrente.getDomanda()==null) return "—";
        return nodoBotCorrente.getDomanda();
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  FINE PARTITA
    // ════════════════════════════════════════════════════════════════════════════
    private void evidenzia(PersonaggioCard[] cards, Personaggio trovato, boolean[] elim){
        for(int i=0;i<tutti.length;i++){
            if(tutti[i].equals(trovato)) cards[i].setEvidenziato(true);
            else if(!elim[i]){ elim[i]=true; cards[i].repaint(); }
        }
    }

    private void fine(boolean umanoVince, String msg){
        btnSi.setEnabled(false); btnNo.setEnabled(false);
        btnFai.setEnabled(false); btnIndovina.setEnabled(false);
        new Timer(350, e->{
            int sc=JOptionPane.showConfirmDialog(this,
                    msg+"\n\nVuoi giocare ancora?","Fine partita!",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if(sc==JOptionPane.YES_OPTION) schermataSetup();
        }){{setRepeats(false);}}.start();
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  DOMANDE PER L'UMANO
    // ════════════════════════════════════════════════════════════════════════════
    interface Checker { boolean check(Personaggio p); }
    static class DomandaGiocatore {
        final String testo;
        final Checker c;
        DomandaGiocatore(String t, Checker c){ testo=t; this.c=c; }
        boolean check(Personaggio p){ return c.check(p); }
        @Override public String toString(){ return testo; }
    }

    private List<DomandaGiocatore> generaDomande(){
        List<DomandaGiocatore> l=new ArrayList<>();
        l.add(new DomandaGiocatore("È una donna?",           p->!p.isSesso()));
        l.add(new DomandaGiocatore("È un uomo?",             p->p.isSesso()));
        l.add(new DomandaGiocatore("Ha i capelli biondi?",   p->p.getColoreCapelli()==ColoriCapelli.BIONDI));
        l.add(new DomandaGiocatore("Ha i capelli neri?",     p->p.getColoreCapelli()==ColoriCapelli.NERI));
        l.add(new DomandaGiocatore("Ha i capelli castani?",  p->p.getColoreCapelli()==ColoriCapelli.CASTANI));
        l.add(new DomandaGiocatore("Ha i capelli rossi?",    p->p.getColoreCapelli()==ColoriCapelli.ROSSI));
        l.add(new DomandaGiocatore("Ha i capelli bianchi?",  p->p.getColoreCapelli()==ColoriCapelli.BIANCHI));
        l.add(new DomandaGiocatore("È calvo?",               p->p.getColoreCapelli()==ColoriCapelli.NESSUNO));
        l.add(new DomandaGiocatore("Ha gli occhi azzurri?",  p->p.getColoreOcchi()==ColoriOcchi.AZZURRI));
        l.add(new DomandaGiocatore("Ha gli occhi marroni?",  p->p.getColoreOcchi()==ColoriOcchi.MARRONI));
        l.add(new DomandaGiocatore("Ha la pelle chiara?",    p->p.getColorePelle()==ColorePelle.CHIARA));
        l.add(new DomandaGiocatore("Ha la pelle scura?",     p->p.getColorePelle()==ColorePelle.SCURA));
        l.add(new DomandaGiocatore("Ha gli occhiali?",       p->p.isOcchiali()));
        l.add(new DomandaGiocatore("Ha i baffi?",            p->p.isBaffi()));
        l.add(new DomandaGiocatore("Ha la barba?",           p->p.isBarba()));
        l.add(new DomandaGiocatore("Ha il pizzetto?",        p->p.isPizzettto()));
        l.add(new DomandaGiocatore("Ha gli orecchini?",      p->p.isOrecchini()));
        l.add(new DomandaGiocatore("Ha il cappello?",        p->p.isCappello()));
        return l;
    }

    private void aggiornaCombo(){
        if(combo==null) return;
        combo.removeAllItems();
        for(DomandaGiocatore d:domande) if(!giàFatte.contains(d.testo)) combo.addItem(d);
    }

    private void aggiornaContatore(){
        if(lblContatore==null) return;
        long r=rimasti(elimU).size();
        lblContatore.setText("Tabellone: "+r+" personagg"+(r==1?"io rimasto":"i rimasti"));
    }

    private List<Personaggio> rimasti(boolean[] elim){
        List<Personaggio> r=new ArrayList<>();
        for(int i=0;i<tutti.length;i++) if(!elim[i]) r.add(tutti[i]);
        return r;
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  CARD MINI (setup)
    // ════════════════════════════════════════════════════════════════════════════
    class MiniCard extends JPanel {
        final Personaggio p; BufferedImage img; boolean hov;
        MiniCard(Personaggio p){
            this.p=p; setPreferredSize(new Dimension(CW,CH)); setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            try{File f=new File(p.getUrl());if(f.exists())img=ImageIO.read(f);}catch(Exception ig){}
            addMouseListener(new MouseAdapter(){
                public void mouseEntered(MouseEvent e){hov=true;repaint();}
                public void mouseExited(MouseEvent e){hov=false;repaint();}
            });
        }
        @Override protected void paintComponent(Graphics g){
            Graphics2D g2=(Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            int w=getWidth(),h=getHeight();
            g2.setColor(hov?new Color(32,52,82):BG_CARD); g2.fillRoundRect(0,0,w,h,12,12);
            g2.setColor(hov?ACCENT_BLUE:new Color(45,58,85)); g2.setStroke(new BasicStroke(hov?2f:1f));
            g2.drawRoundRect(0,0,w-1,h-1,12,12);
            int ix=(w-IW)/2,iy=8;
            if(img!=null) g2.drawImage(img,ix,iy,IW,IH,null);
            else{g2.setColor(new Color(45,58,85));g2.fillRoundRect(ix,iy,IW,IH,8,8);}
            g2.setFont(new Font("SansSerif",Font.BOLD,10));
            FontMetrics fm=g2.getFontMetrics();
            g2.setColor(hov?ACCENT_GOLD:TEXT_WHITE);
            g2.drawString(p.getNome(),(w-fm.stringWidth(p.getNome()))/2,iy+IH+fm.getAscent()+3);
            g2.dispose();
        }
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  CARD TABELLONE
    // ════════════════════════════════════════════════════════════════════════════
    class PersonaggioCard extends JPanel {
        final Personaggio p; final int idx; final boolean[] elimRef; final boolean botBoard;
        BufferedImage img; boolean evid;
        PersonaggioCard(Personaggio p,int i,boolean[] el,boolean bb){
            this.p=p;idx=i;elimRef=el;botBoard=bb;
            setPreferredSize(new Dimension(CW,CH));setOpaque(false);setToolTipText(p.getNome());
            try{File f=new File(p.getUrl());if(f.exists())img=ImageIO.read(f);}catch(Exception ig){}
        }
        void setEvidenziato(boolean v){evid=v;repaint();}
        @Override protected void paintComponent(Graphics g){
            Graphics2D g2=(Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            boolean el=elimRef[idx]; int w=getWidth(),h=getHeight(),arc=13;
            g2.setColor(evid?new Color(28,58,36):BG_CARD); g2.fillRoundRect(0,0,w,h,arc,arc);
            g2.setColor(evid?new Color(75,210,105):el?new Color(36,42,60):new Color(48,60,86));
            g2.setStroke(new BasicStroke(evid?2.5f:1f)); g2.drawRoundRect(0,0,w-1,h-1,arc,arc);
            int ix=(w-IW)/2,iy=8;
            if(img!=null){
                if(el) g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,.26f));
                g2.drawImage(img,ix,iy,IW,IH,null);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
            } else {
                g2.setColor(new Color(45,55,78)); g2.fillRoundRect(ix,iy,IW,IH,8,8);
            }
            if(el&&!evid){
                g2.setColor(botBoard?ELIM_B:ELIM_U); g2.fillRoundRect(0,0,w,h,arc,arc);
                g2.setColor(botBoard?new Color(195,50,95):new Color(195,55,55));
                g2.setStroke(new BasicStroke(3.2f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
                int m=11; g2.drawLine(m,m,w-m,h-m-18); g2.drawLine(w-m,m,m,h-m-18);
            }
            int ny=iy+IH+5;
            g2.setFont(new Font("SansSerif",Font.BOLD,10)); FontMetrics fm=g2.getFontMetrics();
            g2.setColor(evid?new Color(95,235,125):el?new Color(55,65,88):TEXT_WHITE);
            g2.drawString(p.getNome(),(w-fm.stringWidth(p.getNome()))/2,ny+fm.getAscent());
            if(evid){g2.setColor(ACCENT_GOLD); g2.drawString("★",(w-10)/2,ny+fm.getAscent()+13);}
            g2.dispose();
        }
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  UTILITY UI
    // ════════════════════════════════════════════════════════════════════════════
    JButton roundBtn(String txt, Color bg, Color fg){
        JButton b=new JButton(txt){
            @Override protected void paintComponent(Graphics g){
                Graphics2D g2=(Graphics2D)g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground()); g2.fillRoundRect(0,0,getWidth(),getHeight(),11,11);
                g2.dispose(); super.paintComponent(g);
            }
        };
        b.setFont(new Font("SansSerif",Font.BOLD,13));
        b.setForeground(fg); b.setBackground(bg);
        b.setOpaque(false); b.setContentAreaFilled(false);
        b.setBorderPainted(false); b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
    void hoverFx(JButton b,Color n,Color h){
        b.addMouseListener(new MouseAdapter(){
            public void mouseEntered(MouseEvent e){b.setBackground(h);b.repaint();}
            public void mouseExited(MouseEvent e){b.setBackground(n);b.repaint();}
        });
    }
    JLabel sideTitle(String t,Color c){
        JLabel l=new JLabel(t); l.setFont(new Font("SansSerif",Font.BOLD,16));
        l.setForeground(c); l.setAlignmentX(CENTER_ALIGNMENT); return l;
    }
    JLabel sideSub(String t){
        JLabel l=new JLabel("<html><div style='text-align:center;width:205px;'>"+t+"</div></html>");
        l.setFont(new Font("SansSerif",Font.PLAIN,11)); l.setForeground(TEXT_MUTED);
        l.setAlignmentX(CENTER_ALIGNMENT); return l;
    }
    JLabel tiny(String t){
        JLabel l=new JLabel(t); l.setFont(new Font("SansSerif",Font.BOLD,11));
        l.setForeground(TEXT_MUTED); l.setAlignmentX(CENTER_ALIGNMENT); return l;
    }
    Component sep(){
        JSeparator s=new JSeparator(); s.setForeground(new Color(40,50,72));
        s.setMaximumSize(new Dimension(Integer.MAX_VALUE,1)); return s;
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  ENTRY POINT
    // ════════════════════════════════════════════════════════════════════════════
    public static void avvia(){
        SwingUtilities.invokeLater(()->{
            try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception ig){}
            new GUI().setVisible(true);
        });
    }
    public static void main(String[] args){ avvia(); }
}