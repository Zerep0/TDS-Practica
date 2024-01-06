package umu.tds.vista;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import umu.tds.controlador.ControladorAppMusic;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.event.ListSelectionEvent;

import umu.tds.negocio.Cancion;
import  umu.tds.negocio.IReproductorListener;
import javax.swing.border.EmptyBorder;
import umu.tds.helper.*;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.UIManager;

public class MenuBusquedaR extends JPanel implements IReproductorListener{

	private AlineamientoLista alinamientoListaBusqueda;
	private static final long serialVersionUID = 1L;
	private CardLayout menuBusqueda;
	private String ruta;
	private String pausa;
	private JList<umu.tds.negocio.Cancion> listaCanciones;
	private ListaModelo<Cancion> miModelo;
	private JLabel MsgCancionesEncontradas;
	private JLabel btnFavorito;
	private JLabel btnStop;
	private JLabel btnRedo;
	private JLabel btnPlay;
	private JLabel btnForwa;
	private JLabel btnRandom;
	private MenuBusquedaR menuBusquedaR;
	private JSlider barraReproduccion = new JSlider();
	private JLabel msgDuracion = new JLabel("00:00");
	private boolean modoAletorio;
	
	/**
	 * Create the panel.
	 */
	public MenuBusquedaR(CardLayout menuBusqueda, String ruta) {
		this.menuBusqueda = menuBusqueda;
		this.ruta = ruta;
		this.pausa = "play";
		menuBusquedaR = this;
		modoAletorio = true;
		ControladorAppMusic.getInstancia().setMenuBusquedaR(menuBusquedaR, barraReproduccion,msgDuracion);
		initialize();
	}
	
	@SuppressWarnings({})
	private void initialize()
	{
		// HELPER
		alinamientoListaBusqueda = new AlineamientoLista("izquierda");
		miModelo = new ListaModelo<Cancion>();
		
		setBackground(new Color(18, 159, 186));
		setLayout(new BorderLayout(0, 0));
		
		JPanel PanelReproduccion = new JPanel();
		PanelReproduccion.setBackground(new Color(18, 156, 189));
		add(PanelReproduccion, BorderLayout.SOUTH);
		
		JLabel btnAñadirPlaylists = new JLabel("");
		btnAñadirPlaylists.setIcon(new ImageIcon(MenuBusquedaR.class.getResource("/ImagenesMenu/pagina.png")));
		PanelReproduccion.add(btnAñadirPlaylists);
		
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
		
		
		listaCanciones = new JList<Cancion>();
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
		barraReproduccion.setEnabled(false);
		barraReproduccion.setBorder(new EmptyBorder(0, 0, 0, 0));
		barraReproduccion.setBackground(new Color(18, 156, 189));
		barraReproduccion.setPreferredSize(new Dimension(400, 26));
		subPanelTiempo.add(barraReproduccion, BorderLayout.NORTH);
		barraReproduccion.setValue(0);
		barraReproduccion.setUI(new CustomSliderUI(barraReproduccion));
		
		
        

		msgDuracion.setForeground(new Color(255, 255, 255));
		msgDuracion.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_msgDuracion = new GridBagConstraints();
		gbc_msgDuracion.gridx = 3;
		gbc_msgDuracion.gridy = 0;
		LayoutTiempo.add(msgDuracion, gbc_msgDuracion);
		
		JPanel PanelPlaylist = new JPanel();
		add(PanelPlaylist, BorderLayout.WEST);
		PanelPlaylist.setLayout(new GridLayout(0, 1, 0, 0));
		
        lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				menuBusqueda.show(MenuBusquedaR.this.getParent(), ruta);
			}
		});
		
        listaCanciones.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e)
        	{
        		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
    				umu.tds.negocio.Cancion c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
    				if(c!=null)
    				{
    					pausa = "stop";
    					ControladorAppMusic.getInstancia().reproducirCancion(pausa,c);
    				}
    				ControladorAppMusic.getInstancia().actualizarEstadoReproductor(pausa);
    				pausa = "play";
                        ControladorAppMusic.getInstancia().actualizarPanelReproduccion(menuBusquedaR);
        				c = listaCanciones.getSelectedValue();
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
        	}
        });
        
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ControladorAppMusic.getInstancia().actualizarPanelReproduccion(menuBusquedaR);
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
				Cancion c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
				if(c != null)
				{
					pausa = "stop";
					ControladorAppMusic.getInstancia().reproducirCancion(pausa,c);
					pausa = "play";
				}
				boolean vengoOtroPanel = ControladorAppMusic.getInstancia().comprobarPaneles(menuBusquedaR);
				if(vengoOtroPanel && listaCanciones.getModel().getSize() > 0)
				{
					 listaCanciones.setSelectedIndex(listaCanciones.getModel().getSize()-1);
				}
				int siguienteIndice = vengoOtroPanel ? listaCanciones.getModel().getSize() - 1
						: listaCanciones.getSelectedIndex() - 1;	
		        int totalCanciones = listaCanciones.getModel().getSize();
		        if (totalCanciones > 0) {
					if(modoAletorio)
					{
						siguienteIndice = vengoOtroPanel ? siguienteIndice : 
							Aleatorio.generarAletorio(0, totalCanciones, siguienteIndice+1);
					}
					else
					{
						if(siguienteIndice < 0)
			        	{
							siguienteIndice = listaCanciones.getModel().getSize()-1;
			        	}
					}
					listaCanciones.setSelectedIndex(siguienteIndice);
		            c = miModelo.getElementAt(siguienteIndice);
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
				Cancion c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
				if(c != null)
				{
					pausa = "stop";
					ControladorAppMusic.getInstancia().reproducirCancion(pausa,c);
					pausa = "play";
				}
				boolean vengoOtroPanel = ControladorAppMusic.getInstancia().comprobarPaneles(menuBusquedaR);
				if(vengoOtroPanel && listaCanciones.getModel().getSize() > 0)
				{
					 listaCanciones.setSelectedIndex(0);
				}
				int indiceActual = listaCanciones.getSelectedIndex();
		        int totalCanciones = listaCanciones.getModel().getSize();
		        int siguienteIndice;
		        if (totalCanciones > 0) {
		        	if(modoAletorio)
		        		siguienteIndice = vengoOtroPanel ? 0 : Aleatorio.generarAletorio(0, totalCanciones, indiceActual);
		        	else siguienteIndice = vengoOtroPanel ? 0 : (indiceActual + 1) % totalCanciones;

		        	listaCanciones.setSelectedIndex(siguienteIndice);
		            c = miModelo.getElementAt(siguienteIndice);
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
				if(c!=null)
				{
					pausa = "stop";
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
					boolean esFavorita;
					if(ControladorAppMusic.getInstancia().isFavorita(cancionSeleccionada))
					{
						quitaIconoFavorito();
						esFavorita = false;
					}
					else
					{
						cambiaIconoFavorito();
						esFavorita = true;
					}
					ControladorAppMusic.getInstancia().actualizarFavorito(esFavorita, cancionSeleccionada);
					
				}
			}
		});
		
		listaCanciones.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				umu.tds.negocio.Cancion cancionSeleccionada = listaCanciones.getSelectedValue();
				if(cancionSeleccionada != null)
				{
					if(ControladorAppMusic.getInstancia().isFavorita(cancionSeleccionada))
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
		
		btnAñadirPlaylists.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Cancion c = listaCanciones.getSelectedValue();
				if(c != null)
				{
					abrirVentanaModal(c, ControladorAppMusic.getInstancia().getNombresPlaylist());
				}
			}
		});
		
		btnRandom.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
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
	public void entrarVentana()
	{
		if(miModelo.getSize() > 0)
		{
			int indice = listaCanciones.getSelectedIndex();
			if(ControladorAppMusic.getInstancia().isFavorita(miModelo.getElementAt(indice == -1 ? 0 : indice)))
			{
				cambiaIconoFavorito();
			}
			else
			{
				quitaIconoFavorito();
			}
		}
		
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
	
	public void cancionAlFinalizar()
	{
		int indiceActual = listaCanciones.getSelectedIndex();
        int totalCanciones = listaCanciones.getModel().getSize();
        if (totalCanciones > 0) {
        	int siguienteIndice;
        	if(modoAletorio)
        		siguienteIndice = Aleatorio.generarAletorio(0, totalCanciones, indiceActual);
        	else siguienteIndice = (indiceActual + 1) % totalCanciones;
        	
            listaCanciones.setSelectedIndex(siguienteIndice);
            Cancion c = (Cancion) miModelo.getElementAt(siguienteIndice);
            pausa = "play";
			ControladorAppMusic.getInstancia().reproducirCancion(pausa,c);
			ControladorAppMusic.getInstancia().actualizarEstadoReproductor(pausa);
			pausa = "pause";
        }
	}
	
	 private void abrirVentanaModal(Cancion c, ArrayList<String> canciones) {
	        // Crear un JDialog modal con MenuBusquedaR como propietario
	        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Panel Seleccion", true);
	        
	        // Configurar el contentPane del JDialog con una instancia de PanelSeleccionPlaylist
	        PanelSeleccionPlaylist panelSeleccion = new PanelSeleccionPlaylist(c,canciones, dialog);
	        dialog.setContentPane(panelSeleccion);

	        // Configurar otras propiedades del JDialog según sea necesario
	        dialog.setSize(400, 300);
	        dialog.setLocationRelativeTo(this);
	        dialog.setResizable(false);
	        dialog.setVisible(true);
	    }
}

