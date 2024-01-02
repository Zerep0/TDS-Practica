package umu.tds.negocio;

import java.util.Objects;

public class Cancion {
	private int id;
	private String titulo, ruta, estilo, interprete;
	private int numReproducciones;
	
	

	public Cancion(String titulo,String ruta, String estilo, String interprete) {
		this.id = 0;
		this.titulo = titulo;
		this.ruta = ruta;
		this.estilo = estilo;
		this.interprete = interprete;
		numReproducciones = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return ruta;
	}

	/**
	 * @return the estilo
	 */
	public String getEstilo() {
		return estilo;
	}

	/**
	 * @return the interprete
	 */
	public String getInterprete() {
		return interprete;
	}

	/**
	 * @return the numReproducciones
	 */
	public int getNumReproducciones() {
		return numReproducciones;
	}

	/**
	 * @return the marcado
	 */
	
	public void setEstilo(String estilo)
	{
		this.estilo = estilo;
	}
	
	

	public void setNumReproducciones(int numReproducciones)
	{
		this.numReproducciones = numReproducciones;
	}
	
	public String toString()
	{
		return titulo + " ~ " + interprete;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(estilo, id, interprete, ruta, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cancion other = (Cancion) obj;
		return Objects.equals(estilo, other.estilo) && id == other.id && Objects.equals(interprete, other.interprete)
				&& Objects.equals(ruta, other.ruta) && Objects.equals(titulo, other.titulo);
	}
	
}
