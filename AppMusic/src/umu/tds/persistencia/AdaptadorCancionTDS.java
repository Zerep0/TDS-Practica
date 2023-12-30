package umu.tds.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.negocio.Cancion;

public class AdaptadorCancionTDS implements IAdaptadorCancionDAO{
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorCancionTDS unicaInstancia = null;
	private AdaptadorCancionTDS()
	{
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();

	}
	@Override
	public int registrarCancion(Cancion cancion) {
		Entidad eCancion = null;

		// Si la entidad esta registrada no la registra de nuevo
		try {
			eCancion = servPersistencia.recuperarEntidad(cancion.getId());
		} catch (NullPointerException e) {}
		if (eCancion == null) 
		{
			Boolean existeCancion = recuperarTodasCanciones().stream()
					.anyMatch(c -> c.getTitulo().equals(cancion.getTitulo()) && c.getInterprete().equals(cancion.getInterprete()));		
					
			if(!existeCancion)
			{
				// crear entidad Cliente
				eCancion = new Entidad();
				eCancion.setNombre("cancion");
				eCancion.setPropiedades(new ArrayList<Propiedad>(
						Arrays.asList(new Propiedad("titulo", cancion.getTitulo()), new Propiedad("reciente",String.valueOf(cancion.getReciente().ordinal())), new Propiedad("ruta",cancion.getRuta()),new Propiedad("estilo",cancion.getEstilo()),
								new Propiedad("interprete",cancion.getInterprete()),new Propiedad("favorito",String.valueOf(cancion.isFavorita())),
								new Propiedad("numReproducciones",String.valueOf(cancion.getNumReproducciones())))));

				// registrar entidad cliente
				eCancion = servPersistencia.registrarEntidad(eCancion);
				// asignar identificador unico
				// Se aprovecha el que genera el servicio de persistencia
				cancion.setId(eCancion.getId());
			}
		}
		
		
		return cancion.getId();
	}
	
	@Override
	public Cancion recuperarCancion(int codigo) {
		// si no, la recupera de la base de datos
		Entidad eCancion;
		String titulo, ruta, estilo, interprete,favorito,numReproducciones;
		int reciente;
		// recuperar entidad
		eCancion = servPersistencia.recuperarEntidad(codigo);
		

		// recuperar propiedades que no son objetos
		titulo = servPersistencia.recuperarPropiedadEntidad(eCancion, "titulo");
		ruta = servPersistencia.recuperarPropiedadEntidad(eCancion, "ruta");
		estilo = servPersistencia.recuperarPropiedadEntidad(eCancion, "estilo");
		interprete = servPersistencia.recuperarPropiedadEntidad(eCancion, "interprete");
		favorito = servPersistencia.recuperarPropiedadEntidad(eCancion, "favorito");
		numReproducciones = servPersistencia.recuperarPropiedadEntidad(eCancion, "numReproducciones");
		reciente = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eCancion, "reciente"));

		// recuperar playlist
		Cancion cancion = new Cancion(titulo,ruta, estilo, interprete);
		cancion.setId(codigo);
		cancion.setNumReproducciones(Integer.parseInt(numReproducciones));
		cancion.setFavorita(Boolean.parseBoolean(favorito));
		cancion.setReciente(reciente);
		return cancion;
	}

	@Override
	public List<Cancion> recuperarTodasCanciones() {
		List<Entidad> eCanciones = servPersistencia.recuperarEntidades("cancion");
		List<Cancion> canciones = new LinkedList<Cancion>();

		for (Entidad eCancion : eCanciones) {
			canciones.add(recuperarCancion(eCancion.getId()));
		}
		return canciones;
	}

	public static AdaptadorCancionTDS getUnicaInstancia() {
		if (unicaInstancia == null)
			return new AdaptadorCancionTDS();
		else
			return unicaInstancia;
	}
	
	@Override
	public void actualizarFavorito(Cancion c) {
		Entidad recuperaCancion = servPersistencia.recuperarEntidad(c.getId());
		recuperaCancion.getPropiedades().stream().filter(p -> p.getNombre().equals("favorito"))
		.forEach(p -> {p.setValor(String.valueOf(c.isFavorita()));
		servPersistencia.modificarPropiedad(p);});
	}
	
	public void recienteEliminada(Cancion recienteEliminada)
	{
		actualizarReciente(recienteEliminada);
	}
	
	public void actualizarRecientes(LinkedList<Cancion> recientes)
	{
		for(Cancion c : recientes)
		{
			actualizarReciente(c);
		}
	}
	
	public void actualizarReciente(Cancion recientes)
	{
		Entidad recuperaCancion = servPersistencia.recuperarEntidad(recientes.getId());
		recuperaCancion.getPropiedades().stream().filter(p -> p.getNombre().equals("reciente"))
		.forEach(p -> {p.setValor(String.valueOf(recientes.getReciente().ordinal()));
		servPersistencia.modificarPropiedad(p);});
	}
	
}
