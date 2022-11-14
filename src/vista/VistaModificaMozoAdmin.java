package vista;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import modelo.Enumerados;
import modelo.Enumerados.estadoMozo;
import modelo.Mozo;
import negocio.Sistema;

public class VistaModificaMozoAdmin extends JPanel implements ItemListener, IVistaModificaMozoAdmin{
	private JTextField textFieldNyA;
	private JTextField textFieldCantHijos;
	private JTextField textFieldSueldo;
	private JButton btnVolver;
	private JButton btnConfirmar;
	private JComboBox comboBox;
	private JComboBox<String> comboBoxMozo;
	private ActionListener actionListener;
	private Mozo mozo;
	/**
	 * Create the panel.
	 */
	public VistaModificaMozoAdmin() {
		setBorder(new TitledBorder(null, "Modifica Mozo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(1, 1, 0, 0));
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new GridLayout(7, 1, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		JLabel lblSeleccionaMozo = new JLabel("Seleccione Mozo");
		panel_1.add(lblSeleccionaMozo);
		lblSeleccionaMozo.setHorizontalAlignment(SwingConstants.CENTER);
		
		comboBoxMozo = new JComboBox<String>();
		comboBoxMozo = new JComboBox<String>();
		comboBoxMozo.setEditable(true);
		comboBoxMozo.addItem(Sistema.getInstance().getMozos().get("Marti").getNyA());
		comboBoxMozo.addItem(Sistema.getInstance().getMozos().get("Valen").getNyA());
		comboBoxMozo.addItem(Sistema.getInstance().getMozos().get("Pau").getNyA());
		comboBoxMozo.addItemListener(this);
		panel_1.add(comboBoxMozo);

		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		JLabel lblNyA = new JLabel("Nombre y apellido");
		panel_2.add(lblNyA);
		
		textFieldNyA = new JTextField();
		panel_2.add(textFieldNyA);
		textFieldNyA.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		JLabel lblCantHijos = new JLabel("Cantidad  de hijos");
		panel_3.add(lblCantHijos);
		
		textFieldCantHijos = new JTextField();
		panel_3.add(textFieldCantHijos);
		textFieldCantHijos.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		
		JLabel lblSueldo = new JLabel("Sueldo");
		panel_4.add(lblSueldo);
		
		textFieldSueldo = new JTextField();
		panel_4.add(textFieldSueldo);
		textFieldSueldo.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		
		JLabel lblNewLabel = new JLabel("Estado");
		panel_5.add(lblNewLabel);
		
		comboBox = new JComboBox();
		panel_5.add(comboBox);
		comboBox.addItem(Enumerados.estadoMozo.ACTIVO);
		comboBox.addItem(Enumerados.estadoMozo.AUSENTE);
		comboBox.addItem(Enumerados.estadoMozo.DEFRANCO);
		comboBox.addItemListener(this);
		
		JPanel panel_6 = new JPanel();
		panel.add(panel_6);
		
		btnConfirmar = new JButton("Confirmar");
		panel_6.add(btnConfirmar);
		btnConfirmar.setActionCommand("CONFIRMAR");
		
		JPanel panel_1_1 = new JPanel();
		panel.add(panel_1_1);
		
		btnVolver = new JButton("Volver");
		panel_1_1.add(btnVolver);
		btnVolver.setActionCommand("VOLVER");
		mozo = Sistema.getInstance().getMozos().get(this.comboBoxMozo.getSelectedItem().toString());
		textFieldCantHijos.setText(String.valueOf(mozo.getCantHijos()));
		textFieldNyA.setText(mozo.getNyA());
		textFieldSueldo.setText(String.valueOf(mozo.getSueldo()));
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		mozo = Sistema.getInstance().getMozos().get(this.comboBoxMozo.getSelectedItem().toString());
		textFieldCantHijos.setText(String.valueOf(mozo.getCantHijos()));
		textFieldNyA.setText(mozo.getNyA());
		textFieldSueldo.setText(String.valueOf(mozo.getSueldo()));
	}

	@Override
	public void addAcionListener(ActionListener actionListener) {
		this.btnConfirmar.addActionListener(actionListener);
		this.btnVolver.addActionListener(actionListener);
		this.actionListener = actionListener;
		
	}

	@Override
	public String getNyA() {
		// TODO Auto-generated method stub
		return this.textFieldNyA.getText();
	}

	@Override
	public int getCantHijos() {
		int cantHijos=-1;
		try {
			cantHijos = Integer.parseInt(this.textFieldCantHijos.getText());
		}
		catch (NumberFormatException e2) {
		}
		return cantHijos;
	}

	@Override
	public float getSueldo() {
		float sueldo=0;
		try {
			sueldo = Float.parseFloat(this.textFieldSueldo.getText());
		}
		catch (NumberFormatException e2) {
		}
		return sueldo;
	}

	@Override
	public void limpiarVista() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ventanaEmergente(String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje);		
	}

	@Override
	public estadoMozo getEstadoMozo() {
		return (estadoMozo) this.comboBox.getSelectedItem();
	}

	@Override
	public Mozo getMozo() {
		return this.mozo;
	}

}