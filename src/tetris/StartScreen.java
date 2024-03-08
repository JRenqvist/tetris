package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class StartScreen extends JComponent {

    private ImageIcon icon;

    public StartScreen() {
	final URL image = ClassLoader.getSystemResource("images/tetris.png");
	this.icon = new ImageIcon(image);
    }

    public void paintComponent(final Graphics g) {
	final Graphics2D g2d = (Graphics2D) g;
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	final AffineTransform old = g2d.getTransform();
	final AffineTransform at = AffineTransform.getScaleInstance(0.5, 0.5);
	g2d.transform(at);

	this.icon.paintIcon(this, g, 0, 0);

	g2d.setTransform(old);
    }

    public ImageIcon getIcon() {
	return icon;
    }
    public int getWidth() {
	return icon.getIconWidth();
    }

    public int getHeight() {
	return icon.getIconHeight();
    }
}
