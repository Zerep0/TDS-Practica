package umu.tds.helper;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class HiperVinculo {
	
	
	public void crearHiperVinculo(JLabel etiqueta, String ruta, String texto, JFrame frame )
	{
		etiqueta.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				etiqueta.setText("<html><font color=\"#9370DB\"><a href=''>" + texto + "</a></font></html>");
			}
			
			public void mouseExited(MouseEvent e) {
				etiqueta.setText("<html><font color=\"white\"><a href=''>" + texto + "</a></font></html>");
               
            }
			public void mouseClicked(MouseEvent e) {
				CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
				cardLayout.show(frame.getContentPane(), ruta);
			}
		});
	}
}
