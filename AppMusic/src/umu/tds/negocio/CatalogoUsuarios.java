package umu.tds.negocio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import umu.tds.persistencia.DAOException;
import umu.tds.persistencia.FactoriaDAO;
import umu.tds.persistencia.IAdaptadorUsuarioDAO;




/* El cat�logo mantiene los objetos en memoria, en una tabla hash
 * para mejorar el rendimiento. Esto no se podr�a hacer en una base de
 * datos con un n�mero grande de objetos. En ese caso se consultaria
 * directamente la base de datos
 */
public class CatalogoUsuarios {
	private Map<String,Usuario> usuarios; 
	private static CatalogoUsuarios unicaInstancia;
	
	private FactoriaDAO dao;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	
	private CatalogoUsuarios() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorUsuario = dao.getUsuarioDAO();
  			usuarios = new HashMap<String,Usuario>();
  			this.cargarCatalogo();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	
	public static CatalogoUsuarios getUnicaInstancia(){
		if(unicaInstancia == null)
		{
			unicaInstancia = new CatalogoUsuarios();
		}
		return unicaInstancia;
	}
	
	/*devuelve todos los clientes*/
	public List<Usuario> getUsuarios(){
		LinkedList<Usuario> lista = new LinkedList<Usuario>();
		for (Usuario u : usuarios.values()) 
			lista.add(u);
		System.out.println(lista);
		return lista;
	}
	
	public Usuario getUsuario(int id) {
		for (Usuario c:usuarios.values()) {
			if (c.getId()==id) return c;
		}
		return null;
	}
	public Usuario getUsuario(String login) {
		return usuarios.get(login); 
	}
	
	public boolean addUsuario(Usuario usu) {
		if(!adaptadorUsuario.registrarUsuario(usu))
		{
			return false;
		}else usuarios.put(usu.getLogin(),usu);
		return true;
	}
	
	/*Recupera todos los clientes para trabajar con ellos en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<Usuario> usuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		 for (Usuario user : usuariosBD) 
			     usuarios.put(user.getLogin(), user);
	}

	public void borrarUsuarios() {
		usuarios.clear();
		adaptadorUsuario.borrarUsuarios();
	}
	
	
	
}
