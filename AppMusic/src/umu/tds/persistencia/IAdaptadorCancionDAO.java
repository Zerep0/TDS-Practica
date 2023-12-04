package umu.tds.persistencia;

import java.util.List;

import umu.tds.negocio.Cancion;

public interface IAdaptadorCancionDAO {
	public boolean registrarCancion(Cancion cancion);
	public Cancion recuperarCancion(int codigo);
	public List<Cancion> recuperarTodasCanciones();
}
