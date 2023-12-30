package umu.tds.negocio;

public enum Reciente {
	PRIMERA, SEGUNDA, TERCERA, CUARTA, QUINTA, NORECIENTE;
	
	public static Reciente fromValor(int valor) {
        switch (valor) {
            case 0:
                return PRIMERA;
            case 1:
                return SEGUNDA;
            case 2:
                return TERCERA;
            case 3:
                return CUARTA;
            case 4:
                return QUINTA;
            case 5:
                return NORECIENTE;
            default:
                throw new IllegalArgumentException("Número no válido para Reciente");
        }
    }
}


