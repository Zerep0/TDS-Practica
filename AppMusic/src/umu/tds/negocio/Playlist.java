package umu.tds.negocio;

import java.util.List;

public class Playlist {

	
	private String nombrePlaylist;
	
	public Playlist(String nombrePlaylist) {
		this.nombrePlaylist = nombrePlaylist;
	}
	
	
	public void addCancion()
	{
		
	}
	
	public List<Cancion> getCanciones()
	{
		return null;
	}
	public String getNombrePlaylist() {
		return nombrePlaylist;
	}

	public void setNombrePlaylist(String nombrePlaylist) {
		this.nombrePlaylist = nombrePlaylist;
	}
	
	
}
