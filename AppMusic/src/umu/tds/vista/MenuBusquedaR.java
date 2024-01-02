package umu.tds.vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import umu.tds.controlador.ControladorAppMusic;
import umu.tds.helper.AlineamientoLista;
import umu.tds.utils.Player;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.ResourceBundle.Control;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import  umu.tds.negocio.IReproductorListener;

public class MenuBusquedaR extends JPanel implements IReproductorListener{

	private AlineamientoLista alinamientoListaBusqueda;
	private static final long serialVersionUID = 1L;
	private CardLayout menuBusqueda;
	private String ruta;
	private String pausa;
	private JList<umu.tds.negocio.Cancion> listaCanciones;
	private ListaModelo miModelo;
	private JLabel MsgCancionesEncontradas;
	private JLabel btnFavorito;
	private JLabel btnStop;
	private JLabel btnRedo;
	private JLabel btnPlay;
	private JLabel btnForwa;
	private JLabel btnRandom;
	/**
	 * Create the panel.
	 */
	public MenuBusquedaR(CardLayout menuBusqueda, String ruta) {
		this.menuBusqueda = menuBusqueda;
		this.ruta = ruta;
		this.pausa = "play";
		ControladorAppMusic.getInstancia().setMenuBusquedaR(this);
		initialize();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	private void initialize()
	{
		// HELPER
		alinamientoListaBusqueda = new AlineamientoLista();
		miModelo = new ListaModelo();
		
		setBackground(new Color(18, 159, 186));
		setLayout(new BorderLayout(0, 0));
		
		JPanel PanelReproduccion = new JPanel();
		PanelReproduccion.setBackground(new Color(18, 156, 189));
		add(PanelReproduccion, BorderLayout.SOUTH);
		
		btnStop = new JLabel("");
		btnStop.setIcon(new ImageIcon(MenuBusquedaR.class.getResource("/ImagenesMenu/cuadrado.png")));
		PanelReproduccion.add(btnStop);
		
		btnRedo = new JLabel("");
		btnRedo.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/atras.png")));
		PanelReproduccion.add(btnRedo);
		
		btnPlay = new JLabel("");
		btnPlay.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/triangulo-negro-flecha-derecha.png")));
		PanelReproduccion.add(btnPlay);
		
		btnForwa = new JLabel("");
		btnForwa.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/siguiente.png")));
		PanelReproduccion.add(btnForwa);
		
		btnRandom = new JLabel("");
		btnRandom.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/aleatorio.png")));
		PanelReproduccion.add(btnRandom);
		
		btnFavorito = new JLabel("");
		btnFavorito.setIcon(new ImageIcon(MenuBusquedaR.class.getResource("/ImagenesMenu/favoritos.png")));
		PanelReproduccion.add(btnFavorito);
		
		JPanel PanelBienvenida = new JPanel();
		PanelBienvenida.setBackground(new Color(18, 159, 186));
		add(PanelBienvenida, BorderLayout.NORTH);
		PanelBienvenida.setLayout(new BorderLayout(0, 0));
		
		MsgCancionesEncontradas = new JLabel("Se han encontrado " + miModelo.getSize() + " resultados");
		MsgCancionesEncontradas.setForeground(new Color(255, 255, 255));
		MsgCancionesEncontradas.setHorizontalAlignment(SwingConstants.CENTER);
		MsgCancionesEncontradas.setFont(new Font("Arial", Font.BOLD, 24));
		PanelBienvenida.add(MsgCancionesEncontradas, BorderLayout.CENTER);
		
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
		barraReproduccion.setEnabled(false);
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
				umu.tds.negocio.Cancion c = listaCanciones.getSelectedValue();
				if(c == null)
				{
					c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
				}
				if(c != null)
				{
					ControladorAppMusic.getInstancia().reproducirCancion(pausa,c);
					ControladorAppMusic.getInstancia().actualizarEstadoReproductor(pausa);
					if(pausa.equals("play"))
					{
						pausa = "pause";
					}
					else
					{
						pausa = "play";
					}
				}
			}
		});
		
		btnRedo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				umu.tds.negocio.Cancion c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
				if(c != null)
				{
					pausa = "stop";
					ControladorAppMusic.getInstancia().reproducirCancion(pausa,c);
					pausa = "play";
					ListModel<umu.tds.negocio.Cancion> modelo = listaCanciones.getModel();
					int j = 0;
					for (int i = 0; i < modelo.getSize(); i++) {
				            if (modelo.getElementAt(i).equals(c)) {
				                j = i;
				                i = modelo.getSize();
				           }
				     }
					j = (j-1) % modelo.getSize();
					if(j<0)
					{
						j = modelo.getSize()-1;
					}
					c = modelo.getElementAt(j);
					pausa = "play";
					ControladorAppMusic.getInstancia().reproducirCancion(pausa,c);
					ControladorAppMusic.getInstancia().actualizarEstadoReproductor(pausa);
					pausa = "pause";
				}
			}
		});
		
		btnForwa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean vengoOtroPanel = ControladorAppMusic.getInstancia.comprobarPaneles(this);
				umu.tds.negocio.Cancion c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
				if(c != null)
				{
					pausa = "stop";
					ControladorAppMusic.getInstancia().reproducirCancion(pausa,c);
					pausa = "play";
					ListModel<umu.tds.negocio.Cancion> modelo = listaCanciones.getModel();
					int j = 0;
					for (int i = 0; i < modelo.getSize(); i++) {
				            if (modelo.getElementAt(i).equals(c)) {
				                j = i;
				                i = modelo.getSize();
				           }
				     }
					j = (j+1) % modelo.getSize();
					c = modelo.getElementAt(j);
					pausa = "play";
					ControladorAppMusic.getInstancia().reproducirCancion(pausa,c);
					ControladorAppMusic.getInstancia().actualizarEstadoReproductor(pausa);
					pausa = "pause";
				}
			}
		});
		
		
		btnStop.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				umu.tds.negocio.Cancion c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
				pausa = "stop";
				if(c!=null)
				{
					ControladorAppMusic.getInstancia().reproducirCancion(pausa,c);
				}
				ControladorAppMusic.getInstancia().actualizarEstadoReproductor(pausa);
				pausa = "play";
				
			}
		});
		
		btnFavorito.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				umu.tds.negocio.Cancion cancionSeleccionada = listaCanciones.getSelectedValue();
				if(cancionSeleccionada != null)
				{
					if(cancionSeleccionada.isFavorita())
					{
						quitaIconoFavorito();
						cancionSeleccionada.setFavorita(false);
					}
					else
					{
						cambiaIconoFavorito();
						cancionSeleccionada.setFavorita(true);
					}
					ControladorAppMusic.getInstancia().actualizarFavorito(cancionSeleccionada);
				}
			}
		});
		
		listaCanciones.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				umu.tds.negocio.Cancion cancionSeleccionada = listaCanciones.getSelectedValue();
				if(cancionSeleccionada != null)
				{
					if(cancionSeleccionada.isFavorita())
					{
						cambiaIconoFavorito();
					}
					else
					{
						quitaIconoFavorito();
					}
				}
			}
		});
		
		
		
	}
	

	public void cargarCanciones(ArrayList<umu.tds.negocio.Cancion> canciones)
	{
		miModelo.actualizarLista(canciones);
		MsgCancionesEncontradas.setText("Se han encontrado " + miModelo.getSize() + " resultados");
	}
	
	public void cambiaIconoFavorito()
	{
		btnFavorito.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/favoritosAmarillo.png")));
	}
	
	public void quitaIconoFavorito()
	{
		btnFavorito.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/favoritos.png")));
	}

	@Override
	public void actualizarEstadoReproductor(String pausa) {
		this.pausa = pausa;	
		if(pausa == "play")
		{
			btnPlay.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/boton-de-pausa.png")));
		}
		else
		{
			btnPlay.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/triangulo-negro-flecha-derecha.png")));
		}
	}
	
}

