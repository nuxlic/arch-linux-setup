package ar.com.vioflaInc.system.base.welcome;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.UIManager;

import ar.com.vioflaInc.system.connection.ConnectionWindow;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class WelcomeWindow extends JFrame {
	public WelcomeWindow() {
		setTitle("Viofla Inc.");
		setIconImage(Toolkit.getDefaultToolkit().getImage(WelcomeWindow.class.getResource("/ar/com/vioflaInc/resources/Arhc_logo.png")));
		getContentPane().setLayout(null);
		
		JLabel lblBienvenidoAlAsistente = new JLabel("Bienvenido al Asistente de Instalación de Arch Linux");
		lblBienvenidoAlAsistente.setFont(new Font("Dialog", Font.BOLD, 24));
		lblBienvenidoAlAsistente.setBounds(12, 12, 720, 23);
		getContentPane().add(lblBienvenidoAlAsistente);
		
		JButton btnSiguiente = new JButton("Siguiente");
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConnectionWindow cw=new ConnectionWindow();
				cw.setBounds(0,27,754,495);
				cw.setVisible(true);
				dispose();
			}
		});
		
		btnSiguiente.setBounds(615, 423, 117, 25);
		getContentPane().add(btnSiguiente);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnSalir.setBounds(12, 423, 117, 25);
		getContentPane().add(btnSalir);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(WelcomeWindow.class.getResource("/ar/com/vioflaInc/resources/Arhc_logo.png")));
		label.setBounds(344, 52, 388, 290);
		getContentPane().add(label);
		
		JTextArea txtrArchLinuxDefine = new JTextArea();
		txtrArchLinuxDefine.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtrArchLinuxDefine.setBackground(UIManager.getColor("windowBorder"));
		txtrArchLinuxDefine.setForeground(Color.BLACK);
		txtrArchLinuxDefine.setText("Arch Linux define \nsimplicidad como \n\"...una ligera estructura \nbase sin agregados \ninnecesarios, modificaciones, \no complicaciones, que \npermite a un usuario \nindividual modelar \nel sistema de acuerdo \na sus propias necesidades\"");
		txtrArchLinuxDefine.setToolTipText("");
		txtrArchLinuxDefine.setBounds(61, 101, 247, 202);
		getContentPane().add(txtrArchLinuxDefine);
	}
}
