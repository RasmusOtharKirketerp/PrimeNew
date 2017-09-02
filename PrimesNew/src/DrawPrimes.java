

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawPrimes extends JPanel {
	static Prime myPrime = new Prime(10000L);
	
	static int maxX = 510;
	static int maxY = 300;
	

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//Graphics2D g2d = (Graphics2D) g;
		
		BufferedImage img = new BufferedImage(maxX, maxY, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = img.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.setBackground(Color.black);
		myPrime.draw(g2d, maxX, (int)maxY/2);
		myPrime.save(img);
	}
	
	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame("Primes by ROM soft");
		DrawPrimes drawPrime = new DrawPrimes();
		frame.add(drawPrime);
		frame.setSize(maxX, maxY);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawPrime.repaint();
		
	}
}