package com.evoluzion;

public class Alelo implements Comparable<Alelo> {

	String nombre;
	int cantidad = 1;
	int identificador = 0;
	String nombre2;
	String secuencia;

	public Alelo(String nombre, int identificador, String secuencia) {

		this.nombre = nombre;
		this.identificador = identificador;
		this.secuencia = secuencia;

		nombre2 = nombre + identificador;
	}

	@Override
	public String toString() {
		return nombre;
	}

	@Override
	public int compareTo(Alelo arg0) {

		// The order of two Organism's is determined by the name.
		String p2Name = arg0.nombre2;
		int result = nombre2.compareTo(p2Name);
		if (result < 0)
			return 1; // or any negative int
		else if (result > 0)
			return -1; // or any positive int
		else
			return 0;

	}

}
