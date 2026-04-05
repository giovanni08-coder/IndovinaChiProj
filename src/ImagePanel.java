import java.awt.*;
import java.awt.image.BufferedImage;

//classe che serve per mostrare le immagini
public class ImagePanel extends Panel {
    private BufferedImage immagine;

    public ImagePanel(int w, int h) {
        setPreferredSize(new Dimension(w, h));
    }

    public void setImage(Image img) {
        if (img == null) { immagine = null; repaint(); return; }
        immagine = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = immagine.createGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        repaint();
    }

    @Override public void paint(Graphics g) {
        if (immagine != null) g.drawImage(immagine, 0, 0, getWidth(), getHeight(), this);
    }

    @Override public void update(Graphics g) { paint(g); } // evita sfarfallio AWT
}