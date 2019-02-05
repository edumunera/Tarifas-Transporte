package com.borman.myapp;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Visualizacion {

	public JFrame frmTarifasTransporteBorman;
	private JTextField jTextField_kilos;
	private JTextField jTextField_dhl_zonas;
	private JTextField jTextField_gefco_zonas;
	private JTextField jTextField_gls_zonas;
	private JTextField jTextField_halcourier_zonas;
	private JTextField jTextField_nacex_zonas;
	private JTextField jTextField_redur_zonas;
	private JTextField jTextField_seur_zonas;
	private JTextField jTextField_tnt_zonas;
	private JTextField jTextField_transaher_zonas;
	private JTextField jTextField_txt_zonas;
	private JTextField jTextField_dhl_kilos;
	private JTextField jTextField_txt_kilos;
	private JTextField jTextField_gefco_kilos;
	private JTextField jTextField_gls_kilos;
	private JTextField jTextField_halcourier_kilos;
	private JTextField jTextField_nacex_kilos;
	private JTextField jTextField_redur_kilos;
	private JTextField jTextField_seur_kilos;
	private JTextField jTextField_tnt_kilos;
	private JTextField jTextField_transaher_kilos;
	private JTextField jTextField_dhl_precio;
	private JTextField jTextField_txt_precio;
	private JTextField jTextField_gefco_precio;
	private JTextField jTextField_gls_precio;
	private JTextField jTextField_halcourier_precio;
	private JTextField jTextField_nacex_precio;
	private JTextField jTextField_redur_precio;
	private JTextField jTextField_seur_precio;
	private JTextField jTextField_tnt_precio;
	private JTextField jTextField_transaher_precio;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Visualizacion window = new Visualizacion();
					window.frmTarifasTransporteBorman.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Visualizacion() {
		initialize();

		jComboBoxProvincias.inicializarFiltro();
	}

	private void initialize() {

		frmTarifasTransporteBorman = new JFrame();
		frmTarifasTransporteBorman.getContentPane().setFocusTraversalKeysEnabled(false);

		frmTarifasTransporteBorman.setResizable(false);
		frmTarifasTransporteBorman.setTitle("Tarifas transporte Borman Industria Textil SI");
		frmTarifasTransporteBorman.getContentPane().setFont(new Font("Garamond", Font.PLAIN, 12));
		frmTarifasTransporteBorman.setBounds(100, 100, 350, 275);
		frmTarifasTransporteBorman.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTarifasTransporteBorman.getContentPane().setLayout(null);

		final List<String> a = ComboBoxTyping.populateArray();
		jComboBoxProvincias = new ComboBoxTyping(a);
		jComboBoxProvincias.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				limpiar();
			}
		});

		ComboBoxTyping.textfieldComboBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_TAB) {
					pulsar = true;
					jTextField_kilos.grabFocus();
				}
			}
		});

		ComboBoxTyping.textfieldComboBox.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {

				if (ke.getKeyCode() == KeyEvent.VK_DOWN || ke.getKeyCode() == KeyEvent.VK_UP
						|| ke.getKeyCode() == KeyEvent.VK_LEFT || ke.getKeyCode() == KeyEvent.VK_RIGHT) {
					return;
				}
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						jComboBoxProvincias.comboFilter(ComboBoxTyping.textfieldComboBox.getText());
					}
				});
			}
		});

		ComboBoxTyping.textfieldComboBox.setFocusTraversalKeysEnabled(false);
		ComboBoxTyping.textfieldComboBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_DELETE) {
					jComboBoxProvincias.limpiarFiltro();
					limpiar();
				}
			}
		});

		ComboBoxTyping.textfieldComboBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					pulsar = true;
					String provincia = ComboBoxTyping.textfieldComboBox.getText();
					jComboBoxProvincias.setSelectedItem(provincia);
					return;
				}
			}
		});

		jComboBoxProvincias.setBounds(38, 36, 165, 20);
		frmTarifasTransporteBorman.getContentPane().add(jComboBoxProvincias);

		jTextField_kilos = new JTextField();
		jTextField_kilos.setFocusTraversalKeysEnabled(false);
		jTextField_kilos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {

				if (evt.getKeyCode() == KeyEvent.VK_TAB) {
					ComboBoxTyping.textfieldComboBox.grabFocus();

				}

				provinciaSeleccionada = String.valueOf(jComboBoxProvincias.getSelectedItem());

				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!com.borman.myapp.TextFieldValidacion.maxCaracteres(jTextField_kilos, lblComprobar, 4)) {
						return;
					}
					if (!com.borman.myapp.TextFieldValidacion.istextFieldTypeNumber(jTextField_kilos, lblComprobar)) {
						return;
					}

					if (jComboBoxProvincias.getSelectedIndex() < 0) {
						JOptionPane.showMessageDialog(null, "Provincia desconocida", "Error",
								JOptionPane.ERROR_MESSAGE);
						jComboBoxProvincias.grabFocus();
						return;
					}

					llenar_campos();

					jTextField_dhl_zonas.setText(dhl);
					jTextField_gefco_zonas.setText(gefco);
					jTextField_gls_zonas.setText(gls);
					jTextField_halcourier_zonas.setText(halcourier);
					jTextField_nacex_zonas.setText(nacex);
					jTextField_redur_zonas.setText(redur);
					jTextField_seur_zonas.setText(seur);
					jTextField_tnt_zonas.setText(tnt);
					jTextField_transaher_zonas.setText(transaher);
					jTextField_txt_zonas.setText(txt);

					jTextField_dhl_kilos.setText(jTextField_kilos.getText());
					jTextField_gefco_kilos.setText(jTextField_kilos.getText());
					jTextField_gls_kilos.setText(jTextField_kilos.getText());
					jTextField_halcourier_kilos.setText(jTextField_kilos.getText());
					jTextField_nacex_kilos.setText(jTextField_kilos.getText());
					jTextField_redur_kilos.setText(jTextField_kilos.getText());
					jTextField_seur_kilos.setText(jTextField_kilos.getText());
					jTextField_tnt_kilos.setText(jTextField_kilos.getText());
					jTextField_transaher_kilos.setText(jTextField_kilos.getText());
					jTextField_txt_kilos.setText(jTextField_kilos.getText());

					double precioDevuelto;
					String tabla;

					/// PRIMER PASO
					if (!dhl.equals("-1")) {
						tabla = "dhl";
						precioDevuelto = cargarDatos(dhl, tabla);
						datos(precioDevuelto, tabla);
					} else {
						limpiarCampos("dhl");
					}
					if (!gefco.equals("-1")) {
						tabla = "gefco";
						precioDevuelto = cargarDatos(gefco, tabla);
						datos(precioDevuelto, tabla);
					} else {
						limpiarCampos("gefco");
					}
					if (!gls.equals("-1")) {
						tabla = "gls";
						precioDevuelto = cargarDatos(gls, tabla);
						datos(precioDevuelto, tabla);
					} else {
						limpiarCampos("gls");
					}
					if (!halcourier.equals("-1")) {
						tabla = "halcourier";
						precioDevuelto = cargarDatos(halcourier, tabla);
						datos(precioDevuelto, tabla);
					} else {
						limpiarCampos("halcourier");
					}
					if (!nacex.equals("-1")) {
						tabla = "nacex";
						precioDevuelto = cargarDatos(nacex, tabla);
						datos(precioDevuelto, tabla);
					} else {
						limpiarCampos("nacex");
					}
					if (!redur.equals("-1")) {
						tabla = "redur";
						precioDevuelto = cargarDatos(redur, tabla);
						datos(precioDevuelto, tabla);
					} else {
						limpiarCampos("redur");
					}
					if (!seur.equals("-1")) {
						tabla = "seur";
						precioDevuelto = cargarDatos(seur, tabla);
						datos(precioDevuelto, tabla);

					} else {
						limpiarCampos("seur");
					}
					if (!tnt.equals("-1")) {
						tabla = "tnt";
						precioDevuelto = cargarDatos(tnt, tabla);
						datos(precioDevuelto, tabla);
					} else {
						limpiarCampos("tnt");
					}
					if (!transaher.equals("-1")) {
						tabla = "transaher";
						precioDevuelto = cargarDatos(transaher, tabla);
						datos(precioDevuelto, tabla);
					} else {
						limpiarCampos("transaher");
					}
					if (!txt.equals("-1")) {
						tabla = "txt";
						precioDevuelto = cargarDatos(txt, tabla);
						datos(precioDevuelto, tabla);
					} else {
						limpiarCampos("txt");
					}

				}
			}
		});

		jTextField_kilos.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE || evt.getKeyCode() == KeyEvent.VK_DELETE) {
					String guardoKilos = jTextField_kilos.getText();
					limpiar();
					jTextField_kilos.setText(guardoKilos);
				}
			}
		});

		jTextField_kilos.setBounds(213, 36, 45, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_kilos);

		JLabel lbl_zonas = new JLabel("ZONA");
		lbl_zonas.setFont(new Font("Microsoft Tai Le", Font.BOLD, 11));
		lbl_zonas.setBounds(38, 115, 46, 14);
		frmTarifasTransporteBorman.getContentPane().add(lbl_zonas);

		JLabel lbl_kilos = new JLabel("KG");
		lbl_kilos.setFont(new Font("Microsoft Tai Le", Font.BOLD, 11));
		lbl_kilos.setBounds(38, 147, 46, 14);
		frmTarifasTransporteBorman.getContentPane().add(lbl_kilos);

		JLabel lbl_Precio = new JLabel("PVP");
		lbl_Precio.setFont(new Font("Microsoft Tai Le", Font.BOLD, 11));
		lbl_Precio.setBounds(38, 178, 46, 14);
		frmTarifasTransporteBorman.getContentPane().add(lbl_Precio);

		jTextField_dhl_zonas = new JTextField();
		jTextField_dhl_zonas.setFont(new Font("Dialog", Font.PLAIN, 11));
		jTextField_dhl_zonas.setFocusTraversalKeysEnabled(false);
		jTextField_dhl_zonas.setBounds(77, 113, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_dhl_zonas);
		jTextField_dhl_zonas.setColumns(10);
		jTextField_dhl_zonas.setEditable(false);

		jTextField_gefco_zonas = new JTextField();
		jTextField_gefco_zonas.setVisible(false);
		jTextField_gefco_zonas.setFocusable(false);
		jTextField_gefco_zonas.setEnabled(false);
		jTextField_gefco_zonas.setFocusTraversalKeysEnabled(false);
		jTextField_gefco_zonas.setColumns(10);
		jTextField_gefco_zonas.setBounds(759, 92, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_gefco_zonas);
		jTextField_gefco_zonas.setEditable(false);

		jTextField_gls_zonas = new JTextField();
		jTextField_gls_zonas.setVisible(false);
		jTextField_gls_zonas.setFocusable(false);
		jTextField_gls_zonas.setEnabled(false);
		jTextField_gls_zonas.setFocusTraversalKeysEnabled(false);
		jTextField_gls_zonas.setColumns(10);
		jTextField_gls_zonas.setBounds(828, 92, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_gls_zonas);
		jTextField_gls_zonas.setEditable(false);

		jTextField_halcourier_zonas = new JTextField();
		jTextField_halcourier_zonas.setVisible(false);
		jTextField_halcourier_zonas.setFocusable(false);
		jTextField_halcourier_zonas.setEnabled(false);
		jTextField_halcourier_zonas.setFocusTraversalKeysEnabled(false);
		jTextField_halcourier_zonas.setColumns(10);
		jTextField_halcourier_zonas.setBounds(898, 92, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_halcourier_zonas);
		jTextField_halcourier_zonas.setEditable(false);

		jTextField_nacex_zonas = new JTextField();
		jTextField_nacex_zonas.setFont(new Font("Dialog", Font.PLAIN, 11));
		jTextField_nacex_zonas.setFocusTraversalKeysEnabled(false);
		jTextField_nacex_zonas.setColumns(10);
		jTextField_nacex_zonas.setBounds(147, 113, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_nacex_zonas);
		jTextField_nacex_zonas.setEditable(false);

		jTextField_redur_zonas = new JTextField();
		jTextField_redur_zonas.setVisible(false);
		jTextField_redur_zonas.setFocusable(false);
		jTextField_redur_zonas.setEnabled(false);
		jTextField_redur_zonas.setFocusTraversalKeysEnabled(false);
		jTextField_redur_zonas.setColumns(10);
		jTextField_redur_zonas.setBounds(1038, 92, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_redur_zonas);
		jTextField_redur_zonas.setEditable(false);

		jTextField_seur_zonas = new JTextField();
		jTextField_seur_zonas.setFont(new Font("Dialog", Font.PLAIN, 11));
		jTextField_seur_zonas.setFocusTraversalKeysEnabled(false);
		jTextField_seur_zonas.setColumns(10);
		jTextField_seur_zonas.setBounds(217, 113, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_seur_zonas);
		jTextField_seur_zonas.setEditable(false);

		jTextField_tnt_zonas = new JTextField();
		jTextField_tnt_zonas.setVisible(false);
		jTextField_tnt_zonas.setFocusable(false);
		jTextField_tnt_zonas.setEnabled(false);
		jTextField_tnt_zonas.setFocusTraversalKeysEnabled(false);
		jTextField_tnt_zonas.setColumns(10);
		jTextField_tnt_zonas.setBounds(1176, 92, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_tnt_zonas);
		jTextField_tnt_zonas.setEditable(false);

		jTextField_transaher_zonas = new JTextField();
		jTextField_transaher_zonas.setVisible(false);
		jTextField_transaher_zonas.setFocusable(false);
		jTextField_transaher_zonas.setEnabled(false);
		jTextField_transaher_zonas.setFocusTraversalKeysEnabled(false);
		jTextField_transaher_zonas.setColumns(10);
		jTextField_transaher_zonas.setBounds(1246, 92, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_transaher_zonas);
		jTextField_transaher_zonas.setEditable(false);

		jTextField_txt_zonas = new JTextField();
		jTextField_txt_zonas.setVisible(false);
		jTextField_txt_zonas.setFocusable(false);
		jTextField_txt_zonas.setEnabled(false);
		jTextField_txt_zonas.setFocusTraversalKeysEnabled(false);
		jTextField_txt_zonas.setColumns(10);
		jTextField_txt_zonas.setBounds(1316, 92, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_txt_zonas);
		jTextField_txt_zonas.setEditable(false);

		jTextField_dhl_kilos = new JTextField();
		jTextField_dhl_kilos.setFont(new Font("Dialog", Font.PLAIN, 11));
		jTextField_dhl_kilos.setFocusTraversalKeysEnabled(false);
		jTextField_dhl_kilos.setColumns(10);
		jTextField_dhl_kilos.setBounds(77, 144, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_dhl_kilos);
		jTextField_dhl_kilos.setEditable(false);

		jTextField_txt_kilos = new JTextField();
		jTextField_txt_kilos.setVisible(false);
		jTextField_txt_kilos.setFocusable(false);
		jTextField_txt_kilos.setEnabled(false);
		jTextField_txt_kilos.setFocusTraversalKeysEnabled(false);
		jTextField_txt_kilos.setColumns(10);
		jTextField_txt_kilos.setBounds(1316, 123, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_txt_kilos);
		jTextField_txt_kilos.setEditable(false);

		jTextField_gefco_kilos = new JTextField();
		jTextField_gefco_kilos.setVisible(false);
		jTextField_gefco_kilos.setFocusable(false);
		jTextField_gefco_kilos.setEnabled(false);
		jTextField_gefco_kilos.setFocusTraversalKeysEnabled(false);
		jTextField_gefco_kilos.setColumns(10);
		jTextField_gefco_kilos.setBounds(759, 123, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_gefco_kilos);
		jTextField_gefco_kilos.setEditable(false);

		jTextField_gls_kilos = new JTextField();
		jTextField_gls_kilos.setVisible(false);
		jTextField_gls_kilos.setFocusable(false);
		jTextField_gls_kilos.setEnabled(false);
		jTextField_gls_kilos.setFocusTraversalKeysEnabled(false);
		jTextField_gls_kilos.setColumns(10);
		jTextField_gls_kilos.setBounds(828, 123, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_gls_kilos);
		jTextField_gls_kilos.setEditable(false);

		jTextField_halcourier_kilos = new JTextField();
		jTextField_halcourier_kilos.setVisible(false);
		jTextField_halcourier_kilos.setFocusable(false);
		jTextField_halcourier_kilos.setEnabled(false);
		jTextField_halcourier_kilos.setFocusTraversalKeysEnabled(false);
		jTextField_halcourier_kilos.setColumns(10);
		jTextField_halcourier_kilos.setBounds(898, 123, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_halcourier_kilos);
		jTextField_halcourier_kilos.setEditable(false);
		;

		jTextField_nacex_kilos = new JTextField();
		jTextField_nacex_kilos.setFont(new Font("Dialog", Font.PLAIN, 11));
		jTextField_nacex_kilos.setFocusTraversalKeysEnabled(false);
		jTextField_nacex_kilos.setColumns(10);
		jTextField_nacex_kilos.setBounds(147, 144, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_nacex_kilos);
		jTextField_nacex_kilos.setEditable(false);

		jTextField_redur_kilos = new JTextField();
		jTextField_redur_kilos.setVisible(false);
		jTextField_redur_kilos.setFocusable(false);
		jTextField_redur_kilos.setEnabled(false);
		jTextField_redur_kilos.setFocusTraversalKeysEnabled(false);
		jTextField_redur_kilos.setColumns(10);
		jTextField_redur_kilos.setBounds(1038, 123, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_redur_kilos);
		jTextField_redur_kilos.setEditable(false);

		jTextField_seur_kilos = new JTextField();
		jTextField_seur_kilos.setFont(new Font("Dialog", Font.PLAIN, 11));
		jTextField_seur_kilos.setFocusTraversalKeysEnabled(false);
		jTextField_seur_kilos.setColumns(10);
		jTextField_seur_kilos.setBounds(217, 144, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_seur_kilos);
		jTextField_seur_kilos.setEditable(false);

		jTextField_tnt_kilos = new JTextField();
		jTextField_tnt_kilos.setVisible(false);
		jTextField_tnt_kilos.setFocusable(false);
		jTextField_tnt_kilos.setEnabled(false);
		jTextField_tnt_kilos.setFocusTraversalKeysEnabled(false);
		jTextField_tnt_kilos.setColumns(10);
		jTextField_tnt_kilos.setBounds(1176, 123, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_tnt_kilos);
		jTextField_tnt_kilos.setEditable(false);

		jTextField_transaher_kilos = new JTextField();
		jTextField_transaher_kilos.setVisible(false);
		jTextField_transaher_kilos.setFocusable(false);
		jTextField_transaher_kilos.setEnabled(false);
		jTextField_transaher_kilos.setFocusTraversalKeysEnabled(false);
		jTextField_transaher_kilos.setColumns(10);
		jTextField_transaher_kilos.setBounds(1246, 123, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_transaher_kilos);
		jTextField_transaher_kilos.setEditable(false);

		jTextField_dhl_precio = new JTextField();
		jTextField_dhl_precio.setFont(new Font("Dialog", Font.PLAIN, 11));
		jTextField_dhl_precio.setFocusTraversalKeysEnabled(false);
		jTextField_dhl_precio.setColumns(10);
		jTextField_dhl_precio.setBounds(77, 175, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_dhl_precio);
		jTextField_dhl_precio.setEditable(false);

		jTextField_txt_precio = new JTextField();
		jTextField_txt_precio.setVisible(false);
		jTextField_txt_precio.setFocusable(false);
		jTextField_txt_precio.setEnabled(false);
		jTextField_txt_precio.setFocusTraversalKeysEnabled(false);
		jTextField_txt_precio.setColumns(10);
		jTextField_txt_precio.setBounds(1316, 154, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_txt_precio);
		jTextField_txt_precio.setEditable(false);

		jTextField_gefco_precio = new JTextField();
		jTextField_gefco_precio.setVisible(false);
		jTextField_gefco_precio.setFocusable(false);
		jTextField_gefco_precio.setEnabled(false);
		jTextField_gefco_precio.setFocusTraversalKeysEnabled(false);
		jTextField_gefco_precio.setColumns(10);
		jTextField_gefco_precio.setBounds(759, 154, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_gefco_precio);
		jTextField_gefco_precio.setEditable(false);

		jTextField_gls_precio = new JTextField();
		jTextField_gls_precio.setVisible(false);
		jTextField_gls_precio.setFocusable(false);
		jTextField_gls_precio.setEnabled(false);
		jTextField_gls_precio.setFocusTraversalKeysEnabled(false);
		jTextField_gls_precio.setColumns(10);
		jTextField_gls_precio.setBounds(828, 154, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_gls_precio);
		jTextField_gls_precio.setEditable(false);

		jTextField_halcourier_precio = new JTextField();
		jTextField_halcourier_precio.setVisible(false);
		jTextField_halcourier_precio.setFocusable(false);
		jTextField_halcourier_precio.setEnabled(false);
		jTextField_halcourier_precio.setFocusTraversalKeysEnabled(false);
		jTextField_halcourier_precio.setColumns(10);
		jTextField_halcourier_precio.setBounds(898, 154, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_halcourier_precio);
		jTextField_halcourier_precio.setEditable(false);

		jTextField_nacex_precio = new JTextField();
		jTextField_nacex_precio.setFont(new Font("Dialog", Font.PLAIN, 11));
		jTextField_nacex_precio.setFocusTraversalKeysEnabled(false);
		jTextField_nacex_precio.setColumns(10);
		jTextField_nacex_precio.setBounds(147, 175, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_nacex_precio);
		jTextField_nacex_precio.setEditable(false);

		jTextField_redur_precio = new JTextField();
		jTextField_redur_precio.setVisible(false);
		jTextField_redur_precio.setFocusable(false);
		jTextField_redur_precio.setEnabled(false);
		jTextField_redur_precio.setFocusTraversalKeysEnabled(false);
		jTextField_redur_precio.setColumns(10);
		jTextField_redur_precio.setBounds(1038, 154, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_redur_precio);
		jTextField_redur_precio.setEditable(false);

		jTextField_seur_precio = new JTextField();
		jTextField_seur_precio.setFont(new Font("Dialog", Font.PLAIN, 11));
		jTextField_seur_precio.setFocusTraversalKeysEnabled(false);
		jTextField_seur_precio.setColumns(10);
		jTextField_seur_precio.setBounds(217, 175, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_seur_precio);
		jTextField_seur_precio.setEditable(false);

		jTextField_tnt_precio = new JTextField();
		jTextField_tnt_precio.setVisible(false);
		jTextField_tnt_precio.setFocusable(false);
		jTextField_tnt_precio.setEnabled(false);
		jTextField_tnt_precio.setFocusTraversalKeysEnabled(false);
		jTextField_tnt_precio.setColumns(10);
		jTextField_tnt_precio.setBounds(1176, 154, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_tnt_precio);
		jTextField_tnt_precio.setEditable(false);

		jTextField_transaher_precio = new JTextField();
		jTextField_transaher_precio.setVisible(false);
		jTextField_transaher_precio.setFocusable(false);
		jTextField_transaher_precio.setEnabled(false);
		jTextField_transaher_precio.setFocusTraversalKeysEnabled(false);
		jTextField_transaher_precio.setColumns(10);
		jTextField_transaher_precio.setBounds(1246, 154, 60, 20);
		frmTarifasTransporteBorman.getContentPane().add(jTextField_transaher_precio);
		jTextField_transaher_precio.setEditable(false);

		JLabel lblDhl = new JLabel("DHL");
		lblDhl.setFont(new Font("Microsoft Tai Le", Font.BOLD, 11));
		lblDhl.setBounds(89, 92, 46, 14);
		frmTarifasTransporteBorman.getContentPane().add(lblDhl);

		JLabel lblGefco = new JLabel("GEFCO");
		lblGefco.setVisible(false);
		lblGefco.setFocusable(false);
		lblGefco.setEnabled(false);
		lblGefco.setFont(new Font("Microsoft Tai Le", Font.BOLD, 11));
		lblGefco.setBounds(770, 71, 46, 14);
		frmTarifasTransporteBorman.getContentPane().add(lblGefco);

		JLabel lblGls = new JLabel("GLS");
		lblGls.setVisible(false);
		lblGls.setFocusable(false);
		lblGls.setEnabled(false);
		lblGls.setFont(new Font("Microsoft Tai Le", Font.BOLD, 11));
		lblGls.setBounds(840, 71, 45, 14);
		frmTarifasTransporteBorman.getContentPane().add(lblGls);

		JLabel lblHalcourier = new JLabel("HALCOURIER");
		lblHalcourier.setVisible(false);
		lblHalcourier.setFocusable(false);
		lblHalcourier.setEnabled(false);
		lblHalcourier.setFont(new Font("Microsoft Tai Le", Font.BOLD, 11));
		lblHalcourier.setBounds(898, 71, 73, 14);
		frmTarifasTransporteBorman.getContentPane().add(lblHalcourier);

		JLabel lblNacex = new JLabel("NACEX");
		lblNacex.setFont(new Font("Microsoft Tai Le", Font.BOLD, 11));
		lblNacex.setBounds(159, 92, 46, 14);
		frmTarifasTransporteBorman.getContentPane().add(lblNacex);

		JLabel lblRedur = new JLabel("REDUR");
		lblRedur.setVisible(false);
		lblRedur.setFocusable(false);
		lblRedur.setEnabled(false);
		lblRedur.setFont(new Font("Microsoft Tai Le", Font.BOLD, 11));
		lblRedur.setBounds(1050, 71, 46, 14);
		frmTarifasTransporteBorman.getContentPane().add(lblRedur);

		JLabel lblSeur = new JLabel("SEUR");
		lblSeur.setFont(new Font("Microsoft Tai Le", Font.BOLD, 11));
		lblSeur.setBounds(229, 92, 46, 14);
		frmTarifasTransporteBorman.getContentPane().add(lblSeur);

		JLabel lblTnt = new JLabel("TNT");
		lblTnt.setVisible(false);
		lblTnt.setFocusable(false);
		lblTnt.setEnabled(false);
		lblTnt.setFont(new Font("Microsoft Tai Le", Font.BOLD, 11));
		lblTnt.setBounds(1190, 71, 46, 14);
		frmTarifasTransporteBorman.getContentPane().add(lblTnt);

		JLabel lblTransaher = new JLabel("TRANSAHER");
		lblTransaher.setVisible(false);
		lblTransaher.setFocusable(false);
		lblTransaher.setEnabled(false);
		lblTransaher.setFont(new Font("Microsoft Tai Le", Font.BOLD, 11));
		lblTransaher.setBounds(1246, 71, 73, 14);
		frmTarifasTransporteBorman.getContentPane().add(lblTransaher);

		JLabel lblTxt = new JLabel("TXT");
		lblTxt.setVisible(false);
		lblTxt.setFocusable(false);
		lblTxt.setEnabled(false);
		lblTxt.setFont(new Font("Microsoft Tai Le", Font.BOLD, 11));
		lblTxt.setBounds(1329, 71, 46, 14);
		frmTarifasTransporteBorman.getContentPane().add(lblTxt);

		lblKg = new JLabel("KG");
		lblKg.setFont(new Font("Microsoft Tai Le", Font.BOLD, 11));
		lblKg.setBounds(268, 39, 46, 14);
		frmTarifasTransporteBorman.getContentPane().add(lblKg);

		lblProvincias = new JLabel("PROVINCIAS");
		lblProvincias.setFont(new Font("Microsoft Tai Le", Font.BOLD, 11));
		lblProvincias.setBounds(38, 16, 165, 14);
		frmTarifasTransporteBorman.getContentPane().add(lblProvincias);

		lblComprobar = new JLabel("");
		lblComprobar.setBounds(213, 16, 111, 14);
		frmTarifasTransporteBorman.getContentPane().add(lblComprobar);
	}

	private void llenar_campos() {

		String sql = "SELECT * FROM zonas where provincia = '" + provinciaSeleccionada + "'";

		try (Connection connection = Conexion.Conectar();
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				dhl = rs.getString(3);
				gefco = rs.getString(4);
				gls = rs.getString(5);
				halcourier = rs.getString(6);
				nacex = rs.getString(7);
				redur = rs.getString(8);
				seur = rs.getString(9);
				tnt = rs.getString(10);
				transaher = rs.getString(11);
				txt = rs.getString(12);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/// SEGUNDO PASO
	private Double cargarDatos(String zona, String tabla) {
		double valor = 0;
		int reg = 0;
		// Guardar en variable global para usarla para Nacex.
		zonaGuardada = zona;

		zona = "ZONA " + zona;

		String sqlContar = "SELECT Count([" + zona + "]) AS PVP FROM tarifa_" + tabla + " WHERE kilos>="
				+ jTextField_kilos.getText();

		try (Connection connection = Conexion.Conectar();
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(sqlContar)) {
			while (rs.next()) {
				reg = rs.getInt(1);

			}

		} catch (Exception e) {
			System.out.print(e);
		}

		String sql = "SELECT First([" + zona + "]) AS PVP FROM tarifa_" + tabla + " WHERE kilos>="
				+ jTextField_kilos.getText();
		if (reg > 0 || tabla.equals("nacex")) {

			try (Connection connection = Conexion.Conectar();
					Statement st = connection.createStatement();
					ResultSet rs = st.executeQuery(sql)) {

				while (rs.next()) {

					if (rs.getString(1) == null) {
						System.out.println("no data");
						valor = -1.0;
					} else {
						valor = Double.parseDouble(rs.getString(1));
					}
				}
			} catch (

			SQLException e) {
				e.printStackTrace();
			}

		}
		return valor;
	}

	private void limpiar() {
		jTextField_dhl_zonas.setText("");
		jTextField_gefco_zonas.setText("");
		jTextField_gls_zonas.setText("");
		jTextField_halcourier_zonas.setText("");
		jTextField_nacex_zonas.setText("");
		jTextField_redur_zonas.setText("");
		jTextField_seur_zonas.setText("");
		jTextField_tnt_zonas.setText("");
		jTextField_transaher_zonas.setText("");
		jTextField_txt_zonas.setText("");

		jTextField_dhl_kilos.setText("");
		jTextField_gefco_kilos.setText("");
		jTextField_gls_kilos.setText("");
		jTextField_halcourier_kilos.setText("");
		jTextField_nacex_kilos.setText("");
		jTextField_redur_kilos.setText("");
		jTextField_seur_kilos.setText("");
		jTextField_tnt_kilos.setText("");
		jTextField_transaher_kilos.setText("");
		jTextField_txt_kilos.setText("");

		jTextField_dhl_precio.setText("");
		jTextField_gefco_precio.setText("");
		jTextField_gls_precio.setText("");
		jTextField_halcourier_precio.setText("");
		jTextField_nacex_precio.setText("");
		jTextField_redur_precio.setText("");
		jTextField_seur_precio.setText("");
		jTextField_tnt_precio.setText("");
		jTextField_transaher_precio.setText("");
		jTextField_txt_precio.setText("");

		jTextField_kilos.setText("");
	}

	private void limpiarCampos(String tarifa) {
		switch (tarifa) {
		case "dhl":
			jTextField_dhl_zonas.setText("Sin tarifa");
			jTextField_dhl_precio.setText("Sin datos");
			break;
		case "gefco":
			jTextField_gefco_zonas.setText("Sin tarifa");
			jTextField_gefco_precio.setText("Sin datos");
			break;
		case "gls":
			jTextField_gls_zonas.setText("Sin tarifa");
			jTextField_gls_precio.setText("Sin datos");
			break;
		case "halcourier":
			jTextField_halcourier_zonas.setText("Sin tarifa");
			jTextField_halcourier_precio.setText("Sin datos");
			break;
		case "nacex":
			jTextField_nacex_zonas.setText("Sin tarifa");
			jTextField_nacex_precio.setText("Sin datos");
			break;
		case "redur":
			jTextField_redur_zonas.setText("Sin tarifa");
			jTextField_redur_precio.setText("Sin datos");
			break;
		case "seur":
			jTextField_seur_zonas.setText("Sin tarifa");
			jTextField_seur_precio.setText("Sin datos");
			break;
		case "tnt":
			jTextField_tnt_zonas.setText("Sin tarifa");
			jTextField_tnt_precio.setText("Sin datos");
			break;
		case "transaher":
			jTextField_transaher_zonas.setText("Sin tarifa");
			jTextField_transaher_precio.setText("Sin datos");
			break;
		case "txt":
			jTextField_txt_zonas.setText("Sin tarifa");
			jTextField_txt_precio.setText("Sin datos");
			break;
		}
	}

	/// TERCER PASO
	private void datos(Double valor, String tabla) {
		DecimalFormat df = new DecimalFormat("#.000");
		switch (tabla) {
		case "dhl":
			if (valor == 0.0) {
				jTextField_dhl_precio.setText("Sin PVP");
			} else {
				jTextField_dhl_precio.setText("" + df.format(valor) + " €");
			}

			break;
		case "gefco":
			if (valor == 0.0) {
				jTextField_gefco_precio.setText("Sin PVP");
			} else {
				jTextField_gefco_precio.setText("" + df.format(valor) + " €");
			}
			break;
		case "gls":
			if (valor == 0.0) {
				jTextField_gls_precio.setText("Sin PVP");
			} else {
				jTextField_gls_precio.setText("" + df.format(valor) + " €");
			}

			break;
		case "halcourier":
			if (valor == 0.0) {
				jTextField_halcourier_precio.setText("Sin PVP");
			} else {
				jTextField_halcourier_precio.setText("" + df.format(valor) + " €");
			}

			break;
		case "nacex":
			if (valor == -1.0) {
				Object[] valoresIncrementoNacex = obtenerIncrementoNacex();
				int aumentoKilos = (int) valoresIncrementoNacex[0];
				double aumentoPrecio = (double) valoresIncrementoNacex[1];
				Object[] ultimoValor = obtenerUltimoValor();
				calcularNacex(aumentoKilos, aumentoPrecio, ultimoValor);

			} else {
				jTextField_nacex_precio.setText("" + df.format(valor) + " €");
			}
			break;
		case "redur":
			jTextField_redur_precio.setText("" + df.format(valor) + " €");

			if (valor == 0.0) {
				jTextField_redur_precio.setText("Sin PVP");
			}

			break;
		case "seur":
			jTextField_seur_precio.setText("" + df.format(valor) + " €");

			if (valor == 0.0) {
				jTextField_seur_precio.setText("Sin PVP");
			}

			break;
		case "tnt":
			if (valor == 0.0) {
				jTextField_tnt_precio.setText("Sin PVP");
			} else {
				jTextField_tnt_precio.setText("" + df.format(valor) + " €");
			}

			break;
		case "transaher":
			if (valor == 0.0) {
				jTextField_transaher_precio.setText("Sin PVP");
			} else {
				jTextField_transaher_precio.setText("" + df.format(valor) + " €");
			}

			break;
		case "txt":
			if (valor == 0.0) {
				jTextField_txt_precio.setText("Sin PVP");
			} else {
				jTextField_txt_precio.setText("" + df.format(valor) + " €");
			}
			break;
		}
	}

	// CUARTO PASO
	private Object[] obtenerIncrementoNacex() {
		int kilos = 0;
		double precio = 0;
		zonaGuardada = "ZONA " + zonaGuardada;
		String sql = "SELECT kilos, [" + zonaGuardada + "] FROM incremento_NACEX";
		try (Connection connection = Conexion.Conectar();
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				kilos = rs.getInt(1);
				precio = rs.getDouble(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Object[] { kilos, precio };
	}

	// QUINTO PASO
	private Object[] obtenerUltimoValor() {
		String sql = "SELECT Max(kilos), [" + zonaGuardada + "] FROM tarifa_NACEX GROUP BY [" + zonaGuardada + "] ";

		System.out.print(sql);
		int kilosTarifaNacex = 0;
		double precioTarifaNacex = 0;
		try (Connection connection = Conexion.Conectar();
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				kilosTarifaNacex = rs.getInt(1);
				precioTarifaNacex = rs.getDouble(2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Object[] { kilosTarifaNacex, precioTarifaNacex };
	}

	/// SEXTO PASO
	private void calcularNacex(double aumentoKilos, double aumentoPrecio, Object[] ultimoValor) {
		double kilosIntroducidos = Double.parseDouble(jTextField_kilos.getText());
		double val1;
		int kilosTarifaNacex = (int) ultimoValor[0];
		double precioTarifaNacex = (double) ultimoValor[1];

		double diferenciaKilos = kilosIntroducidos - kilosTarifaNacex;
		int division = (int) (diferenciaKilos / aumentoKilos);

		double resto = (diferenciaKilos % aumentoKilos);
		if (resto == 0) {
			val1 = division * aumentoPrecio;
			val1 = val1 + precioTarifaNacex;
		} else {

			val1 = (division + 1) * aumentoPrecio;
			val1 = val1 + precioTarifaNacex;
		}
		DecimalFormat df = new DecimalFormat("#.000");
		jTextField_nacex_precio.setText("" + df.format(val1) + " €");
	}

	private String dhl;
	private String gefco;
	private String gls;
	private String halcourier;
	private String nacex;
	private String redur;
	private String seur;
	private String tnt;
	private String transaher;
	private String txt;
	private JLabel lblKg;
	private JLabel lblProvincias;

	public static boolean pulsar = false;

	private String provinciaSeleccionada;
	private ComboBoxTyping jComboBoxProvincias;
	private String zonaGuardada;
	private JLabel lblComprobar;
}