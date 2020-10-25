package com.evoluzion;

public class Fenotipo implements Comparable<Fenotipo> {
	Mundo m;

	String nombre;
	int cantidad = 1;
	int identificador = 0;

	public Fenotipo(Mundo m) {

		this.m = m;
		nombre = "";

	}

	@Override
	public int compareTo(Fenotipo arg0) {
		{
			// The order of two Organism's is determined by the name.
			String p2Name = arg0.nombre;
			;

			if (nombre.compareTo(p2Name) < 0)
				return 1; // or any negative int
			else if (nombre.compareTo(p2Name) > 0)
				return -1; // or any positive int
			else
				return 0;
		}
	}

}
