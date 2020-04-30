package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class NewMovie extends JDialog {

	private JTextField id;
	private JTextField cim;
	private JTextField mufaj;
	private JTextField kezdoIdopont;
	private JTextField terem;
	private DatabaseMethod databaseMethod = new DatabaseMethod();

	public NewMovie() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		JLabel lblid = new JLabel("Sorsz\u00E1m:");
		lblid.setBounds(10, 11, 89, 14);
		getContentPane().add(lblid);

		JLabel lblCim = new JLabel("C\u00EDm:");
		lblCim.setBounds(10, 44, 89, 14);
		getContentPane().add(lblCim);

		JLabel lblMufaj = new JLabel("M\u0171faj:");
		lblMufaj.setBounds(10, 75, 89, 14);
		getContentPane().add(lblMufaj);

		JLabel lblKezdoIdopont = new JLabel("Kezd\u0151 id\u0151pont:");
		lblKezdoIdopont.setBounds(10, 116, 89, 14);
		getContentPane().add(lblKezdoIdopont);

		JLabel lblTeremszam = new JLabel("Teremsz\u00E1m:");
		lblTeremszam.setBounds(10, 161, 89, 14);
		getContentPane().add(lblTeremszam);

		id = new JTextField();
		id.setBounds(134, 8, 128, 20);
		getContentPane().add(id);
		id.setColumns(10);

		cim = new JTextField();
		cim.setBounds(134, 41, 128, 20);
		getContentPane().add(cim);
		cim.setColumns(10);

		mufaj = new JTextField();
		mufaj.setBounds(134, 72, 128, 20);
		getContentPane().add(mufaj);
		mufaj.setColumns(10);

		kezdoIdopont = new JTextField();
		kezdoIdopont.setBounds(134, 113, 128, 20);
		getContentPane().add(kezdoIdopont);
		kezdoIdopont.setColumns(10);

		terem = new JTextField();
		terem.setBounds(134, 158, 128, 20);
		getContentPane().add(terem);
		terem.setColumns(10);

		JButton btnBeszuras = new JButton("Hozz\u00E1ad\u00E1s");
		btnBeszuras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ValidationUtil validation = new ValidationUtil();
				if (validation.isValidInt(id, "Sorszám")) {
					if (validation.isFilled(cim, "Cím")) {
						if (validation.isFilled(mufaj, "Mûfaj")) {
							if (validation.isValidDate(kezdoIdopont, "Kezdõ idõpont")) {
								if (validation.isValidInt(terem, "Teremszám")) {
									databaseMethod.connect();
									databaseMethod.InsertData(validation.getJTextFieldValue(id),
											validation.getJTextFieldValue(cim), validation.getJTextFieldValue(mufaj),
											validation.getJTextFieldValue(kezdoIdopont),
											validation.getJTextFieldValue(terem));
									databaseMethod.DisConnect();
								}
							}
						}
					}
				}
			}
		});
		btnBeszuras.setBounds(134, 227, 128, 23);
		getContentPane().add(btnBeszuras);
	}
}
