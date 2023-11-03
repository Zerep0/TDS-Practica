package umu.tds.vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;

import umu.tds.helper.*;
import umu.tds.negocio.Cancion;
import umu.tds.negocio.Interprete;

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
import javax.swing.JTable;

import java.awt.Cursor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.Collections;


public class MenuPlaylist extends JPanel {

	private static final String PLACEHOLDER_PLAYLIST = "Nombre de la Playlist";
	private static final long serialVersionUID = 1L;
	
	private JTextField creadorPlaylist;
	private JTable tablaCancionesPlaylist;
	private Placeholder placeholder;
	private RenderizadorLetrasCabecera renderizadorLetras;
	private boolean pausa;
	/**
	 * Create the panel.
	 */
	
	public MenuPlaylist() {
		this.pausa = true;
		initialize();
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	private void initialize()
	{
		
		class MiTablaPersonalizada extends AbstractTableModel {
			
			private ArrayList<Cancion> cancionesPrueba;
			private String[] columnas = {"Titulo","Interprete","Estilo",""};
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
	                    return cancion.getInterprete().getNombre();
	                case 2:
	                    return cancion.getEstilo();
	                case 3:
	                    return cancion.isMarcado();
	                default:
	                    return null;
	            }
	        }
			
			public void setValueAt(Object value, int row, int column) {
	            Cancion cancion = cancionesPrueba.get(row);
	            if (column == 3) {
	            	
	                cancion.setMarcado((Boolean) value);
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
		
		JScrollPane PanelPlaylist = new JScrollPane();
		PanelPlaylist.setFont(new Font("Arial", Font.PLAIN, 14));
		PanelPlaylist.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(PanelPlaylist, BorderLayout.WEST);
		
		JList<String> listaCanciones = new JList();
		listaCanciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaCanciones.setFont(new Font("Arial", Font.PLAIN, 14));
		listaCanciones.setModel(new AbstractListModel() {
			String[] values = new String[] {"Favoritas", "Musica Random", "Musica Clasica","Metal", "Un dia mas aqui","Pop","Pop","Pop","Pop","Pop","Pop","Pop","Pop","Pop","Pop"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		PanelPlaylist.setViewportView(listaCanciones);
		listaCanciones.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		
		
		
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
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(MenuPlaylist.class.getResource("/ImagenesMenu/pagina.png")));
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnNewButton_1.setBackground(new Color(18, 159, 186));
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 2;
		gbc_btnNewButton_1.gridy = 1;
		PanelGestionaPlaylist.add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setIcon(new ImageIcon(MenuPlaylist.class.getResource("/ImagenesMenu/no-disponible.png")));
		btnNewButton_2.setBorderPainted(false);
		btnNewButton_2.setBackground(new Color(18, 159, 186));
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_2.gridx = 3;
		gbc_btnNewButton_2.gridy = 1;
		PanelGestionaPlaylist.add(btnNewButton_2, gbc_btnNewButton_2);
		
		JPanel PanelReproduccion = new JPanel();
		PanelReproduccion.setBackground(new Color(18, 159, 186));
		add(PanelReproduccion, BorderLayout.SOUTH);
		
		JLabel btnStop = new JLabel("");
		btnStop.setIcon(new ImageIcon(MenuPlaylist.class.getResource("/ImagenesMenu/cuadrado.png")));
		PanelReproduccion.add(btnStop);
		
		JLabel btnRedo = new JLabel("");
		btnRedo.setIcon(new ImageIcon(MenuPlaylist.class.getResource("/ImagenesMenu/atras.png")));
		PanelReproduccion.add(btnRedo);
		
		JLabel btnPlay = new JLabel("");
		btnPlay.setIcon(new ImageIcon(MenuPlaylist.class.getResource("/ImagenesMenu/triangulo-negro-flecha-derecha.png")));
		PanelReproduccion.add(btnPlay);
		
		JLabel btnForwa = new JLabel("");
		btnForwa.setIcon(new ImageIcon(MenuPlaylist.class.getResource("/ImagenesMenu/siguiente.png")));
		PanelReproduccion.add(btnForwa);
		
		JLabel btnRandom = new JLabel("");
		btnRandom.setIcon(new ImageIcon(MenuPlaylist.class.getResource("/ImagenesMenu/aleatorio.png")));
		PanelReproduccion.add(btnRandom);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(18, 156, 189));
		panel.add(panel_1, BorderLayout.WEST);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(18, 156, 189));
		panel.add(panel_2, BorderLayout.NORTH);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(18, 156, 189));
		panel.add(panel_3, BorderLayout.SOUTH);
		
		JButton btnEliminarCancion = new JButton("");
		btnEliminarCancion.setBorderPainted(false);
		btnEliminarCancion.setBackground(new Color(18, 159, 186));
		btnEliminarCancion.setIcon(new ImageIcon(MenuPlaylist.class.getResource("/ImagenesMenu/cancelar.png")));
		panel_3.add(btnEliminarCancion);
		btnEliminarCancion.setVisible(false);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(18, 156, 189));
		panel.add(panel_4, BorderLayout.EAST);
		
		
		Interprete Wos = new Interprete("Wos");
		Interprete Barbie = new Interprete("Barbie");
		Cancion [] cancionesPrueba = {new Cancion("ANDROMEDA","ruta","rap",Wos), new Cancion("Aqua Girl","rutaa","pop",Barbie)};
		MiTablaPersonalizada modeloPlaylist = new MiTablaPersonalizada(cancionesPrueba);
		tablaCancionesPlaylist = new JTable(modeloPlaylist);	
		
		tablaCancionesPlaylist.setAutoCreateRowSorter(true);
		JTableHeader header = tablaCancionesPlaylist.getTableHeader(); 
		
		// HELPER
		renderizadorLetras = new RenderizadorLetrasCabecera(header.getDefaultRenderer(), new Font("Arial", Font.BOLD, 14),Color.BLACK);;
		header.setDefaultRenderer(renderizadorLetras);
		JScrollPane scrollPane = new JScrollPane(tablaCancionesPlaylist);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		
		
		placeholder.crearPlaceholderText(creadorPlaylist, PLACEHOLDER_PLAYLIST);
		
		
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(pausa)
				{
					btnPlay.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/boton-de-pausa.png")));
					btnPlay.setVisible(true);
					pausa = false;
				}
				else
				{
					btnPlay.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/triangulo-negro-flecha-derecha.png")));
					btnPlay.setVisible(true);
					pausa = true;
				}

			}
		});
	}
}
