package umu.tds.observer;

import cargadorCanciones.Canciones;
import umu.tds.negocio.CargadorCanciones;

public class CancionEvent {
	private CargadorCanciones source;
	private Canciones canciones;
	public CancionEvent(CargadorCanciones source, Canciones canciones)
	{
		this.source = source;
		this.canciones = canciones;
	}
	
	// getter
	public Canciones getCanciones()
	{
		return canciones;
	}
	
	public CargadorCanciones getCargadorCanciones()
	{
		return source;
	}
}
