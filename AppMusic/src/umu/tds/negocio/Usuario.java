package umu.tds.negocio;

import java.time.LocalDate;

public class Usuario {
	private int id;
	private String email, login, password;
	private LocalDate fechaNacimiento;
	private boolean premium;

	public Usuario(String login, String password, String email, LocalDate fechaNacimiento) {
		this.email = email;
		this.id = 0;
		this.login = login;
		this.password = password;
		this.fechaNacimiento = fechaNacimiento;
		this.premium = false;
	}

	/**
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}


	public String getPassword() {
		return password;
	}

	public String getEmail()
	{
		return email;
	}
	
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public boolean isPremium()
	{
		return premium;
	}
	
	public void setPremium(boolean premium)
	{
		this.premium = premium;
	}
}
