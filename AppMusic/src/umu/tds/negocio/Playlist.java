package umu.tds.negocio;

import java.util.LinkedList;
import java.util.List;

public class Playlist {

	private String nombrePlaylist;
	private LinkedList<Cancion> canciones;
	public Playlist(String nombrePlaylist) {
		this.nombrePlaylist = nombrePlaylist;
		canciones = new LinkedList<Cancion>();
	}
	
	
	public void addCancion(Cancion c)
	{
		if(!canciones.contains(c))
		{
			canciones.add(c);
		}
	}
	
	public LinkedList<Cancion> getCanciones()
	{
		return canciones;
	}
	
	public boolean borrarCancion(Cancion c)
	{		
		return canciones.remove(c);
	}
	
	public String getNombrePlaylist() 
	{
		return nombrePlaylist;
	}
	
}
