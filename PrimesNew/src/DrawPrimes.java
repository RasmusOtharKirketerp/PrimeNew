

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawPrimes extends JPanel {
	static Prime myPrime = new Prime(30L);

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// g2d.drawLine(Alma.loc.x,Alma.loc.y, Alma.loc.x+2,Alma.loc.y+2);
		this.setBackground(Color.black);
		myPrime.draw(g2d);
	}
	
	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame("Primes by ROM soft");
		DrawPrimes game = new DrawPrimes();
		frame.add(game);
		frame.setSize(2000, 1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//while (true) {
			game.repaint();
			Thread.sleep(1000);
		//}
	}
}