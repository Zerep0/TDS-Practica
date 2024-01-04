package umu.tds.vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;

import umu.tds.controlador.ControladorAppMusic;
import umu.tds.helper.*;
import umu.tds.negocio.Cancion;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;

import java.awt.Font;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;

import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.Collections;

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
	/**
	 * Create the panel.
	 */
	
	public MenuPlaylist() {
		this.pausa = "play";
		menuPlaylist = this;
		ControladorAppMusic.getInstancia().setMenuPlaylist(menuPlaylist, barraReproduccion, msgDuracion);
		initialize();
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	private void initialize()
	{
		miModelo = new ListaModelo<String>();
		class MiTablaPersonalizada extends AbstractTableModel {
			
			private ArrayList<Cancion> cancionesPrueba;
			private String[] columnas = {"Titulo","Interprete","Estilo","Seleccionar"};
			private static final long serialVersionUID = 1L;
			
			public MiTablaPersonalizada(Cancion ...cancionesPrueba)
			{
				this.cancionesPrueba = new ArrayList<>();
				Collections.addAll(this.cancionesPrueba, cancionesPrueba);
			}

			public int getRowCount() {
				
				return cancionesPrueba.size();
			}

			public int getColumnCount() {
				
				return columnas.length;
			}
			
			public String getColumnName(int col) {
				
				return columnas[col];
			}

			public Object getValueAt(int row, int column) {
	            Cancion cancion = cancionesPrueba.get(row);
	            switch (column) {
	                case 0:
	                    return cancion.getTitulo();
	                case 1:
	                    return cancion.getInterprete();
	                case 2:
	                    return cancion.getEstilo();
	                case 3:
	                    return true;
	                default:
	                    return null;
	            }
	        }
			
			public void setValueAt(Object value, int row, int column) {
	            Cancion cancion = cancionesPrueba.get(row);
	            if (column == 3) {
	            	
	                //cancion.setFavorita((Boolean) value);
	            }
	            fireTableCellUpdated(row, column);
	        }
			
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 3) {
	                return Boolean.class; // CheckBox
	            }
	            return super.getColumnClass(columnIndex);
			}
				
			public boolean isCellEditable(int row, int col) {
				
				return  col == 3; // Solo la columna de checkboxes es editable.
			}

		}
		
		
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
		
		
		Cancion [] cancionesPrueba = {new Cancion("ANDROMEDA","ruta","rap","Wos"), new Cancion("Aqua Girl","rutaa","pop","Barbie")};
		MiTablaPersonalizada modeloPlaylist = new MiTablaPersonalizada(cancionesPrueba);
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
				if(ControladorAppMusic.getInstancia().registrarPlaylist(creadorPlaylist))
				{
					anadirPlaylist(creadorPlaylist.getText());
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
	
	public void anadirPlaylist(String playlist)
	{
		miModelo.add(playlist);
	}
	
	public void quitarPlaylist(String playlist)
	{
		miModelo.remove(playlist);
	}
	
	public void cancionAlFinalizar()
	{
		// TODO:
	}
}
