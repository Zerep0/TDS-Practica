package umu.tds.observer;

public class UsuarioEvent {
	private String login;
	public UsuarioEvent(String login)
	{
		this.login = login;
	}
	public String getNombreUsuario()
	{
		return login;
	}
}
