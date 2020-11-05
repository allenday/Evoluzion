package com.evoluzion;

public class Allele implements Comparable<Allele> {

	String name;
	int quantity = 1;
	int identifier = 0;
	String name2;
	String sequence;

	public Allele(String name, int identifier, String sequence) {

		this.name = name;
		this.identifier = identifier;
		this.sequence = sequence;

		name2 = name + identifier;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Allele arg0) {

		// The order of two Organism's is determined by the name.
		String p2Name = arg0.name2;
		int result = name2.compareTo(p2Name);
		if (result < 0)
			return 1; // or any negative int
		else if (result > 0)
			return -1; // or any positive int
		else
			return 0;

	}

}
