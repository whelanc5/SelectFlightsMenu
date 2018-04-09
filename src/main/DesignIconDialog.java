
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DesignIconDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Hashmap to map colors to their string representation
	private HashMap<String, Color> colorsList = new HashMap<>();
	//FlightFilter to be edided
	private FlightFilter filter;
	//temporary FlightIcon to store changed until they're saved
	private FlightPanel icon;
	//ButtonGroup to control the selections of the colorButtons
	private ButtonGroup colorBoxes = new ButtonGroup();

	/**
	 * Class Constructor.
	 * 
	 * @param parent
	 *            - the parent JFrame
	 * @param f
	 *            - FlightFilter object that's icon is being designed
	 */
	public DesignIconDialog(JFrame parent, FlightFilter f) {
		super(parent, "Design Icon");
		this.filter = f;
		this.setModal(true);
		icon = new FlightPanel(Color.red);
		icon.setColor(f.getIcon().getColor());
		icon.setShape(f.getIcon().getShape());		
	}
	
	/**
	 * Method for creating GUI
	 */
	public void setUpGUI(){
		this.getContentPane().add(getPanel());
		pack();
		setVisible(true);
	}

	/**
	 * method for creating the main JPanel for the dialog
	 * 
	 * @return - JPanel
	 */
	private JPanel getPanel() {

		JPanel designPanel = new JPanel(new BorderLayout());
		JLabel designLabel = new JLabel("Design Icon");
		Font font = designLabel.getFont();
		@SuppressWarnings("unchecked")
		Map<TextAttribute, Integer> attributes = (Map<TextAttribute, Integer>) font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		designLabel.setFont(font.deriveFont(attributes));
		designPanel.add(designLabel, BorderLayout.NORTH);

		// Setting up button Panel

		designPanel.add(getButtonPanel(), BorderLayout.SOUTH);

		// pane for the right side of the layout
		JPanel rightSidePanel = new JPanel(new BorderLayout());

		rightSidePanel.add(this.getColorPanel(), BorderLayout.NORTH);

		designPanel.add(rightSidePanel, BorderLayout.EAST);

		designPanel.add(new JPanel());

		// pane for the left side of the layout
		JPanel leftSidePanel = new JPanel(new BorderLayout());

		leftSidePanel.add(this.getPreviewPanel(), BorderLayout.NORTH);
		leftSidePanel.add(this.getMarkingPanel(), BorderLayout.CENTER);

		designPanel.add(leftSidePanel, BorderLayout.WEST);

		return designPanel;
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * Apply and OK will copy the color and shape from the icon object into the passed in filter's icon,
		 * Cancel will just dispose() and the data won't be saved
		 */
		if (e.getActionCommand().equals("Apply")) {
			filter.getIcon().setColor(icon.getColor());
			filter.getIcon().setShape(icon.getShape());
		} else if (e.getActionCommand().equals("OK")) {
			filter.getIcon().setColor(icon.getColor());
			filter.getIcon().setShape(icon.getShape());
			dispose();
		} else if (e.getActionCommand().equals("Cancel")) {
			dispose();
		} else if (e.getActionCommand().equals("Help")) {
			getHelpPanel();
			// action command to set color
		} else if (colorsList.get(e.getActionCommand()) != null) {
			colorBoxes.clearSelection();
			icon.setColor(colorsList.get(e.getActionCommand()));
			icon.repaint();
			JCheckBox cb = (JCheckBox) e.getSource();
			cb.setSelected(true);
		}

	}

	/**
	 * method for help dialog
	 * 
	 * @return - JDialog for Help menu
	 * 
	 */
	private JDialog getHelpPanel() {
		JDialog help = new JDialog((JFrame) this.getParent());

		JPanel helpPanel = new JPanel();

		help.add(helpPanel);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				help.dispose();
			}
		});

		helpPanel.add(okButton);

		help.pack();
		help.setVisible(true);

		return help;

	}

	/**
	 * method for creating the preview panel
	 * 
	 * @return JPanel with preview of the Icon
	 */

	private JPanel getPreviewPanel() {

		JPanel previewPanel = new JPanel(new BorderLayout());
		// previewPanel.setLayout(new BoxLayout(previewPanel, ));

		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		previewPanel.add(labelPanel);
		JLabel previewLabel = new JLabel("Preview");
		labelPanel.setOpaque(true);
		labelPanel.setBackground(Color.lightGray);

		labelPanel.add(previewLabel);
		previewPanel.add(labelPanel);

		JPanel iconPanel = new JPanel(new BorderLayout());
		JPanel previewIconPanel = new JPanel();
		previewIconPanel.setSize(new Dimension(50, 50));
		previewIconPanel.setBackground(Color.WHITE);

		previewIconPanel.add(icon);

		String[] shapes = { "rectangle", "circle" };
		JComboBox<String> shapeComboBox = new JComboBox<String>(shapes);
		shapeComboBox.setSelectedItem(filter.getIcon().getShape());
		shapeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				icon.setShape(shapeComboBox.getSelectedItem().toString());
				icon.repaint();
				// "this" here would refer to the ActionListener (not that
				// useful)
			}
		});

		previewPanel.add(iconPanel, BorderLayout.SOUTH);
		iconPanel.add(previewIconPanel, BorderLayout.NORTH);
		iconPanel.add(shapeComboBox, BorderLayout.SOUTH);
		icon.repaint();
		return previewPanel;
	}

	/**
	 * method for creating the color panel
	 * 
	 * @return - JPanel with selectable color buttons
	 */
	private JPanel getColorPanel() {

		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		labelPanel.setOpaque(true);
		labelPanel.setBackground(Color.lightGray);
		JLabel colorLabel = new JLabel("Color");
		labelPanel.add(colorLabel);

		JPanel colorWrapper = new JPanel(new BorderLayout());

		colorWrapper.add(labelPanel, BorderLayout.NORTH);
		JPanel colorPanel = new JPanel(new GridLayout(6, 6));
		

		List<Color> colors = ColorList.getColors();
		/*Add the list of colors to the ColorPanel, and set them a selectedIcon and actionCommand, add them to the HashMap 
		* so they can be found via their string representation
		*/
		for (Color c : colors) {
			ColorIcon cIcon = new ColorIcon(c, 30, 30);
			JCheckBox cb = new JCheckBox(cIcon);
			cb.setSelectedIcon(cIcon.getRolloverIcon());
			cb.setActionCommand(cIcon.getColor().toString());
			colorsList.put(cIcon.getColor().toString(), cIcon.getColor());
			cb.addActionListener(this);
			colorBoxes.add(cb);
			colorPanel.add(cb);
			//Sets the currently selected color as selected when entering the dialog
			if (c.equals(filter.getIcon().getColor())) {
				cb.setSelected(true);
				
			}

		}

		colorWrapper.add(colorPanel, BorderLayout.CENTER);
		return colorWrapper;
	}

	/**
	 * method for making button panel
	 * 
	 * @return - JPanel with buttons
	 */

	private JPanel getButtonPanel() {
		JPanel buttonPanel = new JPanel(new GridLayout());

		JPanel applyWrapper = new JPanel();
		JButton applyButton = new JButton("Apply");
		applyButton.setActionCommand(applyButton.getText());
		applyButton.addActionListener(this);
		applyWrapper.add(applyButton);

		JPanel okWrapper = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.setActionCommand(okButton.getText());
		okButton.addActionListener(this);
		okWrapper.add(okButton);

		JPanel cancelWrapper = new JPanel();
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand(cancelButton.getText());
		cancelButton.addActionListener(this);
		cancelWrapper.add(cancelButton);

		JPanel helpWrapper = new JPanel();
		JButton helpButton = new JButton("Help");
		helpButton.setActionCommand(helpButton.getText());
		helpButton.addActionListener(this);
		helpWrapper.add(helpButton);

		buttonPanel.add(okWrapper);
		buttonPanel.add(applyWrapper);
		buttonPanel.add(cancelWrapper);
		buttonPanel.add(helpWrapper);

		return buttonPanel;
	}

	/**
	 * method to create the marking panel. Its unclear what the marking and border comboBoxes will do at this point
	 * @return JPanel 
	 */
	//TODO marking and border comboBoxes need functionality added
	private JPanel getMarkingPanel() {
		JPanel markingWrapper = new JPanel(new BorderLayout());
		// label panel set up
		JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		labelPanel.setOpaque(true);
		labelPanel.setBackground(Color.lightGray);
		JLabel markingLabel = new JLabel("Marking");
		labelPanel.add(markingLabel);
		markingWrapper.add(labelPanel, BorderLayout.NORTH);

		// marking panel
		JPanel markingPanel = new JPanel();
		markingPanel.setLayout(new BoxLayout(markingPanel, BoxLayout.Y_AXIS));

		// marking label and combobox set up
		JPanel markingBoxPanel = new JPanel(new GridBagLayout());
		String[] markingList = { "<NONE>", "1" };
		JComboBox<String> markingBox = new JComboBox<String>(markingList);
		JLabel markingBoxLabel = new JLabel("Marking");

		markingBoxPanel.add(markingBoxLabel);
		markingBoxPanel.add(markingBox);

		markingPanel.add(markingBoxPanel);

		// border label and combo box set up
		JPanel borderBoxPanel = new JPanel(new GridBagLayout());
		String[] borderList = { "<NONE>", "Red", "Blue" };
		JComboBox<String> borderBox = new JComboBox<String>(borderList);
		JLabel borderBoxLabel = new JLabel("Border   ");
		borderBoxPanel.add(borderBoxLabel);
		borderBoxPanel.add(borderBox);

		markingPanel.add(borderBoxPanel);

		markingWrapper.add(markingPanel);

		return markingWrapper;
	}
	
	  

}
