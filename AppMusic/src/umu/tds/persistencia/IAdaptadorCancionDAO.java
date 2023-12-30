package umu.tds.persistencia;

import java.util.LinkedList;
import java.util.List;

import umu.tds.negocio.Cancion;

public interface IAdaptadorCancionDAO {
	public int registrarCancion(Cancion cancion);
	public Cancion recuperarCancion(int codigo);
	public List<Cancion> recuperarTodasCanciones();
	public void actualizarFavorito(Cancion c);
	public void recienteEliminada(Cancion recienteEliminada);
	public void actualizarRecientes(LinkedList<Cancion> recientes);
	public void actualizarReciente(Cancion recientes);
}
