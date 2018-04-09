import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * A colored box icon
 *
 * @author Gil Handy
 * @version %I% %G%
 */
public class ColorIcon
  implements Icon
{
  private Color color;
  private int height;
  private int width;
  private RolloverRouteColorIcon rolloverIcon =
    new RolloverRouteColorIcon();

  /**
   * Constructor
   * @param theColor The color of the icon
   * @param theHeight The height of the icon
   * @param theWidth The width of the icon
   */
  public ColorIcon(Color theColor, int theHeight, int theWidth)
  {
    color = theColor;
    height = theHeight;
    width = theWidth;
  }

  /**
   * Interface method for drawing the icon
   * @param aComponent The component
   * @param theGraphics The graphics
   * @param anXCoord The X coordinate
   * @param aYCoord The Y coordinate
   */
  public void paintIcon(Component aComponent, Graphics theGraphics, int anXCoord, int aYCoord)
  {
    theGraphics.setColor(color);
    theGraphics.fillRect(5, 5, getIconWidth(), getIconHeight());
    theGraphics.setColor(Color.GRAY);
    theGraphics.drawRect(5, 5, getIconWidth(), getIconHeight());
  }

  /**
   * Interface method for icon width
   * @return The icon width
   */
  public int getIconWidth()
  {
    return width;
  }
  
  /**
   * Interface method for icon height
   * @return The icon height
   */
  public int getIconHeight()
  {
    return height;
  }

  /**
   * Retrieves a pressed icon based on the settings of the color icon
   * @param thePressedColor The pressed color
   * @return The pressed icon
   */
  public Icon getPressedIcon(Color thePressedColor)
  {
    return new PressedRouteColorIcon(thePressedColor);
  }
  
  /**
   * Retrieves a rollover icon based on the color icon
   * @return The rollover icon
   */
  public Icon getRolloverIcon()
  {
    return rolloverIcon;
  }

  /**
   * Sets the color of the icon.  Repaint after setting this property.
   * @param aColor The new color
   */
  public void setColor(Color aColor)
  {
    color = aColor;
  }
  
  /**
   * Gets the color of the icon.
   * @return the color.
   */
  public Color getColor()
  {
    return color;
  }

  /**
   * Inner class for pressed icon based on this color icon
   */
  private class PressedRouteColorIcon
    implements Icon
  {
    private Color pressedColor;
    
    /**
     * Constructor
     * @param theColor The pressed icon color
     */
    private PressedRouteColorIcon(Color theColor)
    {
      pressedColor = theColor;
    }
  
    /**
     * Interface method for drawing the icon
     * @param aComponent The component
     * @param theGraphics The graphics
     * @param anXCoord The X coordinate
     * @param aYCoord The Y coordinate
     */
    public void paintIcon(Component aComponent, Graphics theGraphics, int anXCoord, int aYCoord)
    {
      theGraphics.setColor(pressedColor);
      theGraphics.fillRect(5, 5, getIconWidth(), getIconHeight());
      theGraphics.setColor(Color.GRAY);
      theGraphics.drawRect(5, 5, getIconWidth(), getIconHeight());
    }
    
    /**
     * Interface method for icon width
     * @return The icon width
     */
    public int getIconWidth()
    {
      return ColorIcon.this.getIconWidth();
    }
    
    /**
     * Interface method for icon height
     * @return The icon height
     */
    public int getIconHeight()
    {
      return ColorIcon.this.getIconHeight();
    }
  }
  
  /**
   * Inner class for rollover icon based on this color icon
   */
  private class RolloverRouteColorIcon
    implements Icon
  {
    /**
     * Interface method for drawing the icon
     * @param aComponent The component
     * @param theGraphics The graphics
     * @param anXCoord The X coordinate
     * @param aYCoord The Y coordinate
     */
    public void paintIcon(Component aComponent, Graphics theGraphics, int anXCoord, int aYCoord)
    {
      theGraphics.setColor(color);
      theGraphics.fillRect(5, 5, getIconWidth(), getIconHeight());
      theGraphics.setColor(Color.GRAY);
      theGraphics.drawRect(5, 5, getIconWidth(), getIconHeight());

      theGraphics.setColor(Color.BLUE);
      theGraphics.drawRect(6, 6, getIconWidth() - 1, getIconHeight() - 1);
      theGraphics.setColor(Color.WHITE);
      theGraphics.drawRect(7, 7, getIconWidth() - 2, getIconHeight() - 2);
    }
    
    /**
     * Interface method for icon width
     * @return The icon width
     */
    public int getIconWidth()
    {
      return ColorIcon.this.getIconWidth();
    }
    
    /**
     * Interface method for icon height
     * @return The icon height
     */
    public int getIconHeight()
    {
      return ColorIcon.this.getIconHeight();
    }
  }
}
