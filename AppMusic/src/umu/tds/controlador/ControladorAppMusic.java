package umu.tds.controlador;


import umu.tds.persistencia.DAOException;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.awt.Font;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JPasswordField;
import javax.swing.JTextField;


import umu.tds.negocio.CatalogoUsuarios;
import umu.tds.negocio.Usuario;
import umu.tds.persistencia.AdaptadorUsuarioTDS;
import umu.tds.persistencia.FactoriaDAO;
import umu.tds.persistencia.IAdaptadorUsuarioDAO;
import umu.tds.vista.Alerta;
import umu.tds.vista.Inicio;
import umu.tds.vista.Registro;

public class ControladorAppMusic {
	private static final String MENSAJE_USUARIO_REPETIDO = "El nombre de usuario ya esta cogido";
	private static final String MENSAJE_USUARIO_REGISTRADO = "Se ha registrado con exito";
	private static final String MENSAJE_USUARIO_LOGIN = "Se ha iniciado sesion correctamente";
	private static final String MENSAJE_USUARIO_LOGIN_FALLO = "No existe tal usuario, registrate";
	private static final String MENSAJE_CONTRASEÑA_LOGIN_FALLO = "Contraseña erronea";
	private static final String MENSAJE_FECHA_VACIA = "Rellena el campo Fecha";
	private static final String MENSAJE_EMAIL_VACIO = "Rellena el campo Email";
	private static final String MENSAJE_PASSWORD_VACIA = "Rellena el campo Contraseña";
	private static final String MENSAJE_USER_VACIO = "Rellena el campo Usuario";
	
	private static final String ASUNTO_ERROR = "Usuario repetido";
	private static final String ASUNTO_ERROR_CAMPO = "Campo Faltante";
	private static final String ASUNTO_REGISTRO = "Registrado";

	private static final String ASUNTO_ERROR_LOGIN = "Login error";
	private static final String ASUNTO_LOGIN = "Login exitoso";
	
	
	
	
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
	
	public Boolean registrarUsuario(JTextField login, JPasswordField password, JTextField email, LocalDate fechaNacimiento, Registro ventana) //JTextField pasar en vez de string
	{

		Usuario user = new Usuario(login.getText(),new String(password.getPassword()),email.getText(),fechaNacimiento);
		Font fuente = new Font("Arial", Font.ITALIC, 14);
		if(login.getText().equals("User") && login.getFont().equals(fuente))
		{
			Alerta.INSTANCIA.mostrarAlerta(MENSAJE_USER_VACIO, ASUNTO_ERROR_CAMPO, ventana);
			return false;
			//Alerta.INSTANCIA.mostrarAlerta(MENSAJE_USUARIO_REPETIDO, ASUNTO_ERROR, ventana);
		}else
		{
			Alerta.INSTANCIA.mostrarAlerta(MENSAJE_USUARIO_REGISTRADO, ASUNTO_REGISTRO, ventana);
			catalogoUsuarios.addUsuario(user);
			// TODO: CAMBIAR DE VENTANA
		}
		if(new String(password.getPassword()).equals("Password") && password.getFont().equals(fuente))
		{
			Alerta.INSTANCIA.mostrarAlerta(MENSAJE_PASSWORD_VACIA, ASUNTO_ERROR_CAMPO, ventana);
			return false;
		}
		if(email.getText().equals("Email") && password.getFont().equals(fuente))
		{
			Alerta.INSTANCIA.mostrarAlerta(MENSAJE_EMAIL_VACIO, ASUNTO_ERROR_CAMPO, ventana);
			return false;
		}
		if(fechaNacimiento == null)
		{
			Alerta.INSTANCIA.mostrarAlerta(MENSAJE_FECHA_VACIA, ASUNTO_ERROR_CAMPO, ventana);
			return false;
		}
			if(!adaptadorUsuario.registrarUsuario(user))
			{
				Alerta.INSTANCIA.mostrarAlerta(MENSAJE_USUARIO_REPETIDO, ASUNTO_ERROR, ventana);
				return false;
			}else
			{
				Alerta.INSTANCIA.mostrarAlerta(MENSAJE_USUARIO_REGISTRADO, ASUNTO_REGISTRO, ventana);
				catalogoUsuarios.addUsuario(user);
				return true;
			}
		
	}
	
	public Boolean loginUsuario(JTextField login, JPasswordField password,Inicio ventana)
	{
		List<Usuario> lista = adaptadorUsuario.recuperarTodosUsuarios();
		Font fuente = new Font("Arial", Font.ITALIC, 14);
		for(Usuario us : lista)
		{
			if(us.getLogin().equals(login.getText()) && us.getPassword().equals(new String(password.getPassword()) ) && !login.getFont().equals(fuente) && !password.getFont().equals(fuente))
			{
				Alerta.INSTANCIA.mostrarAlerta(MENSAJE_USUARIO_LOGIN,ASUNTO_LOGIN , ventana);
				return true;
			}
			else if(us.getLogin().equals(login.getText()) && !us.getPassword().equals(new String(password.getPassword())))
			{
				Alerta.INSTANCIA.mostrarAlerta(MENSAJE_CONTRASEÑA_LOGIN_FALLO,ASUNTO_ERROR_LOGIN , ventana);
				return false;
			}
		}
		Alerta.INSTANCIA.mostrarAlerta(MENSAJE_USUARIO_LOGIN_FALLO,ASUNTO_ERROR_LOGIN , ventana);
		return false;
		
	}
	
	public void registrarGit(String user, String password)
	{
		GitHub github = null;
		try {
			github = new GitHubBuilder().withPassword(user,password).build();
			new GitHubBuilder();
			System.out.println("¿Login válido?:" + github.isCredentialValid());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}

	}
}
