package umu.tds.vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;

import umu.tds.controlador.ControladorAppMusic;
import umu.tds.helper.*;
import umu.tds.negocio.Cancion;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;

import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;

import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import umu.tds.negocio.IReproductorListener;
import java.awt.Component;
import java.awt.ComponentOrientation;

public class MenuPlaylist extends JPanel implements IReproductorListener{

	private static final String PLACEHOLDER_PLAYLIST = "Nombre de la Playlist";
	private static final long serialVersionUID = 1L;
	
	private JTextField creadorPlaylist;
	private Placeholder placeholder;
	private RenderizadorLetrasCabecera renderizadorLetras;
	private String pausa;
	private MenuPlaylist menuPlaylist;
	private ListaModelo<String> miModelo;
	private JLabel btnPlay;
	private JTable tablaCancionesPlaylist;
	private JLabel msgDuracion = new JLabel("00:00");
	private JSlider barraReproduccion = new JSlider();
	private MiTablaPersonalizada modeloPlaylist;
	private String playlistActual;
	/**
	 * Create the panel.
	 */
	
	public MenuPlaylist() {
		this.pausa = "play";
		menuPlaylist = this;
		playlistActual = "";
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
		btnRandom.setIcon(new ImageIcon(MenuPlaylist.class.getResource("/ImagenesMenu/aleatorio.png")));
		panel_botones.add(btnRandom);
		
		JLabel btnEliminarCanciones = new JLabel("");
		btnEliminarCanciones.setIcon(new ImageIcon(MenuPlaylist.class.getResource("/ImagenesMenu/pagina.png")));
		panel_botones.add(btnEliminarCanciones);
		btnEliminarCanciones.setVisible(false);
		
		
	    modeloPlaylist = new MiTablaPersonalizada();
		tablaCancionesPlaylist = new JTable(modeloPlaylist);	
		
		tablaCancionesPlaylist.setAutoCreateRowSorter(true);
		JTableHeader header = tablaCancionesPlaylist.getTableHeader();
		
		// HELPER
		renderizadorLetras = new RenderizadorLetrasCabecera(header.getDefaultRenderer(), new Font("Arial", Font.BOLD, 14),Color.BLACK);;
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
		barraReproduccion.setBackground(new Color(18, 156, 189));
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
		listaPlaylist.setPreferredSize(new Dimension(100, 0));
		listaPlaylist.setCellRenderer(centro);
		listaPlaylist.setModel(miModelo);
		PanelPlaylist.setViewportView(listaPlaylist);
		anadirPlaylist("Favoritas");
		
		JScrollPane PanelTablaCanciones = new JScrollPane(tablaCancionesPlaylist);
		PanelCentral.add(PanelTablaCanciones, BorderLayout.CENTER);
		
		creaPlaylist.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
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
					}
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
					}
				}
				if(ControladorAppMusic.getInstancia().eliminarPlaylist(creadorPlaylist))
				{
					quitarPlaylist(creadorPlaylist.getText());
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
	                    btnEliminarCanciones.setVisible(false);
	                    for (int i = 0; i<canciones.size();i++) {
	                    	modeloPlaylist.quitarMarcada(i);
						}
	                    modeloPlaylist.actualizarTabla(canciones, selectedValue);
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
    					pausa = "stop";
    					ControladorAppMusic.getInstancia().reproducirCancion(pausa,c);
    				}
    				ControladorAppMusic.getInstancia().actualizarEstadoReproductor(pausa);
    				pausa = "play"; 	
                        ControladorAppMusic.getInstancia().actualizarPanelReproduccion(menuPlaylist);
                        MiTablaPersonalizada tabla = (MiTablaPersonalizada) tablaCancionesPlaylist.getModel();
        				c = tabla.getCancionAt(tablaCancionesPlaylist.getSelectedRow());
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
				ControladorAppMusic.getInstancia().actualizarPanelReproduccion(menuPlaylist);
				MiTablaPersonalizada tabla = (MiTablaPersonalizada) tablaCancionesPlaylist.getModel();
				umu.tds.negocio.Cancion c = tabla.getCancionAt(tablaCancionesPlaylist.getSelectedRow());
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
				ControladorAppMusic.getInstancia().actualizarPanelReproduccion(menuPlaylist);
				MiTablaPersonalizada tabla = (MiTablaPersonalizada) tablaCancionesPlaylist.getModel();
				LinkedList<Cancion> canciones = tabla.getCanciones();
				boolean vengoOtroPanel = ControladorAppMusic.getInstancia().comprobarPaneles(menuPlaylist);
				if(vengoOtroPanel && canciones.size() > 0)
				{
					tablaCancionesPlaylist.setRowSelectionInterval(canciones.size()-1, canciones.size()-1);
				}
				int indiceActual =vengoOtroPanel ? canciones.size() : tablaCancionesPlaylist.getSelectedRow();
		        int totalCanciones = canciones.size();
		        if (totalCanciones > 0) {
		        	indiceActual--;
		        	int siguienteIndice;
		        	if(indiceActual < 0)
		        	{
		        		indiceActual = canciones.size()-1;
		        	}
		        	siguienteIndice = indiceActual;
		            c = tabla.getCancionAt(siguienteIndice);
		            tablaCancionesPlaylist.setRowSelectionInterval(siguienteIndice, siguienteIndice);
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
				
				ControladorAppMusic.getInstancia().actualizarPanelReproduccion(menuPlaylist);
				MiTablaPersonalizada tabla = (MiTablaPersonalizada) tablaCancionesPlaylist.getModel();
				LinkedList<Cancion> canciones = tabla.getCanciones();
				boolean vengoOtroPanel = ControladorAppMusic.getInstancia().comprobarPaneles(menuPlaylist);
				if(vengoOtroPanel && canciones.size() > 0)
				{
					 tablaCancionesPlaylist.setRowSelectionInterval(0, 0);
				}
				int indiceActual = tablaCancionesPlaylist.getSelectedRow();
		        int totalCanciones = canciones.size();
		        if (totalCanciones > 0) {
		            int siguienteIndice = vengoOtroPanel ? 0 :(indiceActual + 1) % totalCanciones;
		            c = tabla.getCancionAt(siguienteIndice);
		            tablaCancionesPlaylist.setRowSelectionInterval(siguienteIndice, siguienteIndice);
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
				
			}
		});
		
		tablaCancionesPlaylist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	int selectedRow = tablaCancionesPlaylist.getSelectedRow();
                int selectedColumn = tablaCancionesPlaylist.getSelectedColumn();
                if(selectedColumn == 3)
                {
                	boolean marcado = (boolean) tablaCancionesPlaylist.getValueAt(selectedRow, selectedColumn);
                	System.out.println(marcado);
                	if(marcado)
			        	modeloPlaylist.añadirMarcada(selectedRow);
                	else modeloPlaylist.quitarMarcada(selectedRow);
			        if(modeloPlaylist.isCancionesMarcadas())
			        		btnEliminarCanciones.setVisible(true);
			        else btnEliminarCanciones.setVisible(false);
                }
                modeloPlaylist.showCheckBox();
            }
        });
		
		btnEliminarCanciones.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				
				String selectedValue = (String) listaPlaylist.getSelectedValue();
				HashSet<Integer> marcadas = modeloPlaylist.listaMarcadas();
				if(selectedValue != "Favoritas" && !marcadas.isEmpty())
				{
					List<Integer> listaOrdenada = new ArrayList<>(marcadas);
			        Collections.sort(listaOrdenada, Collections.reverseOrder());
					for (Integer integer : listaOrdenada) {
						modeloPlaylist.quitarMarcada(integer);
						modeloPlaylist.borrarCancion(integer);
					}
					modeloPlaylist.showCheckBox();
					btnEliminarCanciones.setVisible(false);
				}
			}
		});
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
            int siguienteIndice = (indiceActual + 1) % totalCanciones;
            System.out.println(siguienteIndice);
            tablaCancionesPlaylist.setRowSelectionInterval(siguienteIndice, siguienteIndice);
            Cancion c = ((MiTablaPersonalizada)tablaCancionesPlaylist.getModel()).getCancionAt(siguienteIndice);
            pausa = "play";
			ControladorAppMusic.getInstancia().reproducirCancion(pausa,c);
			ControladorAppMusic.getInstancia().actualizarEstadoReproductor(pausa);
			pausa = "pause";
        }
	}
	
	public void actualizarTabla()
	{
		if(!playlistActual.equals(""))
		{
			LinkedList<Cancion> canciones = ControladorAppMusic.getInstancia().getPlaylistCanciones(playlistActual);
	        modeloPlaylist.actualizarTabla(canciones, playlistActual);
		}
		 
	}
	
	
}
