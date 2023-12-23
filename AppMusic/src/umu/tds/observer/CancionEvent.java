package umu.tds.observer;

import CargadorCanciones.Canciones;
import umu.tds.negocio.CargadorCanciones;

public class CancionEvent {
	CargadorCanciones source;
	Canciones canciones;
	public CancionEvent(CargadorCanciones source, Canciones canciones)
	{
		this.source = source;
		this.canciones = canciones;
	}
	
	// getter
}
