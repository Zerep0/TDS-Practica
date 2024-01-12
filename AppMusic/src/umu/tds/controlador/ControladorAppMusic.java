package umu.tds.controlador;



import umu.tds.observer.*;
import umu.tds.persistencia.DAOException;
import umu.tds.persistencia.FactoriaDAO;
import umu.tds.persistencia.IAdaptadorUsuarioDAO;
import umu.tds.utils.PDF;
import umu.tds.utils.Player;
import cargadorCanciones.Cancion;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSlider;
import javax.swing.JTextField;

import umu.tds.descuentos.Descuento;
import umu.tds.descuentos.FactoriaDescuento;
import umu.tds.negocio.CargadorCanciones;
import umu.tds.negocio.CatalogoCanciones;
import umu.tds.negocio.CatalogoUsuarios;
import umu.tds.negocio.IReproductorListener;
import umu.tds.negocio.Playlist;
import umu.tds.negocio.Usuario;
import umu.tds.vista.Menu;
import umu.tds.vista.MenuBusquedaR;
import umu.tds.vista.MenuHome;
import umu.tds.vista.MenuPlaylist;
import umu.tds.vista.MenuPremium;
import umu.tds.vista.Registro;

public class ControladorAppMusic implements ICancionesListener{

	private static final String CADENA_VACIA = "";
	private static final String ESTILO = "-";
	private static final int PRECIO_BASE = 15;
	
	
	private static ControladorAppMusic unicaInstancia;
		
	private CatalogoUsuarios catalogoUsuarios;
	
	private CatalogoCanciones catalogoCanciones;
	
	private ArrayList<IUsuarioListener> listeners = new ArrayList<IUsuarioListener>();
	private ArrayList<IReproductorListener> listenerReproductor = new ArrayList<IReproductorListener>();
	
	private MenuHome menuHome;
	private MenuPlaylist menuPlaylist;
	private MenuPremium menuPremium;
	private MenuBusquedaR MenuBusquedaR;
	private Menu menu;
	private JPanel panelActualReproduccion;
	
	private FactoriaDAO dao;
	private Usuario usuarioActual;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	
	private LinkedList<JSlider> sliders = new LinkedList<JSlider>();
	private LinkedList<JLabel> labels = new LinkedList<JLabel>();
	private Function<umu.tds.negocio.Cancion,String> parseador = c -> "Canción: " + c.getTitulo() + "\nIntérprete: " + c.getInterprete()
											+ "\nEstilo: " + c.getEstilo() + "\n----------------------------------------------------------------------------------------------------\n";
	
	private JSlider volumen;
	private Timer temporizador = null;
	
	
	private ControladorAppMusic()
	{
		usuarioActual = null;

		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
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
		
		Usuario user = new Usuario(login.getText(),new String(password.getPassword()),email.getText(),fechaNacimiento);
		
		if(catalogoUsuarios.addUsuario(user))
		{
			UsuarioEvent e = new UsuarioEvent(user.getLogin());
			notificarCambioNombre(e);
			usuarioActual = user;
			setFavoritasPlaylist();
			this.menuHome.refrescarRecientes(usuarioActual.getRecientes());
			menuPremium.refrescarMasEscuchadas(catalogoCanciones.getMasEscuchadas());
			if(usuarioActual.isPremium())
				menu.actualizarPremium("Premium");
			return true;
		}
		return false;
	}
	
	public Boolean loginUsuario(JTextField login, JPasswordField password)
	{
		
		// precondiciones
		if(login.getFont().isItalic() || password.getFont().isItalic())
			return false;

		List<Usuario> lista = CatalogoUsuarios.getUnicaInstancia().getUsuarios();
		// encuentra si existe un usuario dada una lista de usuarios con el mismo usuario y contraseña
		
		Usuario usuario = lista.stream().filter(us -> us.getLogin().equals(login.getText()) && us.getPassword().equals(new String(password.getPassword())))
												  .findFirst().orElse(null);
		if(usuario != null)
		{
			UsuarioEvent e = new UsuarioEvent(usuario.getLogin());
			notificarCambioNombre(e);
			usuarioActual = usuario;
			setFavoritasPlaylist();
			menuHome.refrescarRecientes(usuarioActual.getRecientes());
			menuPremium.refrescarMasEscuchadas(catalogoCanciones.getMasEscuchadas());
			if(usuarioActual.isPremium())
				menu.actualizarPremium("Premium");
			return true;
		}else return false;

			
	}
	
	public boolean registrarIniciarGit(JTextField usuario, String pathOuth)
	{
		
		if(usuario.getFont().isItalic())
			return false;
		try {
			GitHub github = GitHubBuilder.fromPropertyFile(pathOuth).build();

			if (github.isCredentialValid()) {
				GHUser ghuser = github.getMyself();
				if(ghuser.getLogin().equals(usuario.getText()))
				{
					List<Usuario> lista = CatalogoUsuarios.getUnicaInstancia().getUsuarios();
					usuarioActual = lista.stream().filter(us -> us.getLogin().equals(usuario.getText())).findFirst().orElse(null);
					if(usuarioActual == null)
					{
						usuarioActual = new Usuario(usuario.getText(),"none",ghuser.getEmail(),ghuser.getCreatedAt()
								.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
						catalogoUsuarios.addUsuario(usuarioActual);
					}
					UsuarioEvent e = new UsuarioEvent(usuarioActual.getLogin());
					notificarCambioNombre(e);
					setFavoritasPlaylist();
					this.menuHome.refrescarRecientes(usuarioActual.getRecientes());
					menuPremium.refrescarMasEscuchadas(catalogoCanciones.getMasEscuchadas());
					if(usuarioActual.isPremium())
						menu.actualizarPremium("Premium");
				}
				return (ghuser.getLogin().equals(usuario.getText()) && github.isCredentialValid());
			}

		} catch (IOException e) {
			System.out.println("Archivo con un formato no especifico " + e.getStackTrace());
		}
		return false;

	}
	

	
	public void cargarCanciones(String rutaArchivo)
	{
		umu.tds.negocio.CargadorCanciones.INSTANCE.setArchivoCanciones(rutaArchivo);
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
		List<umu.tds.negocio.Cancion> lista = new LinkedList<umu.tds.negocio.Cancion>();
		for (Cancion c : e.getCanciones().getCancion()) {
			umu.tds.negocio.Cancion can = new umu.tds.negocio.Cancion(c.getTitulo(),c.getURL(),c.getEstilo(),c.getInterprete());
			System.out.println("Cancion: " + c.getTitulo() + " " + c.getURL() + " " + c.getEstilo() + " " + c.getInterprete()+ " ");
			lista.add(can);
		}
		
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
	
	public void cambiarImagenFavorito()
	{
		MenuBusquedaR.entrarVentana();
	}
	
	public void setMenuHome(MenuHome menuHome, JSlider slider, JLabel etiquetaTiempo)
	{
		this.menuHome = menuHome;
		listenerReproductor.add(menuHome);
		sliders.add(slider);
		labels.add(etiquetaTiempo);
	}
	
	public void setMenuPlaylist(MenuPlaylist menuPlaylist, JSlider slider, JLabel etiquetaTiempo)
	{
		this.menuPlaylist = menuPlaylist;
		listenerReproductor.add(menuPlaylist);
		sliders.add(slider);
		labels.add(etiquetaTiempo);
	}
	
	public void setMenuBusquedaR(MenuBusquedaR menuBusquedaR, JSlider slider, JLabel etiquetaTiempo)
	{
		this.MenuBusquedaR = menuBusquedaR;
		listenerReproductor.add(menuBusquedaR);
		sliders.add(slider);
		labels.add(etiquetaTiempo);
	}
	
	public void setMenuPremium(MenuPremium menuPremium, JSlider slider, JLabel etiquetaTiempo)
	{
		this.menuPremium = menuPremium;
		listenerReproductor.add(menuPremium);
		sliders.add(slider);
		labels.add(etiquetaTiempo);
;	}
	
	public void setMenu(Menu menu)
	{
		this.menu = menu;
	}
	
	public void reproducirCancion(String play, umu.tds.negocio.Cancion c)
	{
		if(play.equals("play") && (getCancionReproduciendose() == null || !getCancionReproduciendose().equals(c)))
		{
			actualizarNumReproducciones(c);
		}
		if(play.equals("play") && getCancionReproduciendose() != null && !getCancionReproduciendose().equals(c))
		{
			Player.INSTANCE.play("stop",getCancionReproduciendose(), sliders, labels);
		}
		Player.INSTANCE.play(play,c, sliders, labels);
		if(play.equals("play"))
		{
			usuarioActual.addReciente(c,false);
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
		}else if(panelActualReproduccion instanceof MenuPlaylist)
		{
			((MenuPlaylist) panelActualReproduccion).cancionAlFinalizar();
		}else
		{
			((MenuPremium) panelActualReproduccion).cancionAlFinalizar();
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
	

	public void actualizarNumReproducciones(umu.tds.negocio.Cancion cancion)
	{
		catalogoCanciones.incrementarReproducciones(cancion);
		menuPremium.refrescarMasEscuchadas(catalogoCanciones.getMasEscuchadas());
	}
	
	public LinkedList<String> getEstilosMusicales()
	{
		return catalogoCanciones.getEstilosMusicales();
	}
	
	public void crearPDF(File carpeta)
	{
		System.out.println(carpeta.getAbsolutePath());
		Document pdf = PDF.INSTANCE.crearDocumento(carpeta.getAbsolutePath() + "\\prueba.pdf");
		PDF.INSTANCE.abrirDocumento(pdf, "AppMusic");
		Chapter pagina = PDF.INSTANCE.crearSeccion();
		PDF.INSTANCE.añadirParrafo(pagina, 
				PDF.INSTANCE.crearLinea("AppMusic", PDF.INSTANCE.chapterFont), Element.ALIGN_CENTER);
		PDF.INSTANCE.añadirParrafo(pagina, 
				PDF.INSTANCE.crearLinea("\n\nCANCIONES MÁS ESCUCHADAS DEL MOMENTO", PDF.INSTANCE.paragraphFont), Element.ALIGN_CENTER);
		PDF.INSTANCE.añadirParrafo(pagina, 
				PDF.INSTANCE.crearLinea("\n\n", PDF.INSTANCE.paragraphFont), Element.ALIGN_CENTER);
		for(String playlist : usuarioActual.getNombresPlaylists())
		{
			PDF.INSTANCE.añadirParrafo(pagina,
					PDF.INSTANCE.crearLinea("\n\nPlaylist " + playlist + "\n\n", PDF.INSTANCE.paragraphFont), Element.ALIGN_LEFT);
			for(umu.tds.negocio.Cancion c : usuarioActual.getCancionesPlaylist(playlist))
			{
				PDF.INSTANCE.añadirParrafo(pagina,
						PDF.INSTANCE.crearLinea(parseador.apply(c), PDF.INSTANCE.normalFont), Element.ALIGN_LEFT);
			}
		}
		PDF.INSTANCE.añadirSeccion(pdf, pagina);
		PDF.INSTANCE.cerrarDocumento(pdf);
	}
	
	public void actualizarSaldoUsuario()
	{
		adaptadorUsuario.actualizarSaldo(usuarioActual, usuarioActual.getSaldo());
	}
	
	public boolean pagarPremium()
	{
		double saldoActual;
		Optional<Descuento> miDescuento = FactoriaDescuento.INSTANCE.getDescuento(usuarioActual);
		if(miDescuento.isEmpty())
			saldoActual = usuarioActual.getSaldo() - PRECIO_BASE;
		else saldoActual = usuarioActual.getSaldo() - PRECIO_BASE * miDescuento.get().getDescuento();
		
		
		if(saldoActual >= 0)
		{
			if(!usuarioActual.isPremium())
				simularTiempo();
			usuarioActual.setSaldo(saldoActual);
			usuarioActual.setPremium(true);
			adaptadorUsuario.actualizarSaldo(usuarioActual, saldoActual);
			adaptadorUsuario.actualizarPremium(usuarioActual, true);
			return true;
		}else
		{
			usuarioActual.setPremium(false);
			adaptadorUsuario.actualizarPremium(usuarioActual, false);
			return false;
		}
	}
	
	public boolean isPremium()
	{
		return usuarioActual.isPremium();
	}
	
	public void simularTiempo()
	{

    	  temporizador = new Timer();
    	  TimerTask task = new TimerTask() {
              @Override
              public void run() {
              	if(pagarPremium())
  				{
  					menu.actualizarPremium("Premium");
  				}else
  				{
  					menu.actualizarPremium("Obtener Premium");
  					JOptionPane.showMessageDialog(null, "Subscricion Caducada", "Aviso de pago", JOptionPane.INFORMATION_MESSAGE);
  					temporizador.cancel();
  					return;
  				}
              }
          };
          temporizador.schedule(task, 10000, 10000);
      }
       
	
}
