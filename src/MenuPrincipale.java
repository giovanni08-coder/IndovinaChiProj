import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class MenuPrincipale extends JFrame {

    public static final int SCELTA_GIOCA   = 1;
    public static final int SCELTA_REGOLE  = 2;
    public static final int SCELTA_ESCI    = 4;

    private int scelta = -1;

    private static final Color COL_BG  = new Color(15, 12, 41);
    private static final Color COL_CARD = new Color(26, 22, 65);
    private static final Color COL_ACCENT = new Color(255, 214, 0);
    private static final Color COL_ACCENT2 = new Color(255, 100, 80);
    private static final Color COL_TEXT = new Color(240, 235, 255);
    private static final Color COL_SUBTEXT = new Color(160, 150, 200);

    public MenuPrincipale() {
        setTitle("Indovina Chi — Menu");
        setSize(520, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(0, 0, COL_BG, getWidth(), getHeight(), new Color(30, 10, 60));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(new Color(255, 214, 0, 18));
                g2.fillOval(-80, -80, 300, 300);
                g2.setColor(new Color(255, 100, 80, 14));
                g2.fillOval(300, 350, 280, 280);
                g2.setColor(new Color(100, 80, 255, 20));
                g2.fillOval(350, -60, 220, 220);
            }
        };
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(BorderFactory.createEmptyBorder(50, 60, 40, 60));
        setContentPane(root);

        JLabel lblTitolo = new JLabel("INDOVINA CHI", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 46));
        lblTitolo.setForeground(COL_ACCENT);
        lblTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSottotitolo = new JLabel("Il gioco dei personaggi misteriosi", SwingConstants.CENTER);
        lblSottotitolo.setFont(new Font("Serif", Font.ITALIC, 16));
        lblSottotitolo.setForeground(COL_SUBTEXT);
        lblSottotitolo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(COL_ACCENT.getRed(), COL_ACCENT.getGreen(), COL_ACCENT.getBlue(), 80));
        sep.setMaximumSize(new Dimension(340, 2));

        JButton btnGioca = creaBottone("▶   GIOCA",   COL_ACCENT,  COL_BG);
        JButton btnRegole = creaBottone("📖  REGOLE",  COL_CARD,    COL_TEXT);
        JButton btnEsci = creaBottone("✕   ESCI",    COL_ACCENT2, COL_BG);

        btnGioca.addActionListener(e -> { scelta = SCELTA_GIOCA; });

        btnRegole.addActionListener(e -> mostraRegole());

        btnEsci.addActionListener(e -> {
            scelta = SCELTA_ESCI;
            dispose();
            System.exit(0);
        });

        root.add(lblTitolo);
        root.add(Box.createVerticalStrut(6));
        root.add(lblSottotitolo);
        root.add(Box.createVerticalStrut(18));
        root.add(sep);
        root.add(Box.createVerticalStrut(40));
        root.add(btnGioca);
        root.add(Box.createVerticalStrut(14));
        root.add(btnRegole);
        root.add(Box.createVerticalStrut(14));
        root.add(btnEsci);

        JLabel lblFooter = new JLabel("© 2025 Indovina Chi", SwingConstants.CENTER);
        lblFooter.setFont(new Font("Serif", Font.ITALIC, 11));
        lblFooter.setForeground(new Color(100, 90, 140));
        lblFooter.setAlignmentX(Component.CENTER_ALIGNMENT);
        root.add(Box.createVerticalGlue());
        root.add(lblFooter);

        setVisible(true);
    }

    public int aspettaSceltaMenu() {
        while (scelta == -1) {
            try { Thread.sleep(100); } catch (InterruptedException ignored) {}
        }
        dispose();
        return scelta;
    }

    private void mostraRegole() {
        String testo =
                "<html><body style='font-family:Serif; width:340px; color:#1a0a2e;'>" + "<h2 style='color:#b8860b;'>📖 Regole del gioco</h2>" + "<p><b>Obiettivo:</b> Indovina il personaggio segreto del tuo avversario " + "prima che lui indovini il tuo!</p><br>" + "<b>Come si gioca:</b>" + "<ol>" + "<li>Scegli il tuo personaggio segreto dalla griglia.</li>" + "<li>Ogni turno il bot ti farà una domanda: rispondi <b>SÌ</b> o <b>NO</b> " + "usando i bottoni in basso.</li>" +  "<li>Poi tocca a te: scegli una domanda da fare al bot tra quelle proposte.</li>" + "<li>Quando rimangono ≤ 6 personaggi avversari, puoi tentare di indovinare " + "il suo personaggio segreto.</li>" + "<li>Chi indovina per primo vince!</li>" + "</ol>" + "<p style='color:#888;font-style:italic;'>Attenzione: mentire nelle risposte " + "è rilevato automaticamente!</p>" + "</body></html>";

        JLabel lbl = new JLabel(testo);
        JOptionPane.showMessageDialog(this, lbl, "Regole", JOptionPane.PLAIN_MESSAGE);
    }

    private void mostraCrediti() {
        String testo = "<html><body style='font-family:Serif; width:300px; color:#1a0a2e; text-align:center;'>" + "<h2 style='color:#b8860b;'>★ Crediti</h2>" + "<p><b>Indovina Chi — Versione digitale</b></p><br>" + "<p>Sviluppato come progetto scolastico</p><br>" + "<p style='color:#555;'>Grafica e logica di gioco:<br><b>Il tuo nome qui</b></p><br>" + "<p style='color:#888; font-style:italic;'>Ispirato al classico gioco da tavolo<br>" + "\"Guess Who?\" di Milton Bradley</p>" + "</body></html>";

        JLabel lbl = new JLabel(testo);
        JOptionPane.showMessageDialog(this, lbl, "Crediti", JOptionPane.PLAIN_MESSAGE);
    }

    private JButton creaBottone(String testo, Color sfondo, Color testoCols) {
        JButton btn = new JButton(testo) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color base = sfondo;
                if (getModel().isPressed()) {
                    base = base.darker();
                } else if (getModel().isRollover()) {
                    base = base.brighter();
                }
                g2.setColor(base);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.setColor(new Color(255, 255, 255, 30));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 18, 18);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Serif", Font.BOLD, 17));
        btn.setForeground(testoCols);
        btn.setBackground(sfondo);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(340, 52));
        btn.setPreferredSize(new Dimension(340, 52));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }
}

