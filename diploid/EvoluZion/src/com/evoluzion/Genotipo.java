package com.evoluzion;

public class Genotipo implements Comparable<Genotipo> {
	Mundo m;

	String nombre, genotipo;
	int cantidad = 1;
	int identificador = 0;

	public Genotipo(String nombre) {

		this.nombre = nombre;

	}

	@Override
	public int compareTo(Genotipo arg0) {
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
