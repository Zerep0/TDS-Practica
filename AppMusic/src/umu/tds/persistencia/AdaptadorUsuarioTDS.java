package umu.tds.persistencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.negocio.Cancion;
import umu.tds.negocio.Usuario;
import beans.Entidad;
import beans.Propiedad;



//Usa un pool para evitar problemas doble referencia con ventas
public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUsuarioTDS unicaInstancia = null;

	public static AdaptadorUsuarioTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new AdaptadorUsuarioTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorUsuarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/* cuando se registra un cliente se le asigna un identificador �nico */
	public boolean registrarUsuario(Usuario usuario) {
		Entidad eCliente = null;

		// Si la entidad esta registrada no la registra de nuevo
		try {
			eCliente = servPersistencia.recuperarEntidad(usuario.getId());
		} catch (NullPointerException e) {}
		if (eCliente != null) return false;
		
		Boolean existeUsuario = AdaptadorUsuarioTDS.getUnicaInstancia()
				.recuperarTodosUsuarios().stream()
										 .map(u -> u.getLogin())
										 .anyMatch(u -> u.equals(usuario.getLogin()));
		if(!existeUsuario)
		{
			// crear entidad Cliente
			eCliente = new Entidad();
			eCliente.setNombre("usuario");
			eCliente.setPropiedades(new ArrayList<Propiedad>(
					Arrays.asList(new Propiedad("login", usuario.getLogin()),new Propiedad("favoritas",""), new Propiedad("password",usuario.getPassword()),new Propiedad("email",usuario.getEmail()),
							new Propiedad("fechaNacimiento",usuario.getFechaNacimiento().toString()),new Propiedad("premium",String.valueOf(usuario.isPremium())),
							new Propiedad("recientes",""))));

			// registrar entidad cliente
			eCliente = servPersistencia.registrarEntidad(eCliente);
			// asignar identificador unico
			// Se aprovecha el que genera el servicio de persistencia
			usuario.setId(eCliente.getId());
			return true;
		}
		
		return false;
	}


	public Usuario recuperarUsuario(int codigo) {

		

		// si no, la recupera de la base de datos
		Entidad eUsuario;
		String nombre, email, fechaNacimiento, password, premium, recientes, favoritas;
		

		// recuperar entidad
		eUsuario = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, "login");
		email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		fechaNacimiento = servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento");
		password = servPersistencia.recuperarPropiedadEntidad(eUsuario, "password");
		premium = servPersistencia.recuperarPropiedadEntidad(eUsuario, "premium");
		Usuario usuario = new Usuario(nombre,password,email, LocalDate.parse(fechaNacimiento));
		
		// recuperar playlist
		recientes = servPersistencia.recuperarPropiedadEntidad(eUsuario, "recientes");
		String [] canciones = recientes.split(",");
		Cancion c;
		for(String s : canciones)
		{
			if(!s.equals(""))
			{
				
				c = AdaptadorCancionTDS.getUnicaInstancia().recuperarCancion(Integer.parseInt(s));
				if(c != null)
				{
					usuario.addReciente(c,true);
				}
					
			}
			
		}
		favoritas = servPersistencia.recuperarPropiedadEntidad(eUsuario, "favoritas");
		canciones = favoritas.split(",");
		for(String s : canciones)
		{
			if(!s.equals(""))
			{
				c = AdaptadorCancionTDS.getUnicaInstancia().recuperarCancion(Integer.parseInt(s));
				if(c != null)
					usuario.addFavorita(c);
			}
			
		}
		
		
		usuario.setId(codigo);
		usuario.setPremium(Boolean.parseBoolean(premium));

		return usuario;
	}

	public List<Usuario> recuperarTodosUsuarios() {

		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("usuario");
		List<Usuario> usuarios = new LinkedList<Usuario>();

		for (Entidad eUser : eUsuarios) {
			usuarios.add(recuperarUsuario(eUser.getId()));
		}
		return usuarios;
	}
	
	public void actualizar(LinkedList<Integer> nums, Usuario u, String propiedad)
	{
		String informacion = IntegerListToString(nums);
		modificarUsuario(informacion, u,propiedad);
	}

	
	public String IntegerListToString(LinkedList<Integer> nums)
	{
		String listaCan = "";
		if(!nums.isEmpty())
		{
			int i;
			for (i = 0;i<nums.size()-1;i++) {
				listaCan += nums.get(i).toString() + ",";
			}
			listaCan += nums.get(i).toString(); 
		}
		return listaCan;
	}
	
	public void modificarUsuario(String informacion, Usuario u, String propiedad)
	{
		Entidad recuperaCancion = servPersistencia.recuperarEntidad(u.getId());
		recuperaCancion.getPropiedades().stream().filter(p -> p.getNombre().equals(propiedad))
		.forEach(p -> {p.setValor(informacion);
		servPersistencia.modificarPropiedad(p);});
	}
	

	
	
	
}
