package umu.tds.vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;

public class MenuBusqueda extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String RUTA_RESULTADO = "BusquedaR";
	private static final String RUTA_BUSQUEDA = "BusquedaB";
	/**
	 * Create the panel.
	 */
	private JPanel menuBusquedaB;
	private JPanel menuBusquedaR;
	private CardLayout miCardLayout;
	public MenuBusqueda() {
		initialize();
	}
	
	private void initialize() {
		miCardLayout = new CardLayout(0, 0);
		setLayout(miCardLayout);
		
		// AÑADIR MENU DE BUSQUEDA CON LA LUPA
		menuBusquedaB = new MenuBusquedaB(miCardLayout,RUTA_RESULTADO);
		add(menuBusquedaB,RUTA_BUSQUEDA);
		// AÑADIR MENU DE BUSQUEDA DE RESULTADO
		menuBusquedaR = new MenuBusquedaR(miCardLayout,RUTA_BUSQUEDA);
		add(menuBusquedaR,RUTA_RESULTADO);
		
		
		
	}
	
}
