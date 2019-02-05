package com.borman.myapp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class ComboBoxTyping extends JComboBox<Object> {

	private static final long serialVersionUID = 1L;
	private List<String> array;
	public static JTextField textfieldComboBox;
	private List<String> filterArray;

	public ComboBoxTyping(List<String> array) {

		super(array.toArray());

		this.array = array;
		this.setEditable(true);
		textfieldComboBox = (JTextField) this.getEditor().getEditorComponent();
		UpperCaseDocument ucd = new UpperCaseDocument();
		// Associates the editor with a text document.
		textfieldComboBox.setDocument(ucd);

		
	}

	public void inicializarFiltro() {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				comboFilter(textfieldComboBox.getText());
			}
		});

	}
	
	public void limpiarFiltro() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String valorPrevio = textfieldComboBox.getText();
				textfieldComboBox.setText("");
				comboFilter(textfieldComboBox.getText());
				textfieldComboBox.setText(valorPrevio);
			}
		});

	}


	public void comboFilter(String enteredText) {

		boolean pulsacion = Visualizacion.pulsar;
		filterArray = new ArrayList<String>();
		
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i).toUpperCase().contains(enteredText.toUpperCase())) {
				filterArray.add(array.get(i));
			}
		}
		if (filterArray.size() > 0 && pulsacion == false) {
			this.setModel(new DefaultComboBoxModel<Object>(filterArray.toArray()));
			this.setSelectedItem(enteredText);
			this.showPopup();
		} else {
			this.hidePopup();

		}
		Visualizacion.pulsar = false;
	}

	public static List<String> populateArray() {
		String query = "SELECT * FROM zonas";
		try (Connection connection = Conexion.Conectar();
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			while (rs.next()) {
				listaNombresProvincia.add(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaNombresProvincia;
	}

	public class UpperCaseDocument extends PlainDocument {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private boolean upperCase = true;

		public void setUpperCase(boolean flag) {
			upperCase = flag;
		}

		public void insertString(int offset, String str, AttributeSet attSet) throws BadLocationException {
			if (upperCase)
				str = str.toUpperCase();
			super.insertString(offset, str, attSet);
		}

	}

	private static ArrayList<String> listaNombresProvincia = new ArrayList<String>();
}