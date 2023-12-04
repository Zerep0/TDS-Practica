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
	public boolean registrarCancion(Cancion cancion) {
		Entidad eCancion = null;

		// Si la entidad esta registrada no la registra de nuevo
		try {
			eCancion = servPersistencia.recuperarEntidad(cancion.getId());
		} catch (NullPointerException e) {}
		if (eCancion != null) return false;
		
		Boolean existeCancion = recuperarTodasCanciones().stream().anyMatch(c -> c.getTitulo().equals(cancion.getTitulo()) && c.getInterprete().equals(cancion.getInterprete()));		
				
		if(!existeCancion)
		{
			// crear entidad Cliente
			eCancion = new Entidad();
			eCancion.setNombre("cancion");
			eCancion.setPropiedades(new ArrayList<Propiedad>(
					Arrays.asList(new Propiedad("titulo", cancion.getTitulo()), new Propiedad("ruta",cancion.getRuta()),new Propiedad("estilo",cancion.getEstilo()),
							new Propiedad("interprete",cancion.getInterprete()),new Propiedad("marcado",String.valueOf(cancion.isMarcado())),
							new Propiedad("numReproducciones",String.valueOf(cancion.getNumReproducciones())))));

			// registrar entidad cliente
			eCancion = servPersistencia.registrarEntidad(eCancion);
			// asignar identificador unico
			// Se aprovecha el que genera el servicio de persistencia
			cancion.setId(eCancion.getId());
			return true;
	}
		return false;
	}
	@Override
	public Cancion recuperarCancion(int codigo) {
		// si no, la recupera de la base de datos
		Entidad eCancion;
		String titulo, ruta, estilo, interprete,marcado,numReproducciones;
		// recuperar entidad
		eCancion = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		titulo = servPersistencia.recuperarPropiedadEntidad(eCancion, "titulo");
		ruta = servPersistencia.recuperarPropiedadEntidad(eCancion, "ruta");
		estilo = servPersistencia.recuperarPropiedadEntidad(eCancion, "estilo");
		interprete = servPersistencia.recuperarPropiedadEntidad(eCancion, "interprete");
		marcado = servPersistencia.recuperarPropiedadEntidad(eCancion, "marcado");
		numReproducciones = servPersistencia.recuperarPropiedadEntidad(eCancion, "numReproducciones");

		// recuperar playlist
		Cancion cancion = new Cancion(titulo,ruta, estilo, interprete);
		cancion.setId(codigo);
		cancion.setNumReproducciones(Integer.parseInt(numReproducciones));
		cancion.setMarcado(Boolean.parseBoolean(marcado));
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

}
