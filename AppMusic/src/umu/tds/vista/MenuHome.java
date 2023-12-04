package umu.tds.vista;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicSliderUI;

import umu.tds.controlador.ControladorAppMusic;
import umu.tds.helper.AlineamientoLista;
import umu.tds.observer.IUsuarioListener;
import umu.tds.observer.UsuarioEvent;

import javax.swing.JSlider;
import java.awt.Dimension;
import javax.swing.SwingConstants;

public class MenuHome extends JPanel{

	private static final long serialVersionUID = 1L;
	private AlineamientoLista alineamientoListaMenu;
	private boolean pausa;
	private JLabel MsgBienvenida;
	/**
	 * Create the panel.
	 */
	public MenuHome() {
		this.pausa = true;
		initialize();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	private void initialize()
	{
		// HELPER
		alineamientoListaMenu = new AlineamientoLista();
		
		setBackground(new Color(18, 159, 186));
		setLayout(new BorderLayout(0, 0));
		
		JPanel PanelReproduccion = new JPanel();
		PanelReproduccion.setBackground(new Color(18, 159, 186));
		add(PanelReproduccion, BorderLayout.SOUTH);
		
		JLabel btnStop = new JLabel("");
		btnStop.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/cuadrado.png")));
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
		
		MsgBienvenida = new JLabel("Sigue escuchando, ");
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
		
		JList<String> listaCanciones = new JList();
		listaCanciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaCanciones.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		listaCanciones.setModel(new AbstractListModel() {
			String[] values = new String[] {"Movie Dizzie", "Cancion de lax", "Wos Andromeda","Cancion de prueba", "Alfa"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(listaCanciones);
		listaCanciones.setBorder(BorderFactory.createLineBorder(Color.black));
		listaCanciones.setCellRenderer(alineamientoListaMenu);
		
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
		
		ControladorAppMusic.getInstancia().addUsuarioListener(new IUsuarioListener() {
			
			@Override
			public void actualizar(UsuarioEvent e) {
				MsgBienvenida.setText(MsgBienvenida.getText() + e.getNombreUsuario());
				
			}
		});
		
		
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


	


	
	
	
	
	
}
