package umu.tds.negocio;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import umu.tds.controlador.ControladorAppMusic;

public class Usuario {
	private int id;
	private String email, login, password;
	private LocalDate fechaNacimiento;
	private boolean premium;
	private LinkedList<Cancion> cancionesRecientes;
	private LinkedList<Cancion> cancionesFavoritas;
	private static final int MAX_RECIENTES = 10;

	public Usuario(String login, String password, String email, LocalDate fechaNacimiento) {
		this.email = email;
		this.id = 0;
		this.login = login;
		this.password = password;
		this.fechaNacimiento = fechaNacimiento;
		this.premium = false;
		cancionesRecientes = new LinkedList<Cancion>();
		cancionesFavoritas = new LinkedList<Cancion>();
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
	
	public LinkedList<Cancion> getRecientes()
	{
		return cancionesRecientes;
	}
	
	public LinkedList<Integer> getRecientesNum()
	{
		LinkedList<Integer> nums = new LinkedList<Integer>();
		for (umu.tds.negocio.Cancion can : cancionesRecientes) {
			nums.add(can.getId());
		}
		return nums;
	}
	
	public void addReciente(Cancion c)
	{
		if(cancionesRecientes.contains(c))
		{
			cancionesRecientes.remove(c);
			cancionesRecientes.addFirst(c);
		}else
		{
			if(cancionesRecientes.size() == MAX_RECIENTES)
			{
				cancionesRecientes.removeLast();
				cancionesRecientes.addFirst(c);
			}
			else cancionesRecientes.addFirst(c);
		}
	}
	
	public LinkedList<Cancion> getFavoritas()
	{
		return cancionesFavoritas;
	}
	
	public LinkedList<Integer> getFavoritasNum()
	{
		LinkedList<Integer> nums = new LinkedList<Integer>();
		for (umu.tds.negocio.Cancion can : cancionesFavoritas) {
			nums.add(can.getId());
		}
		return nums;
	}
	
	public void addFavorita(Cancion c)
	{
		if(!cancionesFavoritas.contains(c))
			cancionesFavoritas.add(c);
	}
	
	public void removeFavorita(Cancion c)
	{
		cancionesFavoritas.remove(c);
	}
	
	public boolean isFavorita(Cancion c)
	{
		return cancionesFavoritas.contains(c);
	}
}
