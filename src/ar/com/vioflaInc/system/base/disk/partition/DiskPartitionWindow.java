package ar.com.vioflaInc.system.base.disk.partition;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Rectangle;

@SuppressWarnings("serial")
public class DiskPartitionWindow extends JFrame {
	public DiskPartitionWindow() {
		setBounds(new Rectangle(0, 27, 754, 495));
		setTitle("Viofla Inc.");
		setIconImage(Toolkit.getDefaultToolkit().getImage(DiskPartitionWindow.class.getResource("/ar/com/vioflaInc/resources/Arhc_logo.png")));
		getContentPane().setLayout(null);
	}

}
