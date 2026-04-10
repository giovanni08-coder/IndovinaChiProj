import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/** Pannello Swing per mostrare un'immagine scalata. */
public class ImagePanel extends JPanel {
    private BufferedImage immagine;

    public ImagePanel(int w, int h) {
        setPreferredSize(new Dimension(w, h));
        setOpaque(false);
    }

    public void setImage(Image img) {
        if (img == null) { immagine = null; repaint(); return; }
        immagine = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = immagine.createGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (immagine != null) {
            g.drawImage(immagine, 0, 0, getWidth(), getHeight(), this);
        }
    }
}