package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ValidationUtil {

	public String getJTextFieldValue(JTextField textField) {
		return textField.getText();
	}

	public void SystemMessage(String message, int type) {
		JOptionPane.showMessageDialog(null, message, "Program üzenet", type);
	}

	public boolean isFilled(JTextField data, String dataField) {
		String dataString = getJTextFieldValue(data);
		if (dataString.length() > 0)
			return true;
		else {
			SystemMessage("Hiba: a(z) " + dataField + " mezõ üres", 0);
			return false;
		}
	}

	public boolean isValidInt(JTextField data, String dataField) {
		String dataString = getJTextFieldValue(data);
		boolean filled = isFilled(data, dataField);
		if (filled)
			try {
				Integer.parseInt(dataString);
			} catch (NumberFormatException e) {
				SystemMessage("A(z) " + dataField + " mezõben hibás számadat!", 0);
				filled = false;
			}
		return filled;
	}

	public boolean DateValidator(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		try {
			Date parseDate = dateFormat.parse(dateString);
			if (!dateFormat.format(parseDate).equals(dateString)) {
				return false;
			}
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public boolean isValidDate(JTextField data, String dataField) {
		String dataString = getJTextFieldValue(data);
		boolean filled = isFilled(data, dataField);
		if (filled && DateValidator(dataString))
			return true;
		else {
			SystemMessage("A(z) " + dataField + " Mezõben hibás a dátum", 0);
			return false;
		}
	}

	public boolean isFilled(JTextField data) {
		String dataString = getJTextFieldValue(data);
		return dataString.length() > 0;
	}

	public int convertStringToInt(String dataString) {
		int checkInt = -1;
		try {
			checkInt = Integer.valueOf(dataString);
		} catch (NumberFormatException e) {
			SystemMessage("strinToInt: " + e.getMessage(), 0);
		}
		return checkInt;
	}
}
