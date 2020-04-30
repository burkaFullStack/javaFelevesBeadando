package main;

import javax.swing.table.DefaultTableModel;

public class MovieTableModel extends DefaultTableModel {
	public MovieTableModel(Object fildName[], int rows) {
		super(fildName, rows);
	}

	public boolean isCellEditable(int row, int col) {
		return col == 0;
	}

	public Class<?> getColumnClass(int index) {
		if (index == 0) {
			return (Boolean.class);
		} else if (index == 1 || index == 5) {
			return (Integer.class);
		}
		return (String.class);
	}

}
