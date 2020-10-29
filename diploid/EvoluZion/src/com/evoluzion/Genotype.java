package com.evoluzion;

public class Genotype implements Comparable<Genotype> {
	World world;

	String name, genotype;
	int quantity = 1;
	int identifier = 0;

	public Genotype(String name) {

		this.name = name;

	}

	@Override
	public int compareTo(Genotype arg0) {
		{
			// The order of two Organism's is determined by the name.
			String p2Name = arg0.name;

			if (name.compareTo(p2Name) < 0)
				return 1; // or any negative int
			else if (name.compareTo(p2Name) > 0)
				return -1; // or any positive int
			else
				return 0;
		}
	}

}
