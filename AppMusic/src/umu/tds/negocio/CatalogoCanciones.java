package umu.tds.negocio;

import java.util.HashMap;
import java.util.List;

import umu.tds.persistencia.DAOException;
import umu.tds.persistencia.FactoriaDAO;
import umu.tds.persistencia.IAdaptadorCancionDAO;
import umu.tds.persistencia.IAdaptadorUsuarioDAO;

public class CatalogoCanciones {
	private static CatalogoCanciones unicaInstancia;
	
	private FactoriaDAO dao;
	private IAdaptadorCancionDAO adaptadorCancion;
	// la clave es interprete + "_" + titulo
	private HashMap<String,Cancion> canciones;
	
	private CatalogoCanciones()
	{
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorCancion = dao.getCancionDAO();
			canciones = new HashMap<String,Cancion>();
			this.cargarCatalogo();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static CatalogoCanciones getUnicaInstancia()
	{
		if(unicaInstancia == null)
		{
			unicaInstancia = new CatalogoCanciones();
		}
		return unicaInstancia;
	}
	
	public void registrarCanciones(List<Cancion> canciones)
	{
		// TODO: dada una lista de canciones las registra
	}
	
	public void registrarCancion(Cancion cancion)
	{
		// TODO: registra una cancion en el catalogo y en la base de datos
	}
	
	public List<Cancion> getCanciones()
	{
		// TODO:
		return null;
	}
	
	public Cancion getCancion(String titulo, String interprete)
	{
		// TODO:
		return null;
	}
	
	public void cargarCatalogo()
	{
		// TODO: cargar Canciones de la BBDD
	}

}
