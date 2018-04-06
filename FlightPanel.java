
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JComponent;

/**
 * Panel for displaying icon on the designIconDialog
 * 
 * @author cwhelan
 * 
 */
public class FlightPanel extends JComponent  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color color;
	private String shape;
	private int size;
	private int width;
	private int height;

	/**
	 * @param colorIn
	 *            - color of the Icon
	 */
	public FlightPanel(Color colorIn) {
		setPreferredSize(new Dimension(75, 75));
		color = colorIn;
		shape = "circle";
		size = 50;
		
	}

	/**
	 * @param colorIn
	 *            - color of the Icon
	 * @param shapeIn
	 *            - shape of the Icon
	 */
	public FlightPanel(Color colorIn, String shapeIn) {
		setPreferredSize(new Dimension(75, 75));
		color = colorIn;
		shape = shapeIn;
		size = 50;
		width = 50;
		height = 50;
	}

	/**
	 * @param colorIn
	 *            - color of the Icon
	 * @param shapeIn
	 *            - shape of the Icon
	 * @param sizeIn
	 *            - size of the Icon
	 * @param widthIn
	 * @param heightIn
	 */
	public FlightPanel(Color colorIn, String shapeIn, int sizeIn, int widthIn, int heightIn) {
		setPreferredSize(new Dimension(75, 75));
		color = colorIn;
		shape = shapeIn;
		size = sizeIn;
		width = 50;
		height = 50;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(color);
		if (this.shape.equals("circle")) {
			g.drawOval(0, 0, size, size);
		} else
			g.drawRect(0, 0, size, size);

	}

	/**
	 * method to change the color of the FlightIcon
	 * 
	 * @param colorIn
	 */
	public void setColor(Color colorIn) {

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

	
	public void paintIcon(Component aComponent, Graphics g, int anXCoord, int aYCoord) {
		g.setColor(color);
		if (this.shape.equals("circle")) {
			g.drawOval(5, 5, getIconWidth(), getIconHeight());
		} else
			g.drawRect(5, 5, getIconWidth(), getIconHeight());
	}


	public int getIconWidth() {

		return width;
	}


	public int getIconHeight() {

		return height;
	}
}
