package umu.tds.vista;

import javax.swing.*;

import umu.tds.controlador.ControladorAppMusic;
import umu.tds.helper.AlineamientoLista;
import umu.tds.negocio.Cancion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Font;

public class PanelSeleccionPlaylist extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Cancion cancion;
	private ListaModelo<String> miModelo;
	private ArrayList<String> playlists;
	private AlineamientoLista alineamiento;
	private JDialog padre;
	private static String PLAYLIST_NO_CREADAS = "No hay playlists creadas";
	
	public PanelSeleccionPlaylist(Cancion cancion, ArrayList<String> playlists, JDialog padre) {
		this.cancion = cancion;
		this.playlists = playlists;
		playlists.remove("Favoritas"); // no se le proporciona por que para eso ya esta la estrella
		this.padre = padre;
		alineamiento = new AlineamientoLista("centro");
		inicialize();

    }
	
	private void inicialize() 
	{
		
		setBackground(new Color(18, 156, 189));

        setVisible(true);
        setSize(400, 300);
        setLayout(new BorderLayout(0, 0));
        
        miModelo = new ListaModelo<String>();
		miModelo.actualizarLista(playlists);
        JList<String> playlistSeleccion = new JList<String>();
        playlistSeleccion.setBackground(new Color(193, 255, 245));
        playlistSeleccion.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
        playlistSeleccion.setSelectionBackground(new Color(0,128,255));
        playlistSeleccion.setSelectionForeground(Color.WHITE);
        playlistSeleccion.setCellRenderer(alineamiento);
        playlistSeleccion.setModel(miModelo);
        add(playlistSeleccion, BorderLayout.SOUTH);
        playlistSeleccion.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(playlistSeleccion);
        scrollPane.setBackground(new Color(18, 156, 189));
        add(scrollPane);
        
        JLabel elige = new JLabel("Selecciona las playlist para " + cancion.getTitulo());
        elige.setHorizontalAlignment(SwingConstants.CENTER);
        elige.setFont(new Font("Arial", Font.BOLD, 14));
        elige.setForeground(new Color(255, 255, 255));
        add(elige, BorderLayout.NORTH);
        
        JButton btnAñadirPlaylists = new JButton("Añadir");
        btnAñadirPlaylists.setFont(new Font("Arial", Font.BOLD, 14));
        btnAñadirPlaylists.setForeground(new Color(255, 255, 255));
        btnAñadirPlaylists.setBorderPainted(false);
        btnAñadirPlaylists.setBackground(new Color(18, 156, 189));
        add(btnAñadirPlaylists, BorderLayout.SOUTH);
        
		if(playlists.isEmpty())
		{
			btnAñadirPlaylists.setEnabled(false);
			btnAñadirPlaylists.setText(PLAYLIST_NO_CREADAS);
		}
        
        btnAñadirPlaylists.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		LinkedList<String> seleccionado = new LinkedList<String>(playlistSeleccion.getSelectedValuesList());
        		ControladorAppMusic.getInstancia().anadirCancionesPlaylist(seleccionado,cancion);
        		padre.dispose();
        	}
        });
	}
  
}