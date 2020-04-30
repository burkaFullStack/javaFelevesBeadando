package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

public class MovieModify extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private MovieTableModel tableModel;
	private ValidationUtil validation = new ValidationUtil();
	private DatabaseMethod databaseModel = new DatabaseMethod();
	private JTextField id;
	private JTextField cim;
	private JTextField mufaj;
	private JTextField kezdoIdopont;
	private JTextField terem;
	private JLabel lblId;
	private JLabel lblCim;
	private JLabel lblMufaj;
	private JLabel lblKezdoIdopont;
	private JLabel lblTerem;

	public int modifyDataCounter() {
		int counter = 0;
		if (validation.isFilled(id)) {
			counter++;
		}
		if (validation.isFilled(cim)) {
			counter++;
		}
		if (validation.isFilled(mufaj)) {
			counter++;
		}
		if (validation.isFilled(kezdoIdopont)) {
			counter++;
		}
		if (validation.isFilled(terem)) {
			counter++;
		}
		return counter;
	}

	public MovieModify(JFrame f, MovieTableModel tableModelParam) {
		super(f, "Mozifilmek módosítása", true);
		tableModel = tableModelParam;
		setBounds(100, 100, 530, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 494, 202);
		contentPanel.add(scrollPane);

		JButton btnBezar = new JButton("Bez\u00E1r");
		btnBezar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBezar.setBounds(415, 327, 89, 23);
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

		JButton btnModositas = new JButton("Rekord m\u00F3dos\u00EDt\u00E1sa");
		btnModositas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int counter = 0, index = 0;
				for (int i = 0; i < tableModel.getRowCount(); i++) {
					if ((Boolean) tableModel.getValueAt(i, 0)) {
						counter++;
						index = i;
					}
				}
				if (counter == 0) {
					validation.SystemMessage("Nincs kijelölve a módosítandó rekord.", 0);
				} else if (counter > 1) {
					validation.SystemMessage("Több rekord van kijelölve!\nEgyszerre csak egy rekord módosítható!", 0);
				} else if (counter == 1) {
					if (modifyDataCounter() > 0) {
						boolean previousValid = true;
						if (validation.isFilled(id)) {
							previousValid = validation.isValidInt(id, "Sorszám");
						}
						if (previousValid && validation.isFilled(terem)) {
							previousValid = validation.isValidInt(terem, "Teremszám");
						}
						if (previousValid && validation.isFilled(kezdoIdopont)) {
							previousValid = validation.isValidDate(kezdoIdopont, "Kezdõ idõpont");
						}
						if (previousValid) {
							String fieldId = tableModel.getValueAt(index, 1).toString();
							databaseModel.connect();
							if (validation.isFilled(cim)) {
								databaseModel.UpdateData(fieldId, "cim", validation.getJTextFieldValue(cim));
							}
							if (validation.isFilled(mufaj)) {
								databaseModel.UpdateData(fieldId, "mufaj", validation.getJTextFieldValue(mufaj));
							}
							if (validation.isFilled(kezdoIdopont)) {
								databaseModel.UpdateData(fieldId, "kezdo_idopont",
										validation.getJTextFieldValue(kezdoIdopont));
							}
							if (validation.isFilled(terem)) {
								databaseModel.UpdateData(fieldId, "terem", validation.getJTextFieldValue(terem));
							}
							if (validation.isFilled(id)) {
								databaseModel.UpdateData(fieldId, "sorszam", validation.getJTextFieldValue(id));
							}
							databaseModel.DisConnect();

							if (validation.isFilled(id)) {
								tableModel.setValueAt(validation.convertStringToInt(validation.getJTextFieldValue(id)),
										index, 1);
							}
							if (validation.isFilled(cim)) {
								tableModel.setValueAt(validation.getJTextFieldValue(cim), index, 2);
							}
							if (validation.isFilled(mufaj)) {
								tableModel.setValueAt(validation.getJTextFieldValue(mufaj), index, 3);
							}
							if (validation.isFilled(kezdoIdopont)) {
								tableModel.setValueAt(validation.getJTextFieldValue(kezdoIdopont), index, 4);
							}
							if (validation.isFilled(terem)) {
								tableModel.setValueAt(
										validation.convertStringToInt(validation.getJTextFieldValue(terem)), index, 5);
							}
							validation.SystemMessage("A rekod módosítva!", 1);
						} else {
							validation.SystemMessage("Nincs kitöltv egyetlen módosító mezõ sem!", 0);
						}
					}
				}
			}
		});
		btnModositas.setBounds(10, 327, 150, 23);
		contentPanel.add(btnModositas);

		id = new JTextField();
		id.setBounds(41, 254, 68, 20);
		contentPanel.add(id);
		id.setColumns(10);

		cim = new JTextField();
		cim.setBounds(119, 254, 129, 20);
		contentPanel.add(cim);
		cim.setColumns(10);

		mufaj = new JTextField();
		mufaj.setBounds(258, 254, 76, 20);
		contentPanel.add(mufaj);
		mufaj.setColumns(10);

		kezdoIdopont = new JTextField();
		kezdoIdopont.setBounds(344, 254, 82, 20);
		contentPanel.add(kezdoIdopont);
		kezdoIdopont.setColumns(10);

		terem = new JTextField();
		terem.setBounds(436, 254, 68, 20);
		contentPanel.add(terem);
		terem.setColumns(10);

		lblId = new JLabel("Sorsz\u00E1m");
		lblId.setBounds(41, 236, 68, 14);
		contentPanel.add(lblId);

		lblCim = new JLabel("C\u00EDm");
		lblCim.setBounds(119, 236, 129, 14);
		contentPanel.add(lblCim);

		lblMufaj = new JLabel("M\u0171faj");
		lblMufaj.setBounds(258, 236, 76, 14);
		contentPanel.add(lblMufaj);

		lblKezdoIdopont = new JLabel("Kezd\u0151 id\u0151pont");
		lblKezdoIdopont.setBounds(344, 236, 82, 14);
		contentPanel.add(lblKezdoIdopont);

		lblTerem = new JLabel("Teremsz\u00E1m");
		lblTerem.setBounds(436, 236, 68, 14);
		contentPanel.add(lblTerem);
	}
}
