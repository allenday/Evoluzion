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

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class World implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected Evoluzion ev;// clase prinsipal
	StartMenu mi; // menu inicio
	Text tx;

	//Virus virus = new Virus(20,20,10,95); 

	Array<Senergy> ase, ase1, ase2, ase3, ase4, ase5, ase6, ase7, ase8, ase9,
			ase10; // lista de energia verde
	Array<Qenergy> aqe, aqe1, aqe2, aqe3, aqe4, aqe5, aqe6, aqe7, aqe8, aqe9,
			aqe10; // lista de energia roja (biomasa)
	Array<Organism> organisms; // lista de organismos
	Array<Organism> aEspesies, aEspesiesTotales; // listas de organismos
	// colectados segun especies
	Array<Allele> aAlelos;
	Array<Phenotype> aFenotipos;
	Array<Genotype> aGenotipos;

	float ancho, alto, ratio; // dimenciones de la pantalla
	//
	float deltaX = 0; // delta de x entre dos individuos
	float deltaY = 0; // delta de y entre dos individuos
	float medSpeed; // se usa para el calcula de velosidad media
	double temperatura = 25;
	double medTem;
	float efficiency = 1;
	// suma de toda la masa libre

	int dias, horas, minutos, minutes2, minutos3, seconds, segundos2,
			segundos3, seconds4, segundos5, segundos6; // anota el paso del
	// tiempo
	long edad, delta, delta2, delta3, tiempo; // mide diferencia de tiempo entre
	// una accion y la siguiente
	int maxTime = 0;
	double Masatotal, MasatotalL, MasatotalR;
	int tiempoMuestreo;
	int tiempoCatastrofe;
	int tiempoATB;
	int cantidadMuestras;
	int contadorMuestreo;
	int Senergia;
	int Qbiomasa;
	int numOrg;
	int numSen;
	int numQen;
	int numSenR;
	int numQenR;
	int SenergiaR;
	int QbiomasaR;
	int EnRe;
	int BioRe;
	float medSize;
	int medMut;
	int medLon;
	int pausaGame = 1;// 1= play -1=stop
	float zoom = 2;
	int index = 0;
	int maximo = 1000; // numero maximo de organismos
	int ingles = -1; // -1=false 1=true
	int indice = 0, indiceDer = 0, indiceIz = 0; // se usa para ordenar los
													// organismos al final de la
													// partida
	
	
	float minStar1, minStar2;
	float TempFinal1, TempFinal2, tempXSecond1, tempXSecond2;
	float deltaTime1, deltaTime2;
	float salud;
	// hotSpot for crossover

	int gap1, gap2, gap3, gap4, gap5, gap6, gap7, gap8, gap9, gap92, gap10,
			gap11;

	int antibiotico = -1; // 1= true -1= false

	int numero = 0; // es usado por metodos que cuentan cosas
	int id = 0; // es usado por metodos que cuentan cosas
	int cantidad = 0;// es usado por metodos que cuentan cosas

	int numeroI = 0; // es usado por los metodos que usan calculos
	int numeroD = 0; // es usado por los metodos que usan calculos

	int numEspI = 0; // es usado por los metodos que usan calculos
	int numEspD = 0; // es usado por los metodos que usan calculos


	int horizontalTransferRate;

	boolean verFrontera = false;
	boolean viewMaleFemale = false;
	boolean parar = false;

	boolean collectWidth = false;
	boolean collectHeight = false;
	boolean collectSpeed = false;
	boolean collectColor = false;
	boolean collectSense = false;
	boolean collectPredator = false;
	boolean collectHunt = false;
	boolean collectEscape = false;
	boolean collectRadius = false;
	boolean collectMutationRate = false;
	boolean collectLongevity = false;
	boolean collectTemp = false;
	boolean collectResistance = false;
	boolean collectPheromone = false;
	boolean colectarParteNoGen = false;

	boolean mutarColor = true;
	boolean mutarSize = true;
	boolean mutarSpeed = true;
	boolean mutarSentir = true;
	boolean mutarPredador = true;
	boolean mutarCazar = true;
	boolean mutarEscapar = true;
	boolean mutarRadioconsiente = true;
	boolean mutarTasaMut = true;
	boolean mutarLongevidad = true;
	boolean mutarTemp = true;
	boolean mutarResistencia = true;
	boolean mutarFeromona = false;
	boolean mutarParteNoGen = false;

	boolean cargarPoblacion = false;
	boolean moverMasa;

	String ruta, poblacion;
	StringBuffer linea, orgNombre; // se usa para archivar los resultado
	StringBuffer ceroIz, ceroIz2; // agrega un cero a la izquierda
	String nombre;

	TextureAtlas textuRA_ENER, textura_ORG, organismTexture, textura_sex; // contine
	// las
	// imagenes
	Texture auraATB, transferido, textura_feromona;
	Archive f_datos, f_genes, f_arbol, f_proteoma, f_poblacion, f_mutantes,
			f_alelos, f_fenotipos, f_genotipos, f_alelos2; // para archivar
	NumberFormat format = new DecimalFormat("0.00");
	NumberFormat format2 = new DecimalFormat("0");
	NumberFormat format3 = new DecimalFormat("#.##");

	Rectangle frontera;

	public World(Evoluzion ev, String ruta, String nombre, String poblacion,
				 int numOrg, int numSen, int numQen, int Senergia, int Qbiomasa,
				 int numSenR, int numQenR, int SenergiaR, int QbiomasaR,
				 boolean cargarPoblacion, boolean moverMasa, boolean verFrontera,
				 boolean viewMaleFemale, String genesPedidos, int ingles) {
		this.ev = ev;

		this.numOrg = numOrg;
		this.numSen = numSen;
		this.numQen = numQen;
		this.Senergia = Senergia;
		this.Qbiomasa = Qbiomasa;

		this.numSenR = numSenR;
		this.numQenR = numQenR;
		this.SenergiaR = SenergiaR;
		this.QbiomasaR = QbiomasaR;
		this.cargarPoblacion = cargarPoblacion;
		this.moverMasa = moverMasa;
		this.verFrontera = verFrontera;
		this.viewMaleFemale = viewMaleFemale;
		this.nombre = nombre;
		this.ruta = ruta;
		this.poblacion = poblacion;
		this.ingles = ingles;

		tx = new Text();
		if (ingles == 1) {
			tx.setEnglish();
		}
		if (ingles == -1) {
			tx.setSpanish();
		}

		orgNombre = new StringBuffer("a");
		ceroIz = new StringBuffer();
		ceroIz2 = new StringBuffer();

		// quaddtree

		// quad = new Quadtree(0, new Rectangle(0,0,ancho,alto));

		// tamaño de la pantalla
		ancho = Gdx.graphics.getWidth();
		alto = Gdx.graphics.getHeight();

		// listas
		ase = new Array<Senergy>();
		ase1 = new Array<Senergy>();
		ase2 = new Array<Senergy>();
		ase3 = new Array<Senergy>();
		ase4 = new Array<Senergy>();
		ase5 = new Array<Senergy>();
		ase6 = new Array<Senergy>();
		ase7 = new Array<Senergy>();
		ase8 = new Array<Senergy>();
		ase9 = new Array<Senergy>();
		ase10 = new Array<Senergy>();
		aqe = new Array<Qenergy>();
		aqe1 = new Array<Qenergy>();
		aqe2 = new Array<Qenergy>();
		aqe3 = new Array<Qenergy>();
		aqe4 = new Array<Qenergy>();
		aqe5 = new Array<Qenergy>();
		aqe6 = new Array<Qenergy>();
		aqe7 = new Array<Qenergy>();
		aqe8 = new Array<Qenergy>();
		aqe9 = new Array<Qenergy>();
		aqe10 = new Array<Qenergy>();

		organisms = new Array<Organism>();

		aEspesies = new Array<Organism>();

		aEspesiesTotales = new Array<Organism>();

		aAlelos = new Array<Allele>();
		aFenotipos = new Array<Phenotype>();
		aGenotipos = new Array<Genotype>();

		// set time to 0
		setDelta();
		setDelta2();
		setDelta3();
		setTiempo();

		linea = new StringBuffer();// used to write text in files

		organismTexture = new TextureAtlas("data/organismo.pack");


		frontera = new Rectangle();
		frontera.x = (ancho / 2) - 10;
		frontera.y = 0;
		frontera.height = alto;
		frontera.width = 20;

		// agregar cuantos de energia solar panel Izquierdo

		float x = 0;
		float y = alto;
		for (int i = 0; i < this.numSen; i++) {

			Vector2 pos = new Vector2((float) Math.random() * ancho / 2,
					(float) Math.random() * alto);

			x = x + 20;
			if (x >= ancho) {
				x = 0;
				y = y - 20;
			}

			Senergy se = new Senergy(pos, this);
			se.energy = this.Senergia;
			ase.add(se);
		}

		// agregar cuantos de energia solar panel Derecho

		for (int e = 0; e < this.numSenR; e++) {

			Vector2 pos = new Vector2((float) Math.random()
					* (ancho - (ancho / 2)) + ancho / 2, (float) Math.random()
					* alto);

			x = x + 20;
			if (x >= ancho) {
				x = 0;
				y = y - 20;
			}

			Senergy se = new Senergy(pos, this);
			se.energy = this.SenergiaR;
			ase.add(se);

		}

		ase.shuffle();
		ase.shrink();

		// agregar cuantos de energia en las listas divididas por sector

		for (int i = 0; i < ase.size; i++) {

			if (ase.get(i).posicion.x <= ancho * (1 / 10)) {
				ase1.add(ase.get(i));
			}
			if (ase.get(i).posicion.x > ancho * (1 / 10)
					&& ase.get(i).posicion.x <= ancho * (2 / 10)) {
				ase2.add(ase.get(i));
			}
			if (ase.get(i).posicion.x > ancho * (2 / 10)
					&& ase.get(i).posicion.x <= ancho * (3 / 10)) {
				ase3.add(ase.get(i));
			}
			if (ase.get(i).posicion.x > ancho * (3 / 10)
					&& ase.get(i).posicion.x <= ancho * (4 / 10)) {
				ase4.add(ase.get(i));
			}
			if (ase.get(i).posicion.x > ancho * (4 / 10)
					&& ase.get(i).posicion.x <= ancho * (5 / 10)) {
				ase5.add(ase.get(i));
			}
			if (ase.get(i).posicion.x > ancho * (5 / 10)
					&& ase.get(i).posicion.x <= ancho * (6 / 10)) {
				ase6.add(ase.get(i));
			}
			if (ase.get(i).posicion.x > ancho * (6 / 10)
					&& ase.get(i).posicion.x <= ancho * (7 / 10)) {
				ase7.add(ase.get(i));
			}
			if (ase.get(i).posicion.x > ancho * (7 / 10)
					&& ase.get(i).posicion.x <= ancho * (8 / 10)) {
				ase8.add(ase.get(i));
			}
			if (ase.get(i).posicion.x > ancho * (8 / 10)
					&& ase.get(i).posicion.x <= ancho * (9 / 10)) {
				ase9.add(ase.get(i));
			}
			if (ase.get(i).posicion.x > ancho * (9 / 10)
					&& ase.get(i).posicion.x <= ancho) {
				ase10.add(ase.get(i));
			}

		}

		ase1.shrink();// ajusta el tamaño de la lista a la cantedidad de
						// elmentos que tiene
		ase2.shrink();
		ase3.shrink();
		ase4.shrink();
		ase5.shrink();
		ase6.shrink();
		ase7.shrink();
		ase8.shrink();
		ase9.shrink();
		ase10.shrink();

		// agregar materia del lado izquierdo

		for (int i = 0; i < this.numQen; i++) {

			Vector2 pos = new Vector2((float) Math.random() * ancho / 2,
					(float) Math.random() * alto);

			aqe.add(new Qenergy(pos, moverMasa, Qbiomasa, this));
		}

		// agregar materia del lado Derecho

		for (int i = 0; i < this.numQenR; i++) {

			Vector2 pos = new Vector2((float) Math.random()
					* (ancho - (ancho / 2)) + ancho / 2, (float) Math.random()
					* alto);

			aqe.add(new Qenergy(pos, moverMasa, this.QbiomasaR, this));
		}

		// agregar materia invisible para usarla en el balanse de masa

		for (int i = 0; i < numQen / 3; i++) {

			Vector2 pos = new Vector2((float) Math.random() * ancho / 2,
					(float) Math.random() * alto);

			Qenergy qe = new Qenergy(pos, moverMasa, Qbiomasa, this);
			qe.visible = false;
			aqe.add(qe);
		}

		for (int i = 0; i < this.numQenR / 3; i++) {

			Vector2 pos = new Vector2((float) Math.random()
					* (ancho - (ancho / 2)) + ancho / 2, (float) Math.random()
					* alto);

			Qenergy qe = new Qenergy(pos, moverMasa, QbiomasaR, this);
			qe.visible = false;
			aqe.add(qe);
		}

		aqe.shrink();

		// repartirla materia izquierda y derecha

		// agregar cuantos de materia en las listas divididas por sector

		for (int i = 0; i < aqe.size; i++) {

			if (aqe.get(i).position.x <= ancho * (1 / 10)) {
				aqe1.add(aqe.get(i));
			}
			if (aqe.get(i).position.x > ancho * (1 / 10)
					&& aqe.get(i).position.x <= ancho * (2 / 10)) {
				aqe2.add(aqe.get(i));
			}
			if (aqe.get(i).position.x > ancho * (2 / 10)
					&& aqe.get(i).position.x <= ancho * (3 / 10)) {
				aqe3.add(aqe.get(i));
			}
			if (aqe.get(i).position.x > ancho * (3 / 10)
					&& aqe.get(i).position.x <= ancho * (4 / 10)) {
				aqe4.add(aqe.get(i));
			}
			if (aqe.get(i).position.x > ancho * (4 / 10)
					&& aqe.get(i).position.x <= ancho * (5 / 10)) {
				aqe5.add(aqe.get(i));
			}
			if (aqe.get(i).position.x > ancho * (5 / 10)
					&& aqe.get(i).position.x <= ancho * (6 / 10)) {
				aqe6.add(aqe.get(i));
			}
			if (aqe.get(i).position.x > ancho * (6 / 10)
					&& aqe.get(i).position.x <= ancho * (7 / 10)) {
				aqe7.add(aqe.get(i));
			}
			if (aqe.get(i).position.x > ancho * (7 / 10)
					&& aqe.get(i).position.x <= ancho * (8 / 10)) {
				aqe8.add(aqe.get(i));
			}
			if (aqe.get(i).position.x > ancho * (8 / 10)
					&& aqe.get(i).position.x <= ancho * (9 / 10)) {
				aqe9.add(aqe.get(i));
			}
			if (aqe.get(i).position.x > ancho * (9 / 10)
					&& aqe.get(i).position.x <= ancho) {
				aqe10.add(aqe.get(i));
			}

		}
		aqe1.shrink();
		aqe2.shrink();
		aqe3.shrink();
		aqe4.shrink();
		aqe5.shrink();
		aqe6.shrink();
		aqe7.shrink();
		aqe8.shrink();
		aqe9.shrink();
		aqe10.shrink();

		// colecta la primera especie en la lista

		for (Organism or : organisms) {

			boolean igual = false;

			id = or.identifier;
			for (Organism or2 : aEspesies) {

				if (id == or2.identifier) {
					igual = true;
				}

			}
			if (igual == false) {
				aEspesies.add(or);
			}

		}

		for (Organism or : organisms) {

			boolean igual = false;
			id = or.identifier;

			for (Organism or2 : aEspesiesTotales) {

				if (id == or2.identifier) {
					igual = true;
				}

			}
			if (igual == false) {
				aEspesiesTotales.add(or);
			}

		}

		// manejamos los archivos
		f_datos = new Archive();
		f_genes = new Archive();
		f_proteoma = new Archive();
		f_arbol = new Archive();
		f_poblacion = new Archive();
		f_mutantes = new Archive();
		f_alelos = new Archive();
		f_fenotipos = new Archive();
		f_genotipos = new Archive();
		f_alelos2 = new Archive();

	}

	// end of cronstructor metod

	// metodos

	public void agregarPrimerosOrg(int pan, boolean sex, int num, int tosRes,
			int anch, int alt, int senses, int optTem, int pred, int speed,
			int cazar, int escape, int radio, int fer, int parteNoGen,
			int relleno, int dnaPol, int longe, int col) {

		for (int i = 0; i < num; i++) {

			float a = 0;
			if (pan == 1) {
				a = (float) Math.random() * (ancho - (ancho / 2)) + ancho / 2;
			}
			if (pan == 2) {
				a = (float) Math.random() * ancho / 2;
			}

			Vector2 pos = new Vector2(a, (float) Math.random() * alto);

			Vector2 dir = new Vector2((float) Math.random() * 20,
					(float) Math.random() * 20);
			if (dir.x < 10) {
				dir.x = dir.x * (-1);
			}
			if (dir.x > 10) {
				dir.x = dir.x - 10;
			}
			if (dir.y < 10) {
				dir.y = dir.y * (-1);
			}
			if (dir.y > 10) {
				dir.y = dir.y - 10;
			}

			Organism or = new Organism(sex, new Genome(this, tosRes, anch,
					alt, senses, optTem, pred), new Genome(this, tosRes, anch,
					alt, senses, optTem, pred), new Genome(this, speed, cazar,
					escape, radio, fer, parteNoGen, relleno), new Genome(this,
					speed, cazar, escape, radio, fer, parteNoGen, relleno),
					new Genome(this, dnaPol, longe, col), new Genome(this,
					dnaPol, longe, col), pos, orgNombre, this);
			or.direction = dir;

			or.infected = true;

			organisms.add(or);

		}
		double o = BiomasaTotal(organisms) / Qbiomasa;

		for (int i = 0; i < o; i++) {

			Qenergy qe = aqe.get(i);
			qe.visible = false;

		}

		Masatotal = MateriaLibre() + BiomasaTotal(organisms);

		MasatotalL = MateriaLibreL() + BiomasaTotalI(organisms);

		MasatotalR = MateriaLibreR() + BiomasaTotalD(organisms);

		cantidadOrganismosI(organisms);
		cantidadOrganismosD(organisms);
		cantidadEspeciesI(organisms);
		cantidadEspeciesD(organisms);


	}

	public int cantidadOrganismos(Array<Organism> aor) {

		return aor.size;
	}


	public void cantidadOrganismosI(Array<Organism> aor) {

		int num = 0;

		for (int i = 0; i < aor.size; i++) {

			if (aor.get(i).position.x < ancho / 2) {
				num = num + 1;
			}
			//	System.out.println("dentro del loop"+ num);//return numeroI;

		}
		numeroI = num;
		//System.out.println("dentro del metodo"+ numeroI);//return numeroI;
	}

	//caluculos


	public void cantidadOrganismosD(Array<Organism> aor) {
		int num = 0;
		for (int i = 0; i < aor.size; i++) {

			if (aor.get(i).position.x > ancho / 2) {
				num = num + 1;
			}
		}
		numeroD = num;
		//return numeroD;
	}

	public void cantidadEspeciesI(Array<Organism> aor) {
		int num = 0;
		for (int i = 0; i < aor.size; i++) {

			if (aor.get(i).position.x > ancho / 2) {
				num = num + 1;
			}
		}
		numEspI = num;
		//return numeroD;
	}

	public void cantidadEspeciesD(Array<Organism> aor) {
		int num = 0;
		for (int i = 0; i < aor.size; i++) {

			if (aor.get(i).position.x > ancho / 2) {
				num = num + 1;
			}
		}
		numEspD = num;
		//return numeroD;
	}


	public void borrarOrganismo(Organism or) {

		organisms.removeValue(or, true);

		or = null;

		organisms.shrink();

	}

	// cuenta la cantidad de machos

	public int cantidadMachos(Array<Organism> aor) {

		numero = aor.size;
		int machos = 0;
		for (int i = numero - 1; i >= 0; i--) {

			if (aor.get(i).male == true) {
				machos = machos + 1;
			}

		}

		return machos;

	}

	public int cantidadMachosI(Array<Organism> aor) {

		numero = aor.size;
		int machos = 0;
		for (int i = numero - 1; i >= 0; i--) {

			if (aor.get(i).position.x < ancho / 2 && aor.get(i).male == true) {
				machos = machos + 1;
			}

		}

		return machos;

	}

	public int cantidadMachosD(Array<Organism> aor) {

		numero = aor.size;
		int machos = 0;
		for (int i = numero - 1; i >= 0; i--) {

			if (aor.get(i).position.x > ancho / 2 && aor.get(i).male == true) {
				machos = machos + 1;
			}

		}

		return machos;

	}

	public int cantidadHembras(Array<Organism> aor) {

		numero = aor.size;
		int hembras = 0;
		for (int i = numero - 1; i >= 0; i--) {

			if (aor.get(i).male == false) {
				hembras = hembras + 1;
			}

		}

		return hembras;

	}

	public int cantidadHembrasI(Array<Organism> aor) {

		numero = aor.size;
		int hembras = 0;
		for (int i = numero - 1; i >= 0; i--) {

			if (aor.get(i).position.x < ancho / 2 && aor.get(i).male == false) {
				hembras = hembras + 1;
			}

		}

		return hembras;

	}

	public int cantidadHembrasD(Array<Organism> aor) {

		numero = aor.size;
		int hembras = 0;
		for (int i = numero - 1; i >= 0; i--) {

			if (aor.get(i).position.x > ancho / 2 && aor.get(i).male == false) {
				hembras = hembras + 1;
			}

		}

		return hembras;

	}

	public double temOptimaMedia(Array<Organism> aor) {
		medTem = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				medTem = medTem + aor.get(i).optimalTemp;
			}
		}
		if (numero == 0) {
			numero = 1;
		}

		return medTem / numero;
	}

	public double temOptimaMediaI(Array<Organism> aor) {
		medTem = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).position.x < ancho / 2) {
					medTem = medTem + aor.get(i).optimalTemp;
				}
			}
		}
		if (numero == 0) {
			numero = 1;
		}

		return medTem / numeroI;
	}

	public double temOptimaMediaD(Array<Organism> aor) {
		medTem = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).position.x > ancho / 2) {
					medTem = medTem + aor.get(i).optimalTemp;
				}
			}
		}
		if (numero == 0) {
			numero = 1;
		}

		return medTem / numeroD;
	}


	public int cantidadPredadores(Array<Organism> aor) {
		cantidad = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).carnivore == true) {
				cantidad = cantidad + 1;
			}
		}
		return cantidad;
	}

	public int cantidadPredadoresI(Array<Organism> aor) {
		cantidad = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).position.x < ancho / 2
					&& aor.get(i).carnivore == true) {
				cantidad = cantidad + 1;
			}
		}
		return cantidad;
	}

	public int cantidadPredadoresD(Array<Organism> aor) {
		cantidad = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).position.x > ancho / 2
					&& aor.get(i).carnivore == true) {
				cantidad = cantidad + 1;
			}
		}
		return cantidad;
	}

	public int cantidadResistentes(Array<Organism> aor) {
		cantidad = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).resistanceATB == true) {
				cantidad = cantidad + 1;
			}
		}
		return cantidad;
	}

	public int cantidadResistentesI(Array<Organism> aor) {
		cantidad = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).position.x < ancho / 2
					&& aor.get(i).resistanceATB == true) {
				cantidad = cantidad + 1;
			}
		}
		return cantidad;
	}

	public int cantidadResistentesD(Array<Organism> aor) {
		cantidad = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).position.x > ancho / 2
					&& aor.get(i).resistanceATB == true) {
				cantidad = cantidad + 1;
			}
		}
		return cantidad;
	}

	public float velocidadMedia(Array<Organism> aor) {
		medSpeed = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				medSpeed = medSpeed + aor.get(i).speed;
			}
		}
		if (numero == 0 || numeroI == 0 || numeroD == 0) {
			numero = 1;
			numeroI = 1;
			numeroD = 1;
		}

		return medSpeed / numero;
	}

	public float velocidadMediaI(Array<Organism> aor) {
		medSpeed = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).position.x < ancho / 2) {
					medSpeed = medSpeed + aor.get(i).speed;
				}
			}
		}
		if (numero == 0 || numeroI == 0 || numeroD == 0) {
			numero = 1;
			numeroI = 1;
			numeroD = 1;
		}

		return medSpeed / numeroI;
	}

	public float velocidadMediaD(Array<Organism> aor) {
		medSpeed = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).position.x > ancho / 2) {
					medSpeed = medSpeed + aor.get(i).speed;
				}
			}
		}
		if (numero == 0 || numeroI == 0 || numeroD == 0) {
			numero = 1;
			numeroI = 1;
			numeroD = 1;
		}

		return medSpeed / numeroD;
	}

	public int tasaMutMedio(Array<Organism> aor) {
		medMut = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				medMut = (int) (medMut + aor.get(i).mutationRate);
			}
		}
		if (numero == 0 || numeroI == 0 || numeroD == 0) {
			numero = 1;
			numeroI = 1;
			numeroD = 1;
		}

		return (medMut / numero);
	}

	public int tasaMutMedioI(Array<Organism> aor) {
		medMut = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).position.x < ancho / 2) {
					medMut = (int) (medMut + aor.get(i).mutationRate);
				}
			}
		}
		if (numero == 0 || numeroI == 0 || numeroD == 0) {
			numero = 1;
			numeroI = 1;
			numeroD = 1;
		}

		return (medMut / numeroI);
	}

	public int tasaMutMedioD(Array<Organism> aor) {
		medMut = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).position.x > ancho / 2) {
					medMut = (int) (medMut + aor.get(i).mutationRate);
				}
			}
		}
		if (numero == 0 || numeroI == 0 || numeroD == 0) {
			numero = 1;
			numeroI = 1;
			numeroD = 1;
		}

		return (medMut / numeroD);
	}

	public float longevidadMedio(Array<Organism> aor) {
		medLon = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				medLon = (int) (medLon + aor.get(i).longevity);
			}
		}
		if (numero == 0 || numeroI == 0 || numeroD == 0) {
			numero = 1;
			numeroI = 1;
			numeroD = 1;
		}

		return (float) (medLon) / 1000 / numero;
	}

	public float longevidadMedioI(Array<Organism> aor) {
		medLon = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).position.x < ancho / 2) {
					medLon = (int) (medLon + aor.get(i).longevity);
				}
			}
		}
		if (numero == 0 || numeroI == 0 || numeroD == 0) {
			numero = 1;
			numeroI = 1;
			numeroD = 1;
		}

		return (float) (medLon) / 1000 / numeroI;
	}

	public float longevidadMedioD(Array<Organism> aor) {
		medLon = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).position.x > ancho / 2) {
					medLon = (int) (medLon + aor.get(i).longevity);
				}
			}
		}
		if (numero == 0 || numeroI == 0 || numeroD == 0) {
			numero = 1;
			numeroI = 1;
			numeroD = 1;
		}

		return (float) (medLon) / 1000 / numeroD;
	}

	public float saludMedio(Array<Organism> aor) {
		salud = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				salud = salud + aor.get(i).SaludCoefi;
			}
		}
		if (numero == 0 || numeroI == 0 || numeroD == 0) {
			numero = 1;
			numeroI = 1;
			numeroD = 1;
		}

		return salud / numero;
	}

	public float saludMedioD(Array<Organism> aor) {
		salud = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).position.x > ancho / 2) {
					salud = salud + aor.get(i).SaludCoefi;
				}
			}
		}
		if (numero == 0 || numeroI == 0 || numeroD == 0) {
			numero = 1;
			numeroI = 1;
			numeroD = 1;
		}

		return salud / numeroD;
	}

	public float saludMedioI(Array<Organism> aor) {
		salud = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).position.x < ancho / 2) {
					salud = salud + aor.get(i).SaludCoefi;
				}
			}
		}
		if (numero == 0 || numeroI == 0 || numeroD == 0) {
			numero = 1;
			numeroI = 1;
			numeroD = 1;
		}

		return salud / numeroI;
	}

	public float tamanoMedio(Array<Organism> aor) {
		medSize = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				medSize = medSize + aor.get(i).capacity;
			}
		}
		if (numero == 0 || numeroI == 0 || numeroD == 0) {
			numero = 1;
			numeroI = 1;
			numeroD = 1;
		}

		return medSize / numero;
	}

	public float tamanoMedioI(Array<Organism> aor) {
		medSize = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).position.x < ancho / 2) {
					medSize = medSize + aor.get(i).capacity;
				}
			}
		}
		if (numero == 0 || numeroI == 0 || numeroD == 0) {
			numero = 1;
			numeroI = 1;
			numeroD = 1;
		}

		return medSize / numeroI;
	}

	public float tamanoMedioD(Array<Organism> aor) {
		medSize = 0;
		numero = aor.size;
		if (numero > 0) {
			for (int i = numero - 1; i >= 0; i--) {
				if (aor.get(i).position.x > ancho / 2) {
					medSize = medSize + aor.get(i).capacity;
				}
			}
		}
		if (numero == 0 || numeroI == 0 || numeroD == 0) {
			numero = 1;
			numeroI = 1;
			numeroD = 1;
		}

		return medSize / numeroD;
	}

	public int MateriaLibre() {
		int materia = 0;
		numero = aqe.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aqe.get(i).visible == true) {
				materia = (int) (materia + aqe.get(i).mass);
			}
		}
		return materia;
	}

	public int MateriaLibreL() {
		int materia = 0;
		numero = aqe.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aqe.get(i).position.x < ancho / 2 && aqe.get(i).visible == true) {
				materia = (int) (materia + aqe.get(i).mass);
			}
		}
		return materia;
	}

	public int MateriaLibreR() {
		int materia = 0;
		numero = aqe.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aqe.get(i).position.x > ancho / 2 && aqe.get(i).visible == true) {
				materia = (int) (materia + aqe.get(i).mass);
			}
		}
		return materia;
	}

	public double BiomasaTotal(Array<Organism> aor) {
		int biomasaTotal = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			biomasaTotal = (int) (biomasaTotal + aor.get(i).biomass);
		}
		return biomasaTotal;
	}

	public int BiomasaTotalD(Array<Organism> aor) {
		int biomasaTotal = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).position.x > ancho / 2) {
				biomasaTotal = (int) (biomasaTotal + aor.get(i).biomass);
			}
		}
		return biomasaTotal;
	}

	public int BiomasaTotalI(Array<Organism> aor) {
		int biomasaTotal = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			if (aor.get(i).position.x < ancho / 2) {
				biomasaTotal = (int) (biomasaTotal + aor.get(i).biomass);
			}
		}
		return biomasaTotal;
	}

	public int BioenergiaTotal(Array<Organism> aor) {
		int energiaTotal = 0;
		numero = aor.size;
		for (int i = numero - 1; i >= 0; i--) {
			energiaTotal = (int) (energiaTotal + aor.get(i).energy);
		}
		return energiaTotal;
	}

	// to correct rounding errors in the total mass of the system, add or remove
	// mass

	public void chequearBalance() {

		if (verFrontera == false) {

			double a = Masatotal - (MateriaLibre() + BiomasaTotal(organisms));

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			a = Masatotal - (MateriaLibre() + BiomasaTotal(organisms));

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			a = Masatotal - (MateriaLibre() + BiomasaTotal(organisms));

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			a = Masatotal - (MateriaLibre() + BiomasaTotal(organisms));

			if (a < Qbiomasa * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			if (a < Qbiomasa * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			a = Masatotal - (MateriaLibre() + BiomasaTotal(organisms));

			if (a < Qbiomasa * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			if (a < Qbiomasa * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

		}

		if (verFrontera == true) {
			double a = MasatotalL - (MateriaLibreL() + BiomasaTotalI(organisms));
			numero = 0;
			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x < ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x < ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x < ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			a = MasatotalL - (MateriaLibreL() + BiomasaTotalI(organisms));

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x < ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x < ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x < ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			a = MasatotalL - (MateriaLibreL() + BiomasaTotalI(organisms));

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x < ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x < ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (a > Qbiomasa) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x < ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			a = MasatotalL - (MateriaLibreL() + BiomasaTotalI(organisms));

			if (a < Qbiomasa * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x < ancho / 2
							&& aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			if (a < Qbiomasa * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x < ancho / 2
							&& aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			a = MasatotalL - (MateriaLibreL() + BiomasaTotalI(organisms));

			if (a < Qbiomasa * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x < ancho / 2
							&& aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			if (a < Qbiomasa * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x < ancho / 2
							&& aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			// lado derecho

			double b = MasatotalR - (MateriaLibreR() + BiomasaTotalD(organisms));

			if (b > QbiomasaR) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x > ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (b > QbiomasaR) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x > ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (b > QbiomasaR) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x > ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			b = MasatotalR - (MateriaLibreR() + BiomasaTotalD(organisms));

			if (b > QbiomasaR) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x > ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (b > QbiomasaR) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x > ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (b > QbiomasaR) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x > ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			b = MasatotalR - (MateriaLibreR() + BiomasaTotalD(organisms));

			if (b > QbiomasaR) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x > ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (b > QbiomasaR) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x > ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			if (b > QbiomasaR) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x > ancho / 2
							&& aqe.get(i).visible == false) {
						aqe.get(i).visible = true;
						i = 0;
					}
				}
			}

			b = MasatotalR - (MateriaLibreR() + BiomasaTotalD(organisms));

			if (b < QbiomasaR * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x > ancho / 2
							&& aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			if (b < QbiomasaR * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x > ancho / 2
							&& aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			b = MasatotalR - (MateriaLibreR() + BiomasaTotalD(organisms));

			if (b < QbiomasaR * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x > ancho / 2
							&& aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

			if (b < QbiomasaR * (-1)) {
				numero = aqe.size;
				for (int i = numero - 1; i >= 0; i--) {
					if (aqe.get(i).position.x > ancho / 2
							&& aqe.get(i).visible == true) {
						aqe.get(i).visible = false;
						i = 0;
					}
				}
			}

		}
	}

	// kill 95% of all organism

	public void catastrofe() {

		numero = organisms.size;
		for (int i = numero - 1; i >= 0; i--) {

			int e = (int) (Math.random() * 10000);

			if (e > 500) {
				organisms.get(i).morir();
			}

		}

	}

	// metodos para colectar los datos

	public void guardarPoblacion() {

		try {
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(ruta));
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"población Total" + "     (pob2)", "pob2");

			fc.setFileFilter(filter);

			int returnVal = fc.showSaveDialog(fc);

			if (fc.getFileFilter() == filter) {

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// File file = fc.getSelectedFile();

					f_poblacion.createArchive(fc.getSelectedFile() + ".pob2");

					StringBuffer linea = new StringBuffer();

					numero = organisms.size;

					for (int i = numero - 1; i >= 0; i--) {
						Organism or = organisms.get(i);
						linea.replace(0, linea.length(), "<Male>" + or.male
								+ "<h>" + or.nombre.toString() + "dX"
								+ or.position.x + "dY" + or.position.y
								+ "<atb>" + or.adn.resistanceATB + "<ancho>"
								+ or.adn.width + "<alto>" + or.adn.height
								+ "<sentir>" + or.adn.sense + "<temp>"
								+ or.adn.toleranceTemp + "<predador>"
								+ or.adn.predator + "<speed>" + or.adn3.speed
								+ "<cazar>" + or.adn3.hunt + "<escapar>"
								+ or.adn3.escape + "<alcance>"
								+ or.adn3.radius + "<fero>"
								+ or.adn3.pheromone + "<noGen>"
								+ or.adn3.parteNoGen + "<tasamut>"
								+ or.adn5.mutationRate + "<longevidad>"
								+ or.adn5.longevity + "<color>"
								+ or.adn5.color + "<atb2>"
								+ or.adn2.resistanceATB + "<ancho2>"
								+ or.adn2.width + "<alto2>" + or.adn2.height
								+ "<sentir2>" + or.adn2.sense + "<temp2>"
								+ or.adn2.toleranceTemp + "<predador2>"
								+ or.adn2.predator + "<speed2>" + or.adn4.speed
								+ "<cazar2>" + or.adn4.hunt + "<escapar2>"
								+ or.adn4.escape + "<alcance2>"
								+ or.adn4.radius + "<fero2>"
								+ or.adn4.pheromone + "<noGen2>"
								+ or.adn4.parteNoGen + "<tasamut2>"
								+ or.adn6.mutationRate + "<longevidad2>"
								+ or.adn6.longevity + "<color2>"
								+ or.adn6.color + "\n");
						f_poblacion.writeArchive(linea.toString());
					}
					f_poblacion.cerrarArchivo();

				}
			}
		} catch (HeadlessException e) {
			JOptionPane.showMessageDialog(null, "error al escribir el archivo");
			e.printStackTrace();
		}
	}

	public void guardarPoblacionMarcada() {

		try {
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(ruta));
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"población Marcada" + "     (pob2)", "pob2");

			fc.setFileFilter(filter);

			int returnVal = fc.showSaveDialog(fc);

			if (fc.getFileFilter() == filter) {

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// File file = fc.getSelectedFile();

					f_poblacion.createArchive(fc.getSelectedFile() + ".pob2");

					StringBuffer linea = new StringBuffer();

					numero = organisms.size;

					for (int i = numero - 1; i >= 0; i--) {
						Organism or = organisms.get(i);
						if (or.mark == -1) { // -1 == true
							linea.replace(0, linea.length(), "<Male>" + or.male
									+ "<h>" + or.nombre.toString() + "dX"
									+ or.position.x + "dY" + or.position.y
									+ "<atb>" + or.adn.resistanceATB
									+ "<ancho>" + or.adn.width + "<alto>"
									+ or.adn.height + "<sentir>" + or.adn.sense
									+ "<temp>" + or.adn.toleranceTemp
									+ "<predador>" + or.adn.predator
									+ "<speed>" + or.adn3.speed + "<cazar>"
									+ or.adn3.hunt + "<escapar>"
									+ or.adn3.escape + "<alcance>"
									+ or.adn3.radius + "<fero>"
									+ or.adn3.pheromone + "<noGen>"
									+ or.adn3.parteNoGen + "<tasamut>"
									+ or.adn5.mutationRate + "<longevidad>"
									+ or.adn5.longevity + "<color>"
									+ or.adn5.color + "<atb2>"
									+ or.adn2.resistanceATB + "<ancho2>"
									+ or.adn2.width + "<alto2>" + or.adn2.height
									+ "<sentir2>" + or.adn2.sense + "<temp2>"
									+ or.adn2.toleranceTemp + "<predador2>"
									+ or.adn2.predator + "<speed2>"
									+ or.adn4.speed + "<cazar2>"
									+ or.adn4.hunt + "<escapar2>"
									+ or.adn4.escape + "<alcance2>"
									+ or.adn4.radius + "<fero2>"
									+ or.adn4.pheromone + "<noGen2>"
									+ or.adn4.parteNoGen + "<tasamut2>"
									+ or.adn6.mutationRate + "<longevidad2>"
									+ or.adn6.longevity + "<color2>"
									+ or.adn6.color + "\n");
							f_poblacion.writeArchive(linea.toString());
						}
					}
					f_poblacion.cerrarArchivo();

				}
			}
		} catch (HeadlessException e) {
			JOptionPane.showMessageDialog(null, "error al escribir el archivo");
			e.printStackTrace();
		}
	}

	public void guardarPoblacionNoMaracada() {

		try {
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(ruta));
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"población No Marcada" + "     (pob2)", "pob2");

			fc.setFileFilter(filter);

			int returnVal = fc.showSaveDialog(fc);

			if (fc.getFileFilter() == filter) {

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// File file = fc.getSelectedFile();

					f_poblacion.createArchive(fc.getSelectedFile() + ".pob2");

					StringBuffer linea = new StringBuffer();

					numero = organisms.size;

					for (int i = numero - 1; i >= 0; i--) {
						Organism or = organisms.get(i);
						if (or.mark == 1) { // 1 = false
							linea.replace(0, linea.length(), "<Male>" + or.male
									+ "<h>" + or.nombre.toString() + "dX"
									+ or.position.x + "dY" + or.position.y
									+ "<atb>" + or.adn.resistanceATB
									+ "<ancho>" + or.adn.width + "<alto>"
									+ or.adn.height + "<sentir>" + or.adn.sense
									+ "<temp>" + or.adn.toleranceTemp
									+ "<predador>" + or.adn.predator
									+ "<speed>" + or.adn3.speed + "<cazar>"
									+ or.adn3.hunt + "<escapar>"
									+ or.adn3.escape + "<alcance>"
									+ or.adn3.radius + "<fero>"
									+ or.adn3.pheromone + "<noGen>"
									+ or.adn3.parteNoGen + "<tasamut>"
									+ or.adn5.mutationRate + "<longevidad>"
									+ or.adn5.longevity + "<color>"
									+ or.adn5.color + "<atb2>"
									+ or.adn2.resistanceATB + "<ancho2>"
									+ or.adn2.width + "<alto2>" + or.adn2.height
									+ "<sentir2>" + or.adn2.sense + "<temp2>"
									+ or.adn2.toleranceTemp + "<predador2>"
									+ or.adn2.predator + "<speed2>"
									+ or.adn4.speed + "<cazar2>"
									+ or.adn4.hunt + "<escapar2>"
									+ or.adn4.escape + "<alcance2>"
									+ or.adn4.radius + "<fero2>"
									+ or.adn4.pheromone + "<noGen2>"
									+ or.adn4.parteNoGen + "<tasamut2>"
									+ or.adn6.mutationRate + "<longevidad2>"
									+ or.adn6.longevity + "<color2>"
									+ or.adn6.color + "\n");
							f_poblacion.writeArchive(linea.toString());
						}
					}
					f_poblacion.cerrarArchivo();

				}
			}
		} catch (HeadlessException e) {
			JOptionPane.showMessageDialog(null, "error al escribir el archivo");
			e.printStackTrace();
		}
	}

	public void leerArchivoPoblacion() {

		for (Organism or : organisms) {
			organisms.removeValue(or, true);
		}
		for (Organism or : aEspesiesTotales) {
			aEspesiesTotales.removeValue(or, true);
		}

		try {

			FileReader fr = new FileReader(poblacion);
			BufferedReader br = new BufferedReader(fr);
			String linea = null;
			while ((linea = br.readLine()) != null) {

				Genome gen = new Genome(this, 1, 1, 1, 0, 0, 0);
				Genome gen2 = new Genome(this, 1, 1, 1, 0, 0, 0);

				Genome gen3 = new Genome(this, 0, 0, 0, 0, 0, 0, 0);
				Genome gen4 = new Genome(this, 0, 0, 0, 0, 0, 0, 0);

				Genome gen5 = new Genome(this, 0, 0, 0);
				Genome gen6 = new Genome(this, 0, 0, 0);

				StringBuffer sexo = new StringBuffer(linea.substring(
						linea.indexOf("<Male>") + 6, linea.indexOf("<h>")));
				StringBuffer nombre = new StringBuffer(linea.substring(
						linea.indexOf("<h>") + 3, linea.indexOf("dX")));
				Vector2 pos = new Vector2(
						Float.parseFloat(linea.substring(
								linea.indexOf("dX") + 2, linea.indexOf("dY"))),
						Float.parseFloat(linea.substring(
								linea.indexOf("dY") + 2, linea.indexOf("<atb>"))));

				boolean male = true;

				if (sexo.toString().equals("true")) {
					male = true;
				}
				if (sexo.toString().equals("false")) {
					male = false;
				}

				gen.resistanceATB.replace(
						0,
						gen.resistanceATB.length(),
						linea.substring(linea.indexOf("<atb>") + 5,
								linea.indexOf("<ancho>")));
				gen.width.replace(
						0,
						gen.width.length(),
						linea.substring(linea.indexOf("<ancho>") + 7,
								linea.indexOf("<alto>")));
				gen.height.replace(
						0,
						gen.height.length(),
						linea.substring(linea.indexOf("<alto>") + 6,
								linea.indexOf("<sentir>")));
				gen.sense.replace(
						0,
						gen.sense.length(),
						linea.substring(linea.indexOf("<sentir>") + 8,
								linea.indexOf("<temp>")));
				gen.toleranceTemp.replace(
						0,
						gen.toleranceTemp.length(),
						linea.substring(linea.indexOf("<temp>") + 6,
								linea.indexOf("<predador>")));
				gen.predator.replace(
						0,
						gen.predator.length(),
						linea.substring(linea.indexOf("<predador>") + 10,
								linea.indexOf("<speed>")));

				gen3.speed.replace(
						0,
						gen3.speed.length(),
						linea.substring(linea.indexOf("<speed>") + 7,
								linea.indexOf("<cazar>")));
				gen3.hunt.replace(
						0,
						gen3.hunt.length(),
						linea.substring(linea.indexOf("<cazar>") + 7,
								linea.indexOf("<escapar>")));
				gen3.escape.replace(
						0,
						gen3.escape.length(),
						linea.substring(linea.indexOf("<escapar>") + 9,
								linea.indexOf("<alcance>")));
				gen3.radius.replace(
						0,
						gen3.radius.length(),
						linea.substring(linea.indexOf("<alcance>") + 9,
								linea.indexOf("<fero>")));
				gen3.pheromone.replace(
						0,
						gen3.pheromone.length(),
						linea.substring(linea.indexOf("<fero>") + 6,
								linea.indexOf("<noGen>")));
				gen3.parteNoGen.replace(
						0,
						gen4.parteNoGen.length(),
						linea.substring(linea.indexOf("<noGen>") + 7,
								linea.indexOf("<tasamut2>")));

				gen5.mutationRate.replace(
						0,
						gen5.mutationRate.length(),
						linea.substring(linea.indexOf("<tasamut>") + 9,
								linea.indexOf("<longevidad>")));
				gen5.longevity.replace(0, gen5.longevity.length(), linea
						.substring(linea.indexOf("<longevidad>") + 12,
								linea.indexOf("<color>")));
				gen5.color.replace(
						0,
						gen5.color.length(),
						linea.substring(linea.indexOf("<color>") + 7
						));

				gen2.resistanceATB.replace(
						0,
						gen2.resistanceATB.length(),
						linea.substring(linea.indexOf("<atb2>") + 6,
								linea.indexOf("<ancho2>")));
				gen2.width.replace(
						0,
						gen2.width.length(),
						linea.substring(linea.indexOf("<ancho2>") + 8,
								linea.indexOf("<alto2>")));
				gen2.height.replace(
						0,
						gen2.height.length(),
						linea.substring(linea.indexOf("<alto2>") + 7,
								linea.indexOf("<sentir2>")));
				gen2.sense.replace(
						0,
						gen2.sense.length(),
						linea.substring(linea.indexOf("<sentir2>") + 9,
								linea.indexOf("<temp2>")));
				gen2.toleranceTemp.replace(
						0,
						gen2.toleranceTemp.length(),
						linea.substring(linea.indexOf("<temp2>") + 7,
								linea.indexOf("<predador2>")));
				gen2.predator.replace(0, gen2.predator.length(), linea
						.substring(linea.indexOf("<predador2>") + 11,
								linea.indexOf("<speed2>")));

				gen4.speed.replace(
						0,
						gen4.speed.length(),
						linea.substring(linea.indexOf("<speed2>") + 8,
								linea.indexOf("<cazar2>")));
				gen4.hunt.replace(
						0,
						gen4.hunt.length(),
						linea.substring(linea.indexOf("<cazar2>") + 8,
								linea.indexOf("<escapar2>")));
				gen4.escape.replace(
						0,
						gen4.escape.length(),
						linea.substring(linea.indexOf("<escapar2>") + 10,
								linea.indexOf("<alcance2>")));
				gen4.radius.replace(
						0,
						gen4.radius.length(),
						linea.substring(linea.indexOf("<alcance2>") + 10,
								linea.indexOf("<fero2>")));
				gen4.pheromone.replace(
						0,
						gen4.pheromone.length(),
						linea.substring(linea.indexOf("<fero2>") + 7,
								linea.indexOf("<noGen2>")));
				gen4.parteNoGen.replace(
						0,
						gen4.parteNoGen.length(),
						linea.substring(linea.indexOf("<noGen2>") + 8,
								linea.indexOf("<tasamut2>")));

				gen6.mutationRate.replace(
						0,
						gen6.mutationRate.length(),
						linea.substring(linea.indexOf("<tasamut2>") + 10,
								linea.indexOf("<longevidad2>")));
				gen6.longevity.replace(0, gen6.longevity.length(), linea
						.substring(linea.indexOf("<longevidad2>") + 11,
								linea.indexOf("<color2>")));
				gen6.color.replace(
						0,
						gen6.color.length(),
						linea.substring(linea.indexOf("<color2>") + 8
						));

				Organism or = new Organism(male, gen, gen2, gen3, gen4, gen5,
						gen6, pos, nombre, this);

				// asiganr una direccion a cada organismo

				float x = (float) Math.random() * 360;
				float seno = (float) Math.sin(x) * 10;
				float coseno = (float) Math.sin(x + 3.1416 / 2) * 10;
				Vector2 dir = new Vector2(seno, coseno);

				or.direction.x = dir.x;
				or.direction.y = dir.y;
				organisms.add(or);
			}

			Masatotal = MateriaLibre() + BiomasaTotal(organisms);

			MasatotalL = MateriaLibreL() + BiomasaTotalI(organisms);

			MasatotalR = MateriaLibreR() + BiomasaTotalD(organisms);

			br.close();
			fr.close();

		} catch (IOException ex) {

			JOptionPane
					.showMessageDialog(null, "no se puede leer este archivo");

			for (int i = 0; i < numOrg; i++) {

				Vector2 dir = new Vector2((float) Math.random() * 20,
						(float) Math.random() * 20);
				if (dir.x < 10) {
					dir.x = dir.x * (-1);
				}
				if (dir.x > 10) {
					dir.x = dir.x - 10;
				}
				if (dir.y < 10) {
					dir.y = dir.y * (-1);
				}
				if (dir.y > 10) {
					dir.y = dir.y - 10;
				}

				ex.printStackTrace();
			}
		}
	}

	// ordena las los organismos para que se vean mejor al final

	public void Ordenar() {

		if (verFrontera == false) {

			organisms.sort();

			float y = (int) (alto - 120);
			float x = 10;

			for (Organism or : organisms) {

				or.position.x = -100;
				or.position.y = -100;

				or.Ordenar();// este metodo mueve los organismosa un nueva
				// posicion sin usar el metodo update

			}

			for (int e = indice; e < organisms.size; e++) {
				Organism or = organisms.get(e);

				indice++;

				or.position.y = y;
				or.position.x = x;

				x = (int) (x + or.width * 4);

				if (x > ancho - or.width) {
					x = 10;
					y = y - or.height * 6;
				}

				if (y <= 10) {
					e = organisms.size;
				} // si se llena la pantalla de organismos, se detiene el loop

				or.Ordenar();

			}

		}

		if (verFrontera == true) {

			// actualizarListasOrganismos();

			// oreganismos de la derecha

			// lado derecho

			float y = (int) (alto - 120);
			float x = (ancho / 2) + 10;

			for (int e = indiceDer; e < organisms.size; e++) {

				if (organisms.get(e).position.x > ancho / 2) {

					indiceDer++;

					organisms.get(e).position.y = y;
					organisms.get(e).position.x = x;

					x = (int) (x + organisms.get(e).width * 4);

					if (x > ancho - organisms.get(e).width * 4) {
						x = (ancho / 2) + 10;
						y = y - organisms.get(e).height * 6;
					}

					if (y <= 10) {
						e = organisms.size;
					}
				} // si se llena la pantalla de organismos, se detiene el loop

				organisms.get(e).Ordenar();

			}

			// oreganismos de la Izquierda

			float y2 = (int) (alto - 120);
			float x2 = 10;

			for (int e = indiceIz; e < organisms.size; e++) {

				if (organisms.get(e).position.x < ancho / 2) {
					indiceIz++;

					organisms.get(e).position.y = y2;
					organisms.get(e).position.x = x2;

					x2 = (int) (x2 + organisms.get(e).width * 4);

					if (x2 > (ancho / 2) - organisms.get(e).width * 4) {
						x2 = 10;
						y2 = y2 - organisms.get(e).height * 6;
					}

					if (y2 <= 10) {
						e = organisms.size;
					}
				} // si se llena la pantalla de organismos, se detiene el loop

				organisms.get(e).Ordenar();

			}
		}
	}

	// guara las especies que estan en un momento determinado

	// colectar mutantes o especies del momento

	public void colectorEspesies() {

		for (Organism or : aEspesies) {
			or.quantity = 1;
			aEspesies.removeValue(or, true);
		}

		for (Organism or : organisms) {

			boolean igual = false;

			String id = or.nombre.toString();
			for (Organism or2 : aEspesies) {

				if (id.equals(or2.nombre.toString())) {
					igual = true;
					or2.quantity++;
				}

			}
			if (igual == false) {
				aEspesies.add(or);
			}

		}

		aEspesies.sort();

	}

	// colect color allele
	public void colectGenotipo(Array<Organism> aor, Array<Genotype> agen) {

		if (aor.size > 0) {
			int index = agen.size;
			for (int i = index - 1; i >= 0; i--) {
				agen.get(i).quantity = 1;
				agen.removeValue(agen.get(i), true);
			}

			aor.get(0).genotype.quantity = 0;
			agen.add(aor.get(0).genotype);// agrege el primer alelo

			for (int i = 0; i < aor.size; i++) {

				boolean igual = false;

				for (int j = 0; j < agen.size; j++) {

					if (agen.get(j).name.equals(aor.get(i).genotype.name)) {

						igual = true;
						agen.get(j).quantity++;
					}

				}
				if (igual == false) {
					agen.add(aor.get(i).genotype);
				}
			}

			agen.sort();

		}
	}

	public void colectGenotipoI(Array<Organism> aor, Array<Genotype> agen) {

		if (aor.size > 0) {
			int index = agen.size;
			for (int i = index - 1; i >= 0; i--) {
				agen.get(i).quantity = 1;
				agen.removeValue(agen.get(i), true);
			}

			int pos = 0;
			for (int i = 0; i < organisms.size; i++) {
				if (organisms.get(i).position.x < ancho / 2) {
					pos = i;
					i = organisms.size;
				}
			}

			aor.get(pos).genotype.quantity = 0;
			agen.add(aor.get(pos).genotype);// agrege el primer alelo

			for (int i = 0; i < aor.size; i++) {

				boolean igual = false;
				if (aor.get(i).position.x < ancho / 2) {
					for (int j = 0; j < agen.size; j++) {

						if (agen.get(j).name
								.equals(aor.get(i).genotype.name)) {

							igual = true;
							agen.get(j).quantity++;
						}

					}
					if (igual == false) {
						agen.add(aor.get(i).genotype);
					}
				}
			}

			agen.sort();
		}
	}

	public void colectGenotipoD(Array<Organism> aor, Array<Genotype> agen) {

		if (aor.size > 0) {
			int index = agen.size;
			for (int i = index - 1; i >= 0; i--) {
				agen.get(i).quantity = 1;
				agen.removeValue(agen.get(i), true);
			}

			int pos = 0;
			for (int i = 0; i < organisms.size; i++) {
				if (organisms.get(i).position.x > ancho / 2) {
					pos = i;
					i = organisms.size;
				}
			}

			aor.get(pos).genotype.quantity = 0;
			agen.add(aor.get(pos).genotype);// agrege el primer alelo

			for (int i = 0; i < aor.size; i++) {

				boolean igual = false;
				if (aor.get(i).position.x > ancho / 2) {
					for (int j = 0; j < agen.size; j++) {

						if (agen.get(j).name
								.equals(aor.get(i).genotype.name)) {

							igual = true;
							agen.get(j).quantity++;
						}

					}
					if (igual == false) {
						agen.add(aor.get(i).genotype);
					}
				}
			}

			agen.sort();
		}
	}

	public void colectFenotipo(Array<Organism> aor, Array<Phenotype> afen) {

		if (aor.size > 0) {
			int index = afen.size;
			for (int i = index - 1; i >= 0; i--) {
				afen.get(i).quantity = 1;
				afen.removeValue(afen.get(i), true);
			}

			aor.get(0).phenotype.quantity = 0;
			afen.add(aor.get(0).phenotype);// agrege el primer alelo

			for (int i = 0; i < aor.size; i++) {

				boolean igual = false;

				for (int j = 0; j < afen.size; j++) {

					if (afen.get(j).name.equals(aor.get(i).phenotype.name)) {

						igual = true;
						afen.get(j).quantity++;
					}

				}
				if (igual == false) {
					afen.add(aor.get(i).phenotype);
				}
			}

			afen.sort();

		}
	}

	public void colectFenotipoI(Array<Organism> aor, Array<Phenotype> afen) {

		if (aor.size > 0) {
			int index = afen.size;
			for (int i = index - 1; i >= 0; i--) {
				afen.get(i).quantity = 1;
				afen.removeValue(afen.get(i), true);
			}

			int pos = 0;
			for (int i = 0; i < organisms.size; i++) {
				if (organisms.get(i).position.x < ancho / 2) {
					pos = i;
					i = organisms.size;
				}
			}

			aor.get(pos).phenotype.quantity = 0;
			afen.add(aor.get(pos).phenotype);// agrege el primer alelo

			for (int i = 0; i < aor.size; i++) {
				if (aor.get(i).position.x < ancho / 2) {
					boolean igual = false;

					for (int j = 0; j < afen.size; j++) {

						if (afen.get(j).name
								.equals(aor.get(i).phenotype.name)) {

							igual = true;
							afen.get(j).quantity++;
						}

					}
					if (igual == false) {
						afen.add(aor.get(i).phenotype);
					}
				}
			}

			afen.sort();

		}
	}

	public void colectFenotipoD(Array<Organism> aor, Array<Phenotype> afen) {

		if (aor.size > 0) {
			int index = afen.size;
			for (int i = index - 1; i >= 0; i--) {
				afen.get(i).quantity = 1;
				afen.removeValue(afen.get(i), true);
			}

			int pos = 0;
			for (int i = 0; i < organisms.size; i++) {
				if (organisms.get(i).position.x > ancho / 2) {
					pos = i;
					i = organisms.size;
				}
			}

			aor.get(pos).phenotype.quantity = 0;
			afen.add(aor.get(pos).phenotype);// agrege el primer alelo

			for (int i = 0; i < aor.size; i++) {
				if (aor.get(i).position.x > ancho / 2) {
					boolean igual = false;

					for (int j = 0; j < afen.size; j++) {

						if (afen.get(j).name
								.equals(aor.get(i).phenotype.name)) {

							igual = true;
							afen.get(j).quantity++;
						}

					}
					if (igual == false) {
						afen.add(aor.get(i).phenotype);
					}
				}
			}

			afen.sort();

		}
	}

	public void colectarAlelos(Array<Organism> aor, Array<Allele> aal) {
		if (aor.size > 0) {
			int index = aal.size;
			for (int i = index - 1; i >= 0; i--) {
				aal.get(i).quantity = 1;
				aal.removeValue(aal.get(i), true);
			}

			for (int i = 0; i < aor.get(0).aAlleles.size; i = i + 2) {

				Allele al = aor.get(0).aAlleles.get(i);
				al.quantity = 0;
				aal.add(al);

			} // se agregan los alelos delprimer organismo

			for (int i = 0; i < aor.size; i++) {

				Organism or = aor.get(i);

				for (int a = 0; a < or.aAlleles.size; a++) {

					Allele al = or.aAlleles.get(a);
					boolean igual = false;
					for (int j = 0; j < aal.size; j++) {

						if (aal.get(j).name.equals(al.name)
								&& aal.get(j).identifier == al.identifier) {

							igual = true;
							aal.get(j).quantity++;
						}

					}
					if (igual == false) {
						aal.add(al);
					}

				}

			}

			aal.sort();
		}
	}

	public void colectarAlelosI(Array<Organism> aor, Array<Allele> aal) {
		if (aor.size > 0) {
			int index = aal.size;
			for (int i = index - 1; i >= 0; i--) {
				aal.get(i).quantity = 1;
				aal.removeValue(aal.get(i), true);
			}// limpia la lista

			int pos = 0;
			for (int i = 0; i < organisms.size; i++) {
				if (organisms.get(i).position.x < ancho / 2) {
					pos = i;
					i = organisms.size;
				}
			}

			for (int i = 0; i < aor.get(pos).aAlleles.size; i = i + 2) {

				Allele al = aor.get(pos).aAlleles.get(i);
				al.quantity = 0;
				aal.add(al);
			} // se agregan los alelos delprimer organismo

			for (int i = 0; i < aor.size; i++) {

				Organism or = aor.get(i);
				if (or.position.x < ancho / 2) {
					for (int a = 0; a < or.aAlleles.size; a++) {

						Allele al = or.aAlleles.get(a);
						boolean igual = false;
						for (int j = 0; j < aal.size; j++) {

							if (aal.get(j).name.equals(al.name)
									&& aal.get(j).identifier == al.identifier) {

								igual = true;
								aal.get(j).quantity++;
							}

						}
						if (igual == false) {
							aal.add(al);
						}

					}
				}

			}

			aal.sort();
		}
	}

	public void colectarAlelosD(Array<Organism> aor, Array<Allele> aal) {
		if (aor.size > 0) {
			int index = aal.size;
			for (int i = index - 1; i >= 0; i--) {
				aal.get(i).quantity = 1;
				aal.removeValue(aal.get(i), true);
			}// limpia la lista

			int pos = 0;
			for (int i = 0; i < organisms.size; i++) {
				if (organisms.get(i).position.x > ancho / 2) {
					pos = i;
					i = organisms.size;
				}
			}

			for (int i = 0; i < aor.get(pos).aAlleles.size; i = i + 2) {

				Allele al = aor.get(pos).aAlleles.get(i);
				al.quantity = 0;
				aal.add(al);
			} // se agregan los alelos delprimer organismo

			for (int i = 0; i < aor.size; i++) {

				Organism or = aor.get(i);
				if (or.position.x > ancho / 2) {
					for (int a = 0; a < or.aAlleles.size; a++) {

						Allele al = or.aAlleles.get(a);
						boolean igual = false;
						for (int j = 0; j < aal.size; j++) {

							if (aal.get(j).name.equals(al.name)
									&& aal.get(j).identifier == al.identifier) {

								igual = true;
								aal.get(j).quantity++;
							}

						}
						if (igual == false) {
							aal.add(al);
						}

					}
				}

			}

			aal.sort();
		}
	}

	// colectar todas las que aparecen

	public void colectorEspesiesTotales(Array<Organism> aor) {

		for (Organism or : aor) {

			boolean igual = false;
			String id = or.nombre.toString();

			for (Organism or2 : aEspesiesTotales) {

				if (id.equals(or2.nombre.toString())) {
					igual = true;
				}

			}
			if (igual == false) {
				aEspesiesTotales.add(or);
			}

		}

	}

	public void archivarAlelos() {

		if (verFrontera == false) {

			colectarAlelos(organisms, aAlelos);

			numero = aAlelos.size;
			int cantidad = 0;

			for (Allele al : aAlelos) {
				cantidad = cantidad + al.quantity;
			}

			linea.replace(0, linea.length(), "");
			linea.append(addCero2().toString() + minutes2 + ":"
					+ addCero().toString() + seconds + "");

			for (int i = numero - 1; i >= 0; i--) {

				Allele al = aAlelos.get(i);

				if (!al.name.equals(tx.color)
						|| !al.name.equals(tx.longevidad)
						|| !al.name.equals(tx.fidelidadADNpol)) {

					cantidad = organisms.size * 2;
				}

				if (al.name.equals(tx.color)
						|| al.name.equals(tx.longevidad)
						|| al.name.equals(tx.fidelidadADNpol)) {

					cantidad = cantidadHembras(organisms) * 2 + cantidadMachos(organisms);
				}

				linea.append(";"
						+ al.name
						+ ";"
						+ "a"
						+ al.identifier
						+ ";"
						+ format3
						.format(((float) al.quantity / (float) cantidad) * 100)
						+ ";" + al.quantity);

				linea.append("\n");
			}
			linea.append("\n");
			f_alelos.writeArchive(linea.toString());
		}

		if (verFrontera == true) {

			colectarAlelosD(organisms, aAlelos);// trabajar con los organismosdela
			// derecha
			numero = aAlelos.size;
			int cantidadD = 0;
			for (Allele al : aAlelos) {
				cantidadD = cantidadD + al.quantity;
			}

			linea.replace(0, linea.length(), "");

			linea.append(addCero2().toString() + minutes2 + ":"
					+ addCero().toString() + seconds + "");
			linea.append("\n" + tx.panelDer + "\n");

			for (int i = numero - 1; i >= 0; i--) {

				Allele al = aAlelos.get(i);

				if (!al.name.equals(tx.color)
						|| !al.name.equals(tx.longevidad)
						|| !al.name.equals(tx.fidelidadADNpol)) {

					cantidadD = organisms.size * 2;
				}

				if (al.name.equals(tx.color)
						|| al.name.equals(tx.longevidad)
						|| al.name.equals(tx.fidelidadADNpol)) {

					cantidadD = cantidadHembrasD(organisms) * 2
							+ cantidadMachosD(organisms);
				}
				linea.append(";"
						+ al.name
						+ ";"
						+ "a"
						+ al.identifier
						+ ";"
						+ format3
						.format(((float) al.quantity / (float) al.quantity) * 100)
						+ ";" + al.quantity);

				linea.append("\n");
			}
			linea.append("\n");

			colectarAlelosI(organisms, aAlelos);// trabajar con los organismosdela
			// izquierd
			numero = aAlelos.size;
			int cantidadI = 0;
			for (Allele al : aAlelos) {
				cantidadI = cantidadI + al.quantity;
			}

			linea.append(tx.panelIz + "\n");// trabajar con los organismos de la
			// izquierda

			for (int i = numero - 1; i >= 0; i--) {

				Allele al = aAlelos.get(i);

				if (!al.name.equals(tx.color)
						|| !al.name.equals(tx.longevidad)
						|| !al.name.equals(tx.fidelidadADNpol)) {

					cantidadI = organisms.size * 2;
				}

				if (al.name.equals(tx.color)
						|| al.name.equals(tx.longevidad)
						|| al.name.equals(tx.fidelidadADNpol)) {

					cantidadI = cantidadHembrasI(organisms) * 2
							+ cantidadMachosI(organisms);
				}
				linea.append(";"
						+ al.name
						+ ";"
						+ "a"
						+ al.identifier
						+ ";"
						+ format3
						.format(((float) al.quantity / (float) al.quantity) * 100)
						+ ";" + al.quantity);

				linea.append("\n");
			}
			linea.append("\n");

			f_alelos.writeArchive(linea.toString());
		}

	}

	public void archivarFenotipo2() {

		if (verFrontera == false) {
			colectFenotipo(organisms, aFenotipos);

			numero = aFenotipos.size;
			int cantidad = 0;

			for (Phenotype fn : aFenotipos) {
				cantidad = cantidad + fn.quantity;
			}

			linea.replace(0, linea.length(), "");

			for (int i = numero - 1; i >= 0; i--) {

				Phenotype fn = aFenotipos.get(i);

				if (i == numero - 1) {
					linea.append(";"
							+ addCero2().toString()
							+ minutes2
							+ ":"
							+ addCero().toString()
							+ seconds
							+ ";"
							+ fn.name
							+ ";"
							+ fn.quantity
							+ ";"
							+ format.format(((float) fn.quantity / (float) cantidad) * 100));
				}
				if (i < numero - 1) {
					linea.append(";;"
							+ fn.name
							+ ";"
							+ fn.quantity
							+ ";"
							+ format.format(((float) fn.quantity / (float) cantidad) * 100));
				}

				linea.append("\n");
			}
			linea.append("\n");
			f_fenotipos.writeArchive(linea.toString());
		}

		if (verFrontera == true) {

			colectFenotipoD(organisms, aFenotipos);// trabajar con los organismos de
			// la derecha
			numero = aFenotipos.size;
			int cantidadD = 0;
			for (Phenotype fn : aFenotipos) {
				cantidadD = cantidadD + fn.quantity;
			}

			linea.replace(0, linea.length(), "");

			linea.append(tx.panelDer + "\n");

			for (int i = numero - 1; i >= 0; i--) {

				Phenotype fn = aFenotipos.get(i);

				if (i == numero - 1) {
					linea.append(";"
							+ addCero2().toString()
							+ minutes2
							+ ":"
							+ addCero().toString()
							+ seconds
							+ ";"
							+ fn.name
							+ ";"
							+ fn.quantity
							+ ";"
							+ format.format(((float) fn.quantity / (float) cantidadD) * 100));
				}
				if (i < numero - 1) {
					linea.append(";;"
							+ fn.name
							+ ";"
							+ fn.quantity
							+ ";"
							+ format.format(((float) fn.quantity / (float) cantidadD) * 100));
				}

				linea.append("\n");
			}

			colectFenotipoI(organisms, aFenotipos);// trabajar con los organismos de
			// la izquierda
			numero = aFenotipos.size;
			int cantidadI = 0;
			for (Phenotype fn : aFenotipos) {
				cantidadI = cantidadI + fn.quantity;
			}

			linea.append(tx.panelIz + "\n");

			for (int i = numero - 1; i >= 0; i--) {

				Phenotype fn = aFenotipos.get(i);

				if (i == numero - 1) {
					linea.append(";"
							+ addCero2().toString()
							+ minutes2
							+ ":"
							+ addCero().toString()
							+ seconds
							+ ";"
							+ fn.name
							+ ";"
							+ fn.quantity
							+ ";"
							+ format.format(((float) fn.quantity / (float) cantidadI) * 100));
				}
				if (i < numero - 1) {
					linea.append(";;"
							+ fn.name
							+ ";"
							+ fn.quantity
							+ ";"
							+ format.format(((float) fn.quantity / (float) cantidadI) * 100));
				}

				linea.append("\n");
			}

			f_fenotipos.writeArchive(linea.toString());
		}

	}

	public void archivarGenotipo() {

		if (verFrontera == false) {

			colectGenotipo(organisms, aGenotipos);

			numero = aGenotipos.size;
			int cantidad = 0;

			for (Genotype gn : aGenotipos) {
				cantidad = cantidad + gn.quantity;
			}

			linea.replace(0, linea.length(), "");
			// linea.append(";"+minutos2+":"+segundos+"");

			for (int i = numero - 1; i >= 0; i--) {

				Genotype gn = aGenotipos.get(i);

				if (i == numero - 1) {
					linea.append(";"
							+ addCero2().toString()
							+ minutes2
							+ ":"
							+ addCero().toString()
							+ seconds
							+ ";"
							+ gn.name
							+ ";"
							+ gn.quantity
							+ ";"
							+ format.format(((float) gn.quantity / (float) cantidad) * 100));
				}
				if (i < numero - 1) {
					linea.append(";;"
							+ gn.name
							+ ";"
							+ gn.quantity
							+ ";"
							+ format.format(((float) gn.quantity / (float) cantidad) * 100));
				}

				linea.append("\n");
			}
			linea.append("\n");
			f_genotipos.writeArchive(linea.toString());
		}

		if (verFrontera == true) {

			colectGenotipoD(organisms, aGenotipos);// trabajar con los organismos de
			// la derecha
			numero = aGenotipos.size;
			int cantidadD = 0;
			for (Genotype gn : aGenotipos) {
				cantidadD = cantidadD + gn.quantity;
			}
			linea.replace(0, linea.length(), "");
			// linea.append(minutos2+":"+segundos+"\n");

			linea.append(tx.panelDer + "\n");

			for (int i = numero - 1; i >= 0; i--) {

				Genotype gn = aGenotipos.get(i);

				if (i == numero - 1) {
					linea.append(";"
							+ addCero2().toString()
							+ minutes2
							+ ":"
							+ addCero().toString()
							+ seconds
							+ ";"
							+ gn.name
							+ ";"
							+ gn.quantity
							+ ";"
							+ format.format(((float) gn.quantity / (float) cantidadD) * 100));
				}
				if (i < numero - 1) {
					linea.append(";;"
							+ gn.name
							+ ";"
							+ gn.quantity
							+ ";"
							+ format.format(((float) gn.quantity / (float) cantidadD) * 100));
				}

				linea.append("\n");
			}

			colectGenotipoI(organisms, aGenotipos);// trabajar con los organismos de
			// la izquierda
			numero = aGenotipos.size;
			int cantidadI = 0;
			for (Genotype gn : aGenotipos) {
				cantidadI = cantidadI + gn.quantity;
			}

			linea.append(tx.panelIz + "\n");

			for (int i = numero - 1; i >= 0; i--) {

				Genotype gn = aGenotipos.get(i);

				if (i == numero - 1) {
					linea.append(";"
							+ addCero2().toString()
							+ minutes2
							+ ":"
							+ addCero().toString()
							+ seconds
							+ ";"
							+ gn.name
							+ ";"
							+ gn.quantity
							+ ";"
							+ format.format(((float) gn.quantity / (float) cantidadI) * 100));
				}
				if (i < numero - 1) {
					linea.append(";;"
							+ gn.name
							+ ";"
							+ gn.quantity
							+ ";"
							+ format.format(((float) gn.quantity / (float) cantidadI) * 100));
				}

				linea.append("\n");
			}
			linea.append("\n");

			f_genotipos.writeArchive(linea.toString());
		}

	}

	public void archivarGenoma() {
		if (verFrontera == false) {

			colectarAlelos(organisms, aAlelos);

			numero = aAlelos.size;
			int cantidad = 0;

			for (Allele al : aAlelos) {
				cantidad = cantidad + al.quantity;
			}

			linea.replace(0, linea.length(), "");
			linea.append("\n" + tx.tiempo + ": " + addCero2().toString()
					+ minutes2 + ":" + addCero().toString() + seconds + "\n\n");

			for (int i = numero - 1; i >= 0; i--) {

				Allele al = aAlelos.get(i);

				linea.append(al.name + "-a" + al.identifier + ">  "
						+ al.sequence);

				linea.append("\n");
			}
			f_genes.writeArchive(linea.toString());
		}

		if (verFrontera == true) {

			colectarAlelosD(organisms, aAlelos);// trabajar con los organismosdela
			// derecha
			numero = aAlelos.size;
			int cantidadD = 0;
			for (Allele al : aAlelos) {
				cantidadD = cantidadD + al.quantity;
			}
			linea.replace(0, linea.length(), "");
			linea.append("\n" + tx.tiempo + ": " + addCero2().toString()
					+ minutes2 + ":" + addCero().toString() + seconds + "\n\n");

			linea.append(tx.panelDer + "\n");

			for (int i = numero - 1; i >= 0; i--) {

				Allele al = aAlelos.get(i);

				linea.append(al.name + "-a" + al.identifier + ">  "
						+ al.sequence);

				linea.append("\n");
			}

			colectarAlelosI(organisms, aAlelos);// trabajar con los organismosdela
			// izquierd
			numero = aAlelos.size;
			int cantidadI = 0;
			for (Allele al : aAlelos) {
				cantidadI = cantidadI + al.quantity;
			}

			linea.append(tx.panelIz + "\n");// trabajar con los organismos de la
			// izquierda

			for (int i = numero - 1; i >= 0; i--) {

				Allele al = aAlelos.get(i);

				linea.append(al.name + "-a" + al.identifier + ">  "
						+ al.sequence);

				linea.append("\n");
			}

			f_genes.writeArchive(linea.toString());
		}

	}

	public void archivarProteoma() {
		if (organisms.size > 0) {

			f_proteoma.writeArchive("" + addCero2().toString() + minutes2
					+ ":" + addCero().toString() + seconds + "\n");
			numero = aEspesies.size;
			for (int i = numero - 1; i >= 0; i--) {
				Organism or = aEspesies.get(i);
				linea.replace(0, linea.length(), "");
				linea.append(">" + or.nombre.toString() + "\n"
						+ or.adn.collectProteome(this) + "\n\n");

				f_proteoma.writeArchive(linea.toString());
			}
			f_proteoma.writeArchive("\n\n");
		}
	}

	public void archivarFenotipo() {
		if (organisms.size > 0) {

			f_mutantes.writeArchive("" + addCero2().toString() + minutes2
					+ ":" + addCero().toString() + seconds);
			numero = aEspesies.size;
			for (int i = numero - 1; i >= 0; i--) {
				Organism or = aEspesies.get(i);

				linea.replace(0, linea.length(), "");
				linea.append(" " + ";" + or.nombre.toString() + ";"
						+ or.quantity);

				if (collectHeight == true) {
					linea.append("," + (or.height + or.width));
				}

				if (collectSpeed == true) {
					linea.append(";" + or.speed);
				}
				if (collectColor == true) {
					linea.append(";" + or.color);
				}
				if (collectMutationRate == true) {
					linea.append(";" + or.mutationRate);
				}
				if (collectTemp == true) {
					linea.append(";" + or.optimalTemp);
				}
				if (collectLongevity == true) {
					linea.append(";" + or.longevity);
				}
				if (collectRadius == true) {
					linea.append(";" + or.radius);
				}
				if (collectPheromone == true) {
					linea.append(";" + or.pheromone);
				}
				if (collectPredator == true) {
					linea.append(";" + or.carnivore);
				}
				if (collectSense == true) {
					linea.append(";" + or.sense);
				}
				if (collectHunt == true) {
					linea.append(";" + or.hunt);
				}
				if (collectEscape == true) {
					linea.append(";" + or.escape);
				}
				if (collectResistance == true) {
					linea.append(";" + or.resistanceATB);
				}
				linea.append("\n");

				f_mutantes.writeArchive(linea.toString());
			}

		}
	}

	public int contarOrganismos(Array<Organism> aor, boolean der) {

		int cuenta = 0;

		for (int i = 0; i < aor.size; i++) {
			if (der == true) {
				if (aor.get(i).position.x > ancho / 2) {
					cuenta = cuenta + 1;
				}
			}
			if (der == false) {
				if (aor.get(i).position.x < ancho / 2) {
					cuenta = cuenta + 1;
				}
			}

		}

		return cuenta;

	}

	// colecta datos para graficar
	public void guardarDatos() {
		if (verFrontera == false) {
			linea.replace(
					0,
					linea.length(),
					"" + addCero2().toString() + minutes2 + ":"
							+ addCero().toString() + seconds + ";" + organisms.size
							+ ";" + cantidadPredadores(organisms) + ";"
							+ cantidadResistentes(organisms) + ";"
							+ format.format(velocidadMedia(organisms)) + ";"
							+ format.format(tamanoMedio(organisms)) + ";"
							+ format.format(longevidadMedio(organisms)) + ";"
							+ format.format(tasaMutMedio(organisms)) + ";"
							+ format.format(temperatura) + ";"
							+ format.format(temOptimaMedia(organisms)) + "\n");
			f_datos.writeArchive(linea.toString());
		}

		if (verFrontera == true) {

			linea.replace(
					0,
					linea.length(),
					addCero2().toString()
							+ minutes2
							+ ":"
							+ addCero().toString()
							+ seconds
							+ ";"
							+ contarOrganismos(organisms, false)
							+ ";"
							+ contarOrganismos(organisms, true)
							+ ";"
							+ contarOrganismos(aEspesies, false)
							+ ";"
							+ contarOrganismos(aEspesies, true)
							+ ";"
							+ (contarOrganismos(organisms, false) - cantidadPredadoresI(organisms))
							+ ";"
							+ (contarOrganismos(organisms, true) - cantidadPredadoresD(organisms))
							+ ";" + cantidadPredadoresI(organisms) + ";"
							+ cantidadPredadoresD(organisms) + ";"
							+ cantidadResistentesI(organisms) + ";"
							+ cantidadResistentesD(organisms) + ";"
							+ format.format(velocidadMediaI(organisms)) + ";"
							+ format.format(velocidadMediaD(organisms)) + ";"
							+ format.format(tamanoMedioI(organisms)) + ";"
							+ format.format(tamanoMedioD(organisms)) + ";"
							+ format.format(longevidadMedioI(organisms)) + ";"
							+ format.format(longevidadMedioD(organisms)) + ";"
							+ tasaMutMedioI(organisms) + ";" + tasaMutMedioD(organisms)
							+ ";" + format.format(temperatura) + ";"
							+ format.format(temOptimaMediaI(organisms)) + ";"
							+ format.format(temOptimaMediaD(organisms)) + "\n");
			f_datos.writeArchive(linea.toString());
		}

	}

	public void tomarMuestras() {

		if (segundos2 >= tiempoMuestreo) {

			colectorEspesies();// colecta las especies o mutante de ese momento

			guardarDatos();
			archivarGenoma();

			archivarGenotipo();
			archivarFenotipo2();
			archivarAlelos();

			segundos2 = 0;
		}
	}

	public void activarCatastrofe() {

		if (segundos3 >= tiempoCatastrofe && tiempoCatastrofe != 0) {

			catastrofe();

			segundos3 = 0;
		}

	}

	public void activarATB() {

		if (segundos5 == tiempoATB && antibiotico == -1 && tiempoATB != 0) {

			antibiotico = 1;

		}

	}

	public void archivaTodaslasEspecies() {

		StringBuffer linea = new StringBuffer();

		for (Organism or : aEspesiesTotales) {
			linea.replace(0, linea.length(), "");
			linea.append(">" + or.fechaNacimiento + " " + or.nombre.toString()
					+ "\n" + or.adn.collectGenome(this) + "\n");
			f_arbol.writeArchive(linea.toString());
		}

		f_arbol.cerrarArchivo();
	}

	// metodo que actualiza el sitema

	public void update() {

		if (parar == false) {
			if (maxTime == 0) {
				if (organisms.size > 0) {

					if (verFrontera == true) {

						cantidadOrganismosI(organisms);
						cantidadOrganismosD(organisms);
						cantidadEspeciesI(aEspesies);
						cantidadEspeciesD(aEspesies);

					}

					for (Senergy es : ase) {
						es.update();
					}// mueve los cuantos de luz
					for (Qenergy eq : aqe) {
						eq.update();
					}// mueve los cuantos de luz
					for (Organism or : organisms) {
						or.update();
					}// mueve los organismos
					if (seconds == 60) {
						System.gc();
					}
					detectarColiciones();
					repoduccionParteNoGen();
					chequearBalance();
					activarCatastrofe();
					activarATB();
					colectorEspesiesTotales(organisms);
					tomarMuestras();
					termociclar();
					contadorTiempo();

				}
			}

			if (maxTime > 0) {
				if (organisms.size > 0 && seconds4 < maxTime) {

					if (verFrontera == true) {

						cantidadOrganismosI(organisms);
						cantidadOrganismosD(organisms);
						cantidadEspeciesI(aEspesies);
						cantidadEspeciesD(aEspesies);

					}

					for (Senergy es : ase) {
						es.update();
					}// mueve los cuantos de luz
					for (Qenergy eq : aqe) {
						eq.update();
					}// mueve los cuantos de luz
					for (Organism or : organisms) {
						or.update();
					}// mueve los organismos
					if (seconds == 60) {
						System.gc();
					}
					detectarColiciones();
					repoduccionParteNoGen();
					chequearBalance();
					activarCatastrofe();
					activarATB();
					colectorEspesiesTotales(organisms);
					tomarMuestras();
					termociclar();
					contadorTiempo();

				}
			}
		}
	}

	// method that controls the temperature

	public double termociclar() {

		// primer control de temperatura

		if (deltaTime1 <= 0 && minStar1 != 0 && minutes2 >= minStar1) {
			temperatura = TempFinal1;
		}

		if (deltaTime1 > 0 && minStar1 != 0 && minutes2 >= minStar1
				&& minutes2 < minStar1 + deltaTime1) {

			if (delta3Time() > msecondTime(1000)) {

				temperatura = temperatura + tempXSecond1;

				setDelta3();

			}
		}

		// segundo control de temperatura

		if (deltaTime2 <= 0 && minStar2 != 0 && minutes2 >= minStar2) {
			temperatura = TempFinal2;
		}

		if (deltaTime2 > 0 && minStar2 != 0 && minutes2 >= minStar2
				&& minutes2 < minStar2 + deltaTime2) {

			if (delta3Time() > msecondTime(1000)) {

				temperatura = temperatura + tempXSecond2;

				setDelta3();

			}
		}

		return temperatura;

	}

	// metodos que manejan el tiempo de corrida

	public void contadorTiempo() {

		if (deltaTime() > msecondTime(1000)) {

			seconds = seconds + 1;
			segundos2 = segundos2 + 1;
			segundos3 = segundos3 + 1;
			seconds4 = seconds4 + 1;
			segundos5 = segundos5 + 1;

			if (seconds == 60) {
				seconds = 0;
				minutos = minutos + 1;
				minutes2 = minutes2 + 1;
				minutos3 = minutos3 + 1;
			}

			if (minutos == 60) {
				minutos = 0;
				horas = horas + 1;
			}

			if (horas == 24) {
				horas = 0;
				dias = dias + 1;
			}

			setDelta();
		}
	}

	// agregar cero antes de los segundos menores a 9

	public StringBuffer addCero() {

		ceroIz.replace(0, ceroIz.length(), "");

		if (seconds < 10) {
			ceroIz.append("0");
		}

		return ceroIz;
	}

	public StringBuffer addCero2() {

		ceroIz2.replace(0, ceroIz2.length(), "");

		if (minutes2 < 10) {
			ceroIz2.append("0");
		}

		return ceroIz2;
	}

	// mide el tiempo transcurrido desde el unltimo set

	public long deltaTime() {
		return TimeUtils.nanoTime() - delta;
	}

	public long delta2Time() {
		return TimeUtils.nanoTime() - delta2;
	}

	public long delta3Time() {
		return TimeUtils.nanoTime() - delta3;
	}

	public long tiemoTime() {
		return TimeUtils.nanoTime() - tiempo;
	}

	// anota el tiempo de juego transcurrido en el momento que se invoca un
	// evento

	public void setDelta() {
		delta = TimeUtils.nanoTime();
	}

	public void setDelta2() {
		delta2 = TimeUtils.nanoTime();
	}

	public void setDelta3() {
		delta3 = TimeUtils.nanoTime();
	}

	public void setTiempo() {
		tiempo = TimeUtils.nanoTime();
	}

	// convierte de ms a nanosegundos para mas comodidad
	public long msecondTime(long ms) {

		return ms * 1000000;
	}

	public void detectarColiciones() {

		// organismo toca la energia verde

		for (int i = 0; i < organisms.size; i++) {
			Organism or = organisms.get(i);
			Rectangle er = or.border;

			if (or.carnivore == false && or.muriendo == 0) { // el organismo no
				// es carnivoro

				// chequea la posicion del organismo para ver que cuantos de
				// energia buscar

				if (or.position.x <= ancho * (1 / 10)) {

					for (int a = 0; a < ase1.size; a++) {
						Senergy se = ase1.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energy < or.capacity) {
								double delta = or.capacity - or.energy;
								or.energy = or.energy + se.energy;
								se.energy = se.energy - delta;
								if (se.energy <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energy > or.capacity) {
							or.energy = or.capacity;
						}
					}
				}

				if (or.position.x > ancho * (1 / 10)
						&& or.position.x <= ancho * (2 / 10)) {

					for (int a = 0; a < ase2.size; a++) {
						Senergy se = ase2.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energy < or.capacity) {
								double delta = or.capacity - or.energy;
								or.energy = or.energy + se.energy;
								se.energy = se.energy - delta;
								if (se.energy <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energy > or.capacity) {
							or.energy = or.capacity;
						}
					}
				}

				if (or.position.x > ancho * (2 / 10)
						&& or.position.x <= ancho * (3 / 10)) {

					for (int a = 0; a < ase3.size; a++) {
						Senergy se = ase3.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energy < or.capacity) {
								double delta = or.capacity - or.energy;
								or.energy = or.energy + se.energy;
								se.energy = se.energy - delta;
								if (se.energy <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energy > or.capacity) {
							or.energy = or.capacity;
						}
					}
				}

				if (or.position.x > ancho * (3 / 10)
						&& or.position.x <= ancho * (4 / 10)) {

					for (int a = 0; a < ase4.size; a++) {
						Senergy se = ase4.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energy < or.capacity) {
								double delta = or.capacity - or.energy;
								or.energy = or.energy + se.energy;
								se.energy = se.energy - delta;
								if (se.energy <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energy > or.capacity) {
							or.energy = or.capacity;
						}
					}
				}

				if (or.position.x > ancho * (4 / 10)
						&& or.position.x <= ancho * (5 / 10)) {

					for (int a = 0; a < ase5.size; a++) {
						Senergy se = ase5.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energy < or.capacity) {
								double delta = or.capacity - or.energy;
								or.energy = or.energy + se.energy;
								se.energy = se.energy - delta;
								if (se.energy <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energy > or.capacity) {
							or.energy = or.capacity;
						}
					}
				}

				if (or.position.x > ancho * (5 / 10)
						&& or.position.x <= ancho * (6 / 10)) {

					for (int a = 0; a < ase6.size; a++) {
						Senergy se = ase6.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energy < or.capacity) {
								double delta = or.capacity - or.energy;
								or.energy = or.energy + se.energy;
								se.energy = se.energy - delta;
								if (se.energy <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energy > or.capacity) {
							or.energy = or.capacity;
						}
					}
				}

				if (or.position.x > ancho * (6 / 10)
						&& or.position.x <= ancho * (7 / 10)) {

					for (int a = 0; a < ase7.size; a++) {
						Senergy se = ase7.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energy < or.capacity) {
								double delta = or.capacity - or.energy;
								or.energy = or.energy + se.energy;
								se.energy = se.energy - delta;
								if (se.energy <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energy > or.capacity) {
							or.energy = or.capacity;
						}
					}
				}

				if (or.position.x > ancho * (7 / 10)
						&& or.position.x <= ancho * (8 / 10)) {

					for (int a = 0; a < ase8.size; a++) {
						Senergy se = ase8.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energy < or.capacity) {
								double delta = or.capacity - or.energy;
								or.energy = or.energy + se.energy;
								se.energy = se.energy - delta;
								if (se.energy <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energy > or.capacity) {
							or.energy = or.capacity;
						}
					}
				}

				if (or.position.x > ancho * (8 / 10)
						&& or.position.x <= ancho * (9 / 10)) {

					for (int a = 0; a < ase9.size; a++) {
						Senergy se = ase9.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energy < or.capacity) {
								double delta = or.capacity - or.energy;
								or.energy = or.energy + se.energy;
								se.energy = se.energy - delta;
								if (se.energy <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energy > or.capacity) {
							or.energy = or.capacity;
						}
					}
				}

				if (or.position.x > ancho * (9 / 10) && or.position.x <= ancho) {

					for (int a = 0; a < ase10.size; a++) {
						Senergy se = ase10.get(a);
						Rectangle tr = se.borde;
						if (se.visible == true) {
							if (er.overlaps(tr) && or.energy < or.capacity) {
								double delta = or.capacity - or.energy;
								or.energy = or.energy + se.energy;
								se.energy = se.energy - delta;
								if (se.energy <= 0) {
									se.visible = false;
								}
							}
						}
						if (or.energy > or.capacity) {
							or.energy = or.capacity;
						}
					}
				}

			}
		}

		// organismo toca la biomasa

		for (int i = 0; i < organisms.size; i++) {
			Organism or = organisms.get(i);
			Rectangle er = or.border;

			if (or.carnivore == false && or.muriendo == 0) {

				// chequea la posicion del organismo para ver que cuantos de
				// energia buscar

				if (or.position.x <= ancho * (1 / 10)) {
					for (int a = 0; a < aqe1.size; a++) {
						Qenergy qe = aqe1.get(a);
						Rectangle tr = qe.border;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomass < or.capacity) {
									or.biomass = (int) (or.biomass + qe.mass);
									qe.visible = false;
								}
							}
						}
					}
				}

				if (or.position.x > ancho * (1 / 10)
						&& or.position.x <= ancho * (2 / 10)) {
					for (int a = 0; a < aqe2.size; a++) {
						Qenergy qe = aqe2.get(a);
						Rectangle tr = qe.border;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomass < or.capacity) {
									or.biomass = (int) (or.biomass + qe.mass);
									qe.visible = false;
								}
							}
						}
					}
				}

				if (or.position.x > ancho * (2 / 10)
						&& or.position.x <= ancho * (3 / 10)) {
					for (int a = 0; a < aqe3.size; a++) {
						Qenergy qe = aqe3.get(a);
						Rectangle tr = qe.border;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomass < or.capacity) {
									or.biomass = (int) (or.biomass + qe.mass);
									qe.visible = false;
								}
							}
						}
					}
				}

				if (or.position.x > ancho * (3 / 10)
						&& or.position.x <= ancho * (4 / 10)) {
					for (int a = 0; a < aqe4.size; a++) {
						Qenergy qe = aqe4.get(a);
						Rectangle tr = qe.border;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomass < or.capacity) {
									or.biomass = (int) (or.biomass + qe.mass);
									qe.visible = false;
								}
							}
						}
					}
				}

				if (or.position.x > ancho * (4 / 10)
						&& or.position.x <= ancho * (5 / 10)) {
					for (int a = 0; a < aqe5.size; a++) {
						Qenergy qe = aqe5.get(a);
						Rectangle tr = qe.border;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomass < or.capacity) {
									or.biomass = (int) (or.biomass + qe.mass);
									qe.visible = false;
								}
							}
						}
					}
				}

				if (or.position.x > ancho * (5 / 10)
						&& or.position.x <= ancho * (6 / 10)) {
					for (int a = 0; a < aqe6.size; a++) {
						Qenergy qe = aqe6.get(a);
						Rectangle tr = qe.border;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomass < or.capacity) {
									or.biomass = (int) (or.biomass + qe.mass);
									qe.visible = false;
								}
							}
						}
					}
				}

				if (or.position.x > ancho * (6 / 10)
						&& or.position.x <= ancho * (7 / 10)) {
					for (int a = 0; a < aqe7.size; a++) {
						Qenergy qe = aqe7.get(a);
						Rectangle tr = qe.border;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomass < or.capacity) {
									or.biomass = (int) (or.biomass + qe.mass);
									qe.visible = false;
								}
							}
						}
					}
				}

				if (or.position.x > ancho * (7 / 10)
						&& or.position.x <= ancho * (8 / 10)) {
					for (int a = 0; a < aqe8.size; a++) {
						Qenergy qe = aqe8.get(a);
						Rectangle tr = qe.border;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomass < or.capacity) {
									or.biomass = (int) (or.biomass + qe.mass);
									qe.visible = false;
								}
							}
						}
					}
				}

				if (or.position.x > ancho * (8 / 10)
						&& or.position.x <= ancho * (9 / 10)) {
					for (int a = 0; a < aqe9.size; a++) {
						Qenergy qe = aqe9.get(a);
						Rectangle tr = qe.border;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomass < or.capacity) {
									or.biomass = (int) (or.biomass + qe.mass);
									qe.visible = false;
								}
							}
						}
					}
				}

				if (or.position.x > ancho * (9 / 10) && or.position.x <= ancho) {
					for (int a = 0; a < aqe10.size; a++) {
						Qenergy qe = aqe10.get(a);
						Rectangle tr = qe.border;

						if (qe.visible == true) {
							if (er.overlaps(tr)) {
								if (or.biomass < or.capacity) {
									or.biomass = (int) (or.biomass + qe.mass);
									qe.visible = false;
								}
							}
						}
					}
				}

			}
		}

		// Organismo toca organismo

		for (int x = 0; x < organisms.size; x++) {

			Organism or = organisms.get(x);
			Rectangle er = or.border;

			// Fecundacion
			if (or.seconds >= or.timeMitosis / 1000 && or.male == false
					&& or.parteNoGen == false) {// Si es mayor de edad, hembra y
				// se reproduce sexualmente

				for (int a = x; a < organisms.size; a++) { // Se busca pareja
					Organism or2 = organisms.get(a);
					Rectangle er2 = or2.border;

					if (or2.seconds >= or2.timeMitosis / 1000
							&& or2.male == true) { // o2 es macho

						if (er.overlaps(er2)) { // Se tocan?
							if (a != x) {

								or.Fecundation(or, or2); // Se produce la
								// fecundacion
								a = organisms.size; // No busca más machos

							}
						}
					}
				}
			}

			// Fecundacion partenogenesis con Cortejo

			if (or.seconds >= or.timeMitosis / 1000 && or.male == false
					&& or.parteNoGen == true) {// Si es mayor de edad, hembra y
				// se reproduce asexualmente

				for (int a = x; a < organisms.size; a++) { // Se busca pareja
					Organism or2 = organisms.get(a);
					Rectangle er2 = or2.border;

					if (or2.seconds >= or2.timeMitosis / 1000
							&& or2.male == true) { // o2 es macho

						if (er.overlaps(er2)) { // Se tocan?
							if (a != x) {

								or.ReproduccionParteNoGen(or); // se reproduce
								// asexualmente
								// y listo
								a = organisms.size; // No busca más machos

							}
						}
					}
				}
			}
			
			
			//infectado
			
		/*	if(or.infectado== true) {//el organismo esta infactado
				
				for (int a = 0; a < aorg.size; a++) { // Se busca una un sano
					Organismo or2 = aorg.get(a);
					Rectangle er2 = or2.borde;

					if (or2.infectado == false) { // or2 es otro organismo y no esta infectado

						if (er.overlaps(er2)) { //Se tocan ?
							
						float random = (float) (Math.random()*1000);//
						
				 		if(random < 1000*(float) (virus.contagio/100) ) {//chanse de contagiarse por contacto
					 			 		
				 		if(or2.inmune=false){ or2.infectado = true; }//el organismo se enferma
				 		
				 		if(or2.inmune=true) { 
				 			float random2 = (float) (Math.random()*100);//si supera la proteccion de la inmunidad
				 			
				 			if (random2> virus.vacuna) { or2.infectado = true; }//el organismo se infecta
				 		}
				 		
				 		}}

					}
				}
			}*/


			// Carnivoros

			if (or.carnivore == true && or.muriendo == 0) { // El organismo es
				// carnivoro

				for (int a = 0; a < organisms.size; a++) { // Se busca una presa
					Organism or2 = organisms.get(a);
					Rectangle er2 = or2.border;

					if (or.identifier != or2.identifier
							&& or.capacity >= or2.capacity) { // La presa es
						// otro
						// organismo

						if (er.overlaps(er2)) { // Se tocan ?
							if (a != x) {

								// intercamvio de masa y energia
								EnRe = (int) (or.capacity - or.energy);
								BioRe = (int) (or.capacity - or.biomass);

								if (EnRe >= or2.energy && EnRe > 0) {
									or.energy = or.energy + or2.energy;
									or2.energy = 0;
								}
								if (EnRe < or2.energy && EnRe > 0) {
									or.energy = or.energy + EnRe;
									or2.energy = or2.energy - EnRe;
								}

								if (BioRe >= or2.biomass && BioRe > 0) {
									or.biomass = or.biomass + or2.biomass;
									or2.biomass = 0;
								}
								if (BioRe < or2.biomass && BioRe > 0) {
									or.biomass = or.biomass + BioRe;
									or2.biomass = or2.biomass - BioRe;
								}

								if (or2.energy <= 0) {
									or2.morir();
								} // muerte de la presa

							}
						}

					}
				}
			}

		}

		// organismo toca la barrera

		// organismo toca la barrera

		if (verFrontera == true) {

			for (int x = 0; x < organisms.size; x++) {

				Organism or = organisms.get(x);
				Rectangle er = or.border;
				if (er.overlaps(frontera)) {
					if (or.position.x < (ancho / 2 - or.width) || or.position.x < ancho / 2 && or.direction.x > 0) {
						or.position.x = 0;
						//or.direccion.x = -1;
					}

					if (or.position.x > (ancho / 2 + or.width * 2) || or.position.x > ancho / 2 && or.direction.x < 0) {
						or.position.x = ancho;
						//or.direccion.x = 1;
					}

				}
			}
		}
	}

	// reproduccion partenogenetica Sin cortejo
	public void repoduccionParteNoGen() {

		// reproduccion partenogenetica

		for (int x = 0; x < organisms.size; x++) {

			Organism or = organisms.get(x);

			if (or.seconds >= or.timeMitosis / 1000 && or.timeCycle >= 10
					&& or.male == false && or.parteNoGen == true) {// Si es
				// mayor de
				// edad,
				// hembra y
				// se
				// reproduce
				// asexualmente

				or.ReproduccionParteNoGenSinCortejo(or); // se reproduce
				// asexualmente y
				// listo

			}
		}
	}

	// Horizontal transfer

	public void contagio(Organism or1, Organism or2) {


	}

	public void horizontalTransfer(Organism or1, Organism or2) {

		if (horizontalTransferRate > 0) {
			int random = (int) (Math.random() * horizontalTransferRate);//
			// int pos = (int) (Math.random()*100);
			int gentipe = (int) (Math.random() * 12);

			if (random == 0) {

				switch (gentipe) {
					case 0:
						or1.adn.height.insert(0, or2.adn.height);

						break;
					case 1:
						or1.adn.width.insert(0, or2.adn.width);

						break;

					case 2:
						or1.adn.color.insert(0, or2.adn.color);

						break;

					case 3:
						or1.adn.speed.insert(0, or2.adn.speed);

						break;

					case 4:
						or1.adn.predator.insert(0, or2.adn.predator);

						break;

					case 5:
						or1.adn.sense.insert(0, or2.adn.sense);

						break;

					case 6:
						or1.adn.hunt.insert(0, or2.adn.hunt);

						break;

					case 7:
						or1.adn.escape.insert(0, or2.adn.escape);

						break;

					case 8:
						or1.adn.radius.insert(0, or2.adn.radius);

						break;

					case 9:
						or1.adn.mutationRate.insert(0, or2.adn.mutationRate);

						break;

					case 10:
						or1.adn.longevity.insert(0, or2.adn.longevity);

						break;

					case 11:
						or1.adn.toleranceTemp.insert(0, or2.adn.toleranceTemp);

						break;

					case 12:
						or1.adn.resistanceATB.insert(0, or2.adn.resistanceATB);

						break;

				}

				or1.transferred = true;
				or1.translate();

			}
		}
	}

	public void dispose() {

		// textuRA_ENER.dispose();
		// textura_ORG.dispose();
		// auraATB.dispose();
		// transferido.dispose();
		// textura_sex.dispose();
		// textura_feromona.dispose();
		organismTexture.dispose();
	}

}