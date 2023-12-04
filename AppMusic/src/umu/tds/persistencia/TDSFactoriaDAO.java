package umu.tds.persistencia;

public class TDSFactoriaDAO extends FactoriaDAO {
	public TDSFactoriaDAO () {
	}
	
	/*@Override
	public IAdaptadorVentaDAO getVentaDAO() {
		return AdaptadorVentaTDS.getUnicaInstancia();
	}

	@Override
	public IAdaptadorLineaVentaDAO getLineaVentaDAO() {
		return AdaptadorLineaVentaTDS.getUnicaInstancia();
	}
*/
	@Override
	public IAdaptadorCancionDAO getCancionDAO() {
		return AdaptadorCancionTDS.getUnicaInstancia();
	}

	@Override
	public IAdaptadorUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuarioTDS.getUnicaInstancia();
	}

}
