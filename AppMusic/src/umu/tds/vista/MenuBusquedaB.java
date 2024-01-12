package umu.tds.vista;

import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;

import umu.tds.controlador.ControladorAppMusic;
import umu.tds.helper.Placeholder;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MenuBusquedaB extends JPanel {

	private Placeholder placeholder;
	private static final String PLACEHOLDER_INTERPRETE = "Interprete";
	private static final String PLACEHOLDER_BUSCAR = "¿Qué titulo deseas buscar?";
	private static final long serialVersionUID = 1L;
	private JTextField Interprete;
	private boolean filtroActivado;
	private MenuBusqueda menuBusqueda;
	private String ruta;
	/**
	 * Create the panel.
	 */
	public MenuBusquedaB(MenuBusqueda menuBusqueda, String ruta) {
		filtroActivado = false;
		this.menuBusqueda = menuBusqueda;
		this.ruta = ruta;
		initialize();
	}
	
	private void initialize()
	{
		// HELPER
		placeholder = new Placeholder();
		
		
		
		setBackground(new Color(18, 156, 189));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(18, 156, 189));
		add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 353, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0};
		gbl_panel.rowHeights = new int[]{20, 0, 0};
		panel.setLayout(gbl_panel);
		
		JLabel btnLupa = new JLabel("");
		btnLupa.setIcon(new ImageIcon(MenuBusquedaB.class.getResource("/ImagenesMenu/lupaMini.png")));
		GridBagConstraints gbc_btnLupa = new GridBagConstraints();
		gbc_btnLupa.insets = new Insets(0, 0, 5, 5);
		gbc_btnLupa.anchor = GridBagConstraints.EAST;
		gbc_btnLupa.gridx = 0;
		gbc_btnLupa.gridy = 1;
		panel.add(btnLupa, gbc_btnLupa);
		JTextField Buscador = new JTextField();
		Buscador.setFont(new Font("Arial", Font.ITALIC, 14));
		Buscador.setText(PLACEHOLDER_BUSCAR);
		Buscador.setPreferredSize(new Dimension(7, 30));
		Buscador.setColumns(40);
		GridBagConstraints gbc_Buscador = new GridBagConstraints();
		gbc_Buscador.fill = GridBagConstraints.HORIZONTAL;
		gbc_Buscador.insets = new Insets(0, 0, 5, 5);
		gbc_Buscador.gridx = 1;
		gbc_Buscador.gridy = 1;
		panel.add(Buscador,gbc_Buscador);
		
		JLabel btnFiltro = new JLabel("");

		btnFiltro.setIcon(new ImageIcon(MenuBusquedaB.class.getResource("/ImagenesMenu/filtrar.png")));
		GridBagConstraints gbc_btnFiltro = new GridBagConstraints();
		gbc_btnFiltro.anchor = GridBagConstraints.WEST;
		gbc_btnFiltro.insets = new Insets(0, 0, 5, 0);
		gbc_btnFiltro.gridx = 2;
		gbc_btnFiltro.gridy = 1;
		panel.add(btnFiltro, gbc_btnFiltro);
		
		JPanel panelFiltro = new JPanel();
		panelFiltro.setVisible(false);
		panelFiltro.setBackground(new Color(128, 255, 255));
		GridBagConstraints gbc_panelFiltro = new GridBagConstraints();
		gbc_panelFiltro.insets = new Insets(0, 0, 0, 5);
		gbc_panelFiltro.fill = GridBagConstraints.BOTH;
		gbc_panelFiltro.gridx = 1;
		gbc_panelFiltro.gridy = 2;
		panel.add(panelFiltro, gbc_panelFiltro);
		
		
		Interprete = new JTextField();
		Interprete.setFont(new Font("Arial", Font.ITALIC, 14));
		Interprete.setText(PLACEHOLDER_INTERPRETE);
		panelFiltro.add(Interprete);
		Interprete.setColumns(15);
		
		JComboBox<String> estilos = new JComboBox<String>(ControladorAppMusic.getInstancia().getEstilosMusicales().toArray(new String[0]));
		estilos.setPreferredSize(new Dimension(85, 23));
		estilos.setMinimumSize(new Dimension(7, 20));
		panelFiltro.add(estilos);
		
		JCheckBox chckbxFavoritas = new JCheckBox("Favoritas");
		chckbxFavoritas.setBackground(new Color(128, 255, 255));
		panelFiltro.add(chckbxFavoritas);
		
		
		// EVENTOS
		placeholder.crearPlaceholderText(Buscador, PLACEHOLDER_BUSCAR);
		placeholder.crearPlaceholderText(Interprete, PLACEHOLDER_INTERPRETE);
		btnFiltro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				 if(filtroActivado == false)
				 {
					 panelFiltro.setVisible(true);
					 filtroActivado = true;
				 }
				 else
				 {
					 panelFiltro.setVisible(false);
					 filtroActivado = false;
				 }
				
				
			}
		});
		btnLupa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				((CardLayout) menuBusqueda.getLayout()).show(MenuBusquedaB.this.getParent(), ruta);
				
				ArrayList<umu.tds.negocio.Cancion> canciones = ControladorAppMusic.getInstancia()
						.aplicarFiltros(Buscador,Interprete,estilos.getSelectedItem().toString(),chckbxFavoritas.isSelected());
				
				Component componente = menuBusqueda.getComponent(1);
				((MenuBusquedaR)componente).cargarCanciones(canciones);
				((MenuBusquedaR)componente).entrarVentana();
				
				
				
				
			}
		});
	}

}
