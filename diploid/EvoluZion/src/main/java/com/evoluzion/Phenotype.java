package com.evoluzion;

public class Phenotype implements Comparable<Phenotype> {
	World world;

	String name;
	int quantity = 1;
	int identifier = 0;

	public Phenotype(World world) {

		this.world = world;
		name = "";

	}

	@Override
	public int compareTo(Phenotype arg0) {
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
