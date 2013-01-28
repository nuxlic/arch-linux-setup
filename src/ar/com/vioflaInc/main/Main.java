package ar.com.vioflaInc.main;

import ar.com.vioflaInc.system.base.welcome.WelcomeWindow;

public class Main {
	public static void main(String[] args) throws Exception {
//		Script sc = new Script("/home/viofla/Escritorio/MiScript.sh");
//		sc.setVerbose(false);
//		//String CONTENT = "sqlplus2\nls -l\nps\necho \"End\\nFin\"";
////		String OUTPUT = sc.executeScript(CONTENT,
////				"/home/viofla/Escritorio/MiScript.sh", true);
//		String OUTPUT = sc.executeCommand();
//		System.out.println(OUTPUT);
//		String ERROR = sc.getError();
//		System.out.println("**ERROR**" + ERROR);

		WelcomeWindow w=new WelcomeWindow();
		w.setBounds(0,27,754,495);
		w.setVisible(true);

	}
}
