import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;


	
	public class DefinitionPane extends JPanel {

        private JButton button;
        private JCheckBox cb;
        private String state;

        public DefinitionPane() {
            setLayout(new GridBagLayout());
            button = new JButton("...");
            button.setActionCommand("...");
            cb = new JCheckBox("cb");
            cb.setActionCommand("cb");

            add(cb);
            add(button);

            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    state = e.getActionCommand();
                    System.out.println("State = " + state);
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
    }

    class DefinitionRenderer extends DefaultTableCellRenderer {

        private DefinitionPane definitionPane;

        public DefinitionRenderer() {
        	definitionPane = new DefinitionPane();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
            	definitionPane.setBackground(table.getSelectionBackground());
            } else {
                definitionPane.setBackground(table.getBackground());
            }
            return definitionPane;
        }
    }

    class DefinitionEditor extends AbstractCellEditor implements TableCellEditor {

        private DefinitionPane definitionPane;

        public DefinitionEditor() {
        	definitionPane = new DefinitionPane();
        	definitionPane.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                 /*   SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            stopCellEditing();
                           
                        } 
                    });
                }
            */}});
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
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
            	definitionPane.setBackground(table.getSelectionBackground());
            } else {
            	definitionPane.setBackground(table.getBackground());
            }
            return definitionPane;
        }

}

