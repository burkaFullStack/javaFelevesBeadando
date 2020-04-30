package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

public class MovieDelete extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private MovieTableModel tableModel;
	private ValidationUtil validation = new ValidationUtil();
	private DatabaseMethod databaseMethod = new DatabaseMethod();

	public MovieDelete(JFrame f, MovieTableModel tableModelParam) {
		super(f, "Mozifilmek törlése", true);
		tableModel = tableModelParam;
		setBounds(100, 100, 530, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 494, 255);
		contentPanel.add(scrollPane);

		JButton btnBezar = new JButton("Bez\u00E1r");
		btnBezar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBezar.setBounds(415, 277, 89, 23);
		contentPanel.add(btnBezar);

		table = new JTable(tableModel);
		scrollPane.setViewportView(table);

		TableColumn column = null;
		for (int i = 0; i < 6; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0) {
				column.setPreferredWidth(40);
			} else if (i == 1 || i == 5) {
				column.setPreferredWidth(70);
			} else if (i == 2) {
				column.setPreferredWidth(150);
			} else {
				column.setPreferredWidth(80);
			}
		}
		table.setAutoCreateRowSorter(true);
		TableRowSorter<MovieTableModel> rowSorter = (TableRowSorter<MovieTableModel>) table.getRowSorter();
		rowSorter.setSortable(0, false);

		JButton btnKijeloltTorlese = new JButton("Rekord t\u00F6rl\u00E9se");
		btnKijeloltTorlese.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int quantity = 0, index = 0;
				for (int i = 0; i < tableModel.getRowCount(); i++) {
					if ((Boolean) tableModel.getValueAt(i, 0)) {
						quantity++;
						index = i;
					}
				}
				if (quantity == 0) {
					validation.SystemMessage("Nincs kijelölve a törlendõ rekord.", 0);
				} else if (quantity > 1) {
					validation.SystemMessage("Több rekord van kijelölve!\nEgyszerre csak egy rekord törölhetõ!", 0);
				} else if (quantity == 1) {
					String id = tableModel.getValueAt(index, 1).toString();
					databaseMethod.connect();
					databaseMethod.DeleteData(id);
					databaseMethod.DisConnect();
					tableModel.removeRow(index);
					validation.SystemMessage("A rekord törölve!", 1);
				}
			}
		});
		btnKijeloltTorlese.setBounds(10, 277, 129, 23);
		contentPanel.add(btnKijeloltTorlese);
	}
}
