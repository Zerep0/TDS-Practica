package umu.tds.negocio;

import java.net.URISyntaxException;
import java.util.ArrayList;

import cargadorCanciones.Canciones;
import cargadorCanciones.MapperCancionesXMLtoJava;
import umu.tds.observer.CancionEvent;
import umu.tds.observer.ICancionesListener;

public enum CargadorCanciones implements IBuscadorCanciones{
	INSTANCE;
	ArrayList<ICancionesListener> listeners = new ArrayList<>();
	private CargadorCanciones()
	{
		
	}
	
	public synchronized void addListener(ICancionesListener listener)
	{
		listeners.add(listener);
	}
	
	public synchronized void removeListener(ICancionesListener listener)
	{
		listeners.remove(listener);
	}
	
	public void setArchivoCanciones(String rutaArchivo)
	{
		Canciones canciones = null;
		try {
			canciones = MapperCancionesXMLtoJava.cargarCanciones(rutaArchivo);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		if(canciones != null)
		{
			CancionEvent ev = new CancionEvent(this,canciones);
			notificarCambio(ev);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void notificarCambio(CancionEvent ev)
	{
		ArrayList<ICancionesListener> lista;
		synchronized (this) {
			lista = (ArrayList<ICancionesListener>) listeners.clone();
		}
		for(ICancionesListener listener : lista)
		{
			listener.actualizarCanciones(ev);
		}
	}
	
	
	

}
