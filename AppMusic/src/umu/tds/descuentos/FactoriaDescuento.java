package umu.tds.descuentos;

import java.time.LocalDate;
import java.util.Optional;

import umu.tds.negocio.Usuario;

public enum FactoriaDescuento {
	INSTANCE;
	private static final int JOVEN = 24;
	private static final int JUBILADO = 65;
	
	public Optional<Descuento> getDescuento(Usuario u)
	{
		if((LocalDate.now().getYear() - u.getFechaNacimiento().getYear()) < JOVEN)
			return Optional.of(new DescuentoJoven());
		else if(LocalDate.now().getYear() - u.getFechaNacimiento().getYear() >= JUBILADO)
			return Optional.of(new DescuentoMayor());
		else return Optional.empty();
	}
}
