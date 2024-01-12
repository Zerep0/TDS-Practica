package umu.tds.vista;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.BorderFactory;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicSliderUI;

import umu.tds.controlador.ControladorAppMusic;
import umu.tds.helper.Aleatorio;
import umu.tds.helper.AlineamientoLista;
import umu.tds.helper.CustomSliderUI;
import umu.tds.negocio.Cancion;
import umu.tds.negocio.IReproductorListener;
import umu.tds.observer.IUsuarioListener;
import umu.tds.observer.UsuarioEvent;

import javax.swing.JSlider;
import java.awt.Dimension;
import javax.swing.SwingConstants;

public class MenuHome extends JPanel implements IReproductorListener,IUsuarioListener{

	private static final long serialVersionUID = 1L;
	private AlineamientoLista alineamientoListaMenu;
	private String pausa;
	private JLabel MsgBienvenida;
	private ListaModelo<Cancion> miModelo;
	private JLabel btnStop;
	private JLabel btnRedo;
	private JLabel btnPlay;
	private JLabel btnForwa;
	private JLabel btnRandom;
	private MenuHome menuHome;
	private JList<Cancion> listaCanciones;
	private JSlider barraReproduccion = new JSlider();
	private JLabel msgDuracion = new JLabel("00:00");
	private boolean modoAleatorio;
	/**
	 * Create the panel.
	 */
	public MenuHome() {
		this.pausa = "play";
		miModelo = new ListaModelo<Cancion>();
		menuHome = this;
		modoAleatorio = false;
		ControladorAppMusic.getInstancia().setMenuHome(menuHome, barraReproduccion, msgDuracion);
		initialize();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize()
	{
		// HELPER
		alineamientoListaMenu = new AlineamientoLista("izquierda");
		
		
		setBackground(new Color(18, 159, 186));
		setLayout(new BorderLayout(0, 0));
		
		JPanel PanelReproduccion = new JPanel();
		PanelReproduccion.setBackground(new Color(18, 159, 186));
		add(PanelReproduccion, BorderLayout.SOUTH);
		
		btnStop = new JLabel("");
		btnStop.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/cuadrado.png")));
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
		btnRandom.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/repetir.png")));
		PanelReproduccion.add(btnRandom);
		
		JPanel PanelBienvenida = new JPanel();
		PanelBienvenida.setBackground(new Color(18, 159, 186));
		add(PanelBienvenida, BorderLayout.NORTH);
		PanelBienvenida.setLayout(new BorderLayout(0, 0));
		
		MsgBienvenida = new JLabel("Sigue escuchando: ");
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
		
		JPanel PanelRecientes = new JPanel();
		PanelRecientes.setBackground(new Color(18, 159, 186));
		add(PanelRecientes, BorderLayout.CENTER);
		PanelRecientes.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		PanelRecientes.add(scrollPane, BorderLayout.CENTER);
		
		listaCanciones = new JList();
		listaCanciones.setBackground(new Color(193, 255, 245));
		listaCanciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaCanciones.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		listaCanciones.setSelectionBackground(new Color(0,128,255));
		listaCanciones.setSelectionForeground(Color.WHITE);
		listaCanciones.setModel(miModelo);
		scrollPane.setViewportView(listaCanciones);
		listaCanciones.setBorder(BorderFactory.createLineBorder(Color.black));
		listaCanciones.setCellRenderer(alineamientoListaMenu);
		
		JPanel LayoutTiempo = new JPanel();
		LayoutTiempo.setBackground(new Color(18, 159, 186));
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
		
		
		barraReproduccion.setBackground(new Color(18, 156, 189));
		barraReproduccion.setPreferredSize(new Dimension(400, 26));
		barraReproduccion.setValue(0);
		barraReproduccion.setEnabled(false);
		barraReproduccion.setUI(new CustomSliderUI(barraReproduccion));
		subPanelTiempo.add(barraReproduccion, BorderLayout.NORTH);
		
		
		msgDuracion.setForeground(new Color(255, 255, 255));
		msgDuracion.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_msgDuracion = new GridBagConstraints();
		gbc_msgDuracion.gridx = 3;
		gbc_msgDuracion.gridy = 0;
		LayoutTiempo.add(msgDuracion, gbc_msgDuracion);
		
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
                        ControladorAppMusic.getInstancia().actualizarPanelReproduccion(menuHome);
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
				umu.tds.negocio.Cancion c = listaCanciones.getSelectedValue();
				if(c == null)
				{
					c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
				}
				if(c != null)
				{
					ControladorAppMusic.getInstancia().actualizarEstadoReproductor(pausa);
					ControladorAppMusic.getInstancia().reproducirCancion(pausa,c);
					if(pausa.equals("play"))
					{
						pausa = "pause";
					}
					else
					{
						pausa = "play";
					}
				}
				ControladorAppMusic.getInstancia().actualizarPanelReproduccion(menuHome);
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
				boolean vengoOtroPanel = ControladorAppMusic.getInstancia().comprobarPaneles(menuHome);
				if(vengoOtroPanel && listaCanciones.getModel().getSize() > 0)
				{
					 listaCanciones.setSelectedIndex(listaCanciones.getModel().getSize()-1);
				}
				int siguienteIndice = vengoOtroPanel ? listaCanciones.getModel().getSize() - 1 
						: listaCanciones.getSelectedIndex() - 1;
		        int totalCanciones = listaCanciones.getModel().getSize();
		        if (totalCanciones > 0) {
		        	if(modoAleatorio)
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
		            c = (Cancion) miModelo.getElementAt(siguienteIndice);
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
				boolean vengoOtroPanel = ControladorAppMusic.getInstancia().comprobarPaneles(menuHome);
				if(vengoOtroPanel && listaCanciones.getModel().getSize() > 0)
				{
					 listaCanciones.setSelectedIndex(0);
				}
				int indiceActual = listaCanciones.getSelectedIndex();
		        int totalCanciones = listaCanciones.getModel().getSize();
		        int siguienteIndice;
		        if (totalCanciones > 0) {
		        	if(modoAleatorio)
		        		siguienteIndice = vengoOtroPanel ? 0 : Aleatorio.generarAletorio(0, totalCanciones, indiceActual);
		        	else siguienteIndice = vengoOtroPanel ? 0 : (indiceActual + 1) % totalCanciones;
		        	
		            listaCanciones.setSelectedIndex(siguienteIndice);
		            c = (Cancion) miModelo.getElementAt(siguienteIndice);
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
		
		btnRandom.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(modoAleatorio == true)
				{
					modoAleatorio = false;
					btnRandom.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/repetir.png")));
				}
				else
				{
					modoAleatorio = true;
					btnRandom.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/aleatorio.png")));
				}
			}
		});
		
		ControladorAppMusic.getInstancia().addUsuarioListener(this);
	}
	
	public void refrescarRecientes(LinkedList<Cancion> cancionesRecientes)
	{
		// refrescarRecientes
		miModelo.actualizarLista(new ArrayList<Cancion>(cancionesRecientes));
	}
	

	static class SlideCustomizado extends BasicSliderUI  {
		public SlideCustomizado(JSlider slider) {
	        super(slider);
	    }

	    public void paintTrack(Graphics g) {
	        Rectangle trackBounds = trackRect;
	        g.setColor(Color.RED); // Cambia el color de la barra a rojo
	        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
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
        	if(modoAleatorio)
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

	@Override
	public void actualizar(UsuarioEvent e) {
		MsgBienvenida.setText(MsgBienvenida.getText() + e.getNombreUsuario());
		
	}


	
	
	
	
	
}
