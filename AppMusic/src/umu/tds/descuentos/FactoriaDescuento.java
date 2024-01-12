package umu.tds.descuentos;

import java.time.LocalDate;
import java.util.Optional;

import umu.tds.negocio.Usuario;

public enum FactoriaDescuento {
	INSTANCE;
	public Optional<Descuento> getDescuento(Usuario u)
	{
		if((LocalDate.now().getYear() - u.getFechaNacimiento().getYear()) < 18)
			return Optional.of(new DescuentoJoven());
		else if(LocalDate.now().getYear() - u.getFechaNacimiento().getYear() >= 65)
			return Optional.of(new DescuentoMayor());
		else return Optional.empty();
	}
}
