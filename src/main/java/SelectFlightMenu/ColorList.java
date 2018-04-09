import java.awt.Color;
import java.util.ArrayList;

public class ColorList {
	
	private static ArrayList<Color> colors;
	
	
	public ColorList(){
		
		
		
	}
	
	public static ArrayList<Color> getColors(){
		colors = new ArrayList<>();
		colors.add(new Color(1,1,1));
		colors.add(new Color(102,102,101));
		colors.add(new Color(153,153,153));
		colors.add(new Color(193,205,205));
		colors.add(new Color(253,253,253));
		colors.add(new Color(238,223,204));
		colors.add(new Color(139,0,0));
		colors.add(new Color(255,0,0));
		colors.add(new Color(250,69,0));
		colors.add(new Color(255,127,80));
		colors.add(new Color(255,182,193));
		colors.add(new Color(255,228,196));
		colors.add(new Color(125,38,205));
		colors.add(new Color(221,160,221));
		colors.add(new Color(205,0,205));
		colors.add(new Color(255,62,160));
		colors.add(new Color(255,187,255));
		colors.add(new Color(224,255,255));
		colors.add(new Color(39,64,139));
		colors.add(new Color(0,0,255));
		colors.add(new Color(79,148,205));
		colors.add(new Color(135,206,235));
		colors.add(new Color(0,255,255));
		colors.add(new Color(175,238,238));
		colors.add(new Color(105,139,34));
		colors.add(new Color(50,205,50));
		colors.add(new Color(143,188,143));
		colors.add(new Color(205,198,115));
		colors.add(new Color(26,178,120));
		colors.add(new Color(127,255,0));
		colors.add(new Color(205,102,29));
		colors.add(new Color(255,165,0));
		colors.add(new Color(255,215,0));
		colors.add(new Color(255,160,122));
		colors.add(new Color(255,255,0));
		colors.add(new Color(250,250,210));
		return colors;
	}
}
