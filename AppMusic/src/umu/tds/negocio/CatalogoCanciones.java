package umu.tds.negocio;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import umu.tds.persistencia.DAOException;
import umu.tds.persistencia.FactoriaDAO;
import umu.tds.persistencia.IAdaptadorCancionDAO;

public class CatalogoCanciones {
	private static CatalogoCanciones unicaInstancia;
	private static int MAXIMO_ESCUCHADAS = 10;
	
	private FactoriaDAO dao;
	private IAdaptadorCancionDAO adaptadorCancion;
	// la clave es interprete + "_" + titulo
	private HashMap<String,Cancion> canciones;
	public LinkedList<Cancion> cancionesMasEscuchadas;
	
	private Function<LinkedList<Cancion>,LinkedList<Cancion>> topEscuchadas = cancionesMasEscuchadas -> cancionesMasEscuchadas.stream().sorted(Comparator.comparing(Cancion::getNumReproducciones)
			 .thenComparing(Cancion::getTitulo).thenComparing(Cancion::getInterprete).reversed()).limit(MAXIMO_ESCUCHADAS).collect(Collectors.toCollection(LinkedList::new));
	
	private LinkedList<String> estilosMusicales;
	
	private CatalogoCanciones()
	{
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorCancion = dao.getCancionDAO();
			canciones = new HashMap<String,Cancion>();
			estilosMusicales = new LinkedList<String>();
			cancionesMasEscuchadas = new LinkedList<Cancion>();
			this.cargarCatalogo();
		} catch (DAOException e) {
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
		for (Cancion c : canciones) {
			registrarCancion(c);
		}
	}
	
	public void registrarCancion(Cancion cancion)
	{
		int id = adaptadorCancion.registrarCancion(cancion);
		cancion.setId(id);
		canciones.put(cancion.getInterprete() + "_" + cancion.getTitulo(), cancion);
	}
	
	public List<Cancion> getCanciones()
	{
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
		
		 List<Cancion> cancionesBD = adaptadorCancion.recuperarTodasCanciones();
		 for (Cancion can : cancionesBD) 
		 {
			 if(!estilosMusicales.contains(can.getEstilo()))
				 estilosMusicales.add(can.getEstilo());
			 canciones.put(can.getInterprete()  + "_" + can.getTitulo(), can);
		 }
		 Collections.sort(estilosMusicales);
		 estilosMusicales.addFirst("-");
		 
		 cancionesMasEscuchadas = topEscuchadas.apply(new LinkedList<Cancion>(cancionesBD));
	}
	
	public void incrementarReproducciones(Cancion cancion)
	{
		int reproducciones = adaptadorCancion.actualizarReproducciones(cancion);
		rebalancearRanking(cancion, reproducciones);
	}
	
	public LinkedList<Cancion> getMasEscuchadas()
	{
		return cancionesMasEscuchadas;
	}
	
	public void rebalancearRanking(Cancion cancion, int reproducciones)
	{
		if(!cancionesMasEscuchadas.contains(cancion))
		{
			cancion.setNumReproducciones(reproducciones);
			cancionesMasEscuchadas.add(cancion);
			
		}else
		{
			cancionesMasEscuchadas.stream().filter(c -> c.equals(cancion)).forEach(c -> c.setNumReproducciones(reproducciones));
		}
		cancionesMasEscuchadas = topEscuchadas.apply(cancionesMasEscuchadas);
	}
	
	public LinkedList<String> getEstilosMusicales()
	{
		return estilosMusicales;
	}
	

}
