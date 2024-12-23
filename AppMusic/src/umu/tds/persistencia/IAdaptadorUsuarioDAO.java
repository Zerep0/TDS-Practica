package umu.tds.persistencia;

import java.util.LinkedList;
import java.util.List;
import umu.tds.negocio.Usuario;

public interface IAdaptadorUsuarioDAO {

	public boolean registrarUsuario(Usuario cliente);
	//public void borrarCliente(Cliente cliente);
	//public void modificarCliente(Usuario cliente);
	public Usuario recuperarUsuario(int codigo);
	public List<Usuario> recuperarTodosUsuarios();
	public void actualizar(LinkedList<Integer> nums, Usuario u, String propiedad);
	public String IntegerListToString(LinkedList<Integer> nums);
	public void actualizarSaldo(Usuario u,double saldo);
	public void actualizarPremium(Usuario u,boolean premium);
	public void borrarUsuarios();
}
