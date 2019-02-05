package com.borman.myapp;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class TextFieldValidacion {

	// Texto obligatorio
	public static boolean isTextFieldNotEmpty(JTextField tf) {
		boolean b = false;
		if (tf.getText().length() != 0 || !tf.getText().isEmpty()) {
			b = true;
		}
		return b;

	}

	public static boolean isTextFieldNotEmpty(JTextField tf, JLabel lb, String errorMessage) {
		boolean b = true;
		String msg = null;
		tf.setBackground(Color.white);
		if (!isTextFieldNotEmpty(tf)) {
			b = false;
			msg = errorMessage;
			tf.setBackground(new Color(255, 230, 230));
		}
		lb.setText(msg);

		return b;
	}

	// Limpiar textfield
	public static void isTextFieldClear(JTextField tf, JLabel lb) {
		tf.setBackground(Color.white);
		lb.setText("");
	}

	// Solo Números
	public static boolean istextFieldTypeNumber2(JTextField tf) {

		if (tf.getText().matches("([0-9]+(\\.[0-9]?)?)+")) {
			return true;
		}
		return false;
	}

	public static boolean istextFieldTypeNumber(JTextField tf, JLabel lb) {
		boolean b;
		if (!istextFieldTypeNumber2(tf)) {
			tf.setBackground(new Color(255, 230, 230));
			lb.setText("Solo números");
			b = false;
		} else {
			isTextFieldClear(tf, lb);
			b = true;
		}
		return b;
	}

	// Máximo 4 caracteres
	public static boolean maxCaracteres(JTextField tf, JLabel lb, int caracteres) {
		double kg = 0;
		System.out.print("entra");
		boolean b = true;
		if (!tf.getText().equals("")) {
			kg = Double.parseDouble(tf.getText());
		}

		if (kg <= 0 || tf.getText().equals("")) {
			tf.setBackground(new Color(255, 230, 230));
			lb.setText("Campo vacio");
			b = false;
		} else if (tf.getText().length() > caracteres) {
			tf.setBackground(new Color(255, 230, 230));
			lb.setText("Máximo 4 digitos");
			b = false;
		} else {
			isTextFieldClear(tf, lb);
		}
		return b;
	}
}