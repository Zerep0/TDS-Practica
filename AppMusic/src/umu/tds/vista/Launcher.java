package umu.tds.vista;

import java.awt.EventQueue;
import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.CardLayout;


public class Launcher {
	private static final String INICIO = "Inicio";
	private static String REGISTRO = "Registro";
	private static String MENU = "Menu";
	private static String SPLASH = "Splash";
	private static final int ANCHO_DEFECTO = 500;
	private static final int ALTO_DEFECTO = 800;
	private JFrame frame;
	private JPanel splash, inicio, registro, menu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
					Launcher window = new Launcher();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws FontFormatException 
	 */
	public Launcher() throws FontFormatException, IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws FontFormatException 
	 */
	private void initialize() throws FontFormatException, IOException {
		frame = new JFrame();
		frame.setSize(ALTO_DEFECTO,ANCHO_DEFECTO);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		// INICIAR EL SPLASH
		splash = new Splash(frame,INICIO, ALTO_DEFECTO, ANCHO_DEFECTO);
		frame.getContentPane().add(splash, SPLASH);
		
		// INICIO
		inicio = new Inicio(frame);
		frame.getContentPane().add(inicio,INICIO);
		
		// REGISTRO
		registro = new Registro(frame);
		frame.getContentPane().add(registro,REGISTRO);
		
		//MENU
		menu = new Menu(this.frame);
		frame.getContentPane().add(menu,MENU);
		
		
	}
	
	public JFrame getFrame()
	{
		return frame;
	}

}
