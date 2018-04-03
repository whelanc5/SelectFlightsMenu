

import java.awt.Color;
import java.util.ArrayList;

public class FlightFilter implements Comparable<FlightFilter> {
	private boolean definition;
	private boolean show;
	private String name;
	private FlightIcon icon;
	private String dest;
	private String dFix;
	private int priority;
	ArrayList<String> acids;
	ArrayList<String> airlines;

	public FlightFilter() {
		icon = new FlightIcon(new Color(255,127,80));
		show = false;
		definition = false;
		name = "";
		dest = "";
		dFix = "";
		priority = 100;
	}
	public FlightFilter(Color color) {
		icon = new FlightIcon(color);
	}
	public FlightFilter(Color color, String shape) {
		icon = new FlightIcon(color, shape);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FlightIcon getIcon() {
		return icon;
	}

	public void setIcon(FlightIcon icon) {
		this.icon = icon;
	}
	public boolean isDefinition() {
		return definition;
	}
	public void setDefinition(boolean definition) {
		this.definition = definition;
	}
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	public String getdFix() {
		return dFix;
	}
	public void setdFix(String dFix) {
		this.dFix = dFix;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	@Override
	public int compareTo(FlightFilter o) {
		
		return Integer.compare(priority, o.getPriority());
	}
}
