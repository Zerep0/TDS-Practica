package umu.tds.vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import cargadorCanciones.Cancion;
import javafx.scene.media.MediaPlayer;
import umu.tds.helper.AlineamientoLista;
import umu.tds.negocio.CatalogoCanciones;
import umu.tds.negocio.CatalogoUsuarios;
import umu.tds.utils.Player;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class MenuBusquedaR extends JPanel {

	private AlineamientoLista alinamientoListaBusqueda;
	private static final long serialVersionUID = 1L;
	private CardLayout menuBusqueda;
	private String ruta;
	private String pausa;
	private int numResultados;
	Player reproductor = new Player();
	JList<String> listaCanciones;
	ListaModelo miModelo;
	/**
	 * Create the panel.
	 */
	public MenuBusquedaR(CardLayout menuBusqueda, String ruta) {
		this.menuBusqueda = menuBusqueda;
		this.ruta = ruta;
		this.pausa = "play";
		this.numResultados = 0;
		initialize();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	private void initialize()
	{
		// HELPER
		alinamientoListaBusqueda = new AlineamientoLista();
		
		setBackground(new Color(18, 159, 186));
		setLayout(new BorderLayout(0, 0));
		
		JPanel PanelReproduccion = new JPanel();
		PanelReproduccion.setBackground(new Color(18, 156, 189));
		add(PanelReproduccion, BorderLayout.SOUTH);
		
		JLabel btnStop = new JLabel("");
		btnStop.setIcon(new ImageIcon(MenuBusquedaR.class.getResource("/ImagenesMenu/cuadrado.png")));
		PanelReproduccion.add(btnStop);
		
		JLabel btnRedo = new JLabel("");
		btnRedo.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/atras.png")));
		PanelReproduccion.add(btnRedo);
		
		JLabel btnPlay = new JLabel("");
		btnPlay.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/triangulo-negro-flecha-derecha.png")));
		PanelReproduccion.add(btnPlay);
		
		JLabel btnForwa = new JLabel("");
		btnForwa.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/siguiente.png")));
		PanelReproduccion.add(btnForwa);
		
		JLabel btnRandom = new JLabel("");
		btnRandom.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/aleatorio.png")));
		PanelReproduccion.add(btnRandom);
		
		JPanel PanelBienvenida = new JPanel();
		PanelBienvenida.setBackground(new Color(18, 159, 186));
		add(PanelBienvenida, BorderLayout.NORTH);
		PanelBienvenida.setLayout(new BorderLayout(0, 0));
		
		JLabel MsgBienvenida = new JLabel("Se han encontrado " + numResultados + " resultados");
		MsgBienvenida.setForeground(new Color(255, 255, 255));
		MsgBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
		MsgBienvenida.setFont(new Font("Arial", Font.BOLD, 24));
		PanelBienvenida.add(MsgBienvenida, BorderLayout.CENTER);
		
		JPanel rellenoBienvenida1 = new JPanel();
		rellenoBienvenida1.setBackground(new Color(18, 156, 189));
		PanelBienvenida.add(rellenoBienvenida1, BorderLayout.SOUTH);
		
		JPanel rellenoBienvenida2 = new JPanel();
		rellenoBienvenida2.setBackground(new Color(18, 156, 189));
		PanelBienvenida.add(rellenoBienvenida2, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MenuBusquedaR.class.getResource("/ImagenesMenu/flecha.png")));
		PanelBienvenida.add(lblNewLabel, BorderLayout.WEST);
		
		JPanel PanelRecientes = new JPanel();
		PanelRecientes.setBackground(new Color(18, 159, 186));
		add(PanelRecientes, BorderLayout.CENTER);
		PanelRecientes.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		PanelRecientes.add(scrollPane, BorderLayout.CENTER);
		
		miModelo = new ListaModelo();
		listaCanciones = new JList();
		listaCanciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaCanciones.setFont(new Font("Arial", Font.PLAIN, 16));
		listaCanciones.setModel(miModelo);
		scrollPane.setViewportView(listaCanciones);
		listaCanciones.setBorder(BorderFactory.createLineBorder(Color.black));
		listaCanciones.setCellRenderer(alinamientoListaBusqueda);
		
		JPanel LayoutTiempo = new JPanel();
		LayoutTiempo.setBackground(new Color(18, 156, 189));
		PanelRecientes.add(LayoutTiempo, BorderLayout.SOUTH);
		GridBagLayout gbl_LayoutTiempo = new GridBagLayout();
		gbl_LayoutTiempo.columnWidths = new int[]{55, 228, 5, 0};
		gbl_LayoutTiempo.rowHeights = new int[]{1};
		gbl_LayoutTiempo.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		gbl_LayoutTiempo.rowWeights = new double[]{1.0};
		LayoutTiempo.setLayout(gbl_LayoutTiempo);
		
		JPanel PanelTiempo = new JPanel();
		PanelTiempo.setBackground(new Color(18, 156, 189));
		GridBagConstraints gbc_PanelTiempo = new GridBagConstraints();
		gbc_PanelTiempo.insets = new Insets(0, 0, 0, 5);
		gbc_PanelTiempo.gridx = 1;
		gbc_PanelTiempo.gridy = 0;
		LayoutTiempo.add(PanelTiempo,gbc_PanelTiempo);
		
		JPanel subPanelTiempo = new JPanel();
		subPanelTiempo.setBackground(new Color(18, 156, 189));
		PanelTiempo.add(subPanelTiempo);
		subPanelTiempo.setLayout(new BorderLayout(0, 0));
		
		JSlider barraReproduccion = new JSlider();
		barraReproduccion.setBackground(new Color(18, 156, 189));
		barraReproduccion.setPreferredSize(new Dimension(400, 26));
		subPanelTiempo.add(barraReproduccion, BorderLayout.NORTH);
		
		JLabel msgDuracion = new JLabel("3:16");
		msgDuracion.setForeground(new Color(255, 255, 255));
		msgDuracion.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_msgDuracion = new GridBagConstraints();
		gbc_msgDuracion.gridx = 3;
		gbc_msgDuracion.gridy = 0;
		LayoutTiempo.add(msgDuracion, gbc_msgDuracion);
		
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuBusqueda.show(MenuBusquedaR.this.getParent(), ruta);
			}
		});
		
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				umu.tds.negocio.Cancion c = buscaCancion(listaCanciones.getSelectedValue());
				if(pausa == "play")
				{
					btnPlay.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/boton-de-pausa.png")));
					btnPlay.setVisible(true);
					reproductor.play(pausa, c);
					pausa = "pause";
				}
				else
				{
					btnPlay.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/triangulo-negro-flecha-derecha.png")));
					btnPlay.setVisible(true);
					reproductor.play(pausa, c);
					pausa = "play";
				}

			}
		});
		
		btnStop.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				umu.tds.negocio.Cancion c = buscaCancion(listaCanciones.getSelectedValue());
				reproductor.play("stop",c);
				btnPlay.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/triangulo-negro-flecha-derecha.png")));
				btnPlay.setVisible(true);
				pausa = "play";
			}
		});
		
	}
	
	void setNumResultados(int num)
	{
		this.numResultados = num;
	}
	
	public void refrescar()
	{
		
		List<umu.tds.negocio.Cancion> canciones = CatalogoCanciones.getUnicaInstancia().getCanciones();
		ArrayList<String> cancionesString = new ArrayList<>();
		for(umu.tds.negocio.Cancion can : canciones)
		{
			cancionesString.add(can.getTitulo() + " - " + can.getInterprete());
		}
		setNumResultados(cancionesString.size());
		miModelo.actualizarLista(cancionesString);
	}
	
	public umu.tds.negocio.Cancion buscaCancion(String cancion)
	{
		umu.tds.negocio.Cancion c;
		List<umu.tds.negocio.Cancion> canciones = CatalogoCanciones.getUnicaInstancia().getCanciones();
		for (umu.tds.negocio.Cancion can : canciones) {
			String aux = can.getTitulo() + " - " + can.getInterprete();
			if(aux.equals(cancion))
			{
				c = can;
				return c;
			}
		}
		return null;
	}
}

