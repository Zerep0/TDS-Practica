package umu.tds.negocio;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
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
	private LinkedList<Cancion> cancionesRecientes;
	
	private CatalogoCanciones()
	{
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorCancion = dao.getCancionDAO();
			canciones = new HashMap<String,Cancion>();
			cancionesRecientes = new LinkedList<>();
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
		for (Cancion c : canciones) {
			registrarCancion(c);
		}
	}
	
	public void registrarCancion(Cancion cancion)
	{
		// TODO: registra una cancion en el catalogo y en la base de datos
		int id = adaptadorCancion.registrarCancion(cancion);
		cancion.setId(id);
		canciones.put(cancion.getInterprete()  + "_" + cancion.getTitulo(), cancion);
	}
	
	public List<Cancion> getCanciones()
	{
		// TODO:
		List<Cancion> listaCanciones = new LinkedList<Cancion>();
		for(Cancion c:canciones.values())
		{
			listaCanciones.add(c);
		}
		return listaCanciones;
	}
	
	public Cancion getCancion(String titulo, String interprete)
	{
		String clave = interprete + "_" + titulo;
		return canciones.get(clave);
	}
	
	public void cargarCatalogo()
	{
		// TODO: cargar Canciones de la BBDD
		 List<Cancion> cancionesBD = adaptadorCancion.recuperarTodasCanciones();
		 for (Cancion can : cancionesBD) 
		 {
			  canciones.put(can.getInterprete()  + "_" + can.getTitulo(), can);
			  if(can.getReciente() != Reciente.NORECIENTE)
			  {
				  cancionesRecientes.add(can);
			  }
		 }
		 cancionesRecientes.sort(new Comparator<Cancion>() {
            @Override
            public int compare(Cancion c1, Cancion c2) {
                // Compara las canciones según el índice de reciente
                return Integer.compare(c1.getReciente().ordinal(), c2.getReciente().ordinal());
            }
        });
		 
	}
	
	public void actualizarFavorito(Cancion c)
	{
		String clave =  c.getInterprete() + "_" + c.getTitulo();
		canciones.put(clave, c);
		adaptadorCancion.actualizarFavorito(c);
	}
	
	public void agregarReciente(Cancion nuevaCancion) {
	    if (!cancionesRecientes.contains(nuevaCancion)) {
	    	
	    	cancionesRecientes.addFirst(nuevaCancion);  // Agregar al principio de la lista
	    	if(cancionesRecientes.size() == Reciente.values().length)
	    	{
	    		Cancion antigua = cancionesRecientes.removeLast();
	            antigua.setReciente(Reciente.NORECIENTE.ordinal());
	            adaptadorCancion.recienteEliminada(antigua);
	    	}
	    }
	    else
	    {
	    	if(nuevaCancion.getReciente() != Reciente.PRIMERA)
	    	{
	    		cancionesRecientes.remove(nuevaCancion);
	    		cancionesRecientes.addFirst(nuevaCancion);
	    	}
	    }
	    for (int i = 0; i < cancionesRecientes.size(); i++) {
            Cancion cancionActual = cancionesRecientes.get(i);
            cancionActual.setReciente(i);
        }
	    adaptadorCancion.actualizarRecientes(cancionesRecientes);
	}
	
	public LinkedList<Cancion> getRecientes()
	{
		return cancionesRecientes;
	}

}
