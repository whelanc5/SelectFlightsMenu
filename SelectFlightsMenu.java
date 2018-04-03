import java.util.ArrayList;
import java.util.EventObject;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragSource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;

public class SelectFlightsMenu extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	// indexes for elements in array for the definition column
	private final int FILTER_INDEX = 0;
	private final int BOOL_INDEX = 1;
	private final int TEMP_INDEX = 2;
	// indexes for columns in table
	private final int SHOW_COLUMN = 0;
	private final int NAME_COLUMN = 2;
	private final int DEST_COLUMN = 3;
	private final int DFIX_COLUMN = 4;
	private final int DEFINITION_COLUMN = 5;
	DefinitionModel tableModel = new DefinitionModel();

	private ArrayList<FlightFilter> filters;

	/**
	 * Class Constructor.
	 * 
	 * @param parent
	 *            - the parent JFrame
	 * @param filtersIn
	 *            - List of FlightFilters
	 */
	public SelectFlightsMenu(JFrame parent, ArrayList<FlightFilter> filtersIn) {
		super(parent, "Select Flights");
		filters = filtersIn;
		this.setPreferredSize(new Dimension(800, 250));
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
		try {
			table = getTable();
		} catch (ArrayIndexOutOfBoundsException e) {
			FlightFilter f = new FlightFilter();
			f.setName("PINNED");
			f.setPriority(0);
			filters.add(f);
			table = getTable();
		}

		table.setDragEnabled(true);
		table.setDropMode(DropMode.INSERT_ROWS);
		table.setTransferHandler(new TableRowTransferHandler(table));
		JScrollPane pane = new JScrollPane(table);
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

		flightTable.setFillsViewportHeight(true);
		DefinitionModel model = new DefinitionModel();

		flightTable.setModel(model);
		model.addColumn("Show");
		model.addColumn("");
		model.addColumn("Name");
		model.addColumn("DEST");
		model.addColumn("DFIX");
		model.addColumn("Definition");

		flightTable.getColumnModel().getColumn(SHOW_COLUMN).setMaxWidth(50);
		flightTable.getColumnModel().getColumn(1).setMaxWidth(50);
		flightTable.getColumnModel().getColumn(NAME_COLUMN).setPreferredWidth(100);
		flightTable.getColumnModel().getColumn(DEST_COLUMN).setPreferredWidth(100);
		flightTable.getColumnModel().getColumn(DFIX_COLUMN).setPreferredWidth(100);
		flightTable.getColumnModel().getColumn(DEFINITION_COLUMN).setPreferredWidth(100);

		for (FlightFilter f : filters) {
			model.addRow(new Object[] { f.isShow(), "", f.getName(), f.getDest(), f.getdFix(), new Object[] { f,
					f.isDefinition(), new FlightFilter(f.getIcon().getColor(), f.getIcon().getShape()) } });
		}
		TableColumn definition = flightTable.getColumnModel().getColumn(DEFINITION_COLUMN);
		DefinitionRenderer renderer = new DefinitionRenderer();
		DefinitionEditor editor = new DefinitionEditor();
		definition.setCellRenderer(renderer);
		definition.setCellEditor(editor);
		flightTable.setRowHeight(
				renderer.getTableCellRendererComponent(flightTable, null, true, true, 0, 0).getPreferredSize().height);
		JFrame frame = (JFrame) this.getParent();

		editor.addCellEditorListener(new CellEditorListener() {
			@Override
			public void editingStopped(ChangeEvent e) {
				String s = (String) ((DefinitionEditor) e.getSource()).getCellEditorValue();
				if (s.equals("...")) {
					try {
						new DesignIconDialog(frame, model.getFlight(flightTable.getSelectedRow()));
					} catch (RuntimeException b) {
						System.out.println(b);
					}

				} else if (s.equals("cb")) {
					DefinitionModel fl = (DefinitionModel) flightTable.getModel();
					fl.changeBool(flightTable.getSelectedRow());
				}

			}

			@Override
			public void editingCanceled(ChangeEvent e) {
				String s = (String) ((DefinitionEditor) e.getSource()).getCellEditorValue();
				if (s.equals("...")) {
					try {
						new DesignIconDialog(frame, model.getFlight(flightTable.getSelectedRow()));
					} catch (RuntimeException b) {
						System.out.println(b);
					}

				} else if (s.equals("cb")) {
					DefinitionModel fl = (DefinitionModel) flightTable.getModel();
					fl.changeBool(flightTable.getSelectedRow());
				}

			}

		});

		tableModel = model;

		return flightTable;

	}

	/**
	 * method for adding FlightFilters to the table
	 * 
	 * @param f
	 *            - FlightFilter to be added
	 */
	private void addEntry(FlightFilter f) {

		tableModel.addRow(new Object[] { false, "", "", "", "",
				new Object[] { f, false, new FlightFilter(f.getIcon().getColor(), f.getIcon().getShape()) } });
		filters.add(f);
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
				addEntry(new FlightFilter());
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
			for (FlightFilter f : filters) {
				for (int row = 0; row < tableModel.getRowCount(); row++) {
					if (f == ((Object[]) tableModel.getValueAt(row, DEFINITION_COLUMN))[FILTER_INDEX]) {
						f.setShow((boolean) tableModel.getValueAt(row, SHOW_COLUMN));
						f.setName((String) tableModel.getValueAt(row, NAME_COLUMN));
						f.setDest((String) tableModel.getValueAt(row, DEST_COLUMN));
						f.setdFix((String) tableModel.getValueAt(row, DFIX_COLUMN));
						f.setDefinition(
								(boolean) ((Object[]) tableModel.getValueAt(row, DEFINITION_COLUMN))[BOOL_INDEX]);
						f.getIcon().setShape(
								((FlightFilter) ((Object[]) tableModel.getValueAt(row, DEFINITION_COLUMN))[TEMP_INDEX])
										.getIcon().getShape());
						f.getIcon().changeColor(
								((FlightFilter) ((Object[]) tableModel.getValueAt(row, DEFINITION_COLUMN))[TEMP_INDEX])
										.getIcon().getColor());
						if (f.getName() == "PINNED") {
							f.setPriority(0);
						} else 
						{
							f.setPriority(row + 1);
						}
					}
				}
			}
			if (e.getActionCommand().equals("OK")) {
				filters.sort(null);
				dispose();
			}
		} else if (e.getActionCommand().equals("Cancel")) {
			dispose();
		}

	}

	/** 
	 * method used to change value of checkbox within the DefinitionPane
	 * @param checkBox 
	 * @param value - value checkbox should be changed to
	 */
	private static void setCheckboxValue(JCheckBox checkBox, Object value) {
		if (value instanceof Boolean) {
			checkBox.setSelected(((Boolean) value).booleanValue());
		} else if (value instanceof String) {
			checkBox.setSelected(value.equals("true"));
		}
	}

	/**
	 * method to maintain the Appearance of the DefinitionRenderer and DefinitionEditor
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
	 * @author cwhelan
	 *	inner class used to embed a button and checkbox into the same JTable
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
	}

	/**
	 * @author cwhelan
	 *	Renderer to display checkbox and Button in same Table column
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
			setCheckboxValue(definitionPane.getCheckBox(),
					((DefinitionModel) table.getModel()).isDefinitionChecked(row, column));
			copyAppearanceFrom(definitionPane, this);
			return definitionPane;
		}

	}
	
	/**
	 * @author cwhelan
	 *	Editor to control checkbox and Button in same Table column
	 */
	private class DefinitionEditor extends AbstractCellEditor implements TableCellEditor {

		/**
		 * 
		 */

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
			setCheckboxValue(definitionPane.getCheckBox(), table.getValueAt(row, column));
			TableCellRenderer renderer = table.getCellRenderer(row, column);
			Component c = renderer.getTableCellRendererComponent(table, value, true, true, row, column);
			copyAppearanceFrom(definitionPane, c);
			definitionPane.setState("nil");
			return definitionPane;
		}

	}
	
	/**
	 * @author cwhelan
	 *	model of the Definition column
	 */
	public class DefinitionModel extends DefaultTableModel implements Reorderable {
		/**
		 * 
		 */
		public Class<?> getColumnClass(int col) {

			if (col == 0) {
				return Boolean.class;
			}

			return super.getColumnClass(col);

		}

		public boolean isDefinitionChecked(int rowIndex, int columnIndex) {
			return (boolean) ((Object[]) super.getValueAt(rowIndex, DEFINITION_COLUMN))[BOOL_INDEX];
		}

		private static final long serialVersionUID = 1L;

		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			if (columnIndex == DEFINITION_COLUMN) {
			} else
				super.setValueAt(aValue, rowIndex, columnIndex);
		}

		public void changeBool(int row) {

			((Object[]) super.getValueAt(row, DEFINITION_COLUMN))[1] = !((boolean) ((Object[]) super.getValueAt(row,
					DEFINITION_COLUMN))[BOOL_INDEX]);
		}

		public FlightFilter getFlight(int row) {
			FlightFilter f = (FlightFilter) ((Object[]) super.getValueAt(row, DEFINITION_COLUMN))[2];
			return f;
		}

		@Override
		public void reorder(int fromIndex, int toIndex) {
			if (toIndex < this.getRowCount()) {
				this.moveRow(fromIndex, fromIndex, toIndex);
			}
		}
	}

	public class TableRowTransferHandler extends TransferHandler {
		/**
		* 
		*/
		private static final long serialVersionUID = 1L;
		private final DataFlavor localObjectFlavor = new ActivationDataFlavor(Integer.class,
				"application/x-java-Integer;class=java.lang.Integer", "Integer Row Index");
		private JTable table = null;

		public TableRowTransferHandler(JTable table) {
			this.table = table;
		}

		@Override
		protected Transferable createTransferable(JComponent c) {
			assert (c == table);
			return new DataHandler(new Integer(table.getSelectedRow()), localObjectFlavor.getMimeType());
		}

		@Override
		public boolean canImport(TransferHandler.TransferSupport info) {
			boolean b = info.getComponent() == table && info.isDrop() && info.isDataFlavorSupported(localObjectFlavor);
			table.setCursor(b ? DragSource.DefaultMoveDrop : DragSource.DefaultMoveNoDrop);
			return b;
		}

		@Override
		public int getSourceActions(JComponent c) {
			return TransferHandler.COPY_OR_MOVE;
		}

		@Override
		public boolean importData(TransferHandler.TransferSupport info) {
			JTable target = (JTable) info.getComponent();
			JTable.DropLocation dl = (JTable.DropLocation) info.getDropLocation();
			int index = dl.getRow();
			int max = table.getModel().getRowCount();
			if (index < 0 || index > max)
				index = max;
			target.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			try {
				Integer rowFrom = (Integer) info.getTransferable().getTransferData(localObjectFlavor);
				if (rowFrom != -1 && rowFrom != index) {
					((Reorderable) table.getModel()).reorder(rowFrom, index);
					if (index > rowFrom)
						index--;
					target.getSelectionModel().addSelectionInterval(index, index);
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void exportDone(JComponent c, Transferable t, int act) {
			if ((act == TransferHandler.MOVE) || (act == TransferHandler.NONE)) {
				table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}

	}

	
}
