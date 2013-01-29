package ar.com.vioflaInc.system.connection;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;

import ar.com.vioflaInc.system.scripting.Script;

public class ConnectionApplication {

	private static ConnectionApplication instance = null;

	public static ConnectionApplication INSTANCE() {
		if (instance == null) {
			instance = new ConnectionApplication();
		}
		return instance;
	}

	public boolean getWifi() {
		return wifi;
	}

	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}

	private boolean wifi = false;

	public Collection<String> getInterfaces() {
		Script conn = new Script();
		String content = "ifconfig -s";
		String output = conn.executeScript(content, null, true);
		Collection<String> toRet;
		toRet = this.recortar(output);
		return toRet;

	}

	public Collection<String> recortar(String o) {
		String[] col = o.split("\n");
		Collection<String> toRet = new ArrayList<String>();
		for (int i = 1; i < col.length; i++) {
			if (!col[i].contains("lo")) {
				toRet.add(col[i].split(" ")[0]);
			}
		}
		return toRet;

	}

	public Boolean isWireless(String interfaz) {
		Script conn = new Script();
		String content = "iwconfig " + interfaz;
		conn.executeScript(content, null, true);
		if (!conn.getError().contains("no wireless extensions")) {
			this.setWifi(true);
		}else{
			this.setWifi(false);
		}
		return this.getWifi();
	}

	public void connect(String interfaz, String red, String pass, JButton sig) {
		Script conn = new Script();
		if (!this.getWifi()) {
			String content = "dhcpcd " + interfaz;
			conn.executeScript(content, null, true);
			
		} else {
			if(red.equals("")||red==null||pass.equals("")||pass==null){
				throw new RuntimeException("No se pudo Conectar: Red o Clave no completada");
			}
			
			String content = "ip link set "
					+ interfaz
					+ " up\nmv /etc/wpa_supplicant/wpa_supplicant.conf /etc/wpa_supplicant/wpa_supplicant.conf.orig\nwpa_passphrase "
					+ red
					+ " "
					+ pass
					+ " > /etc/wpa_supplicant/wpa_supplicant.conf\nwpa_supplicant -B -Dwext -i "
					+ interfaz
					+ " -c /etc/wpa_supplicant/wpa_supplicant.conf\ndhcpcd "
					+ interfaz;
			conn.executeScript(content, null, true);
			
		}
		if (!conn.getError().equals("") && conn.getError() != null) {
			throw new RuntimeException("No se pudo Conectar: "
					+ conn.getError());
		}else{
			sig.setEnabled(true);
		}
	}
	
	public Collection<String> getEssid() {
		Script conn = new Script();
		String content = "iwlist scan";
		String output = conn.executeScript(content, null, true);
		Collection<String> toRet;
		toRet = this.getNombresEssid(output);
		return toRet;

	}
	
	private Collection<String> getNombresEssid(String o){
		String[] col = o.split("ESSID:");
		Collection<String> toRet = new ArrayList<String>();
		for (int i = 1; i < col.length; i++) {
			int cont=1;
			for (int j = 1; j < col[i].toCharArray().length; j++) {
				if(col[i].toCharArray()[j]=='"'){
					break;
				}else{
					cont++;
				}
				
			}
			toRet.add(col[i].substring(1, cont));
		}
		return toRet;
	}

}
