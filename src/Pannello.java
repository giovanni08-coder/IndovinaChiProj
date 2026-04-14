import javax.swing.*;
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
