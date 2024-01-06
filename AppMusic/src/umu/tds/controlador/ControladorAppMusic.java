package umu.tds.controlador;



import umu.tds.observer.*;
import umu.tds.persistencia.DAOException;
import umu.tds.persistencia.FactoriaDAO;
import umu.tds.persistencia.IAdaptadorUsuarioDAO;
import umu.tds.utils.Player;
import cargadorCanciones.Cancion;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSlider;
import javax.swing.JTextField;

import umu.tds.negocio.CargadorCanciones;
import umu.tds.negocio.CatalogoCanciones;
import umu.tds.negocio.CatalogoUsuarios;
import umu.tds.negocio.IReproductorListener;
import umu.tds.negocio.Playlist;
import umu.tds.negocio.Usuario;

import umu.tds.vista.Alerta;
import umu.tds.vista.Inicio;
import umu.tds.vista.MenuBusquedaR;
import umu.tds.vista.MenuHome;
import umu.tds.vista.MenuPlaylist;
import umu.tds.vista.Registro;

public class ControladorAppMusic implements ICancionesListener{
	private static final String MENSAJE_USUARIO_REPETIDO = "El nombre de usuario ya esta cogido";
	private static final String MENSAJE_USUARIO_REGISTRADO = "Se ha registrado con exito";
	private static final String MENSAJE_USUARIO_LOGIN = "Se ha iniciado sesion correctamente";
	private static final String MENSAJE_LOGIN_FALLO = "Usuario o contraseña inválidos";
	
	private static final String ASUNTO_ERROR = "Usuario repetido";
	private static final String ASUNTO_ERROR_CAMPO = "Campo Faltante";
	private static final String ASUNTO_REGISTRO = "Registrado";

	private static final String ASUNTO_ERROR_LOGIN = "Login error";
	private static final String ASUNTO_LOGIN = "Login exitoso";
	
	private static final String CADENA_VACIA = "";
	private static final String ESTILO = "Estilo";
	
	
	private String mensaje_error;  //Variable global para errores de falta de campos rellenados
	
	private static ControladorAppMusic unicaInstancia;
		
	private CatalogoUsuarios catalogoUsuarios;
	
	private CatalogoCanciones catalogoCanciones;
	
	private ArrayList<IUsuarioListener> listeners = new ArrayList<IUsuarioListener>();
	private ArrayList<IReproductorListener> listenerReproductor = new ArrayList<IReproductorListener>();
	
	private MenuHome menuHome;
	private MenuPlaylist menuPlaylist;
	private JPanel panelActualReproduccion;
	
	private FactoriaDAO dao;
	private Usuario usuarioActual;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	
	private LinkedList<JSlider> sliders = new LinkedList<JSlider>();
	private LinkedList<JLabel> labels = new LinkedList<JLabel>();
	
	private JSlider volumen;
	
	private ControladorAppMusic()
	{
		usuarioActual = null;

		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inicializarCatalogos();
		inicializarAdaptadores();
		
		CargadorCanciones.INSTANCE.addListener(this);
		
	}
	
	public static ControladorAppMusic getInstancia()
	{
		if (unicaInstancia == null)
			unicaInstancia = new ControladorAppMusic();
		return unicaInstancia;
	}

	private void inicializarCatalogos() {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
		catalogoCanciones = CatalogoCanciones.getUnicaInstancia();
	}
	public void inicializarAdaptadores()
	{
		adaptadorUsuario = dao.getUsuarioDAO();
	}
	public Boolean registrarUsuario(JTextField login, JPasswordField password, JTextField email, LocalDate fechaNacimiento, Registro ventana) //JTextField pasar en vez de string
	{
		mensaje_error = "No estan los siguientes campos rellenados: ";
		boolean error = errorCreacion(login,password,email,fechaNacimiento);
		if(error)
		{
			Alerta.INSTANCIA.mostrarAlerta(mensaje_error, ASUNTO_ERROR_CAMPO, ventana);
			return false;
		}
		
		Usuario user = new Usuario(login.getText(),new String(password.getPassword()),email.getText(),fechaNacimiento);
		
		if(!catalogoUsuarios.addUsuario(user))
		{
			Alerta.INSTANCIA.mostrarAlerta(MENSAJE_USUARIO_REPETIDO, ASUNTO_ERROR, ventana);
			return false;
		}else
		{
			Alerta.INSTANCIA.mostrarAlerta(MENSAJE_USUARIO_REGISTRADO, ASUNTO_REGISTRO, ventana);
			UsuarioEvent e = new UsuarioEvent(user.getLogin());
			notificarCambioNombre(e);
			usuarioActual = user;
			setFavoritasPlaylist();
			this.menuHome.refrescarRecientes(usuarioActual.getRecientes());
			return true;
		}
	}
	
	public Boolean loginUsuario(JTextField login, JPasswordField password,Inicio ventana)
	{
		
		// precondiciones
		if(login.getFont().isItalic() || password.getFont().isItalic())
		{
			// alertas
			return false;
		}
		List<Usuario> lista = CatalogoUsuarios.getUnicaInstancia().getUsuarios();
		// encuentra si existe un usuario dada una lista de usuarios con el mismo usuario y contraseña
		
		Usuario usuario = lista.stream().filter(us -> us.getLogin().equals(login.getText()) && us.getPassword().equals(new String(password.getPassword())))
												  .findFirst().orElse(null);
		if(usuario != null)
		{
			Alerta.INSTANCIA.mostrarAlerta(MENSAJE_USUARIO_LOGIN,ASUNTO_LOGIN , ventana);
			UsuarioEvent e = new UsuarioEvent(usuario.getLogin());
			notificarCambioNombre(e);
			usuarioActual = usuario;
			setFavoritasPlaylist();
			this.menuHome.refrescarRecientes(usuarioActual.getRecientes());
			return true;
		}else 
		{
			Alerta.INSTANCIA.mostrarAlerta(MENSAJE_LOGIN_FALLO,ASUNTO_ERROR_LOGIN , ventana);
			return false;
		}
			
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
	

	
	public void cargarCanciones(String rutaArchivo)
	{
		umu.tds.negocio.CargadorCanciones.INSTANCE.setArchivoCanciones(rutaArchivo);
	}
	

	
	
	
	
	//--------------------------------------METODOS AUXILIARES---------------------------------------------
	private boolean isErrorTexto(JTextField text,String campo)
	{
		if(text.getText().equals(campo) && text.getFont().isItalic())
		{
			return true;
		}
		return false;
	}
	
	private boolean isErrorContraseña(JPasswordField password,String campo)
	{
		if(new String(password.getPassword()).equals(campo) && password.getFont().isItalic())
		{
			return true;
		}
		return false;
	}
	
	private boolean errorCreacion(JTextField login, JPasswordField password, JTextField email, LocalDate fechaNacimiento)
	{
		Boolean error = false;
		if(isErrorTexto(login,"User"))
		{
			error = true;
			mensaje_error += "Login ";
		}
		if(isErrorContraseña(password, "Password"))
		{
			error = true;
			mensaje_error += "Password ";
		}
		if(isErrorTexto(email,"Email"))
		{
			error = true;
			mensaje_error += "Email ";
		}
		if(fechaNacimiento == null)
		{
			error = true;
			mensaje_error += "FechaNacimiento";
		}
		return error;
	}
	
	public synchronized void addUsuarioListener(IUsuarioListener listener){
		listeners.add(listener);
	}
	
	@SuppressWarnings("unchecked")
	void notificarCambioNombre(UsuarioEvent e)
	{
		ArrayList<IUsuarioListener> lista;
		synchronized (this) {
			lista = (ArrayList<IUsuarioListener>) listeners.clone();
		}
		for(IUsuarioListener users : lista)
		{
			users.actualizar(e);
		}
	}
	
	
	
	@Override
	public void actualizarCanciones(CancionEvent e) {
		// TODO: convertir de Cancion a Cancion jaja
		List<umu.tds.negocio.Cancion> lista = new LinkedList<umu.tds.negocio.Cancion>();
		for (Cancion c : e.getCanciones().getCancion()) {
			umu.tds.negocio.Cancion can = new umu.tds.negocio.Cancion(c.getTitulo(),c.getURL(),c.getEstilo(),c.getInterprete());
			System.out.println("Cancion: " + c.getTitulo() + " " + c.getURL() + " " + c.getEstilo() + " " + c.getInterprete()+ " ");
			lista.add(can);
		}
		
		// TODO: sincronizar list
		catalogoCanciones.registrarCanciones(lista);
		
	}
	
	public ArrayList<umu.tds.negocio.Cancion> aplicarFiltros(JTextField titulo, JTextField interprete, String estilo, boolean cancionFavorita)
	{
		ArrayList<umu.tds.negocio.Cancion> cancionesEncontradas = new ArrayList<>(CatalogoCanciones.getUnicaInstancia().getCanciones());
		for(umu.tds.negocio.Cancion c : cancionesEncontradas)
		{
			System.out.println(c);
		}
		if(!titulo.getText().equals(CADENA_VACIA) && !titulo.getFont().isItalic())
		{
			cancionesEncontradas = (ArrayList<umu.tds.negocio.Cancion>) cancionesEncontradas.stream()
					.filter(c -> c.getTitulo().contains(titulo.getText())).collect(Collectors.toList());
		}
		if(!interprete.getText().equals(CADENA_VACIA) && !interprete.getFont().isItalic())
		{
			cancionesEncontradas = (ArrayList<umu.tds.negocio.Cancion>) cancionesEncontradas.stream()
					.filter(c -> c.getInterprete().contains(interprete.getText())).collect(Collectors.toList());
		}
		if(!estilo.equals(ESTILO))
		{
			cancionesEncontradas = (ArrayList<umu.tds.negocio.Cancion>) cancionesEncontradas.stream()
					.filter(c -> c.getEstilo().equals(estilo)).collect(Collectors.toList());
		}
		if(cancionFavorita)
		{
			cancionesEncontradas = (ArrayList<umu.tds.negocio.Cancion>) cancionesEncontradas.stream()
					.filter(c -> usuarioActual.getFavoritas().contains(c)).collect(Collectors.toList());
		}
		return cancionesEncontradas;
	}
	
	public void actualizarFavorito(boolean esFavorita, umu.tds.negocio.Cancion c)
	{
		if(esFavorita)
			usuarioActual.addFavorita(c);
		else usuarioActual.removeFavorita(c);
		adaptadorUsuario.actualizar(usuarioActual.getFavoritasNum(),usuarioActual, "favoritas");
	}
	
	public void setMenuHome(MenuHome menuHome, JSlider slider, JLabel etiquetaTiempo)
	{
		this.menuHome = menuHome;
		listenerReproductor.add(menuHome);
		sliders.add(slider);
		labels.add(etiquetaTiempo);
;	}
	
	public void setMenuPlaylist(MenuPlaylist menuPlaylist, JSlider slider, JLabel etiquetaTiempo)
	{
		this.menuPlaylist = menuPlaylist;
		listenerReproductor.add(menuPlaylist);
		sliders.add(slider);
		labels.add(etiquetaTiempo);
	}
	
	public void setMenuBusquedaR(MenuBusquedaR menuBusquedaR, JSlider slider, JLabel etiquetaTiempo)
	{
		listenerReproductor.add(menuBusquedaR);
		sliders.add(slider);
		labels.add(etiquetaTiempo);
	}
	
	
	
	public void reproducirCancion(String play, umu.tds.negocio.Cancion c)
	{
		if(getCancionReproduciendose() != null)
		{
			Player.INSTANCE.play("stop",c, sliders, labels);
		}
		Player.INSTANCE.play(play,c, sliders, labels);
		if(play.equals("play"))
		{
			usuarioActual.addReciente(c);
			adaptadorUsuario.actualizar(usuarioActual.getRecientesNum(),usuarioActual, "recientes");
			menuHome.refrescarRecientes(usuarioActual.getRecientes());
		}
	}
	
	public umu.tds.negocio.Cancion getCancionReproduciendose()
	{
		return Player.INSTANCE.getCancionReproduciendo();
	}
	
	public void actualizarEstadoReproductor(String pausa)
	{
		for(IReproductorListener r : listenerReproductor)
			r.actualizarEstadoReproductor(pausa);
	}
	
	// play
	public void actualizarPanelReproduccion(JPanel reproActual)
	{
		panelActualReproduccion = reproActual;
	}
	
	// forward o un rewind
	public boolean comprobarPaneles(JPanel panelActual)
	{
		boolean devolver = false;
		if(panelActual == panelActualReproduccion)
			devolver = false;
		else devolver = true;
		panelActualReproduccion = panelActual;
		return devolver;
	}
	
	public void siguienteAlFinalizar()
	{
		if(panelActualReproduccion instanceof MenuBusquedaR)
		{
			((MenuBusquedaR) panelActualReproduccion).cancionAlFinalizar();
		}else if(panelActualReproduccion instanceof MenuHome)
		{
			((MenuHome) panelActualReproduccion).cancionAlFinalizar();
		}else
		{
			((MenuPlaylist) panelActualReproduccion).cancionAlFinalizar();
		}
	}
	
	public boolean isFavorita(umu.tds.negocio.Cancion c)
	{
		return usuarioActual.isFavorita(c);
	}
	
	public Boolean registrarPlaylist(JTextField playlist)
	{
		if(playlist.getFont().isItalic())
		{
			return false;
		}
		
		usuarioActual.setPlaylist(playlist.getText(),new Playlist(playlist.getText()));  //Poner tambien lista de canciones seleccionadas en null
		return true;
		
	}
	
	public LinkedList<umu.tds.negocio.Cancion> getPlaylistCanciones(String playlist)
	{
		return usuarioActual.getCancionesPlaylist(playlist);
	}
	
	public Boolean eliminarPlaylist(JTextField playlist)
	{
		if(playlist.getFont().isItalic() || playlist.getText().equals("Favoritas"))
		{
			return false;
		}
		usuarioActual.erasePlaylist(playlist.getText());
		return true;
	}
	
	public Boolean eliminarPlaylistSelec(String playlist)
	{
		if(playlist.equals("Favoritas"))
		{
			return false;
		}
		usuarioActual.erasePlaylist(playlist);
		return true;
	}
	
	public void setFavoritasPlaylist()
	{
		Playlist favoritasPlaylist = new Playlist("Favoritas");
		LinkedList<umu.tds.negocio.Cancion> favs = usuarioActual.getFavoritas();
		for (umu.tds.negocio.Cancion c : favs) {
			favoritasPlaylist.addCancion(c);
		}
		usuarioActual.setPlaylist(favoritasPlaylist.getNombrePlaylist(), favoritasPlaylist);
	}
	
	public void anadirCancionPlaylist(String playlist,umu.tds.negocio.Cancion c)
	{
		if(c != null && !playlist.equals("Favoritas") && usuarioActual.isPlaylist(playlist))
		{
			LinkedList<umu.tds.negocio.Cancion> canciones = getPlaylistCanciones(playlist);
			if(canciones != null && !canciones.contains(c))
			{		
				usuarioActual.setNuevasCanciones(playlist, c);
			}
		}
	}
	
	public void anadirCancionesPlaylist(LinkedList<String> playlists,umu.tds.negocio.Cancion c)
	{
		for(String playlist : playlists)
		{
			anadirCancionPlaylist(playlist,c);
		}
	}
	{
		
	}
	
	public void setSliderVolumen(JSlider volumen)
	{
		this.volumen = volumen;
	}
	
	public void actualizarVolumen()
	{
		Player.INSTANCE.actualizarVolumen(volumen.getValue() / 100f);
	}
	
	public float getVolumen()
	{
		return volumen.getValue() / 100f;
	}
	
	public ArrayList<String >getNombresPlaylist()
	{
		return usuarioActual.getNombresPlaylists();
	}
	
	public void refrescarPlaylists()
	{
		menuPlaylist.actualizarTabla();
	}
}
