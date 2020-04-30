package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainProgram extends JFrame {

	private JPanel contentPane;
	private DatabaseMethod databaseMethod = new DatabaseMethod();
	private MovieTableModel tableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainProgram frame = new MainProgram();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainProgram() {
		databaseMethod.Reg();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnLista = new JButton("List\u00E1z\u00E1s");
		btnLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				databaseMethod.connect();
				tableModel = databaseMethod.ReadData();
				MovieList list = new MovieList(MainProgram.this, tableModel);
				list.setVisible(true);
				databaseMethod.DisConnect();
			}
		});
		btnLista.setBounds(188, 38, 115, 38);
		contentPane.add(btnLista);

		JButton btnUjFilm = new JButton("\u00DAj");
		btnUjFilm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewMovie newMovie = new NewMovie();
				newMovie.setVisible(true);
			}
		});
		btnUjFilm.setBounds(188, 102, 115, 38);
		contentPane.add(btnUjFilm);

		JButton btnTorles = new JButton("T\u00F6rl\u00E9s");
		btnTorles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				databaseMethod.connect();
				tableModel = databaseMethod.ReadData();
				databaseMethod.DisConnect();
				MovieDelete delete = new MovieDelete(MainProgram.this, tableModel);
				delete.setVisible(true);
			}
		});
		btnTorles.setBounds(188, 165, 115, 35);
		contentPane.add(btnTorles);

		JButton btnModosit = new JButton("M\u00F3dos\u00EDt\u00E1s");
		btnModosit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				databaseMethod.connect();
				tableModel = databaseMethod.ReadData();
				databaseMethod.DisConnect();
				MovieModify modify = new MovieModify(MainProgram.this, tableModel);
				modify.setVisible(true);
			}

		});
		btnModosit.setBounds(188, 222, 115, 38);
		contentPane.add(btnModosit);

	}
}
