import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JComponent;

/**
 * Icon for a FlightFilter
 * 
 * @author cwhelan
 * 
 */
public class FilterIcon implements Icon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color color;
	private String shape;
	private int width;
	private int height;

	/**
	 * @param colorIn
	 *            - color of the Icon
	 */
	public FilterIcon(Color colorIn, String shapeIn, int widthIn, int heightIn) {

		color = colorIn;
		shape = shapeIn;
		width = widthIn;
		height = heightIn;

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

	@Override
	public void paintIcon(Component aComponent, Graphics g, int anXCoord, int aYCoord) {
		g.setColor(color);
		if (this.shape.equals("circle")) {
			g.drawOval(5, 5, getIconWidth(), getIconHeight());
		} else
			g.drawRect(5, 5, getIconWidth(), getIconHeight());
	}

	public int getWidth() {

		return width;
	}

	public int getHeight() {

		return height;
	}

	public void setWidth(int w) {
		width = w;
	}

	public void setHeight(int h) {
		height = h;
	}

	@Override
	public int getIconWidth() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public int getIconHeight() {
		// TODO Auto-generated method stub
		return width;
	}
}