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

	@Override
	public IAdaptadorProductoDAO getProductoDAO() {
		return AdaptadorProductoTDS.getUnicaInstancia();
	}*/

	@Override
	public IAdaptadorUsuarioDAO getClienteDAO() {
		return AdaptadorUsuarioTDS.getUnicaInstancia();
	}

}
