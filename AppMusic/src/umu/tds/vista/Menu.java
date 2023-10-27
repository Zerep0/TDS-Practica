package umu.tds.vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JToolBar;
import javax.swing.JTabbedPane;
import java.awt.Choice;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;

public class Menu extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Create the panel.
	 */
	private JPanel home,playlist,busqueda; //Panel que te lleva al home
	public Menu() {
		
		initialize();
	}
	
	private void initialize()
	{
		setBackground(new Color(18, 159, 186));
		setLayout(new BorderLayout(0, 0));
		
		JPanel PanelBotonesMenu = new JPanel();
		PanelBotonesMenu.setBackground(new Color(18, 156, 189));
		add(PanelBotonesMenu, BorderLayout.SOUTH);
		
		JLabel btnHome = new JLabel("");
		btnHome.setIcon(new ImageIcon(Menu.class.getResource("/ImagenesMenu/home_icon_190886.png")));
		PanelBotonesMenu.add(btnHome);
		
		JLabel btnLupa = new JLabel("");
		btnLupa.setIcon(new ImageIcon(Menu.class.getResource("/ImagenesMenu/lupa.png")));
		PanelBotonesMenu.add(btnLupa);
		
		JLabel btnPlaylist = new JLabel("");
		btnPlaylist.setIcon(new ImageIcon(Menu.class.getResource("/ImagenesMenu/libros.png")));
		PanelBotonesMenu.add(btnPlaylist);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Menu.class.getResource("/ImagenesMenu/descargar.png")));
		PanelBotonesMenu.add(lblNewLabel);
		
		JPanel PanelBotonIcono = new JPanel();
		PanelBotonIcono.setBackground(new Color(18, 156, 189));
		add(PanelBotonIcono, BorderLayout.EAST);
		PanelBotonIcono.setLayout(new BorderLayout(0, 0));
		
		JLabel btnIcono = new JLabel("");
		btnIcono.setBackground(new Color(18, 156, 189));
		btnIcono.setVerticalAlignment(SwingConstants.TOP);
		btnIcono.setIcon(new ImageIcon(Menu.class.getResource("/ImagenesMenu/lavado-en-seco.png")));
		PanelBotonIcono.add(btnIcono, BorderLayout.CENTER);
		
		JPopupMenu desplegableIcono = new JPopupMenu();
		addPopup(btnIcono, desplegableIcono);
		
		
		JMenuItem btnRename = new JMenuItem("Rename");
		desplegableIcono.add(btnRename);
		
		JMenuItem btnPremium = new JMenuItem("Premium");
		desplegableIcono.add(btnPremium);
		
		JMenuItem btnLogout = new JMenuItem("Logout");
		desplegableIcono.add(btnLogout);
		
		JPanel rellenoIcono1 = new JPanel();
		rellenoIcono1.setBackground(new Color(18, 156, 189));
		PanelBotonIcono.add(rellenoIcono1, BorderLayout.WEST);
		
		JPanel rellenoIcono2 = new JPanel();
		rellenoIcono2.setBackground(new Color(18, 156, 189));
		PanelBotonIcono.add(rellenoIcono2, BorderLayout.EAST);
		
		JPanel rellenoIcono3 = new JPanel();
		rellenoIcono3.setBackground(new Color(18, 156, 189));
		PanelBotonIcono.add(rellenoIcono3, BorderLayout.NORTH);
		
		JPanel PanelNavegacion = new JPanel();
		PanelNavegacion.setBackground(new Color(18, 159, 186));
		add(PanelNavegacion, BorderLayout.CENTER);
		PanelNavegacion.setLayout(new CardLayout(0, 0));
		
		JPanel rellenoMargen = new JPanel();
		rellenoMargen.setBackground(new Color(18, 156, 189));
		add(rellenoMargen, BorderLayout.WEST);
		GridBagLayout gbl_rellenoMargen = new GridBagLayout();
		gbl_rellenoMargen.columnWidths = new int[]{80, 0};
		gbl_rellenoMargen.rowHeights = new int[]{0, 0};
		gbl_rellenoMargen.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_rellenoMargen.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		rellenoMargen.setLayout(gbl_rellenoMargen);
		
		// AÑADIR MENU DE HOME
		home = new MenuHome();
		PanelNavegacion.add(home, "Home");
		
		// AÑADIR MENU DE PLAYLIST
		playlist = new MenuPlaylist();
		PanelNavegacion.add(playlist,"Playlist");
		
		//AÑADIR MENU DE BUSQUEDA
		busqueda = new MenuBusqueda();
		PanelNavegacion.add(busqueda,"Busqueda");
		
		// EVENTOS 
		
		btnHome.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				CardLayout cardlayout = (CardLayout) PanelNavegacion.getLayout();
				cardlayout.show(PanelNavegacion, "Home");
			}
		});
		
		btnPlaylist.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				CardLayout cardlayout = (CardLayout) PanelNavegacion.getLayout();
				cardlayout.show(PanelNavegacion, "Playlist");
			}
		});
		
		btnLupa.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				CardLayout cardlayout = (CardLayout) PanelNavegacion.getLayout();
				cardlayout.show(PanelNavegacion, "Busqueda");
			}
		});
	}

	
	
	
	
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		
		
	}
	
	
	
	
	
	
	
	
}
