package umu.tds.persistencia;

import java.util.List;
import umu.tds.negocio.Usuario;

public interface IAdaptadorUsuarioDAO {

	public boolean registrarUsuario(Usuario cliente);
	//public void borrarCliente(Cliente cliente);
	//public void modificarCliente(Usuario cliente);
	public Usuario recuperarUsuario(int codigo);
	public List<Usuario> recuperarTodosUsuarios();
}
