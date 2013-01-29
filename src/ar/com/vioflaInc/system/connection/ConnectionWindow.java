package ar.com.vioflaInc.system.connection;

import javax.swing.JFrame;
import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JCheckBox;


@SuppressWarnings("serial")
public class ConnectionWindow extends JFrame implements ItemListener {
	private JPasswordField passwordField=new JPasswordField();;
	private ConnectionApplication connApp=ConnectionApplication.INSTANCE();
	final JComboBox<String> comboBox = new JComboBox<String>();
	final JLabel lblRedssid = new JLabel("Red (essid)");
	final JLabel lblPassword = new JLabel("Password");
	final JComboBox<String> comboBox_1 = new JComboBox<String>();
	JLabel lblDebeEstarConfigurada = new JLabel("Debe estar configurada con seguridad WPA");
	public ConnectionWindow() {
		setTitle("Viofla Inc.");
		setBounds(new Rectangle(0, 27, 754, 495));
		getContentPane().setLayout(null);
		
		JLabel lblConfiguracionDeConexion = new JLabel("Configuración de conexión a Internet");
		lblConfiguracionDeConexion.setFont(new Font("Dialog", Font.BOLD, 16));
		lblConfiguracionDeConexion.setBounds(12, 40, 618, 38);
		getContentPane().add(lblConfiguracionDeConexion);
		
		JLabel lblSeleccioneSuInterfaz = new JLabel("Seleccione su interfaz de red");
		lblSeleccioneSuInterfaz.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSeleccioneSuInterfaz.setBounds(23, 135, 336, 38);
		getContentPane().add(lblSeleccioneSuInterfaz);
		
		JLabel lblInterfaz = new JLabel("Interfaz");
		lblInterfaz.setBounds(33, 178, 84, 15);
		getContentPane().add(lblInterfaz);
		
		comboBox.addItemListener(this);
		comboBox.setBounds(124, 175, 155, 20);
		for(String interfaz: connApp.getInterfaces()){
			comboBox.addItem(interfaz);
		}
		getContentPane().add(comboBox);
		
		
		
		lblRedssid.setVisible(false);
		lblRedssid.setBounds(33, 276, 94, 15);
		getContentPane().add(lblRedssid);
		
		
		lblPassword.setVisible(false);
		lblPassword.setBounds(33, 303, 84, 15);
		getContentPane().add(lblPassword);
		
		
		passwordField.setVisible(false);
		passwordField.setText("");
		
		passwordField.setBounds(124, 301, 155, 19);
		getContentPane().add(passwordField);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setBounds(23, 425, 117, 25);
		getContentPane().add(btnSalir);
		
		final JButton btnSiguiente = new JButton("Siguiente");
		btnSiguiente.setEnabled(false);
		btnSiguiente.setBounds(623, 425, 117, 25);
		getContentPane().add(btnSiguiente);
		
		final JLabel label = new JLabel("");
		JButton btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					connApp.connect(comboBox.getSelectedItem().toString(), comboBox_1.getSelectedItem().toString(), passwordField.getPassword().toString(), btnSiguiente);
					
				} catch (RuntimeException e) {
					label.setText(e.getMessage());
				}
			}
		});
		btnConectar.setBounds(301, 173, 117, 25);
		getContentPane().add(btnConectar);
		
		label.setBounds(22, 90, 718, 49);
		getContentPane().add(label);
		
		final JCheckBox chckbxYaEstoyConectado = new JCheckBox("Ya estoy conectado a Internet y me quiero saltar este paso");
		chckbxYaEstoyConectado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxYaEstoyConectado.isSelected()){
					btnSiguiente.setEnabled(true);
				}
			}
		});
		chckbxYaEstoyConectado.setBounds(23, 377, 458, 23);
		getContentPane().add(chckbxYaEstoyConectado);
		
		comboBox_1.setVisible(false);
		comboBox_1.setBounds(124, 271, 155, 24);
		for(String red: connApp.getEssid()){
			comboBox_1.addItem(red);
		}
		getContentPane().add(comboBox_1);
		
		lblDebeEstarConfigurada.setVisible(false);
		lblDebeEstarConfigurada.setBounds(33, 227, 326, 15);
		getContentPane().add(lblDebeEstarConfigurada);
		
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(connApp.isWireless(comboBox.getSelectedItem().toString())){
			lblRedssid.setVisible(true);
			lblPassword.setVisible(true);
			passwordField.setVisible(true);
			comboBox_1.setVisible(true);
			lblDebeEstarConfigurada.setVisible(true);

			
		}else{
			System.out.println("Entro");
			lblRedssid.setVisible(false);
			lblPassword.setVisible(false);
			passwordField.setVisible(false);
			comboBox_1.setVisible(false);
			lblDebeEstarConfigurada.setVisible(false);

		}
	}
}
