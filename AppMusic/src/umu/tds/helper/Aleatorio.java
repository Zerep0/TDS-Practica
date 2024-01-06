package umu.tds.helper;

import java.util.Random;

public class Aleatorio {
	public static int generarAletorio(int rangoInferior, int rangoSuperior, int elementoNoRepetir)
	{
		int num;
		while((num = new Random().nextInt(rangoInferior, rangoSuperior)) == elementoNoRepetir)
			num = new Random().nextInt(rangoInferior, rangoSuperior);
		return num;
	}
}
