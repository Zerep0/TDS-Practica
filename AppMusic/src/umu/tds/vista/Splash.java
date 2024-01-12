package umu.tds.vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Splash extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int ALTO_GIF = 450;
	private static final int ANCHO_GIF = 800;
	/**
	 * Create the panel.
	 */
	private JLabel splash;
	private ImageIcon gifSplash;
	private JFrame frame;
	private String ruta;
	private int alto_original, ancho_original;
	
	
	public Splash(JFrame frame, String ruta, int alto_original,int ancho_original) {
		this.alto_original = alto_original;
		this.ancho_original = ancho_original;
		this.frame = frame;
		this.ruta = ruta;
		inicialize();
	}
	
	void inicialize()
	{
		frame.setSize(ANCHO_GIF, ALTO_GIF);
		frame.setResizable(false);
		gifSplash = new ImageIcon("src/ImagenesEntrada/INTRO.gif");
		splash = new JLabel(gifSplash);
		setLayout(new BorderLayout());
		add(splash, BorderLayout.CENTER);
		Timer timer = new Timer(5000, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	               frame.setSize(alto_original,ancho_original);
	               CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
	               cardLayout.show(frame.getContentPane(), ruta);
	               frame.setResizable(true);
	            }
	        });

	        timer.setRepeats(false); // No se repite automáticamente
	        timer.start(); // Inicia el temporizador
	    
	}
}
