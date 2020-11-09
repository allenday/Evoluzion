/*Copyright 2014 Adolfo R. Zurita*/

/*This file is part of EvoluZion.

 EvoluZion is free software: you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 EvoluZion is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with EvoluZion.  If not, see <http://www.gnu.org/licenses/>.*/

package com.evoluzion;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Organism implements Comparable<Organism> {

	private World world;

	Sprite body, alas, eyes, antennae, headP, resToxinas, sex, sp_pheromone,
			dead;

	float speed;
	float seno, coseno;
	float radius; // radio de los sentidos
	float pheromone;
	double toleranceTemp;
	double optimalTemp;
	float SaludCoefi;
	private final NumberFormat format = new DecimalFormat("0.00");
	double cuadradoTemp;

	float width;
	float height;
	float mutationRate = 1000; // si mutar es true, indica la frecuencia de mutacion
	float capacity; // cantidad maxima de masa y energia que puede portar el
	// organismo
	double energy = 484; // energia del organismo
	double biomass = 1; // biomasa del organismo
	float color; // variable color
	float longevity; // tiempo en que muere de viejo milisegundos
	int timeMitosis; // tiempo en que se divide
	int timeCycle;
	int seconds, seconds2, seconds3;
	int random;// se usa para las operaciones random
	int mark = 1; // 1= false -1= true
	int quantity = 1;
	Vector2 position;
	Vector2 direction;

	Rectangle border;

	// patron de traduccion de las proteinas

	String patternWidth = "..[RKH][R]......S[KRH]..";
	String patternHeight = "..[RKH][R]......S[KRH]..";
	String patternSpeed = ".[RKH][LMI][LMI].....Q[AG]........";
	String patternColor = "ILI";
	String patternPredator = ".....[M].{4,7}[MIV][KRH]..";
	String patternRadius = "..[APTS]..[MILV]F";
	String patternSense = "[M].{4,13}[IV][EV]";
	String patternHunt = "..[PAG][A][L].{4,10}[HKR]...";
	String patternEscape = ".....[QE].{2,15}[RKH][C]......";
	String patternMutationRate = ".M.[LMIV][LMI]...[VLI]..";
	String patternLongevity = ".....[DN][LMIV]......[QE]....[FY]";
	String patternToleranceTemp = "...[VYS]..........[HPF].......";
	String patternResATB = "......[GAP]G..{2,12}[IMLV][IMLV]....";
	String patternPheromone = "M........F[KGS].{2,20}.[LIGSV][LIGSV]..";
	String patronParteNoGen = "M..........DFL........[MI]";

	char[] letras = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z'};

	String fechaNacimiento;

	boolean mutable = true; // puede mutar o no
	boolean carnivore = false; // capacidad de tomar alimentarse de otros
	// organismos
	boolean resistanceATB; // capacidad de resistir Antibioticos
	boolean sense; // capacidad de sensar el entornno por comida
	boolean hunt; // capacidad de buscar su comida
	boolean escape;
	boolean transferred = false;
	boolean male = false;
	boolean intoxicated = false;
	boolean parteNoGen = false;// se reproduce asexualmente

	//modelo de infection viral

	boolean infected = false;
	boolean immune = false;
	int immunity;

	long edad, delta, delta2, deltaToxico, deltaEnfermo, delta3, deltaAleatorio; // se usa
	// para
	// determinar
	// el paso
	// del
	// tiempo
	Genome adn, adn2, adn3, adn4, adn5, adn6, ch1, ch2, ch3, ch4, ch5, ch6,
			ch7, ch8, ch9, ch10, ch11, ch12; // genoma

	int identifier;// indica la especie del organismo
	int carnivoreI;
	int senseI;
	int huntI;
	int escapeI;
	int carniI2;
	int sentI2;
	int cazarI2;
	int escapI2;
	int resistenciaI2;
	int parteNoGenI;
	int parteNoGenI2;
	int resistenciaI;
	int muriendo = 0;

	String cromosoma; // todos los genes juntos
	StringBuffer nombre;

	Array<Vector2> aObjects; // memoria donde se guarda la posicion de las
	// fuentes de alimento
	Array<Organism> aFemales; // memoria donde se guarda la posicion de las
	// hembras
	Array<Vector2> aPredators; // memoria donde se guarda la posicion de las
	// fuentes de peligro
	Array<Allele> aAlleles;
	Array<Genotype> aGenomes;
	char base;// nucleotido

	Allele aColor, aColor2, aAlto, aAlto2;
	Genotype genotype;

	Phenotype phenotype;
	StringBuffer gen;

	public Organism() {

	}

	public Organism(boolean male, Genome adn, Genome adn2, Genome adn3,
					Genome adn4, Genome adn5, Genome adn6, Vector2 position,
					StringBuffer name, World world) {

		this.world = world;

		fechaNacimiento = "" + world.minutes2 + ":" + world.seconds;

		aObjects = new Array<Vector2>();
		aFemales = new Array<Organism>();
		aPredators = new Array<Vector2>();
		aAlleles = new Array<Allele>();
		aGenomes = new Array<Genotype>();

		this.adn = adn;
		this.adn2 = adn2;
		this.adn3 = adn3;
		this.adn4 = adn4;
		this.adn5 = adn5;
		this.adn6 = adn6;
		this.male = male;

		this.position = position;
		this.nombre = name;

		ch1 = new Genome(world, 1, 1, 1, 0, 0, 0);
		ch2 = new Genome(world, 1, 1, 1, 0, 0, 0);

		ch3 = new Genome(world, 1, 1, 1, 0, 0, 0);
		ch4 = new Genome(world, 1, 1, 1, 0, 0, 0);

		ch5 = new Genome(world, 0, 0, 0, 0, 0, 0, 0);
		ch6 = new Genome(world, 0, 0, 0, 0, 0, 0, 0);

		ch7 = new Genome(world, 0, 0, 0, 0, 0, 0, 0);
		ch8 = new Genome(world, 0, 0, 0, 0, 0, 0, 0);

		ch9 = new Genome(world, 0, 0, 0);
		ch10 = new Genome(world, 0, 0, 0);

		ch11 = new Genome(world, 0, 0, 0);
		ch12 = new Genome(world, 0, 0, 0);

		direction = new Vector2();
		border = new Rectangle();

		translate();

		seconds = 0;
		seconds2 = 0;
		seconds3 = 0;
		setTime();
		setEdad();
		setDelta();
		setDelta3();
		setDeltaToxico();
		setDeltaEnfermo();

		// alelos paracomparar
		if (world.collectResistance) {
			aAlleles.add(new Allele(world.tx.ResATB, (int) adn.translateMagnitude(
					adn.resistanceATB, patternResATB) / 10, adn.resistanceATB
					.toString()));//
			aAlleles.add(new Allele(world.tx.ResATB, (int) adn2.translateMagnitude(
					adn2.resistanceATB, patternResATB) / 10,
					adn2.resistanceATB.toString()));
		}//
		if (world.collectHeight) {
			aAlleles.add(new Allele(world.tx.size, (int) adn.translateMagnitude(
					adn.width, patternWidth)
					/ 10
					+ (int) adn.translateMagnitude(adn.height, patternHeight)
					/ 10, adn.height.toString() + adn.height.toString()));
			aAlleles.add(new Allele(world.tx.size, (int) adn2.translateMagnitude(
					adn2.width, patternWidth)
					/ 10
					+ (int) adn2.translateMagnitude(adn2.height, patternHeight)
					/ 10, adn2.height.toString() + adn2.height.toString()));
		}
		if (world.collectSpeed) {
			aAlleles.add(new Allele(world.tx.alas, (int) (adn3.translateMagnitude(
					adn3.speed, patternSpeed) / 9), adn3.speed.toString()));
			aAlleles.add(new Allele(world.tx.alas, (int) (adn4.translateMagnitude(
					adn4.speed, patternSpeed) / 9), adn4.speed.toString()));
		}

		if (world.collectTemp) {
			aAlleles.add(new Allele(world.tx.optimalTemp, (int) (adn.translateMagnitude(
					adn.toleranceTemp, patternToleranceTemp) / 7),
					adn.toleranceTemp.toString()));
			aAlleles.add(new Allele(world.tx.optimalTemp, (int) (adn2.translateMagnitude(
					adn2.toleranceTemp, patternToleranceTemp) / 7),
					adn2.toleranceTemp.toString()));
		}
		if (world.collectPredator) {
			aAlleles.add(new Allele(world.tx.predador, (int) adn.translateMagnitude(
					adn.predator, patternPredator) / 6, adn.predator
					.toString()));
			aAlleles.add(new Allele(world.tx.predador, (int) adn2.translateMagnitude(
					adn2.predator, patternPredator) / 6, adn2.predator
					.toString()));
		}
		if (world.collectSense) {
			aAlleles.add(new Allele(world.tx.senses, (int) adn.translateMagnitude(
					adn.sense, patternSense) / 6, adn.sense.toString()));
			aAlleles.add(new Allele(world.tx.senses, (int) adn2.translateMagnitude(
					adn2.sense, patternSense) / 6, adn2.sense
					.toString()));
		}

		if (world.collectHunt) {
			aAlleles.add(new Allele(world.tx.searchFood, (int) adn3
					.translateMagnitude(adn3.hunt, patternHunt) / 7,
					adn3.hunt.toString()));
			aAlleles.add(new Allele(world.tx.searchFood, (int) adn4
					.translateMagnitude(adn4.hunt, patternHunt) / 7,
					adn4.hunt.toString()));
		}
		if (world.collectEscape) {
			aAlleles.add(new Allele(world.tx.escape, (int) adn3.translateMagnitude(
					adn3.escape, patternEscape) / 7, adn3.escape
					.toString()));
			aAlleles.add(new Allele(world.tx.escape, (int) adn4.translateMagnitude(
					adn4.escape, patternEscape) / 7, adn4.escape
					.toString()));
		}
		if (world.collectRadius) {
			aAlleles.add(new Allele(world.tx.alcanceVisual,
					(int) adn3.translateMagnitude(adn3.radius,
							patternRadius) / 7, adn3.radius.toString()));
			aAlleles.add(new Allele(world.tx.alcanceVisual,
					(int) adn4.translateMagnitude(adn4.radius,
							patternRadius) / 7, adn4.radius.toString()));
		}
		if (world.collectPheromone) {
			aAlleles.add(new Allele(world.tx.feromona, (int) adn3.translateMagnitude(
					adn3.pheromone, patternPheromone) / 30, adn3.pheromone
					.toString()));
			aAlleles.add(new Allele(world.tx.feromona, (int) adn4.translateMagnitude(
					adn4.pheromone, patternPheromone) / 30, adn4.pheromone
					.toString()));
		}

		if (world.colectarParteNoGen) {
			aAlleles.add(new Allele(world.tx.partenogenesis,
					(int) adn3.translateMagnitude(adn3.parteNoGen,
							patronParteNoGen) / 10, adn3.parteNoGen.toString()));
			aAlleles.add(new Allele(world.tx.partenogenesis,
					(int) adn4.translateMagnitude(adn4.parteNoGen,
							patronParteNoGen) / 10, adn4.parteNoGen.toString()));
		}

		if (world.collectMutationRate) {
			aAlleles.add(new Allele(world.tx.fidelidadADNpol,
					(int) ((adn5.translateMagnitude(adn5.mutationRate,
							patternMutationRate) / 8)), adn5.mutationRate.toString()));
			if (!male) {
				aAlleles.add(new Allele(world.tx.fidelidadADNpol, (int) ((adn6
						.translateMagnitude(adn6.mutationRate,
								patternMutationRate) / 8)), adn6.mutationRate
						.toString()));
			}
		}
		if (world.collectLongevity) {
			aAlleles.add(new Allele(world.tx.longevidad,
					(int) (adn5.translateMagnitude(adn5.longevity,
							patternLongevity) / 7), adn5.longevity.toString()));
			if (!male) {
				aAlleles.add(new Allele(world.tx.longevidad, (int) (adn6
						.translateMagnitude(adn6.longevity,
								patternLongevity) / 7), adn6.longevity
						.toString()));
			}
		}
		if (world.collectColor) {
			aAlleles.add(new Allele(world.tx.color, (int) adn5.translateMagnitude(
					adn5.color, patternColor) / 10, adn5.color.toString()));
			if (!male) {
				aAlleles.add(new Allele(world.tx.color, (int) adn6.translateMagnitude(
						adn6.color, patternColor) / 10, adn6.color
						.toString()));
			}
		} else {
		}

		// Genomas para comparar

		gen = new StringBuffer();

		if (world.viewMaleFemale) {
			if (male) {
				gen.append(world.tx.male);
			}
			if (!male) {
				gen.append(world.tx.female);
			}
		}
		var n1 = 0;
		var n2 = 0;

		if (!male) {

			for (var i = 0; i < aAlleles.size; i = i + 2) {
				n1 = aAlleles.get(i).identificador;
				n2 = aAlleles.get(i + 1).identificador;

				if (n1 == n2) {
					gen.append(";a").append(n1).append(" / a").append(n2);
				}
				if (n1 > n2) {
					gen.append(";a").append(n1).append(" / a").append(n2);
				}
				if (n1 < n2) {
					gen.append(";a").append(n2).append(" / a").append(n1);
				}
			}

		}

		if (male) {

			var salto = 2;

			for (var i = 0; i < aAlleles.size; i = i + salto) {

				if (!aAlleles.get(i).nombre.equals(world.tx.color)
						&& !aAlleles.get(i).nombre.equals(world.tx.longevidad)
						&& !aAlleles.get(i).nombre.equals(world.tx.fidelidadADNpol)) {

					n1 = aAlleles.get(i).identificador;
					n2 = aAlleles.get(i + 1).identificador;

					if (n1 == n2) {
						gen.append(";a").append(n1).append(" / a").append(n2);
					}
					if (n1 > n2) {
						gen.append(";a").append(n1).append(" / a").append(n2);
					}
					if (n1 < n2) {
						gen.append(";a").append(n2).append(" / a").append(n1);
					}

				}

				if (aAlleles.get(i).nombre.equals(world.tx.color)
						|| aAlleles.get(i).nombre.equals(world.tx.longevidad)
						|| aAlleles.get(i).nombre.equals(world.tx.fidelidadADNpol)) {

					n1 = aAlleles.get(i).identificador;

					gen.append(";a").append(n1).append(" /");
					salto = 1;
				}

			}

		}

		genotype = new Genotype(gen.toString());

	}

	public float translateSpeed(Genome g1, Genome g2) {
		float fenotipe = 0;

		var gen1 = (g1.translateMagnitude(g1.speed, patternSpeed) / 250)
				/ world.zoom;
		var gen2 = (g2.translateMagnitude(g2.speed, patternSpeed) / 250)
				/ world.zoom;

		if (gen1 == 0 && gen2 == 0) {
			fenotipe = 0;
		}
		if (gen1 == 0 && gen2 > 0) {
			fenotipe = gen2;
		}
		if (gen2 == 0 && gen1 > 0) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0) {
			fenotipe = (gen1 + gen2) / 2;
		}

		return fenotipe;
	}

	public float translateAncho(Genome g1, Genome g2) {
		float fenotipe = 0;

		var gen1 = (g1.translateMagnitude(g1.width, patternWidth) / (224 * world.zoom));
		var gen2 = (g2.translateMagnitude(g2.width, patternWidth) / (224 * world.zoom));

		if (gen1 == 0 && gen2 == 0) {
			fenotipe = 0;
		}
		if (gen1 == 0 && gen2 > 0) {
			fenotipe = gen2;
		}
		if (gen2 == 0 && gen1 > 0) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0 && gen2 > gen1) {
			fenotipe = gen2;
		}
		if (gen2 > 0 && gen1 > 0 && gen2 < gen1) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0 && gen2 == gen1) {
			fenotipe = gen1;
		}

		return fenotipe;
	}

	public float translateAlto(Genome g1, Genome g2) {
		float fenotipe = 0;

		var gen1 = (g1.translateMagnitude(g1.height, patternHeight) / (224 * world.zoom));
		var gen2 = (g2.translateMagnitude(g2.height, patternHeight) / (224 * world.zoom));

		if (gen1 == 0 && gen2 == 0) {
			fenotipe = 0;
		}
		if (gen1 == 0 && gen2 > 0) {
			fenotipe = gen2;
		}
		if (gen2 == 0 && gen1 > 0) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0 && gen2 > gen1) {
			fenotipe = gen2;
		}
		if (gen2 > 0 && gen1 > 0 && gen2 < gen1) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0 && gen2 == gen1) {
			fenotipe = gen1;
		}

		return fenotipe;
	}

	public double translateToleTemp(Genome g1, Genome g2) {
		double fenotipe = 0;

		var gen1 = (g1.translateMagnitude(g1.toleranceTemp,
				patternToleranceTemp) / 126.2552);
		var gen2 = (g2.translateMagnitude(g2.toleranceTemp,
				patternToleranceTemp) / 126.2552);

		if (gen1 == 0 && gen2 == 0) {
			fenotipe = 0;
		}
		if (gen1 == 0 && gen2 > 0) {
			fenotipe = gen2;
		}
		if (gen2 == 0 && gen1 > 0) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0) {
			fenotipe = (gen1 + gen2) / 2;
		}

		return fenotipe;
	}

	public float translateColor(Genome g1, Genome g2) {
		float fenotipe = 0;

		var gen1 = g1.translateMagnitude(g1.color, patternColor) / 10;
		var gen2 = g2.translateMagnitude(g2.color, patternColor) / 10;

		if (gen1 == 0 && gen2 == 0) {
			fenotipe = 0;
		}
		if (gen1 == 0 && gen2 > 0) {
			fenotipe = gen2;
		}
		if (gen2 == 0 && gen1 > 0) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0 && gen2 > gen1) {
			fenotipe = gen2;
		}
		if (gen2 > 0 && gen1 > 0 && gen2 < gen1) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0 && gen2 == gen1) {
			fenotipe = gen1;
		}

		return fenotipe;
	}

	public float translateRadio(Genome g1, Genome g2) {
		float fenotipe = 0;

		var gen1 = (g1.translateMagnitude(g1.radius,
				patternRadius) / 30);
		var gen2 = (g2.translateMagnitude(g2.radius,
				patternRadius) / 30);

		if (gen1 == 0 && gen2 == 0) {
			fenotipe = 0;
		}
		if (gen1 == 0 && gen2 > 0) {
			fenotipe = gen2;
		}
		if (gen2 == 0 && gen1 > 0) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0) {
			fenotipe = (gen1 + gen2) / 2;
		}

		return fenotipe;
	}

	public float translateferomona(Genome g1, Genome g2) {
		float fenotipe = 0;

		if (!male) {

			var gen1 = (g1
					.translateMagnitude(g1.pheromone, patternPheromone) / 30);
			var gen2 = (g2
					.translateMagnitude(g2.pheromone, patternPheromone) / 30);

			if (gen1 == 0 && gen2 == 0) {
				fenotipe = 0;
			}
			if (gen1 == 0 && gen2 > 0) {
				fenotipe = gen2;
			}
			if (gen2 == 0 && gen1 > 0) {
				fenotipe = gen1;
			}
			if (gen2 > 0 && gen1 > 0 && gen2 > gen1) {
				fenotipe = gen2;
			}
			if (gen2 > 0 && gen1 > 0 && gen2 < gen1) {
				fenotipe = gen1;
			}
			if (gen2 > 0 && gen1 > 0 && gen2 == gen1) {
				fenotipe = gen1;
			}

		}

		return fenotipe;
	}

	public float translateLongevity(Genome g1, Genome g2) {
		float fenotipe = 0;

		var gen1 = (g1
				.translateMagnitude(g1.longevity, patternLongevity) * 8.090f);
		var gen2 = (g2
				.translateMagnitude(g2.longevity, patternLongevity) * 8.090f);

		if (gen1 == 0 && gen2 == 0) {
			fenotipe = 0;
		}
		if (gen1 == 0 && gen2 > 0) {
			fenotipe = gen2;
		}
		if (gen2 == 0 && gen1 > 0) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0) {
			fenotipe = (gen1 + gen2) / 2;
		}

		return fenotipe;
	}

	public float translateMutationRate(Genome g1, Genome g2) {
		float fenotipe = 0;

		var gen1 = (float) ((g1.translateMagnitude(g1.mutationRate,
				patternMutationRate) / 1.9) * world.efficiency);
		var gen2 = (float) ((g2.translateMagnitude(g2.mutationRate,
				patternMutationRate) / 1.9) * world.efficiency);

		if (gen1 == 0 && gen2 == 0) {
			fenotipe = 0;
		}
		if (gen1 == 0 && gen2 > 0) {
			fenotipe = gen2;
		}
		if (gen2 == 0 && gen1 > 0) {
			fenotipe = gen1;
		}
		if (gen2 > 0 && gen1 > 0) {
			fenotipe = (gen1 + gen2) / 2;
		}

		return fenotipe;
	}

	public boolean translatePredator(Genome g1, Genome g2) {
		var fenotipe = true;

		var gen1 = g1.translateBoolean(g1.predator, patternPredator);
		var gen2 = g2.translateBoolean(g2.predator, patternPredator);

		if (!gen1 && !gen2) {
			fenotipe = false;
		}

		return fenotipe;
	}

	public boolean translateSense(Genome g1, Genome g2) {
		var fenotipe = true;

		var gen1 = g1.translateBoolean(g1.sense, patternSense);
		var gen2 = g2.translateBoolean(g2.sense, patternSense);

		if (!gen1 && !gen2) {
			fenotipe = false;
		}

		return fenotipe;
	}

	public boolean translateCazar(Genome g1, Genome g2) {
		var fenotipe = true;

		var gen1 = g1.translateBoolean(g1.hunt, patternHunt);
		var gen2 = g2.translateBoolean(g2.hunt, patternHunt);

		if (!gen1 && !gen2) {
			fenotipe = false;
		}

		return fenotipe;
	}

	public boolean translateEscape(Genome g1, Genome g2) {
		var fenotipe = true;

		var gen1 = g1.translateBoolean(g1.escape, patternEscape);
		var gen2 = g2.translateBoolean(g2.escape, patternEscape);

		if (!gen1 && !gen2) {
			fenotipe = false;
		}

		return fenotipe;
	}

	public boolean translatParteNoGen(Genome g1, Genome g2) {
		var fenotipe = false;

		var gen1 = g1.translateBoolean(g1.parteNoGen, patronParteNoGen);
		var gen2 = g2.translateBoolean(g2.parteNoGen, patronParteNoGen);

		if (!gen1 && !gen2) {
			fenotipe = true;
		}

		return fenotipe;

	}

	public boolean translateResisATB(Genome g1, Genome g2) {
		var fenotipe = true;

		var gen1 = g1.translateBoolean(g1.resistanceATB, patternResATB);
		var gen2 = g2.translateBoolean(g2.resistanceATB, patternResATB);

		if (!gen1 && !gen2) {
			fenotipe = false;
		}

		return fenotipe;
	}

	// para machos homosigotas en C3

	public float translateColor(Genome g1) {

		float gen1;
		gen1 = g1.translateMagnitude(g1.color, patternColor) / 10;
		return gen1;
	}

	public float translateLongevity(Genome g1) {

		return (g1
				.translateMagnitude(g1.longevity, patternLongevity) * 8.090f);
	}

	public float translateMutationRate(Genome g1) {

		float gen1;
		gen1 = (float) ((g1.translateMagnitude(g1.mutationRate,
				patternMutationRate) / 1.9) * world.efficiency);
		return (int) gen1;
	}

	public void translate() {

		resistanceATB = translateResisATB(adn, adn2);
		width = translateAncho(adn, adn2);
		height = translateAlto(adn, adn2);
		sense = translateSense(adn, adn2);
		toleranceTemp = translateToleTemp(adn, adn2);
		carnivore = translatePredator(adn, adn2);

		speed = translateSpeed(adn3, adn4);
		hunt = translateCazar(adn3, adn4);
		escape = translateEscape(adn3, adn4);
		radius = translateRadio(adn3, adn4);
		pheromone = translateferomona(adn3, adn4);
		parteNoGen = translatParteNoGen(adn3, adn4);

		if (!male) {
			longevity = translateLongevity(adn5, adn6);
			mutationRate = translateMutationRate(adn5, adn6);
			color = translateColor(adn5, adn6);
		}

		if (male) {
			longevity = translateLongevity(adn5);
			mutationRate = translateMutationRate(adn5);
			color = translateColor(adn5);
		}

		// asigna valoresnumericos para los genes booleanos
		resistenciaI = (int) adn.translateMagnitude(adn.resistanceATB,
				patternResATB) / 8;
		carnivoreI = (int) adn.translateMagnitude(adn.predator, patternPredator) / 6;
		senseI = (int) adn.translateMagnitude(adn.sense, patternSense) / 6;
		huntI = (int) adn3.translateMagnitude(adn3.hunt, patternHunt) / 7;
		escapeI = (int) adn3.translateMagnitude(adn3.escape, patternEscape) / 7;
		parteNoGenI = (int) adn3.translateMagnitude(adn3.parteNoGen,
				patronParteNoGen) / 10;

		resistenciaI2 = (int) adn2.translateMagnitude(adn2.resistanceATB,
				patternResATB) / 8;
		carniI2 = (int) adn2.translateMagnitude(adn2.predator,
				patternPredator) / 6;
		sentI2 = (int) adn2.translateMagnitude(adn2.sense, patternSense) / 6;
		cazarI2 = (int) adn4.translateMagnitude(adn4.hunt, patternHunt) / 7;
		escapI2 = (int) adn4
				.translateMagnitude(adn4.escape, patternEscape) / 7;
		parteNoGenI2 = (int) adn4.translateMagnitude(adn4.parteNoGen,
				patronParteNoGen) / 10;

		// suma todos los genes y se multiplican por un factor de ponderacion
		// para que cada uno de más o menos 1000

		float suma = (int) ((width * 135.3) + (height * 135.3) + (speed * 115.6)
				+ (toleranceTemp * 35.52) + (longevity * 0.03333) + (mutationRate * 0.486));
		// + resistenciaI +resistenciaI2 + sentI+ sentI2+ carniI + carniI2+
		// radioConsiente+cazarI+cazarI2+escapI+escapI2
		// +parteNoGenI+parteNoGenI2) ;

		// System.out.println(ancho +";"+ alto+";"+ speed +";"+
		// toleranciaTemp+";"+ longevidad+";"+ tasaMut+";"+
		// resistenciaI +";"+resistenciaI2+";" + sentI+";"+ sentI2+";"+
		// carniI+";" + carniI2+";"+
		// radioConsiente+";"+cazarI+";"+cazarI2+";"+escapI+";"+escapI2
		// +";"+parteNoGenI+";"+parteNoGenI2);

		SaludCoefi = suma / 6000;

		timeMitosis = (int) (20000 / SaludCoefi);
		longevity = longevity * SaludCoefi;

		optimalTemp = toleranceTemp - 3;

		// System.out.println("suma: "+suma + ", SaludCoefi: "+SaludCoefi
		// +", TiemMit: "+ tiempoMitosis+ ",longev: "+ longevidad);

		// limita el tamaño menos

		// limita el tamaño menos

		if (height > 0 && height < 0.5) {
			height = 0.5f;
		}
		if (width > 0 && width < 0.5) {
			width = 0.5f;
		}

		capacity = (int) (width * height);
		biomass = 0;

		capacity = (width * height);
		// biomasa=1;

		if (energy > capacity) {
			energy = capacity;
		}
		// if(biomasa>capacidad){biomasa=capacidad;}

		var indexcolor = 0;

		if (color == 0) {
			indexcolor = 0;
		}
		if (color > 0 && color >= 76) {
			indexcolor = 1;
		}
		if (color > 76 && color <= 152) {
			indexcolor = 2;
		}
		if (color > 152 && color <= 228) {
			indexcolor = 3;
		}
		if (color > 228 && color <= 304) {
			indexcolor = 4;
		}
		if (color > 304 && color <= 380) {
			indexcolor = 5;
		}
		if (color > 380 && color <= 456) {
			indexcolor = 6;
		}
		if (color > 456 && color <= 532) {
			indexcolor = 7;
		}
		if (color > 532 && color <= 608) {
			indexcolor = 8;
		}
		if (color > 608 && color <= 684) {
			indexcolor = 9;
		}
		if (color > 684 && color <= 760) {
			indexcolor = 10;
		}
		if (color > 760) {
			indexcolor = 10;
		}

		body = new Sprite(world.organismTexture.getRegions().get(indexcolor));
		body.setOrigin(position.x, position.y);
		body.setPosition(this.position.x, this.position.y);
		body.setSize(width, height * 3);

		alas = new Sprite(world.organismTexture.getRegions().get(16));
		alas.setOrigin(position.x, position.y);
		alas.setPosition(this.position.x, this.position.y);
		alas.setSize(speed * 3, 10);

		var colorOjos = 0;

		if (!hunt && !escape) {
			colorOjos = 11;
		}
		if (hunt && !escape) {
			colorOjos = 12;
		}
		if (!hunt && escape) {
			colorOjos = 13;
		}
		if (hunt && escape) {
			colorOjos = 14;
		}

		eyes = new Sprite(world.organismTexture.getRegions().get(colorOjos));
		eyes.setSize(width, height);
		eyes.setPosition(this.position.x, this.position.y);

		antennae = new Sprite(world.organismTexture.getRegions().get(17));
		antennae.setSize(width, height);

		headP = new Sprite(world.organismTexture.getRegions().get(15));
		headP.setSize(width, height);

		resToxinas = new Sprite(world.organismTexture.getRegions().get(18));
		resToxinas.setSize(width, height);

		// transferido = new Sprite(m.transferido);
		// transferido.setPosition(this.posicion.x,this.posicion.y);
		// transferido.setSize(ancho+5, alto+5);

		var g = 0;

		if (male) {
			g = 21;
		}
		if (!male && !parteNoGen) {
			g = 20;
		}
		if (!male && parteNoGen) {
			g = 22;
		}

		sex = new Sprite(world.organismTexture.getRegions().get(g));
		sex.setOrigin(position.x, position.y);
		sex.setSize(width * 2, height * 2);
		sex.setPosition(this.position.x, this.position.y - height);

		sp_pheromone = new Sprite(world.organismTexture.getRegions().get(19));
		sp_pheromone.setOrigin(this.position.x, this.position.y);
		sp_pheromone.setSize(pheromone / 5, pheromone / 5);
		sp_pheromone.setPosition(this.position.x, this.position.y - height);

		phenotype = new Phenotype(world);
		phenotype.name = getFenotipo(world);

		border.height = height;
		border.width = width;

	}

	public String getFenotipo(World mu) {

		var sb = new StringBuffer();

		if (world.viewMaleFemale) {
			if (male) {
				sb.append(world.tx.male);
			}
			if (!male) {
				sb.append(world.tx.female);
			}
		}

		if (mu.collectResistance) {

			var ge1 = adn.translateBoolean(adn.resistanceATB, patternResATB);
			var ge2 = adn2.translateBoolean(adn2.resistanceATB,
					patternResATB);

			if (ge1 == ge2) {
				if (resistenciaI > resistenciaI2) {
					sb.append(";Tox-").append(this.resistenciaI);
				}
				if (resistenciaI2 > resistenciaI) {
					sb.append(";Tox-").append(this.resistenciaI2);
				}
				if (resistenciaI2 == resistenciaI) {
					sb.append(";Tox-").append(this.resistenciaI2);
				}
			}
			if (ge1 && !ge2) {
				sb.append(";Tox-").append(this.resistenciaI);
			}
			if (!ge1 && ge2) {
				sb.append(";Tox-").append(this.resistenciaI2);
			}
		}

		if (mu.collectWidth) {
			sb.append(";Siz-").append((int) (this.width * 54.934) + (int) (this.height * 54.934));
		}

		if (mu.collectSpeed) {
			sb.append(";Win-").append((int) (this.speed * 55.477f));
		}

		if (mu.collectTemp) {
			sb.append(";Tem-").append((int) (this.toleranceTemp));
		}

		if (mu.collectPredator) {
			var ge1 = adn.translateBoolean(adn.predator, patternPredator);
			var ge2 = adn2.translateBoolean(adn.predator, patternPredator);

			if (ge1 == ge2) {
				if (carnivoreI > carniI2) {
					sb.append(";Pre-").append(this.carnivoreI);
				}
				if (carniI2 > carnivoreI) {
					sb.append(";Pre-").append(this.carniI2);
				}
				if (carniI2 == carnivoreI) {
					sb.append(";Pre-").append(this.carniI2);
				}
			}
			if (ge1 && !ge2) {
				sb.append(";Pre-").append(this.carnivoreI);
			}
			if (!ge1 && ge2) {
				sb.append(";Pre-").append(this.carniI2);
			}

		}

		if (mu.collectSense) {

			var ge1 = adn.translateBoolean(adn.sense, patternSense);
			var ge2 = adn2.translateBoolean(adn2.sense, patternSense);

			if (ge1 == ge2) {
				if (senseI > sentI2) {
					sb.append(";Sen-").append(this.senseI);
				}
				if (sentI2 > senseI) {
					sb.append(";Sen-").append(this.sentI2);
				}
				if (sentI2 == senseI) {
					sb.append(";Sen-").append(this.sentI2);
				}
			}
			if (ge1 && !ge2) {
				sb.append(";Sen-").append(this.senseI);
			}
			if (!ge1 && ge2) {
				sb.append(";Sen-").append(this.sentI2);
			}

		}

		if (mu.collectHunt) {

			var ge1 = adn3.translateBoolean(adn3.hunt, patternHunt);
			var ge2 = adn4.translateBoolean(adn4.hunt, patternHunt);

			if (ge1 == ge2) {
				if (huntI > cazarI2) {
					sb.append(";Ba-").append(this.huntI);
				}
				if (cazarI2 > huntI) {
					sb.append(";Ba-").append(this.cazarI2);
				}
				if (cazarI2 == huntI) {
					sb.append(";Ba-").append(this.cazarI2);
				}
			}
			if (ge1 && !ge2) {
				sb.append(";Ba-").append(this.huntI);
			}
			if (!ge1 && ge2) {
				sb.append(";Ba-").append(this.cazarI2);
			}

		}

		if (mu.collectEscape) {
			var ge1 = adn3.translateBoolean(adn3.escape, patternEscape);
			var ge2 = adn4.translateBoolean(adn4.escape, patternEscape);

			if (ge1 == ge2) {
				if (escapeI > escapI2) {
					sb.append(";Ya-").append(this.escapeI);
				}
				if (escapI2 > escapeI) {
					sb.append(";Ya-").append(this.escapI2);
				}
				if (escapI2 == escapeI) {
					sb.append(";Ya-").append(this.escapI2);
				}
			}
			if (ge1 && !ge2) {
				sb.append(";Ya-").append(this.escapeI);
			}
			if (!ge1 && ge2) {
				sb.append(";Ya-").append(this.escapI2);
			}
		}
		if (mu.colectarParteNoGen) {
			var ge1 = adn3.translateBoolean(adn3.parteNoGen,
					patronParteNoGen);
			var ge2 = adn4.translateBoolean(adn4.parteNoGen,
					patronParteNoGen);

			if (ge1 == ge2) {
				if (parteNoGenI > parteNoGenI2) {
					sb.append(";nG-").append(this.parteNoGenI);
				}
				if (parteNoGenI2 > parteNoGenI) {
					sb.append(";nG-").append(this.parteNoGenI2);
				}
				if (parteNoGenI2 == parteNoGenI) {
					sb.append(";nG-").append(this.parteNoGenI2);
				}
			}
			if (ge1 && !ge2) {
				sb.append(";nG-").append(this.parteNoGenI);
			}
			if (!ge1 && ge2) {
				sb.append(";nG-").append(this.parteNoGenI2);
			}
		}

		if (mu.collectRadius) {
			sb.append(";Rc-").append((int) this.radius);
		}

		if (mu.collectPheromone) {
			sb.append(";Fr-").append((int) this.pheromone);
		}

		if (mu.collectMutationRate) {
			sb.append(";Mut-").append(this.mutationRate);
		}
		if (mu.collectLongevity) {
			sb.append(";lon-").append(this.longevity);
		}

		if (mu.collectColor) {
			sb.append(";Col-").append((int) this.color);
		}

		// System.out.println(sentI);
		return sb.toString();

	}

	// permite al programa saber se un organismo es diferente uno de otro

	public float Identificar(World mu) {

		return ((height / width) * (height * width));

	}

	public void verOrganismo(SpriteBatch sb) {

		sb.begin();

		if (!male) {
			sp_pheromone.draw(sb);
		}
		body.draw(sb);
		alas.draw(sb);
		if (carnivore) {
			headP.draw(sb);
		}
		eyes.draw(sb);
		if (sense) {
			antennae.draw(sb);
		}
		if (resistanceATB) {
			resToxinas.draw(sb);
		}
		sex.draw(sb);
		sb.end();

	}

	public void verMarcado(ShapeRenderer sr, SpriteBatch sb, BitmapFont f) {
		if (mark == -1) {
			sr.begin(ShapeType.Rectangle);

			sr.setColor(Color.GREEN);
			sr.rect(border.x - 2, border.y - 2, border.width + 4, border.height + 4);
			sr.end();

			sb.begin();
			f.setColor(Color.GREEN);
			f.draw(sb, nombre, position.x + width + 5, position.y + (height) + 5);
			f.draw(sb, "M:" + format.format(biomass), position.x + width + 5,
					position.y);
			f.draw(sb, "E:" + format.format(energy), position.x + width + 5,
					position.y - 20);
			f.draw(sb, "V:" + format.format(speed), position.x + width + 5,
					position.y - 40);
			sb.end();

		}
	}

	public void verBorde(ShapeRenderer sr) {

		sr.begin(ShapeType.Rectangle);

		sr.setColor(Color.CYAN);
		sr.rect(border.x, border.y, border.width, border.height);
		sr.end();

	}

	public void Ordenar() {

		body.setPosition(position.x, position.y);
		alas.setPosition(
				this.position.x + body.getWidth() / 2 - alas.getWidth() / 2,
				this.position.y + body.getHeight() / 2 - alas.getHeight() / 2);

		eyes.setPosition(
				this.position.x + body.getWidth() / 2 - eyes.getWidth() / 2,
				this.position.y + body.getHeight() - alas.getHeight() / 2);
		if (sense) {
			antennae.setPosition(this.position.x + body.getWidth() / 2
							- antennae.getWidth() / 2,
					this.position.y + body.getHeight());
		}
		if (carnivore) {
			headP.setPosition(this.position.x + body.getWidth() / 2
							- headP.getWidth() / 2,
					this.position.y + body.getHeight() - headP.getHeight());
		}
		if (resistanceATB) {
			resToxinas.setPosition(this.position.x + body.getWidth() / 2
							- resToxinas.getWidth() / 2,
					this.position.y + resToxinas.getHeight() / 2);
		}
		sex.setPosition(position.x - sex.getWidth() / 4,
				position.y - sex.getHeight());
		sp_pheromone.setPosition(
				this.position.x + body.getWidth() / 2
						- sp_pheromone.getWidth() / 2,
				this.position.y + body.getHeight() / 2
						- sp_pheromone.getHeight() / 2);

		// transferido.setPosition(posicion.x-2.5f,posicion.y-2.5f);

		border.x = position.x;
		border.y = position.y;

	}

	public void update() {

		if (height <= 1 || width <= 1) {
			morir();
		}

		if (height > 1 && width > 1) {
			// if(biomasa<=0){morir();}

			if (toleranceTemp < world.temperature) {
				morir();
			}
			if (world.temperature < toleranceTemp - 6) {
				morir();
			}

			if (world.antibiotic == 1) {
				if (!resistanceATB) {
					intoxicado();
				}
			}

			//if (infectado == true) {

			//	enfermedad();
			//	System.out.println(segundos3);
			//}

			if (world.pausaGame == 1) {
				metabolismo();
				contadorTiempo();
				apoptosis();
				aleatorio();

				if (sense) {
					escannearObjetos();
				}
				if (hunt) {
					cazarObjetos();
				}
				if (escape) {
					escapar();
				}
				if (male) {
					buscarHembra();
				}

			}

			position.add(Gdx.graphics.getDeltaTime() * (direction.x) * speed,
					Gdx.graphics.getDeltaTime() * (direction.y) * speed);

			body.setPosition(position.x, position.y);
			alas.setPosition(
					this.position.x + body.getWidth() / 2 - alas.getWidth()
							/ 2, this.position.y + body.getHeight() / 2
							- alas.getHeight() / 2);

			eyes.setPosition(
					this.position.x + body.getWidth() / 2 - eyes.getWidth()
							/ 2,
					this.position.y + body.getHeight() - alas.getHeight() / 2);
			if (sense) {
				antennae.setPosition(this.position.x + body.getWidth() / 2
								- antennae.getWidth() / 2,
						this.position.y + body.getHeight());
			}
			if (carnivore) {
				headP.setPosition(
						this.position.x + body.getWidth() / 2
								- headP.getWidth() / 2,
						this.position.y + body.getHeight()
								- headP.getHeight());
			}
			if (resistanceATB) {
				resToxinas.setPosition(this.position.x + body.getWidth() / 2
						- resToxinas.getWidth() / 2, this.position.y
						+ resToxinas.getHeight() / 2);
			}
			sex.setPosition(position.x - sex.getWidth() / 4, position.y
					- sex.getHeight());
			sp_pheromone.setPosition(
					this.position.x + body.getWidth() / 2
							- sp_pheromone.getWidth() / 2,
					this.position.y + body.getHeight() / 2
							- sp_pheromone.getHeight() / 2);

			// transferido.setPosition(posicion.x-2.5f,posicion.y-2.5f);

			border.x = position.x;
			border.y = position.y;

			// direccion.x= (float) Math.sqrt((this.SPEED*this.SPEED) -
			// (direccion.y*direccion.y));
			if (!world.verFrontera) {
				if (position.x < 0) {
					position.x = world.width - width;
					//direccion.x = direccion.x * (-1);
				}
				if (position.x > world.width) {
					position.x = 0 + width;
					//direccion.x = direccion.x * (-1);
				}
			}
			if (world.verFrontera) {
				if (position.x < 0) {
					position.x = 1;
					direction.x = direction.x * (-1);
				}
				if (position.x > world.width) {
					position.x = world.width - width;
					direction.x = direction.x * (-1);
				}
			}

			if (position.y < 0) {
				position.y = world.height - height;
				//direccion.y = direccion.y * (-1);
			}
			if (position.y > world.height) {
				position.y = 0 + height;
				//direccion.y = direccion.y * (-1);
			}
			// System.out.println("Speed " + SPEED);

		}
	}

	public void metabolismo() {

		if (deltaTime() > msecondTime(100)) {

			float movX = direction.x * speed;
			float movY = direction.y * speed;

			var movh = (float) Math.sqrt((movX * movX) + (movY * movY));

			cuadradoTemp = (optimalTemp - world.temperature)
					* (optimalTemp - world.temperature);

			// System.out.println(cuadradoTemp);

			if (cuadradoTemp <= 1) {
				energy = energy
						- (width * height)
						/ 150f
						- (0.5f * (width * height) * (movh * movh) / 350000f)
						* world.zoom;

			}

			if (cuadradoTemp > 1) {

				energy = (float) energy
						- ((width * height) / 400f)
						* cuadradoTemp
						- (0.5f * (width * height) * (movh * movh) / 35000f)
						* world.zoom * cuadradoTemp;

				// System.out.println((float)(ancho*alto)/10000f*cuadradoTemp);

			}
			setTime();
		}

		if (energy <= 0) {

			morir();

		}
	}// (Math.random()*(Max-Min))+Min)

	public void aleatorio() {

		if (delta3Time() > msecondTime((long) (Math.random() * (20000 - 5000)) + 5000)) {

			direction.x = (float) (Math.random() * (10 + 10) - 10);
			direction.y = (float) (Math.random() * (10 + 10) - 10);

			if (direction.x > 10) {
				direction.x = 10;
			}
			if (direction.x < -10) {
				direction.x = -10;
			}
			if (direction.y > 10) {
				direction.y = 10;
			}
			if (direction.y < -10) {
				direction.y = -10;
			}

			setDelta3();

		}

	}

	public void escannearObjetos() {

		if (sense) {
			for (var i = 0; i < aObjects.size; i++) {
				aObjects.removeIndex(i);
			}// limpia la lista par un nuevo escaneo
			for (var i = 0; i < aPredators.size; i++) {
				aPredators.removeIndex(i);
			}// limpia la lista par un nuevo escaneo
			if (male) {
				for (var i = 0; i < aFemales.size; i++) {
					aFemales.removeIndex(i);
				}
			}

			// si la frontera no esta activada

			if (!world.verFrontera) {
				if (!carnivore) {

					for (Qenergy qe : world.aqe) {

						if (qe.visible) {

							float qX = qe.position.x;
							float qY = qe.position.y;

							float Dx = position.x - qX;
							float Dy = position.y - qY;

							var h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

							if (h < radius) {
								aObjects.add(new Vector2(qX, qY));
							}
						}
					}
				}

				if (carnivore) {

					for (var i = 0; i < world.organisms.size; i++) {
						Organism or = world.organisms.get(i);

						if (this != or && identifier != or.identifier
								&& capacity > or.capacity) {

							// if
							// (!or.getIdentificador().equals(or2.getIdentificador())){

							float qX = or.position.x;
							float qY = or.position.y;

							float Dx = position.x - qX;
							float Dy = position.y - qY;

							var h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

							if (h < radius) {
								aObjects.add(new Vector2(qX, qY));
							}

							// System.out.println("presa detectada");

						}
					}
				}

				if (male) {

					for (var i = 0; i < world.organisms.size; i++) {
						Organism or = world.organisms.get(i);

						if (!or.male
								&& or.seconds >= or.timeMitosis / 1000
								&& (or.biomass / or.capacity) > 0.8) {

							// if
							// (!or.getIdentificador().equals(or2.getIdentificador())){

							float qX = or.position.x;
							float qY = or.position.y;

							float Dx = position.x - qX;
							float Dy = position.y - qY;

							var h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

							if (h < radius * 3) {
								aFemales.add(or);
							}

							// System.out.println("hembra detectada"+
							// aHembras.size);

						}
					}
				}
			}

			// si la frontera esta activada

			// si la frontera esta activada

			if (world.verFrontera) {
				// panel izquierdo

				if (!carnivore && this.position.x < world.width / 2) { // si
					// el
					// organismo
					// esta
					// a
					// la
					// izquierda

					for (Qenergy qeI : world.aqe) {

						if (qeI.position.x < world.width / 2 && qeI.visible) {

							float qX = qeI.position.x;
							float qY = qeI.position.y;

							float Dx = position.x - qX;
							float Dy = position.y - qY;

							var h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

							if (h < radius) {
								aObjects.add(new Vector2(qX, qY));
							}
						}
					}
				}

				if (carnivore && this.position.x < world.width / 2) {

					for (var i = 0; i < world.organisms.size; i++) {
						Organism or = world.organisms.get(i);

						if (or.position.x < world.width / 2 && this != or
								&& identifier != or.identifier
								&& capacity > or.capacity) {

							// if
							// (!or.getIdentificador().equals(or2.getIdentificador())){

							float qX = or.position.x;
							float qY = or.position.y;

							float Dx = position.x - qX;
							float Dy = position.y - qY;

							var h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

							if (h < radius) {
								aObjects.add(new Vector2(qX, qY));
							}

							// System.out.println("presa detectada");

						}
					}
				}

				// panel Derecho

				if (!carnivore && this.position.x > world.width / 2) { // si
					// el
					// organismo
					// esta
					// a
					// la
					// Derecha

					for (Qenergy qeD : world.aqe) {

						if (qeD.position.x > world.width / 2 && qeD.visible) {

							float qX = qeD.position.x;
							float qY = qeD.position.y;

							float Dx = position.x - qX;
							float Dy = position.y - qY;

							var h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

							if (h < radius) {
								aObjects.add(new Vector2(qX, qY));
							}
						}
					}
				}

				if (carnivore && this.position.x > world.width / 2) {

					for (var i = 0; i < world.organisms.size; i++) {
						Organism or = world.organisms.get(i);

						if (or.position.x > world.width / 2 && this != or
								&& identifier != or.identifier
								&& capacity > or.capacity) {

							// if
							// (!or.getIdentificador().equals(or2.getIdentificador())){

							float qX = or.position.x;
							float qY = or.position.y;

							float Dx = position.x - qX;
							float Dy = position.y - qY;

							var h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

							if (h < radius) {
								aObjects.add(new Vector2(qX, qY));
							}

							// System.out.println("presa detectada");

						}
					}
				}

			}
			aObjects.shrink();
			aPredators.shrink();

		}

	}

	public void escapar() {

		if (aPredators.size > 0) {

			for (Vector2 v2 : aPredators) {

				float qX = v2.x;
				float qY = v2.y;

				float Dx = position.x - qX;
				float Dy = position.y - qY;

				var h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

				var cosX = Dx / h;
				var senY = Dy / h;

				var fh = 100 / (h);


				direction.x = direction.x + (cosX * fh / 1);
				direction.y = direction.y + (senY * fh / 1);

				// System.out.println("Objetos vistos: "+ aObjetos.size);

			}
		}
		if (direction.x > 10) {
			direction.x = 10;
		}
		if (direction.x < -10) {
			direction.x = -10;
		}
		if (direction.y > 10) {
			direction.y = 10;
		}
		if (direction.y < -10) {
			direction.y = -10;
		}
		// System.out.println("dirX: "+direccion.x + " dirY: "+ direccion.y);
	}

	public void buscarHembra() {

		if (seconds >= timeMitosis / 1000 && (biomass / capacity) > 0.8) { // el
			// macho
			// tiene
			// que
			// estar
			// maduro

			if (aFemales.size > 0) {

				int size = aFemales.size;

				for (var i = size - 1; i > 0; i--) {

					Organism or = aFemales.get(i);

					if (or.seconds >= timeMitosis / 1000) {// la hembra tien
						// que estar
						// maduro

						float qX = or.position.x;
						float qY = or.position.y;

						float Dx = position.x - qX;
						float Dy = position.y - qY;

						var h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

						var cosX = Dx / h;
						var senY = Dy / h;

						var fh = or.pheromone / (h);

						direction.x = direction.x - (cosX * fh / 1);
						direction.y = direction.y - (senY * fh / 1);


					}
					if (direction.x > 10) {
						direction.x = 10;
					}
					if (direction.x < -10) {
						direction.x = -10;
					}
					if (direction.y > 10) {
						direction.y = 10;
					}
					if (direction.y < -10) {
						direction.y = -10;
					}

				}
			}
		}

	}

	public void cazarObjetos() {

		if (capacity > 0 && (100 * biomass) / capacity < 80) {

			if (aObjects.size > 0) {

				for (Vector2 v2 : aObjects) {

					float qX = v2.x;
					float qY = v2.y;

					float Dx = position.x - qX;
					float Dy = position.y - qY;

					var h = (float) Math.sqrt((Dy * Dy) + (Dx * Dx));

					var cosX = Dx / h;
					var senY = Dy / h;

					var fh = 100 / (h);


					direction.x = direction.x - (cosX * fh / 1);
					direction.y = direction.y - (senY * fh / 1);

					// System.out.println("Objetos vistos: "+ aObjetos.size);

				}
			}
			if (direction.x > 10) {
				direction.x = 10;
			}
			if (direction.x < -10) {
				direction.x = -10;
			}
			if (direction.y > 10) {
				direction.y = 10;
			}
			if (direction.y < -10) {
				direction.y = -10;
			}

		}
	}
	
	public void enfermar() {//el organismo se enfermó
		
		
		
		
	}

	public void morir() {

		direction.x = 0;
		direction.y = 0;

		if (muriendo == 0) {

			if (height <= 1 || width <= 1) {
				world.borrarOrganismo(this);
			}

			if (height > 1 && width > 1) {

				if (position.x < world.width / 2) {// el organismo se encuentra en
					// elpanel izquierdo

					for (var i = 0; i < world.aqe.size; i++) {

						Qenergy qe = world.aqe.get(i);

						if (qe.position.x < world.width / 2 && !qe.visible) {

							if (biomass > world.Qbiomasa) {

								qe.visible = true;

								qe.update();

								biomass = biomass - world.Qbiomasa;

							}

							if (biomass <= world.Qbiomasa) {

								qe.visible = true;

								qe.update();

								biomass = 0;
							}

							if (biomass <= 0) {
								i = world.aqe.size;
							}
						}
					}
				}

				if (position.x > world.width / 2) {// el organismo se encuentra en
					// el panel Derecho

					for (var i = 0; i < world.aqe.size; i++) {

						Qenergy qe = world.aqe.get(i);

						if (qe.position.x > world.width / 2 && !qe.visible) {

							if (biomass > world.QbiomasaR) {

								qe.visible = true;
								// qe.posicion.x= this.posicion.x;
								// qe.posicion.y= this.posicion.y;
								qe.update();

								biomass = biomass - world.Qbiomasa;
								qe.mass = world.QbiomasaR;

							}

							if (biomass <= world.QbiomasaR) {

								qe.visible = true;
								qe.mass = world.QbiomasaR;

								qe.update();

								biomass = 0;
							}

							if (biomass <= 0) {
								i = world.aqe.size;
							}
						}
					}
				}
			}
		}

		world.borrarOrganismo(this);

	}

	public void intoxicado() {

		if (world.antibiotic == 1 && !this.resistanceATB) {

			intoxicated = true;
		}

		if (intoxicated = true) {

			this.contadorTiempoToxico();
		}
		if (seconds2 > 4) {
			morir();
		}

	}
	
	
	public void enfermedad() {//evolucion de la enfermedad

		
		
		//this.contadorTiempoEnfermo();
		
		//if (segundos3 > 10) {
			
		///int random = (int) (Math.random()*100);//lo dejamos a la suerte
		
		//	if(random <= m.virus.mortalidad) {morir();} //se muere
		//	if(random > m.virus.mortalidad) {infectado = false; inmune=true;}//vive y queda inmune
		//}

	}

	public void apoptosis() {

		if (seconds >= longevity / 1000) {

			morir();

		}

	}

	
	// determina la distribucion de alelos

	public void crossOver() {

		var cross = false;
		int chance;
		var buffer = new StringBuffer();

		var i = world.gap1 * 2;

		cross = false;
		chance = (int) (Math.random() * 100);

		if (chance < world.gap1 * 2) {
			cross = true;
		}

		if (cross) {
			buffer.replace(0, buffer.length(), ch2.resistanceATB.toString());
			ch2.resistanceATB.replace(0, ch2.resistanceATB.length(),
					ch3.resistanceATB.toString());
			ch3.resistanceATB.replace(0, ch3.resistanceATB.length(),
					buffer.toString());

		}

		i = world.gap2 * 2;

		cross = false;
		chance = (int) (Math.random() * 100);

		if (chance < world.gap2 * 2) {
			cross = true;
		}

		if (cross) {
			buffer.replace(0, buffer.length(), ch2.resistanceATB.toString());
			ch2.resistanceATB.replace(0, ch2.resistanceATB.length(),
					ch3.resistanceATB.toString());
			ch3.resistanceATB.replace(0, ch3.resistanceATB.length(),
					buffer.toString());
			buffer.replace(0, buffer.length(), ch2.width.toString());
			ch2.width.replace(0, ch2.width.length(), ch3.width.toString());
			ch3.width.replace(0, ch3.width.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch2.height.toString());
			ch2.height.replace(0, ch2.height.length(), ch3.height.toString());
			ch3.height.replace(0, ch3.height.length(), buffer.toString());
		}

		i = world.gap3 * 2;

		cross = false;
		chance = (int) (Math.random() * 100);

		if (chance < world.gap3 * 2) {
			cross = true;
		}

		if (cross) {
			buffer.replace(0, buffer.length(), ch2.resistanceATB.toString());
			ch2.resistanceATB.replace(0, ch2.resistanceATB.length(),
					ch3.resistanceATB.toString());
			ch3.resistanceATB.replace(0, ch3.resistanceATB.length(),
					buffer.toString());
			buffer.replace(0, buffer.length(), ch2.width.toString());
			ch2.width.replace(0, ch2.width.length(), ch3.width.toString());
			ch3.width.replace(0, ch3.width.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch2.height.toString());
			ch2.height.replace(0, ch2.height.length(), ch3.height.toString());
			ch3.height.replace(0, ch3.height.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch2.sense.toString());
			ch2.sense.replace(0, ch2.sense.length(), ch3.sense.toString());
			ch3.sense.replace(0, ch3.sense.length(), buffer.toString());
		}

		i = world.gap4 * 2;

		cross = false;
		chance = (int) (Math.random() * 100);

		if (chance < world.gap4 * 2) {
			cross = true;
		}

		if (cross) {
			buffer.replace(0, buffer.length(), ch2.resistanceATB.toString());
			ch2.resistanceATB.replace(0, ch2.resistanceATB.length(),
					ch3.resistanceATB.toString());
			ch3.resistanceATB.replace(0, ch3.resistanceATB.length(),
					buffer.toString());
			buffer.replace(0, buffer.length(), ch2.width.toString());
			ch2.width.replace(0, ch2.width.length(), ch3.width.toString());
			ch3.width.replace(0, ch3.width.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch2.height.toString());
			ch2.height.replace(0, ch2.height.length(), ch3.height.toString());
			ch3.height.replace(0, ch3.height.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch2.sense.toString());
			ch2.sense.replace(0, ch2.sense.length(), ch3.sense.toString());
			ch3.sense.replace(0, ch3.sense.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch2.toleranceTemp.toString());
			ch2.toleranceTemp.replace(0, ch2.toleranceTemp.length(),
					ch3.toleranceTemp.toString());
			ch3.toleranceTemp.replace(0, ch3.toleranceTemp.length(),
					buffer.toString());
		}

		i = world.gap6 * 2;

		i--;
		cross = false;
		chance = (int) (Math.random() * 100);

		if (chance < world.gap6 * 2) {
			cross = true;
		}

		if (cross) {

			buffer.replace(0, buffer.length(), ch6.speed.toString());
			ch6.speed.replace(0, ch6.speed.length(), ch7.speed.toString());
			ch7.speed.replace(0, ch7.speed.length(), buffer.toString());
		}

		i = world.gap7 * 2;

		cross = false;
		chance = (int) (Math.random() * 100);

		if (chance < world.gap7 * 2) {
			cross = true;
		}

		if (cross) {

			buffer.replace(0, buffer.length(), ch6.speed.toString());
			ch6.speed.replace(0, ch6.speed.length(), ch7.speed.toString());
			ch7.speed.replace(0, ch7.speed.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch6.hunt.toString());
			ch6.hunt.replace(0, ch6.hunt.length(), ch7.hunt.toString());
			ch7.hunt.replace(0, ch7.hunt.length(), buffer.toString());
		}

		i = world.gap8 * 2;

		cross = false;
		chance = (int) (Math.random() * 100);

		if (chance < world.gap8 * 2) {
			cross = true;
		}

		if (cross) {

			buffer.replace(0, buffer.length(), ch6.speed.toString());
			ch6.speed.replace(0, ch6.speed.length(), ch7.speed.toString());
			ch7.speed.replace(0, ch7.speed.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch6.hunt.toString());
			ch6.hunt.replace(0, ch6.hunt.length(), ch7.hunt.toString());
			ch7.hunt.replace(0, ch7.hunt.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch6.escape.toString());
			ch6.escape
					.replace(0, ch6.escape.length(), ch7.escape.toString());
			ch7.escape.replace(0, ch7.escape.length(), buffer.toString());

		}

		i = world.gap9 * 2;

		cross = false;
		chance = (int) (Math.random() * 100);

		if (chance < world.gap9 * 2) {
			cross = true;
		}

		if (cross) {

			buffer.replace(0, buffer.length(), ch6.speed.toString());
			ch6.speed.replace(0, ch6.speed.length(), ch7.speed.toString());
			ch7.speed.replace(0, ch7.speed.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch6.hunt.toString());
			ch6.hunt.replace(0, ch6.hunt.length(), ch7.hunt.toString());
			ch7.hunt.replace(0, ch7.hunt.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch6.escape.toString());
			ch6.escape
					.replace(0, ch6.escape.length(), ch7.escape.toString());
			ch7.escape.replace(0, ch7.escape.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch6.radius.toString());
			ch6.radius.replace(0, ch6.radius.length(),
					ch7.radius.toString());
			ch7.radius.replace(0, ch7.radius.length(),
					buffer.toString());

		}

		i = world.gap92 * 2;

		cross = false;
		chance = (int) (Math.random() * 100);

		if (chance < world.gap92 * 2) {
			cross = true;
		}

		if (cross) {

			buffer.replace(0, buffer.length(), ch6.speed.toString());
			ch6.speed.replace(0, ch6.speed.length(), ch7.speed.toString());
			ch7.speed.replace(0, ch7.speed.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch6.hunt.toString());
			ch6.hunt.replace(0, ch6.hunt.length(), ch7.hunt.toString());
			ch7.hunt.replace(0, ch7.hunt.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch6.escape.toString());
			ch6.escape
					.replace(0, ch6.escape.length(), ch7.escape.toString());
			ch7.escape.replace(0, ch7.escape.length(), buffer.toString());
			buffer.replace(0, buffer.length(), ch6.radius.toString());
			ch6.radius.replace(0, ch6.radius.length(),
					ch7.radius.toString());
			ch7.radius.replace(0, ch7.radius.length(),
					buffer.toString());
			buffer.replace(0, buffer.length(), ch6.pheromone.toString());
			ch6.pheromone.replace(0, ch6.pheromone.length(),
					ch7.pheromone.toString());
			ch7.pheromone.replace(0, ch7.pheromone.length(), buffer.toString());
		}

		// cromosomas 3

		i = world.gap10 * 2;
		if (!male) {

			cross = false;
			chance = (int) (Math.random() * 100);

			if (chance < world.gap10 * 2) {
				cross = true;
			}

			if (cross) {

				buffer.replace(0, buffer.length(), ch10.mutationRate.toString());
				ch10.mutationRate.replace(0, ch10.mutationRate.length(),
						ch11.mutationRate.toString());
				ch11.mutationRate.replace(0, ch11.mutationRate.length(),
						buffer.toString());

			}

			i = world.gap11 * 2;

			cross = false;
			chance = (int) (Math.random() * 100);
			if (chance < world.gap11 * 2) {
				cross = true;
			}

			if (cross) {

				buffer.replace(0, buffer.length(), ch10.mutationRate.toString());
				ch10.mutationRate.replace(0, ch10.mutationRate.length(),
						ch11.mutationRate.toString());
				ch11.mutationRate.replace(0, ch11.mutationRate.length(),
						buffer.toString());
				buffer.replace(0, buffer.length(), ch10.longevity.toString());
				ch10.longevity.replace(0, ch10.longevity.length(),
						ch11.longevity.toString());
				ch11.longevity.replace(0, ch11.longevity.length(),
						buffer.toString());

			}

		}

	}

	public void replication() {

		if (world.mutarResistencia) {
			ch1.resistanceATB.replace(0, ch1.resistanceATB.length(),
					replicar(adn.resistanceATB));
			ch2.resistanceATB.replace(0, ch2.resistanceATB.length(),
					replicar(adn.resistanceATB));
			ch3.resistanceATB.replace(0, ch3.resistanceATB.length(),
					replicar(adn2.resistanceATB));
			ch4.resistanceATB.replace(0, ch4.resistanceATB.length(),
					replicar(adn2.resistanceATB));
		}

		if (world.mutarSize) {
			ch1.width.replace(0, ch1.width.length(), replicar(adn.width));
			ch2.width.replace(0, ch2.width.length(), replicar(adn.width));
			ch3.width.replace(0, ch3.width.length(), replicar(adn2.width));
			ch4.width.replace(0, ch4.width.length(), replicar(adn2.width));
		}

		if (world.mutarSize) {
			ch1.height.replace(0, ch1.height.length(), replicar(adn.height));
			ch2.height.replace(0, ch2.height.length(), replicar(adn.height));
			ch3.height.replace(0, ch3.height.length(), replicar(adn2.height));
			ch4.height.replace(0, ch4.height.length(), replicar(adn2.height));
		}

		if (world.mutarSpeed) {
			ch5.speed.replace(0, ch5.speed.length(), replicar(adn3.speed));
			ch6.speed.replace(0, ch6.speed.length(), replicar(adn3.speed));
			ch7.speed.replace(0, ch7.speed.length(), replicar(adn4.speed));
			ch8.speed.replace(0, ch8.speed.length(), replicar(adn4.speed));
		}

		if (world.mutarTemp) {
			ch1.toleranceTemp.replace(0, ch1.toleranceTemp.length(),
					replicar(adn.toleranceTemp));
			ch2.toleranceTemp.replace(0, ch2.toleranceTemp.length(),
					replicar(adn.toleranceTemp));
			ch3.toleranceTemp.replace(0, ch3.toleranceTemp.length(),
					replicar(adn2.toleranceTemp));
			ch4.toleranceTemp.replace(0, ch4.toleranceTemp.length(),
					replicar(adn2.toleranceTemp));
		}

		if (world.mutarPredador) {
			ch1.predator.replace(0, ch1.predator.length(),
					replicar(adn.predator));
			ch2.predator.replace(0, ch2.predator.length(),
					replicar(adn.predator));
			ch3.predator.replace(0, ch3.predator.length(),
					replicar(adn2.predator));
			ch4.predator.replace(0, ch4.predator.length(),
					replicar(adn2.predator));
		}

		if (world.mutarSentir) {
			ch1.sense.replace(0, ch1.sense.length(), replicar(adn.sense));
			ch2.sense.replace(0, ch2.sense.length(), replicar(adn.sense));
			ch3.sense.replace(0, ch3.sense.length(), replicar(adn2.sense));
			ch4.sense.replace(0, ch4.sense.length(), replicar(adn2.sense));
		}

		if (world.mutarCazar) {
			ch5.hunt.replace(0, ch5.hunt.length(), replicar(adn3.hunt));
			ch6.hunt.replace(0, ch6.hunt.length(), replicar(adn3.hunt));
			ch7.hunt.replace(0, ch7.hunt.length(), replicar(adn4.hunt));
			ch8.hunt.replace(0, ch8.hunt.length(), replicar(adn4.hunt));
		}

		if (world.mutarEscapar) {
			ch5.escape
					.replace(0, ch5.escape.length(), replicar(adn3.escape));
			ch6.escape
					.replace(0, ch6.escape.length(), replicar(adn3.escape));
			ch7.escape
					.replace(0, ch7.escape.length(), replicar(adn4.escape));
			ch8.escape
					.replace(0, ch8.escape.length(), replicar(adn4.escape));

		}
		if (world.mutarRadioconsiente) {
			ch5.radius.replace(0, ch5.radius.length(),
					replicar(adn3.radius));
			ch6.radius.replace(0, ch6.radius.length(),
					replicar(adn3.radius));
			ch7.radius.replace(0, ch7.radius.length(),
					replicar(adn4.radius));
			ch8.radius.replace(0, ch8.radius.length(),
					replicar(adn4.radius));
		}
		if (world.mutarFeromona) {
			ch5.pheromone.replace(0, ch5.pheromone.length(),
					replicar(adn3.pheromone));
			ch6.pheromone.replace(0, ch6.pheromone.length(),
					replicar(adn3.pheromone));
			ch7.pheromone.replace(0, ch7.pheromone.length(),
					replicar(adn4.pheromone));
			ch8.pheromone.replace(0, ch8.pheromone.length(),
					replicar(adn4.pheromone));
		}
		if (world.mutarParteNoGen) {
			ch5.parteNoGen.replace(0, ch5.parteNoGen.length(),
					replicar(adn3.parteNoGen));
			ch6.parteNoGen.replace(0, ch6.parteNoGen.length(),
					replicar(adn3.parteNoGen));
			ch7.parteNoGen.replace(0, ch7.parteNoGen.length(),
					replicar(adn4.parteNoGen));
			ch8.parteNoGen.replace(0, ch8.parteNoGen.length(),
					replicar(adn4.parteNoGen));
		}
		if (world.mutarTasaMut) {
			ch9.mutationRate.replace(0, ch9.mutationRate.length(),
					replicar(adn5.mutationRate));
			ch10.mutationRate.replace(0, ch10.mutationRate.length(),
					replicar(adn5.mutationRate));
			if (!male) {
				ch11.mutationRate.replace(0, ch11.mutationRate.length(),
						replicar(adn6.mutationRate));
				ch12.mutationRate.replace(0, ch12.mutationRate.length(),
						replicar(adn6.mutationRate));
			}
		}
		if (world.mutarLongevidad) {
			ch9.longevity.replace(0, ch9.longevity.length(),
					replicar(adn5.longevity));
			ch10.longevity.replace(0, ch10.longevity.length(),
					replicar(adn5.longevity));
			if (!male) {
				ch11.longevity.replace(0, ch11.longevity.length(),
						replicar(adn6.longevity));
				ch12.longevity.replace(0, ch12.longevity.length(),
						replicar(adn6.longevity));
			}
		}

		if (world.mutarColor) {
			ch9.color.replace(0, ch9.color.length(), replicar(adn5.color));
			ch10.color.replace(0, ch10.color.length(), replicar(adn5.color));
			if (!male) {
				ch11.color
						.replace(0, ch11.color.length(), replicar(adn6.color));
				ch12.color
						.replace(0, ch12.color.length(), replicar(adn6.color));
			}

		}

		if (!world.mutarResistencia) {
			ch1.resistanceATB.replace(0, ch1.resistanceATB.length(),
					adn.resistanceATB.toString());
			ch2.resistanceATB.replace(0, ch2.resistanceATB.length(),
					adn.resistanceATB.toString());
			ch3.resistanceATB.replace(0, ch3.resistanceATB.length(),
					adn2.resistanceATB.toString());
			ch4.resistanceATB.replace(0, ch4.resistanceATB.length(),
					adn2.resistanceATB.toString());
		}

		if (!world.mutarSize) {
			ch1.width.replace(0, ch1.width.length(), adn.width.toString());
			ch2.width.replace(0, ch2.width.length(), adn.width.toString());
			ch3.width.replace(0, ch3.width.length(), adn2.width.toString());
			ch4.width.replace(0, ch4.width.length(), adn2.width.toString());
		}

		if (!world.mutarSize) {
			ch1.height.replace(0, ch1.height.length(), adn.height.toString());
			ch2.height.replace(0, ch2.height.length(), adn.height.toString());
			ch3.height.replace(0, ch3.height.length(), adn2.height.toString());
			ch4.height.replace(0, ch4.height.length(), adn2.height.toString());
		}

		if (!world.mutarTemp) {
			ch1.toleranceTemp.replace(0, ch1.toleranceTemp.length(),
					adn.toleranceTemp.toString());
			ch2.toleranceTemp.replace(0, ch2.toleranceTemp.length(),
					adn.toleranceTemp.toString());
			ch3.toleranceTemp.replace(0, ch3.toleranceTemp.length(),
					adn2.toleranceTemp.toString());
			ch4.toleranceTemp.replace(0, ch4.toleranceTemp.length(),
					adn2.toleranceTemp.toString());
		}

		if (!world.mutarPredador) {
			ch1.predator.replace(0, ch1.predator.length(),
					adn.predator.toString());
			ch2.predator.replace(0, ch2.predator.length(),
					adn.predator.toString());
			ch3.predator.replace(0, ch3.predator.length(),
					adn2.predator.toString());
			ch4.predator.replace(0, ch4.predator.length(),
					adn2.predator.toString());
		}

		if (!world.mutarSentir) {
			ch1.sense.replace(0, ch1.sense.length(), adn.sense.toString());
			ch2.sense.replace(0, ch2.sense.length(), adn.sense.toString());
			ch3.sense.replace(0, ch3.sense.length(), adn2.sense.toString());
			ch4.sense.replace(0, ch4.sense.length(), adn2.sense.toString());
		}

		if (!world.mutarSpeed) {
			ch5.speed.replace(0, ch5.speed.length(), adn3.speed.toString());
			ch6.speed.replace(0, ch6.speed.length(), adn3.speed.toString());
			ch7.speed.replace(0, ch7.speed.length(), adn4.speed.toString());
			ch8.speed.replace(0, ch8.speed.length(), adn4.speed.toString());
		}

		if (!world.mutarCazar) {
			ch5.hunt.replace(0, ch5.hunt.length(), adn3.hunt.toString());
			ch6.hunt.replace(0, ch6.hunt.length(), adn3.hunt.toString());
			ch7.hunt.replace(0, ch7.hunt.length(), adn4.hunt.toString());
			ch8.hunt.replace(0, ch8.hunt.length(), adn4.hunt.toString());
		}

		if (!world.mutarEscapar) {
			ch5.escape.replace(0, ch5.escape.length(),
					adn3.escape.toString());
			ch6.escape.replace(0, ch6.escape.length(),
					adn3.escape.toString());
			ch7.escape.replace(0, ch7.escape.length(),
					adn4.escape.toString());
			ch8.escape.replace(0, ch8.escape.length(),
					adn4.escape.toString());

		}
		if (!world.mutarRadioconsiente) {
			ch5.radius.replace(0, ch5.radius.length(),
					adn3.radius.toString());
			ch6.radius.replace(0, ch6.radius.length(),
					adn3.radius.toString());
			ch7.radius.replace(0, ch7.radius.length(),
					adn4.radius.toString());
			ch8.radius.replace(0, ch8.radius.length(),
					adn4.radius.toString());
		}
		if (!world.mutarFeromona) {
			ch5.pheromone.replace(0, ch5.pheromone.length(),
					adn3.pheromone.toString());
			ch6.pheromone.replace(0, ch6.pheromone.length(),
					adn3.pheromone.toString());
			ch7.pheromone.replace(0, ch7.pheromone.length(),
					adn4.pheromone.toString());
			ch8.pheromone.replace(0, ch8.pheromone.length(),
					adn4.pheromone.toString());
		}
		if (!world.mutarParteNoGen) {
			ch5.parteNoGen.replace(0, ch5.parteNoGen.length(),
					adn3.parteNoGen.toString());
			ch6.parteNoGen.replace(0, ch6.parteNoGen.length(),
					adn3.parteNoGen.toString());
			ch7.parteNoGen.replace(0, ch7.parteNoGen.length(),
					adn4.parteNoGen.toString());
			ch8.parteNoGen.replace(0, ch8.parteNoGen.length(),
					adn4.parteNoGen.toString());

		}

		if (!world.mutarTasaMut) {
			ch9.mutationRate.replace(0, ch9.mutationRate.length(),
					adn5.mutationRate.toString());
			ch10.mutationRate.replace(0, ch10.mutationRate.length(),
					adn5.mutationRate.toString());
			if (!male) {
				ch11.mutationRate.replace(0, ch11.mutationRate.length(),
						adn6.mutationRate.toString());
				ch12.mutationRate.replace(0, ch12.mutationRate.length(),
						adn6.mutationRate.toString());
			}
		}
		if (!world.mutarLongevidad) {
			ch9.longevity.replace(0, ch9.longevity.length(),
					adn5.longevity.toString());
			ch10.longevity.replace(0, ch10.longevity.length(),
					adn5.longevity.toString());
			if (!male) {
				ch11.longevity.replace(0, ch11.longevity.length(),
						adn6.longevity.toString());
				ch12.longevity.replace(0, ch12.longevity.length(),
						adn6.longevity.toString());
			}
		}

		if (!world.mutarColor) {
			ch9.color.replace(0, ch9.color.length(), adn5.color.toString());
			ch10.color.replace(0, ch10.color.length(), adn5.color.toString());
			if (!male) {
				ch11.color.replace(0, ch11.color.length(),
						adn6.color.toString());
				ch12.color.replace(0, ch12.color.length(),
						adn6.color.toString());
			}
		}

	}

	public void gametoGenesis() {// coping the genome

		replication();
		crossOver();

	}

	public Genome randomCigoto() {

		var cigoto = new Genome(world, 1, 1, 1, 0, 0, 0);
		var a = (int) (Math.random() * 2000);

		if (a <= 500) {
			cigoto = ch1;
		}
		if (a > 500 && a <= 1000) {
			cigoto = ch4;
		}
		if (a > 1000 && a <= 1500) {
			cigoto = ch3;
		}
		if (a > 1500 && a <= 2000) {
			cigoto = ch2;
		}

		return cigoto;

	}

	public Genome randomCigoto2() {

		var cigoto = new Genome(world, 0, 0, 0, 0, 0, 0);
		var a = (int) (Math.random() * 2000);

		if (a <= 500) {
			cigoto = ch5;
		}
		if (a > 500 && a <= 1000) {
			cigoto = ch8;
		}
		if (a > 1000 && a <= 1500) {
			cigoto = ch7;
		}
		if (a > 1500 && a <= 2000) {
			cigoto = ch6;
		}

		return cigoto;

	}

	public Genome randomCigoto3() {

		var cigoto = new Genome(world, 0, 0, 0);
		var a = (int) (Math.random() * 2000);
		if (!male) {
			if (a <= 500) {
				cigoto = ch9;
			}
			if (a > 500 && a <= 1000) {
				cigoto = ch12;
			}
			if (a > 1000 && a <= 1500) {
				cigoto = ch11;
			}
			if (a > 1500 && a <= 2000) {
				cigoto = ch10;
			}
		}

		if (male) {

			if (a <= 1000) {
				cigoto = ch9;
			}
			if (a > 1000 && a <= 2000) {
				cigoto = ch10;
			}

		}

		return cigoto;

	}

	// Fecundacion

	public void Fecundation(Organism o1, Organism o2) {

		var frac = o1.Identificar(world) / o2.Identificar(world);

		if (frac > 1) {
			frac = 1 / frac;
		}

		var masatotal = (int) (world.BiomasaTotal(world.organisms) + world.MateriaLibre());
		if (o1.capacity > 1 && o2.capacity > 1
				&& o1.carnivore == o2.carnivore && world.organisms.size < world.maximo
				&& masatotal <= (world.totalMass + 100)) {

			if (o1.biomass > o1.capacity / 2 && o2.biomass > o2.capacity / 2) {

				o1.gametoGenesis();
				o2.gametoGenesis();

				var sb = new StringBuffer(o1.nombre.toString());
				var sb2 = new StringBuffer(o1.nombre.toString());
				var sb3 = new StringBuffer(o2.nombre.toString());
				var sb4 = new StringBuffer(o2.nombre.toString());

				var pos = new Vector2((float) position.x + ((width) + 3)
						/ 2, (float) position.y);

				var x = (float) Math.random() * 360;
				seno = (float) Math.sin(x) * 10;
				coseno = (float) Math.sin(x + 3.1416 / 2) * 10;

				var dir = new Vector2(seno, coseno);

				var pos2 = new Vector2((float) position.x + ((width) + 3)
						/ 2, (float) position.y);
				x = (float) Math.random() * 360;
				seno = (float) Math.sin(x) * 10;
				coseno = (float) Math.sin(x + 3.1416 / 2) * 10;

				var dir2 = new Vector2(seno, coseno);

				var pos3 = new Vector2((float) position.x + ((width) + 3)
						/ 2, (float) position.y);
				x = (float) Math.random() * 360;
				seno = (float) Math.sin(x) * 10;
				coseno = (float) Math.sin(x + 3.1416 / 2) * 10;

				var dir3 = new Vector2(seno, coseno);

				var pos4 = new Vector2((float) position.x + ((width) + 3)
						/ 2, (float) position.y);
				x = (float) Math.random() * 360;
				seno = (float) Math.sin(x) * 10;
				coseno = (float) Math.sin(x + 3.1416 / 2) * 10;

				var dir4 = new Vector2(seno, coseno);

				var sex = true;
				var gen = (int) (Math.random() * 999);
				if (gen < 500) {
					sex = true;
				}
				if (gen >= 500) {
					sex = false;
				}

				var h1 = new Organism(sex, o1.randomCigoto(),
						o2.randomCigoto(), o1.randomCigoto2(),
						o2.randomCigoto2(), o1.randomCigoto3(),
						o2.randomCigoto3(), pos, sb, world);
				h1.male = sex;
				h1.direction = dir;
				h1.energy = o1.energy / 5;
				h1.biomass = o1.biomass / 5;

				gen = (int) (Math.random() * 999);
				if (gen < 500) {
					sex = true;
				}
				if (gen >= 500) {
					sex = false;
				}

				var h2 = new Organism(sex, o1.randomCigoto(),
						o2.randomCigoto(), o1.randomCigoto2(),
						o2.randomCigoto2(), o1.randomCigoto3(),
						o2.randomCigoto3(), pos2, sb2, world);
				h2.male = sex;
				h2.direction = dir2;
				h2.energy = o1.energy / 5;
				h2.biomass = o1.biomass / 5;

				gen = (int) (Math.random() * 999);
				if (gen < 500) {
					sex = true;
				}
				if (gen >= 500) {
					sex = false;
				}

				var h3 = new Organism(sex, o1.randomCigoto(),
						o2.randomCigoto(), o1.randomCigoto2(),
						o2.randomCigoto2(), o1.randomCigoto3(),
						o2.randomCigoto3(), pos3, sb3, world);

				h3.direction = dir3;
				h3.energy = o2.energy / 5;
				h3.biomass = o2.biomass / 5;

				gen = (int) (Math.random() * 999);
				if (gen < 500) {
					sex = true;
				}
				if (gen >= 500) {
					sex = false;
				}

				var h4 = new Organism(sex, o1.randomCigoto(),
						o2.randomCigoto(), o1.randomCigoto2(),
						o2.randomCigoto2(), o1.randomCigoto3(),
						o2.randomCigoto3(), pos4, sb4, world);
				h4.male = sex;
				h4.direction = dir4;
				h4.energy = o2.energy / 5;
				h4.biomass = o2.biomass / 5;

				o1.energy = o1.energy / 5; // la hembra paga elcosto
				// energetico
				o1.biomass = o1.biomass / 5;

				world.organisms.add(h1);
				world.organisms.add(h2);
				world.organisms.add(h3);
				world.organisms.add(h4);

			}
		}
	}

	public void ReproduccionParteNoGen(Organism o1) {// Con cortejo

		var masatotal = (int) (world.BiomasaTotal(world.organisms) + world.MateriaLibre());

		if (o1.capacity > 1 && world.organisms.size < world.maximo
				&& masatotal <= (world.totalMass + 100)) {

			if (o1.biomass > o1.capacity / 2) {

				o1.gametoGenesis();

				var sb = new StringBuffer(o1.nombre.toString());
				var sb2 = new StringBuffer(o1.nombre.toString());

				var pos = new Vector2((float) position.x + ((width) + 3)
						/ 2, (float) position.y);

				var x = (float) Math.random() * 360;
				seno = (float) Math.sin(x) * 10;
				coseno = (float) Math.sin(x + 3.1416 / 2) * 10;

				var dir = new Vector2(seno, coseno);

				var pos2 = new Vector2((float) position.x + ((width) + 3)
						/ 2, (float) position.y);
				x = (float) Math.random() * 360;
				seno = (float) Math.sin(x) * 10;
				coseno = (float) Math.sin(x + 3.1416 / 2) * 10;

				var dir2 = new Vector2(seno, coseno);

				var h1 = new Organism(o1.male, o1.randomCigoto(),
						o1.randomCigoto(), o1.randomCigoto2(),
						o1.randomCigoto2(), o1.randomCigoto3(),
						o1.randomCigoto3(), pos, sb, world);

				h1.direction = dir;
				h1.energy = o1.energy / 5;
				h1.biomass = o1.biomass / 5;

				var h2 = new Organism(o1.male, o1.randomCigoto(),
						o1.randomCigoto(), o1.randomCigoto2(),
						o1.randomCigoto2(), o1.randomCigoto3(),
						o1.randomCigoto3(), pos2, sb2, world);

				h2.direction = dir2;
				h2.energy = o1.energy / 5;
				h2.biomass = o1.biomass / 5;

				o1.energy = o1.energy / 5;
				o1.biomass = o1.biomass / 5;

				world.organisms.add(h1);
				world.organisms.add(h2);

				o1.timeCycle = 0;

			}
		}
	}

	public void ReproduccionParteNoGenSinCortejo(Organism o1) {// Sin cortejo

		var masatotal = (int) (world.BiomasaTotal(world.organisms) + world.MateriaLibre());

		if (o1.capacity > 1 && world.organisms.size < world.maximo
				&& masatotal <= (world.totalMass + 100)) {

			if (o1.biomass > o1.capacity / 2) {

				o1.gametoGenesis();

				var sb = new StringBuffer(o1.nombre.toString());
				var sb2 = new StringBuffer(o1.nombre.toString());

				var pos = new Vector2((float) position.x + ((width) + 3)
						/ 2, (float) position.y);

				var x = (float) Math.random() * 360;
				seno = (float) Math.sin(x) * 10;
				coseno = (float) Math.sin(x + 3.1416 / 2) * 10;

				var dir = new Vector2(seno, coseno);

				var pos2 = new Vector2((float) position.x + ((width) + 3)
						/ 2, (float) position.y);
				x = (float) Math.random() * 360;
				seno = (float) Math.sin(x) * 10;
				coseno = (float) Math.sin(x + 3.1416 / 2) * 10;

				var dir2 = new Vector2(seno, coseno);

				var h1 = new Organism(o1.male, o1.randomCigoto(),
						o1.randomCigoto(), o1.randomCigoto2(),
						o1.randomCigoto2(), o1.randomCigoto3(),
						o1.randomCigoto3(), pos, sb, world);

				h1.direction = dir;
				h1.energy = o1.energy / 5;
				h1.biomass = o1.biomass / 5;

				var h2 = new Organism(o1.male, o1.randomCigoto(),
						o1.randomCigoto(), o1.randomCigoto2(),
						o1.randomCigoto2(), o1.randomCigoto3(),
						o1.randomCigoto3(), pos2, sb2, world);

				h2.direction = dir2;
				h2.energy = o1.energy / 5;
				h2.biomass = o1.biomass / 5;

				o1.energy = o1.energy / 5;
				o1.biomass = o1.biomass / 5;

				world.organisms.add(h1);

				o1.timeCycle = 0;

			}
		}
	}

	// mide los segundos transcurrido desde su creación

	public void contadorTiempo() {

		if (delta2Time() > msecondTime(1000)) {

			seconds = seconds + 1;
			timeCycle = timeCycle + 1;

			setDelta();
		}
	}
	
	public void contadorTiempoEnfermo() {

		if (deltaEnfermo() > msecondTime(1000)) {

			seconds3 = seconds3 + 1;

			setDeltaEnfermo();
		}

	}

	public void contadorTiempoToxico() {

		if (deltaToxico() > msecondTime(1000)) {

			seconds2 = seconds2 + 1;

			setDeltaToxico();
		}

	}

	// mide el tiempo transcurrido desde el unltimo set

	public long deltaTime() {
		return TimeUtils.nanoTime() - delta;
	}

	public long edadTime() {
		return TimeUtils.nanoTime() - edad;
	}

	public long delta2Time() {
		return TimeUtils.nanoTime() - delta2;
	}

	public long deltaToxico() {
		return TimeUtils.nanoTime() - deltaToxico;
	}
	
	public long deltaEnfermo() {
		return TimeUtils.nanoTime() - deltaEnfermo;
	}

	public long delta3Time() {
		return TimeUtils.nanoTime() - delta3;
	}

	// anota el tiempo de juego transcurrido en el momento que se invoca un
	// evento

	public void setTime() {
		delta = TimeUtils.nanoTime();
	}

	public void setEdad() {
		edad = TimeUtils.nanoTime();
	}

	public void setDelta() {
		delta2 = TimeUtils.nanoTime();
	}

	public void setDeltaToxico() {
		deltaToxico = TimeUtils.nanoTime();
	}
	
	public void setDeltaEnfermo() {
		deltaEnfermo = TimeUtils.nanoTime();
	}

	public void setDelta3() {
		delta3 = TimeUtils.nanoTime();
	}

	// convierte de ms a nanosegundos para mas comodidad
	public long msecondTime(long ms) {

		return ms * 1000000;
	}

	public String replicar(StringBuffer str) {

		for (var ei = 0; ei < str.length(); ei++) {// se recorre toada la
			// longitud edl gen letra
			// por letra

			// obtenemos la letra de una posicion determinada
			base = str.charAt(ei);

			// tiramos los dados
			var a = 0;
			if (mutable) {
				a = (int) (Math.random() * mutationRate);
			}
			if (!mutable) {
				a = 100;
			}

			if (a <= 1) {// se produce una mutacion

				var b = (int) (Math.random() * 1000);

				if (b > 10) {
					// sustitucion
					var c = (int) (Math.random() * 40);
					if (c <= 10) {
						c = 0;
					}
					if (c > 10 && c <= 20) {
						c = 1;
					}
					if (c > 20 && c <= 30) {
						c = 2;
					}
					if (c > 30 && c <= 40) {
						c = 3;
					}

					switch (c) {
					case 0:
						str.setCharAt(ei, 'a');
						break;
					case 1:
						str.setCharAt(ei, 'c');
						break;
					case 2:
						str.setCharAt(ei, 'g');
						break;
					case 3:
						str.setCharAt(ei, 't');
						break;

					}
				}
				if (b <= 10) { // delecion o insercion
					var e = (int) (Math.random() * 81);
					if (e > 0 && e <= 10) {
						e = 0;
					}
					if (e > 10 && e <= 20) {
						e = 1;
					}
					if (e > 20 && e <= 30) {
						e = 2;
					}
					if (e > 30 && e <= 40) {
						e = 3;
					}
					if (e > 40 && e <= 50) {
						e = 4;
					}
					if (e > 50 && e <= 60) {
						e = 5;
					}
					if (e > 60 && e <= 70) {
						e = 6;
					}
					if (e > 70 && e <= 80) {
						e = 7;
					}
					if (e == 0) {
						e = 8;
					}
					if (e == 81) {
						e = 9;
					}

					switch (e) {
					case 0:
						str.insert(ei, "a");
						break;
					case 1:
						str.insert(ei, "c");
						break;
					case 2:
						str.insert(ei, "g");
						break;
					case 3:
						str.insert(ei, "t");
						break;
						case 4:
							str.deleteCharAt(ei);
							break;
						case 5:
							str.deleteCharAt(ei);
							break;
						case 6:
							str.deleteCharAt(ei);
							break;
						case 7:
							str.deleteCharAt(ei);
							break;
						case 8:
							str.replace(0, str.length(), " ");
							ei = str.length();
							break;
						case 9:
							str.append("aaaaaa").append(str);
							break;
					}
				}

			}

		}

		return str.toString();
	}

	@Override
	public String toString() {
		return nombre.toString();
	}

	@Override
	public int compareTo(Organism arg0) {
		{
			// The order of two Organism's is determined by the name.
			var p2Name = arg0.phenotype.name;

			// or any positive int
			return Integer.compare(0, phenotype.name.compareTo(p2Name)); // or any negative int
		}
	}

}