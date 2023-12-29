package umu.tds.negocio;

public class Cancion {
	private int id;
	private String titulo, ruta, estilo, interprete;
	private int numReproducciones;
	private boolean favorita;
	
	public Cancion(String titulo,String ruta, String estilo, String interprete) {
		this.id = 0;
		this.titulo = titulo;
		this.ruta = ruta;
		this.estilo = estilo;
		this.interprete = interprete;
		numReproducciones = 0;
		favorita = false;
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
	public boolean isFavorita() {
		return favorita;
	}
	
	public void setFavorita(boolean favorita)
	{
		this.favorita = favorita;
	}
	
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
}
