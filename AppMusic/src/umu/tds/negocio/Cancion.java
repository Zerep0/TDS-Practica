package umu.tds.negocio;

public class Cancion {
	private int id;
	private String titulo, ruta, estilo, interprete;
	private int numReproducciones;
	private boolean marcado;
	
	public Cancion(String titulo,String ruta, String estilo, String interprete) {
		this.id = 0;
		this.titulo = titulo;
		this.ruta = ruta;
		this.estilo = estilo;
		this.interprete = interprete;
		numReproducciones = 0;
		marcado = false;
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
	public boolean isMarcado() {
		return marcado;
	}
	
	public void setMarcado(boolean marcado)
	{
		this.marcado = marcado;
	}
	
	public void setEstilo(String estilo)
	{
		this.estilo = estilo;
	}
	
	public void setNumReproducciones(int numReproducciones)
	{
		this.numReproducciones = numReproducciones;
	}
}
