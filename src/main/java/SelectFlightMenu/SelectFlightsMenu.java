import java.util.EventObject;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

class SelectFlightsMenu extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	// indexes for elements in array for the definition column, FILTER_INDEX is
	// for the incoming FlightFilter, TEMP_INDEX is the location of the
	// temporary FlightFilter that holds the data until it is saved
	private static final int FILTER_INDEX = 0;
	private static final int TEMP_INDEX = 1;
	// indexes for columns in table
	private static final int SHOW_COLUMN = 0;
	private static final int ICON_COLUMN = 1;
	private static final int NAME_COLUMN = 2;
	private static final int DEST_COLUMN = 3;
	private static final int DFIX_COLUMN = 4;
	private static final int DEFINITION_COLUMN = 5;
	private static final int ACIDS_COLUMN = 6;
	//the model of the JTable, made a field so it is accessible to methods it isn't declared in
	private FiltersTableModel tableModel = new FiltersTableModel();
	private JFrame parentFrame;
	//holds the value of the PINNED flight Filter
	private FlightFilter pinned;
	//List of existing flight filters
	private List<FlightFilter> filters;

	/**
	 * Class Constructor.
	 * 
	 * @param parent
	 *            - the parent JFrame
	 * @param filtersIn
	 *            - List of FlightFilters
	 */
	public SelectFlightsMenu(JFrame theParent, List<FlightFilter> theFilters) {
		super(theParent, "Select Flights");
		parentFrame = theParent;
		filters = theFilters;
		int height = 250;
		if (filters.size() > 3){
			height = filters.size() * 75;
		}
		this.setPreferredSize(new Dimension(800, height));
		getContentPane().add(getPanel());
		pack();
		setVisible(true);

	}

	/**
	 * method for creating the panel that will display this dialog
	 * 
	 * @return JPanel
	 */
	private JPanel getPanel() {

		JPanel mainPanel = new JPanel(new BorderLayout());
		// Setting up button Panel

		JPanel buttonPanel = this.getButtonPanel();
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		JTable table = new JTable();
		table = getTable();
		table.setBackground(this.getBackground());
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		table.setDragEnabled(true);
		table.setDropMode(DropMode.INSERT_ROWS);
		table.setTransferHandler(new TableRowTransferHandler(table));
		table.setIntercellSpacing(new Dimension(0, 0));
		TableHeaderRenderer headerRenderer = new TableHeaderRenderer();
		// headerRenderer.set
		table.getTableHeader().setDefaultRenderer(headerRenderer);
		JScrollPane pane = new JScrollPane(table);
		pane.setBorder(BorderFactory.createEmptyBorder());
		mainPanel.add(pane);

		return mainPanel;
	}

	/**
	 * Method for creating the Table where the flightFliters will be displayed
	 * 
	 * @return JTable
	 */
	private JTable getTable() {
		JTable flightTable = new JTable();
		flightTable.setShowGrid(false);
		flightTable.setFillsViewportHeight(true);
		tableModel = new FiltersTableModel();

		flightTable.setModel(tableModel);
		tableModel.addColumn("Show");
		tableModel.addColumn("");
		tableModel.addColumn("Name");
		tableModel.addColumn("DEST");
		tableModel.addColumn("DFIX");
		tableModel.addColumn("Definition");
		tableModel.addColumn("");

		flightTable.getColumnModel().getColumn(SHOW_COLUMN).setMaxWidth(50);
		flightTable.getColumnModel().getColumn(ICON_COLUMN).setMaxWidth(50);
		flightTable.getColumnModel().getColumn(NAME_COLUMN).setPreferredWidth(100);
		flightTable.getColumnModel().getColumn(DEST_COLUMN).setPreferredWidth(100);
		flightTable.getColumnModel().getColumn(DFIX_COLUMN).setPreferredWidth(100);
		flightTable.getColumnModel().getColumn(DEFINITION_COLUMN).setPreferredWidth(100);

		// add a PINNED filter, if it doesn't exist
		if (filters.size() == 0) {
			FlightFilter f = new FlightFilter();
			f.setName("PINNED");
			f.setPriority(0);
			f.getIcon().setColor(Color.RED);
			f.getIcon().setShape("Rectangle");
			f.setDefinition(true);
			f.setShow(true);
			addEntry(f);
			pinned = f;

		} else {
			pinned = filters.get(0);
		}
		// add existing filters to the table
		for (int i = 0; i < filters.size(); i++) {
			addEntry(filters.get(i));
		}
		// listner for values changing in the table
		tableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				int row = flightTable.getSelectedRow();
				if (row >= 0) {
					FlightFilter f = tableModel.getTempFlight(row);
					f.setDest((String) tableModel.getValueAt(row, DEST_COLUMN));
					f.setdFix((String) tableModel.getValueAt(row, DFIX_COLUMN));
					f.setName((String) tableModel.getValueAt(row, NAME_COLUMN));
					f.setShow((Boolean) tableModel.getValueAt(row, SHOW_COLUMN));
				}
				System.out.println(1);
			}

		});
		// set up the iconColumn with its own renderer and editor
		TableColumn iconColumn = flightTable.getColumnModel().getColumn(ICON_COLUMN);
		ButtonRenderer iconRenderer = new ButtonRenderer();
		iconColumn.setCellRenderer(iconRenderer);
		ButtonEditor iconEditor = new ButtonEditor();
		iconColumn.setCellEditor(iconEditor);
		JFrame frame = (JFrame) this.getParent();
		// listen for the icon button being pushed, if so open a
		// DesignIconDialog for that entry
		iconEditor.addCellEditorListener(new CellEditorListener() {
			@Override
			public void editingStopped(ChangeEvent e) {
				DesignIconDialog designDialog = new DesignIconDialog(frame,
						tableModel.getTempFlight(flightTable.getSelectedRow()));
				designDialog.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						SelectFlightsMenu.this.toFront();
					}
				});
				designDialog.setUpGUI();

			}

			@Override
			public void editingCanceled(ChangeEvent e) {

			}
		});
		// set up definition column with its own renderer and editor
		TableColumn definition = flightTable.getColumnModel().getColumn(DEFINITION_COLUMN);
		DefinitionRenderer renderer = new DefinitionRenderer();
		DefinitionEditor editor = new DefinitionEditor();
		definition.setCellRenderer(renderer);
		definition.setCellEditor(editor);
		flightTable.setRowHeight(50);
		// listen for the checkbox and the button in the dfinition column, if
		// the button is pressed open a SlecFlightDetails dialog
		editor.addCellEditorListener(new CellEditorListener() {
			@Override
			public void editingStopped(ChangeEvent e) {
				// get value from the source to tell the difference between
				// checkbox and button, these determined by the state in the
				// DefinitionPane Class
				String s = (String) ((DefinitionEditor) e.getSource()).getCellEditorValue();
				if (s.equals("...")) {
					SlectFlightDetails slectWindow = new SlectFlightDetails(parentFrame,
							tableModel.getTempFlight(flightTable.getSelectedRow()));
					slectWindow.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							tableModel.setValueAt(
									tableModel.getTempFlight(flightTable.getSelectedRow()).getAcidsAsString(),
									flightTable.getSelectedRow(), ACIDS_COLUMN);
						}
					});
					slectWindow.createGUI();
				} else if (s.equals("cb")) {
					tableModel.changeBool(flightTable.getSelectedRow());
					tableModel.getTempFlight(flightTable.getSelectedRow())
							.setDefinition(tableModel.getBool(flightTable.getSelectedRow()));
				}
			}

			@Override
			public void editingCanceled(ChangeEvent e) {
			}
		});
		return flightTable;
	}

	/**
	 * @param f
	 *            - FlightFilter to be added to the table
	 */
	private void addEntry(FlightFilter flightFilter) {
		FlightFilter temp = new FlightFilter();
		temp.copyFrom(flightFilter);
		tableModel.addRow(new Object[] { temp.isShow(), temp.getIcon(), temp.getName(), temp.getDest(), temp.getdFix(),
				new Object[] { flightFilter, temp }, temp.getAcidsAsString() });
	}

	/**
	 * Method for getting the panel w/ southern buttons
	 * 
	 * @return JPanel
	 */
	private JPanel getButtonPanel() {
		JPanel buttonPanel = new JPanel(new GridLayout());

		JPanel addWrapper = new JPanel();
		JButton addEntryButton = new JButton("Add");
		addEntryButton.addActionListener(this);
		addEntryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FlightFilter f = new FlightFilter();
				// filters.add(f);
				addEntry(f);
			}
		});
		addWrapper.add(addEntryButton);
		buttonPanel.add(addWrapper);

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

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Apply") || e.getActionCommand().equals("OK")) {
			/*
			 * for (FlightFilter f : filters) {
			 * 
			 * for (int row = 0; row < tableModel.getRowCount(); row++) { if (f
			 * == ((Object[]) tableModel.getValueAt(row,
			 * DEFINITION_COLUMN))[FILTER_INDEX]) {
			 * f.copyFrom(tableModel.getTempFlight(row)); if (f.getName() ==
			 * "PINNED") { f.setPriority(0); } else { f.setPriority(row + 1); }
			 * } } }
			 */
			// go through rows and either to save their data to the passed in
			// filters list
			for (int row = 0; row < tableModel.getRowCount(); row++) {
				FlightFilter flightFilter;
				// check if the filter already exists, if so update it, if not
				// at it to the list
				int index = filters.indexOf(tableModel.getRealFlight(row));
				if (index > -1) {
					filters.get(index).copyFrom(tableModel.getTempFlight(row));
					flightFilter = filters.get(index);
				} else {
					flightFilter = tableModel.getTempFlight(row);
					filters.add(row, flightFilter);
					tableModel.setRealFlight(row, flightFilter);
				}
				// if this is the pinned object set it to 0 priority so it stays
				// at the top
				if (flightFilter == pinned) {
					flightFilter.setPriority(0);
				}
				// set priority based on the row the filter was last placed on
				else {
					flightFilter.setPriority(row + 1);
				}

			}
			// remove entries that have empty string as the name
			for (int i = 0; i < filters.size(); i++) {
				// TODO may change how this remove works
				if (filters.get(i).getName().equals("")) {
					filters.remove(i);
					i--;
				}
			}
			// sort the filters, which is done by priority in the FlightFilter
			// class
			filters.sort(null);
			if (e.getActionCommand().equals("OK")) {
				dispose();
			}
		} else if (e.getActionCommand().equals("Cancel")) {
			dispose();
		}

	}

	/**
	 * method used to change value of checkbox within the DefinitionPane
	 * 
	 * @param checkBox
	 * @param value
	 *            - value checkbox should be changed to
	 */
	private static void setCheckboxValue(JCheckBox checkBox, Object value) {
		if (value instanceof Boolean) {
			checkBox.setSelected(((Boolean) value).booleanValue());
		} else if (value instanceof String) {
			checkBox.setSelected(value.equals("true"));
		}
	}

	/**
	 * method to maintain the Appearance of the DefinitionRenderer and
	 * DefinitionEditor
	 * 
	 * @param to
	 * @param from
	 */
	private static void copyAppearanceFrom(JPanel to, Component from) {
		if (from != null) {
			// to.setOpaque(true);
			to.setBackground(from.getBackground());
			if (from instanceof JComponent) {
				to.setBorder(((JComponent) from).getBorder());
			}
		} else {
			to.setOpaque(false);
		}
	}

	/**
	 * @author cwhelan inner class used to embed a button and checkbox into the
	 *         same JTable
	 */
	private class DefinitionPane extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JButton button;
		private JCheckBox cb;
		private String state;

		public DefinitionPane() {
			setLayout(new GridBagLayout());
			button = new JButton("...");
			button.setActionCommand("...");
			cb = new JCheckBox();
			cb.setOpaque(false);
			cb.setActionCommand("cb");
			add(cb);
			add(button);
			state = "nil";

			ActionListener listener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equals("cb") || e.getActionCommand().equals("...")) {

						state = e.getActionCommand();

					} else
						state = "nil";
				}

			};

			cb.addActionListener(listener);
			button.addActionListener(listener);
		}

		public void addActionListener(ActionListener listener) {

			cb.addActionListener(listener);
			button.addActionListener(listener);
		}

		public String getState() {
			return state;
		}

		public void setState(String s) {
			state = s;
		}

		public JCheckBox getCheckBox() {
			return cb;
		}

		public void setButton(Boolean b) {
			button.setEnabled(b);
		}
	}

	/**
	 * @author cwhelan Renderer to display checkbox and Button in same Table
	 *         column
	 */
	private class DefinitionRenderer extends DefaultTableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private DefinitionPane definitionPane;

		public DefinitionRenderer() {
			definitionPane = new DefinitionPane();
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			setCheckboxValue(definitionPane.getCheckBox(), tableModel.getTempFlight(row).isDefinition());
			definitionPane.setButton(definitionPane.getCheckBox().isSelected());
			copyAppearanceFrom(definitionPane, this);
			return definitionPane;
		}

	}

	/**
	 * @author cwhelan Editor to control checkbox and Button in same Table
	 *         column
	 */
	private class DefinitionEditor extends AbstractCellEditor implements TableCellEditor {
		private static final long serialVersionUID = 1L;
		private DefinitionPane definitionPane;

		public DefinitionEditor() {

			definitionPane = new DefinitionPane();
			definitionPane.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							stopCellEditing();
						}
					});
				}
			});
		}

		@Override
		public Object getCellEditorValue() {
			return definitionPane.getState();
		}

		@Override
		public boolean isCellEditable(EventObject e) {
			return true;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			setCheckboxValue(definitionPane.getCheckBox(), tableModel.getTempFlight(row).isDefinition());
			TableCellRenderer renderer = table.getCellRenderer(row, column);
			Component c = renderer.getTableCellRendererComponent(table, value, true, true, row, column);
			definitionPane.setButton(definitionPane.getCheckBox().isSelected());
			copyAppearanceFrom(definitionPane, c);
			definitionPane.setState("nil");
			return definitionPane;
		}

	}

	/**
	 * Renderer for the table headers
	 */
	public class TableHeaderRenderer extends DefaultTableHeaderCellRenderer {

		private static final long serialVersionUID = 1L;
		private JPanel pane = new JPanel();
		JLabel label = new JLabel();

		public TableHeaderRenderer() {
			pane = new JPanel();
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			label.setText((String) tableModel.getColumnName(column));
			Font font = label.getFont();
			@SuppressWarnings("unchecked")
			Map<TextAttribute, Integer> attributes = (Map<TextAttribute, Integer>) font.getAttributes();
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			label.setFont(font.deriveFont(attributes));
			pane.add(label);

			return pane;
		}
	}

	/**
	 * @author cwhelan Renderer to display checkbox and Button in same Table
	 *         column
	 */
	private class ButtonRenderer implements TableCellRenderer {

		private JButton button;

		public ButtonRenderer() {
			button = new JButton();
			button.setContentAreaFilled(false);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
			}
			button.setIcon(tableModel.getTempFlight(row).getIcon());
			button.repaint();
			button.revalidate();

			return button;
		}

	}

	private class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JButton button;

		public ButtonEditor() {

			button = new JButton();
			button.setContentAreaFilled(false);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							stopCellEditing();
						}
					});
				}
			});
		}

		@Override
		public Object getCellEditorValue() {

			return null;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			button.setIcon(tableModel.getTempFlight(row).getIcon());
			button.repaint();
			button.revalidate();
			return button;
		}

	}

	/**
	 * @author cwhelan model for the table
	 */
	/**
	 * @author cwhelan
	 *
	 */
	public class FiltersTableModel extends DefaultTableModel implements Reorderable {
		/**
		 * 
		 */
		public Class<?> getColumnClass(int col) {
			if (col == SHOW_COLUMN) {
				return Boolean.class;
			}
			return super.getColumnClass(col);
		}

		public boolean isCellEditable(int row, int column) {
			if (column == ACIDS_COLUMN) {
				return false;
			}
			if (row == 0 && column != DEFINITION_COLUMN) {
				return false;
			}
			return super.isCellEditable(row, column);
		}

		/**
		 * Tells you if the Definition checkBox is checked or not
		 * 
		 * @param rowIndex
		 * @return boolean
		 */
		public boolean isDefinitionChecked(int rowIndex) {
			return tableModel.getTempFlight(rowIndex).isDefinition();
		}

		private static final long serialVersionUID = 1L;

		// override setValueAT just for the definition column, otherwise it
		// passed to super
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			if (columnIndex == DEFINITION_COLUMN) {
			} else
				super.setValueAt(aValue, rowIndex, columnIndex);
		}

		/**
		 * method for changing the value of the Bool in the definitionColumn
		 * 
		 * @param row
		 */
		public void changeBool(int row) {
			tableModel.getTempFlight(row).setDefinition(!tableModel.getTempFlight(row).isDefinition());
		}

		/**
		 * @param row
		 *            - row of the flight object
		 * @return the temp flight object - won't be saved if cancel is used
		 */
		public FlightFilter getTempFlight(int row) {
			FlightFilter f = (FlightFilter) ((Object[]) super.getValueAt(row, DEFINITION_COLUMN))[TEMP_INDEX];
			return f;
		}

		/**
		 * method for getting the flightFilter that will be saved to the list passed in
		 * @param row
		 * @return
		 */
		public FlightFilter getRealFlight(int row) {
			FlightFilter f = (FlightFilter) ((Object[]) super.getValueAt(row, DEFINITION_COLUMN))[FILTER_INDEX];
			return f;
		}
		
		/**
		 * method for setting the flightFilter that will be saved to the list
		 * @param row
		 * @param f
		 */
		public void setRealFlight(int row, FlightFilter f) {
			((Object[]) super.getValueAt(row, DEFINITION_COLUMN))[FILTER_INDEX] = f;

		}

		/**
		 * method for getting the boolean to tell wether or not the bool at the
		 * definitionColumn is checked
		 * 
		 * @param row
		 * @return
		 */
		public Boolean getBool(int row) {
			return tableModel.getTempFlight(row).isDefinition();

		}

		@Override
		public void reorder(int fromIndex, int toIndex) {
			if (toIndex < this.getRowCount() && fromIndex != 0 && toIndex != 0) {
				this.moveRow(fromIndex, fromIndex, toIndex);
			}
		}
	}

}
