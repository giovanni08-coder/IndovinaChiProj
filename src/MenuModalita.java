import javax.swing.*;
import java.awt.*;


public class MenuModalita extends JFrame {

    public static final int MODALITA_CLASSICA = 1;
    public static final int MODALITA_ALBERO   = 2;

    private static final Color COL_BG      = new Color(15, 12, 41);
    private static final Color COL_CARD    = new Color(26, 22, 65);
    private static final Color COL_ACCENT  = new Color(255, 214, 0);
    private static final Color COL_ACCENT2 = new Color(255, 100, 80);
    private static final Color COL_PURPLE  = new Color(160, 100, 255);
    private static final Color COL_TEXT    = new Color(240, 235, 255);
    private static final Color COL_SUBTEXT = new Color(160, 150, 200);

    private int scelta = -1;



    public MenuModalita() {
        setTitle("Indovina Chi — Scegli modalità");
        setSize(540, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
                g2.setColor(new Color(255, 214, 0, 14));
                g2.fillOval(-60, -60, 260, 260);
                g2.setColor(new Color(160, 100, 255, 12));
                g2.fillOval(330, 300, 250, 250);
            }
        };
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(BorderFactory.createEmptyBorder(40, 50, 36, 50));
        setContentPane(root);


        JLabel lblTitolo = new JLabel("SCEGLI MODALITÀ", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 32));
        lblTitolo.setForeground(COL_ACCENT);
        lblTitolo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Come vuoi giocare?", SwingConstants.CENTER);
        lblSub.setFont(new Font("Serif", Font.ITALIC, 15));
        lblSub.setForeground(COL_SUBTEXT);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 214, 0, 60));
        sep.setMaximumSize(new Dimension(360, 2));


        JPanel cardClassica = creaScheda(
                "▶  CLASSICA",
                "Il bot sceglie le domande in modo casuale.",
                "Modalità standard, adatta a tutti.",
                COL_ACCENT
        );
        cardClassica.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) { scelta = MODALITA_CLASSICA; }
            @Override public void mouseEntered(java.awt.event.MouseEvent e) { cardClassica.setBackground(new Color(40, 35, 90)); repaint(); }
            @Override public void mouseExited(java.awt.event.MouseEvent e)  { cardClassica.setBackground(COL_CARD);              repaint(); }
        });
        cardClassica.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel cardAlbero = creaScheda(
                "🌳  CON ALBERO",
                "Il bot costruisce un albero decisionale in tempo reale.",
                "Ogni risposta viene memorizzata: SI → destra, NO → sinistra.",
                COL_PURPLE
        );
        cardAlbero.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) { scelta = MODALITA_ALBERO; }
            @Override public void mouseEntered(java.awt.event.MouseEvent e) { cardAlbero.setBackground(new Color(40, 35, 90)); repaint(); }
            @Override public void mouseExited(java.awt.event.MouseEvent e)  { cardAlbero.setBackground(COL_CARD);              repaint(); }
        });
        cardAlbero.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        JButton btnIndietro = creaBottoneInline("← Indietro");
        btnIndietro.addActionListener(e -> { scelta = 0; dispose(); });


        root.add(lblTitolo);
        root.add(Box.createVerticalStrut(6));
        root.add(lblSub);
        root.add(Box.createVerticalStrut(20));
        root.add(sep);
        root.add(Box.createVerticalStrut(28));
        root.add(cardClassica);
        root.add(Box.createVerticalStrut(16));
        root.add(cardAlbero);
        root.add(Box.createVerticalStrut(20));
        root.add(btnIndietro);

        setVisible(true);
    }




    public int aspettaScelta() {
        while (scelta == -1) {
            try { Thread.sleep(80); } catch (InterruptedException ignored) {}
        }
        dispose();
        return scelta;
    }





    private JPanel creaScheda(String titolo, String descr1, String descr2, Color accentColore) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COL_CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accentColore, 2, true),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));
        panel.setMaximumSize(new Dimension(440, 110));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitolo = new JLabel(titolo);
        lblTitolo.setFont(new Font("Serif", Font.BOLD, 18));
        lblTitolo.setForeground(accentColore);
        lblTitolo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblD1 = new JLabel(descr1);
        lblD1.setFont(new Font("Serif", Font.PLAIN, 13));
        lblD1.setForeground(COL_TEXT);
        lblD1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblD2 = new JLabel(descr2);
        lblD2.setFont(new Font("Serif", Font.ITALIC, 12));
        lblD2.setForeground(COL_SUBTEXT);
        lblD2.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblTitolo);
        panel.add(Box.createVerticalStrut(6));
        panel.add(lblD1);
        panel.add(Box.createVerticalStrut(3));
        panel.add(lblD2);
        return panel;
    }

    private JButton creaBottoneInline(String testo) {
        JButton btn = new JButton(testo);
        btn.setFont(new Font("Serif", Font.BOLD, 13));
        btn.setForeground(COL_SUBTEXT);
        btn.setBackground(new Color(0, 0, 0, 0));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }
}
