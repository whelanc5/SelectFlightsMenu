import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SlectFlightDetails extends JDialog implements ItemListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String[] programStrings = { null, "p1", "p2", "p3" };
	private static String[] airlineStrings = { null, "AAL", "DEL", "ANY" };
	private static String[] conformanceStrings = { null, "Conforming", "Non-Conforming" };
	private FlightFilter filter;
	
	
	JLabel acidLabel;
	JLabel typeLabel;
	
	private JCheckBox cbACID;
	private JTextField tfACID;
	private JCheckBox cbAirline;
	private JComboBox<String> airlineList;
	private JCheckBox cbAircraftType;
	private JTextField tfAircraftType;
	private JCheckBox cbA;
	private JCheckBox cbB;
	private JCheckBox cbC;
	private JCheckBox cbD;
	private JCheckBox cbE;
	private JCheckBox cbF;
	private JCheckBox cbProgram;
	private JComboBox<String> programList;
	private JCheckBox cbConformance;
	private JComboBox<String> conformanceList;
	private JButton okButton;
	private JButton cancelButton;
	private JButton helpButton;
	
	private ArrayList<JCheckBox> categories = new ArrayList<>();
	private String ACID_Input, AircraftType_Input;

	/*
	 * 
	 */
	public SlectFlightDetails() {
		filter = new FlightFilter();
		
	}

	public SlectFlightDetails(JFrame parent, FlightFilter f) {
		super(parent, "Define Filter");
		filter = f;

	}

	public void createGUI() {
		createAndShowGUI();
	}

	public void createAndShowGUI() {
		this.setModal(true);
		JLabel l1 = new JLabel("Define Filter");
		

		// program
		cbProgram = new JCheckBox("Program");
		programList = new JComboBox<String>(programStrings);
		if (filter.getProgram() != null && !filter.getProgram().equals("")) {
			cbProgram.setSelected(true);
			programList.setSelectedItem(filter.getProgram());
		}
		programList.setEnabled(cbProgram.isSelected());

		// conformance
		cbConformance = new JCheckBox("Conformance");
		conformanceList = new JComboBox<String>(conformanceStrings);
		if (filter.getConformance() != null && !filter.getConformance().equals("")) {
			cbConformance.setSelected(true);
			conformanceList.setSelectedItem(filter.getConformance());
		}
		conformanceList.setEnabled(cbConformance.isSelected());

		// aircraftType
		cbAircraftType = new JCheckBox("Aircraft Type");
		tfAircraftType = new JTextField("", 5);
		if (filter.getAircraftType() != null && !filter.getAircraftType().replaceAll("\\s+","").equals("")) {
			tfAircraftType.setText(filter.getAircraftType());
			cbAircraftType.setSelected(true);
		}
		tfAircraftType.setEnabled(cbAircraftType.isSelected());

		// Acids
		cbACID = new JCheckBox("ACID(s)");
		tfACID = new JTextField("", 10);
		if (filter.getAcids() != null && filter.getAcids().size() > 0 && !filter.getAcids().get(0).equals("")) {
			cbACID.setSelected(true);
			tfACID.setText(filter.getAcidsAsString());
		}
		tfACID.setEnabled(cbACID.isSelected());

		// Airline
		cbAirline = new JCheckBox("Airline(s)");
		airlineList = new JComboBox<String>(airlineStrings);
		if (filter.getAirline() != null && !filter.getAirline().equals("")) {
			cbAirline.setSelected(true);
			airlineList.setSelectedItem(filter.getAirline());
		}
		airlineList.setEnabled(cbAirline.isSelected());

		// category
		
		cbA = new JCheckBox("A");
		cbB = new JCheckBox("B");
		cbC = new JCheckBox("C");
		cbD = new JCheckBox("D");
		cbE = new JCheckBox("E");
		cbF = new JCheckBox("F");
		categories.add(cbA);
		categories.add(cbB);
		categories.add(cbC);
		categories.add(cbD);
		categories.add(cbE);
		categories.add(cbF);
		HashMap<String, Boolean> m = filter.getCategory();

		for (JCheckBox c : categories) {
			c.setSelected(m.get(c.getText()));
		}

		okButton = new JButton("OK");
		cancelButton = new JButton("CANCEL");
		helpButton = new JButton("HELP");
		tfACID.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void insertUpdate(DocumentEvent e) {
				acidLabel.setText("Acid: "+ tfACID.getText());
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				acidLabel.setText("Acid: "+ tfACID.getText());
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				acidLabel.setText("Acid: "+ tfACID.getText());
				
			}
			
		});
	

		cbACID.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Object scource = e.getItemSelectable();
				if (scource == cbACID) {
					tfACID.setEnabled(true);
				}
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					tfACID.setEnabled(false);
					// tfACID.setText("");
					// ACID_Input = null;
				}
			}
		});

		cbAirline.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Object scource = e.getItemSelectable();
				if (scource == cbAirline) {
					airlineList.setEnabled(true);
				}

				if (e.getStateChange() == ItemEvent.DESELECTED) {
					airlineList.setEnabled(false);
					// airlineList.setSelectedIndex(0);
					// airlineChoice = null;
				}
			}
		});

		tfAircraftType.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void insertUpdate(DocumentEvent e) {
				typeLabel.setText("Type: " + tfAircraftType.getText());
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				typeLabel.setText("Type: " + tfAircraftType.getText());
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				typeLabel.setText("Type: " + tfAircraftType.getText());
				
			}
			
		});
	

		cbAircraftType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Object scource = e.getItemSelectable();
				if (scource == cbAircraftType) {
					tfAircraftType.setEnabled(true);
				}

				if (e.getStateChange() == ItemEvent.DESELECTED) {
					tfAircraftType.setEnabled(false);
					// tfAircraftType.setText("");
					// AircraftType_Input = null;
				}
			}
		});

		cbProgram.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Object scource = e.getItemSelectable();
				if (scource == cbProgram) {
					programList.setEnabled(true);
				}

				if (e.getStateChange() == ItemEvent.DESELECTED) {
					programList.setEnabled(false);
					// programList.setSelectedIndex(0);
					// programChoice = null;
				}
			}
		});

		cbConformance.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Object scource = e.getItemSelectable();
				if (scource == cbConformance) {
					conformanceList.setEnabled(true);
				}

				if (e.getStateChange() == ItemEvent.DESELECTED) {
					conformanceList.setEnabled(false);
					// conformanceList.setSelectedIndex(0);

				}
			}
		});

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		okButton.setActionCommand("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// HAVE A BUNCH OF RETURN STATEMENTS HERE
				// USE THE IF STATEMENTS TO ONLY RETURN THE DATA IF THE
				// CHECKBOXES ARE CHECKED
				if (cbAirline.isSelected()) {
					filter.setAirline((String) airlineList.getSelectedItem());
				} else {
					filter.setAirline("");
				}
				if (cbAircraftType.isSelected()) {
					filter.setAircraftType(tfAircraftType.getText());
				} else {
					filter.setAircraftType("");
				}
				HashMap<String, Boolean> m = filter.getCategory();
				for (JCheckBox c : categories) {
					m.put(c.getText(), c.isSelected());
				}
				if (cbProgram.isSelected()) {
					filter.setProgram((String) programList.getSelectedItem());
				} else {
					filter.setProgram("");
				}
				if (cbConformance.isSelected()) {
					filter.setConformance((String) conformanceList.getSelectedItem());
				} else {
					filter.setConformance("");
				}
				if (cbACID.isSelected()) {
					ArrayList<String> acidList = new ArrayList<String>(Arrays.asList(tfACID.getText().split(",")));
					for (int i = 0; i < acidList.size(); i++){
						acidList.set(i, acidList.get(i).replaceAll("\\s+",""));
						if (acidList.get(i).equals("")){
							System.out.println("|" + acidList.get(i) + "|" );
							acidList.remove(i);
							i--;
						}
						
					} 
					filter.setAcids(acidList);
				} else {
					filter.setAcids(new ArrayList<String>());
				}

				dispose();
			}
		});
		cancelButton.setActionCommand("CANCEL");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if ("CANCEL".equals(a.getActionCommand())) {
					dispose();
				}
			}
		});
		helpButton.setActionCommand("HELP");
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if ("HELP".equals(a.getActionCommand())) {

					createAndShowHelpMenu();
				}
			}
		});

		// Set up first subpanel
		JPanel subPanel1 = new JPanel(new GridLayout());
		 acidLabel = new JLabel("ACID: " + filter.getAcidsAsString());
		 typeLabel = new JLabel("Type: " + filter.getAircraftType());
		JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
		wrapper.add(acidLabel);
		
		wrapper.add(typeLabel);
		subPanel1.add(wrapper);
		
		wrapper.setBackground(Color.LIGHT_GRAY);
		wrapper.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		wrapper.setOpaque(true);
		

		JPanel subPanelButton = new JPanel();

		subPanelButton.add(okButton);
		subPanelButton.add(cancelButton);
		subPanelButton.add(helpButton);

		add(subPanel1, BorderLayout.NORTH);
		add(subpanel2(), BorderLayout.WEST);
		add(subPanelButton, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void setACIDInput(String ACID_Input) {
		this.ACID_Input = ACID_Input;
	}

	public String getACIDInput() {
		if (ACID_Input != null) {
			return ACID_Input;
		} else {
			return null;
		}
	}

	public void setAircraftTypeInput(String Aircraft_Input) {
		AircraftType_Input = Aircraft_Input;
	}

	public String getAircraftTypeInput() {
		if (AircraftType_Input != null) {
			return AircraftType_Input;
		} else {
			return null;
		}
	}

	/*
	 * public static void main(String[] args) {
	 * javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run()
	 * { new SlectFlightDetails(); } }); }
	 */
	public static void createAndShowHelpMenu() {
		JFrame helpFrame = new JFrame("Help");
		helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JTextArea helpInfo = new JTextArea(
				"Help Information Goes Here.\n" + "It could be Multiple Lines.\nOr even go to a link.");
		helpInfo.setEditable(false);

		JPanel subPanel = new JPanel();

		subPanel.add(helpInfo);

		helpFrame.getContentPane().add(subPanel);
		helpFrame.setLocationRelativeTo(null);
		helpFrame.pack();
		helpFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}

	public JPanel subpanel2() {
		JPanel subPanel2 = new JPanel(new BorderLayout());
		JPanel ACIDPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ACIDPanel.add(cbACID);
		ACIDPanel.add(tfACID);
		subPanel2.add(ACIDPanel, BorderLayout.NORTH);
		subPanel2.add(subpanel3(), BorderLayout.CENTER);
		return subPanel2;
	}

	public JPanel subpanel3() {
		JPanel subPanel3 = new JPanel(new BorderLayout());
		JPanel AirlinePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		AirlinePanel.add(cbAirline);
		AirlinePanel.add(airlineList);
		airlineList.setOpaque(true);
		airlineList.setBackground(Color.white);
		subPanel3.add(AirlinePanel, BorderLayout.NORTH);
		subPanel3.add(subpanel4(), BorderLayout.CENTER);
		return subPanel3;
	}

	public JPanel subpanel4() {

		JPanel subPanel4 = new JPanel(new BorderLayout());
		JPanel TypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		TypePanel.add(cbAircraftType);
		TypePanel.add(tfAircraftType);
		subPanel4.add(TypePanel, BorderLayout.NORTH);
		subPanel4.add(subpanel5(), BorderLayout.CENTER);
		return subPanel4;
	}

	public JPanel subpanel5() {
		
		JLabel categoryLabel = new JLabel("Category");
		JPanel subPanel5 = new JPanel(new BorderLayout());
		JPanel CategoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		CategoryPanel.add(categoryLabel);
		CategoryPanel.add(cbA);
		CategoryPanel.add(cbB);
		CategoryPanel.add(cbC);
		CategoryPanel.add(cbD);
		CategoryPanel.add(cbE);
		CategoryPanel.add(cbF);
		subPanel5.add(CategoryPanel, BorderLayout.NORTH);
		subPanel5.add(subpanel6(), BorderLayout.CENTER);
		return subPanel5;
	}

	public JPanel subpanel6() {
		JPanel subPanel6 = new JPanel(new BorderLayout());
		JPanel ProgramPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ProgramPanel.add(cbProgram);
		ProgramPanel.add(programList);
		programList.setOpaque(true);
		programList.setBackground(Color.white);
		subPanel6.add(ProgramPanel, BorderLayout.NORTH);
		subPanel6.add(subpanel7(), BorderLayout.CENTER);
		return subPanel6;
	}

	public JPanel subpanel7() {
		JPanel subPanel7 = new JPanel(new BorderLayout());
		JPanel ConformancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ConformancePanel.add(cbConformance);
		ConformancePanel.add(conformanceList);
		conformanceList.setOpaque(true);
		conformanceList.setBackground(Color.white);
		subPanel7.add(ConformancePanel);
		return subPanel7;
	}

}