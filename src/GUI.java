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
 * GUI completa per "Indovina Chi!" — usa tutte le classi del progetto.
 *
 * Classi usate:
 *   Albero, Nodo, Personaggio, GestoreFile         → dati e persistenza
 *   GiocatoreUmanoS                                → gestisce il turno dell'umano
 *   GiocatoreBotS                                  → gestisce il turno del bot
 *     (avanza nell'albero, ChiediDomanda, indovinaPersonaggio, getPersonaggiAttivi)
 *   ImagePanel                                     → rendering immagine nelle card
 *   ColoriCapelli, ColoriOcchi, ColorePelle        → domande tipizzate dell'umano
 *
 * FLUSSO:
 *   1. Setup  → l'umano clicca il suo personaggio segreto; il bot sceglie il suo a caso.
 *   2. Turno UMANO   → sceglie una domanda dal menu; il bot risponde SI/NO automaticamente.
 *   3. Turno BOT     → GiocatoreBotS fa una domanda; l'umano risponde SI/NO.
 *   4. Vince chi indovina per primo.
 *
 * USO:
 *   1. Esegui MainCaricaAlbero.main() una volta sola (genera albero.bin, personaggi.bin).
 *   2. Lancia: java GuindovinaChiGUI
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
    static final int COLS = 6;
    static final int CW = 110;
    static final int CH = 145;
    static final int IW = 80;
    static final int IH = 92;

    // ── dati ─────────────────────────────────────────────────────────────────────
    private Albero albero;
    private Personaggio[] tutti;

    private Personaggio segUmano;
    private Personaggio segBot;
    private GiocatoreUmanoS giocatoreUmano;

    /**
     * GiocatoreBotS — gestisce la logica del turno bot.
     * Il suo "tabellone" è la copia dei personaggi dell'umano che il bot elimina.
     * Metodi usati: ChiediDomanda(nodo), avanza(risposta),
     *               indovinaPersonaggio(), getPersonaggiAttivi().
     */
    private GiocatoreBotS giocatoreBot;

    /** Array parallelo a tutti[]: true = eliminato dal tabellone umano */
    private boolean[] elimU;
    /** Array parallelo a tutti[]: true = eliminato dal tabellone bot */
    private boolean[] elimB;

    private boolean turnoUmano;
    private List<DomandaGiocatore> domande;
    private Set<String> giàFatte = new HashSet<>();

    // ── componenti UI ────────────────────────────────────────────────────────────
    private PersonaggioCard[] cardsU, cardsB;
    private JPanel tabU, tabB, cardCont;
    private JPanel sidePanel, panUmano, panBot;
    private JLabel lblTurno, lblContatore, lblDomandaBot, lblRispostaBot;
    private JButton btnSi, btnNo, btnFai, btnIndovina;
    private JComboBox<DomandaGiocatore> combo;

    // ════════════════════════════════════════════════════════════════════════════
    public GUI() {
        super("Indovina Chi!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        getContentPane().setBackground(BG_DARK);
        caricaDati();
        schermataSetup();
    }

    // ════════════════════════════════════════════════════════════════════════════
    //  CARICAMENTO DATI  (GestoreFile)
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


    public static void main(String[] args) { avvia(); }
}