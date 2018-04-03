
import java.awt.Color;
import java.awt.Dimension;

import java.awt.Graphics;

import javax.swing.JComponent;

public class FlightIcon extends JComponent {

	private Color color;
	private String shape;

	public FlightIcon(Color colorIn) {
		setPreferredSize(new Dimension(75, 75));
		color = colorIn;
		shape = "circle";
	}
	
	public FlightIcon(Color colorIn, String shapeIn) {
		setPreferredSize(new Dimension(75, 75));
		color = colorIn;
		shape = shapeIn;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(color);
		if (this.shape.equals("circle")) {
			g.drawOval(0, 0, 50, 50);
		} else
			g.drawRect(0, 0, 50, 50);

	}

	public void changeColor(Color colorIn) {

		this.color = colorIn;

	}

	public Color getColor() {
		return color;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String str) {
		shape = str;
	}
}
