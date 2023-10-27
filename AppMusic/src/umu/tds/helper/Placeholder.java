package umu.tds.helper;

import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Placeholder{
	
	private static final String TEXTO_VACIO = "";
	
	public void crearPlaceholderText(JTextField campo, String PLACEHOLDER)
	{
		campo.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if(campo.getText().equals(PLACEHOLDER) && campo.getFont().isItalic())
				{
					campo.setText(TEXTO_VACIO);
					Font nuevaFuente = new Font("Arial", Font.PLAIN, 14);
					campo.setFont(nuevaFuente);
				}
			}
			
			public void focusLost(FocusEvent e)
			{
				if(campo.getText().equals(TEXTO_VACIO))
				{
					campo.setFont(new Font("Arial", Font.ITALIC, 14));
					campo.setText(PLACEHOLDER);
				}
			}
		});
	}
	
	public void crearPlaceholderPassword(JPasswordField campo, String PLACEHOLDER, JButton btnVisibilidad)
	{
		campo.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if(new String(campo.getPassword()).equals(PLACEHOLDER) && campo.getFont().isItalic())
				{
					campo.setText(TEXTO_VACIO);
					campo.setFont(new Font("Arial", Font.PLAIN, 14));
					campo.setEchoChar('*');
				}
				
			}
			
			public void focusLost(FocusEvent e)
			{
				if(new String(campo.getPassword()).equals(TEXTO_VACIO))
				{
					campo.setEchoChar((char) 0);
					campo.setFont(new Font("Arial", Font.ITALIC, 14));
					campo.setText(PLACEHOLDER);
					btnVisibilidad.setEnabled(false);
				}
			}
			
			
		});
	}
	
	public void crearPlaceholderPassword(JPasswordField campo, String PLACEHOLDER)
	{
		campo.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if(new String(campo.getPassword()).equals(PLACEHOLDER) && campo.getFont().isItalic())
				{
					campo.setText(TEXTO_VACIO);
					campo.setFont(new Font("Arial", Font.PLAIN, 14));
					campo.setEchoChar('*');
				}
				
			}
			
			public void focusLost(FocusEvent e)
			{
				if(new String(campo.getPassword()).equals(TEXTO_VACIO))
				{
					campo.setEchoChar((char) 0);
					campo.setFont(new Font("Arial", Font.ITALIC, 14));
					campo.setText(PLACEHOLDER);
				}
			}
			
			
		});

	}
	
}
