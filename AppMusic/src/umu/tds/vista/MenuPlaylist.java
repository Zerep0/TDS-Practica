package umu.tds.vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;

import umu.tds.controlador.ControladorAppMusic;
import umu.tds.helper.*;
import umu.tds.negocio.Cancion;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.util.LinkedList;
import umu.tds.negocio.IReproductorListener;

public class MenuPlaylist extends JPanel implements IReproductorListener{

	private static final String PLACEHOLDER_PLAYLIST = "Nombre de la Playlist";
	private static final long serialVersionUID = 1L;
	
	private JTextField creadorPlaylist;
	private Placeholder placeholder;
	private RenderizadorLetrasCabecera renderizadorLetras;
	private String opcionReproduccion;
	private MenuPlaylist menuPlaylist;
	private ListaModelo<String> miModelo;
	private JLabel btnPlay;
	private JTable tablaCancionesPlaylist;
	private JLabel msgDuracion = new JLabel("00:00");
	private JSlider barraReproduccion = new JSlider();
	private MiTablaPersonalizada modeloPlaylist;
	private String playlistActual;
	private boolean modoAleatorio;
	private static String PLAY = "play";
	private static String STOP = "stop";
	private static String PAUSE = "pause";
	
	/**
	 * Create the panel.
	 */
	
	public MenuPlaylist() {
		this.opcionReproduccion = PLAY;
		menuPlaylist = this;
		playlistActual = "";
		modoAleatorio = true;
		ControladorAppMusic.getInstancia().setMenuPlaylist(menuPlaylist, barraReproduccion, msgDuracion);
		initialize();
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	private void initialize() 
	{
		miModelo = new ListaModelo<String>();
		
		
		// USO DE HELPER
		placeholder = new Placeholder();
		
		
		setLayout(new BorderLayout(0, 0));
		

		JPanel PanelGestionaPlaylist = new JPanel();
		PanelGestionaPlaylist.setForeground(new Color(198, 255, 255));
		PanelGestionaPlaylist.setBackground(new Color(18, 156, 189));
		add(PanelGestionaPlaylist, BorderLayout.NORTH);
		GridBagLayout gbl_PanelGestionaPlaylist = new GridBagLayout();
		gbl_PanelGestionaPlaylist.columnWidths = new int[]{128, 206, 52, 27, 0};
		gbl_PanelGestionaPlaylist.rowHeights = new int[]{20, 0, 0, 10, 20, 0};
		gbl_PanelGestionaPlaylist.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_PanelGestionaPlaylist.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		PanelGestionaPlaylist.setLayout(gbl_PanelGestionaPlaylist);
		
		creadorPlaylist = new JTextField(PLACEHOLDER_PLAYLIST);
		creadorPlaylist.setFont(new Font("Arial", Font.ITALIC, 14));
		GridBagConstraints gbc_creadorPlaylist = new GridBagConstraints();
		gbc_creadorPlaylist.insets = new Insets(0, 0, 5, 5);
		gbc_creadorPlaylist.anchor = GridBagConstraints.WEST;
		gbc_creadorPlaylist.gridx = 1;
		gbc_creadorPlaylist.gridy = 1;
		PanelGestionaPlaylist.add(creadorPlaylist, gbc_creadorPlaylist);
		creadorPlaylist.setColumns(25);
		creadorPlaylist.setPreferredSize(new Dimension(150,30));
		
		JButton creaPlaylist = new JButton("");
		creaPlaylist.setIcon(new ImageIcon(MenuPlaylist.class.getResource("/ImagenesMenu/pagina.png")));
		creaPlaylist.setBorderPainted(false);
		creaPlaylist.setBorder(new EmptyBorder(0, 0, 0, 0));
		creaPlaylist.setBackground(new Color(18, 159, 186));
		GridBagConstraints gbc_creaPlaylist = new GridBagConstraints();
		gbc_creaPlaylist.insets = new Insets(0, 0, 5, 5);
		gbc_creaPlaylist.gridx = 2;
		gbc_creaPlaylist.gridy = 1;
		PanelGestionaPlaylist.add(creaPlaylist, gbc_creaPlaylist);
		
		JButton eliminarPlaylist = new JButton("");
		eliminarPlaylist.setIcon(new ImageIcon(MenuPlaylist.class.getResource("/ImagenesMenu/no-disponible.png")));
		eliminarPlaylist.setBorderPainted(false);
		eliminarPlaylist.setBackground(new Color(18, 159, 186));
		GridBagConstraints gbc_eliminarPlaylist = new GridBagConstraints();
		gbc_eliminarPlaylist.insets = new Insets(0, 0, 5, 0);
		gbc_eliminarPlaylist.gridx = 3;
		gbc_eliminarPlaylist.gridy = 1;
		PanelGestionaPlaylist.add(eliminarPlaylist, gbc_eliminarPlaylist);
		

		
		JPanel PanelReproduccion = new JPanel();
		PanelReproduccion.setBackground(new Color(18, 159, 186));
		add(PanelReproduccion, BorderLayout.SOUTH);
		PanelReproduccion.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_botones = new JPanel();
		panel_botones.setBackground(new Color(18, 156, 189));
		PanelReproduccion.add(panel_botones, BorderLayout.CENTER);
		
		JLabel btnStop = new JLabel("");
		btnStop.setIcon(new ImageIcon(MenuPlaylist.class.getResource("/ImagenesMenu/cuadrado.png")));
		panel_botones.add(btnStop);
		
		JLabel btnRedo = new JLabel("");
		btnRedo.setIcon(new ImageIcon(MenuPlaylist.class.getResource("/ImagenesMenu/atras.png")));
		panel_botones.add(btnRedo);
		
		btnPlay = new JLabel("");
		btnPlay.setIcon(new ImageIcon(MenuPlaylist.class.getResource("/ImagenesMenu/triangulo-negro-flecha-derecha.png")));
		panel_botones.add(btnPlay);
		
		JLabel btnForwa = new JLabel("");
		btnForwa.setIcon(new ImageIcon(MenuPlaylist.class.getResource("/ImagenesMenu/siguiente.png")));
		panel_botones.add(btnForwa);
		
		JLabel btnRandom = new JLabel("");
		btnRandom.setIcon(new ImageIcon(MenuPlaylist.class.getResource("/ImagenesMenu/repetir.png")));
		panel_botones.add(btnRandom);
		
		JLabel btnEliminarCanciones = new JLabel("");
		btnEliminarCanciones.setIcon(new ImageIcon(MenuPlaylist.class.getResource("/ImagenesMenu/eliminar.png")));
		panel_botones.add(btnEliminarCanciones);
		btnEliminarCanciones.setVisible(false);
		
		
	    modeloPlaylist = new MiTablaPersonalizada();
		tablaCancionesPlaylist = new JTable(modeloPlaylist);
		tablaCancionesPlaylist.setSelectionBackground(new Color(0,128,255));
		tablaCancionesPlaylist.setSelectionForeground(Color.WHITE);
		tablaCancionesPlaylist.setBackground(new Color(193, 255, 245));
		tablaCancionesPlaylist.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		
		tablaCancionesPlaylist.setAutoCreateRowSorter(true);
		JTableHeader header = tablaCancionesPlaylist.getTableHeader();
		header.setBackground(new Color(193, 255, 245));
		
		// HELPER
		renderizadorLetras = new RenderizadorLetrasCabecera(header.getDefaultRenderer(),Color.BLACK);;
		header.setDefaultRenderer(renderizadorLetras);
		 
		
		
		placeholder.crearPlaceholderText(creadorPlaylist, PLACEHOLDER_PLAYLIST);
		
		JPanel PanelCentral = new JPanel();
		PanelCentral.setBackground(new Color(18, 156, 189));
		add(PanelCentral, BorderLayout.CENTER);
		PanelCentral.setLayout(new BorderLayout(0, 0));
		
		JPanel PanelSlider = new JPanel();
		PanelSlider.setBackground(new Color(18, 156, 189));
		PanelCentral.add(PanelSlider, BorderLayout.SOUTH);
		
		barraReproduccion.setPreferredSize(new Dimension(400, 26));
		barraReproduccion.setBackground(new Color(18, 159, 186));
		barraReproduccion.setValue(0);
		barraReproduccion.setUI(new CustomSliderUI(barraReproduccion));
		barraReproduccion.setEnabled(false);
		PanelSlider.add(barraReproduccion);
		
		msgDuracion.setForeground(new Color(255, 255, 255));
		msgDuracion.setFont(new Font("Arial", Font.BOLD, 14));
		PanelSlider.add(msgDuracion);
		
		JScrollPane PanelPlaylist = new JScrollPane();
		PanelCentral.add(PanelPlaylist, BorderLayout.WEST);
		
		AlineamientoLista centro = new AlineamientoLista("centro");
		JList listaPlaylist = new JList();
		listaPlaylist.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		listaPlaylist.setBackground(new Color(193, 255, 245));
		listaPlaylist.setPreferredSize(new Dimension(100, 0));
		listaPlaylist.setCellRenderer(centro);
		listaPlaylist.setSelectionBackground(new Color(0,128,255));
		listaPlaylist.setSelectionForeground(Color.WHITE);
		listaPlaylist.setModel(miModelo);
		PanelPlaylist.setViewportView(listaPlaylist);
		anadirPlaylist("Favoritas");
		
		JScrollPane PanelTablaCanciones = new JScrollPane(tablaCancionesPlaylist);
		PanelTablaCanciones.setBackground(new Color(193, 255, 245));
		PanelTablaCanciones.getViewport().setBackground(new Color(128, 255, 236));
		PanelCentral.add(PanelTablaCanciones, BorderLayout.CENTER);
		
		creaPlaylist.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(!creadorPlaylist.getFont().isItalic())
				{
					int respuesta = JOptionPane.showConfirmDialog(null, "¿Quieres crear la playlist?", "Confirmación", JOptionPane.YES_NO_OPTION);

			        if (respuesta == JOptionPane.YES_OPTION) {
						if(ControladorAppMusic.getInstancia().registrarPlaylist(creadorPlaylist))
						{
							if(anadirPlaylist(creadorPlaylist.getText()))
							{
						        JOptionPane.showMessageDialog(null, "La lista se ha creado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
							}
							else
							{
						        JOptionPane.showMessageDialog(null, "No se ha podido crear la lista", "Éxito", JOptionPane.INFORMATION_MESSAGE);
							}
							creadorPlaylist.setText(PLACEHOLDER_PLAYLIST);
					        creadorPlaylist.setFont(new Font("Arial", Font.ITALIC, 14));
					        creadorPlaylist.setText(PLACEHOLDER_PLAYLIST);
						}
			        }
				}else
				{
					JOptionPane.showMessageDialog(null, "Introduce un nombre válido de playlist", "Sin información", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		eliminarPlaylist.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index = listaPlaylist.getSelectedIndex();
				if(index>=0 && index<miModelo.getSize())
				{
					String nombrePlaylistSel = miModelo.getElementAt(index);
					if(ControladorAppMusic.getInstancia().eliminarPlaylistSelec(nombrePlaylistSel))
					{
						quitarPlaylist(nombrePlaylistSel);
						playlistActual = "";
						actualizarTabla();
					}
				}
				if(ControladorAppMusic.getInstancia().eliminarPlaylist(creadorPlaylist))
				{
					quitarPlaylist(creadorPlaylist.getText());
					playlistActual = "";
					actualizarTabla();
				}
				
			}
		});
		
		listaPlaylist.addListSelectionListener(new ListSelectionListener() {
	        @Override
	        public void valueChanged(ListSelectionEvent e) {
	            if (!e.getValueIsAdjusting()) {
	                // Acciones que ocurren cuando se selecciona un elemento
	            	String selectedValue = (String) listaPlaylist.getSelectedValue();
	                if (selectedValue != null) {
	                    System.out.println("Elemento seleccionado: " + selectedValue);
	                    playlistActual = selectedValue;
	                    //Realizar acciones adicionales aquí
	                    LinkedList<Cancion> canciones = ControladorAppMusic.getInstancia().getPlaylistCanciones(selectedValue);
	                    modeloPlaylist.actualizarTabla(canciones, selectedValue);
	                    btnEliminarCanciones.setVisible(false);
	                    for (int i = 0; i<canciones.size();i++) {
	                    	modeloPlaylist.quitarMarcada(i);
						}
	                    
	                }
	            }
	        }
	    });
		
		tablaCancionesPlaylist.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e)
        	{
        		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
    				umu.tds.negocio.Cancion c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
    				if(c!=null)
    				{
    					opcionReproduccion = STOP;
    					ControladorAppMusic.getInstancia().reproducirCancion(opcionReproduccion,c);
    				}
    				ControladorAppMusic.getInstancia().actualizarEstadoReproductor(opcionReproduccion);
    				opcionReproduccion = PLAY; 	
                        ControladorAppMusic.getInstancia().actualizarPanelReproduccion(menuPlaylist);
                        MiTablaPersonalizada tabla = (MiTablaPersonalizada) tablaCancionesPlaylist.getModel();
        				c = tabla.getCancionAt(tablaCancionesPlaylist.getSelectedRow());
        				if(c == null)
        				{
        					c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
        				}
        				if(c != null)
        				{
        					ControladorAppMusic.getInstancia().reproducirCancion(opcionReproduccion,c);
        					ControladorAppMusic.getInstancia().actualizarEstadoReproductor(opcionReproduccion);
        					if(opcionReproduccion.equals(PLAY))
        					{
        						opcionReproduccion = PAUSE;
        					}
        					else
        					{
        						opcionReproduccion = PLAY;
        					}
        				}
                }
        	}
        });
		
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MiTablaPersonalizada tabla = (MiTablaPersonalizada) tablaCancionesPlaylist.getModel();
				umu.tds.negocio.Cancion c = tabla.getCancionAt(tablaCancionesPlaylist.getSelectedRow());
				if(c == null)
				{
					c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
				}
				if(c != null)
				{
					ControladorAppMusic.getInstancia().actualizarEstadoReproductor(opcionReproduccion);
					ControladorAppMusic.getInstancia().reproducirCancion(opcionReproduccion,c);
					if(opcionReproduccion.equals(PLAY))
					{
						opcionReproduccion = PAUSE;
					}
					else
					{
						opcionReproduccion = PLAY;
					}
				}
				ControladorAppMusic.getInstancia().actualizarPanelReproduccion(menuPlaylist);
			}
		});
		
		btnRedo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Cancion c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
				
				if(c != null)
				{
					opcionReproduccion = STOP;
					ControladorAppMusic.getInstancia().reproducirCancion(opcionReproduccion,c);
					opcionReproduccion = PLAY;
				} 
				MiTablaPersonalizada tabla = (MiTablaPersonalizada) tablaCancionesPlaylist.getModel();
				LinkedList<Cancion> canciones = tabla.getCanciones();
				boolean vengoOtroPanel = ControladorAppMusic.getInstancia().comprobarPaneles(menuPlaylist);
				if(vengoOtroPanel && canciones.size() > 0)
				{
					tablaCancionesPlaylist.setRowSelectionInterval(canciones.size()-1, canciones.size()-1);
				}
				int siguienteIndice = vengoOtroPanel ? canciones.size() - 1
						: tablaCancionesPlaylist.getSelectedRow() - 1;
		        int totalCanciones = canciones.size();
		        if (totalCanciones > 0) {
		        	if(modoAleatorio)
		        	{
		        		siguienteIndice = vengoOtroPanel ? siguienteIndice : 
		        			Aleatorio.generarAletorio(0, totalCanciones, siguienteIndice+1);
		        	}else
		        	{
		        		if(siguienteIndice < 0)
			        	{
							siguienteIndice = canciones.size() - 1;
			        	}
		        	}
		            c = tabla.getCancionAt(siguienteIndice);
		            tablaCancionesPlaylist.setRowSelectionInterval(siguienteIndice, siguienteIndice);
		            opcionReproduccion = PLAY;
					ControladorAppMusic.getInstancia().reproducirCancion(opcionReproduccion,c);
					ControladorAppMusic.getInstancia().actualizarEstadoReproductor(opcionReproduccion);
					opcionReproduccion = PAUSE;  
		        }
			}
		});
		
		
		btnForwa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Cancion c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
				if(c != null)
				{
					opcionReproduccion = STOP;
					ControladorAppMusic.getInstancia().reproducirCancion(opcionReproduccion,c);
					opcionReproduccion = PLAY;
				}
				 
				MiTablaPersonalizada tabla = (MiTablaPersonalizada) tablaCancionesPlaylist.getModel();
				LinkedList<Cancion> canciones = tabla.getCanciones();
				boolean vengoOtroPanel = ControladorAppMusic.getInstancia().comprobarPaneles(menuPlaylist);
				if(vengoOtroPanel && canciones.size() > 0)
				{
					 tablaCancionesPlaylist.setRowSelectionInterval(0, 0);
				}
				int indiceActual = tablaCancionesPlaylist.getSelectedRow();
		        int totalCanciones = canciones.size();
		        int siguienteIndice;
		        if (totalCanciones > 0) {
		        	if(modoAleatorio)
		        		siguienteIndice = vengoOtroPanel ? 0 : Aleatorio.generarAletorio(0, totalCanciones, indiceActual);
		        	else siguienteIndice = vengoOtroPanel ? 0 :(indiceActual + 1) % totalCanciones;
		        	
		            c = tabla.getCancionAt(siguienteIndice);
		            tablaCancionesPlaylist.setRowSelectionInterval(siguienteIndice, siguienteIndice);
		            opcionReproduccion = PLAY;
					ControladorAppMusic.getInstancia().reproducirCancion(opcionReproduccion,c);
					ControladorAppMusic.getInstancia().actualizarEstadoReproductor(opcionReproduccion);
					opcionReproduccion = PAUSE;
		        }
				
			}
		});
		
		
		btnStop.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				umu.tds.negocio.Cancion c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
				opcionReproduccion = STOP;
				if(c!=null)
				{
					ControladorAppMusic.getInstancia().reproducirCancion(opcionReproduccion,c);
				}
				ControladorAppMusic.getInstancia().actualizarEstadoReproductor(opcionReproduccion);
				opcionReproduccion = PLAY;
				
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
		
		tablaCancionesPlaylist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedColumn = tablaCancionesPlaylist.getSelectedColumn();
                if(selectedColumn == 3)
                {
			        if(modeloPlaylist.isCancionesMarcadas())
			        		btnEliminarCanciones.setVisible(true);
			        else btnEliminarCanciones.setVisible(false);
                }
            }
        });
		
		btnEliminarCanciones.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				
				String selectedValue = (String) listaPlaylist.getSelectedValue();
				if(selectedValue.equals("Favoritas"))
				{
					modeloPlaylist.listaMarcadas()
					.forEach(c -> ControladorAppMusic.getInstancia().actualizarFavorito(false, c));
					ControladorAppMusic.getInstancia().cambiarImagenFavorito();
				}
				
				Cancion c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
				if(c != null && modeloPlaylist.listaMarcadas().contains(c))
				{
					ControladorAppMusic.getInstancia().reproducirCancion(STOP, c);
				}
				modeloPlaylist.borrarCanciones();
				btnEliminarCanciones.setVisible(false);
				
				if(modeloPlaylist.getCanciones().size() > 0)
					tablaCancionesPlaylist.setRowSelectionInterval(0, 0);
			}
		});
		
	} 

	@Override
	public void actualizarEstadoReproductor(String pausa) {
		this.opcionReproduccion = pausa;	
		if(pausa == PLAY)
		{
			btnPlay.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/boton-de-pausa.png")));
		}
		else
		{
			btnPlay.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/triangulo-negro-flecha-derecha.png")));
		}
	}
	
	
	public boolean anadirPlaylist(String playlist)
	{
		return miModelo.add(playlist);
	}
	
	public void quitarPlaylist(String playlist)
	{
		miModelo.remove(playlist);
	}
	
	
	
	public void cancionAlFinalizar()
	{
		int indiceActual = tablaCancionesPlaylist.getSelectedRow();
		System.out.println(indiceActual);
		int totalCanciones = tablaCancionesPlaylist.getModel().getRowCount();
        if (totalCanciones > 0) {
        	int siguienteIndice;
        	if(modoAleatorio)
        		siguienteIndice = Aleatorio.generarAletorio(0, totalCanciones, indiceActual);
        	else siguienteIndice = (indiceActual + 1) % totalCanciones;	
            Cancion c = ((MiTablaPersonalizada) tablaCancionesPlaylist.getModel()).getCancionAt(siguienteIndice); 
            tablaCancionesPlaylist.setRowSelectionInterval(siguienteIndice, siguienteIndice);
            opcionReproduccion = PLAY;
			ControladorAppMusic.getInstancia().reproducirCancion(opcionReproduccion,c);
			ControladorAppMusic.getInstancia().actualizarEstadoReproductor(opcionReproduccion);
			opcionReproduccion = PAUSE;
        }
	}
	
	public void actualizarTabla()
	{
		if(!playlistActual.equals(""))
		{
			LinkedList<Cancion> canciones = ControladorAppMusic.getInstancia().getPlaylistCanciones(playlistActual);
	        modeloPlaylist.actualizarTabla(canciones, playlistActual);
		}else
		{
			modeloPlaylist.actualizarTabla(new LinkedList<Cancion>(), playlistActual);
		}
		 
	}
	

	
}
