package ar.com.vioflaInc.system.connection;

import javax.swing.JFrame;
import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;


@SuppressWarnings("serial")
public class ConnectionWindow extends JFrame implements ItemListener {
	private JTextField textField=new JTextField();
	private JPasswordField passwordField=new JPasswordField();;
	private ConnectionApplication connApp=ConnectionApplication.INSTANCE();
	final JComboBox<String> comboBox = new JComboBox<String>();
	final JLabel lblRedssid = new JLabel("Red (ssid)");
	final JLabel lblPassword = new JLabel("Password");
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
		comboBox.setBounds(124, 175, 114, 20);
		for(String interfaz: connApp.getInterfaces()){
			comboBox.addItem(interfaz);
		}
		getContentPane().add(comboBox);
		
		
		
		lblRedssid.setVisible(false);
		lblRedssid.setBounds(33, 227, 94, 15);
		getContentPane().add(lblRedssid);
		
		
		textField.setVisible(false);
		textField.setText("");
		textField.setBounds(124, 225, 114, 19);
		getContentPane().add(textField);
		
		
		lblPassword.setVisible(false);
		lblPassword.setBounds(33, 254, 84, 15);
		getContentPane().add(lblPassword);
		
		
		passwordField.setVisible(false);
		passwordField.setText("");
		passwordField.setBounds(124, 252, 114, 19);
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
					
					connApp.connect(comboBox.getSelectedItem().toString(), textField.getText(), passwordField.getPassword().toString(), btnSiguiente);
					
				} catch (RuntimeException e) {
					label.setText(e.getMessage());
				}
			}
		});
		btnConectar.setBounds(301, 173, 117, 25);
		getContentPane().add(btnConectar);
		
		label.setBounds(22, 90, 718, 49);
		getContentPane().add(label);
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(connApp.isWireless(comboBox.getSelectedItem().toString())){
			lblRedssid.setVisible(true);
			lblPassword.setVisible(true);
			passwordField.setVisible(true);
			textField.setVisible(true);

			
		}else{
			System.out.println("Entro");
			lblRedssid.setVisible(false);
			lblPassword.setVisible(false);
			passwordField.setVisible(false);
			textField.setVisible(false);
		}
	}
}
