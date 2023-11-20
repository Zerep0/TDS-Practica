package umu.tds.controlador;


import umu.tds.persistencia.DAOException;

import java.time.LocalDate;


import umu.tds.negocio.CatalogoUsuarios;
import umu.tds.negocio.Usuario;
import umu.tds.persistencia.AdaptadorUsuarioTDS;
import umu.tds.persistencia.FactoriaDAO;
import umu.tds.persistencia.IAdaptadorUsuarioDAO;
import umu.tds.vista.Alerta;
import umu.tds.vista.Registro;

public class ControladorAppMusic {
	private static final String MENSAJE_USUARIO_REPETIDO = "El nombre de usuario ya esta cogido";
	private static final String MENSAJE_USUARIO_REGISTRADO = "Se ha registrado con exito";
	private static final String ASUNTO_ERROR = "Usuario repetido";
	private static final String ASUNTO_REGISTRO = "Registrado";

	private static ControladorAppMusic unicaInstancia;
	
	private IAdaptadorUsuarioDAO adaptadorUsuario;
		
	private CatalogoUsuarios catalogoUsuarios;
	
	private ControladorAppMusic()
	{
		inicializarAdaptadores();
		
		inicializarCatalogos();
	}
	
	public static ControladorAppMusic getInstancia()
	{
		if (unicaInstancia == null)
			unicaInstancia = new ControladorAppMusic();
		return unicaInstancia;
	}
	
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getClienteDAO();
	}

	private void inicializarCatalogos() {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
	}
	
	public void registrarUsuario(String login, String password, String email, LocalDate fechaNacimiento, Registro ventana)
	{
		Usuario user = new Usuario(login,password,email,fechaNacimiento);
		if(!adaptadorUsuario.registrarUsuario(user))
		{
			Alerta.INSTANCIA.mostrarAlerta(MENSAJE_USUARIO_REPETIDO, ASUNTO_ERROR, ventana);
			return;
		}
		Alerta.INSTANCIA.mostrarAlerta(MENSAJE_USUARIO_REGISTRADO, ASUNTO_REGISTRO, ventana);
		catalogoUsuarios.addUsuario(user);
	}

}
