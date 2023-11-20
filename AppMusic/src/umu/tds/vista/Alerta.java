package umu.tds.vista;

import javax.swing.JOptionPane;

public enum Alerta {
	
	INSTANCIA;
	Registro ventana;
	private Alerta() { }

	public void mostrarAlerta(String mensaje, String asunto, Registro ventana)
	{
		JOptionPane.showMessageDialog(ventana, mensaje, asunto,
				JOptionPane.PLAIN_MESSAGE);
	}
	
	
	
	
	
}
