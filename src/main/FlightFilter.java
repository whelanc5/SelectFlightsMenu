
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class FlightFilter implements Comparable<FlightFilter> {
	private boolean definition;
	private boolean show;
	private String name;
	private FilterIcon icon;
	private String dest;
	private String dFix;
	private int priority;
	private ArrayList<String> acids;
	private String airline;
	private HashMap<String, Boolean> category;
	private String program;
	private String conformance;
	private String aircraftType;

	public FlightFilter() {
		icon = new FilterIcon(new Color(255, 127, 80), "circle", 40, 40);
		classSetUp();

	}

	private void classSetUp() {

		show = false;
		definition = false;
		name = "";
		dest = "";
		dFix = "";
		priority = 100;
		acids = new ArrayList<>();
		airline = new String();
		program = new String();
		conformance = new String();
		aircraftType = "";
		category = new HashMap<String, Boolean>();
		category.put("A", false);
		category.put("B", false);
		category.put("C", false);
		category.put("D", false);
		category.put("E", false);
		category.put("F", false);
	}

	public FlightFilter(Color color) {
		icon = new FilterIcon(new Color(255, 127, 80), "circle", 30, 30);
		classSetUp();
	}

	public FlightFilter(Color color, String shape) {
		icon = new FilterIcon(new Color(255, 127, 80), "circle", 30, 30);
		classSetUp();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FilterIcon getIcon() {
		return icon;
	}

	public void setIcon(FilterIcon icon) {
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

	public String getConformance() {
		return conformance;
	}

	public void setConformance(String conformance) {
		this.conformance = conformance;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public HashMap<String, Boolean> getCategory() {
		return this.category;
	}

	public void setCategory(HashMap<String, Boolean> category) {
		this.category = category;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airlines) {
		this.airline = airlines;
	}

	public ArrayList<String> getAcids() {
		return acids;
	}

	public void setAcids(ArrayList<String> acids) {
		this.acids = acids;
	}

	public String getAcidsAsString() {

		String temp = "";
		for (int i = 0; i < acids.size(); i++) {
			temp = temp + acids.get(i);
			if (i < acids.size() - 1) {
				temp = temp + ", ";
			}
		}
		return temp;
	}

	public String getAircraftType() {
		return aircraftType;
	}

	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}

	public void copyFrom(FlightFilter copyFrom) {
		this.icon.setColor(copyFrom.getIcon().getColor());
		this.icon.setShape(copyFrom.getIcon().getShape());
		this.name = copyFrom.getName();
		this.dest = copyFrom.getDest();
		this.dFix = copyFrom.getdFix();
		this.show = copyFrom.isShow();
		this.definition = copyFrom.isDefinition();
		this.acids = copyFrom.getAcids();
		this.airline = copyFrom.getAirline();
		this.category = copyFrom.getCategory();
		this.program = copyFrom.getProgram();
		this.conformance = copyFrom.getConformance();
	}
}
